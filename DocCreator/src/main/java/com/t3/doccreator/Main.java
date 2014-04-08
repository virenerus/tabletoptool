/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3.doccreator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.t3.dice.expression.Expression;
import com.t3.macro.api.MacroAPI;
import com.t3.macro.api.functions.CampaignFunctions;
import com.t3.macro.api.functions.DialogFunctions;
import com.t3.macro.api.functions.InfoFunctions;
import com.t3.macro.api.functions.MapFunctions;
import com.t3.macro.api.functions.PathFunctions;
import com.t3.macro.api.functions.PlayerFunctions;
import com.t3.macro.api.views.MacroButtonView;
import com.t3.macro.api.views.InitiativeListView;
import com.t3.macro.api.views.MapView;
import com.t3.macro.api.views.TokenView;


public class Main {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String[] files={
			
			"global scope",path("TabletopTool",MacroAPI.class),
			"info.",path("TabletopTool",InfoFunctions.class),
			"player.",path("TabletopTool",PlayerFunctions.class),
			"map.",path("TabletopTool",MapFunctions.class),
			"dialog.",path("TabletopTool",DialogFunctions.class),
			"path.",path("TabletopTool",PathFunctions.class),
			"states.",path("TabletopTool",CampaignFunctions.class),
			"TokenView",path("TabletopTool",TokenView.class),
			"MapView",path("TabletopTool",MapView.class),
			"MacroButtonView",path("TabletopTool",MacroButtonView.class),
			"InitiativeListView",path("TabletopTool",InitiativeListView.class),
			"ExpressionView",path("TabletopTool",Expression.class),
			};
		
		
		for(int i=0;i<files.length;i+=2) {
			DocCreator dc = new DocCreator(files[i],new File(files[i+1]));
			dc.print(System.out);
			System.out.println();
		}
	}
	
	private static String path(String project, Class<?> c) {
		return "../"+project+"/src/main/java/"+c.getName().replace('.', '/')+".java";
	}
}
