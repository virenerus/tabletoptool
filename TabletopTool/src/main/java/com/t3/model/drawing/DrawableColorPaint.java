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
package com.t3.model.drawing;

import java.awt.Color;
import java.awt.Paint;
import java.awt.image.ImageObserver;
import java.io.Serializable;

import com.t3.xstreamversioned.SerializationVersion;

@SerializationVersion(0)
public class DrawableColorPaint extends DrawablePaint implements Serializable {
	private int color;
	private transient Color colorCache;

	public DrawableColorPaint() {
		// For deserialization
	}

	public DrawableColorPaint(Color color) {
		this.color = color.getRGB();
	}

	public int getColor() {
		return color;
	}

	@Override
	public Paint getPaint(ImageObserver... observers) {
		if (colorCache == null) {
			colorCache = new Color(color);
		}
		return colorCache;
	}

	@Override
	public Paint getPaint(int offsetX, int offsetY, double scale, ImageObserver... observer) {
		return getPaint();
	}
}
