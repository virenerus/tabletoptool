package com.t3.doccreator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.t3.script.api.DiceExpressionView;
import com.t3.script.api.MT2ScriptLibrary;
import com.t3.script.api.MacroView;
import com.t3.script.api.MapView;
import com.t3.script.api.TokenView;
import com.t3.script.api.functions.DialogFunctions;
import com.t3.script.api.functions.InfoFunctions;
import com.t3.script.api.functions.InitiativeListView;
import com.t3.script.api.functions.MapFunctions;
import com.t3.script.api.functions.PathFunctions;
import com.t3.script.api.functions.player.PlayerFunctions;


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
