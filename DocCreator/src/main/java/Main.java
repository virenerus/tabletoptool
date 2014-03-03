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
import net.rptools.maptool.script.mt2api.functions.player.PlayerFunctions;


public class Main {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String[] files={
			
			"global scope",path("OpenGameTable",MT2ScriptLibrary.class),
			"info.",path("OpenGameTable",InfoFunctions.class),
			"player.",path("OpenGameTable",PlayerFunctions.class),
			"map.",path("OpenGameTable",MapFunctions.class),
			"dialog.",path("OpenGameTable",DialogFunctions.class),
			"path.",path("OpenGameTable",PathFunctions.class),
			"TokenView",path("OpenGameTable",TokenView.class),
			"MapView",path("OpenGameTable",MapView.class),
			"MacroView",path("OpenGameTable",MacroView.class),
			"InitiativeListView",path("OpenGameTable",InitiativeListView.class),
			"DiceExpressionView",path("OpenGameTable",DiceExpressionView.class),
			"InitiativeListView",path("OpenGameTable",InitiativeListView.class),
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
