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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.t3.xstreamversioned.SerializationVersion;

@SerializationVersion(0)
public class Path<T extends AbstractPoint> {
	private final List<T> cellList = new LinkedList<T>();
	private final List<T> waypointList = new LinkedList<T>();

	public void addPathCell(T point) {
		cellList.add(point);
	}

	public void addAllPathCells(List<T> cells) {
		cellList.addAll(cells);
	}

	public List<T> getCellPath() {
		return Collections.unmodifiableList(cellList);
	}

	public void replaceLastPoint(T point) {
		cellList.remove(cellList.size() - 1);
		cellList.add(point);
	}

	public void addWayPoint(T point) {
		waypointList.add(point);
	}

	public boolean isWaypoint(T point) {
		return waypointList.contains(point);
	}

	public T getLastWaypoint() {
		if (waypointList.isEmpty())
			return null;
		return waypointList.get(waypointList.size() - 1);
	}

	/**
	 * Returns the last waypoint if there is one, or the last T point if there is not.
	 * 
	 * @return a non-<code>null</code> location
	 */
	public T getLastJunctionPoint() {
		T temp = getLastWaypoint();
		return temp != null ? temp : cellList.get(cellList.size() - 1);
	}

	public Path<T> derive(int cellOffsetX, int cellOffsetY) {
		Path<T> path = new Path<T>();
		for (T cp : cellList) {
			T np = (T) cp.clone();
			np.x -= cellOffsetX;
			np.y -= cellOffsetY;
			path.addPathCell(np);
		}
		for (T cp : waypointList) {
			T np = (T) cp.clone();
			np.x -= cellOffsetX;
			np.y -= cellOffsetY;
			path.addWayPoint(np);
		}
		return path;
	}
}
