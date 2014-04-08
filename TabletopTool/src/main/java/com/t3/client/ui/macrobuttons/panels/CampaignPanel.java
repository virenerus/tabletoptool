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

import java.util.ArrayList;
import java.util.List;

import com.t3.client.TabletopTool;
import com.t3.client.ui.macrobuttons.buttongroups.AbstractButtonGroup;
import com.t3.model.MacroButtonProperties;

public class CampaignPanel extends AbstractMacroPanel {
	
	public CampaignPanel() {
		setPanelClass("CampaignPanel");
		addMouseListener(this);
		init();
	}

	private void init() {
		if ( TabletopTool.getPlayer() == null || TabletopTool.getPlayer().isGM() || TabletopTool.getServerPolicy().playersReceiveCampaignMacros()) {
			addArea(TabletopTool.getCampaign().getMacroButtonPropertiesArray(),"");
		}
	}
	
	@Override
	public void reset() {
		clear();
		init();
	}

	public static void deleteButtonGroup(String macroGroup) {
		AbstractButtonGroup.clearHotkeys(TabletopTool.getFrame().getCampaignPanel(), macroGroup);
		List<MacroButtonProperties> campProps = TabletopTool.getCampaign().getMacroButtonPropertiesArray();
		List<MacroButtonProperties> startingProps = new ArrayList<MacroButtonProperties>(TabletopTool.getCampaign().getMacroButtonPropertiesArray());
		campProps.clear();
		for(MacroButtonProperties nextProp : startingProps) {
			if(!macroGroup.equals(nextProp.getGroup())) {
				TabletopTool.getCampaign().saveMacroButtonProperty(nextProp);
			}
		}
		TabletopTool.getFrame().getCampaignPanel().reset();
	}
	
	public static void clearPanel() {
		AbstractMacroPanel.clearHotkeys(TabletopTool.getFrame().getCampaignPanel());
		TabletopTool.getCampaign().getMacroButtonPropertiesArray().clear();
		TabletopTool.getFrame().getCampaignPanel().reset();
	}
}

