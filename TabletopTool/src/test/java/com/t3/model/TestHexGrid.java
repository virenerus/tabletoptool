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
package com.t3.model;

import junit.framework.TestCase;

import com.t3.model.grid.HexGrid;
import com.t3.model.grid.HexGridHorizontal;

public class TestHexGrid extends TestCase {
	public void testConvertCellToZone() throws Exception {
		int start = -100;
		//int start = 0;
		HexGrid grid = new HexGridHorizontal();
		for (int y = start; y < 100; y++) {
			for (int x = start; x < 100; x++) {
				CellPoint cp = new CellPoint(x, y);
				ZonePoint zp = grid.convert(cp);
				assertEquals(cp, grid.convert(zp));
			}
		}
	}

	public void testSpotCheck() throws Exception {
		HexGrid grid = new HexGridHorizontal();

		CellPoint cp1 = new CellPoint(4, 1);
		CellPoint cp2 = new CellPoint(3, 1);

		ZonePoint zp1 = grid.convert(cp1);
		ZonePoint zp2 = grid.convert(cp2);

		System.out.println(zp1 + " - " + grid.convert(zp1));
		System.out.println(zp2 + " - " + grid.convert(zp2));
	}
}
