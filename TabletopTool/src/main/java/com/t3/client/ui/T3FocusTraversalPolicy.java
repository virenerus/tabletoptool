/*
 *  This software copyright by various authors including the RPTools.net
 *  development team, and licensed under the LGPL Version 3 or, at your
 *  option, any later version.
 *
 *  Portions of this software were originally covered under the Apache
 *  Software License, Version 1.1 or Version 2.0.
 *
 *  See the file LICENSE elsewhere in this distribution for license details.
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
