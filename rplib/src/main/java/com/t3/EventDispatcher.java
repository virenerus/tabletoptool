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
package com.t3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Application level event dispatch.
 * @author trevor
 */
public class EventDispatcher {

	private Map<Enum, List<AppEventListener>> listenerMap = new HashMap<Enum, List<AppEventListener>>();
	
	public synchronized void registerEvents(Enum[] ids) {
		for (Enum e : ids) {
			registerEvent(e);
		}
	}
	public synchronized void registerEvent(Enum id) {
		if (listenerMap.containsKey(id)) {
			throw new IllegalArgumentException("Event '" + id + "' is already registered.");
		}
		
		listenerMap.put(id, new ArrayList<AppEventListener>());
	}
	
	public synchronized void addListener(Enum id, AppEventListener listener) {
		addListener(listener, id);
	}

	public synchronized void addListener(AppEventListener listener, Enum... ids) {
		for (Enum id : ids) {
			if (!listenerMap.containsKey(id)) {
				throw new IllegalArgumentException("Event '" + id + "' is not registered.");
			}
			
			List<AppEventListener> list = listenerMap.get(id);
			if (!list.contains(listener)) {
				list.add(listener);
			}
		}
	}
	
	public synchronized void fireEvent(Enum id) {
		fireEvent(id, null, null, null);
	}
	public synchronized void fireEvent(Enum id, Object source) {
		fireEvent(id, source, null, null);
	}
	public synchronized void fireEvent(Enum id, Object source, Object newValue) {
		fireEvent(id, source, null, newValue);
	}
	
	public synchronized void fireEvent(Enum id, Object source, Object oldValue, Object newValue) {
		if (!listenerMap.containsKey(id)) {
			throw new IllegalArgumentException("Event '" + id + "' is not registered.");
		}
		
		List<AppEventListener> list = listenerMap.get(id);
		for (AppEventListener listener : list) {
			listener.handleAppEvent(new AppEvent(id, source, oldValue, newValue));
		}
	}
	
}
