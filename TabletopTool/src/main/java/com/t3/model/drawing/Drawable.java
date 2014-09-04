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
import java.awt.geom.Area;

import com.t3.guid.GUID;
import com.t3.model.Zone;
import com.t3.xstreamversioned.version.SerializationVersion;

/**
 * @author drice
 */
public interface Drawable {
	public void draw(Graphics2D g, Pen pen);

	public java.awt.Rectangle getBounds();

	public Area getArea();

	public GUID getId();

	public Zone.Layer getLayer();

	public void setLayer(Zone.Layer layer);
}
