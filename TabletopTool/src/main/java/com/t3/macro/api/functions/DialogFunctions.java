package com.t3.macro.api.functions;

import java.util.Map;

import com.t3.client.ui.htmlframe.HTMLFrame;
import com.t3.client.ui.htmlframe.HTMLFrameFactory;
import com.t3.macro.MacroException;
import com.t3.macro.api.functions.input.InputFunctions;
import com.t3.macro.api.views.TokenView;

public class DialogFunctions {
	
	/**
	 * This method is used to determine if a dialog is visible.
	 * @param name the name of the dialog
	 * @return if the dialog is visible
	 */
	public boolean isDialogVisible(String name) {
		return HTMLFrameFactory.isVisible(false, name);
	}
	
	/**
	 * This method is used to determine if a frame is visible.
	 * @param name the name of the frame
	 * @return if the frame is visible
	 */
	public boolean isFrameVisible(String name) {
		return HTMLFrameFactory.isVisible(true, name);
	}
	
	/**
	 * This method is used to close a dialog.
	 * @param name the name of the dialog
	 */
	public void closeDialog(String name) {
		HTMLFrameFactory.close(false, name);
	}
	
	/**
	 * This method is used to close a frame.
	 * @param name the name of the frame
	 */
	public void closeFrame(String name) {
		HTMLFrameFactory.close(true, name);
	}
	
	/**
	 * This method is used to reset a frame.
	 * @param name the name of the frame
	 */
	public void resetFrame(String name) {
		HTMLFrame.center(name);
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
	public Map<String, String> input(String... parameters) throws MacroException {
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
	public Map<String, String> input(TokenView token, String... parameters) throws MacroException {
		return InputFunctions.input(token, parameters);
	}
}
