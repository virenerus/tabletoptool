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
package com.t3.model.tokenproperties.old;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javassist.expr.NewArray;

import org.apache.commons.lang3.ArrayUtils;

import com.jidesoft.grid.ContextSensitiveCellEditor;
import com.t3.clientserver.CopyUtil;
import com.t3.model.tokenproperties.instance.Struct;
import com.t3.model.tokenproperties.valuetypes.ValueType;
import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(0)
public class TokenProperty implements Serializable {
	private final String name;
	private String shortName;
	private boolean highPriority;
	private boolean ownerOnly;
	private boolean gmOnly;
	private Object defaultValue;
	private final ValueType type;
	private final TokenProperty[] subTypes;
	
	public TokenProperty() {
		// For serialization
		name="";
		type=ValueType.TEXT; //there must always be a type set
		subTypes=new TokenProperty[0];
	}

	public TokenProperty(String name) {
		this(ValueType.TEXT, name, null, false, false, false);
	}

	public TokenProperty(ValueType type, String name, String shortName) {
		this(type,name, shortName, false, false, false);
	}

	public TokenProperty(ValueType type, String name, boolean highPriority, boolean isOwnerOnly, boolean isGMOnly) {
		this(type,name, null, highPriority, isOwnerOnly, isGMOnly);
	}
	
	public TokenProperty(ValueType type, String name, String shortName, boolean highPriority, boolean isOwnerOnly, boolean isGMOnly) {
		this(type,name,shortName,highPriority,isOwnerOnly,isGMOnly,createDefaultSubTypes(type));
	}
	
	public TokenProperty(ValueType type, String name, String shortName, boolean highPriority, boolean isOwnerOnly, boolean isGMOnly, TokenProperty[] subTypes) {
		this.name = name;
		this.shortName = shortName;
		this.highPriority = highPriority;
		this.ownerOnly = isOwnerOnly;
		this.gmOnly = isGMOnly;
		this.type=type;
		this.defaultValue=type.getDefaultDefaultValue();
		this.subTypes=subTypes;
	}

	public TokenProperty(TokenProperty p) {
		name=p.name;
		shortName=p.shortName;
		highPriority=p.highPriority;
		ownerOnly=p.ownerOnly;
		gmOnly=p.gmOnly;
		defaultValue=p.defaultValue==null?p.type.getDefaultDefaultValue():p.defaultValue;
		type=p.type;
		subTypes=ArrayUtils.clone(p.getSubTypes());
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
	public TokenProperty withName(String name) {
		TokenProperty tp=new TokenProperty(type, name, shortName, highPriority, ownerOnly, gmOnly,ArrayUtils.clone(subTypes));
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
		TokenProperty other = (TokenProperty) obj;
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
	
	public TokenProperty withType(ValueType type) {
		return new TokenProperty(type, name, shortName, highPriority, ownerOnly, gmOnly);
	}
	
	private static TokenProperty[] createDefaultSubTypes(ValueType type) {
		if(type==ValueType.LIST)
			return new TokenProperty[]{new TokenProperty(ValueType.TEXT,"listType","")};
		else if(type==ValueType.STRUCT)
			return new TokenProperty[]{new TokenProperty(ValueType.TEXT,"name","")};
		else
			return new TokenProperty[0];
	}

	public TokenProperty[] getSubTypes() {
		return subTypes;
	}

	@Override
	public String toString() {
		return "TokenProperty [name=" + name + ", type=" + type + ", subTypes=" + Arrays.toString(subTypes) + "]";
	}

	public TokenProperty getSubType() {
		if(subTypes.length!=1)
			throw new RuntimeException(toString()+" has no singular subType");
		return subTypes[0];
	}

	public TokenProperty withSubTypes(List<TokenProperty> subTypes) {
		return new TokenProperty(type, name, shortName, highPriority, ownerOnly, gmOnly, subTypes.toArray(new TokenProperty[subTypes.size()]));
	}

	public TokenProperty getSubType(String key) {
		for(TokenProperty tp:subTypes)
			if(tp.getName().equals(key))
				return tp;
		return null;
	}
	
}
