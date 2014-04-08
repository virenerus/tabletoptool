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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.SwingUtilities;

import com.t3.client.ScreenPoint;
import com.t3.client.tool.ToolHelper;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.model.ZonePoint;
import com.t3.model.drawing.Drawable;
import com.t3.model.drawing.DrawableColorPaint;
import com.t3.model.drawing.LineSegment;
import com.t3.model.drawing.Pen;
import com.t3.model.drawing.ShapeDrawable;

/**
 * Tool for drawing freehand lines.
 */
public abstract class AbstractLineTool extends AbstractDrawingTool {
	private int currentX;
	private int currentY;

	private LineSegment line;
	protected boolean drawMeasurementDisabled;

	protected int getCurrentX() {
		return currentX;
	}

	protected int getCurrentY() {
		return currentY;
	}

	protected LineSegment getLine() {
		return this.line;
	}

	protected void startLine(MouseEvent e) {
		line = new LineSegment(getPen().getThickness());
		addPoint(e);
	}

	protected Point addPoint(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			return null;
		}
		ZonePoint zp = getPoint(e);

		if (line == null)
			return null; // Escape has been pressed
		Point ret = new Point(zp.x, zp.y);

		line.getPoints().add(ret);
		currentX = zp.x;
		currentY = zp.y;

		renderer.repaint();
		return ret;
	}

	protected void removePoint(Point p) {
		if (line == null)
			return; // Escape has been pressed

		// Remove most recently added
		// TODO: optimize this
		Collections.reverse(line.getPoints());
		line.getPoints().remove(p);
		Collections.reverse(line.getPoints());
	}

	protected void stopLine(MouseEvent e) {
		if (line == null)
			return; // Escape has been pressed
		addPoint(e);

		Drawable drawable = line;
		if (isBackgroundFill(e) && line.getPoints().size() > 3) { // TODO: There's a bug where the last point is duplicated, hence 3 points
			drawable = new ShapeDrawable(getPolygon(line));
		}
		completeDrawable(renderer.getZone().getId(), getPen(), drawable);

		line = null;
		currentX = -1;
		currentY = -1;
	}

	protected Polygon getPolygon(LineSegment line) {
		Polygon polygon = new Polygon();
		for (Point point : line.getPoints()) {
			polygon.addPoint(point.x, point.y);
		}
		return polygon;
	}

	@Override
	public void paintOverlay(ZoneRenderer renderer, Graphics2D g) {
		if (line != null) {
			Pen pen = getPen();
			pen.setForegroundMode(Pen.MODE_SOLID);

			if (pen.isEraser()) {
				pen = new Pen(pen);
				pen.setEraser(false);
				pen.setPaint(new DrawableColorPaint(Color.white));
			}
			paintTransformed(g, renderer, line, pen);

			List<Point> pointList = line.getPoints();
			if (!drawMeasurementDisabled && pointList.size() > 1 && drawMeasurement()) {

				Point start = pointList.get(pointList.size() - 2);
				Point end = pointList.get(pointList.size() - 1);

				ScreenPoint sp = ScreenPoint.fromZonePoint(renderer, start.x, start.y);
				ScreenPoint ep = ScreenPoint.fromZonePoint(renderer, end.x, end.y);

				//ep.y -= 15;

				ToolHelper.drawMeasurement(renderer, g, sp, ep);
			}
		}
	}

	protected boolean drawMeasurement() {
		return true;
	}

	/**
	 * @see com.t3.client.ui.Tool#resetTool()
	 */
	@Override
	protected void resetTool() {
		if (line != null) {
			line = null;
			currentX = -1;
			currentY = -1;
			renderer.repaint();
		} else {
			super.resetTool();
		}
	}
}
