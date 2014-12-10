package com.t3.model.tokenproperties.instance;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.t3.model.tokenproperties.PropertyType;
import com.t3.model.tokenproperties.PropertyTypeReference;
import com.t3.model.tokenproperties.ValueType;
import com.t3.model.tokenproperties.propertyholder.BasicPropertyHolder;
import com.t3.model.tokenproperties.propertyholder.PropertyHolder;
import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(1)
public class Struct implements PropertyHolder {
	
	private PropertyTypeReference	structPropertyTypeReference;
	private final Map<String, Object> propertyMap;

	public Struct(PropertyType propertyType) {
		this(propertyType, Collections.<String, Object>emptyMap());
	}
	
	public Struct(PropertyType propertyType, Struct struct) {
		this(propertyType, struct.propertyMap);
	}
	
	public Struct(PropertyType propertyType, Map<String, Object> propertyMap) {
		if(propertyType==null || propertyType.getType()!= ValueType.STRUCT)
			throw new IllegalArgumentException("Token property of a Struct must be of type struct");
		this.structPropertyTypeReference=new PropertyTypeReference(propertyType);
		this.propertyMap=Collections.unmodifiableMap(propertyMap);
	}

	protected PropertyType getPropertyInfo(String key) {
		return structPropertyTypeReference.value().getSubType(key);
	}
	
	public PropertyType getPropertyType() {
		return structPropertyTypeReference.value();
	}

	@Override
	public Set<String> getPropertyNames() {
		return Collections.unmodifiableSet(propertyMap.keySet());
	}

	@Override
	public boolean containsValue(Object value) {
		return propertyMap.containsValue(value);
	}

	@Override
	public String getPropertySet() {
		return structPropertyTypeReference.value().getPropertySet();
	}

	@Override
	public Object getProperty(String key) {
		Object value = getPropertyInfo(key).convert(propertyMap.get(key));
		if(value==null) //or is not set
			value=getPropertyInfo(key).getDefaultValue();
		return value;
	}

	@Override
	public Object getProperty(PropertyType tp) {
		Object value = tp.convert(propertyMap.get(tp.getName()));
		if(value==null) //or is not set
			value=tp.getDefaultValue();
		return value;
	}

	@Override
	public Object getPropertyOrNull(String key) {
		PropertyType pt=getPropertyInfo(key);
		return pt.convert(propertyMap.get(pt));
	}
	
	public Struct withProperty(String key, Object value) {
		if(value==null)
			return without(key);
		else if(!getPropertyInfo(key).getType().isInstance(value))
			throw new IllegalArgumentException("Property "+key+" must be of type "+getPropertyInfo(key).getType());
		else {
			HashMap<String, Object> map=new HashMap<>(propertyMap);
			map.put(key, value);
			return new Struct(structPropertyTypeReference.value(), map);
		}
	}

	private Struct without(String key) {
		HashMap<String, Object> map=new HashMap<>(propertyMap);
		map.remove(key);
		return new Struct(structPropertyTypeReference.value(), map);
	}
}
