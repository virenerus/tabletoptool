package com.t3.model.tokenproperties.instance.list;

import java.util.Iterator;

public class EmptyTokenPropertiesList implements TokenPropertiesList<Void> {

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Void get(int i) {
		throw new UnsupportedOperationException();
	}

	@Override
	public TokenPropertiesList<Void> withAdded(Void tp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<Void> iterator() {
		return new Iterator<Void>() {
			@Override
			public Void next() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean hasNext() {
				return false;
			}
		};
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public TokenPropertiesList<Void> without(int index) {
		throw new UnsupportedOperationException();
	}

}
