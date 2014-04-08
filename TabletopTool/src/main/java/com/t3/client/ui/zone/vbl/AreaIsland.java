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
package com.t3.client.ui.zone.vbl;

import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

public class AreaIsland implements AreaContainer {

	private AreaMeta meta;
	private Set<AreaOcean> oceanSet = new HashSet<AreaOcean>();
	
	
	public AreaIsland(AreaMeta meta) {
		this.meta = meta;
	}

	public Set<VisibleAreaSegment> getVisibleAreaSegments(Point2D origin) {

		return meta.getVisibleAreas(origin);
	}
	
	public AreaOcean getDeepestOceanAt(Point2D point) {

		if (!meta.area.contains(point)) {
			return null;
		}
		
		for (AreaOcean ocean : oceanSet) {
			AreaOcean deepOcean = ocean.getDeepestOceanAt(point);
			if (deepOcean != null) {
				return deepOcean;
			}
		}
		
		// If we don't have an ocean that contains the point then 
		// the point is not technically in an ocean
		return null;
	}
	
	public Set<AreaOcean> getOceans() {
		return new HashSet<AreaOcean>(oceanSet);
	}
	
	public void addOcean(AreaOcean ocean) {
		oceanSet.add(ocean);
	}
	
	////
	// AREA CONTAINER
	@Override
	public Area getBounds() {
		return meta.area;
	}
}
