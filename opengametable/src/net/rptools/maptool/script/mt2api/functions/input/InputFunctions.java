package net.rptools.maptool.script.mt2api.functions.input;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.rptools.lib.MD5Key;
import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.language.I18N;
import net.rptools.maptool.script.MT2ScriptException;
import net.rptools.maptool.script.mt2api.TokenView;
import net.rptools.maptool.util.ImageManager;

import org.apache.commons.lang3.StringUtils;

public class InputFunctions {
	
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
	 * @throws MT2ScriptException 
	 */
	public Map<String, String> input(String... parameters) throws MT2ScriptException {
		return input(null,parameters);
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
	 * @throws MT2ScriptException 
	 */
	public Map<String, String> input(TokenView token, String... parameters) throws MT2ScriptException {
		// Extract the list of specifier strings from the parameters
		// "name | value | prompt | inputType | options"
		List<String> varStrings = new ArrayList<String>();
		for (Object param : parameters) {
			String paramStr = (String) param;
			if (StringUtils.isEmpty(paramStr)) {
				continue;
			}
			// Multiple vars can be packed into a string, separated by "##"
			for (String varString : StringUtils.splitByWholeSeparator(paramStr, "##")) {
				if (StringUtils.isEmpty(paramStr)) {
					continue;
				}
				varStrings.add(varString);
			}
		}

		// Create VarSpec objects from each variable's specifier string
		List<VarSpec> varSpecs = new ArrayList<VarSpec>();
		for (String specifier : varStrings) {
			VarSpec vs;
			try {
				vs = new VarSpec(specifier);
			} catch (VarSpec.SpecifierException se) {
				throw new MT2ScriptException(se);
			} catch (InputType.OptionException oe) {
				throw new MT2ScriptException(I18N.getText("macro.function.input.invalidOptionType", oe.key, oe.value, oe.type, specifier));
			}
			varSpecs.add(vs);
		}

		// Check if any variables were defined
		if (varSpecs.isEmpty())
			return Collections.emptyMap(); // No work to do, so treat it as a successful invocation.

		// UI step 1 - First, see if a token is given

		String dialogTitle = "Input Values";
		if (token != null) {
			String name = token.getName(), gm_name = token.getGMName();
			boolean isGM = MapTool.getPlayer().isGM();
			String extra = "";

			if (isGM && gm_name != null && gm_name.compareTo("") != 0)
				extra = " for " + gm_name;
			else if (name != null && name.compareTo("") != 0)
				extra = " for " + name;

			dialogTitle = dialogTitle + extra;
		}

		// UI step 2 - build the panel with the input fields
		InputPanel ip = new InputPanel(varSpecs);

		// Calculate the height
		// TODO: remove this workaround
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		int maxHeight = screenHeight * 3 / 4;
		Dimension ipPreferredDim = ip.getPreferredSize();
		if (maxHeight < ipPreferredDim.height) {
			ip.modifyMaxHeightBy(maxHeight - ipPreferredDim.height);
		}

		// UI step 3 - show the dialog
		JOptionPane jop = new JOptionPane(ip, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		JDialog dlg = jop.createDialog(MapTool.getFrame(), dialogTitle);

		// Set up callbacks needed for desired runtime behavior
		dlg.addComponentListener(new FixupComponentAdapter(ip));

		dlg.setVisible(true);
		int dlgResult = JOptionPane.CLOSED_OPTION;
		try {
			dlgResult = (Integer) jop.getValue();
		} catch (NullPointerException npe) {
		}
		dlg.dispose();

		if (dlgResult == JOptionPane.CANCEL_OPTION || dlgResult == JOptionPane.CLOSED_OPTION)
			return null;

		HashMap<String, String> results=new HashMap<String, String>();
		
		// Finally, assign values from the dialog box to the variables
		for (ColumnPanel cp : ip.columnPanels) {
			List<VarSpec> panelVars = cp.varSpecs;
			List<JComponent> panelControls = cp.inputFields;
			int numPanelVars = panelVars.size();
			StringBuilder allAssignments = new StringBuilder(); // holds all values assigned in this tab

			for (int varCount = 0; varCount < numPanelVars; varCount++) {
				VarSpec vs = panelVars.get(varCount);
				JComponent comp = panelControls.get(varCount);
				String newValue = null;
				switch (vs.inputType) {
				case TEXT: {
					newValue = ((JTextField) comp).getText();
					break;
				}
				case LIST: {
					Integer index = ((JComboBox) comp).getSelectedIndex();
					if (vs.optionValues.optionEquals("VALUE", "STRING")) {
						newValue = vs.valueList.get(index);
					} else { // default is "NUMBER"
						newValue = index.toString();
					}
					break;
				}
				case CHECK: {
					Integer value = ((JCheckBox) comp).isSelected() ? 1 : 0;
					newValue = value.toString();
					break;
				}
				case RADIO: {
					// This code assumes that the Box container returns components
					// in the same order that they were added.
					Component[] comps = ((Box) comp).getComponents();
					int componentCount = 0;
					Integer index = 0;
					for (Component c : comps) {
						if (c instanceof JRadioButton) {
							JRadioButton radio = (JRadioButton) c;
							if (radio.isSelected())
								index = componentCount;
						}
						componentCount++;
					}
					if (vs.optionValues.optionEquals("VALUE", "STRING")) {
						newValue = vs.valueList.get(index);
					} else { // default is "NUMBER"
						newValue = index.toString();
					}
					break;
				}
				case LABEL: {
					newValue = null;
					// The variable name is ignored and not set.
					break;
				}
				case PROPS: {
					// Read out and assign all the subvariables.
					// The overall return value is a property string (as in StrPropFunctions.java) with all the new settings.
					Component[] comps = ((JPanel) comp).getComponents();
					StringBuilder sb = new StringBuilder();
					int setVars = 0; // "NONE", no assignments made
					if (vs.optionValues.optionEquals("SETVARS", "SUFFIXED"))
						setVars = 1;
					if (vs.optionValues.optionEquals("SETVARS", "UNSUFFIXED"))
						setVars = 2;
					if (vs.optionValues.optionEquals("SETVARS", "TRUE"))
						setVars = 2; // for backward compatibility
					for (int compCount = 0; compCount < comps.length; compCount += 2) {
						String key = ((JLabel) comps[compCount]).getText().split("\\:")[0]; // strip trailing colon
						String value = ((JTextField) comps[compCount + 1]).getText();
						sb.append(key);
						sb.append("=");
						sb.append(value);
						sb.append(" ; ");
						switch (setVars) {
						case 0:
							// Do nothing
							break;
						case 1:
							results.put(key + "_", value);
							break;
						case 2:
							results.put(key, value);
							break;
						}
					}
					newValue = sb.toString();
					break;
				}
				default:
					// should never happen
					newValue = null;
					break;
				}
				// Set the variable to the value we got from the dialog box.
				if (newValue != null) {
					results.put(vs.name, newValue.trim());
					allAssignments.append(vs.name + "=" + newValue.trim() + " ## ");
				}
			}
			if (cp.tabVarSpec != null) {
				results.put(cp.tabVarSpec.name, allAssignments.toString());
			}
		}

		return results; // success

		// for debugging:
		//return debugOutput(varSpecs);
	}
	
	/**
	 * Gets icon from the asset manager. Code copied and modified from
	 * EditTokestaticnDialog.java
	 */
	static ImageIcon getIcon(String id, int size, ImageObserver io) {
		// Extract the MD5Key from the URL
		if (id == null)
			return null;
		MD5Key assetID = new MD5Key(id);

		// Get the base image && find the new size for the icon
		BufferedImage assetImage = ImageManager.getImage(assetID, io);

		// Resize
		if (assetImage.getWidth() > size || assetImage.getHeight() > size) {
			Dimension dim = new Dimension(assetImage.getWidth(), assetImage.getWidth());
			if (dim.height < dim.width) {
				dim.height = (int) ((dim.height / (double) dim.width) * size);
				dim.width = size;
			} else {
				dim.width = (int) ((dim.width / (double) dim.height) * size);
				dim.height = size;
			}
			BufferedImage image = new BufferedImage(dim.width, dim.height, Transparency.BITMASK);
			Graphics2D g = image.createGraphics();
			g.drawImage(assetImage, 0, 0, dim.width, dim.height, null);
			assetImage = image;
		}
		return new ImageIcon(assetImage);
	}
}

//A sample for the TAB control
/*
* 
* [h: status = input( "tab0 | Abilities | Enter abilities here | TAB",
* "blah|Max value is 18|Note|LABEL ## abils|Str=8;Con=8;Dex=8;Int=8;Wis=8;Cha=8|Abilities|PROPS"
* , "txt0|text on tab 0|",
* "tab1 | Options | Various options | TAB |select=true",
* "txt|default text ## ck|1|Toggle me|CHECK ## list|a,b,This is a very long item,d|Pick one|LIST ## foo|foo"
* )] [h: abort(status)] tab0 is set to [tab0] <br>tab1 is set to [tab1] <br>
* <br>list is set to [list] <br>get list from the tab1 variable:
* [getStrProp(tab1,"list","","##")]
*/

//A tall tab control, to demonstrate scrolling
/*
* [h: props =
* "a=3;b=bob;c=cow;d=40;e=55;f=33;g=big time;h=hello;i=interesting;j=jack;k=kow;l=leap;m=moon;"
* ] [h: status = input( "tab0 | Settings | Settings tooltip | TAB",
* "foo|||CHECK ## bar|bar",
* "tab1 | Options | Options tooltip | TAB | select=true",
* "num|a,b,c,d|Pick one|list ## zot|zot ## zam|zam",
* "tab2 | Options2 | Options2 tooltip | TAB | ", "p | " + props +
* " | Sample props | PROPS ## p2 | " + props + " | More props | PROPS ## p3 | "
* + props + " | Even more props | PROPS ## p4 | " + props +
* " | Still more props | PROPS", "num2|a,b,c,d|Pick one|list ",
* "num3|after all it's only a listbox here now isn't it dear?,b,c,d|Pick one|list|span=true ## ee|ee ## ff|ff ## gg|gg ## hh|hh ## ii|ii ## jj|jj ## kk|kk"
* , "tab3 | Empty | nothin' | TAB" )] [h: abort(status)] tab0 is [tab0]<br> foo
* is [foo]<br> tab1 is [tab1]<br> num is [num]<br> tab2 is [tab2]<br> tab3 is
* [tab3]<br>
*/

//Here's a sample input to exercise the options
/*
* 
* Original props = [props =
* "Name=Longsword +1; Damage=1d8+1; Crit=1d6; Keyword=fire;"] [H: input( "foo",
* "YourName|George Washington|Your name|TEXT",
* "Weapon|Axe,Sword,Mace|Choose weapon|LIST",
* "WarCry|Attack!,No surrender!,I give up!|Pick a war cry|LIST|VALUE=STRING select=1"
* , "CA || Combat advantage|     CHECK|",
* "props |"+props+"|Weapon properties|PROPS|setvars=true",
* "UsePower |1|Use the power|CHECK",
* "Weight|light,medium,heavy||RADIO|ORIENT=H select=1",
* "Ambition|Survive today, Defeat my enemies, Rule the world, Become immortal||RADIO|VALUE=STRING"
* ,
* "bar | a, b, c, d, e, f, g , h     ,i  j, k   |Radio button test   |  RADIO       | select=5 value = string ; oRiEnT   =h;;;;"
* )]<br> <i>New values of variables:</i> <br>foo is [foo] <br>YourName is
* [YourName] <br>Weapon is [Weapon] <br>WarCry is [WarCry] <br>CA is [CA]
* <br>props is [props] <br>UsePower is [UsePower] <br>Weight is [Weight]
* <br>Ambition is [Ambition] <br> <br>Name is [Name], Damage is [Damage], Crit
* is [Crit], Keyword is [Keyword]
*/

//Here's a longer version of that sample, but the 9/14/08 checked in version of MapTool gets
//a stack overflow when this is pasted into chat (due to its length?)
/*
* 
* Original props = [props =
* "Name=Longsword +1; Damage=1d8+1; Crit=1d6; Keyword=fire;"] [h: setPropVars =
* 1] [H: input( "foo", "YourName|George Washington|Your name|TEXT",
* "Weapon|Axe,Sword,Mace|Choose weapon|LIST",
* "WarCry|Attack!,No surrender!,I give up!|Pick a war cry|LIST|VALUE=STRING select=1"
* , "CA || Combat advantage|     CHECK|",
* "props |"+props+"|Weapon properties|PROPS|setvars=true",
* "UsePower |1|Use the power|CHECK",
* "Weight|light,medium,heavy||RADIO|ORIENT=H select=1",
* "Ambition|Survive today, Defeat my enemies, Rule the world, Become immortal||RADIO|VALUE=STRING"
* ,
* "bar | a, b, c, d, e, f, g , h     ,i  j, k   |Radio button test   |  RADIO       | select=5 value = string ; oRiEnT   =h;;;;"
* )]<br> <i>New values of variables:</i> <table border=0><tr
* style='font-weight:bold;'><td>Name&nbsp;&nbsp;&nbsp;</td><td>Value</td></tr>
* <tr><td>foo&nbsp;&nbsp;&nbsp;</td><td>{foo}</td></tr>
* <tr><td>YourName&nbsp;&nbsp;&nbsp;</td><td>{YourName}</td></tr>
* <tr><td>Weapon&nbsp;&nbsp;&nbsp;</td><td>{Weapon}</td></tr>
* <tr><td>WarCry&nbsp;&nbsp;&nbsp;</td><td>{WarCry}</td></tr>
* <tr><td>CA&nbsp;&nbsp;&nbsp;</td><td>{CA}&nbsp;&nbsp;&nbsp;</td></tr>
* <tr><td>props&nbsp;&nbsp;&nbsp;</td><td>{props}&nbsp;&nbsp;&nbsp;</td></tr>
* <tr
* ><td>UsePower&nbsp;&nbsp;&nbsp;</td><td>{UsePower}&nbsp;&nbsp;&nbsp;</td></
* tr>
* <tr><td>Weight&nbsp;&nbsp;&nbsp;</td><td>{Weight}&nbsp;&nbsp;&nbsp;</td></tr>
* <
* tr><td>Ambition&nbsp;&nbsp;&nbsp;</td><td>{Ambition}&nbsp;&nbsp;&nbsp;</td></
* tr> <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
* <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr> {if
* (setPropVars,
* "<tr><td>Name&nbsp;&nbsp;&nbsp;</td><td>"+Name+"&nbsp;&nbsp;&nbsp;</td></tr>
* <
* tr><td>Damage&nbsp;&nbsp;&nbsp;</td><td>"+Damage+"&nbsp;&nbsp;&nbsp;</td></tr
* > <tr><td>Crit&nbsp;&nbsp;&nbsp;</td><td>"+Crit+"&nbsp;&nbsp;&nbsp;</td></tr>
* <
* tr><td>Keyword&nbsp;&nbsp;&nbsp;</td><td>"+Keyword+"&nbsp;&nbsp;&nbsp;</td></
* tr>", "")} </td></tr> </table> New props = [props]
*/

