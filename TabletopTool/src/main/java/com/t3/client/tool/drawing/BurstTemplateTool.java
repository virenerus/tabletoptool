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
package com.t3.client.tool.drawing;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.t3.client.ScreenPoint;
import com.t3.client.TabletopTool;
import com.t3.model.CellPoint;
import com.t3.model.ZonePoint;
import com.t3.model.drawing.AbstractTemplate;
import com.t3.model.drawing.BurstTemplate;

/**
 * Draw a template for an effect with a burst. Make the template show the squares that are effected, not just draw a
 * circle. Let the player choose the base hex with the mouse and then click again to set the radius. The control key can
 * be used to move the base hex.
 * 
 * @author jgorrell
 * @version $Revision: $ $Date: $ $Author: $
 */
public class BurstTemplateTool extends RadiusTemplateTool {
	/*---------------------------------------------------------------------------------------------
	 * Instance Variables
	 *-------------------------------------------------------------------------------------------*/

	/*---------------------------------------------------------------------------------------------
	 * Constructors
	 *-------------------------------------------------------------------------------------------*/

	/**
	 * Set the icon for the base tool.
	 */
	public BurstTemplateTool() {
		try {
			setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("com/t3/client/image/tool/temp-blue-burst.png"))));
		} catch (IOException ioe) {
			TabletopTool.showError("Cannot read image 'temp-blue-burst.png'", ioe);
		} // endtry
	}

	/*---------------------------------------------------------------------------------------------
	 * Overridden RadiusTemplateTool methods
	 *-------------------------------------------------------------------------------------------*/

	/**
	 * @see com.t3.client.tool.drawing.RadiusTemplateTool#createBaseTemplate()
	 */
	@Override
	protected AbstractTemplate createBaseTemplate() {
		return new BurstTemplate();
	}

	/**
	 * This seems to be redundant and doesn't account for moving the mouse pointer to the nearest vertex, only
	 * truncating to the nearest top/left vertex.
	 * 
	 * @see com.t3.client.tool.drawing.RadiusTemplateTool#getCellAtMouse(java.awt.event.MouseEvent)
	 */
	@Override
	protected ZonePoint getCellAtMouse(MouseEvent e) {
		ZonePoint mouse = new ScreenPoint(e.getX(), e.getY()).convertToZone(renderer);
		CellPoint cp = renderer.getZone().getGrid().convert(mouse);
		return renderer.getZone().getGrid().convert(cp);
	}

	/**
	 * @see com.t3.client.tool.drawing.RadiusTemplateTool#paintCursor(java.awt.Graphics2D, java.awt.Paint,
	 *      float, com.t3.model.ZonePoint)
	 */
	@Override
	protected void paintCursor(Graphics2D g, Paint paint, float thickness, ZonePoint vertex) {
		g.setPaint(paint);
		g.setStroke(new BasicStroke(thickness));
		int grid = renderer.getZone().getGrid().getSize();
		g.drawRect(vertex.x, vertex.y, grid, grid);
	}

	/**
	 * @see com.t3.client.tool.drawing.RadiusTemplateTool#getRadiusAtMouse(java.awt.event.MouseEvent)
	 */
	@Override
	protected int getRadiusAtMouse(MouseEvent e) {
		return super.getRadiusAtMouse(e);
	}

	/**
	 * @see com.t3.client.ui.Tool#getTooltip()
	 */
	@Override
	public String getTooltip() {
		return "tool.bursttemplate.tooltip";
	}

	/**
	 * @see com.t3.client.ui.Tool#getInstructions()
	 */
	@Override
	public String getInstructions() {
		return "tool.bursttemplate.instructions";
	}
}
