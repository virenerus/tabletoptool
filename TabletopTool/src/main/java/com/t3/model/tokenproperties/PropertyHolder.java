package com.t3.model.tokenproperties;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import com.t3.client.TabletopTool;
import com.t3.model.BaseModel;
import com.t3.model.campaign.Campaign;
import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(0)
public class PropertyHolder extends BaseModel {

	private String propertySet = Campaign.DEFAULT_TOKEN_PROPERTY_SET;
	private HashMap<String, Object> propertyMap=new HashMap<>();
	
	public PropertyHolder() {
	}
	
	public PropertyHolder(PropertyHolder ph) {
		if(ph.propertySet!=null)
			this.propertySet=ph.propertySet;
		this.propertyMap.clear();	
		this.propertyMap.putAll(ph.propertyMap);
	}
	
	/**
	 * @return all property names, all in lowercase.
	 */
	public Set<String> getPropertyNames() {
		return Collections.unmodifiableSet(propertyMap.keySet());
	}
	
	public boolean containsValue(Object value) {
		return propertyMap.containsValue(value);
	}
	
	public String getPropertyType() {
		return propertySet;
	}

	public void setPropertyType(String propertySet) {
		if(propertySet==null)
			throw new IllegalArgumentException("The property type of a token can't be null");
		this.propertySet = propertySet;
	}
	
	public void resetProperty(String key) {
		propertyMap.remove(getPropertyInfo(key));
	}

	public Object setProperty(String key, Object value) {
		return setProperty(getPropertyInfo(key), value);
	}
	
	public Object setProperty(PropertyType tp, Object value) {
		if(value==null)
			return propertyMap.remove(tp.getName());
		else if(!tp.getType().isInstance(value))
			throw new IllegalArgumentException("Property "+tp.getName()+" must be of type "+tp.getType());
		else
			return propertyMap.put(tp.getName(), value);
	}

	protected @Nonnull PropertyType getPropertyInfo(String key) {
		PropertyType tp = TabletopTool.getCampaign().getTokenProperty(propertySet, key);
		if(tp==null)
			throw new IllegalArgumentException("There is no property "+key+" in "+propertySet);
		return tp;
	}
	
	/**
	 * This method returns the value of the given property or the default value if it is not set
	 * @param key the name of the property
	 * @return its value
	 */
	public Object getProperty(String key) {
		Object value = getPropertyInfo(key).convert(propertyMap.get(key));
		if(value==null) //or is not set
			value=getPropertyInfo(key).getDefaultValue();
		return value;
	}
	
	/**
	 * This method returns the value of the given property or the default value if it is not set
	 * @param tp the property
	 * @return its value
	 */
	public Object getProperty(PropertyType tp) {
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