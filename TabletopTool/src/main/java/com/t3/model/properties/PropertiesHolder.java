package com.t3.model.properties;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.t3.client.TabletopTool;
import com.t3.model.BaseModel;
import com.t3.model.campaign.Campaign;
import com.t3.xstreamversioned.SerializationVersion;

@SerializationVersion(0)
public class PropertiesHolder extends BaseModel {

	private String propertyType = Campaign.DEFAULT_TOKEN_PROPERTY_TYPE;
	private HashMap<TokenProperty, Object> propertyMap=new HashMap<>();
	
	public PropertiesHolder() {
	}
	
	public PropertiesHolder(PropertiesHolder ph) {
		if(ph.propertyType!=null)
			this.propertyType=ph.propertyType;
		this.propertyMap.clear();	
		this.propertyMap.putAll(ph.propertyMap);
	}
	
	/**
	 * @return all property names, all in lowercase.
	 */
	public Set<String> getPropertyNames() {
		HashSet<String> set=new HashSet<>(propertyMap.size());
		for(TokenProperty tp:propertyMap.keySet())
			set.add(tp.getName());
		return set;
	}
	
	public boolean containsValue(Object value) {
		return propertyMap.containsValue(value);
	}
	
	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		if(propertyType==null)
			throw new IllegalArgumentException("The property type of a token can't be null");
		this.propertyType = propertyType;
	}
	
	public void resetProperty(String key) {
		propertyMap.remove(getPropertyInfo(key));
	}

	public Object setProperty(String key, Object value) {
		if(value==null)
			return propertyMap.remove(getPropertyInfo(key));
		else if(!getPropertyInfo(key).getType().isInstance(value))
			throw new IllegalArgumentException("Property "+key+" must be of type "+getPropertyInfo(key).getType());
		else
			return propertyMap.put(getPropertyInfo(key), value);
	}
	
	public Object setProperty(TokenProperty tp, Object value) {
		if(value==null)
			return propertyMap.remove(tp);
		else if(!tp.getType().isInstance(value))
			throw new IllegalArgumentException("Property "+tp.getName()+" must be of type "+tp.getType());
		else
			return propertyMap.put(tp, value);
	}

	private TokenProperty getPropertyInfo(String key) {
		TokenProperty tp = TabletopTool.getCampaign().getTokenProperty(propertyType, key);
		if(tp==null)
			throw new IllegalArgumentException("There is no property "+key+" in "+propertyType);
		return tp;
	}
	
	/**
	 * This method returns the value of the given property or the default value if it is not set
	 * @param key the name of the property
	 * @return its value
	 */
	public Object getProperty(String key) {
		Object value = propertyMap.get(getPropertyInfo(key));
		if(value==null) //or is not set
			value=getPropertyInfo(key).getDefaultValue();
		return value;
	}
	
	public Object getProperty(TokenProperty tp) {
		Object value = propertyMap.get(tp);
		if(value==null) //or is not set
			value=tp.getDefaultValue();
		return value;
	}
	
	/**
	 * This method returns the value of the given property or null if it is not set
	 * @param key the name of the property
	 * @return its value
	 */
	public Object getPropertyOrNull(String key) {
		return propertyMap.get(getPropertyInfo(key));
	}
}