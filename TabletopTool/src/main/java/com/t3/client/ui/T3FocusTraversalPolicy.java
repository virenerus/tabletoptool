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
package com.t3.client.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;

import com.t3.client.TabletopTool;

public class T3FocusTraversalPolicy extends FocusTraversalPolicy {

	@Override
	public Component getComponentAfter(Container aContainer,
			Component aComponent) {
		return TabletopTool.getFrame().getCurrentZoneRenderer();
	}

	@Override
	public Component getComponentBefore(Container aContainer,
			Component aComponent) {
		return TabletopTool.getFrame().getCurrentZoneRenderer();
	}

	@Override
	public Component getFirstComponent(Container aContainer) {
		return TabletopTool.getFrame().getCurrentZoneRenderer();
	}

	@Override
	public Component getLastComponent(Container aContainer) {
		return TabletopTool.getFrame().getCurrentZoneRenderer();
	}

	@Override
	public Component getDefaultComponent(Container aContainer) {
		return TabletopTool.getFrame().getCurrentZoneRenderer();
	}

}
