package com.t3.model.tokenproperties.propertyholder;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import com.t3.client.TabletopTool;
import com.t3.model.BaseModel;
import com.t3.model.campaign.Campaign;
import com.t3.model.tokenproperties.PropertyType;
import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(0)
public class BasicPropertyHolder extends BaseModel implements PropertyHolder {

	private String propertySet = Campaign.DEFAULT_TOKEN_PROPERTY_SET;
	private HashMap<String, Object> propertyMap=new HashMap<>();
	
	public BasicPropertyHolder() {
	}
	
	public BasicPropertyHolder(BasicPropertyHolder ph) {
		if(ph.propertySet!=null)
			this.propertySet=ph.propertySet;
		this.propertyMap.clear();	
		this.propertyMap.putAll(ph.propertyMap);
	}
	
	/* (non-Javadoc)
	 * @see com.t3.model.tokenproperties.propertyholder.PropertyHolder#getPropertyNames()
	 */
	@Override
	public Set<String> getPropertyNames() {
		return Collections.unmodifiableSet(propertyMap.keySet());
	}
	
	/* (non-Javadoc)
	 * @see com.t3.model.tokenproperties.propertyholder.PropertyHolder#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		return propertyMap.containsValue(value);
	}
	
	/* (non-Javadoc)
	 * @see com.t3.model.tokenproperties.propertyholder.PropertyHolder#getPropertySet()
	 */
	@Override
	public String getPropertySet() {
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
			return tp.convert(propertyMap.put(tp.getName(), value));
	}

	protected @Nonnull PropertyType getPropertyInfo(String key) {
		PropertyType tp = TabletopTool.getCampaign().getTokenProperty(propertySet, key);
		if(tp==null)
			throw new IllegalArgumentException("There is no property "+key+" in "+propertySet);
		return tp;
	}
	
	/* (non-Javadoc)
	 * @see com.t3.model.tokenproperties.propertyholder.PropertyHolder#getProperty(java.lang.String)
	 */
	@Override
	public Object getProperty(String key) {
		Object value = getPropertyInfo(key).convert(propertyMap.get(key));
		if(value==null) //or is not set
			value=getPropertyInfo(key).getDefaultValue();
		return value;
	}
	
	/* (non-Javadoc)
	 * @see com.t3.model.tokenproperties.propertyholder.PropertyHolder#getProperty(com.t3.model.tokenproperties.PropertyType)
	 */
	@Override
	public Object getProperty(PropertyType tp) {
		Object value = tp.convert(propertyMap.get(tp.getName()));
		if(value==null) //or is not set
			value=tp.getDefaultValue();
		return value;
	}
	
	/* (non-Javadoc)
	 * @see com.t3.model.tokenproperties.propertyholder.PropertyHolder#getPropertyOrNull(java.lang.String)
	 */
	@Override
	public Object getPropertyOrNull(String key) {
		PropertyType pt=getPropertyInfo(key);
		return pt.convert(propertyMap.get(pt));
	}
}