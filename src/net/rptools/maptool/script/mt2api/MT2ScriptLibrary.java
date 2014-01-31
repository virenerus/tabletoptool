package net.rptools.maptool.script.mt2api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.groovy.syntax.ParserException;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.language.I18N;
import net.rptools.maptool.model.CellPoint;
import net.rptools.maptool.model.TokenProperty;
import net.rptools.maptool.model.ZonePoint;
import net.rptools.maptool.script.ScriptLibrary;
import net.rptools.maptool.script.mt2api.functions.DialogFunctions;
import net.rptools.maptool.script.mt2api.functions.InfoFunctions;
import net.rptools.maptool.script.mt2api.functions.MapFunctions;
import net.rptools.maptool.script.mt2api.functions.PathFunctions;
import net.rptools.maptool.script.mt2api.functions.ini.InitiativeFunctions;
import net.rptools.maptool.script.mt2api.functions.player.PlayerFunctions;
import net.rptools.maptool.script.mt2api.functions.token.TokenLocation;

//FIXME make this package into a PLUG-IN
public class MT2ScriptLibrary implements ScriptLibrary {
	
	public final InitiativeFunctions ini;
	public final InfoFunctions info;
	public final PlayerFunctions player;
	public final MapFunctions map;
	public final DialogFunctions dialog;
	public final PathFunctions path;

	public MT2ScriptLibrary() {
		this.ini=new InitiativeFunctions();
		this.info=new InfoFunctions();
		this.player=new PlayerFunctions();
		this.map=new MapFunctions();
		this.dialog=new DialogFunctions();
		this.path=new PathFunctions();
	}
	
	public String getVariableName() {
		return "MT2";
	}

	public void print(int i) {
		print(Integer.toString(i));
	}
	
	public void print(float f) {
		print(Float.toString(f));
	}
	
	public void print(boolean b) {
		print(Boolean.toString(b));
	}
	
	public void print(Object o) {
		if(o==null)
			MapTool.addGlobalMessage("null");
		else
			MapTool.addGlobalMessage(o.toString());
	}
	
	public void goTo(TokenView token) {
		TokenLocation tl=token.getLocation(false);
		MapTool.getFrame().getCurrentZoneRenderer().centerOn(new ZonePoint(tl.getX(), tl.getY()));
	}
	
	public void goTo(int x, int y) {
		goTo(x,y,true);
	}
	
	public void goTo(int x, int y, boolean gridUnit) {
		if(gridUnit)
			MapTool.getFrame().getCurrentZoneRenderer().centerOn(new CellPoint(x, y));
		else
			MapTool.getFrame().getCurrentZoneRenderer().centerOn(new ZonePoint(x, y));
	}
	
	/**
	 * Gets all the property names.
	 * @return a string list containing the property names.
	 */
	public List<String> getAllPropertyNames() {
		Map<String, List<TokenProperty>> pmap = MapTool.getCampaign().getCampaignProperties().getTokenTypeMap();
		ArrayList<String> namesList = new ArrayList<String>();

		for (Entry<String, List<TokenProperty>> entry : pmap.entrySet()) {
			for (TokenProperty tp : entry.getValue()) {
				namesList.add(tp.getName());
			}
		}
		return namesList;
	}
	
	/**
	 * Gets all the property names for the specified type.
	 * 
	 * @param type  The type of property.
	 * @return a string list containing the property names.
	 */
	public List<String> getAllPropertyNames(String type) {
		List<TokenProperty> props = MapTool.getCampaign().getCampaignProperties().getTokenPropertyList(type);
		ArrayList<String> namesList = new ArrayList<String>();
		for (TokenProperty tp : props) {
			namesList.add(tp.getName());
		}
		return namesList;
	}

}
