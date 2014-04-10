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
package com.t3.client.ui.commandpanel;

import java.util.List;

import org.apache.log4j.Logger;

import com.t3.chatparser.ChatPart;
import com.t3.chatparser.ExpressionPart;
import com.t3.chatparser.ParsedChat;
import com.t3.chatparser.UnknownCommandException;
import com.t3.client.TabletopTool;
import com.t3.language.I18N;
import com.t3.macro.MacroEngine;
import com.t3.macro.MacroException;
import com.t3.model.CellPoint;
import com.t3.GUID;
import com.t3.model.LookupTable;
import com.t3.model.LookupTable.LookupEntry;
import com.t3.model.Player;
import com.t3.model.TextMessage;
import com.t3.model.Token;

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

	public static void executeChat(String text, String identity) {
		try {
			ParsedChat parts=parseChat(text);
			if(parts.getChatCommand()!=null) {
				switch(parts.getChatCommand()) {
					case CLEAR_CHAT:
						clearChat();
						break;
					case EMIT:
						emit(buildDefaultStringRepresentation(parts), identity);
						break;
					case EMOTE:
						emote(buildDefaultStringRepresentation(parts),identity);
						break;
					case GM:
						gm(buildDefaultStringRepresentation(parts),identity);
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
					case CLEAR_IMPERSONATE: 
						TabletopTool.getFrame().getCommandPanel().setIdentityGUID(null);
						break;
					case MACRO_EXEC:
						List<Token> l=TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList();
						if(l==null || l.isEmpty())
							MacroEngine.getInstance().evaluate(buildDefaultStringRepresentation(parts));
						else
							MacroEngine.getInstance().evaluate(buildDefaultStringRepresentation(parts),l.get(0));
						break;
					case OOC:
						outOfCharacter(buildDefaultStringRepresentation(parts));
						break;
					case REPLY:
						reply(buildDefaultStringRepresentation(parts), identity);
						break;
					case ROLL:
						TabletopTool.addMessage(TextMessage.say(((ExpressionPart)parts.get(0)).getDiceExpression().toCompleteChatString(),identity));
						break;
					case ROLL_GM:
						TabletopTool.addMessage(TextMessage.gm(((ExpressionPart)parts.get(0)).getDiceExpression().toCompleteChatString(),identity));
						break;
					case ROLL_ME:
						TabletopTool.addMessage(TextMessage.me(((ExpressionPart)parts.get(0)).getDiceExpression().toCompleteChatString()));
						break;
					case ROLL_SECRET:
						String roll=((ExpressionPart)parts.get(0)).getDiceExpression().toCompleteChatString();
						if (!TabletopTool.getPlayer().isGM()) {
			            	TabletopTool.addMessage(new TextMessage(TextMessage.Channel.GM, null, identity, "* " + 
			            			I18N.getText("rollsecret.gm.string", identity, roll)));
			            	TabletopTool.addMessage(new TextMessage(TextMessage.Channel.ME, null, identity, 
			            			I18N.getText("rollsecret.self.string")));
			            } else {
			            	TabletopTool.addMessage(new TextMessage(TextMessage.Channel.GM, null, identity, "* " + 
			            			I18N.getText("rollsecret.gmself.string", roll)));
			            }
						break;
					case SELF:
						self(buildDefaultStringRepresentation(parts));
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
					    		MacroEngine.getInstance().evaluate(lookupValue);
					    		return;
					    	}
					    	StringBuilder sb=new StringBuilder();
					    	sb.append("Table ").append(tableName).append(" (");
					        sb.append(identity);
					        sb.append("): ");
					        
					    	if (result.getImageId() != null) {
					    		sb.append("<img src=\"asset://").append(result.getImageId()).append("\" alt=\"").append(result.getValue()).append("\">");
					    	} else {
						        sb.append("<span style='color:red'>");
						        
						        sb.append(lookupValue);
						        sb.append("</span>");
					    	}

					    	TabletopTool.addMessage(TextMessage.say(sb.toString(),identity));
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
						String playerName=null;
						String part1=parts.get(0).getDefaultTextRepresentation();
						for(Player p:TabletopTool.getPlayerList()) {
							if(part1.startsWith(p.getName()))
								playerName=p.getName();
						}
						
						String message=buildDefaultStringRepresentation(parts).substring(playerName.length()).trim();
						
						whisper(message, identity, playerName);
						break;
					default:
						break;
				}
			}
			else
				say(buildDefaultStringRepresentation(parts), identity);
		} catch (MacroException | IllegalArgumentException | UnknownCommandException e) {
			TabletopTool.addLocalMessage("<font color=\"red\">"+e.getMessage()+"</font>");
			log.error(e);
			e.printStackTrace();
		}
	}

	public static void gm(String message, String identity) {
		TabletopTool.addMessage(TextMessage.gm(message, identity));
	}

	public static void say(String message, String identity) {
		TabletopTool.addMessage(TextMessage.say(message, identity));
	}

	public static void whisper(String message, String identity, String targetPlayer) {
		try {
			
	       
			// Validate
	        if (!TabletopTool.isPlayerConnected(targetPlayer)) {
	            TabletopTool.addMessage(TextMessage.me(I18N.getText("msg.error.playerNotConnected", targetPlayer)));
	            return;
	        }
	        if (TabletopTool.getPlayer().getName().equalsIgnoreCase(targetPlayer)) {
	            TabletopTool.addMessage(TextMessage.me(I18N.getText("whisper.toSelf")));
	            return;
	        }
	        
	        // Send
	        TabletopTool.addMessage(TextMessage.whisper(targetPlayer, "<span class='whisper' style='color:blue'>" 
	        		+ I18N.getText("whisper.string",  identity, message)+"</span>"));
	        TabletopTool.addMessage(TextMessage.me("<span class='whisper' style='color:blue'>" + 
	        		I18N.getText("whisper.you.string", targetPlayer, message) + "</span>"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void self(String message) {
		TabletopTool.addMessage(TextMessage.me(message));
	}

	public static void reply(String message, String identity) {
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
        TabletopTool.addMessage(TextMessage.whisper(playerName, "<span class='whisper' style='color:blue'>" 
        		+ I18N.getText("whisper.string",  identity, message)+"</span>"));
        TabletopTool.addMessage(TextMessage.me("<span class='whisper' style='color:blue'>" + 
        		I18N.getText("whisper.you.string", playerName, message) + "</span>"));
	}

	public static void outOfCharacter(String message) {
		TabletopTool.addMessage(TextMessage.say("(( "+message+" ))", TabletopTool.getPlayer().getName()));
	}

	public static void emote(String message, String identity) {
		TabletopTool.addGlobalMessage("<span color=\"green\" style=\"font-style: italic;\">"
				+identity+" "+message+"</span>");
	}

	public static void emit(String message, String identity) {
		if(TabletopTool.getPlayer().isGM())
			TabletopTool.addGlobalMessage(message);
		else
			TabletopTool.addMessage(TextMessage.say(message,identity));
	}

	public static void clearChat() {
		TabletopTool.getFrame().getCommandPanel().clearMessagePanel();
	}

	

	private static String buildDefaultStringRepresentation(List<? extends ChatPart> parts) {
		StringBuilder sb=new StringBuilder();
		for(ChatPart cp:parts)
			sb.append(cp.getDefaultTextRepresentation());
		return sb.toString();
	}
}
