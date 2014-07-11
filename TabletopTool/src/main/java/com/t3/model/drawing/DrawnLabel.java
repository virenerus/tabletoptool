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

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;

import javax.swing.CellRendererPane;

import com.t3.client.swing.TwoToneTextPane;
import com.t3.client.tool.drawing.DrawnTextTool;
import com.t3.xstreamversioned.SerializationVersion;

/**
 * @author jgorrell
 * @version $Revision: 5945 $ $Date: 2013-06-02 21:05:50 +0200 (Sun, 02 Jun 2013) $ $Author: azhrei_fje $
 */
@SerializationVersion(0)
public class DrawnLabel extends AbstractDrawing {

	/**
	 * The bounds of the display rectangle
	 */
	private Rectangle bounds = new Rectangle();

	/**
	 * Text being painted.
	 */
	private String text;

	/**
	 * The font used to paint the text.
	 */
	private String font;

	/**
	 * The pane used to render the text
	 */
	private transient CellRendererPane renderer;

	/**
	 * The text pane used to paint the text.
	 */
	private transient TwoToneTextPane textPane;

	/**
	 * Create a new drawn label.
	 * 
	 * @param theText Text to be drawn
	 * @param theBounds The bounds containing the text.
	 * @param aFont The font used to draw the text as a string that can
	 * be passed to {@link Font#decode(java.lang.String)}.
	 */
	public DrawnLabel(String theText, Rectangle theBounds, String aFont) {
		text = theText;
		bounds = theBounds;
		font = aFont;
	}

	/**
	 * @see com.t3.model.drawing.Drawable#draw(java.awt.Graphics2D, com.t3.model.drawing.Pen)
	 */
	@Override
	public void draw(Graphics2D aG) {
		if (renderer == null) {
			renderer = new CellRendererPane();
			textPane = DrawnTextTool.createTextPane(bounds, null, font);
			textPane.setText(text);
		}
		renderer.paintComponent(aG, textPane, null, bounds);
	}

	@Override
	protected void drawBackground(Graphics2D g) {
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
		// TODO Auto-generated method stub
		return null;
	}
}
