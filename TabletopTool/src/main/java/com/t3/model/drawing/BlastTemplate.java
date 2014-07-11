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

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

import com.t3.client.TabletopTool;
import com.t3.model.ZonePoint;
import com.t3.xstreamversioned.SerializationVersion;

/**
 * The blast template draws a square for D&D 4e
 * 
 * @author jgorrell
 * @version $Revision: $ $Date: $ $Author: $
 */
@SerializationVersion(0)
public class BlastTemplate extends ConeTemplate {

    /*---------------------------------------------------------------------------------------------
     * Instance Variables
     *-------------------------------------------------------------------------------------------*/

    /**
     * Renderer for the blast. The {@link Shape} is just a rectangle. 
     */
    private ShapeDrawable renderer = new ShapeDrawable(new Rectangle());
    
    private int offsetX;
    private int offsetY;
    
    /*---------------------------------------------------------------------------------------------
     * Instance Methods
     *-------------------------------------------------------------------------------------------*/

    /**
     * This methods adjusts the rectangle in the renderer to match the new radius, vertex, or location.
     */
    private void adjustRectangle() {
        if (getZoneId() == null) return;
        int gridSize = TabletopTool.getCampaign().getZone(getZoneId()).getGrid().getSize();
        int size = getRadius() * gridSize;

        Rectangle r = (Rectangle)renderer.getShape();
        r.setBounds(getVertex().x, getVertex().y, size, size);

        r.x += offsetX * gridSize;
        r.y += offsetY * gridSize;
    }

    /*---------------------------------------------------------------------------------------------
     * Overridden *Template Methods
     *-------------------------------------------------------------------------------------------*/
    
    @Override
    public Rectangle getBounds() {
    	Rectangle r = new Rectangle(renderer.getShape().getBounds());
    	// We don't know pen width, so add some padding to account for it
    	r.x -= 5;
    	r.y -= 5;
    	r.width += 10;
    	r.height += 10;
    	return r;
    }

    /**
     * Defines the blast based on the specified square
     * 
     * @param relX The X coordinate of the control square relative to the origin square
     * @param relY The Y coordinate of the control square relative to the origin square
     */
    public void setControlCellRelative(int relX, int relY) {

    	relX = Math.max(Math.min(relX, MAX_RADIUS), -MAX_RADIUS);
    	relY = Math.max(Math.min(relY, MAX_RADIUS), -MAX_RADIUS);
    	
    	int radius = Math.max(Math.abs(relX), Math.abs(relY));
    	// Number of cells along axis of smaller offset we need to shift the square in order to "center" the blast
        int centerOffset = -(radius / 2);
        // Smallest delta we can apply to centerOffset and still have valid placement
        int lowerBound = -((radius + 1) / 2);
        // Largest delta we can apply to centerOffset and still have valid placement
        int upperBound = (radius / 2) + 1;

        setRadius(radius);
        // The larger magnitude offset determines size and gross positioning, the smaller determines fine positioning
        if (Math.abs(relX) > Math.abs(relY)) {
        	if (relX > 0) {
        		offsetX = 1;
        	} else {
        		offsetX = -radius;
        	}
        	offsetY = centerOffset + Math.min(Math.max(lowerBound, relY), upperBound);
        } else {
        	if (relY > 0) {
        		offsetY = 1;
        	} else {
        		offsetY = -radius;
        	}
        	offsetX = centerOffset + Math.min(Math.max(lowerBound, relX), upperBound);
        }
        adjustRectangle();
    }

    /**
     * @see com.t3.model.drawing.AbstractTemplate#setVertex(com.t3.model.ZonePoint)
     */
    @Override
    public void setVertex(ZonePoint vertex) {
        super.setVertex(vertex);
        adjustRectangle();
    }

    /**
     * @see com.t3.model.drawing.AbstractTemplate#getDistance(int, int)
     */
    @Override
    public int getDistance(int x, int y) {
        return Math.max(x, y);
    }

    /*---------------------------------------------------------------------------------------------
     * Overridden AbstractDrawing Methods
     *-------------------------------------------------------------------------------------------*/
    
    /**
     * @see com.t3.model.drawing.AbstractDrawing#draw(java.awt.Graphics2D)
     */
    @Override
    protected void draw(Graphics2D g) {
      renderer.draw(g);
    }

    /**
     * @see com.t3.model.drawing.AbstractDrawing#drawBackground(java.awt.Graphics2D)
     */
    @Override
    protected void drawBackground(Graphics2D g) {    
      Composite old = g.getComposite();
      if (old != AlphaComposite.Clear)
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, DEFAULT_BG_ALPHA));
      renderer.drawBackground(g);
      g.setComposite(old);
    }
}
