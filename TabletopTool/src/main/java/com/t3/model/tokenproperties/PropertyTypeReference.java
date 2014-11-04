package com.t3.model.tokenproperties;

import com.t3.client.TabletopTool;
import com.t3.util.Reference;
import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(1)
public class PropertyTypeReference extends Reference<String, PropertyType> {

	private final String	propertySet;

	public PropertyTypeReference(String propertySet, String propertyName) {
		super(propertyName);
		this.propertySet=propertySet;
	}
	
	public PropertyTypeReference(PropertyType pt) {
		super(pt.getName(), pt);
		this.propertySet=pt.getPropertySet();
	}

	@Override
	protected PropertyType resolve(String propertyName) {
		return TabletopTool.getCampaign().getTokenProperty(propertySet, propertyName);
	}
}
