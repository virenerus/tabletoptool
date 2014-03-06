package com.t3.script.api.functions.player;

import java.util.ArrayList;
import java.util.List;

import com.t3.client.TabletopTool;
import com.t3.model.GUID;
import com.t3.model.Player;
import com.t3.model.Token;
import com.t3.script.api.TokenView;

public class PlayerFunctions {
	/**
	 * @return the token the local player has impersonated or null if there is none
	 */
	public TokenView getLocalImpersonatedToken() {
		GUID guid = TabletopTool.getFrame().getCommandPanel().getIdentityGUID();
		Token t;
		if (guid != null)
			t=TabletopTool.getFrame().findToken(guid);
		else
			t=TabletopTool.getFrame().getCurrentZoneRenderer().getZone().resolveToken(TabletopTool.getFrame().getCommandPanel().getIdentity());
		
		return t==null ? null : new TokenView(t);
	}
	
	/**
	 * @return if the local player has a token impersonated
	 */
	public boolean hasLocalImpersonatedToken() {
		return getLocalImpersonatedToken()!=null;
	}
	
	/**
	 * @return the name name of the local player
	 */
	public String getLocalName() {
		return TabletopTool.getPlayer().getName();
	}
	
	/**
	 * @return a list of all the player names
	 */
	public List<String> getPlayerNames() {
		List<Player> players=TabletopTool.getPlayerList();
		List<String> names=new ArrayList<String>(players.size());
		for(Player p:players)
			names.add(p.getName());
		return names;
	}
}
