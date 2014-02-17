package net.sf.mt2.chatparser;

import java.io.IOException;
import java.util.List;

import net.sf.mt2.chatparser.generated.ChatParser;
import net.sf.mt2.chatparser.generated.ParseException;

public class Test {

	public static void main(String[] args) throws IOException, ParseException {
		ChatParser p=new ChatParser("/r 4d6+12");
		p.enable_tracing();
		try {
			ParsedChat pc=p.parse();
			System.out.println(pc.getChatCommand());
			if(pc.getArguments()!=null)
				for(String str:pc.getArguments())
					System.out.println("\t"+str);
			System.out.println();
			for(ChatPart cp:pc) {
				System.out.println("\t"+cp.getClass().getSimpleName());
				System.out.println("\t\t"+cp.getDefaultTextRepresentation());
			}
		} catch (UnknownCommandException e) {
			e.printStackTrace();
		}
	}

}
