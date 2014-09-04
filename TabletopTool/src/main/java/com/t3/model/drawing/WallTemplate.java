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

import java.util.List;

import com.t3.model.CellPoint;
import com.t3.model.ZonePoint;
import com.t3.xstreamversioned.version.SerializationVersion;

/**
 * A template that draws consecutive blocks
 * 
 * @author Jay
 */
@SerializationVersion(0)
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
