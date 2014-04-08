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
package com.t3.client.walker;

import java.util.ArrayList;
import java.util.List;

import com.t3.model.CellPoint;
import com.t3.model.Zone;

public class NaiveWalker extends AbstractZoneWalker {
	public NaiveWalker(Zone zone) {
		super(zone);
	}

	private int distance;

	@Override
	protected List<CellPoint> calculatePath(CellPoint start, CellPoint end) {
		List<CellPoint> list = new ArrayList<CellPoint>();

		int x = start.x;
		int y = start.y;

		int count = 0;
		while (true && count < 100) {
			list.add(new CellPoint(x, y));

			if (x == end.x && y == end.y) {
				break;
			}
			if (x < end.x)
				x++;
			if (x > end.x)
				x--;
			if (y < end.y)
				y++;
			if (y > end.y)
				y--;

			count++;
		}
		distance = (list.size() - 1) * 5;
		return list;
	}

	@Override
	public int getDistance() {
		return distance;
	}
}
