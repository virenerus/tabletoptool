package net.rptools.maptool.script.mt2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.list.UnmodifiableList;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.model.Player;
import net.rptools.maptool.script.ScriptLibrary;
import net.rptools.maptool.script.mt2.functions.FogFunctions;
import net.rptools.maptool.script.mt2.functions.InfoFunctions;
import net.rptools.maptool.script.mt2.functions.TokenFunctions;
import net.rptools.maptool.script.mt2.functions.ini.InitiativeFunctions;
import net.rptools.maptool.script.mt2.functions.vbl.VBLFunctions;

//FIXMESOON make everything that returns or gets tokens here into tokenrepr
//ECLIPSE make this into a PLUG-IN
public class MT2ScriptLibrary implements ScriptLibrary {
	
	public final VBLFunctions vbl;
	public final InitiativeFunctions ini;
	public final InfoFunctions info;
	public final TokenFunctions token;
	public final FogFunctions fog;

	public MT2ScriptLibrary() {
		this.vbl=new VBLFunctions();
		this.ini=new InitiativeFunctions();
		this.info=new InfoFunctions();
		this.token=new TokenFunctions();
		this.fog=new FogFunctions();
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
