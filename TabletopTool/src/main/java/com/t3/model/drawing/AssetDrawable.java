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

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Area;

import com.t3.MD5Key;
import com.t3.client.TabletopTool;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.GUID;
import com.t3.util.ImageManager;
import com.t3.xstreamversioned.SerializationVersion;

/**
 * This class allows an asset to be used as a drawable.
 * 
 * @author jgorrell
 * @version $Revision: 5945 $ $Date: 2013-06-02 21:05:50 +0200 (Sun, 02 Jun 2013) $ $Author: azhrei_fje $
 */
@SerializationVersion(0)
public class AssetDrawable extends AbstractDrawing {

	/**
	 * Id of the asset to be drawn
	 */
	private MD5Key assetId;

	/**
	 * The id of the zone where this drawable is painted.
	 */
	private GUID zoneId;

	/**
	 * The bounds of the asset drawn
	 */
	private Rectangle bounds;

	/**
	 * Build a drawable that draws an asset.
	 * 
	 * @param anAssetId The id of the asset to be drawn.
	 * @param theBounds The bounds used to paint the drawable.
	 * @param aZoneId The id of the zone that draws this drawable.
	 */
	public AssetDrawable(MD5Key anAssetId, Rectangle theBounds, GUID aZoneId) {
		assetId = anAssetId;
		bounds = theBounds;
		zoneId = aZoneId;
	}

	/**
	 * @see com.t3.model.drawing.Drawable#draw(java.awt.Graphics2D, com.t3.model.drawing.Pen)
	 */
	@Override
	public void draw(Graphics2D g) {
	}

	@Override
	protected void drawBackground(Graphics2D g) {
		ZoneRenderer renderer = TabletopTool.getFrame().getZoneRenderer(zoneId);
		Image image = ImageManager.getImage(assetId, renderer);
		g.drawImage(image, bounds.x, bounds.y, renderer);
	}

	/**
	 * @see com.t3.model.drawing.Drawable#getBounds()
	 */
	@Override
	public Rectangle getBounds() {
		return bounds;
	}
	
	@Override
	public Area getArea() {
		return null;
	}

}
