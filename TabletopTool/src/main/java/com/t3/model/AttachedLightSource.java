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

import com.t3.util.guidreference.LightSourceReference;
import com.t3.util.guidreference.NullHelper;
import com.t3.xstreamversioned.SerializationVersion;

@SerializationVersion(0)
public class AttachedLightSource {

	private LightSourceReference lightSource;
	private Direction direction;

	public AttachedLightSource() {
		// for serialization
	}
	
	public AttachedLightSource(LightSource source, Direction direction) {
		lightSource = new LightSourceReference(source);
		this.direction = direction;
	}
	
	public Direction getDirection() {
		return direction != null ? direction : Direction.CENTER;
	}

	public LightSourceReference getLightSourceReference() {
		return lightSource;
	}
	
	public LightSource getLightSource() {
		return NullHelper.value(lightSource);
	}
}
