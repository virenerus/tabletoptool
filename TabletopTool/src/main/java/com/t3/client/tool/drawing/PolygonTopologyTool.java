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
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.t3.client.AppStyle;
import com.t3.client.TabletopTool;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.model.Zone;
import com.t3.model.drawing.Drawable;
import com.t3.model.drawing.DrawableColorPaint;
import com.t3.model.drawing.LineSegment;
import com.t3.model.drawing.Pen;
import com.t3.model.drawing.ShapeDrawable;
import com.t3.util.guidreference.ZoneReference;

/**
 * Tool for drawing freehand lines.
 */
public class PolygonTopologyTool extends LineTool implements MouseMotionListener {
	private static final long serialVersionUID = 3258132466219627316L;

	public PolygonTopologyTool() {
		try {
			setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("com/t3/client/image/tool/top-blue-poly.png"))));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	// Override abstracttool to prevent color palette from
	// showing up
	protected void attachTo(ZoneRenderer renderer) {
		super.attachTo(renderer);
		// Hide the drawable color palette
		TabletopTool.getFrame().hideControlPanel();
	}

	@Override
	public boolean isAvailable() {
		return TabletopTool.getPlayer().isGM();
	}

	@Override
	protected boolean drawMeasurement() {
		return false;
	}

	@Override
	public String getTooltip() {
		return "tool.polytopo.tooltip";
	}

	@Override
	public String getInstructions() {
		return "tool.poly.instructions";
	}

	@Override
	protected boolean isBackgroundFill(MouseEvent e) {
		return true;
	}

	@Override
	protected void completeDrawable(ZoneReference zone, Pen pen, Drawable drawable) {
		Area area = new Area();
	
		if (drawable instanceof LineSegment) {	
			LineSegment line = (LineSegment) drawable;
			BasicStroke stroke = new BasicStroke(pen.getThickness(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

			Path2D path = new Path2D.Double();
			Point lastPoint = null;
			
			for (Point point : line.getPoints()) {
				if (path.getCurrentPoint() == null)
				{
					path.moveTo(point.x, point.y);
				} else if (!point.equals(lastPoint)) {
					path.lineTo(point.x, point.y);
					lastPoint = point;
				}
			}
		
			area.add(new Area(stroke.createStrokedShape(path)));
		} else {
			area = new Area(((ShapeDrawable) drawable).getShape());
		}
		if (pen.isEraser()) {
			renderer.getZone().removeTopology(area);
			TabletopTool.serverCommand().removeTopology(renderer.getZone().getId(), area);
		} else {
			renderer.getZone().addTopology(area);
			TabletopTool.serverCommand().addTopology(renderer.getZone().getId(), area);
		}
		renderer.repaint();
	}

	@Override
	protected Pen getPen() {
		Pen pen = new Pen(TabletopTool.getFrame().getPen());
		pen.setEraser(isEraser());
		pen.setForegroundMode(Pen.MODE_TRANSPARENT);
		pen.setBackgroundMode(Pen.MODE_SOLID);
		pen.setThickness(1.0f);
		pen.setPaint(new DrawableColorPaint(isEraser() ? AppStyle.topologyRemoveColor : AppStyle.topologyAddColor));
		return pen;
	}

	@Override
	protected Polygon getPolygon(LineSegment line) {
		Polygon polygon = new Polygon();
		for (Point point : line.getPoints()) {
			polygon.addPoint(point.x, point.y);
		}
		return polygon;
	}

	@Override
	public void paintOverlay(ZoneRenderer renderer, Graphics2D g) {
		if (TabletopTool.getPlayer().isGM()) {
			Zone zone = renderer.getZone();
			Area topology = zone.getTopology();

			Graphics2D g2 = (Graphics2D) g.create();
			g2.translate(renderer.getViewOffsetX(), renderer.getViewOffsetY());
			g2.scale(renderer.getScale(), renderer.getScale());

			g2.setColor(AppStyle.topologyColor);
			g2.fill(topology);

			g2.dispose();
		}
		super.paintOverlay(renderer, g);
	}
}
