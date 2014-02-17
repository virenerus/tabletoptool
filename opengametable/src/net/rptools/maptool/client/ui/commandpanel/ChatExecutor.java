package net.rptools.maptool.client.ui.commandpanel;

import java.util.List;
import java.util.Random;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.language.I18N;
import net.rptools.maptool.model.CellPoint;
import net.rptools.maptool.model.GUID;
import net.rptools.maptool.model.LookupTable;
import net.rptools.maptool.model.LookupTable.LookupEntry;
import net.rptools.maptool.model.Player;
import net.rptools.maptool.model.TextMessage;
import net.rptools.maptool.model.Token;
import net.rptools.maptool.script.MT2ScriptException;
import net.rptools.maptool.script.ScriptManager;
import net.sf.mt2.chatparser.ChatPart;
import net.sf.mt2.chatparser.DiceExpressionPart;
import net.sf.mt2.chatparser.ParsedChat;
import net.sf.mt2.chatparser.UnknownCommandException;
import net.sf.mt2.dice.expression.DiceExpression;

import org.apache.log4j.Logger;

/**
 * This is the class responsible for starting the chat parser on a given string and executing 
 * the results. It can also parse arbitrary dice expressions. 
 * @author Virenerus
 */
public class ChatExecutor {
	private static final Logger log=Logger.getLogger(ChatExecutor.class);
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
							MapTool.addMessage(TextMessage.say(buildDefaultStringRepresentation(parts),MapTool.getFrame().getCommandPanel().getIdentity()));
						break;
					case EMOTE:
						MapTool.addGlobalMessage("<span color=\"green\" style=\"font-style: italic;\">"
								+MapTool.getFrame().getCommandPanel().getIdentity()+" "
								+buildDefaultStringRepresentation(parts)+"</span>");
						break;
					case GM:
						MapTool.addMessage(TextMessage.gm(buildDefaultStringRepresentation(parts),MapTool.getFrame().getCommandPanel().getIdentity()));
						break;
					case GOTO: {
							try {
								String[] args=parts.getArguments();
								int x=Integer.parseInt(args[0]);
								int y=Integer.parseInt(args[1]);
								MapTool.getFrame().getCurrentZoneRenderer().centerOn(new CellPoint(x, y));
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
						List<Token> l=MapTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList();
						if(l==null || l.isEmpty())
							ScriptManager.getInstance().evaluate(buildDefaultStringRepresentation(parts));
						else
							ScriptManager.getInstance().evaluate(buildDefaultStringRepresentation(parts),l.get(0));
						break;
					case OOC:
						MapTool.addMessage(TextMessage.say("(( "+buildDefaultStringRepresentation(parts)+" ))", MapTool.getPlayer().getName()));
						break;
					case REPLY: {
						String playerName = MapTool.getLastWhisperer();
				        if (playerName == null) 
				        {
				        	MapTool.addMessage(TextMessage.me("<b>You have no one to which to reply.</b>"));
				        }
				        
				        // Validate
				        if (!MapTool.isPlayerConnected(playerName)) {
				            MapTool.addMessage(TextMessage.me(I18N.getText("msg.error.playerNotConnected", playerName)));
				            return;
				        }
				        if (MapTool.getPlayer().getName().equalsIgnoreCase(playerName)) {
				            MapTool.addMessage(TextMessage.me(I18N.getText("whisper.toSelf")));
				            return;
				        }
				        
				        // Send
				        String message=buildDefaultStringRepresentation(parts);
				        MapTool.addMessage(TextMessage.whisper(playerName, "<span class='whisper' style='color:blue'>" 
				        		+ I18N.getText("whisper.string",  MapTool.getFrame().getCommandPanel().getIdentity(), message)+"</span>"));
				        MapTool.addMessage(TextMessage.me("<span class='whisper' style='color:blue'>" + 
				        		I18N.getText("whisper.you.string", playerName, message) + "</span>"));
						break;
					}
					case ROLL:
						MapTool.addMessage(TextMessage.say(printRoll((DiceExpressionPart)parts.get(0)),MapTool.getFrame().getCommandPanel().getIdentity()));
						break;
					case ROLL_GM:
						MapTool.addMessage(TextMessage.gm(printRoll((DiceExpressionPart)parts.get(0)),MapTool.getFrame().getCommandPanel().getIdentity()));
						break;
					case ROLL_ME:
						MapTool.addMessage(TextMessage.me(printRoll((DiceExpressionPart)parts.get(0))));
						break;
					case ROLL_SECRET:
						String roll=printRoll((DiceExpressionPart)parts.get(0));
						if (!MapTool.getPlayer().isGM()) {
			            	MapTool.addMessage(new TextMessage(TextMessage.Channel.GM, null, MapTool.getPlayer().getName(), "* " + 
			            			I18N.getText("rollsecret.gm.string", MapTool.getPlayer().getName(), roll)));
			            	MapTool.addMessage(new TextMessage(TextMessage.Channel.ME, null, MapTool.getPlayer().getName(), 
			            			I18N.getText("rollsecret.self.string")));
			            } else {
			            	MapTool.addMessage(new TextMessage(TextMessage.Channel.GM, null, MapTool.getPlayer().getName(), "* " + 
			            			I18N.getText("rollsecret.gmself.string", roll)));
			            }
						break;
					case SELF:
						MapTool.addMessage(TextMessage.me(buildDefaultStringRepresentation(parts)));
						break;
					case TABLE:
						try {
							String[] args=parts.getArguments();
							String tableName=args[0];
							String value=(args.length==1?null:args[1]);
							LookupTable lookupTable = MapTool.getCampaign().getLookupTableMap().get(tableName);
					    	if(!MapTool.getPlayer().isGM() && !lookupTable.getAllowLookup()) {
					    		if(lookupTable.getVisible()) {
					    			MapTool.addLocalMessage(I18N.getText("msg.error.tableDoesNotExist") + " '" + tableName + "'");
					    		} else {
					    			MapTool.showError(I18N.getText("msg.error.tableAccessProhibited") + ": " + tableName);
					    		}
					    		return;
					    	}
					    	if (lookupTable == null) {
					    		MapTool.addLocalMessage(I18N.getText("msg.error.tableDoesNotExist") + " '" + tableName + "'");
					    		return;
					    	}

					    	LookupEntry result = lookupTable.getLookup(value);
					    	String lookupValue = result.getValue();
					
					    	// Command handling
					    	if (result != null && lookupValue.startsWith("/")) {
					    		ScriptManager.getInstance().evaluate(lookupValue);
					    		return;
					    	}
					    	StringBuilder sb=new StringBuilder();
					    	sb.append("Table ").append(tableName).append(" (");
					        sb.append(MapTool.getFrame().getCommandPanel().getIdentity());
					        sb.append("): ");
					        
					    	if (result.getImageId() != null) {
					    		sb.append("<img src=\"asset://").append(result.getImageId()).append("\" alt=\"").append(result.getValue()).append("\">");
					    	} else {
						        sb.append("<span style='color:red'>");
						        
						        sb.append(lookupValue);
						        sb.append("</span>");
					    	}

					    	MapTool.addMessage(TextMessage.say(sb.toString(),MapTool.getFrame().getCommandPanel().getIdentity()));
				    	} catch (Exception pe) {
					        MapTool.addLocalMessage("lookuptable.couldNotPerform" + pe.getMessage());
				    	}
						break;
					case TOKEN_SPEECH:
						List<Token> selectedTokens = MapTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList();
						if (selectedTokens.isEmpty()) {
							MapTool.addLocalMessage(I18N.getText("msg.error.noTokensSelected"));
							return;
						}
						  
						for (Token token : selectedTokens) {
							String speechKey = token.getSpeech(parts.getArguments()[0]);
							String speech=token.getSpeech(speechKey);
							if(text!=null)
								MapTool.addMessage(TextMessage.say(speech, token.getName()));
						}
						break;
					case WHISPER:
						try {
							String playerName=null;
							String part1=parts.get(0).getDefaultTextRepresentation();
							for(Player p:MapTool.getPlayerList()) {
								if(part1.startsWith(p.getName()))
									playerName=p.getName();
							}
					       
							// Validate
					        if (!MapTool.isPlayerConnected(playerName)) {
					            MapTool.addMessage(TextMessage.me(I18N.getText("msg.error.playerNotConnected", playerName)));
					            return;
					        }
					        if (MapTool.getPlayer().getName().equalsIgnoreCase(playerName)) {
					            MapTool.addMessage(TextMessage.me(I18N.getText("whisper.toSelf")));
					            return;
					        }
					        
					        String message=buildDefaultStringRepresentation(parts).substring(playerName.length()).trim();
					        // Send
					        MapTool.addMessage(TextMessage.whisper(playerName, "<span class='whisper' style='color:blue'>" 
					        		+ I18N.getText("whisper.string",  MapTool.getFrame().getCommandPanel().getIdentity(), message)+"</span>"));
					        MapTool.addMessage(TextMessage.me("<span class='whisper' style='color:blue'>" + 
					        		I18N.getText("whisper.you.string", playerName, message) + "</span>"));
						} catch(Exception e) {
							
						}
						break;
					default:
						break;
				}
			}
			else
				MapTool.addMessage(TextMessage.say(buildDefaultStringRepresentation(parts), MapTool.getPlayer().getName()));
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
