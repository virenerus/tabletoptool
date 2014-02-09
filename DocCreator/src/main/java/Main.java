import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.rptools.maptool.script.mt2api.functions.DialogFunctions;
import net.rptools.maptool.script.mt2api.functions.InfoFunctions;
import net.rptools.maptool.script.mt2api.functions.MapFunctions;
import net.rptools.maptool.script.mt2api.functions.PathFunctions;
import net.rptools.maptool.script.mt2api.functions.ini.InitiativeFunctions;
import net.rptools.maptool.script.mt2api.functions.input.InputFunctions;
import net.rptools.maptool.script.mt2api.functions.player.PlayerFunctions;


public class Main {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String[] files={
			"TokenView","../maptoolsfork/src/net/rptools/maptool/script/mt2api/TokenView.java",
			"MapView","../maptoolsfork/src/net/rptools/maptool/script/mt2api/MapView.java",
			"MacroView","../maptoolsfork/src/net/rptools/maptool/script/mt2api/MacroView.java",
			"global scope","../maptoolsfork/src/net/rptools/maptool/script/mt2api/MT2ScriptLibrary.java",
			"ini.","../maptoolsfork/src/"+InitiativeFunctions.class.getName().replace('.', '/')+".java",
			"input.","../maptoolsfork/src/"+InputFunctions.class.getName().replace('.', '/')+".java",
			"info.","../maptoolsfork/src/"+InfoFunctions.class.getName().replace('.', '/')+".java",
			"player.","../maptoolsfork/src/"+PlayerFunctions.class.getName().replace('.', '/')+".java",
			"map.","../maptoolsfork/src/"+MapFunctions.class.getName().replace('.', '/')+".java",
			"dialog.","../maptoolsfork/src/"+DialogFunctions.class.getName().replace('.', '/')+".java",
			"path.","../maptoolsfork/src/"+PathFunctions.class.getName().replace('.', '/')+".java",
			};
		
		
		for(int i=0;i<files.length;i+=2) {
			DocCreator dc = new DocCreator(files[i],new File(files[i+1]));
			dc.print(System.out);
			System.out.println();
		}
	}
}
