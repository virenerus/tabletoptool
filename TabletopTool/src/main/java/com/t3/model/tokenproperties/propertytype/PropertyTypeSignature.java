package com.t3.model.tokenproperties.propertytype;

import javax.annotation.Nonnull;

import com.t3.model.tokenproperties.valuetypes.ValueType;

public class PropertyTypeSignature {
	protected final String name;
	protected final ValueType valueType;
	
	public PropertyTypeSignature(@Nonnull String name, @Nonnull ValueType valueType) {
		this.name=name;
		this.valueType=valueType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((valueType == null) ? 0 : valueType.hashCode());
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
		PropertyTypeSignature other = (PropertyTypeSignature) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (valueType != other.valueType)
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public ValueType getValueType() {
		return valueType;
	}
}
