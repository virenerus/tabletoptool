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

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import com.t3.GUID;
import com.t3.model.Token;
import com.t3.model.Vision;
import com.t3.model.Zone;

public class FacingConicVision extends Vision {
	private Integer lastFacing;

	// DEPRECATED: here to support the serialization
	private transient GUID tokenGUID;

	public FacingConicVision() {
	}

	public FacingConicVision(int distance) {
		setDistance(distance);
	}

	@Override
	public Anchor getAnchor() {
		return Vision.Anchor.CENTER;
	}

	@Override
	public Area getArea(Zone zone, Token token) {
		if (token == null) {
			return null;
		}
		if (lastFacing == null || !lastFacing.equals(token.getFacing())) {
			flush();
			lastFacing = token.getFacing();
		}
		return super.getArea(zone, token);
	}

	@Override
	protected Area createArea(Zone zone, Token token) {
		if (token == null) {
			return null;
		}
		if (token.getFacing() == null) {
			token.setFacing(0);
		}
		// Start round
		int size = getDistance() * getZonePointsPerCell(zone) * 2;
		int half = size / 2;
		Area area = new Area(new Ellipse2D.Float(-half, -half, size, size));

		// Cut off the part that isn't in the cone
		area.subtract(new Area(new Rectangle(-100000, 1, 200000, 200000)));
		area.subtract(new Area(new Rectangle(-100000, -100000, 99999, 200000)));

		// Rotate
		int angle = (-token.getFacing() + 45);
		area.transform(AffineTransform.getRotateInstance(Math.toRadians(angle)));

		lastFacing = token.getFacing();
		return area;
	}

	@Override
	public String toString() {
		return "Conic Facing";
	}
}
