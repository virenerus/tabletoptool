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

import com.t3.client.ScreenPoint;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.model.grid.Grid;

/**
 * This class represents a location based on the grid coordinates of a zone.
 * <p>
 * They can be converted to screen coordinates by calling {@link #convertToScreen(ZoneRenderer)}.
 * <p>
 * They can be converted to ZonePoints by calling {@link Grid#convert(CellPoint)}.
 * 
 * @author trevor
 */
public class CellPoint extends AbstractPoint {
	public CellPoint(int x, int y) {
		super(x, y);
	}

	@Override
	public String toString() {
		return "CellPoint" + super.toString();
	}

	/**
	 * Find the screen coordinates of the upper left hand corner of a cell taking into account scaling and translation.
	 * <b>This code does not call {@link Grid#getCellOffset()}, which might be appropriate in some circumstances.</b>
	 * 
	 * @param renderer
	 *            This renderer provides scaling
	 * @return The screen coordinates of the upper left hand corner in the passed point or in a new point.
	 */
	public ScreenPoint convertToScreen(ZoneRenderer renderer) {
		double scale = renderer.getScale();
		Zone zone = renderer.getZone();

		Grid grid = zone.getGrid();
		ZonePoint zp = grid.convert(this);

		int sx = renderer.getViewOffsetX() + (int) (zp.x * scale);
		int sy = renderer.getViewOffsetY() + (int) (zp.y * scale);

		return new ScreenPoint(sx, sy);
	}
}
