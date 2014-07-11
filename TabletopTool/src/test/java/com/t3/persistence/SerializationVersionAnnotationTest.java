package com.t3.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

import org.reflections.Reflections;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.beust.jcommander.Strings;
import com.t3.xstreamversioned.SerializationVersion;

public class SerializationVersionAnnotationTest {

	@DataProvider(name = "traverseAnnotatedClasses")
	public Object[][] traverseAnnotatedClasses() {
		Reflections reflections = new Reflections("com.t3");
		LinkedList<Class<?>> annotatedClasses = new LinkedList<>(reflections.getTypesAnnotatedWith(SerializationVersion.class));

		Assert.assertFalse(annotatedClasses.isEmpty());

		HashSet<Class<?>> checked = new HashSet<Class<?>>();
		while (!annotatedClasses.isEmpty()) {
			Class<?> cl = annotatedClasses.removeFirst();
			if (cl!=null && cl.getClassLoader()!=null && !checked.contains(cl)) {
				checked.add(cl);
				annotatedClasses.addAll(reflections.getSubTypesOf(cl));
				annotatedClasses.add(cl.getSuperclass());
			}
		}

		ArrayList<Class<?>> sorted=new ArrayList<>(checked);
		Collections.sort(sorted, new Comparator<Class<?>>() {
			public int compare(Class<?> o1, Class<?> o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		
		Object[][] result=new Object[checked.size()][1];
		for(int i=0;i<sorted.size();i++)
			result[i][0]=sorted.get(i);
		System.out.println(sorted);
		return result;
	}

	@Test(dataProvider = "traverseAnnotatedClasses")
	public void testAnnotation(Class<?> cl) {
		if(cl.getSuperclass()!=null && cl.getSuperclass().isEnum())
			cl=cl.getSuperclass();
		Assert.assertNotNull(cl.getAnnotation(SerializationVersion.class), cl + " is not annotated.");
	}
}
