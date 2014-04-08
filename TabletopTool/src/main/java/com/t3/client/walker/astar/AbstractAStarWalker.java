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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import com.t3.client.walker.AbstractZoneWalker;
import com.t3.model.CellPoint;
import com.t3.model.Zone;

public abstract class AbstractAStarWalker extends AbstractZoneWalker {
	public AbstractAStarWalker(Zone zone) {
		super(zone);
	}

	private int distance = -1;

	/**
	 * Returns the list of neighbor cells that are valid for being movement-checked. This is an array of (x,y) offsets
	 * (see the constants in this class) named as compass points.
	 * <p>
	 * It should be possible to query the current (x,y) CellPoint passed in to determine which directions are feasible
	 * to move into. But it would require information about visibility (which token is moving, does it have sight, and
	 * so on). Currently that information is not available here, but perhaps an option Token parameter could be
	 * specified to the constructor? Or maybe as the tree was scanned, since I believe all Grids share a common
	 * ZoneWalker.
	 */
	protected abstract int[][] getNeighborMap(int x, int y);

	@Override
	protected List<CellPoint> calculatePath(CellPoint start, CellPoint end) {
		List<AStarCellPoint> openList = new ArrayList<AStarCellPoint>();
		Map<AStarCellPoint, AStarCellPoint> openSet = new HashMap<AStarCellPoint, AStarCellPoint>(); // For faster lookups
		Set<AStarCellPoint> closedSet = new HashSet<AStarCellPoint>();

		openList.add(new AStarCellPoint(start));
		openSet.put(openList.get(0), openList.get(0));

		AStarCellPoint node = null;

		while (!openList.isEmpty()) {
			node = openList.remove(0);
			openSet.remove(node);
			if (node.equals(end)) {
				break;
			}
			int[][] neighborMap = getNeighborMap(node.x, node.y);
			for (int i = 0; i < neighborMap.length; i++) {
				int x = node.x + neighborMap[i][0];
				int y = node.y + neighborMap[i][1];
				AStarCellPoint neighborNode = new AStarCellPoint(x, y);
				if (closedSet.contains(neighborNode)) {
					continue;
				}
				neighborNode.parent = node;
				neighborNode.gScore = gScore(start, neighborNode);
				neighborNode.hScore = hScore(neighborNode, end);

				if (openSet.containsKey(neighborNode)) {
					AStarCellPoint oldNode = openSet.get(neighborNode);
					// check if it is cheaper to get here the way that we just
					// came, versus the previous path
					if (neighborNode.gScore < oldNode.gScore) {
						oldNode.gScore = neighborNode.gScore;
						neighborNode = oldNode;
						neighborNode.parent = node;
					}
					continue;
				}
				pushNode(openList, neighborNode);
				openSet.put(neighborNode, neighborNode);
			}
			closedSet.add(node);
			node = null;
		}
		List<CellPoint> ret = new LinkedList<CellPoint>();
		while (node != null) {
			ret.add(node);
			node = node.parent;
		}
		distance = -1;
		Collections.reverse(ret);
		return ret;
	}

	private void pushNode(List<AStarCellPoint> list, AStarCellPoint node) {
		if (list.isEmpty()) {
			list.add(node);
			return;
		}
		if (node.cost() < list.get(0).cost()) {
			list.add(0, node);
			return;
		}
		if (node.cost() > list.get(list.size() - 1).cost()) {
			list.add(node);
			return;
		}
		for (ListIterator<AStarCellPoint> iter = list.listIterator(); iter.hasNext();) {
			AStarCellPoint listNode = iter.next();
			if (listNode.cost() > node.cost()) {
				iter.previous();
				iter.add(node);
				return;
			}
		}
	}

	protected abstract int calculateDistance(List<CellPoint> path, int feetPerCell);

	protected abstract double gScore(CellPoint p1, CellPoint p2);

	protected abstract double hScore(CellPoint p1, CellPoint p2);

	@Override
	public int getDistance() {
		if (distance == -1) {
			distance = calculateDistance(getPath().getCellPath(), getZone().getUnitsPerCell());
		}
		return distance;
	}
}
