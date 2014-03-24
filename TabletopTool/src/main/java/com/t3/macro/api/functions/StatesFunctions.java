package com.t3.macro.api.functions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.t3.client.TabletopTool;
import com.t3.client.ui.token.BooleanTokenOverlay;
import com.t3.client.ui.token.ImageTokenOverlay;
import com.t3.language.I18N;
import com.t3.macro.MacroException;

public class StatesFunctions {
	public List<String> getAll() {
		return new ArrayList<String>(TabletopTool.getCampaign().getTokenStatesMap().keySet());
	}
	
	public String getImage(String stateName, int size) throws MacroException {
		return getImage(stateName)+"-"+Math.max(Math.min(size, 500),1);
	}
	
	public String getImage(String stateName) throws MacroException {
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
}
