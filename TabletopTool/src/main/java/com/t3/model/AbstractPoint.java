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

import com.t3.xstreamversioned.SerializationVersion;

@SerializationVersion(0)
public abstract class AbstractPoint implements Cloneable {

    public int x;
    public int y;
    
    public AbstractPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void translate(int dx, int dy) {
        x += dx;
        y += dy;
    }
    
    @Override
	public boolean equals(Object o) {
        if (!(o instanceof AbstractPoint)) return false;
    	AbstractPoint p = (AbstractPoint) o;
    	
    	return p.x == x && p.y == y;
    }

    @Override
	public int hashCode() {
    	return new String(x+"-"+y).hashCode();
    }

    @Override
	public String toString() {
        return "[" + x + "," + y + "]";
    }
    
    @Override
	public AbstractPoint clone() {
    	try {
    	    return (AbstractPoint) super.clone();
    	} catch (CloneNotSupportedException e) {
    	    // this shouldn't happen, since we are Cloneable
    	    throw new InternalError();
    	}
    }
}
