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
package com.t3.client.walker.astar;

import java.util.List;

import com.t3.model.CellPoint;
import com.t3.model.Zone;
import com.t3.model.ZonePoint;

public abstract class AbstractAStarHexEuclideanWalker extends AbstractAStarWalker {
	protected int[][] oddNeighborMap;
	protected int[][] evenNeighborMap;

	public AbstractAStarHexEuclideanWalker(Zone zone) {
		super(zone);
	}

	protected abstract void initNeighborMaps();

	@Override
	protected int[][] getNeighborMap(int x, int y) {
		return x % 2 == 0 ? evenNeighborMap : oddNeighborMap;
	}

	@Override
	protected double gScore(CellPoint p1, CellPoint p2) {
		return euclideanDistance(p1, p2);
	}

	@Override
	protected double hScore(CellPoint p1, CellPoint p2) {
		return euclideanDistance(p1, p2);
	}

	private double euclideanDistance(CellPoint p1, CellPoint p2) {
		ZonePoint zp1 = getZone().getGrid().convert(p1);
		ZonePoint zp2 = getZone().getGrid().convert(p2);

		int a = zp2.x - zp1.x;
		int b = zp2.y - zp1.y;

		return Math.sqrt(a * a + b * b);
	}

	@Override
	protected int calculateDistance(List<CellPoint> path, int feetPerCell) {
		int cellsMoved = path != null && path.size() > 1 ? path.size() - 1 : 0;
		return cellsMoved * feetPerCell;
	}
}
