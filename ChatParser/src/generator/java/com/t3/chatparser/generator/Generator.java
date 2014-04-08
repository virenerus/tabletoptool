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
package com.t3.chatparser.generator;

import org.javacc.parser.Main;

public class Generator {

	public static void main(String[] args) throws Exception {
		//javacc.main(new String[]{});
		Main.main(new String[]{"-OUTPUT_DIRECTORY="+args[0]+"/src/main/java/com/t3/chatparser/generated/",args[0]+"/src/generator/resources/com/t3/chatparser/generator/syntax.jj"});
	}

}
