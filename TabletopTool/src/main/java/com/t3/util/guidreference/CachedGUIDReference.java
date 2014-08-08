package com.t3.util.guidreference;

import com.t3.guid.GUID;
import com.t3.guid.UniquelyIdentifiable;
import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(0)
public abstract class CachedGUIDReference<VALUE extends UniquelyIdentifiable> extends GUIDReference<VALUE> {

	private transient VALUE cachedValue;
	
	public CachedGUIDReference(GUID id) {
		super(id);
	}

	public CachedGUIDReference(VALUE value) {
		super(value);
		cachedValue=value;
	}
	
	@Override
	public VALUE value() {
		if(cachedValue==null)
			cachedValue=super.value();
		return cachedValue;
	}
}
