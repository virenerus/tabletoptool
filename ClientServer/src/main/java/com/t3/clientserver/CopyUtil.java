package com.t3.clientserver;

public class CopyUtil {
	/**
	 * This method creates a deep copy of another object without fully serializing and deserializing it. Be aware not to
	 * pass interconnected objects into this method or you will get a copy of each reachable object. 
	 * @param element the element that you want to copy
	 * @return a deep copy of the element
	 */
	public static <T> T copy(T element) {
		return new KryoPool.KryoFactory().create().copy(element);
	}
}
