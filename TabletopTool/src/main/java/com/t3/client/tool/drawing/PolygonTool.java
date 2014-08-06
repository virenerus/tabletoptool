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

import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.t3.model.drawing.Drawable;
import com.t3.model.drawing.LineSegment;
import com.t3.model.drawing.Pen;
import com.t3.model.drawing.ShapeDrawable;
import com.t3.util.guidreference.ZoneReference;

/**
 * Tool for drawing freehand lines.
 */
public class PolygonTool extends LineTool implements MouseMotionListener {
	private static final long serialVersionUID = 3258132466219627316L;

	public PolygonTool() {
		try {
			setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("com/t3/client/image/tool/draw-blue-strtlines.png"))));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public String getTooltip() {
		return "tool.poly.tooltip";
	}

	@Override
	public String getInstructions() {
		return "tool.poly.instructions";
	}

	@Override
	protected void completeDrawable(ZoneReference zone, Pen pen, Drawable drawable) {
		LineSegment line = (LineSegment) drawable;
		super.completeDrawable(zone, pen, new ShapeDrawable(getPolygon(line)));
	}

	@Override
	protected Polygon getPolygon(LineSegment line) {
		Polygon polygon = new Polygon();
		for (Point point : line.getPoints()) {
			polygon.addPoint(point.x, point.y);
		}
		return polygon;
	}
}
