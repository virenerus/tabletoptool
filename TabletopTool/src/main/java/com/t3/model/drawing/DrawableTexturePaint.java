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

import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.Serializable;

import com.t3.MD5Key;
import com.t3.model.Asset;
import com.t3.model.AssetManager;
import com.t3.util.ImageManager;
import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(0)
public class DrawableTexturePaint extends DrawablePaint implements Serializable {
	private MD5Key assetId;
	private double scale;
	private transient BufferedImage image;
	private transient Asset asset;

	public DrawableTexturePaint() {
		// Serializable
	}

	public DrawableTexturePaint(MD5Key id) {
		this(id, 1);
	}

	public DrawableTexturePaint(MD5Key id, double scale) {
		assetId = id;
		this.scale = scale;
	}

	public DrawableTexturePaint(Asset asset) {
		this(asset != null ? asset.getId() : null);
		this.asset = asset;
	}

	public DrawableTexturePaint(Asset asset, double scale) {
		this(asset.getId(), 1);
		this.asset = asset;
	}

	@Override
	public Paint getPaint(int offsetX, int offsetY, double scale, ImageObserver... observers) {
		BufferedImage texture = null;
		if (image != null) {
			texture = image;
		} else {
			texture = ImageManager.getImage(assetId, observers);
			if (texture != ImageManager.TRANSFERING_IMAGE) {
				image = texture;
			}
		}
		return new TexturePaint(texture, new Rectangle2D.Double(offsetX, offsetY, texture.getWidth() * scale * this.scale, texture.getHeight() * scale * this.scale));
	}

	@Override
	public Paint getPaint(ImageObserver... observers) {
		return getPaint(0, 0, 1, observers);
	}

	public Asset getAsset() {
		if (asset == null && assetId != null) {
			asset = AssetManager.getAsset(assetId);
		}
		return asset;
	}

	public MD5Key getAssetId() {
		return assetId;
	}
}
