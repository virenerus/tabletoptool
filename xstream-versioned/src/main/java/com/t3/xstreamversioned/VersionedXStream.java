package com.t3.xstreamversioned;

import java.util.LinkedList;

import org.apache.commons.lang3.ArrayUtils;

public class VersionedXStream {
	
	private VersionedXStream() {}

	/**
	 * This method generates a version list for a class and its super classes.
	 *  
	 * @param type the type of which you want the version
	 * @param strict if an error should be thrown if a class without a version is encountered
	 * @return an array containing the versions or null if none of the classes has one
	 */
	public static int[] generateVersion(Class<?> type, boolean strict) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		if(generateVersion(type,list,strict)) {
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
	 * @return a string containing the versions or null if none of the classes has one
	 */
	public static String generateVersionAsString(Class<?> type, boolean strict) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		if(generateVersion(type,list,strict)) {
			StringBuilder sb=new StringBuilder();
			for(Integer i:list)
				sb.append(i).append('.');
			return sb.reverse().substring(1);
		}
		else
			return null;
	}

	private static boolean generateVersion(Class<?> type, LinkedList<Integer> list, boolean strict) {
		if(type==null || Object.class.equals(type))
			return false;
		
		SerializationVersion version=type.getAnnotation(SerializationVersion.class);
		if(version==null) {
			if(strict && type.getClassLoader()!=null)
				throw new IllegalArgumentException("Cant serialize Element of type "+type.getName()+" because it is missing a version.");
			list.addLast(0);
			return generateVersion(type.getSuperclass(), list, strict);
		}
		else {
			list.addLast(version.value());
			generateVersion(type.getSuperclass(), list, strict);
			return true;
		}
	}

}
