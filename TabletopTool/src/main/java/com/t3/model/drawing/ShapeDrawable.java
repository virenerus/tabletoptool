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

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;

import com.t3.xstreamversioned.version.SerializationVersion;

/**
 * An rectangle
 */
@SerializationVersion(0)
public class ShapeDrawable extends AbstractDrawing {
	private final Shape shape;
	private final boolean useAntiAliasing;

	public ShapeDrawable(Shape shape, boolean useAntiAliasing) {
		this.shape = shape;
		this.useAntiAliasing = useAntiAliasing;
	}

	public ShapeDrawable(Shape shape) {
		this(shape, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.t3.model.drawing.Drawable#getBounds()
	 */
	@Override
	public java.awt.Rectangle getBounds() {
		return shape.getBounds();
	}

	@Override
	public Area getArea() {
		return new Area(shape);
	}

	@Override
	protected void draw(Graphics2D g) {
		Object oldAA = applyAA(g);
		g.draw(shape);
		restoreAA(g, oldAA);
	}

	@Override
	protected void drawBackground(Graphics2D g) {
		Object oldAA = applyAA(g);
		g.fill(shape);
		restoreAA(g, oldAA);
	}

	public Shape getShape() {
		return shape;
	}

	private Object applyAA(Graphics2D g) {
		Object oldAA = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, useAntiAliasing ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
		return oldAA;
	}

	private void restoreAA(Graphics2D g, Object oldAA) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAA);
	}
}
