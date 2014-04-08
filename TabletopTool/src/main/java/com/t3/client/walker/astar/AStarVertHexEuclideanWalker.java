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

import com.t3.model.Zone;

public class AStarVertHexEuclideanWalker extends AbstractAStarHexEuclideanWalker {

	public AStarVertHexEuclideanWalker (Zone zone) {
		super(zone);
		initNeighborMaps();
	}
	
	@Override
	protected void initNeighborMaps() {
		oddNeighborMap = new int[][] 
	      { { -1, 0, 1 }, { 0, -1, 1 }, { 1, 0, 1 }, 
			{ 0, 0, 0 },   			  { 0, 0, 0 }, 
			{ -1, 1, 1 },  { 0, 1, 1 }, { 1, 1, 1 } };
		
		evenNeighborMap = new int[][] 
  	      { { -1, -1, 1 }, { 0, -1, 1 }, { 1, -1, 1 }, 
			{ 0, 0, 0 },   			  { 0, 0, 0 }, 
			{ -1, 0, 1 },  { 0, 1, 1 }, { 1, 0, 1 } };
	}

	@Override
	protected int[][] getNeighborMap(int x, int y) {

		return x % 2 == 0 ? evenNeighborMap : oddNeighborMap;
	}
		
}
