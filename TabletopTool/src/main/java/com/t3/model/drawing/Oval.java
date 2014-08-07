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
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import com.t3.xstreamversioned.version.SerializationVersion;

/**
 * An oval.
 */
@SerializationVersion(0)
public class Oval extends Rectangle {
	/**
	 * @param x
	 * @param y
	 */
	public Oval(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	protected void draw(Graphics2D g) {
		int minX = Math.min(startPoint.x, endPoint.x);
		int minY = Math.min(startPoint.y, endPoint.y);

		int width = Math.abs(startPoint.x - endPoint.x);
		int height = Math.abs(startPoint.y - endPoint.y);

		g.drawOval(minX, minY, width, height);
	}

	@Override
	protected void drawBackground(Graphics2D g) {
		int minX = Math.min(startPoint.x, endPoint.x);
		int minY = Math.min(startPoint.y, endPoint.y);

		int width = Math.abs(startPoint.x - endPoint.x);
		int height = Math.abs(startPoint.y - endPoint.y);

		g.fillOval(minX, minY, width, height);
	}

	@Override
	public Area getArea() {
		java.awt.Rectangle r = getBounds();
		return new Area(new Ellipse2D.Double(r.x, r.y, r.width, r.height));
	}
}
