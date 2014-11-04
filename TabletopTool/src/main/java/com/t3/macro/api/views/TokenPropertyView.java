/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3.macro.api.views;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.t3.client.TabletopTool;
import com.t3.model.Token;
import com.t3.model.tokenproperties.PropertyType;
import com.t3.util.StringUtil;

/**
 * This class makes the TokenView a bad MapView on the token properties. The 
 * advantage of this is that we can now access properties from groovy via beans
 * e.g.: token.HP="test" 
 * @author Virenerus
 *
 */
public abstract class TokenPropertyView implements Map<String, Object>{

	protected Token token;

	public TokenPropertyView(Token token) {
		this.token=token;
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(Object key) {
		if(key==null)
			return false;
		Object val = token.getPropertyOrNull(key.toString());
		if (val == null) {
			return false;
		}

		if (StringUtil.isEmpty(val.toString())) {
			return false;
		}

		return true;
	}

	@Override
	public boolean containsValue(Object value) {
		return token.containsValue(value);
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object get(Object key) {
		if(key==null)
			throw new NullPointerException();
		else
			return token.getProperty(key.toString());
	}

	@Override
	public boolean isEmpty() {
		return token.getPropertyNames().size()==0;
	}

	/**
	 * This is NOT backed by the properties.
	 */
	@Override
	public Set<String> keySet() {
		return new HashSet<String>(token.getPropertyNames());
	}

	@Override
	public Object put(String key, Object value) {
		List<PropertyType> propTypes = TabletopTool.getCampaign().getCampaignProperties().getTokenPropertyList(token.getPropertyType());
		for(PropertyType propType:propTypes) {
			if(propType.getName().equals(key)) {
				if(value!=null && !propType.getType().isInstance(value))
					throw new IllegalArgumentException("Given value is of type "+value.getClass()+" instead of "+propType.getType());
			}
		}
		Object old=token.setProperty(key, value);
		sendUpdate();
		return old;
	}

	private void sendUpdate() {
		TabletopTool.serverCommand().putToken(token.getZone().getId(), token);
		token.getZone().putToken(token);
	}

	@Override
	public void putAll(Map<? extends String, ?> m) {
		List<PropertyType> propTypes = TabletopTool.getCampaign().getCampaignProperties().getTokenPropertyList(token.getPropertyType());
		for(Entry<? extends String, ?> e:m.entrySet()) {
			for(PropertyType propType:propTypes) {
				if(propType.getName().equals(e.getKey())) {
					if(e.getValue()!=null && !propType.getType().isInstance(e.getValue()))
						throw new IllegalArgumentException("Given value is of type "+e.getValue().getClass()+" instead of "+propType.getType());
				}
			}
			token.setProperty(e.getKey(), e.getValue());
		}
		sendUpdate();
	}

	@Override
	public Object remove(Object key) {
		if(key==null)
			throw new NullPointerException();
		Object o=token.getProperty(key.toString());
		token.resetProperty(key.toString());
		sendUpdate();
		return o;
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Object> values() {
		throw new UnsupportedOperationException();
	}
	
}
