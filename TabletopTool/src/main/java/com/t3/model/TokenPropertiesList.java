package com.t3.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.t3.xstreamversioned.SerializationVersion;

/**This is an immutable list of TokenProperty instances*/
@SerializationVersion(0)
public class TokenPropertiesList<T> implements Iterable<T> {
	
	private final T[] 		elements;
	private final Class<T>	type;

	@SuppressWarnings("unchecked")
	public TokenPropertiesList(Class<T> type) {
		this(type, (T[])new Object[0]);
	}
	
	private TokenPropertiesList(Class<T> type, T[] elements) {
		this.type=type;
		this.elements=elements;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(elements);
	}
	
	public int size() {
		return elements.length;
	}
	
	public T get(int i) {
		return elements[i];
	}
	
	public TokenPropertiesList<T> withAdded(T tp) {
		if(!type.isInstance(tp))
			throw new IllegalArgumentException("Was expepcting an element of type "+type.getSimpleName()+", but got "+tp.getClass().getSimpleName());
		return new TokenPropertiesList<T>(type, ArrayUtils.add(elements, tp));
	}

	@Override
	public Iterator<T> iterator() {
		return new TokenPropertyIterator();
	}
	
	private class TokenPropertyIterator implements Iterator<T> {

		private int i=0;
		
		@Override
		public boolean hasNext() {
			return i<elements.length;
		}

		@Override
		public T next() {
			return elements[i++];
		}
	}

	public boolean isEmpty() {
		return elements.length==0;
	}
	
	public List<T> asList() {
		return Arrays.asList(elements);
	}
}
