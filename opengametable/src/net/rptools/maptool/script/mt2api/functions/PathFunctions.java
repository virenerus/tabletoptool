package net.rptools.maptool.script.mt2api.functions;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import net.rptools.maptool.client.AppPreferences;
import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.client.ui.zone.ZoneRenderer;
import net.rptools.maptool.client.walker.WalkerMetric;
import net.rptools.maptool.client.walker.ZoneWalker;
import net.rptools.maptool.model.CellPoint;
import net.rptools.maptool.model.Grid;
import net.rptools.maptool.model.Path;
import net.rptools.maptool.model.Zone;
import net.rptools.maptool.model.ZonePoint;
import net.rptools.maptool.script.mt2api.TokenView;
import net.rptools.maptool.util.math.IntLine;
import net.rptools.maptool.util.math.IntPoint;

public class PathFunctions {
	public double getMoveCount(List<IntPoint> path, boolean followGrid) {
		ZoneWalker walker = null;

		WalkerMetric metric = MapTool.isPersonalServer() ? 
				AppPreferences.getMovementMetric() : MapTool.getServerPolicy().getMovementMetric();

		ZoneRenderer zr = MapTool.getFrame().getCurrentZoneRenderer();
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
	 * 
	 * @param token the token that moved on the path
	 * @param path the path the token took (gridless unit)
	 * @param points the points of which you want to check if the token walked over them
	 * @return the points that the token walked over
	 */
	public List<IntPoint> movedOverPoint(TokenView token, List<IntPoint> path, List<IntPoint> points) {
		List<IntPoint> returnPoints = new ArrayList<IntPoint>(points.size());
		Zone zone=MapTool.getFrame().getCurrentZoneRenderer().getZone();

		Polygon targetArea = new Polygon();
		for (IntPoint point : points) {
			int x = point.getX();
			int y = point.getY();
			targetArea.addPoint(x, y);
		}
		
		for (IntPoint entry : path) {
			Rectangle2D oa = token.getBounds(zone,entry.getX(),entry.getY()).getBounds2D();
			if (targetArea.contains(oa) || targetArea.intersects(oa)) {
				returnPoints.add(entry);
			}
		}
		return returnPoints;
	}
	
	public List<IntPoint> movedOverToken(TokenView token, List<IntPoint> path, TokenView target) {
		List<IntPoint> returnPoints = new ArrayList<IntPoint>();

		Zone zone=MapTool.getFrame().getCurrentZoneRenderer().getZone();
		Rectangle targetArea = target.getBounds(zone);

		if (path == null) {
			return returnPoints;
		}
		if (token.isSnapToGrid()) {
			for (IntPoint entry : path) {
				Rectangle originalArea = token.getBounds(zone, entry.getX(), entry.getY());
				if (targetArea.intersects(originalArea) || originalArea.intersects(targetArea)) {
					returnPoints.add(entry);
				}
			}
		} else {
			//Lee: establish first point, then process line intersection when a line can be drawn.
			int ctr = 0;
			Point previousPoint = new Point();
			for (IntPoint entry : path) {
				Rectangle tokenArea = token.getBounds(zone);
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
