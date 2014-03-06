package com.t3.client.ui.commandpanel;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.t3.chatparser.ChatPart;
import com.t3.chatparser.DiceExpressionPart;
import com.t3.chatparser.ParsedChat;
import com.t3.chatparser.UnknownCommandException;
import com.t3.dice.expression.DiceExpression;
import com.t3.client.T3Util;
import com.t3.client.TabletopTool;
import com.t3.language.I18N;
import com.t3.model.CellPoint;
import com.t3.model.GUID;
import com.t3.model.LookupTable;
import com.t3.model.Player;
import com.t3.model.TextMessage;
import com.t3.model.Token;
import com.t3.model.LookupTable.LookupEntry;
import com.t3.script.MT2ScriptException;
import com.t3.script.ScriptManager;

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
		return new com.t3.chatparser.generated.ChatParser(text).parse();
	}

	public static void executeChat(String text) {
		try {
			ParsedChat parts=parseChat(text);
			if(parts.getChatCommand()!=null) {
				switch(parts.getChatCommand()) {
					case CLEAR_CHAT:
						TabletopTool.getFrame().getCommandPanel().clearMessagePanel();
						break;
					case EMIT:
						if(TabletopTool.getPlayer().isGM())
							TabletopTool.addGlobalMessage(buildDefaultStringRepresentation(parts));
						else
							TabletopTool.addMessage(TextMessage.say(buildDefaultStringRepresentation(parts),TabletopTool.getFrame().getCommandPanel().getIdentity()));
						break;
					case EMOTE:
						TabletopTool.addGlobalMessage("<span color=\"green\" style=\"font-style: italic;\">"
								+TabletopTool.getFrame().getCommandPanel().getIdentity()+" "
								+buildDefaultStringRepresentation(parts)+"</span>");
						break;
					case GM:
						TabletopTool.addMessage(TextMessage.gm(buildDefaultStringRepresentation(parts),TabletopTool.getFrame().getCommandPanel().getIdentity()));
						break;
					case GOTO: {
							try {
								String[] args=parts.getArguments();
								int x=Integer.parseInt(args[0]);
								int y=Integer.parseInt(args[1]);
								TabletopTool.getFrame().getCurrentZoneRenderer().centerOn(new CellPoint(x, y));
							} catch(Exception e) {
								throw new IllegalArgumentException("goto expects a coordinate x y");
							}
						}
						break;
					case IMPERSONATE: 
						try {
							GUID guid = new GUID(parts.getArguments()[0]);
							TabletopTool.getFrame().getCommandPanel().setIdentityGUID(guid);
						} catch(Exception e) {
							throw new IllegalArgumentException("impersonate expects one 16 characters token id");
						}
						break;
					case MACRO_EXEC:
						List<Token> l=TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList();
						if(l==null || l.isEmpty())
							ScriptManager.getInstance().evaluate(buildDefaultStringRepresentation(parts));
						else
							ScriptManager.getInstance().evaluate(buildDefaultStringRepresentation(parts),l.get(0));
						break;
					case OOC:
						TabletopTool.addMessage(TextMessage.say("(( "+buildDefaultStringRepresentation(parts)+" ))", TabletopTool.getPlayer().getName()));
						break;
					case REPLY: {
						String playerName = TabletopTool.getLastWhisperer();
				        if (playerName == null) 
				        {
				        	TabletopTool.addMessage(TextMessage.me("<b>You have no one to which to reply.</b>"));
				        }
				        
				        // Validate
				        if (!TabletopTool.isPlayerConnected(playerName)) {
				            TabletopTool.addMessage(TextMessage.me(I18N.getText("msg.error.playerNotConnected", playerName)));
				            return;
				        }
				        if (TabletopTool.getPlayer().getName().equalsIgnoreCase(playerName)) {
				            TabletopTool.addMessage(TextMessage.me(I18N.getText("whisper.toSelf")));
				            return;
				        }
				        
				        // Send
				        String message=buildDefaultStringRepresentation(parts);
				        TabletopTool.addMessage(TextMessage.whisper(playerName, "<span class='whisper' style='color:blue'>" 
				        		+ I18N.getText("whisper.string",  TabletopTool.getFrame().getCommandPanel().getIdentity(), message)+"</span>"));
				        TabletopTool.addMessage(TextMessage.me("<span class='whisper' style='color:blue'>" + 
				        		I18N.getText("whisper.you.string", playerName, message) + "</span>"));
						break;
					}
					case ROLL:
						TabletopTool.addMessage(TextMessage.say(printRoll((DiceExpressionPart)parts.get(0)),TabletopTool.getFrame().getCommandPanel().getIdentity()));
						break;
					case ROLL_GM:
						TabletopTool.addMessage(TextMessage.gm(printRoll((DiceExpressionPart)parts.get(0)),TabletopTool.getFrame().getCommandPanel().getIdentity()));
						break;
					case ROLL_ME:
						TabletopTool.addMessage(TextMessage.me(printRoll((DiceExpressionPart)parts.get(0))));
						break;
					case ROLL_SECRET:
						String roll=printRoll((DiceExpressionPart)parts.get(0));
						if (!TabletopTool.getPlayer().isGM()) {
			            	TabletopTool.addMessage(new TextMessage(TextMessage.Channel.GM, null, TabletopTool.getPlayer().getName(), "* " + 
			            			I18N.getText("rollsecret.gm.string", TabletopTool.getPlayer().getName(), roll)));
			            	TabletopTool.addMessage(new TextMessage(TextMessage.Channel.ME, null, TabletopTool.getPlayer().getName(), 
			            			I18N.getText("rollsecret.self.string")));
			            } else {
			            	TabletopTool.addMessage(new TextMessage(TextMessage.Channel.GM, null, TabletopTool.getPlayer().getName(), "* " + 
			            			I18N.getText("rollsecret.gmself.string", roll)));
			            }
						break;
					case SELF:
						TabletopTool.addMessage(TextMessage.me(buildDefaultStringRepresentation(parts)));
						break;
					case TABLE:
						try {
							String[] args=parts.getArguments();
							String tableName=args[0];
							String value=(args.length==1?null:args[1]);
							LookupTable lookupTable = TabletopTool.getCampaign().getLookupTableMap().get(tableName);
					    	if(!TabletopTool.getPlayer().isGM() && !lookupTable.getAllowLookup()) {
					    		if(lookupTable.getVisible()) {
					    			TabletopTool.addLocalMessage(I18N.getText("msg.error.tableDoesNotExist") + " '" + tableName + "'");
					    		} else {
					    			TabletopTool.showError(I18N.getText("msg.error.tableAccessProhibited") + ": " + tableName);
					    		}
					    		return;
					    	}
					    	if (lookupTable == null) {
					    		TabletopTool.addLocalMessage(I18N.getText("msg.error.tableDoesNotExist") + " '" + tableName + "'");
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
					        sb.append(TabletopTool.getFrame().getCommandPanel().getIdentity());
					        sb.append("): ");
					        
					    	if (result.getImageId() != null) {
					    		sb.append("<img src=\"asset://").append(result.getImageId()).append("\" alt=\"").append(result.getValue()).append("\">");
					    	} else {
						        sb.append("<span style='color:red'>");
						        
						        sb.append(lookupValue);
						        sb.append("</span>");
					    	}

					    	TabletopTool.addMessage(TextMessage.say(sb.toString(),TabletopTool.getFrame().getCommandPanel().getIdentity()));
				    	} catch (Exception pe) {
					        TabletopTool.addLocalMessage("lookuptable.couldNotPerform" + pe.getMessage());
				    	}
						break;
					case TOKEN_SPEECH:
						List<Token> selectedTokens = TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList();
						if (selectedTokens.isEmpty()) {
							TabletopTool.addLocalMessage(I18N.getText("msg.error.noTokensSelected"));
							return;
						}
						  
						for (Token token : selectedTokens) {
							String speechKey = token.getSpeech(parts.getArguments()[0]);
							String speech=token.getSpeech(speechKey);
							if(text!=null)
								TabletopTool.addMessage(TextMessage.say(speech, token.getName()));
						}
						break;
					case WHISPER:
						try {
							String playerName=null;
							String part1=parts.get(0).getDefaultTextRepresentation();
							for(Player p:TabletopTool.getPlayerList()) {
								if(part1.startsWith(p.getName()))
									playerName=p.getName();
							}
					       
							// Validate
					        if (!TabletopTool.isPlayerConnected(playerName)) {
					            TabletopTool.addMessage(TextMessage.me(I18N.getText("msg.error.playerNotConnected", playerName)));
					            return;
					        }
					        if (TabletopTool.getPlayer().getName().equalsIgnoreCase(playerName)) {
					            TabletopTool.addMessage(TextMessage.me(I18N.getText("whisper.toSelf")));
					            return;
					        }
					        
					        String message=buildDefaultStringRepresentation(parts).substring(playerName.length()).trim();
					        // Send
					        TabletopTool.addMessage(TextMessage.whisper(playerName, "<span class='whisper' style='color:blue'>" 
					        		+ I18N.getText("whisper.string",  TabletopTool.getFrame().getCommandPanel().getIdentity(), message)+"</span>"));
					        TabletopTool.addMessage(TextMessage.me("<span class='whisper' style='color:blue'>" + 
					        		I18N.getText("whisper.you.string", playerName, message) + "</span>"));
						} catch(Exception e) {
							
						}
						break;
					default:
						break;
				}
			}
			else
				TabletopTool.addMessage(TextMessage.say(buildDefaultStringRepresentation(parts), TabletopTool.getPlayer().getName()));
		} catch (MT2ScriptException | IllegalArgumentException | UnknownCommandException e) {
			TabletopTool.addLocalMessage("<font color=\"red\">"+e.getMessage()+"</font>");
			log.error(e);
			e.printStackTrace();
		}
	}

	private static String printRoll(DiceExpressionPart diceExpressionPart) {
		DiceExpression diceExpression = diceExpressionPart.getDiceExpression();
		float result=diceExpression.evaluate(T3Util.getRandom());
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
