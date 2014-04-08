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

public class ModelChangeEvent {
	public Object model;
	public Object eventType;
	public Object arg;

	public ModelChangeEvent(Object model, Object eventType) {
		this(model, eventType, null);
	}

	public ModelChangeEvent(Object model, Object eventType, Object arg) {
		this.model = model;
		this.eventType = eventType;
		this.arg = arg;
	}

	public Object getModel() {
		return model;
	}

	public Object getArg() {
		return arg;
	}

	public Object getEvent() {
		return eventType;
	}

	@Override
	public String toString() {
		return "ModelChangeEvent: " + model + " - " + eventType + " - " + arg;
	}
}
