package net.sf.mt2.chatparser;

import java.io.IOException;
import java.util.List;

import net.sf.mt2.chatparser.generated.ChatParser;
import net.sf.mt2.chatparser.generated.ParseException;

public class Test {

	public static void main(String[] args) throws IOException, ParseException {
		ChatParser p=new ChatParser("/whallo");
		p.enable_tracing();
		List<ChatPart> l=p.Start();
		
		for(ChatPart cp:l) {
			System.out.println(cp.getClass().getSimpleName());
			System.out.println("\t"+cp.getDefaultTextRepresentation());
		}
	}

}
