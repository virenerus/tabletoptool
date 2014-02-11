package net.rptools.maptool.script.mt2api.functions.player;

import java.util.ArrayList;
import java.util.List;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.model.GUID;
import net.rptools.maptool.model.Player;
import net.rptools.maptool.script.mt2api.TokenView;

public class PlayerFunctions {
	public TokenView getImpersonatedToken() {
		GUID guid = MapTool.getFrame().getCommandPanel().getIdentityGUID();
		if (guid != null)
			return new TokenView(MapTool.getFrame().findToken(guid));
		else
			return new TokenView(MapTool.getFrame().getCurrentZoneRenderer().getZone().resolveToken(MapTool.getFrame().getCommandPanel().getIdentity()));
	}
	
	public boolean hasImpersonatedToken() {
		return getImpersonatedToken()!=null;
	}
	
	public String getPlayer() {
		return MapTool.getPlayer().getName();
	}
	
	public List<String> getPlayers() {
		List<Player> players=MapTool.getPlayerList();
		List<String> names=new ArrayList<String>(players.size());
		for(Player p:players)
			names.add(p.getName());
		return names;
	}
}
