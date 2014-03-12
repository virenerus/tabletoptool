package com.t3.macro.api.functions;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.t3.client.AppPreferences;
import com.t3.client.TabletopTool;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.client.walker.WalkerMetric;
import com.t3.client.walker.ZoneWalker;
import com.t3.macro.api.views.TokenView;
import com.t3.model.CellPoint;
import com.t3.model.Path;
import com.t3.model.Zone;
import com.t3.model.ZonePoint;
import com.t3.model.grid.Grid;
import com.t3.util.math.IntLine;
import com.t3.util.math.IntPoint;

public class PathFunctions {
	
	/**
	 * This method calculates the cost of a given path
	 * @param path the path of which you want the cost
	 * @param followGrid if the cost should be calculated following the grid cells
	 * @return the cost of the path
	 */
	public double getCost(List<IntPoint> path, boolean followGrid) {
		ZoneWalker walker = null;

		WalkerMetric metric = TabletopTool.isPersonalServer() ? 
				AppPreferences.getMovementMetric() : TabletopTool.getServerPolicy().getMovementMetric();

		ZoneRenderer zr = TabletopTool.getFrame().getCurrentZoneRenderer();
		Zone zone = zr.getZone();
		Grid grid = zone.getGrid();

		Path<ZonePoint> gridlessPath;
		/*
		 * Lee: causes an NPE when used on a newly dropped token. While a true solution would probably be to create a
		 * "path" based on the token's coords when it is dropped on the map, the easy out here would be to just return a
		 * "0".
		 * 
		 * Final Edit: attempting to create a default path for new drops had undesirable effects. Therefore, let's opt
		 * for the easy fix
		 */
		if(path==null)
			return 0;
		
		IntPoint p=path.get(0);

		if (followGrid && grid.getCapabilities().isSnapToGridSupported()) {
			if (zone.getGrid().getCapabilities().isPathingSupported()) {
				List<CellPoint> cplist = new ArrayList<CellPoint>();
				walker = grid.createZoneWalker();
				walker.replaceLastWaypoint(new CellPoint(p.getX(), p.getY()));
				for (IntPoint point : path) {
					CellPoint tokenPoint = new CellPoint(point.getX(), point.getY());
					//walker.setWaypoints(tokenPoint);
					walker.replaceLastWaypoint(tokenPoint);
					cplist.add(tokenPoint);
				}
				return calculateGridDistance(cplist, zone.getUnitsPerCell(), metric);
			}
		} else {
			gridlessPath = new Path<ZonePoint>();
			for (IntPoint point : path) {
				gridlessPath.addPathCell(new ZonePoint(point.getX(), point.getY()));
			}
			double c = 0;
			ZonePoint lastPoint = null;
			for (ZonePoint zp : gridlessPath.getCellPath()) {
				if (lastPoint == null) {
					lastPoint = zp;
					continue;
				}
				int a = lastPoint.x - zp.x;
				int b = lastPoint.y - zp.y;
				c += Math.hypot(a, b);
				lastPoint = zp;
			}
			c /= zone.getGrid().getSize(); // Number of "cells"
			c *= zone.getUnitsPerCell(); // "actual" distance traveled
			return c;
		}
		return -1;
	}
	
	private int calculateGridDistance(List<CellPoint> path, int feetPerCell, WalkerMetric metric) {
		if (path == null || path.size() == 0)
			return 0;

		final int feetDistance;

		{
			int numDiag = 0;
			int numStrt = 0;

			CellPoint previousPoint = null;
			for (CellPoint point : path) {
				if (previousPoint != null) {
					int change = Math.abs(previousPoint.x - point.x) + Math.abs(previousPoint.y - point.y);
					if(change==1)
						numStrt++;
					else if(change==2)
						numDiag++;
					else {
						assert false : String.format("Illegal path, cells are not contiguous change=%d", change);
						return -1;
					}
				}
				previousPoint = point;
			}
			final int cellDistance;
			switch (metric) {
			case MANHATTAN:
			case NO_DIAGONALS:
				cellDistance = (numStrt + numDiag * 2);
				break;
			case ONE_ONE_ONE:
				cellDistance = (numStrt + numDiag);
				break;
			default:
			case ONE_TWO_ONE:
				cellDistance = (numStrt + numDiag + numDiag / 2);
				break;
			}
			feetDistance = cellDistance * feetPerCell;
		}
		return feetDistance;
	}
	
	/**
	 * This method allows you to determine if a token moved over a number of points
	 * @param token the token that moved on the path
	 * @param path the path the token took (gridless unit)
	 * @param points the points of which you want to check if the token walked over
	 * @return the points that the token actually walked over
	 */
	public List<IntPoint> movedOverPoints(TokenView token, List<IntPoint> path, List<IntPoint> points) {
		List<IntPoint> returnPoints = new ArrayList<IntPoint>(points.size());

		Polygon targetArea = new Polygon();
		for (IntPoint point : points) {
			int x = point.getX();
			int y = point.getY();
			targetArea.addPoint(x, y);
		}
		
		for (IntPoint entry : path) {
			Rectangle2D oa = token.getBounds(entry.getX(),entry.getY()).getBounds2D();
			if (targetArea.contains(oa) || targetArea.intersects(oa)) {
				returnPoints.add(entry);
			}
		}
		return returnPoints;
	}
	
	/**
	 * This method allows you to determine if a token moved over another token.
	 * @param token the token that moved on the path
	 * @param path the path the token took
	 * @param target the token you want to test against
	 * @return the points where the path of the token crossed the target
	 */
	public List<IntPoint> movedOverToken(TokenView token, List<IntPoint> path, TokenView target) {
		List<IntPoint> returnPoints = new ArrayList<IntPoint>();

		Rectangle targetArea = target.getBounds();

		if (path == null) {
			return returnPoints;
		}
		if (token.isSnapToGrid()) {
			for (IntPoint entry : path) {
				Rectangle originalArea = token.getBounds(entry.getX(), entry.getY());
				if (targetArea.intersects(originalArea) || originalArea.intersects(targetArea)) {
					returnPoints.add(entry);
				}
			}
		} else {
			//Lee: establish first point, then process line intersection when a line can be drawn.
			int ctr = 0;
			Point previousPoint = new Point();
			for (IntPoint entry : path) {
				Rectangle tokenArea = token.getBounds();
				Point currentPoint = new Point(entry.getX(), entry.getY());
				if (ctr > 0) {
					if (targetArea.intersectsLine(new Line2D.Double(previousPoint, currentPoint)) ||
							targetArea.intersects(tokenArea)) {
						returnPoints.add(new IntLine(
								(int) previousPoint.getX(), 
								(int) previousPoint.getY(), 
								entry.getX(),
								entry.getY()));
					}
				}
				previousPoint = currentPoint;
				ctr += 1;
			}
			//Lee: commenting this out
			//originalArea = tokenInContext.getBounds(zone);
		}
		return returnPoints;
	}
}
