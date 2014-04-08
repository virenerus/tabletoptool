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
package com.t3.client.ui.minisheet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Map.Entry;

import com.t3.swing.SwingUtil;

public class MiniSheet {

	private static final Font PROPERTIES_FONT = new Font("Helvetica", 0, 10);
	private static final Font TOKEN_NAME_FONT = new Font("Helvetica", Font.BOLD, 10);
	
	private BufferedImage sheetImage;
	private Rectangle portraitRegion;
	private Rectangle propertiesRegion;
	
	public MiniSheet(BufferedImage sheetImage, Rectangle portraitRegion, Rectangle propertiesRegion) {
		this.sheetImage = sheetImage;
		this.portraitRegion = portraitRegion;
		this.propertiesRegion = propertiesRegion;
	}
	
	public int getWidth() {
		return sheetImage.getWidth();
	}
	
	public int getHeight() {
		return sheetImage.getHeight();
	}
	
	/**
	 * Renders the card at 0, 0 (this means the caller must position the graphics position before calling) 
	 * @param propertyMap What to show, presumably a LinkedHashMap to preserve order
	 */
	public void render(Graphics2D g, BufferedImage portraitImage, Map<String, String> propertyMap) {

		Shape oldClip = g.getClip();

		// Sheet
		g.drawImage(sheetImage, 0, 0, null);
		
		// Portrait
		g.setClip(portraitRegion);
		Dimension size = new Dimension(portraitImage.getWidth(), portraitImage.getHeight());
		if (size.width > portraitRegion.width || size.height > portraitRegion.height) {
			SwingUtil.constrainTo(size, portraitRegion.width, portraitRegion.height);
		}
		g.drawImage(portraitImage, portraitRegion.x + (portraitRegion.width-size.width)/2, portraitRegion.y + (portraitRegion.height-size.height)/2, size.width, size.height, null);

		// Properties
		g.setFont(PROPERTIES_FONT);
		Object oldAAHint = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		FontMetrics fm = g.getFontMetrics();
		g.setClip(propertiesRegion);
		g.setColor(Color.black);
		int y = 3 + fm.getAscent() + propertiesRegion.y;
		for (Entry<String, String> entry : propertyMap.entrySet()) {
			
			g.drawString(entry.getKey() + ":", propertiesRegion.x + 5, y);
			g.drawString(entry.getValue(), propertiesRegion.x + 5 + propertiesRegion.width/2, y);
			
			y += fm.getHeight() + 3;
		}
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAAHint);
		g.setClip(oldClip);
	}
}
