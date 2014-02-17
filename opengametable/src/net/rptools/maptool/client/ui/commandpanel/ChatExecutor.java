package net.rptools.maptool.client.ui.commandpanel;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.model.GUID;
import net.rptools.maptool.model.TextMessage;
import net.rptools.maptool.script.MT2ScriptException;
import net.rptools.maptool.script.ScriptManager;
import net.rptools.maptool.script.mt2api.MT2ScriptLibrary;
import net.rptools.maptool.script.mt2api.TokenView;
import net.sf.mt2.chatparser.ChatPart;
import net.sf.mt2.chatparser.DiceExpressionPart;
import net.sf.mt2.chatparser.ParsedChat;
import net.sf.mt2.chatparser.UnknownCommandException;
import net.sf.mt2.dice.expression.DiceExpression;

import org.codehaus.groovy.tools.shell.util.Logger;

/**
 * This is the class responsible for starting the chat parser on a given string and executing 
 * the results. It can also parse arbitrary dice expressions. 
 * @author Virenerus
 */
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
					case GOTO: {
							try {
								String[] args=parts.getArguments();
								float x=Float.parseFloat(args[0]);
								float y=Float.parseFloat(args[1]);
								//TODO MapTool.getFrame().getCurrentZoneRenderer().centerOn(MapTool.getFrame().findToken(guid).get
							} catch(Exception e) {
								throw new IllegalArgumentException("goto expects a coordinate x y");
							}
						}
						break;
					case IMPERSONATE: 
						try {
							GUID guid = new GUID(parts.getArguments()[0]);
							MapTool.getFrame().getCommandPanel().setIdentityGUID(guid);
						} catch(Exception e) {
							throw new IllegalArgumentException("impersonate expects one 16 characters token id");
						}
						break;
					case MACRO_EXEC:
						ScriptManager.getInstance().evaluate(buildDefaultStringRepresentation(parts));
						break;
					case OOC:
						MapTool.addMessage(TextMessage.say(null, "(( "+buildDefaultStringRepresentation(parts)+" ))", MapTool.getPlayer().getName()));
						break;
					case REPLY:
						//TODO MapTool.getFrame().getCommandPanel().get
						break;
					case ROLL:
						MapTool.addMessage(TextMessage.say(printRoll((DiceExpressionPart)parts.get(0))));
						break;
					case ROLL_GM:
						MapTool.addMessage(TextMessage.gm(printRoll((DiceExpressionPart)parts.get(0))));
						break;
					case ROLL_ME:
						MapTool.addMessage(TextMessage.me(printRoll((DiceExpressionPart)parts.get(0))));
						break;
					case ROLL_SECRET:
						MapTool.addMessage(TextMessage.group(target, message)(printRoll((DiceExpressionPart)parts.get(0))));
						break;
					case SELF:
						MapTool.addMessage(TextMessage.me(buildDefaultStringRepresentation(parts)));
						break;
					case TABLE:
						MapTool.getFrame().get
						break;
					case TOKEN_MACRO:
						MapTool
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
		} catch (MT2ScriptException | IllegalArgumentException | UnknownCommandException e) {
			MapTool.addLocalMessage("<font color=\"red\">"+e.getMessage()+"</font>");
			log.error(e);
			e.printStackTrace();
		}
	}

	private static String printRoll(DiceExpressionPart diceExpressionPart) {
		DiceExpression diceExpression = diceExpressionPart.getDiceExpression();
		int result=diceExpression.evaluate(new Random());
		StringBuilder sb=new StringBuilder();
		sb.append(diceExpression.toString())
			.append(" = ")
			.append(diceExpression.toEvaluatedString())
			.append(" = <b>")
			.append(result)
			.append("</b>");
		
		return sb.toString();
	}

	private static String buildDefaultStringRepresentation(List<? extends ChatPart> parts) {
		StringBuilder sb=new StringBuilder();
		for(ChatPart cp:parts)
			sb.append(cp.getDefaultTextRepresentation());
		return sb.toString();
	}
}
