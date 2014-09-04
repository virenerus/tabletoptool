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

import com.t3.client.ui.AssetPaint;
import com.t3.model.Asset;
import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(0)
public abstract class DrawablePaint implements Serializable {
	public abstract Paint getPaint(ImageObserver... observers);

	public abstract Paint getPaint(int offsetX, int offsetY, double scale, ImageObserver... observers);

	public static DrawablePaint convertPaint(Paint paint) {
		if (paint == null) {
			return null;
		}
		if (paint instanceof Color) {
			return new DrawableColorPaint((Color) paint);
		}
		if (paint instanceof AssetPaint) {
			Asset asset = ((AssetPaint) paint).getAsset();
			return new DrawableTexturePaint(asset);
		}
		throw new IllegalArgumentException("Invalid type of paint: " + paint.getClass().getName());
	}
}
