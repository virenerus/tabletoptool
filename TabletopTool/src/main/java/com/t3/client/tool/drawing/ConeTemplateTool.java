/*
 *  This software copyright by various authors including the RPTools.net
 *  development team, and licensed under the LGPL Version 3 or, at your
 *  option, any later version.
 *
 *  Portions of this software were originally covered under the Apache
 *  Software License, Version 1.1 or Version 2.0.
 *
 *  See the file LICENSE elsewhere in this distribution for license details.
 */

package com.t3.client.tool.drawing;

import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.t3.client.ScreenPoint;
import com.t3.client.TabletopTool;
import com.t3.model.ZonePoint;
import com.t3.model.drawing.AbstractTemplate;
import com.t3.model.drawing.ConeTemplate;
import com.t3.model.drawing.RadiusTemplate;

/**
 * Draw a template for an effect with a cone area. Make the template show the squares that are effected, not just draw a
 * circle. Let the player choose the vertex with the mouse and use the wheel to set the radius. Use control and mouse
 * position to direct the cone. This allows the user to move the entire template where it is to be used before placing
 * it which is very important when casting a spell.
 * 
 * @author jgorrell
 * @version $Revision: 5945 $ $Date: 2013-06-02 21:05:50 +0200 (Sun, 02 Jun 2013) $ $Author: azhrei_fje $
 */
public class ConeTemplateTool extends RadiusTemplateTool {
	/*---------------------------------------------------------------------------------------------
	 * Constructor 
	 *-------------------------------------------------------------------------------------------*/

	/**
	 * Add the icon to the toggle button.
	 */
	public ConeTemplateTool() {
		try {
			setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("com/t3/client/image/tool/temp-blue-cone.png"))));
		} catch (IOException ioe) {
			TabletopTool.showError("Cannot read image 'temp-blue-cone.png'", ioe);
		} // endtry
	}

	/*---------------------------------------------------------------------------------------------
	 * Overidden RadiusTemplateTool Methods
	 *-------------------------------------------------------------------------------------------*/

	/**
	 * @see com.t3.client.tool.drawing.RadiusTemplateTool#createBaseTemplate()
	 */
	@Override
	protected AbstractTemplate createBaseTemplate() {
		return new ConeTemplate();
	}

	/**
	 * @see com.t3.client.ui.Tool#getTooltip()
	 */
	@Override
	public String getTooltip() {
		return "tool.cone.tooltip";
	}

	/**
	 * @see com.t3.client.ui.Tool#getInstructions()
	 */
	@Override
	public String getInstructions() {
		return "tool.cone.instructions";
	}

	/**
	 * @see com.t3.client.tool.drawing.RadiusTemplateTool#setRadiusFromAnchor(java.awt.event.MouseEvent)
	 */
	@Override
	protected void setRadiusFromAnchor(MouseEvent e) {
		super.setRadiusFromAnchor(e);

		// Set the direction based on the mouse location too
		ZonePoint vertex = template.getVertex();
		ZonePoint mouse = new ScreenPoint(e.getX(), e.getY()).convertToZone(renderer);
		((ConeTemplate) template).setDirection(RadiusTemplate.Direction.findDirection(mouse.x, mouse.y, vertex.x, vertex.y));
	}
}