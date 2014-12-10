package com.t3.model.tokenproperties.propertyholder;

import java.util.Set;

import com.t3.model.tokenproperties.PropertyType;

public interface PropertyHolder {

	/**
	 * @return all property names, all in lowercase.
	 */
	public abstract Set<String> getPropertyNames();

	public abstract boolean containsValue(Object value);

	public abstract String getPropertySet();

	/**
	 * This method returns the value of the given property or the default value if it is not set
	 * @param key the name of the property
	 * @return its value
	 */
	public abstract Object getProperty(String key);

	/**
	 * This method returns the value of the given property or the default value if it is not set
	 * @param tp the property
	 * @return its value
	 */
	public abstract Object getProperty(PropertyType tp);

	/**
	 * This method returns the value of the given property or null if it is not set
	 * @param key the name of the property
	 * @return its value
	 */
	public abstract Object getPropertyOrNull(String key);

}