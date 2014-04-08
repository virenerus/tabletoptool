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
package com.t3.model.grid;


/**
 * Given a string describing the type of desired grid, this factory
 * creates and returns an object of the appropriate type.
 * <p>
 * (Ugh.  This really should use an SPI-like factory interface.)
 */
public class GridFactory {
	public static final String HEX_VERT = "Vertical Hex";
	public static final String HEX_HORI = "Horizontal Hex";
	public static final String SQUARE = "Square";
	public static final String NONE = "None";

	public static Grid createGrid(String type) {
		return createGrid(type,true,false);
	}

	public static Grid createGrid(String type, boolean faceEdges, boolean faceVertices) {
		if (isHexVertical(type)) {
			return new HexGridVertical(faceEdges, faceVertices);
		}
		if (isHexHorizontal(type)) {
			return new HexGridHorizontal(faceEdges, faceVertices);
		}
		if (isSquare(type)) {
			return new SquareGrid(faceEdges, faceVertices);
		}
		if (isNone(type)) {
			return new GridlessGrid();
		}
		throw new IllegalArgumentException("Unknown grid type: " + type);
	}
	
	public static String getGridType(Grid grid) {
		if (grid instanceof HexGridVertical) {
			return HEX_VERT;
		}
		if (grid instanceof HexGridHorizontal) {
			return HEX_HORI;
		}
		if (grid instanceof SquareGrid) {
			return SQUARE;
		}
		if (grid instanceof GridlessGrid) {
			return NONE;
		}
		throw new IllegalArgumentException("Don't know type of grid: " + grid.getClass().getName());
	}

	public static boolean isSquare(String gridType) {
		return SQUARE.equals(gridType);
	}
	
	public static boolean isNone(String gridType) {
		return NONE.equals(gridType);
	}
	
	public static boolean isHexVertical(String gridType) {
		return HEX_VERT.equals(gridType);
	}
	
	public static boolean isHexHorizontal(String gridType) {
		return HEX_HORI.equals(gridType);
	}
}
