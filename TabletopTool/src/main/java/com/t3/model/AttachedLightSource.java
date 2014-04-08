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

public class AttachedLightSource {

	private GUID lightSourceId;
	private Direction direction;

	public AttachedLightSource() {
		// for serialization
	}
	
	public AttachedLightSource(LightSource source, Direction direction) {
		lightSourceId = source.getId();
		this.direction = direction;
	}
	
	public Direction getDirection() {
		return direction != null ? direction : Direction.CENTER;
	}

	public GUID getLightSourceId() {
		return lightSourceId;
	}
}
