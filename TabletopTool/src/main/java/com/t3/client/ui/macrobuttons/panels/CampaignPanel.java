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

