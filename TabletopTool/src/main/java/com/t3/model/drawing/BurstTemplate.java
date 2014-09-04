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
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

import com.t3.model.ZonePoint;
import com.t3.xstreamversioned.version.SerializationVersion;

/**
 * Create and paint a donut burst
 * 
 * @author Jay
 */
@SerializationVersion(0)
public class BurstTemplate extends RadiusTemplate {
	/*---------------------------------------------------------------------------------------------
	 * Instance Variables
	 *-------------------------------------------------------------------------------------------*/

	/**
	 * Renderer for the blast. The {@link Shape} is just a rectangle.
	 */
	private final ShapeDrawable renderer = new ShapeDrawable(new Rectangle());

	/**
	 * Renderer for the blast. The {@link Shape} is just a rectangle.
	 */
	private final ShapeDrawable vertexRenderer = new ShapeDrawable(new Rectangle());

	/*---------------------------------------------------------------------------------------------
	 * Instance Methods
	 *-------------------------------------------------------------------------------------------*/

	/**
	 * This methods adjusts the rectangle in the renderer to match the new radius, vertex, or direction. Due to the fact
	 * that it is impossible to draw to the cardinal directions evenly when the radius is an even number and still stay
	 * in the squares, that case isn't allowed.
	 */
	private void adjustShape() {
		if (getZoneReference() == null)
			return;
		int gridSize = getZoneReference().value().getGrid().getSize();
		Rectangle r = (Rectangle) vertexRenderer.getShape();
		r.setBounds(getVertex().x, getVertex().y, gridSize, gridSize);
		r = (Rectangle) renderer.getShape();
		r.setBounds(getVertex().x, getVertex().y, gridSize, gridSize);
		r.x -= getRadius() * gridSize;
		r.y -= getRadius() * gridSize;
		r.width = r.height = (getRadius() * 2 + 1) * gridSize;
	}

	/*---------------------------------------------------------------------------------------------
	 * Overridden *Template Methods
	 *-------------------------------------------------------------------------------------------*/

	/**
	 * @see com.t3.model.drawing.AbstractTemplate#setRadius(int)
	 */
	@Override
	public void setRadius(int squares) {
		super.setRadius(squares);
		adjustShape();
	}

	/**
	 * @see com.t3.model.drawing.AbstractTemplate#setVertex(com.t3.model.ZonePoint)
	 */
	@Override
	public void setVertex(ZonePoint vertex) {
		super.setVertex(vertex);
		adjustShape();
	}

	/**
	 * @see com.t3.model.drawing.AbstractTemplate#getDistance(int, int)
	 */
	@Override
	public int getDistance(int x, int y) {
		return Math.max(x, y);
	}

	@Override
	public Rectangle getBounds() {
		Rectangle r = new Rectangle(renderer.getShape().getBounds());
		// We don't know pen width, so add some padding to account for it
		r.x -= 5;
		r.y -= 5;
		r.width += 10;
		r.height += 10;

		return r;
	}

	/*---------------------------------------------------------------------------------------------
	 * Overridden AbstractDrawing Methods
	 *-------------------------------------------------------------------------------------------*/

	/**
	 * @see com.t3.model.drawing.AbstractDrawing#draw(java.awt.Graphics2D)
	 */
	@Override
	protected void draw(Graphics2D g) {
		renderer.draw(g);
		vertexRenderer.draw(g);
	}

	/**
	 * @see com.t3.model.drawing.AbstractDrawing#drawBackground(java.awt.Graphics2D)
	 */
	@Override
	protected void drawBackground(Graphics2D g) {
		Composite old = g.getComposite();
		if (old != AlphaComposite.Clear)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, DEFAULT_BG_ALPHA));
		renderer.drawBackground(g);
		g.setComposite(old);
	}
}
