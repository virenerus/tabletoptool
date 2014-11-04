package com.t3.model.tokenproperties.instance;

import com.t3.model.tokenproperties.PropertyHolder;
import com.t3.model.tokenproperties.PropertyType;
import com.t3.model.tokenproperties.PropertyTypeReference;
import com.t3.model.tokenproperties.valuetypes.ValueType;
import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(1)
public class Struct extends PropertyHolder {
	
	private PropertyTypeReference	structPropertyTypeReference;

	public Struct(PropertyType propertyType) {
		if(propertyType==null || propertyType.getType()!= ValueType.STRUCT)
			throw new IllegalArgumentException("Token property of a Struct must be of type struct");
		this.structPropertyTypeReference=new PropertyTypeReference(propertyType);
	}
	
	@Override
	protected PropertyType getPropertyInfo(String key) {
		return structPropertyTypeReference.value().getSubType(key);
	}
}
