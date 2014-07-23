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
package com.t3.model.properties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.t3.xstreamversioned.SerializationVersion;

@SerializationVersion(0)
public class TokenProperty implements Serializable {
	private String name;
	private String shortName;
	private boolean highPriority;
	private boolean ownerOnly;
	private boolean gmOnly;
	private Object defaultValue;
	private TokenPropertyType type;
	private List<TokenProperty> subTypes;

	public TokenProperty() {
		// For serialization
		type=TokenPropertyType.TEXT; //there must always be a type set
		subTypes=Collections.emptyList();
	}

	public TokenProperty(String name) {
		this(TokenPropertyType.TEXT, name, null, false, false, false);
	}

	public TokenProperty(TokenPropertyType type, String name, String shortName) {
		this(type,name, shortName, false, false, false);
	}

	public TokenProperty(TokenPropertyType type, String name, boolean highPriority, boolean isOwnerOnly, boolean isGMOnly) {
		this(type,name, null, highPriority, isOwnerOnly, isGMOnly);
	}
	public TokenProperty(TokenPropertyType type, String name, String shortName, boolean highPriority, boolean isOwnerOnly, boolean isGMOnly) {
		this.name = name;
		this.shortName = shortName;
		this.highPriority = highPriority;
		this.ownerOnly = isOwnerOnly;
		this.gmOnly = isGMOnly;
		this.type=type;
		this.defaultValue=type.getDefaultDefaultValue();
		this.subTypes=createDefaultSubTypes(type);
	}

	public TokenProperty(TokenProperty p) {
		name=p.name;
		shortName=p.shortName;
		highPriority=p.highPriority;
		ownerOnly=p.ownerOnly;
		gmOnly=p.gmOnly;
		defaultValue=p.defaultValue==null?p.type.getDefaultDefaultValue():p.defaultValue;
		type=p.type;
		subTypes=new ArrayList<>(p.getSubTypes());
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
	public void setName(String name) {
		this.name = name;
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

	public void setGMOnly(boolean gmOnly) {
		this.gmOnly = gmOnly;
	}

	public Object getDefaultValue()
	{
		return this.defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		if(defaultValue!=null && !type.isInstance(defaultValue))
			throw new RuntimeException("Default type does not match given type");
		this.defaultValue = defaultValue;
	}

	public TokenPropertyType getType() {
		return this.type;
	}
	
	public void setType(TokenPropertyType type) {
		this.type=type;
		this.defaultValue=type.getDefaultDefaultValue();
		this.subTypes=createDefaultSubTypes(type);
	}
	
	private static List<TokenProperty> createDefaultSubTypes(TokenPropertyType type) {
		if(type==TokenPropertyType.LIST)
			return Collections.singletonList(new TokenProperty(TokenPropertyType.TEXT,"listType",""));
		//TODO add struct type
		else
			return Collections.emptyList();
	}

	public List<TokenProperty> getSubTypes() {
		return subTypes;
	}
	
}
