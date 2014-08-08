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

import com.t3.model.CellPoint;
import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(0)
public class AStarCellPoint extends CellPoint {
	AStarCellPoint parent;
	double hScore;
	double gScore;

	public AStarCellPoint() {
		super(0, 0);
	}

	public AStarCellPoint(int x, int y) {
		super(x, y);
	}

	public AStarCellPoint(CellPoint p) {
		super(p.x, p.y);
	}

	public double cost() {
		return hScore + gScore;
	}
}
