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
package com.t3.client.ui.zone;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import com.t3.model.drawing.DrawnElement;

/**
 */
public interface DrawableRenderer {

	public void renderDrawables(Graphics g, List<DrawnElement> drawableList, Rectangle viewport, double scale);
	public void flush();
}
