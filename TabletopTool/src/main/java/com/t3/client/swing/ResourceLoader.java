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
package com.t3.client.swing;

import java.awt.Rectangle;
import java.util.StringTokenizer;

// This should really be in rplib
public class ResourceLoader {

	/**
	 * Rectangles are in the form x, y, width, height
	 */
	public static Rectangle loadRectangle(String rectString) {
		
		StringTokenizer strtok = new StringTokenizer(rectString, ",");
		if (strtok.countTokens() != 4) {
			throw new IllegalArgumentException("Could not load rectangle: '" + rectString + "', must be in the form x, y, w, h");
		}

		int x = Integer.parseInt(strtok.nextToken().trim());
		int y = Integer.parseInt(strtok.nextToken().trim());
		int w = Integer.parseInt(strtok.nextToken().trim());
		int h = Integer.parseInt(strtok.nextToken().trim());

		return new Rectangle(x, y, w, h);
	}
}
