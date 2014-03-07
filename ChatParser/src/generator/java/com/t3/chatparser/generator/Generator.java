package com.t3.chatparser.generator;

import org.javacc.parser.Main;

public class Generator {

	public static void main(String[] args) throws Exception {
		//javacc.main(new String[]{});
		Main.main(new String[]{"-OUTPUT_DIRECTORY="+args[0]+"/src/main/java/com/t3/chatparser/generated/",args[0]+"/src/generator/resources/com/t3/chatparser/generator/syntax.jj"});
	}

}
