package com.t3.model.tokenproperties.propertytype;

import java.util.Arrays;

import javax.annotation.Nonnull;

import com.t3.model.tokenproperties.valuetypes.ValueType;

public class AdvancedPropertyTypeSignature extends PropertyTypeSignature {

	private final PropertyTypeSignature[]	subSignatures;

	public AdvancedPropertyTypeSignature(@Nonnull String name, @Nonnull ValueType valueType, @Nonnull PropertyTypeSignature... subSignatures) {
		super(name, valueType);
		this.subSignatures=subSignatures;
	}

	public PropertyTypeSignature[] getSubSignatures() {
		return subSignatures;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(subSignatures);
		result = prime * result + ((valueType == null) ? 0 : valueType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdvancedPropertyTypeSignature other = (AdvancedPropertyTypeSignature) obj;
		if (!Arrays.equals(subSignatures, other.subSignatures))
			return false;
		return true;
	}

}
