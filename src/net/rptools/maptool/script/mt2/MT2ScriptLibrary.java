package net.rptools.maptool.script.mt2;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.model.CellPoint;
import net.rptools.maptool.model.ZonePoint;
import net.rptools.maptool.script.ScriptLibrary;
import net.rptools.maptool.script.mt2.functions.DialogFunctions;
import net.rptools.maptool.script.mt2.functions.InfoFunctions;
import net.rptools.maptool.script.mt2.functions.MapFunctions;
import net.rptools.maptool.script.mt2.functions.ini.InitiativeFunctions;
import net.rptools.maptool.script.mt2.functions.player.PlayerFunctions;
import net.rptools.maptool.script.mt2.functions.token.TokenLocation;

//FIXME make this package into a PLUG-IN
public class MT2ScriptLibrary implements ScriptLibrary {
	
	public final InitiativeFunctions ini;
	public final InfoFunctions info;
	public final PlayerFunctions player;
	public final MapFunctions map;
	public final DialogFunctions dialog;

	public MT2ScriptLibrary() {
		this.ini=new InitiativeFunctions();
		this.info=new InfoFunctions();
		this.player=new PlayerFunctions();
		this.map=new MapFunctions();
		this.dialog=new DialogFunctions();
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

}
