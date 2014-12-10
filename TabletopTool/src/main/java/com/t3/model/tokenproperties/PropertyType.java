/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3.model.tokenproperties;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.ArrayUtils;

import com.t3.clientserver.CopyUtil;
import com.t3.model.tokenproperties.instance.Struct;
import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(1)
public class PropertyType implements Serializable {
	private final String name;
	private PropertyType parent;
	private final String propertySet;
	private String shortName;
	private boolean highPriority;
	private boolean ownerOnly;
	private boolean gmOnly;
	private Object defaultValue;
	private final ValueType type;
	private final PropertyType[] subTypes;
	
	public PropertyType() {
		// For serialization
		name="";
		propertySet="";
		type=ValueType.TEXT; //there must always be a type set
		subTypes=new PropertyType[0];
	}

	public PropertyType(String propertySet, String name) {
		this(ValueType.TEXT, propertySet,name, null, false, false, false);
	}

	public PropertyType(ValueType type, String propertySet, String name, String shortName) {
		this(type,propertySet,name, shortName, false, false, false);
	}

	public PropertyType(ValueType type, String propertySet, String name, boolean highPriority, boolean isOwnerOnly, boolean isGMOnly) {
		this(type,propertySet,name, null, highPriority, isOwnerOnly, isGMOnly);
	}
	
	public PropertyType(ValueType type, String propertySet, String name, String shortName, boolean highPriority, boolean isOwnerOnly, boolean isGMOnly) {
		this(type,propertySet,name,shortName,highPriority,isOwnerOnly,isGMOnly,null);
	}
	
	public PropertyType(ValueType type, String propertySet, String name, String shortName, boolean highPriority, boolean isOwnerOnly, boolean isGMOnly, PropertyType[] subTypes) {
		this(type,propertySet,null,name,shortName,highPriority,isOwnerOnly,isGMOnly,null);
	}

	public PropertyType(ValueType type, String propertySet, PropertyType parent, String name, String shortName, boolean highPriority, boolean isOwnerOnly, boolean isGMOnly, PropertyType[] subTypes) {
		this.name = name;
		this.shortName = shortName;
		this.highPriority = highPriority;
		this.ownerOnly = isOwnerOnly;
		this.gmOnly = isGMOnly;
		this.type=type;
		this.propertySet=propertySet;
		this.defaultValue=type.getDefaultDefaultValue(this);
		if(subTypes==null)
			this.subTypes=createDefaultSubTypes(type);
		else {
			this.subTypes=subTypes;
			for(PropertyType st:this.subTypes)
				st.setParent(this);
		}
	}
	
	public PropertyType(PropertyType p) {
		name=p.name;
		propertySet=p.propertySet;
		shortName=p.shortName;
		highPriority=p.highPriority;
		ownerOnly=p.ownerOnly;
		gmOnly=p.gmOnly;
		defaultValue=p.defaultValue==null?p.type.getDefaultDefaultValue(this):p.defaultValue;
		type=p.type;
		subTypes=ArrayUtils.clone(p.getSubTypes());
		for(PropertyType st:subTypes)
			st.setParent(this);
	}

	public boolean isOwnerOnly() {
		return ownerOnly;
	}

	public void setOwnerOnly(boolean ownerOnly) {
		this.ownerOnly = ownerOnly;
	}

	public boolean isShowOnStatSheet() {
		return highPriority;
	}
	public void setShowOnStatSheet(boolean showOnStatSheet) {
		this.highPriority = showOnStatSheet;
	}
	public String getName() {
		return name;
	}
	public PropertyType withName(String name) {
		PropertyType tp=new PropertyType(type, propertySet, name, shortName, highPriority, ownerOnly, gmOnly,ArrayUtils.clone(subTypes));
		tp.setDefaultValue(defaultValue);
		return tp;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public boolean isGMOnly() {
		return gmOnly;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((subTypes == null) ? 0 : subTypes.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyType other = (PropertyType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (subTypes == null) {
			if (other.subTypes != null)
				return false;
		} else if (!subTypes.equals(other.subTypes))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public void setGMOnly(boolean gmOnly) {
		this.gmOnly = gmOnly;
	}

	public Object getDefaultValue() {
		//STRUCT default value is always an empty struct
		if(type==ValueType.STRUCT)
			return new Struct(this);
		return CopyUtil.copy(this.defaultValue);
	}

	public void setDefaultValue(Object defaultValue) {
		if(defaultValue!=null && !type.isInstance(defaultValue))
			throw new RuntimeException("Default type does not match given type");
		this.defaultValue = defaultValue;
	}

	public ValueType getType() {
		return this.type;
	}
	
	public PropertyType withType(ValueType type) {
		return new PropertyType(type, name, shortName, highPriority, ownerOnly, gmOnly);
	}
	
	private PropertyType[] createDefaultSubTypes(ValueType type) {
		if(type==ValueType.LIST)
			return new PropertyType[]{new PropertyType(ValueType.TEXT,propertySet, "listType","")};
		else if(type==ValueType.STRUCT)
			return new PropertyType[]{new PropertyType(ValueType.TEXT,propertySet, "name","")};
		else
			return new PropertyType[0];
	}

	public PropertyType[] getSubTypes() {
		return subTypes;
	}

	@Override
	public String toString() {
		return "TokenProperty [name=" + name + ", type=" + type + ", subTypes=" + Arrays.toString(subTypes) + "]";
	}

	public PropertyType getSubType() {
		if(subTypes.length!=1)
			throw new RuntimeException(toString()+" has no singular subType");
		return subTypes[0];
	}

	public PropertyType withSubTypes(List<PropertyType> subTypes) {
		for(PropertyType pt:subTypes)
			pt.setParent(this);
		return new PropertyType(type, propertySet, parent, name, shortName, highPriority, ownerOnly, gmOnly, subTypes.toArray(new PropertyType[subTypes.size()]));
	}

	private void setParent(PropertyType newParent) {
		this.parent=newParent;
	}

	public PropertyType getSubType(String key) {
		for(PropertyType tp:subTypes)
			if(tp.getName().equals(key))
				return tp;
		return null;
	}

	/**
	 * This method tries to convert the given object into the type it should have. If it already has the required
	 * type it returns object. If it is null or of a type that can not be converted it returns the default value
	 * of this type. Otherwise it will convert the value using the converters defined in the ValueTypes.
	 * @param object the object you want to convert
	 * @return the converted result
	 */
	public Object convert(Object object) {
		if(object==null)
			return null;
		else {
			Object ret=this.type.convert(this, object);
			if(ret==null)
				return this.getDefaultValue();
			else
				return ret;
		}
	}

	public String getPropertySet() {
		return propertySet;
	}
}
