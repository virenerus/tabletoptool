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

import junit.framework.TestCase;

import com.t3.client.ScreenPoint;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.client.ui.zone.ZoneRendererFactory;

public class TestScreenPoint extends TestCase {
	public void testConversion() throws Exception {
		ZoneRenderer renderer = ZoneRendererFactory.newRenderer(new Zone());
		renderer.moveViewBy(-100, -100);

		for (int i = -10; i < 10; i++) {
			for (int j = -10; j < 10; j++) {
				ZonePoint zp = new ZonePoint(i, j);
				assertEquals(zp, ScreenPoint.fromZonePoint(renderer, zp).convertToZone(renderer));
			}
		}
	}
}
