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

public class AppEvent {

	private Enum<?> id;
	private Object source;
	private Object oldValue;
	private Object newValue;

	public AppEvent(Enum<?> id, Object source, Object oldValue, Object newValue) {
		this.id = id;
		this.source = source;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	public Enum<?> getId() {
		return id;
	}
	public Object getSource() {
		return source;
	}
	public Object getOldValue() {
		return oldValue;
	}
	public Object getNewValue() {
		return newValue;
	}
}
