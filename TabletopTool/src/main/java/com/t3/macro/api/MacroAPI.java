package com.t3.macro.api;

import groovy.lang.Script;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.t3.chatparser.generated.ChatParser;
import com.t3.chatparser.generated.ParseException;
import com.t3.dice.DiceBuilder;
import com.t3.dice.expression.DiceExpression;
import com.t3.client.TabletopTool;
import com.t3.client.ui.commandpanel.ChatExecutor;
import com.t3.language.I18N;
import com.t3.macro.MacroException;
import com.t3.macro.api.functions.DialogFunctions;
import com.t3.macro.api.functions.InfoFunctions;
import com.t3.macro.api.functions.MapFunctions;
import com.t3.macro.api.functions.PathFunctions;
import com.t3.macro.api.functions.input.InputFunctions;
import com.t3.macro.api.functions.player.PlayerFunctions;
import com.t3.macro.api.functions.token.TokenLocation;
import com.t3.macro.api.views.TokenView;
import com.t3.model.CellPoint;
import com.t3.model.Player;
import com.t3.model.TextMessage;
import com.t3.model.ZonePoint;
import com.t3.model.campaign.TokenProperty;

//FIXME make this package into a PLUG-IN
public abstract class MacroAPI extends Script {
	
	public final InfoFunctions info;
	public final PlayerFunctions player;
	public final MapFunctions map;
	public final DialogFunctions dialog;
	public final PathFunctions path;

	public MacroAPI() {
		super();
		this.info=new InfoFunctions();
		this.player=new PlayerFunctions();
		this.map=new MapFunctions();
		this.dialog=new DialogFunctions();
		this.path=new PathFunctions();
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

	public void goTo(TokenView token) {
		TokenLocation tl=token.getLocation(false);
		TabletopTool.getFrame().getCurrentZoneRenderer().centerOn(new ZonePoint(tl.getX(), tl.getY()));
	}
	
	public void goTo(int x, int y) {
		goTo(x,y,true);
	}
	
	public void goTo(int x, int y, boolean gridUnit) {
		if(gridUnit)
			TabletopTool.getFrame().getCurrentZoneRenderer().centerOn(new CellPoint(x, y));
		else
			TabletopTool.getFrame().getCurrentZoneRenderer().centerOn(new ZonePoint(x, y));
	}
	
	/**
	 * Gets all the property names.
	 * @return a string list containing the property names.
	 */
	public List<String> getAllPropertyNames() {
		Map<String, List<TokenProperty>> pmap = TabletopTool.getCampaign().getCampaignProperties().getTokenTypeMap();
		ArrayList<String> namesList = new ArrayList<String>();

		for (Entry<String, List<TokenProperty>> entry : pmap.entrySet()) {
			for (TokenProperty tp : entry.getValue()) {
				namesList.add(tp.getName());
			}
		}
		return namesList;
	}
	
	/**
	 * Gets all the property names for the specified type.
	 * 
	 * @param type  The type of property.
	 * @return a string list containing the property names.
	 */
	public List<String> getAllPropertyNames(String type) {
		List<TokenProperty> props = TabletopTool.getCampaign().getCampaignProperties().getTokenPropertyList(type);
		ArrayList<String> namesList = new ArrayList<String>();
		for (TokenProperty tp : props) {
			namesList.add(tp.getName());
		}
		return namesList;
	}

	public DiceExpression roll(String diceExpression) throws MacroException {
		try {
			return new ChatParser(diceExpression).parseDiceExpression();
		} catch (ParseException e) {
			throw new MacroException("Could not parse dice Expression '"+diceExpression+"'",e);
		}
	}
	
	public DiceBuilder roll(int numberOfDices) {
		return DiceBuilder.roll(numberOfDices);
	}
	
	/**
	 * <pre>
	 * <span style="font-family:sans-serif;">The input() function prompts the user to input several variable values at once.
	 * 
	 * Each of the string parameters has the following format:
	 *     "varname|value|prompt|inputType|options"
	 * 
	 * Only the first section is required.
	 *     varname   - the variable name to be assigned
	 *     value     - sets the initial contents of the input field
	 *     prompt    - UI text shown for the variable
	 *     inputType - specifies the type of input field
	 *     options   - a string of the form "opt1=val1; opt2=val2; ..."
	 * 
	 * The inputType field can be any of the following (defaults to TEXT):
	 *     TEXT  - A text field.
	 *             "value" sets the initial contents.
	 *             The return value is the string in the text field.
	 *             Option: WIDTH=nnn sets the width of the text field (default 16).
	 *     LIST  - An uneditable combo box.
	 *             "value" populates the list, and has the form "item1,item2,item3..." (trailing empty strings are dropped)
	 *             The return value is the numeric index of the selected item.
	 *             Option: SELECT=nnn sets the initial selection (default 0).
	 *             Option: VALUE=STRING returns the string contents of the selected item (default NUMBER).
	 *             Option: TEXT=FALSE suppresses the text of the list item (default TRUE).
	 *             Option: ICON=TRUE causes icon asset URLs to be extracted from the "value" and displayed (default FALSE).
	 *             Option: ICONSIZE=nnn sets the size of the icons (default 50).
	 *     CHECK - A checkbox.
	 *             "value" sets the initial state of the box (anything but "" or "0" checks the box)
	 *             The return value is 0 or 1.
	 *             No options.
	 *     RADIO - A group of radio buttons.
	 *             "value" is a list "name1, name2, name3, ..." which sets the labels of the buttons.
	 *             The return value is the index of the selected item.
	 *             Option: SELECT=nnn sets the initial selection (default 0).
	 *             Option: ORIENT=H causes the radio buttons to be laid out on one line (default V).
	 *             Option: VALUE=STRING causes the return value to be the string of the selected item (default NUMBER).
	 *     LABEL - A label.
	 *             The "varname" is ignored and no value is assigned to it.
	 *             Option: TEXT=FALSE, ICON=TRUE, ICONSIZE=nnn, as in the LIST type.
	 *     PROPS - A sub-panel with multiple text boxes.
	 *             "value" contains a StrProp of the form "key1=val1; key2=val2; ..."
	 *             One text box is created for each key, populated with the matching value.
	 *             Option: SETVARS=SUFFIXED causes variable assignment to each key name, with appended "_" (default NONE).
	 *             Option: SETVARS=UNSUFFIXED causes variable assignment to each key name.
	 *     TAB   - A tabbed dialog tab is created.  Subsequent variables are contained in the tab.
	 *             Option: SELECT=TRUE causes this tab to be shown at start (default SELECT=FALSE).
	 * 
	 *  All inputTypes except TAB accept the option SPAN=TRUE, which causes the prompt to be hidden and the input
	 *  control to span both columns of the dialog layout (default FALSE).
	 * </span>
	 * </pre>
	 * @param parameters a list of strings containing information as described above
	 * @return a HashMap with the returned values or null if the user clicked on cancel
	 * @author knizia.fan
	 * @throws MacroException 
	 */
	public static Map<String, String> input(String... parameters) throws MacroException {
		return InputFunctions.input(null,parameters);
	}
	
	/**
	 * <pre>
	 * <span style="font-family:sans-serif;">The input() function prompts the user to input several variable values at once.
	 * 
	 * Each of the string parameters has the following format:
	 *     "varname|value|prompt|inputType|options"
	 * 
	 * Only the first section is required.
	 *     varname   - the variable name to be assigned
	 *     value     - sets the initial contents of the input field
	 *     prompt    - UI text shown for the variable
	 *     inputType - specifies the type of input field
	 *     options   - a string of the form "opt1=val1; opt2=val2; ..."
	 * 
	 * The inputType field can be any of the following (defaults to TEXT):
	 *     TEXT  - A text field.
	 *             "value" sets the initial contents.
	 *             The return value is the string in the text field.
	 *             Option: WIDTH=nnn sets the width of the text field (default 16).
	 *     LIST  - An uneditable combo box.
	 *             "value" populates the list, and has the form "item1,item2,item3..." (trailing empty strings are dropped)
	 *             The return value is the numeric index of the selected item.
	 *             Option: SELECT=nnn sets the initial selection (default 0).
	 *             Option: VALUE=STRING returns the string contents of the selected item (default NUMBER).
	 *             Option: TEXT=FALSE suppresses the text of the list item (default TRUE).
	 *             Option: ICON=TRUE causes icon asset URLs to be extracted from the "value" and displayed (default FALSE).
	 *             Option: ICONSIZE=nnn sets the size of the icons (default 50).
	 *     CHECK - A checkbox.
	 *             "value" sets the initial state of the box (anything but "" or "0" checks the box)
	 *             The return value is 0 or 1.
	 *             No options.
	 *     RADIO - A group of radio buttons.
	 *             "value" is a list "name1, name2, name3, ..." which sets the labels of the buttons.
	 *             The return value is the index of the selected item.
	 *             Option: SELECT=nnn sets the initial selection (default 0).
	 *             Option: ORIENT=H causes the radio buttons to be laid out on one line (default V).
	 *             Option: VALUE=STRING causes the return value to be the string of the selected item (default NUMBER).
	 *     LABEL - A label.
	 *             The "varname" is ignored and no value is assigned to it.
	 *             Option: TEXT=FALSE, ICON=TRUE, ICONSIZE=nnn, as in the LIST type.
	 *     PROPS - A sub-panel with multiple text boxes.
	 *             "value" contains a StrProp of the form "key1=val1; key2=val2; ..."
	 *             One text box is created for each key, populated with the matching value.
	 *             Option: SETVARS=SUFFIXED causes variable assignment to each key name, with appended "_" (default NONE).
	 *             Option: SETVARS=UNSUFFIXED causes variable assignment to each key name.
	 *     TAB   - A tabbed dialog tab is created.  Subsequent variables are contained in the tab.
	 *             Option: SELECT=TRUE causes this tab to be shown at start (default SELECT=FALSE).
	 * 
	 *  All inputTypes except TAB accept the option SPAN=TRUE, which causes the prompt to be hidden and the input
	 *  control to span both columns of the dialog layout (default FALSE).
	 * </span>
	 * </pre>
	 * @param parameters a list of strings containing information as described above
	 * @return a HashMap with the returned values or null if the user clicked on cancel
	 * @author knizia.fan
	 * @throws MacroException 
	 */
	public static Map<String, String> input(TokenView token, String... parameters) throws MacroException {
		return InputFunctions.input(token, parameters);
	}
}
