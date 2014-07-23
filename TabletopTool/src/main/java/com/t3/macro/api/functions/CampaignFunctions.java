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
package com.t3.macro.api.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

import com.t3.client.TabletopTool;
import com.t3.client.ui.macrobuttons.buttons.MacroButtonPrefs;
import com.t3.client.ui.token.BooleanTokenOverlay;
import com.t3.client.ui.token.ImageTokenOverlay;
import com.t3.language.I18N;
import com.t3.macro.MacroException;
import com.t3.macro.api.views.MacroButtonView;
import com.t3.model.MacroButtonProperties;
import com.t3.model.properties.TokenProperty;

public class CampaignFunctions {
	/**
	 * @return all token states defined in this campaign 
	 */
	public List<String> getStates() {
		return new ArrayList<String>(TabletopTool.getCampaign().getTokenStatesMap().keySet());
	}
	
	/**
	 * @param stateName the state which image you want
	 * @param size the image size you want the image url to reference
	 * @return the image associated with a state
	 * @throws MacroException when the state does not exist or has no image
	 */
	public String getStateImage(String stateName, int size) throws MacroException {
		return getStateImage(stateName)+"-"+Math.max(Math.min(size, 500),1);
	}
	
	/**
	 * @param stateName the state which image you want
	 * @return the image associated with a state
	 * @throws MacroException when the state does not exist or has no image
	 */
	public String getStateImage(String stateName) throws MacroException {
		BooleanTokenOverlay over = TabletopTool.getCampaign().getTokenStatesMap().get(stateName);
		if (over == null) {
			throw new MacroException(I18N.getText("macro.function.stateImage.unknownState", "getStateImage()", stateName ));
		}
		if (over instanceof ImageTokenOverlay) {
			StringBuilder assetId = new StringBuilder("asset://");
			assetId.append(((ImageTokenOverlay)over).getAssetId().toString());
			return assetId.toString();
		} else {
			throw new MacroException(I18N.getText("macro.function.stateImage.notImage", "getStateImage()", stateName));
		}
	}
	
	/**
	 * Gets all the property names.
	 * @return a string list containing the property names.
	 */
	public List<String> getAllPropertyNames() {
		Map<String, List<TokenProperty>> pmap = TabletopTool.getCampaign().getCampaignProperties().getTokenTypeMap();
		ArrayList<String> namesList = new ArrayList<String>();

		for (Entry<String, List<TokenProperty>> entry : pmap.entrySet()) {
			for (TokenProperty tp : entry.getValue()) {
				namesList.add(tp.getName());
			}
		}
		return namesList;
	}
	
	/**
	 * Gets all the property names for the specified type.
	 * 
	 * @param type  The type of property.
	 * @return a string list containing the property names.
	 */
	public List<String> getAllPropertyNames(String type) {
		List<TokenProperty> props = TabletopTool.getCampaign().getCampaignProperties().getTokenPropertyList(type);
		ArrayList<String> namesList = new ArrayList<String>();
		for (TokenProperty tp : props) {
			namesList.add(tp.getName());
		}
		return namesList;
	}
	
	/**
	 * This method will return a macro button from the global panel.
	 * @param macroName the name of the macro you want the button for
	 * @return the macro button or null if it was not found
	 */
	public MacroButtonView getGlobalMacro(String macroName) {
		for(MacroButtonProperties mbp:MacroButtonPrefs.getButtonProperties()) {
			if(Objects.equals(mbp.getLabel(), macroName))
				return new MacroButtonView(mbp);
		}
		return null;
	}
	
	/**
	 * This method will return a macro button from the campaign panel.
	 * @param macroName the name of the macro you want the button for
	 * @return the macro button or null if it was not found
	 */
	public MacroButtonView getCampaignMacro(String macroName) {
		for(MacroButtonProperties mbp:TabletopTool.getCampaign().getMacroButtonPropertiesArray()) {
			if(Objects.equals(mbp.getLabel(), macroName))
				return new MacroButtonView(mbp);
		}
		return null;
	}
}
