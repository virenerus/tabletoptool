package net.rptools.maptool.script.mt2.functions;

import java.util.ArrayList;
import java.util.List;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.client.ui.zone.ZoneRenderer;
import net.rptools.maptool.script.mt2.MapView;

public class MapFunctions {
	public MapView getCurrentMap() {
		return new MapView(MapTool.getFrame().getCurrentZoneRenderer());
	}
	
	public void setCurrentMap(MapView map) {
		map.makeCurrentMap();
	}
	
	
	
	public List<MapView> getAllMaps() {
		List<ZoneRenderer> zrs = MapTool.getFrame().getZoneRenderers();
		List<MapView> maps=new ArrayList<MapView>(zrs.size());
		for(ZoneRenderer zr:zrs)
			maps.add(new MapView(zr));
		return maps;
	}
	
	public List<String> getMapNames() {
		List<ZoneRenderer> zrs = MapTool.getFrame().getZoneRenderers();
		List<String> maps=new ArrayList<String>(zrs.size());
		for(ZoneRenderer zr:zrs)
			maps.add(zr.getZone().getName());
		return maps;
	}
	
	public MapView getMap(String mapName) {
		return new MapView(MapTool.getFrame().getZoneRenderer(mapName));
	}
}
