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

import com.t3.util.guidreference.ZoneReference;

/**
 * Represents a player pointer on the screen
 */
public class Pointer {

	public enum Type {
		ARROW,
		SPEECH_BUBBLE,
		THOUGHT_BUBBLE
	}
	
	private ZoneReference zone;
	private int x;
	private int y;
	private double direction; // 
	private Type type;
	
	public Pointer() {/* Hessian serializable */}
	
	public Pointer(Zone zone, int x, int y, double direction, Type type) {
		this.zone = new ZoneReference(zone);
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return x + "." + y + "-" + direction;
	}

	public Type getType() {
		return type != null ? type : Type.ARROW;
	}
	
	public ZoneReference getZoneReference() {
		return zone;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getDirection() {
		return direction;
	}
}
