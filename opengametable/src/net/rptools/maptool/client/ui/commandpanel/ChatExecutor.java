package net.rptools.maptool.client.ui.commandpanel;

import java.util.List;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.model.TextMessage;
import net.sf.mt2.chatparser.ChatPart;
import net.sf.mt2.chatparser.ParsedChat;
import net.sf.mt2.chatparser.UnknownCommandException;

import org.codehaus.groovy.tools.shell.util.Logger;

public class ChatExecutor {
	private static final Logger log=Logger.create(ChatExecutor.class);
	/**
	 * This parses the given text and returns the list of understood parts that you can work with.
	 * @param str the text to parse
	 * @return a list of the understood words
	 * @throws UnknownCommandException if you try to call an unknown chat command 
	 */
	public static ParsedChat parseChat(String text) throws UnknownCommandException {
		return new net.sf.mt2.chatparser.generated.ChatParser(text).parse();
	}

	public static void executeChat(String text) {
		try {
			ParsedChat parts=parseChat(text);
			if(parts.getChatCommand()!=null) {
				switch(parts.getChatCommand()) {
				case CLEAR_CHAT:
					MapTool.getFrame().getCommandPanel().clearMessagePanel();
					break;
				case EMIT:
					if(MapTool.getPlayer().isGM())
						MapTool.addGlobalMessage(buildDefaultStringRepresentation(parts));
					else
						MapTool.addMessage(TextMessage.say(null, buildDefaultStringRepresentation(parts), MapTool.getPlayer().getName()));
					break;
				case EMOTE:
					MapTool.addGlobalMessage("<span color=\"green\" style=\"font-style: italic;\">"
							+MapTool.getPlayer().getName()+" "
							+buildDefaultStringRepresentation(parts)+"</span>");
					break;
				case GM:
					MapTool.addMessage(TextMessage.gm(null, buildDefaultStringRepresentation(parts)));
					break;
				case GOTO:
					break;
				case IMPERSONATE:
					break;
				case MACRO_EXEC:
					break;
				case OOC:
					break;
				case REPLY:
					break;
				case ROLL:
					break;
				case ROLL_GM:
					break;
				case ROLL_ME:
					break;
				case ROLL_SECRET:
					break;
				case SELF:
					break;
				case TABLE:
					break;
				case TOKEN_MACRO:
					break;
				case TOKEN_SPEECH:
					break;
				case WHISPER:
					break;
				default:
					break;
				}
			}
			else
				MapTool.addMessage(TextMessage.say(null, buildDefaultStringRepresentation(parts), MapTool.getPlayer().getName()));
		} catch (UnknownCommandException e) {
			MapTool.addLocalMessage("<font color=\"red\">"+e.getMessage()+"</font>");
			log.error(e);
			e.printStackTrace();
		}
	}

	private static String buildDefaultStringRepresentation(List<? extends ChatPart> parts) {
		StringBuilder sb=new StringBuilder();
		for(ChatPart cp:parts)
			sb.append(cp.getDefaultTextRepresentation());
		return sb.toString();
	}
}
