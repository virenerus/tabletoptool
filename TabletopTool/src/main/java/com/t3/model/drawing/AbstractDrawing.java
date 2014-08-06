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

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.image.ImageObserver;

import com.t3.client.TabletopTool;
import com.t3.guid.GUID;
import com.t3.model.Zone;
import com.t3.xstreamversioned.SerializationVersion;

/**
 * Abstract drawing. This class takes care of setting up the Pen since that will be the same for all implementing
 * classes.
 */
@SerializationVersion(0)
public abstract class AbstractDrawing implements Drawable, ImageObserver {
	/**
	 * The unique identifier for this drawable. It is immutable.
	 */
	private final GUID id = new GUID();

	private Zone.Layer layer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see tabletoptool.model.drawing.Drawable#draw(java.awt.Graphics2D, tabletoptool.model.drawing.Pen)
	 */
	@Override
	public void draw(Graphics2D g, Pen pen) {
		if (pen == null) {
			pen = Pen.DEFAULT;
		}
		Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(pen.getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		Composite oldComposite = g.getComposite();
		if (pen.isEraser()) {
			g.setComposite(AlphaComposite.Clear);
		} else if (pen.getOpacity() != 1) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, pen.getOpacity()));
		}
		if (pen.getBackgroundMode() == Pen.MODE_SOLID) {
			if (pen.getBackgroundPaint() != null) {
				g.setPaint(pen.getBackgroundPaint().getPaint(this));
			} else {
				// **** Legacy support for 1.1
				g.setColor(new Color(pen.getBackgroundColor()));
			}
			drawBackground(g);
		}
		if (pen.getForegroundMode() == Pen.MODE_SOLID) {
			if (pen.getPaint() != null) {
				g.setPaint(pen.getPaint().getPaint(this));
			} else {
				// **** Legacy support for 1.1
				g.setColor(new Color(pen.getColor()));
			}
			draw(g);
		}
		g.setComposite(oldComposite);
		g.setStroke(oldStroke);
	}

	protected abstract void draw(Graphics2D g);

	protected abstract void drawBackground(Graphics2D g);

	/**
	 * Get the id for this AbstractDrawing.
	 * 
	 * @return Returns the current value of id.
	 */
	@Override
	public GUID getId() {
		return id;
	}

	@Override
	public void setLayer(Zone.Layer layer) {
		this.layer = layer;
	}

	@Override
	public Zone.Layer getLayer() {
		return layer != null ? layer : Zone.Layer.BACKGROUND;
	}

	/**
	 * Use the id for equals.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AbstractDrawing))
			return false;
		return id.equals(obj);
	}

	/**
	 * Use the id for hash code.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	////
	// IMAGE OBSERVER
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		TabletopTool.getFrame().getCurrentZoneRenderer().flushDrawableRenderer();
		TabletopTool.getFrame().refresh();
		return true;
	}
}
