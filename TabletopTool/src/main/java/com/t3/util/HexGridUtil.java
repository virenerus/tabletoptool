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
package com.t3.util;

import com.t3.model.CellPoint;
import com.t3.model.grid.HexGrid;
import com.t3.model.grid.HexGridHorizontal;

/**
 * Provides methods to handle hexgrid issues that don't exist with a square grid.
 * @author Tylere
 */
public class HexGridUtil {
	/** 
	 * Convert to u-v coordinates where the v-axis points
	 * along the direction of edge to edge hexes
	 */ 
	private static int[] toUVCoords(CellPoint cp, HexGrid grid) {
		int cpU, cpV;
		if (grid instanceof HexGridHorizontal) {
			cpU = cp.y;
			cpV = cp.x;
		}
		else {
			cpU = cp.x;
			cpV = cp.y;
		}
		return new int[] {cpU, cpV};
	}
	
	/**
	 * Convert from u-v coords to grid coords
	 * @return the point in grid-space
	 */
	private static CellPoint fromUVCoords(int u, int v, HexGrid grid) {
		CellPoint cp = new CellPoint(u, v);
		if (grid instanceof HexGridHorizontal) {
			cp.x = v;
			cp.y = u;
		}
		return cp;
	}

	public static CellPoint getWaypoint(HexGrid grid, CellPoint cp, int width, int height) {
		if( width == height ) {
			int[] cpUV = toUVCoords(cp, grid); 
			return fromUVCoords(cpUV[0], cpUV[1] + (int)((width-1)/2), grid);
		}
		return cp;
	}
}
