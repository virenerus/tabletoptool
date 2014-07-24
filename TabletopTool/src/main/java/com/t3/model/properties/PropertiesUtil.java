package com.t3.model.properties;

import com.t3.client.TabletopTool;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.model.Token;

public class PropertiesUtil {
	public static void migrateAllTokenProperties() {
		for(ZoneRenderer zr:TabletopTool.getFrame().getZoneRenderers()) {
			for(Token t:zr.getZone().getTokens()) {
				migrateTokenProperties(t);
				TabletopTool.serverCommand().putToken(zr.getZone().getId(), t);
			}
		}
	}

	private static void migrateTokenProperties(Token t) {
		// TODO Auto-generated method stub
		// update all properties of the given token
		// check if they can be transferred from their current types to the required types
	}
}
