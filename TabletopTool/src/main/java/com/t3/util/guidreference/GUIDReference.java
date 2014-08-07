package com.t3.util.guidreference;

import com.t3.guid.GUID;
import com.t3.guid.UniquelyIdentifiable;
import com.t3.model.InvalidGUIDException;
import com.t3.xstreamversioned.version.SerializationVersion;

/**
 * This class represents a reference to a certain object. The reference is only defined by the GUID and thus if the 
 * the object with the GUID is changed (through to network updates) this reference will resolve to new value.
 * @author Virenerus
 *
 */
@SerializationVersion(1)
public abstract class GUIDReference<VALUE extends UniquelyIdentifiable> implements UniquelyIdentifiable {
	
	private final GUID id;
	
	public GUIDReference(GUID id) {
		if(id==null)
			throw new InvalidGUIDException("Can't create a reference with id null.");
		this.id=id;
	}
	
	public GUIDReference(VALUE value) {
		if(value==null)
			throw new IllegalArgumentException("Cant't create a reference to null.");
		this.id=value.getId();
	}
	
	@Override
	public GUID getId() {
		return id;
	}
	
	/**
	 * This method returns the value referenced by the GUID.
	 * @throws InvalidGUIDException if the GUID resolves to null
	 * @return the value of the id
	 */
	public VALUE value() {
		VALUE v=resolveReference(id);
		if(v==null)
			throw new InvalidGUIDException("The GUID "+id+" of type "+this.getClass().getSimpleName()+" was resolved to null");
		else
			return v;
	}

	protected abstract VALUE resolveReference(GUID id);
}
