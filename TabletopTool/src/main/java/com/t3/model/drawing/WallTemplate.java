/*
 *  This software copyright by various authors including the RPTools.net
 *  development team, and licensed under the LGPL Version 3 or, at your
 *  option, any later version.
 *
 *  Portions of this software were originally covered under the Apache
 *  Software License, Version 1.1 or Version 2.0.
 *
 *  See the file LICENSE elsewhere in this distribution for license details.
 */

package com.t3.model.drawing;

import java.util.List;

import com.t3.model.CellPoint;
import com.t3.model.ZonePoint;

/**
 * A template that draws consecutive blocks
 * 
 * @author Jay
 */
public class WallTemplate extends LineTemplate {
    /**
     * Set the path vertex, it isn't needed by the wall template but the superclass needs it to paint.
     */
    public WallTemplate() {
        setPathVertex(new ZonePoint(0, 0));
    }
    
    /**
     * @see com.t3.model.drawing.AbstractTemplate#getRadius()
     */
    @Override
    public int getRadius() {
        return getPath() == null ? 0 : getPath().size();
    }
    
    /**
     * @see com.t3.model.drawing.LineTemplate#setRadius(int)
     */
    @Override
    public void setRadius(int squares) {
        // Do nothing, calculated from path length
    }
    
    /**
     * @see com.t3.model.drawing.LineTemplate#setVertex(com.t3.model.ZonePoint)
     */
    @Override
    public void setVertex(ZonePoint vertex) {
        ZonePoint v = getVertex();
        v.x = vertex.x;
        v.y = vertex.y;
    }
    
    /**
     * @see com.t3.model.drawing.LineTemplate#calcPath()
     */
    @Override
    protected List<CellPoint> calcPath() {
        return getPath(); // Do nothing, path is set by tool.
    }
}
