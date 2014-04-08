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

import com.t3.model.grid.Grid;
import com.t3.model.grid.SquareGrid;

public class TestSquareGrid extends TestCase {
	public void testSpotCheck() throws Exception {
		Grid grid = new SquareGrid();
		System.out.println(grid.convert(new CellPoint(-1, 0)));
	}
}
