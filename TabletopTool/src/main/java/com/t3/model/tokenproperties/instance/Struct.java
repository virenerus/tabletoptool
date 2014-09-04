package com.t3.model.tokenproperties.instance;

import com.t3.model.tokenproperties.old.PropertyHolder;
import com.t3.model.tokenproperties.old.TokenProperty;
import com.t3.model.tokenproperties.valuetypes.ValueType;
import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(0)
public class Struct extends PropertyHolder {
	
	private TokenProperty	tokenProperty;

	public Struct(TokenProperty tokenProperty) {
		if(tokenProperty==null || tokenProperty.getType()!= ValueType.STRUCT)
			throw new IllegalArgumentException("Token property of a Struct must be of type struct");
		this.tokenProperty=tokenProperty;
	}
	
	@Override
	protected TokenProperty getPropertyInfo(String key) {
		return tokenProperty.getSubType(key);
	}
}
