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

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import com.t3.client.ScreenPoint;
import com.t3.client.T3Util;
import com.t3.client.TabletopTool;
import com.t3.client.tool.DefaultTool;
import com.t3.client.tool.LayerSelectionDialog;
import com.t3.client.tool.LayerSelectionDialog.LayerSelectionListener;
import com.t3.client.ui.zone.ZoneOverlay;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.guid.GUID;
import com.t3.model.Zone;
import com.t3.model.Zone.Layer;
import com.t3.model.ZonePoint;
import com.t3.model.drawing.Drawable;
import com.t3.model.drawing.Pen;
import com.t3.swing.ColorPicker;
import com.t3.swing.SwingUtil;
import com.t3.util.guidreference.ZoneReference;

/**
 * Tool for drawing freehand lines.
 */
public abstract class AbstractDrawingTool extends DefaultTool implements ZoneOverlay {
	private static final long serialVersionUID = 9121558405484986225L;

	private boolean isEraser;
	private boolean isSnapToGridSelected;
	private boolean isEraseSelected;
	private static LayerSelectionDialog layerSelectionDialog;

	private static Zone.Layer selectedLayer = Zone.Layer.TOKEN;

	static {
		layerSelectionDialog = new LayerSelectionDialog(new Zone.Layer[] { Zone.Layer.TOKEN, Zone.Layer.GM, Zone.Layer.OBJECT, Zone.Layer.BACKGROUND }, new LayerSelectionListener() {
			@Override
			public void layerSelected(Layer layer) {
				selectedLayer = layer;
			}
		});
	}

	protected Rectangle createRect(ZonePoint originPoint, ZonePoint newPoint) {
		int x = Math.min(originPoint.x, newPoint.x);
		int y = Math.min(originPoint.y, newPoint.y);

		int w = Math.max(originPoint.x, newPoint.x) - x;
		int h = Math.max(originPoint.y, newPoint.y) - y;

		return new Rectangle(x, y, w, h);
	}

	protected AffineTransform getPaintTransform(ZoneRenderer renderer) {
		AffineTransform transform = new AffineTransform();
		transform.translate(renderer.getViewOffsetX(), renderer.getViewOffsetY());
		transform.scale(renderer.getScale(), renderer.getScale());
		return transform;
	}

	protected void paintTransformed(Graphics2D g, ZoneRenderer renderer, Drawable drawing, Pen pen) {
		AffineTransform transform = getPaintTransform(renderer);
		AffineTransform oldTransform = g.getTransform();
		g.transform(transform);
		drawing.draw(g, pen);
		g.setTransform(oldTransform);
	}

	@Override
	protected void attachTo(ZoneRenderer renderer) {
		if (TabletopTool.getPlayer().isGM()) {
			TabletopTool.getFrame().showControlPanel(TabletopTool.getFrame().getColorPicker(), layerSelectionDialog);
		} else {
			TabletopTool.getFrame().showControlPanel(TabletopTool.getFrame().getColorPicker());
		}
		renderer.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

		TabletopTool.getFrame().getColorPicker().setSnapSelected(isSnapToGridSelected);
		TabletopTool.getFrame().getColorPicker().setEraseSelected(isEraseSelected);
		super.attachTo(renderer);
	}

	@Override
	protected void detachFrom(ZoneRenderer renderer) {
		TabletopTool.getFrame().hideControlPanel();
		renderer.setCursor(Cursor.getDefaultCursor());

		isSnapToGridSelected = TabletopTool.getFrame().getColorPicker().isSnapSelected();
		isEraseSelected = TabletopTool.getFrame().getColorPicker().isEraseSelected();

		super.detachFrom(renderer);
	}

	protected void setIsEraser(boolean eraser) {
		isEraser = eraser;
	}

	protected boolean isEraser() {
		return isEraser;
	}

	protected boolean isBackgroundFill(MouseEvent e) {
		boolean defaultValue = TabletopTool.getFrame().getColorPicker().isFillBackgroundSelected();
		return defaultValue;
	}

	protected boolean isEraser(MouseEvent e) {
		boolean defaultValue = TabletopTool.getFrame().getColorPicker().isEraseSelected();
		if (SwingUtil.isShiftDown(e)) {
			// Invert from the color panel
			defaultValue = !defaultValue;
		}
		return defaultValue;
	}

	protected boolean isSnapToGrid(MouseEvent e) {
		boolean defaultValue = TabletopTool.getFrame().getColorPicker().isSnapSelected();
		if (SwingUtil.isControlDown(e)) {
			// Invert from the color panel
			defaultValue = !defaultValue;
		}
		return defaultValue;
	}

	protected Pen getPen() {
		Pen pen = new Pen(TabletopTool.getFrame().getPen());
		pen.setEraser(isEraser);

		ColorPicker picker = TabletopTool.getFrame().getColorPicker();
		if (picker.isFillForegroundSelected()) {
			pen.setForegroundMode(Pen.MODE_SOLID);
		} else {
			pen.setForegroundMode(Pen.MODE_TRANSPARENT);
		}
		if (picker.isFillBackgroundSelected()) {
			pen.setBackgroundMode(Pen.MODE_SOLID);
		} else {
			pen.setBackgroundMode(Pen.MODE_TRANSPARENT);
		}
		return pen;
	}

	protected ZonePoint getPoint(MouseEvent e) {
		ScreenPoint sp = new ScreenPoint(e.getX(), e.getY());
		ZonePoint zp = sp.convertToZoneRnd(renderer);
		if (isSnapToGrid(e)) {
			zp = renderer.getZone().getNearestVertex(zp);
			sp = ScreenPoint.fromZonePoint(renderer, zp);
		}
		return zp;
	}

	@Override
	public abstract void paintOverlay(ZoneRenderer renderer, Graphics2D g);

	/**
	 * Render a drawable on a zone. This method consolidates all of the calls to the server in one place so that it is
	 * easier to keep them in sync.
	 * 
	 * @param zone
	 *            the zone where the <code>drawable</code> is being drawn.
	 * @param pen
	 *            The pen used to draw.
	 * @param drawable
	 *            What is being drawn.
	 */
	protected void completeDrawable(ZoneReference zone, Pen pen, Drawable drawable) {
		if (!hasPaint(pen)) {
			return;
		}
		drawable.setLayer(selectedLayer);

		// Send new textures
		T3Util.uploadTexture(pen.getPaint());
		T3Util.uploadTexture(pen.getBackgroundPaint());

		// Tell the local/server to render the drawable.
		TabletopTool.serverCommand().draw(zone.getId(), pen, drawable);

		// Allow it to be undone
		Zone z = TabletopTool.getFrame().getCurrentZoneRenderer().getZone();
		z.addDrawable(pen, drawable);
	}

	private boolean hasPaint(Pen pen) {
		return pen.getForegroundMode() != Pen.MODE_TRANSPARENT || pen.getBackgroundMode() != Pen.MODE_TRANSPARENT;
	}
}
