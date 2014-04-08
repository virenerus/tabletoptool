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

import com.t3.client.TabletopTool;
import com.t3.model.Zone;

public class ZoneRendererFactory {
	public static ZoneRenderer newRenderer(Zone zone) {
		ZoneRenderer renderer = new ZoneRenderer(zone);
		if (TabletopTool.getFrame() != null) {
			renderer.addOverlay(TabletopTool.getFrame().getPointerOverlay());
		}
		return renderer;
	}
}
