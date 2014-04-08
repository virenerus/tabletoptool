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

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

/**
 * Tool for drawing freehand lines.
 */
public class FreehandTool extends AbstractLineTool implements MouseMotionListener {
	private static final long serialVersionUID = 3904963036442998837L;

	public FreehandTool() {
		try {
			setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("com/t3/client/image/tool/draw-blue-freehndlines.png"))));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		// Don't ever show measurement drawing with freehand tool
		drawMeasurementDisabled = true;
	}

	@Override
	public String getTooltip() {
		return "tool.freehand.tooltip";
	}

	@Override
	public String getInstructions() {
		return "tool.freehand.instructions";
	}

	////
	// MOUSE LISTENER
	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			startLine(e);
			setIsEraser(isEraser(e));
		}
		super.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			stopLine(e);
		}
		super.mouseReleased(e);
	}

	////
	// MOUSE MOTION LISTENER
	@Override
	public void mouseDragged(java.awt.event.MouseEvent e) {
		addPoint(e);
		super.mouseDragged(e);
	}
}
