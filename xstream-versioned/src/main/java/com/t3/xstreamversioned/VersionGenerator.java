package com.t3.xstreamversioned;

import java.util.LinkedList;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

public class VersionGenerator {
	
	private VersionGenerator() {}

	/**
	 * This method generates a version list for a class and its super classes.
	 *  
	 * @param type the type of which you want the version
	 * @param strict if an error should be thrown if a class without a version is encountered
	 * @param ignoredPackages the packages that should not throw an exception
	 * @return an array containing the versions or null if none of the classes has one
	 */
	public static int[] generateVersion(Class<?> type, boolean strict, Set<String> ignoredPackages) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		if(generateVersion(type,list,strict, ignoredPackages)) {
			int[] res=ArrayUtils.toPrimitive(list.toArray(new Integer[list.size()]));
			ArrayUtils.reverse(res);
			return res;
		}
		else
			return null;
	}
	
	/**
	 * This method generates a version list for a class and its super classes. It generates a string with the
	 * versions delimeted by points
	 *  
	 * @param type the type of which you want the version
	 * @param strict if an error should be thrown if a class without a version is encountered
	 * @param ignoredPackages the packages that should not throw an exception
	 * @return a string containing the versions or null if none of the classes has one
	 */
	public static String generateVersionAsString(Class<?> type, boolean strict, Set<String> ignoredPackages) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		if(generateVersion(type,list,strict, ignoredPackages)) {
			StringBuilder sb=new StringBuilder();
			for(Integer i:list)
				sb.append(i).append('.');
			return sb.reverse().substring(1);
		}
		else
			return null;
	}

	private static boolean generateVersion(Class<?> type, LinkedList<Integer> list, boolean strict, Set<String> ignoredPackages) {
		if(type==null || Object.class.equals(type) || type.isArray())
			return false;
		
		//subclasses of enums can't be annotated and their serialization is done by the enum parent class
		if(type.getSuperclass()!=null && type.getSuperclass().isEnum())
			return generateVersion(type.getSuperclass(), list, strict, ignoredPackages);
		
		SerializationVersion version=type.getAnnotation(SerializationVersion.class);
		if(version==null) {
			//if strict and class is not default jre class and not in ignroed package
			if(strict && type.getClassLoader()!=null && !isInIgnoredPackage(ignoredPackages, type))
				throw new IllegalArgumentException("Cant serialize Element of type "+type.getName()+" because it is missing a version.");
			else {
				list.addLast(0);
				return generateVersion(type.getSuperclass(), list, strict, ignoredPackages);
			}
		}
		else {
			list.addLast(version.value());
			generateVersion(type.getSuperclass(), list, strict, ignoredPackages);
			return true;
		}
	}

	private static boolean isInIgnoredPackage(Set<String> ignoredPackages, Class<?> type) {
		String typeName=type.getName();
		for(String p:ignoredPackages)
			if(typeName.startsWith(p))
				return true;
		return false;
	}

}
