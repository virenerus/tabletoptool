package com.t3.util;

import com.t3.model.InvalidGUIDException;
import com.t3.xstreamversioned.version.SerializationVersion;

/**
 * This class represents a reference to a certain object. The reference is only defined by some kind of id. It is able to
 * resolve the reference dynamically and caches the result. THe reference via id is needed to reference a certain object
 * even after network transmission.
 * @author Virenerus
 *
 */
@SerializationVersion(1)
public abstract class Reference<KEY, VALUE> {
	
	private final KEY id;
	private transient VALUE resolvedValue;
	
	public Reference(KEY id) {
		if(id==null)
			throw new InvalidGUIDException("Can't create a reference with id null.");
		this.id=id;
	}
	
	public Reference(KEY id, VALUE value) {
		if(value==null)
			throw new IllegalArgumentException("Cant't create a reference to null.");
		this.id=id;
		this.resolvedValue=value;
	}
	
	public KEY getId() {
		return id;
	}
	
	/**
	 * This method returns the value referenced by the id.
	 
	 * @return the value of the id
	 */
	//* @throws InvalidGUIDException if the GUID resolves to null
	public VALUE value() {
		if(resolvedValue==null)
			resolvedValue=resolveReference(id);
		return resolvedValue;
	}
	
	public boolean isValid() {
		return null != resolveReference(id);
	}

	protected abstract VALUE resolve(KEY id);
	
	protected VALUE resolveReference(KEY id) {
		resolvedValue=resolveReference(id);
		if(resolvedValue==null)
			throw new InvalidGUIDException("The GUID "+id+" of type "+this.getClass().getSimpleName()+" was resolved to null");
		else
			return resolvedValue;
	}
}
