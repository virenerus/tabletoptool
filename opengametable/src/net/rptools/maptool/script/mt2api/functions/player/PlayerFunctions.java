package net.rptools.maptool.script.mt2api.functions.player;

import java.util.ArrayList;
import java.util.List;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.model.GUID;
import net.rptools.maptool.model.Player;
import net.rptools.maptool.model.Token;
import net.rptools.maptool.script.mt2api.TokenView;

public class PlayerFunctions {
	/**
	 * @return the token the local player has impersonated or null if there is none
	 */
	public TokenView getLocalImpersonatedToken() {
		GUID guid = MapTool.getFrame().getCommandPanel().getIdentityGUID();
		Token t;
		if (guid != null)
			t=MapTool.getFrame().findToken(guid);
		else
			t=MapTool.getFrame().getCurrentZoneRenderer().getZone().resolveToken(MapTool.getFrame().getCommandPanel().getIdentity());
		
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
		return MapTool.getPlayer().getName();
	}
	
	/**
	 * @return a list of all the player names
	 */
	public List<String> getPlayerNames() {
		List<Player> players=MapTool.getPlayerList();
		List<String> names=new ArrayList<String>(players.size());
		for(Player p:players)
			names.add(p.getName());
		return names;
	}
}
