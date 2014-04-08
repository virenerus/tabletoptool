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
package com.t3.client.ui.zone;

import java.awt.geom.Area;

import com.t3.model.LightSource;
import com.t3.model.drawing.DrawablePaint;

public class DrawableLight {

	private DrawablePaint paint;
	private Area area;
	private LightSource.Type type;
	
	public DrawableLight(LightSource.Type type, DrawablePaint paint, Area area) {
		super();
		this.paint = paint;
		this.area = area;
		this.type = type;
	}
	
	public DrawablePaint getPaint() {
		return paint;
	}
	
	public Area getArea() {
		return area;
	}

	public LightSource.Type getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "DrawableLight[" + area.getBounds() + ", " + paint.getClass().getName() + "]";
	}
	
}
