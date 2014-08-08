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
package com.t3.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(0)
public class BaseModel {

	// Transient so that it isn't transfered over the wire
	private transient List<ModelChangeListener> listenerList = new CopyOnWriteArrayList<ModelChangeListener>();

	
	public void addModelChangeListener(ModelChangeListener listener) {
		listenerList.add(listener);
	}

	public void removeModelChangeListener(ModelChangeListener listener) {
		listenerList.remove(listener);
	}

	public void fireModelChangeEvent(ModelChangeEvent event) {

		for (ModelChangeListener listener : listenerList) {
			listener.modelChanged(event);
		}
	}
	
	protected Object readResolve() {
		listenerList = new CopyOnWriteArrayList<ModelChangeListener>();
		return this;
	}
}
