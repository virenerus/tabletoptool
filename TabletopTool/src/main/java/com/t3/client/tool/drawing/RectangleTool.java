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
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import com.t3.client.ScreenPoint;
import com.t3.client.tool.ToolHelper;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.model.ZonePoint;
import com.t3.model.drawing.DrawableColorPaint;
import com.t3.model.drawing.Pen;
import com.t3.model.drawing.ShapeDrawable;
import com.t3.util.guidreference.ZoneReference;

/**
 * @author drice
 * 
 */
public class RectangleTool extends AbstractDrawingTool implements MouseMotionListener {
	private static final long serialVersionUID = 3258413928311830323L;

	protected Rectangle rectangle;
	protected ZonePoint originPoint;

	public RectangleTool() {
		try {
			setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("com/t3/client/image/tool/draw-blue-box.png"))));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public String getInstructions() {
		return "tool.rect.instructions";
	}

	@Override
	public String getTooltip() {
		return "tool.rect.tooltip";
	}

	@Override
	public void paintOverlay(ZoneRenderer renderer, Graphics2D g) {
		if (rectangle != null) {
			Pen pen = getPen();
			if (pen.isEraser()) {
				pen = new Pen(pen);
				pen.setEraser(false);
				pen.setPaint(new DrawableColorPaint(Color.white));
				pen.setBackgroundPaint(new DrawableColorPaint(Color.white));
			}
			paintTransformed(g, renderer, new ShapeDrawable(rectangle, false), pen);
			ToolHelper.drawBoxedMeasurement(renderer, g, ScreenPoint.fromZonePoint(renderer, rectangle.x, rectangle.y),
					ScreenPoint.fromZonePoint(renderer, rectangle.x + rectangle.width, rectangle.y + rectangle.height));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		ZonePoint zp = getPoint(e);
		if (SwingUtilities.isLeftMouseButton(e)) {
			if (rectangle == null) {
				originPoint = zp;
				rectangle = createRect(originPoint, originPoint);
			} else {
				rectangle = createRect(originPoint, zp);

				if (rectangle.width == 0 || rectangle.height == 0) {
					rectangle = null;
					renderer.repaint();
					return;
				}
				// Draw Rectangle with initial point as Center
				if (e.isAltDown()) {
					if (zp.x > originPoint.x)
						rectangle.x -= rectangle.width;

					if (zp.y > originPoint.y)
						rectangle.y -= rectangle.height;

					rectangle.width *= 2;
					rectangle.height *= 2;
				}
//				System.out.println("Adding Rectangle to zone: " + rectangle);
				completeDrawable(new ZoneReference(renderer.getZone()), getPen(), new ShapeDrawable(rectangle, false));
				rectangle = null;
			}
			setIsEraser(isEraser(e));
		}
		super.mousePressed(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (rectangle == null) {
			super.mouseDragged(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);

		if (rectangle != null) {
			ZonePoint p = getPoint(e);
			rectangle = createRect(originPoint, p);

			// Draw Rectangle with initial point as Center
			if (e.isAltDown()) {
				if (p.x > originPoint.x)
					rectangle.x -= rectangle.width;

				if (p.y > originPoint.y)
					rectangle.y -= rectangle.height;

				rectangle.width *= 2;
				rectangle.height *= 2;
			}
			renderer.repaint();
		}
	}

	/**
	 * Stop drawing a rectangle and repaint the zone.
	 */
	@Override
	public void resetTool() {
		if (rectangle != null) {
			rectangle = null;
			renderer.repaint();
		} else {
			super.resetTool();
		}
	}
}
