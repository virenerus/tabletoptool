package net.rptools.maptool.script.mt2.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.client.ui.zone.ZoneRenderer;
import net.rptools.maptool.model.GUID;
import net.rptools.maptool.model.Token;
import net.rptools.maptool.model.Zone;
import net.rptools.maptool.script.mt2.TokenView;
import net.rptools.maptool.script.mt2.tokenfilter.AllFilter;
import net.rptools.maptool.script.mt2.tokenfilter.ExposedFilter;
import net.rptools.maptool.script.mt2.tokenfilter.NPCFilter;
import net.rptools.maptool.script.mt2.tokenfilter.OwnedFilter;
import net.rptools.maptool.script.mt2.tokenfilter.PCFilter;
import net.rptools.maptool.script.mt2.tokenfilter.StateFilter;

public class TokenFunctions {
	public TokenView findToken(String identifier) {
		Zone zone = MapTool.getFrame().getCurrentZoneRenderer().getZone();
		return new TokenView(zone.resolveToken(identifier));
	}
	
	
	public TokenView findToken(String identifier, String zoneName) {
		if (zoneName == null || zoneName.length() == 0) {
			Zone zone = MapTool.getFrame().getCurrentZoneRenderer().getZone();
			Token token = zone.resolveToken(identifier);
			return new TokenView(token);
		} else {
			List<ZoneRenderer> zrenderers = MapTool.getFrame().getZoneRenderers();
			for (ZoneRenderer zr : zrenderers) {
				Zone zone = zr.getZone();
				if (zone.getName().equalsIgnoreCase(zoneName)) {
					Token token = zone.resolveToken(identifier);
					if (token != null) {
						return new TokenView(token);
					}
				}
			}
		}
		return null;
	}
	
	private List<TokenView> makeTokenViewList(List<Token> list) {
		ArrayList<TokenView> l=new ArrayList<TokenView>(list.size());
		for(Token t:list)
			l.add(new TokenView(t));
		return l;
	}
	
	public List<TokenView> getTokensTokens() {
		return makeTokenViewList(MapTool.getFrame().getCurrentZoneRenderer().getZone().getTokensFiltered(new AllFilter()));
	}
	
	public List<TokenView> getSelectedTokens() {
		return makeTokenViewList(MapTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList());
	}
	
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
	
	
	public List<TokenView> getExposedTokens() {
		Zone zone = MapTool.getFrame().getCurrentZoneRenderer().getZone();
		return makeTokenViewList(zone.getTokensFiltered(new ExposedFilter(zone)));
	}
	
	
	public List<TokenView> getPCTokens() {
		return makeTokenViewList(MapTool.getFrame().getCurrentZoneRenderer().getZone().getTokensFiltered(new PCFilter()));
	}
	
	public List<TokenView> getNPCTokens() {
		return makeTokenViewList(MapTool.getFrame().getCurrentZoneRenderer().getZone().getTokensFiltered(new NPCFilter()));
	}
	
	public List<TokenView> getTokensWithState(String state) {
		return makeTokenViewList(MapTool.getFrame().getCurrentZoneRenderer().getZone().getTokensFiltered(new StateFilter(state)));
	}
	
	public List<TokenView> getOwnedTokens(String playerID) {
		return makeTokenViewList(MapTool.getFrame().getCurrentZoneRenderer().getZone().getTokensFiltered(new OwnedFilter(playerID)));
	}

	public ArrayList<TokenView> getVisibleTokens() {
		Zone zone=MapTool.getFrame().getCurrentZoneRenderer().getZone();
		Set<GUID> visibleSet=MapTool.getFrame().getCurrentZoneRenderer().getVisibleTokenSet();
		ArrayList<TokenView> tokenList=new ArrayList<TokenView>(visibleSet.size());
		for (GUID id : visibleSet)
			tokenList.add(new TokenView(zone.getToken(id)));
		return tokenList;
	}
	
	public void selectTokens(Collection<TokenView> tokens, boolean cumulative) {
		ZoneRenderer zr = MapTool.getFrame().getCurrentZoneRenderer();

		List<GUID> guids=new ArrayList<GUID>(tokens.size());
		for(TokenView t:tokens)
			guids.add(t.getId());
		if(!cumulative)
			zr.clearSelectedTokens();
		zr.selectTokens(guids);
	}
	
	public void deselectAllTokens() {
		MapTool.getFrame().getCurrentZoneRenderer().clearSelectedTokens();
	}
	
	public void deselectTokens(Collection<TokenView> tokens) {
		ZoneRenderer zr = MapTool.getFrame().getCurrentZoneRenderer();
		for(TokenView t:tokens)
			zr.deselectToken(t.getId());
	}
}
