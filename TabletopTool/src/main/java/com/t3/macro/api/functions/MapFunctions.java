package com.t3.macro.api.functions;

import java.util.ArrayList;
import java.util.List;

import com.t3.client.TabletopTool;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.macro.api.views.MapView;

public class MapFunctions {
	
	/**
	 * @return the currently visible map
	 */
	public MapView getCurrent() {
		return new MapView(TabletopTool.getFrame().getCurrentZoneRenderer());
	}
	
	/**
	 * This switches the current map to the given one
	 * @param map the map to switch to 
	 */
	public void setCurrentMap(MapView map) {
		map.makeCurrentMap();
	}
	
	/**
	 * @return a list of all hte maps
	 */
	public List<MapView> getAllMaps() {
		List<ZoneRenderer> zrs = TabletopTool.getFrame().getZoneRenderers();
		List<MapView> maps=new ArrayList<MapView>(zrs.size());
		for(ZoneRenderer zr:zrs)
			maps.add(new MapView(zr));
		return maps;
	}
	
	/**
	 * @return a list of the names of all the maps
	 */
	public List<String> getMapNames() {
		List<ZoneRenderer> zrs = TabletopTool.getFrame().getZoneRenderers();
		List<String> maps=new ArrayList<String>(zrs.size());
		for(ZoneRenderer zr:zrs)
			maps.add(zr.getZone().getName());
		return maps;
	}
	
	/**
	 * This looks for a map using its name.
	 * @param mapName the name of the map you are looking for
	 * @return the map or null if none was found
	 */
	public MapView getMap(String mapName) {
		ZoneRenderer map = TabletopTool.getFrame().getZoneRenderer(mapName);
		return map==null?null:new MapView(map);
	}
}
