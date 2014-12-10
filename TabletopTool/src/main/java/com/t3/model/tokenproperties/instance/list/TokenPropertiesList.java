package com.t3.model.tokenproperties.instance.list;

public interface TokenPropertiesList<T> extends Iterable<T> {

	@Override
	public abstract String toString();

	public abstract int size();

	public abstract T get(int i);

	public abstract TokenPropertiesList<T> withAdded(T tp);

	public abstract boolean isEmpty();

	public abstract TokenPropertiesList<T> without(int index);

}