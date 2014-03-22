package com.t3.doccreator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.t3.dice.expression.Expression;
import com.t3.macro.api.MacroAPI;
import com.t3.macro.api.functions.DialogFunctions;
import com.t3.macro.api.functions.InfoFunctions;
import com.t3.macro.api.functions.InitiativeListView;
import com.t3.macro.api.functions.MapFunctions;
import com.t3.macro.api.functions.PathFunctions;
import com.t3.macro.api.functions.player.PlayerFunctions;
import com.t3.macro.api.views.ButtonMacroView;
import com.t3.macro.api.views.MapView;
import com.t3.macro.api.views.TokenView;


public class Main {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String[] files={
			
			"global scope",path("OpenGameTable",MacroAPI.class),
			"info.",path("OpenGameTable",InfoFunctions.class),
			"player.",path("OpenGameTable",PlayerFunctions.class),
			"map.",path("OpenGameTable",MapFunctions.class),
			"dialog.",path("OpenGameTable",DialogFunctions.class),
			"path.",path("OpenGameTable",PathFunctions.class),
			"TokenView",path("OpenGameTable",TokenView.class),
			"MapView",path("OpenGameTable",MapView.class),
			"ButtonMacroView",path("OpenGameTable",ButtonMacroView.class),
			"InitiativeListView",path("OpenGameTable",InitiativeListView.class),
			"ExpressionView",path("OpenGameTable",Expression.class),
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
