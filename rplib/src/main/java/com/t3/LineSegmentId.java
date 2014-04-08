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
package com.t3;

import java.awt.geom.Point2D;

public class LineSegmentId {

	private int x1;
	private int y1;
	private int x2;
	private int y2;

	public LineSegmentId(Point2D p1, Point2D p2) {

		x1 = (int)Math.min(p1.getX(), p2.getX());
		x2 = (int)Math.max(p1.getX(), p2.getX());
		
		y1 = (int)Math.min(p1.getY(), p2.getY());
		y2 = (int)Math.max(p1.getY(), p2.getY());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LineSegmentId)) {
			return false;
		}
		
		LineSegmentId line = (LineSegmentId) obj;
		
		return x1 == line.x1 && y1 == line.y1 && x2 == line.x2 && y2 == line.y2;
	}
	
	@Override
	public int hashCode() {
		// Doesn't have to be unique, only a decent spread
		return x1 + y1 + (x2 + y2)*31;
	}
}
