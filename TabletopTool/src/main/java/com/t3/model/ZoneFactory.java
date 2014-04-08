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

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.t3.MD5Key;
import com.t3.client.AppPreferences;
import com.t3.client.AppUtil;
import com.t3.model.drawing.DrawableColorPaint;
import com.t3.model.drawing.DrawableTexturePaint;
import com.t3.model.grid.GridFactory;
import com.t3.util.ImageManager;

public class ZoneFactory {

	public static final String DEFAULT_MAP_NAME = "Grasslands";
	public static MD5Key defaultImageId;
	
	static {
    	// TODO: I really don't like this being hard wired this way, need to make it a preference or something
    	File grassImage = new File(AppUtil.getAppHome("resource/Default/Textures").getAbsolutePath() + "/Grass.png");
    	if (grassImage.exists()) {
    		try {
    			Asset asset = new Asset(DEFAULT_MAP_NAME, FileUtils.readFileToByteArray(grassImage));
    			defaultImageId = asset.getId();
    			
				// Make sure the image is loaded to avoid a flash screen when it becomes visible
				ImageManager.getImageAndWait(asset.getId());
    		} catch (IOException ioe) {
    			ioe.printStackTrace();
    		}
    		
    	}
	}
	
	public static Zone createZone() {
		
		Zone zone = new Zone();
		
		zone.setName(DEFAULT_MAP_NAME);
		zone.setBackgroundPaint(new DrawableTexturePaint(defaultImageId));
		zone.setFogPaint(new DrawableColorPaint(Color.black));

		zone.setVisible(AppPreferences.getNewMapsVisible());
		zone.setHasFog(AppPreferences.getNewMapsHaveFOW());
		zone.setUnitsPerCell(AppPreferences.getDefaultUnitsPerCell());
		zone.setTokenVisionDistance(AppPreferences.getDefaultVisionDistance());
		
		zone.setGrid(GridFactory.createGrid(AppPreferences.getDefaultGridType(),AppPreferences.getFaceEdge(), AppPreferences.getFaceVertex()));
		zone.setGridColor(AppPreferences.getDefaultGridColor().getRGB());
		zone.getGrid().setSize(AppPreferences.getDefaultGridSize());
		zone.getGrid().setOffset(0, 0);

	    return zone;
	}

}
