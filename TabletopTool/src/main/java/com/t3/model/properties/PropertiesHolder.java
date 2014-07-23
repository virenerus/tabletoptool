package com.t3.model.properties;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

import com.t3.client.TabletopTool;
import com.t3.model.BaseModel;
import com.t3.model.campaign.Campaign;
import com.t3.xstreamversioned.SerializationVersion;

@SerializationVersion(0)
public class PropertiesHolder extends BaseModel {

	private String propertyType = Campaign.DEFAULT_TOKEN_PROPERTY_TYPE;
	private CaseInsensitiveMap<String,Object> propertyMap=new CaseInsensitiveMap<>();
	private transient CaseInsensitiveMap<String, TokenProperty> propertyInfo=new CaseInsensitiveMap<>();
	
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
		return propertyMap.keySet();
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
		this.propertyInfo.clear();
	}
	
	public void resetProperty(String key) {
		propertyMap.remove(key);
	}

	public Object setProperty(String key, Object value) {
		if(!getPropertyInfo(key).getType().isInstance(value))
			throw new IllegalArgumentException("Property "+key+" must be of type "+getPropertyInfo(key).getType());
		return propertyMap.put(key, value);
	}

	private TokenProperty getPropertyInfo(String key) {
		TokenProperty tp=propertyInfo.get(key);
		if(tp==null) {
			tp=TabletopTool.getCampaign().getTokenProperty(propertyType, key);
			if(tp==null)
				throw new IllegalArgumentException("There is no property "+key+" in "+propertyType);
			propertyInfo.put(key, tp);
		}
		return tp;
	}
	
	/**
	 * This method returns the value of the given property or the default value if it is not set
	 * @param key the name of the property
	 * @return its value
	 */
	public Object getProperty(String key) {
		Object value = propertyMap.get(key);
		if(value==null)
			value=getPropertyInfo(key).getDefaultValue();
		return value;
	}
	
	/**
	 * This method returns the value of the given property or null if it is not set
	 * @param key the name of the property
	 * @return its value
	 */
	public Object getPropertyOrNull(String key) {
		return propertyMap.get(key);
	}
}