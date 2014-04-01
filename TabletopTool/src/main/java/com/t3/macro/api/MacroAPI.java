package com.t3.macro.api;

import groovy.lang.Script;

import com.t3.chatparser.generated.ChatParser;
import com.t3.chatparser.generated.ParseException;
import com.t3.client.TabletopTool;
import com.t3.client.ui.commandpanel.ChatExecutor;
import com.t3.dice.DiceBuilder;
import com.t3.dice.expression.Expression;
import com.t3.macro.MacroException;
import com.t3.macro.api.functions.CampaignFunctions;
import com.t3.macro.api.functions.DialogFunctions;
import com.t3.macro.api.functions.InfoFunctions;
import com.t3.macro.api.functions.MapFunctions;
import com.t3.macro.api.functions.PathFunctions;
import com.t3.macro.api.functions.PlayerFunctions;
import com.t3.macro.api.views.InitiativeListView;

//TODO make PLUG-INs for this package
public abstract class MacroAPI extends Script {
	
	public final InfoFunctions info;
	public final PlayerFunctions player;
	public final MapFunctions map;
	public final DialogFunctions dialog;
	public final PathFunctions path;
	public final CampaignFunctions campaign;

	public MacroAPI() {
		super();
		this.info=new InfoFunctions();
		this.player=new PlayerFunctions();
		this.map=new MapFunctions();
		this.dialog=new DialogFunctions();
		this.path=new PathFunctions();
		this.campaign=new CampaignFunctions();
	}

	/**
	 * Simply writes to the chat
	 * @param message a string or some other kind of objects that is written to the chat
	 */
	public void say(Object message) {
		ChatExecutor.say(message.toString(),TabletopTool.getPlayer().getName());
	}
	
	/**
	 * Whispers to a certain player so that only you two can see it
	 * @param message a string or some other kind of objects that is written to the chat
	 */
	public void whisper(Object message, String targetPlayer) {
		ChatExecutor.whisper(message.toString(), TabletopTool.getPlayer().getName(), targetPlayer);		
	}
	
	/**
	 * Whispers to to the GM so that only you two can see it
	 * @param message a string or some other kind of objects that is written to the chat
	 */
	public void whisperToGM(Object message) {
		ChatExecutor.gm(message.toString(), TabletopTool.getPlayer().getName());
	}
	
	/**
	 * Says something out of character
	 * @param message a string or some other kind of objects that is written to the chat
	 */
	public void sayOOC(Object message) {
		ChatExecutor.outOfCharacter(message.toString());
	}
	
	/**
	 * This writes a message without your name to the chat if you are the GM
	 * @param message a string or some other kind of objects that is written to the chat
	 */
	public void emit(Object message) {
		ChatExecutor.emit(message.toString(), TabletopTool.getPlayer().getName());
	}

	/**
	 * This writes a message about you to the chat
	 * @param message a string or some other kind of objects that is written to the chat
	 */
	public void emote(Object message) {
		ChatExecutor.emote(message.toString(), TabletopTool.getPlayer().getName());
	}
	
	/**
	 * This whispers an answer back to last person that wrote to you
	 * @param message a string or some other kind of objects that is written to the chat
	 */
	public void reply(Object message) {
		ChatExecutor.reply(message.toString(), TabletopTool.getPlayer().getName());
	}
	
	/**
	 * This writes a message to the chat so that only you can see it
	 * @param o a string or some other kind of objects that is written to the chat
	 */
	public void print(Object o) {
		TabletopTool.addLocalMessage(o==null?"null":o.toString());
	}

	/**
	 * An convenience method. This will return the initiative list of the current map. 
	 * @return the initiative list of the current map
	 */
	public InitiativeListView getInitiativeList() {
		return new InitiativeListView(TabletopTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList());
	}

	/**
	 * This method allows you to roll dice by parsing a string into a dice expression. The String can contain
	 * the same types of values as the expression property.
	 * Examples:
	 * <ul>
	 * 	<li>1d6</li>
	 *  <li>4d6d1</li>
	 *  <li>4d6+13+2d4</li>
	 * </ul>
	 * The most interesting methods of the returned object are getResult, toString and toEvaluatedString and toCompleteChatString
	 * @param diceExpression the string that should be parsed
	 * @return a Expression representing your expression
	 * @throws MacroException when the expression can not be parsed
	 */
	public Expression roll(String diceExpression) throws MacroException {
		try {
			return new ChatParser(diceExpression).parseExpression();
		} catch (ParseException e) {
			throw new MacroException("Could not parse dice Expression '"+diceExpression+"'",e);
		}
	}
	
	/**
	 * This will allow you to roll dice.<br>
	 * Example:<br>
	 * 1d6: roll(1).{@link DiceBuilder#d d}(6).{@link com.t3.dice.Dice#getResult getResult}();<br>
	 * 3d6e: roll(3).{@link DiceBuilder#d d}(6).{@link com.t3.dice.ExtendableDice#explode explode}().{@link com.t3.dice.Dice#getResult getResult}();
	 * @param numberOfDices the number of dices you want to roll
	 * @return a DiceBuilder that allows you to build your roll
	 */
	public DiceBuilder roll(int numberOfDices) {
		return DiceBuilder.roll(numberOfDices);
	}
}
