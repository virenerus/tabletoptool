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

public interface GridCapabilities {
	/**
	 * Whether the parent grid type supports snap-to-grid. Some may not, such as the Gridless grid type.
	 * 
	 * @return
	 */
	public boolean isSnapToGridSupported();

	/**
	 * Whether the parent grid type supports automatic pathing from point A to point B. Usually true except for the
	 * Gridless grid type.
	 * 
	 * @return
	 */
	public boolean isPathingSupported();

	/**
	 * Whether ...
	 * 
	 * @return
	 */
	public boolean isPathLineSupported();

	/**
	 * Whether the parent grid supports the concept of coordinates to be placed on the grid. Generally this requires a
	 * grid type that has some notion of "cell size", which means Gridless need not apply. ;-)
	 * 
	 * @return
	 */
	public boolean isCoordinatesSupported();

	/**
	 * The secondary dimension should be linked to changes in the primary dimension but the primary dimension is
	 * independent of the secondary.
	 */
	public boolean isSecondDimensionAdjustmentSupported();
}
