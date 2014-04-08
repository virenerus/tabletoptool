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
package com.t3.model.vision;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import com.t3.model.Token;
import com.t3.model.Vision;
import com.t3.model.Zone;

public class RoundVision extends Vision {
	public RoundVision() {
	}

	public RoundVision(int distance) {
		setDistance(distance);
	}

	@Override
	public Anchor getAnchor() {
		return Vision.Anchor.CENTER;
	}

	@Override
	protected Area createArea(Zone zone, Token token) {
		int size = getDistance() * getZonePointsPerCell(zone) * 2;
		int half = size / 2;
		Area area = new Area(new Ellipse2D.Double(-half, -half, size, size));

		return area;
	}

	@Override
	public String toString() {
		return "Round";
	}
}
