package net.rptools.maptool.script.mt2.functions.vbl;

import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.client.ui.zone.ZoneRenderer;

/**
 * This class holds all the implementations of the Vision Blocking Layer functions 
 * and its helpers.
 * @author Virenerus
 *
 */
public class VBLFunctions {
	public void drawVBL(Shape shape) {
		drawVBL(MapTool.getFrame().getCurrentZoneRenderer(),
				false,
				shape,
				true,
				2,
				null);
	}
	
	public void drawVBL(String map, Shape shape) {
		drawVBL(MapTool.getFrame().getZoneRenderer(map),
				false,
				shape,
				true,
				2,
				null);
	}
	
	public void drawVBL(Shape shape, boolean fill) {
		drawVBL(MapTool.getFrame().getCurrentZoneRenderer(),
				false,
				shape,
				fill,
				2,
				null);
	}
	
	public void drawVBL(String map, Shape shape, boolean fill) {
		drawVBL(MapTool.getFrame().getZoneRenderer(map),
				false,
				shape,
				fill,
				2,
				null);
	}
	
	public void drawVBL(Shape shape, int thickness) {
		drawVBL(MapTool.getFrame().getCurrentZoneRenderer(),
				false,
				shape,
				true,
				thickness,
				null);
	}
	
	public void drawVBL(String map, Shape shape, int thickness) {
		drawVBL(MapTool.getFrame().getZoneRenderer(map),
				false,
				shape,
				true,
				thickness,
				null);
	}
	
	public void drawVBL(Shape shape, boolean fill, int thickness) {
		drawVBL(MapTool.getFrame().getCurrentZoneRenderer(),
				false,
				shape,
				fill,
				thickness,
				null);
	}
	
	public void drawVBL(String map, Shape shape, boolean fill, int thickness) {
		drawVBL(MapTool.getFrame().getZoneRenderer(map),
				false,
				shape,
				fill,
				thickness,
				null);
	}
	
	public void drawVBL(Shape shape, AffineTransform transform) {
		drawVBL(MapTool.getFrame().getCurrentZoneRenderer(),
				false,
				shape,
				true,
				2,
				transform);
	}
	
	public void drawVBL(String map, Shape shape, AffineTransform transform) {
		drawVBL(MapTool.getFrame().getZoneRenderer(map),
				false,
				shape,
				true,
				2,
				transform);
	}
	
	public void drawVBL(Shape shape, boolean fill, AffineTransform transform) {
		drawVBL(MapTool.getFrame().getCurrentZoneRenderer(),
				false,
				shape,
				fill,
				2,
				transform);
	}
	
	public void drawVBL(String map, Shape shape, boolean fill, AffineTransform transform) {
		drawVBL(MapTool.getFrame().getZoneRenderer(map),
				false,
				shape,
				fill,
				2,
				transform);
	}
	
	public void drawVBL(Shape shape, int thickness, AffineTransform transform) {
		drawVBL(MapTool.getFrame().getCurrentZoneRenderer(),
				false,
				shape,
				true,
				thickness,
				transform);
	}
	
	public void drawVBL(String map, Shape shape, int thickness, AffineTransform transform) {
		drawVBL(MapTool.getFrame().getZoneRenderer(map),
				false,
				shape,
				true,
				thickness,
				transform);
	}
	
	public void drawVBL(Shape shape, boolean fill, int thickness, AffineTransform transform) {
		drawVBL(MapTool.getFrame().getCurrentZoneRenderer(),
				false,
				shape,
				fill,
				thickness,
				transform);
	}
	
	public void drawVBL(String map, Shape shape, boolean fill, int thickness, AffineTransform transform) {
		drawVBL(MapTool.getFrame().getZoneRenderer(map),
				false,
				shape,
				fill,
				thickness,
				transform);
	}
	
	public void eraseVBL(Shape shape) {
		drawVBL(MapTool.getFrame().getCurrentZoneRenderer(),
				true,
				shape,
				true,
				2,
				null);
	}
	
	public void eraseVBL(String map, Shape shape) {
		drawVBL(MapTool.getFrame().getZoneRenderer(map),
				true,
				shape,
				true,
				2,
				null);
	}
	
	public void eraseVBL(Shape shape, boolean fill) {
		drawVBL(MapTool.getFrame().getCurrentZoneRenderer(),
				true,
				shape,
				fill,
				2,
				null);
	}
	
	public void eraseVBL(String map, Shape shape, boolean fill) {
		drawVBL(MapTool.getFrame().getZoneRenderer(map),
				true,
				shape,
				fill,
				2,
				null);
	}
	
	public void eraseVBL(Shape shape, int thickness) {
		drawVBL(MapTool.getFrame().getCurrentZoneRenderer(),
				true,
				shape,
				true,
				thickness,
				null);
	}
	
	public void eraseVBL(String map, Shape shape, int thickness) {
		drawVBL(MapTool.getFrame().getZoneRenderer(map),
				true,
				shape,
				true,
				thickness,
				null);
	}
	
	public void eraseVBL(Shape shape, boolean fill, int thickness) {
		drawVBL(MapTool.getFrame().getCurrentZoneRenderer(),
				true,
				shape,
				fill,
				thickness,
				null);
	}
	
	public void eraseVBL(String map, Shape shape, boolean fill, int thickness) {
		drawVBL(MapTool.getFrame().getZoneRenderer(map),
				true,
				shape,
				fill,
				thickness,
				null);
	}
	
	public void eraseVBL(Shape shape, AffineTransform transform) {
		drawVBL(MapTool.getFrame().getCurrentZoneRenderer(),
				true,
				shape,
				true,
				2,
				transform);
	}
	
	public void eraseVBL(String map, Shape shape, AffineTransform transform) {
		drawVBL(MapTool.getFrame().getZoneRenderer(map),
				true,
				shape,
				true,
				2,
				transform);
	}
	
	public void eraseVBL(Shape shape, boolean fill, AffineTransform transform) {
		drawVBL(MapTool.getFrame().getCurrentZoneRenderer(),
				true,
				shape,
				fill,
				2,
				transform);
	}
	
	public void eraseVBL(String map, Shape shape, boolean fill, AffineTransform transform) {
		drawVBL(MapTool.getFrame().getZoneRenderer(map),
				true,
				shape,
				fill,
				2,
				transform);
	}
	
	public void eraseVBL(Shape shape, int thickness, AffineTransform transform) {
		drawVBL(MapTool.getFrame().getCurrentZoneRenderer(),
				true,
				shape,
				true,
				thickness,
				transform);
	}
	
	public void eraseVBL(String map, Shape shape, int thickness, AffineTransform transform) {
		drawVBL(MapTool.getFrame().getZoneRenderer(map),
				true,
				shape,
				true,
				thickness,
				transform);
	}
	
	public void eraseVBL(Shape shape, boolean fill, int thickness, AffineTransform transform) {
		drawVBL(MapTool.getFrame().getCurrentZoneRenderer(),
				true,
				shape,
				fill,
				thickness,
				transform);
	}
	
	public void eraseVBL(String map, Shape shape, boolean fill, int thickness, AffineTransform transform) {
		drawVBL(MapTool.getFrame().getZoneRenderer(map),
				true,
				shape,
				fill,
				thickness,
				transform);
	}
	
	private void drawVBL(ZoneRenderer renderer, boolean erase, Shape shape, boolean fill, float thickness, AffineTransform transform) {
		BasicStroke stroke = new BasicStroke(thickness);
		
		Area area;
		if(fill)
			area = new Area(shape);
		else
			area = new Area(stroke.createStrokedShape(shape));
		
		if (transform!=null && !transform.isIdentity())
			area.transform(transform);

		// Send to the engine to render
		if (erase) {
			renderer.getZone().removeTopology(area);
			MapTool.serverCommand().removeTopology(renderer.getZone().getId(), area);
		} else {
			renderer.getZone().addTopology(area);
			MapTool.serverCommand().addTopology(renderer.getZone().getId(), area);
		}
		renderer.repaint();
	}
}
