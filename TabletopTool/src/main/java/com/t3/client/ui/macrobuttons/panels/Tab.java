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
package com.t3.client.ui.macrobuttons.panels;

public enum Tab {
	GLOBAL(0, "Global"),
	CAMPAIGN(1, "Campaign"),
	SELECTED(2, "Selection"),
	IMPERSONATED(3, "Impersonated");

	public final int index;
	public final String title;

	Tab(int index, String title) {
		this.index = index;
		this.title = title;
	}
}
