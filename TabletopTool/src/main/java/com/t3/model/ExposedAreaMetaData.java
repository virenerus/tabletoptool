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

import java.awt.geom.Area;

import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(0)
public class ExposedAreaMetaData {
	private Area exposedAreaHistory;

	public ExposedAreaMetaData() {
		exposedAreaHistory = new Area();
	}

	public ExposedAreaMetaData(Area area) {
		exposedAreaHistory = new Area(area);
	}

	public Area getExposedAreaHistory() {
//		if (exposedAreaHistory == null) {
//			exposedAreaHistory = new Area();
//		}
		return exposedAreaHistory;
	}

	public void addToExposedAreaHistory(Area newArea) {
		if (newArea != null && !newArea.isEmpty()) {
			exposedAreaHistory.add(newArea);
		}
	}

	public void removeExposedAreaHistory(Area newArea) {
		if (newArea != null && !newArea.isEmpty()) {
			exposedAreaHistory.subtract(newArea);
		}
	}

	public void clearExposedAreaHistory() {
		exposedAreaHistory = new Area();
	}
}
