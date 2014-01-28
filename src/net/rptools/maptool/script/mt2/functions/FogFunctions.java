package net.rptools.maptool.script.mt2.functions;

import java.util.Set;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.client.ui.zone.FogUtil;
import net.rptools.maptool.client.ui.zone.ZoneRenderer;
import net.rptools.maptool.model.GUID;
import net.rptools.maptool.script.MT2ScriptException;

public class FogFunctions {
	public void exposePCArea() {
		FogUtil.exposePCArea(MapTool.getFrame().getCurrentZoneRenderer());
	}

	public void exposePCArea(String map) throws MT2ScriptException {
		ZoneRenderer zr = MapTool.getFrame().getZoneRenderer(map);
		if(zr==null)
			throw new MT2ScriptException("The map '"+map+"' does not exist.");
		FogUtil.exposePCArea(zr);
	}
	
	public void exposeVisibleArea() {
		ZoneRenderer zr = MapTool.getFrame().getCurrentZoneRenderer();
		Set<GUID> tokens = zr.getSelectedTokenSet();
		Set<GUID> ownedTokens = zr.getOwnedTokens(tokens);
		FogUtil.exposeVisibleArea(zr, ownedTokens);
	}

	public void exposeVisibleArea(String map) throws MT2ScriptException {
		ZoneRenderer zr = MapTool.getFrame().getZoneRenderer(map);
		if(zr==null)
			throw new MT2ScriptException("The map '"+map+"' does not exist.");
		Set<GUID> tokens = zr.getSelectedTokenSet();
		Set<GUID> ownedTokens = zr.getOwnedTokens(tokens);
		FogUtil.exposeVisibleArea(zr, ownedTokens);
	}
}
