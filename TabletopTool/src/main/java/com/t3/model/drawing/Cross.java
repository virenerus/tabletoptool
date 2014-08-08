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
package com.t3.model.drawing;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Area;

import com.t3.xstreamversioned.version.SerializationVersion;

/**
 * An Cross
 */
@SerializationVersion(0)
public class Cross extends AbstractDrawing {
    protected Point startPoint;
    protected Point endPoint;
    private transient java.awt.Rectangle bounds;
    
    public Cross(int startX, int startY, int endX, int endY) {

        startPoint = new Point(startX, startY);
        endPoint = new Point(endX, endY);
    }
    
    @Override
	public Area getArea() {
    	return new Area(getBounds());
    }

    /* (non-Javadoc)
	 * @see com.t3.model.drawing.Drawable#getBounds()
	 */
	@Override
	public java.awt.Rectangle getBounds() {

		if (bounds == null) {
	        int x = Math.min(startPoint.x, endPoint.x);
	        int y = Math.min(startPoint.y, endPoint.y);
	        int width = Math.abs(endPoint.x - startPoint.x);
	        int height = Math.abs(endPoint.y - startPoint.y);
	        
	        bounds = new java.awt.Rectangle(x, y, width, height);
		}
        
		return bounds;
	}
    
    public Point getStartPoint() {
        return startPoint;
    }
    
    public Point getEndPoint() {
        return endPoint;
    }

    @Override
	protected void draw(Graphics2D g) {
    	Object oldAA = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        //g.drawRect(minX, minY, width, height);
        
        g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        g.drawLine(startPoint.x, endPoint.y, endPoint.x, startPoint.y);
                
    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAA);
    }

    @Override
	protected void drawBackground(Graphics2D g) {
        int minX = Math.min(startPoint.x, endPoint.x);
        int minY = Math.min(startPoint.y, endPoint.y);
        
        int width = Math.abs(startPoint.x - endPoint.x);
        int height = Math.abs(startPoint.y - endPoint.y);
        
    	Object oldAA = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.fillRect(minX, minY, width, height);
    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAA);
    }
}
