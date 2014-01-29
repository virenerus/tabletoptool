package net.rptools.maptool.script.mt2;

import java.util.ArrayList;
import java.util.List;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.model.Player;
import net.rptools.maptool.script.ScriptLibrary;
import net.rptools.maptool.script.mt2.functions.InfoFunctions;
import net.rptools.maptool.script.mt2.functions.MapFunctions;
import net.rptools.maptool.script.mt2.functions.ini.InitiativeFunctions;
import net.rptools.maptool.script.mt2.functions.player.PlayerFunctions;

//FIXMESOON make everything that returns or gets tokens here into tokenrepr
//ECLIPSE make this into a PLUG-IN
public class MT2ScriptLibrary implements ScriptLibrary {
	
	public final InitiativeFunctions ini;
	public final InfoFunctions info;
	public final PlayerFunctions player;
	public final MapFunctions map;

	public MT2ScriptLibrary() {
		this.ini=new InitiativeFunctions();
		this.info=new InfoFunctions();
		this.player=new PlayerFunctions();
		this.map=new MapFunctions();
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
}
