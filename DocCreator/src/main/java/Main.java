import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.rptools.maptool.script.mt2api.DiceExpressionView;
import net.rptools.maptool.script.mt2api.MT2ScriptLibrary;
import net.rptools.maptool.script.mt2api.MacroView;
import net.rptools.maptool.script.mt2api.MapView;
import net.rptools.maptool.script.mt2api.TokenView;
import net.rptools.maptool.script.mt2api.functions.DialogFunctions;
import net.rptools.maptool.script.mt2api.functions.InfoFunctions;
import net.rptools.maptool.script.mt2api.functions.InitiativeListView;
import net.rptools.maptool.script.mt2api.functions.MapFunctions;
import net.rptools.maptool.script.mt2api.functions.PathFunctions;
import net.rptools.maptool.script.mt2api.functions.ini.InitiativeFunctions;
import net.rptools.maptool.script.mt2api.functions.input.InputFunctions;
import net.rptools.maptool.script.mt2api.functions.player.PlayerFunctions;


public class Main {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String[] files={
			"TokenView",path("maptoolsfork",TokenView.class),
			"MapView",path("maptoolsfork",MapView.class),
			"MacroView",path("maptoolsfork",MacroView.class),
			"DiceExpressionView",path("maptoolsfork",DiceExpressionView.class),
			"InitiativeListView",path("maptoolsfork",InitiativeListView.class),
			"global scope",path("maptoolsfork",MT2ScriptLibrary.class),
			"InitiativeListView",path("maptoolsfork",InitiativeListView.class),
			"input.",path("maptoolsfork",InputFunctions.class),
			"info.",path("maptoolsfork",InfoFunctions.class),
			"player.",path("maptoolsfork",PlayerFunctions.class),
			"map.",path("maptoolsfork",MapFunctions.class),
			"dialog.",path("maptoolsfork",DialogFunctions.class),
			"path.",path("maptoolsfork",PathFunctions.class),
			};
		
		
		for(int i=0;i<files.length;i+=2) {
			DocCreator dc = new DocCreator(files[i],new File(files[i+1]));
			dc.print(System.out);
			System.out.println();
		}
	}
	
	private static String path(String project, Class<?> c) {
		return "../"+project+"/src/"+c.getName().replace('.', '/')+".java";
	}
}
