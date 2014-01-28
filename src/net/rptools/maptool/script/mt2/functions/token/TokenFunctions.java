package net.rptools.maptool.script.mt2.functions.token;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.model.GUID;
import net.rptools.maptool.script.mt2.TokenView;

public class TokenFunctions {
	public TokenView getImpersonatedToken() {
		GUID guid = MapTool.getFrame().getCommandPanel().getIdentityGUID();
		if (guid != null)
			return new TokenView(MapTool.getFrame().getCurrentZoneRenderer().getZone().getToken(guid));
		else
			return new TokenView(MapTool.getFrame().getCurrentZoneRenderer().getZone().resolveToken(MapTool.getFrame().getCommandPanel().getIdentity()));
	}
	
	public boolean hasImpersonatedToken() {
		return getImpersonatedToken()!=null;
	}
}
