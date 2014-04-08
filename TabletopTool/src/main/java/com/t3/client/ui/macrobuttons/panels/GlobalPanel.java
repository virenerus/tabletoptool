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

import java.util.List;

import com.t3.client.TabletopTool;
import com.t3.client.ui.macrobuttons.buttongroups.AbstractButtonGroup;
import com.t3.client.ui.macrobuttons.buttons.MacroButtonPrefs;
import com.t3.model.MacroButtonProperties;

public class GlobalPanel extends AbstractMacroPanel {
	public GlobalPanel() {
		super();
		setPanelClass("GlobalPanel");
		addMouseListener(this);
		init();
	}

	private void init() {
		List<MacroButtonProperties> properties = MacroButtonPrefs.getButtonProperties();
		addArea(properties, "");
	}

	@Override
	public void reset() {
		clear();
		init();
	}
	
	public static void deleteButtonGroup(String macroGroup) {
		AbstractButtonGroup.clearHotkeys(TabletopTool.getFrame().getGlobalPanel(), macroGroup);
		for (MacroButtonProperties nextProp : MacroButtonPrefs.getButtonProperties()) {
			if (macroGroup.equals(nextProp.getGroup())) {
				MacroButtonPrefs.delete(nextProp);
			}
		}
		TabletopTool.getFrame().getGlobalPanel().reset();
	}

	public static void clearPanel() {
		MacroButtonPrefs.deletePanel();
		AbstractMacroPanel.clearHotkeys(TabletopTool.getFrame().getGlobalPanel());
		TabletopTool.getFrame().getGlobalPanel().reset();
	}
}
