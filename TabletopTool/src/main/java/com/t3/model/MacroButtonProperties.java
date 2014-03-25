/*
 *  This software copyright by various authors including the RPTools.net
 *  development team, and licensed under the LGPL Version 3 or, at your
 *  option, any later version.
 *
 *  Portions of this software were originally covered under the Apache
 *  Software License, Version 1.1 or Version 2.0.
 *
 *  See the file LICENSE elsewhere in this distribution for license details.
 */

package com.t3.model;

import groovy.lang.Script;

import java.awt.Color;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.t3.client.AppPreferences;
import com.t3.client.T3MacroContext;
import com.t3.client.T3Util;
import com.t3.client.TabletopTool;
import com.t3.client.ui.MacroButtonHotKeyManager;
import com.t3.client.ui.macrobuttons.buttons.MacroButton;
import com.t3.client.ui.macrobuttons.buttons.MacroButtonPrefs;
import com.t3.macro.MacroEngine;
import com.t3.macro.MacroException;
import com.t3.util.StringUtil;

/**
 * This (data)class is used by all Macro Buttons, including campaign, global and token macro buttons.
 * 
 * @see com.t3.client.ui.macrobuttons.buttons.MacroButton
 */
public class MacroButtonProperties implements Comparable<MacroButtonProperties> {

	private static final Logger log = Logger.getLogger(MacroButtonProperties.class);

//	private transient static final List<String> HTMLColors = Arrays.asList("aqua", "black", "blue", "fuchsia", "gray", "green", "lime", "maroon", "navy", "olive", "purple", "red", "silver", "teal",
//			"white", "yellow");
	private transient MacroButton button;
	private transient Token token;
	private String saveLocation;
	private int index;
	private String colorKey;
	private String hotKey;
	private String command;
	private String label;
	private String group;
	private String sortby;
	private String fontColorKey;
	private String fontSize;
	private String minWidth;
	private String maxWidth;
	private Boolean allowPlayerEdits = true;
	private String toolTip;
	/**this will be automatically generated from the command string whenever it is changed */
	private transient Script compiledCommand;

	// constructor that creates a new instance, doesn't auto-save
	public MacroButtonProperties(int index, String colorKey, String hotKey, String command, String label, String group, String sortby, String fontColorKey, String fontSize, String minWidth, String maxWidth, String toolTip) {
		setIndex(index);
		setColorKey(colorKey);
		setHotKey(hotKey);
		setCommand(command);
		setLabel(label);
		setGroup(group);
		setSortby(sortby);
		setFontColorKey(fontColorKey);
		setFontSize(fontSize);
		setMinWidth(minWidth);
		setMaxWidth(maxWidth);
		setButton(null);
		setToken(null);
		setSaveLocation("");
		setAllowPlayerEdits(AppPreferences.getAllowPlayerMacroEditsDefault());
		setCompareGroup(true);
		setCompareSortPrefix(true);
		setCompareCommand(true);
		setToolTip(toolTip);
	}

	// constructor that creates a new instance, doesn't auto save
	public MacroButtonProperties(int index) {
		setIndex(index);
		setColorKey("");
		setHotKey(MacroButtonHotKeyManager.HOTKEYS[0]);
		setCommand("");
		setLabel("(new)");
		setGroup("");
		setSortby("");
		setFontColorKey("");
		setFontSize("");
		setMinWidth("");
		setMaxWidth("");
		setButton(null);
		setToken(null);
		setSaveLocation("");
		setAllowPlayerEdits(AppPreferences.getAllowPlayerMacroEditsDefault());
		setCompareGroup(true);
		setCompareSortPrefix(true);
		setCompareCommand(true);
		setToolTip(null);
	}

	// constructor for creating a new button in a specific button group, auto-saves
	public MacroButtonProperties(String panelClass, int index, String group) {
		this(index);
		setSaveLocation(panelClass);
		setGroup(group);
		setAllowPlayerEdits(AppPreferences.getAllowPlayerMacroEditsDefault());
		setCompareGroup(true);
		setCompareSortPrefix(true);
		setCompareCommand(true);
		setToolTip(null);
		save();
	}

	// constructor for creating a new token button in a specific button group, auto-saves
	public MacroButtonProperties(Token token, int index, String group) {
		this(index);
		setSaveLocation("Token");
		setToken(token);
		setGroup(group);
		setAllowPlayerEdits(AppPreferences.getAllowPlayerMacroEditsDefault());
		setCompareGroup(true);
		setCompareSortPrefix(true);
		setCompareCommand(true);
		setToolTip(null);
		save();
	}

	// constructor for creating a new copy of an existing button, auto-saves
	public MacroButtonProperties(String panelClass, int index, MacroButtonProperties properties) {
		this(index);
		setSaveLocation(panelClass);
		setColorKey(properties.getColorKey());
		// use the default hot key
		setCommand(properties.getCommand());
		setLabel(properties.getLabel());
		setGroup(properties.getGroup());
		setSortby(properties.getSortby());
		setFontColorKey(properties.getFontColorKey());
		setFontSize(properties.getFontSize());
		setMinWidth(properties.getMinWidth());
		setMaxWidth(properties.getMaxWidth());
		setAllowPlayerEdits(properties.getAllowPlayerEdits());
		setCompareGroup(properties.getCompareGroup());
		setCompareSortPrefix(properties.getCompareSortPrefix());
		setCompareCommand(properties.getCompareCommand());
		String tt = properties.getToolTip();
		setToolTip(tt);
		save();
	}

	// constructor for creating a new copy of an existing token button, auto-saves
	public MacroButtonProperties(Token token, int index, MacroButtonProperties properties) {
		this(index);
		setSaveLocation("Token");
		setToken(token);
		setColorKey(properties.getColorKey());
		// use the default hot key
		setCommand(properties.getCommand());
		setLabel(properties.getLabel());
		setGroup(properties.getGroup());
		setSortby(properties.getSortby());
		setFontColorKey(properties.getFontColorKey());
		setFontSize(properties.getFontSize());
		setMinWidth(properties.getMinWidth());
		setMaxWidth(properties.getMaxWidth());
		setAllowPlayerEdits(properties.getAllowPlayerEdits());
		setCompareGroup(properties.getCompareGroup());
		setCompareSortPrefix(properties.getCompareSortPrefix());
		setCompareCommand(properties.getCompareCommand());
		String tt = properties.getToolTip();
		setToolTip(tt);
		save();
	}

	// constructor for creating common macro buttons on selection panel
	public MacroButtonProperties(int index, MacroButtonProperties properties) {
		this(index);
		setToken((Token) null);
		setColorKey(properties.getColorKey());
		// use the default hot key
		setCommand(properties.getCommand());
		setLabel(properties.getLabel());
		setGroup(properties.getGroup());
		setSortby(properties.getSortby());
		setFontColorKey(properties.getFontColorKey());
		setFontSize(properties.getFontSize());
		setMinWidth(properties.getMinWidth());
		setMaxWidth(properties.getMaxWidth());
		setAllowPlayerEdits(properties.getAllowPlayerEdits());
		setCompareGroup(properties.getCompareGroup());
		setCompareSortPrefix(properties.getCompareSortPrefix());
		setCompareCommand(properties.getCompareCommand());
		setToolTip(properties.getToolTip());
		commonMacro = true;
	}

	public MacroButtonProperties(Token token, Map<String, String> props) {
		this(props.containsKey("index") ? Integer.parseInt(props.get("index")) : token.getMacroNextIndex());
		setToken(token);
		if (props.containsKey("saveLocation"))
			setSaveLocation(props.get("saveLocation"));
		if (props.containsKey("colorKey"))
			setColorKey(props.get("colorKey"));
		if (props.containsKey("hotKey"))
			setHotKey(props.get("hotKey"));
		if (props.containsKey("command"))
			setCommand(props.get("command"));
		if (props.containsKey("label"))
			setLabel(props.get("label"));
		if (props.containsKey("group"))
			setGroup(props.get("group"));
		if (props.containsKey("sortby"))
			setSortby(props.get("sortby"));
		if (props.containsKey("fontColorKey"))
			setFontColorKey(props.get("fontColorKey"));
		if (props.containsKey("fontSize"))
			setFontSize(props.get("fontSize"));
		if (props.containsKey("minWidth"))
			setMinWidth(props.get("minWidth"));
		if (props.containsKey("maxWidth"))
			setMaxWidth(props.get("maxWidth"));
		if (props.containsKey("allowPlayerEdits"))
			setAllowPlayerEdits(Boolean.valueOf(props.get("allowPlayerEdits")));
		if (props.containsKey("toolTip"))
			setToolTip(props.get("toolTip"));
		if (props.containsKey("commonMacro"))
			setCommonMacro(Boolean.valueOf(props.get("commonMacro")));
		if (props.containsKey("compareGroup"))
			setCompareGroup(Boolean.valueOf(props.get("compareGroup")));
		if (props.containsKey("compareSortPrefix"))
			setCompareSortPrefix(Boolean.valueOf(props.get("compareSortPrefix")));
		if (props.containsKey("compareCommand"))
			setCompareCommand(Boolean.valueOf(props.get("compareCommand")));
	}

	public void save() {
		if (saveLocation.equals("Token") && token != null) {
			getToken().saveMacroButtonProperty(this);
		} else if (saveLocation.equals("GlobalPanel")) {
			MacroButtonPrefs.savePreferences(this);
		} else if (saveLocation.equals("CampaignPanel")) {
			TabletopTool.getCampaign().saveMacroButtonProperty(this);
		}
	}

	public void executeMacro() {
		executeCommand(token);
	}

	/**
	 * The top level method for executing macros with the given list of tokens as context. In essence, this method calls
	 * the macro once for each token, and the token becomes the "impersonated" token for the duration of the macro
	 * barring any use of the <b>token()</b> or <b>switchToken()</b> roll options inside the macro itself.
	 * 
	 * @param tokenList
	 */
	public void executeMacro(Collection<Token> tokenList) {
		if (tokenList == null || tokenList.size() == 0) {
			executeCommand();
		} else if (commonMacro) {
			executeCommonMacro(tokenList);
		} else {
			if (tokenList.size() > 0) {
				for (Token token : tokenList) {
					executeCommand(token);
				}
			}
		}
	}

	private void executeCommonMacro(Collection<Token> tokenList) {
		if (compareCommand) {
			for (Token token : tokenList) {
				executeCommand(token, null);
			}
		} else {
			// We need to find the "matching" button for each token and ensure to run that one.
			for (Token nextToken : tokenList) {
				for (MacroButtonProperties nextMacro : nextToken.getMacroList(true)) {
					if (nextMacro.hashCodeForComparison() == hashCodeForComparison()) {
						nextMacro.executeCommand(nextToken);
					}
				}
			}
		}
	}

	private void executeCommand(Token token) {
		if (getCommand() != null)
			executeCommand(token,null);
	}

	private void executeCommand() {
		executeCommand(null,null);
	}
	
	public Object executeMacro(Token token) {
		return executeCommand(token,null);
	}
	
	private Object executeCommand(Token contextToken, Map<String, Object> arguments) {
		String loc;
		Object o = null;
		
		boolean trusted = false;
		if (allowPlayerEdits == null) {
			allowPlayerEdits = false;
		}
		if (saveLocation.equals("CampaignPanel") || !allowPlayerEdits) {
			trusted = true;
		}
		if (saveLocation.equals("GlobalPanel")) {
			loc = "global";
			trusted = TabletopTool.getPlayer().isGM();
		} else if (saveLocation.equals("CampaignPanel")) {
			loc = "campaign";
		} else if (contextToken != null) {
			loc = "Token:" + contextToken.getName();
		} else {
			loc = "chat";
		}
		
		T3MacroContext newMacroContext = new T3MacroContext(label, loc, trusted, index);
		try {
			if(compiledCommand==null)
				compileCommand();
			if(compiledCommand!=null)
				o=MacroEngine.getInstance().run(compiledCommand,arguments,contextToken,newMacroContext);
		} catch (MacroException e) {
			log.error("Error while trying to execute a macro from button",e);
			TabletopTool.addMessage(TextMessage.me(e.getHTMLErrorMessage()));
		}
		
		TabletopTool.getFrame().getCommandPanel().getCommandTextArea().requestFocusInWindow();
		return o;
	}

	private void compileCommand() throws MacroException {
		if(command!=null && !StringUtils.isEmpty(command))
			this.compiledCommand=MacroEngine.getInstance().compile(command);
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public void setSaveLocation(String saveLocation) {
		if (saveLocation.equals("ImpersonatePanel") || saveLocation.equals("SelectionPanel")) {
			this.saveLocation = "Token";
		} else {
			this.saveLocation = saveLocation;
		}
	}

	public void setButton(MacroButton button) {
		this.button = button;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getColorKey() {
		if (colorKey == null || colorKey.equals("")) {
			return "default";
		}
		return colorKey;
	}

	public void setColorKey(String colorKey) {
		if (T3Util.getColor(colorKey) != null)
			this.colorKey = colorKey;
	}

	public String getHotKey() {
		return hotKey;
	}

	public void setHotKey(String hotKey) {
		this.hotKey = hotKey;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		try {
			this.command = command;
			compileCommand();
		} catch (MacroException e) {
			//TODO replace this with a better error dialog
			throw new RuntimeException("This script you tried to save is not valid.", e);
		}
	}

	public String getLabel() {
		return (label == null ? "" : label);
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getGroup() {
		return (group == null ? "" : group);
	}

	public String getGroupForDisplay() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getSortby() {
		return (sortby == null ? "" : sortby);
	}

	public void setSortby(String sortby) {
		this.sortby = sortby;
	}

	public static String[] getFontColors() {
//		return (String[]) HTMLColors.toArray();
		String[] array = T3Util.getColorNames().toArray(new String[0]);
		return array;
	}

	/**
	 * Returns the font color of this button as an HTML string. It might be one of the 16 colors defined by the W3C as a
	 * standard HTML color (see <code>COLOR_MAP_HTML</code> for a list), but if it's not then the color is converted to
	 * CSS format <b>#FF00FF</b> format and that string is returned.
	 * 
	 * @return
	 */
	public String getFontColorAsHtml() {
		Color c = null;
		String font = getFontColorKey();
		if (T3Util.isHtmlColor(font)) {
			return font;
		}
		c = T3Util.getColor(font);
		if (c != null) {
			return "#" + Integer.toHexString(c.getRGB()).substring(2);
		}
		return "black";
	}

	public String getFontColorKey() {
		if (fontColorKey == null || StringUtil.isEmpty(fontColorKey)) {
			fontColorKey = "black";
			return fontColorKey;
		}
		Color c = T3Util.getColor(fontColorKey);
		if (c != null) {
			return fontColorKey;
		}
		return "black";
	}

	public void setFontColorKey(String fontColorKey) {
		if (T3Util.getColor(fontColorKey) != null)
			this.fontColorKey = fontColorKey;
	}

	public String getFontSize() {
		return (fontSize == null || fontSize.equals("") ? "1.00em" : fontSize);
	}

	public void setFontSize(String fontSize) {
		this.fontSize = (fontSize == null || fontSize.equals("") ? "1.00em" : fontSize);
	}

	public String getMinWidth() {
		return (minWidth == null ? "" : minWidth);
	}

	public void setMinWidth(String minWidth) {
		this.minWidth = minWidth;
	}

	public String getMaxWidth() {
		return (maxWidth == null ? "" : maxWidth);
	}

	public void setMaxWidth(String maxWidth) {
		this.maxWidth = maxWidth;
	}

	public Boolean getAllowPlayerEdits() {
		return allowPlayerEdits;
	}

	public void setAllowPlayerEdits(Boolean value) {
		allowPlayerEdits = value;
	}

	public String getSaveLocation() {
		return saveLocation;
	}

	public void setToolTip(String tt) {
		toolTip = (tt == null ? "" : tt);
	}

	public String getToolTip() {
		if (toolTip == null)
			toolTip = "";
		return toolTip;
	}

	public String getEvaluatedToolTip() {

		if (toolTip == null) {
			return "";
		}

		if (!toolTip.trim().startsWith("{") && !toolTip.trim().startsWith("[")) {
			return toolTip;
		}

		try {
			T3MacroContext context = new T3MacroContext("ToolTip", token != null ? token.getName() : "", false, index);
			if (log.isDebugEnabled()) {
				log.debug("Evaluating toolTip: " + (token != null ? "for token " + token.getName() + "(" + token.getId() + ")" : "")
						+ "----------------------------------------------------------------------------------");
			}
			return TabletopTool.getParser().parseLine(token, toolTip, context);
		} catch (MacroException pe) {
			return toolTip;
		}
	}

	public boolean isDuplicateMacro(String source, Token token) {
		int macroHashCode = hashCodeForComparison();
		List<MacroButtonProperties> existingMacroList = null;
		if (source.equalsIgnoreCase("CampaignPanel")) {
			existingMacroList = TabletopTool.getCampaign().getMacroButtonPropertiesArray();
		} else if (source.equalsIgnoreCase("GlobalPanel")) {
			existingMacroList = MacroButtonPrefs.getButtonProperties();
		} else if (token != null) {
			existingMacroList = token.getMacroList(false);
		} else {
			return false;
		}
		for (MacroButtonProperties existingMacro : existingMacroList) {
			if (existingMacro.hashCodeForComparison() == macroHashCode) {
				return true;
			}
		}
		return false;
	}

	public void reset() {
		colorKey = "default";
		hotKey = MacroButtonHotKeyManager.HOTKEYS[0];
		command = "";
		compiledCommand=null;
		label = String.valueOf(index);
		group = "";
		sortby = "";
		fontColorKey = "black";
		fontSize = "";
		minWidth = "";
		maxWidth = "";
		toolTip = "";
	}

	//TODO: may have to rewrite hashcode and equals to only take index into account
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MacroButtonProperties that = (MacroButtonProperties) o;

		if (index != that.index) {
			return false;
		}
		if (colorKey != null ? !colorKey.equals(that.colorKey) : that.colorKey != null) {
			return false;
		}
		if (command != null ? !command.equals(that.command) : that.command != null) {
			return false;
		}
		if (hotKey != null ? !hotKey.equals(that.hotKey) : that.hotKey != null) {
			return false;
		}
		if (label != null ? !label.equals(that.label) : that.label != null) {
			return false;
		}
		if (group != null ? !group.equals(that.group) : that.group != null) {
			return false;
		}
		if (sortby != null ? !sortby.equals(that.sortby) : that.sortby != null) {
			return false;
		}
		if (fontColorKey != null ? !fontColorKey.equals(that.fontColorKey) : that.fontColorKey != null) {
			return false;
		}
		if (fontSize != null ? !fontSize.equals(that.fontSize) : that.fontSize != null) {
			return false;
		}
		if (minWidth != null ? !minWidth.equals(that.minWidth) : that.minWidth != null) {
			return false;
		}
		if (maxWidth != null ? !maxWidth.equals(that.maxWidth) : that.maxWidth != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() { // modified so longest strings are at the end
		int result;
		result = index;
		result = 31 * result + (minWidth != null ? minWidth.hashCode() : 0);
		result = 31 * result + (maxWidth != null ? maxWidth.hashCode() : 0);
		result = 31 * result + (fontSize != null ? fontSize.hashCode() : 0);
		result = 31 * result + (fontColorKey != null ? fontColorKey.hashCode() : 0);
		result = 31 * result + (colorKey != null ? colorKey.hashCode() : 0);
		result = 31 * result + (hotKey != null ? hotKey.hashCode() : 0);
		result = 31 * result + (label != null ? label.hashCode() : 0);
		result = 31 * result + (group != null ? group.hashCode() : 0);
		result = 31 * result + (sortby != null ? sortby.hashCode() : 0);
		result = 31 * result + (command != null ? command.hashCode() : 0);
		return result;
	}

	// Don't include the index, so you can compare all the other properties between two macros
	// Also don't include hot key since they can't be the same anyway, or cosmetic fields
	public int hashCodeForComparison() {
		int result;
		result = 0;
		result = 31 * result + (getLabel() != null ? label.hashCode() : 0);
		result = 31 * result + (getCompareGroup() && group != null ? group.hashCode() : 0);
		result = 31 * result + (getCompareSortPrefix() && sortby != null ? sortby.hashCode() : 0);
		result = 31 * result + (getCompareCommand() && command != null ? command.hashCode() : 0);
		return result;
	}

	// function to enable sorting of buttons; uses the group first, then sortby field
	// concatenated with the label field.  Case Insensitive
	public int compareTo(MacroButtonProperties b2) throws ClassCastException {
		if (b2 != this) {
			String b1group = getGroup();
			if (b1group == null)
				b1group = "";
			String b1sortby = getSortby();
			if (b1sortby == null)
				b1sortby = "";
			String b1label = getLabel();
			if (b1label == null)
				b1label = "";

			String b2group = b2.getGroup();
			if (b2group == null)
				b2group = "";
			String b2sortby = b2.getSortby();
			if (b2sortby == null)
				b2sortby = "";
			String b2label = b2.getLabel();
			if (b2label == null)
				b2label = "";

			// now parse the sort strings to help dice codes sort properly, use space as a separator
			String b1string = modifySortString(" " + b1group + " " + b1sortby + " " + b1label);
			String b2string = modifySortString(" " + b2group + " " + b2sortby + " " + b2label);
			return b1string.compareToIgnoreCase(b2string);
		}
		return 0;
	}

	// function to pad numbers with leading zeroes to help sort them appropriately.
	// So this will turn a 2d6 into 0002d0006, and 10d6 into 0010d0006, so the 2d6
	// will sort as lower.
	private static final Pattern sortStringPattern = Pattern.compile("(\\d+)");

	private static String modifySortString(String str) {
		StringBuffer result = new StringBuffer();
		Matcher matcher = sortStringPattern.matcher(str);
		while (matcher.find()) {
			matcher.appendReplacement(result, paddingString(matcher.group(1), 4, '0', true));
		}
		matcher.appendTail(result);
		return result.toString();
	}

	// function found at http://www.rgagnon.com/javadetails/java-0448.html
	// to pad a string by inserting additional characters
	public static String paddingString(String s, int n, char c, boolean paddingLeft) {
		StringBuffer str = new StringBuffer(s);
		int strLength = str.length();
		if (n > 0 && n > strLength) {
			for (int i = 0; i <= n; i++) {
				if (paddingLeft) {
					if (i < n - strLength)
						str.insert(0, c);
				} else {
					if (i > strLength)
						str.append(c);
				}
			}
		}
		return str.toString();
	}

	// Begin comparison customization

	private Boolean commonMacro = false;
	private Boolean compareGroup = true;
	private Boolean compareSortPrefix = true;
	private Boolean compareCommand = true;

	public Boolean getCommonMacro() {
		return commonMacro;
	}

	public void setCommonMacro(Boolean value) {
		commonMacro = value;
	}

	public Boolean getCompareGroup() {
		return compareGroup;
	}

	public void setCompareGroup(Boolean value) {
		compareGroup = value;
	}

	public Boolean getCompareSortPrefix() {
		return compareSortPrefix;
	}

	public void setCompareSortPrefix(Boolean value) {
		compareSortPrefix = value;
	}

	public Boolean getCompareCommand() {
		return compareCommand;
	}

	public void setCompareCommand(Boolean value) {
		compareCommand = value;
	}

	public static void fixOldMacroCompare(MacroButtonProperties oldMacro) {
		if (oldMacro.getCommonMacro() == null) {
			oldMacro.setCommonMacro(new Boolean(true));
		}
		if (oldMacro.getAllowPlayerEdits() == null) {
			oldMacro.setAllowPlayerEdits(new Boolean(true));
		}
		if (oldMacro.getCompareCommand() == null) {
			oldMacro.setCompareCommand(new Boolean(true));
		}
		if (oldMacro.getCompareGroup() == null) {
			oldMacro.setCompareGroup(new Boolean(true));
		}
		if (oldMacro.getCompareSortPrefix() == null) {
			oldMacro.setCompareSortPrefix(new Boolean(true));
		}
	}

	public static void fixOldMacroSetCompare(List<MacroButtonProperties> oldMacros) {
		for (MacroButtonProperties nextMacro : oldMacros) {
			fixOldMacroCompare(nextMacro);
		}
	}

	public Object readResolve() {
		if (commonMacro == null)
			commonMacro = false;
		if (compareGroup == null)
			compareGroup = true;
		if (compareSortPrefix == null)
			compareSortPrefix = true;
		if (compareCommand == null)
			compareCommand = true;
		if (allowPlayerEdits == null)
			allowPlayerEdits = true;
		return this;
	}

	public Object executeMacro(Token token, Map<String, Object> arguments) {
		return executeCommand(token,arguments);
	}
}
