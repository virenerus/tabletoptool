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
package com.t3.client.ui.macrobuttons.buttons;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import com.t3.client.AppConstants;
import com.t3.client.TabletopTool;
import com.t3.client.ui.MacroButtonHotKeyManager;
import com.t3.model.MacroButtonProperties;

/**
 * Class that is responsible for storing global macro buttons' data in the registry (on Windows that is, dunno where it
 * stores on other platforms, duh.) so it persists across sessions.
 */
public class MacroButtonPrefs {
	private static int maxIndex = 0;

	private static final String PREF_COLOR_KEY = "color";
	private static final String PREF_LABEL_KEY = "label";
	private static final String PREF_GROUP_KEY = "group";
	private static final String PREF_SORTBY_KEY = "sortby";
	private static final String PREF_COMMAND_KEY = "command";
	private static final String PREF_HOTKEY_KEY = "hotKey";
	private static final String PREF_FONT_COLOR_KEY = "fontColorKey";
	private static final String PREF_FONT_SIZE = "fontSize";
	private static final String PREF_MIN_WIDTH = "minWidth";
	private static final String PREF_MAX_WIDTH = "maxWidth";
	private static final String PREF_TOOLTIP = "toolTip";

	private static final String FORMAT_STRING = "%010d";

	public MacroButtonPrefs() {
	}

	public static void savePreferences(MacroButtonProperties properties) {
		int index = properties.getIndex();

		// use zero padding to ensure proper ordering in the registry (otherwise 10 will come before 2 etc.)
		String paddedIndex = String.format(FORMAT_STRING, index);

//		prefs = Preferences.userRoot().node(AppConstants.APP_NAME + "/macros/" + paddedIndex);
		Preferences prefs = Preferences.userRoot().node(AppConstants.APP_NAME + "/macros");

		try {
			if (!prefs.nodeExists(paddedIndex)) {
				prefs = prefs.node(paddedIndex);
			} else {
				prefs = prefs.node(paddedIndex);
			}
			// Start with the macro text itself.  Apparently Windows has a length limit for registry values and the JRE doesn't
			// re-read the registry to determine if all information was actually written...  So we'll do it ourselves.  We start with
			// the macro text because if it fails, we don't want to bother saving the rest of the fields.
			String text = properties.getCommand();
			prefs.put(PREF_COMMAND_KEY, text);
			String readback = prefs.get(PREF_COMMAND_KEY, null);
			if (readback == null || !readback.equals(text)) {
				TabletopTool.showError("Macro body not written properly?!  Operating system may have truncated to " + Preferences.MAX_VALUE_LENGTH + " characters.");
				return;
			}
			prefs.put(PREF_COLOR_KEY, properties.getColorKey());
			prefs.put(PREF_LABEL_KEY, properties.getLabel());
			prefs.put(PREF_GROUP_KEY, properties.getGroup());
			prefs.put(PREF_SORTBY_KEY, properties.getSortby());
			prefs.put(PREF_HOTKEY_KEY, properties.getHotKey());
			prefs.put(PREF_FONT_COLOR_KEY, properties.getFontColorKey());
			prefs.put(PREF_FONT_SIZE, properties.getFontSize());
			prefs.put(PREF_MIN_WIDTH, properties.getMinWidth());
			prefs.put(PREF_MAX_WIDTH, properties.getMaxWidth());
			prefs.put(PREF_TOOLTIP, properties.getToolTip());
			prefs.flush();
			TabletopTool.getFrame().getGlobalPanel().reset();
		} catch (BackingStoreException e) {
			TabletopTool.showError("Problem saving Global macros?!", e);
		}
	}

	public static List<MacroButtonProperties> getButtonProperties() {
		List<MacroButtonProperties> buttonProperties = new ArrayList<MacroButtonProperties>();
		Preferences prefsRoot = Preferences.userRoot().node(AppConstants.APP_NAME + "/macros");

		try {
			for (String buttonNode : prefsRoot.childrenNames()) {
				Preferences buttonPref = prefsRoot.node(buttonNode);

				// not elegant by any definition but works using already existing system
				// the downside is the number of buttons the user can create is limited with Integer.MAX_VALUE
				// after 2.147.. billion button creation the program will explode. if you were to create a new button
				// every second it would take 63 years for this to overflow :)
				// TODO: change this if you like
				int index = Integer.parseInt(buttonNode);
				if (index > maxIndex) {
					maxIndex = index;
				}
				if (buttonNode.length() <= 3) {
					// we have an old style button pref. copy it to the new format
					// but check whether we have a new style pref first
					if (!prefsRoot.nodeExists(String.format(FORMAT_STRING, Integer.parseInt(buttonNode)))) {
						// ok, there is no new style pref, it is safe to copy
						// but don't import empty ones (there is no need)
						if (!buttonPref.get(PREF_COMMAND_KEY, "").equals("")) {
							Preferences newPrefs = prefsRoot.node(String.format(FORMAT_STRING, Integer.parseInt(buttonNode)));

							newPrefs.put(PREF_COLOR_KEY, buttonPref.get(PREF_COLOR_KEY, ""));
							newPrefs.put(PREF_LABEL_KEY, buttonPref.get(PREF_LABEL_KEY, Integer.toString(index)));
							newPrefs.put(PREF_GROUP_KEY, buttonPref.get(PREF_GROUP_KEY, ""));
							newPrefs.put(PREF_SORTBY_KEY, buttonPref.get(PREF_SORTBY_KEY, ""));
							newPrefs.put(PREF_COMMAND_KEY, buttonPref.get(PREF_COMMAND_KEY, ""));
							newPrefs.put(PREF_HOTKEY_KEY, buttonPref.get(PREF_HOTKEY_KEY, MacroButtonHotKeyManager.HOTKEYS[0]));
							newPrefs.put(PREF_FONT_COLOR_KEY, buttonPref.get(PREF_FONT_COLOR_KEY, ""));
							newPrefs.put(PREF_FONT_SIZE, buttonPref.get(PREF_FONT_SIZE, ""));
							newPrefs.put(PREF_MIN_WIDTH, buttonPref.get(PREF_MIN_WIDTH, ""));
							newPrefs.put(PREF_MAX_WIDTH, buttonPref.get(PREF_MAX_WIDTH, ""));

							String colorKey = buttonPref.get(PREF_COLOR_KEY, "");
							String label = buttonPref.get(PREF_LABEL_KEY, Integer.toString(index));
							String group = buttonPref.get(PREF_GROUP_KEY, "");
							String sortby = buttonPref.get(PREF_SORTBY_KEY, "");
							String command = buttonPref.get(PREF_COMMAND_KEY, "");
							String hotKey = buttonPref.get(PREF_HOTKEY_KEY, MacroButtonHotKeyManager.HOTKEYS[0]);
							String fontColorKey = buttonPref.get(PREF_FONT_COLOR_KEY, "");
							String fontSize = buttonPref.get(PREF_FONT_SIZE, "");
							String minWidth = buttonPref.get(PREF_MIN_WIDTH, "");
							String maxWidth = buttonPref.get(PREF_MAX_WIDTH, "");
							String toolTip = buttonPref.get(PREF_TOOLTIP, "");

							buttonProperties.add(new MacroButtonProperties(index, colorKey, hotKey, command, label, group, sortby, fontColorKey, fontSize,
									minWidth, maxWidth, toolTip));
						}
					}
					// set old pref to be removed (regardless it was copied or not)
					buttonPref.removeNode();
					continue;
				}
				String colorKey = buttonPref.get(PREF_COLOR_KEY, "");
				String label = buttonPref.get(PREF_LABEL_KEY, Integer.toString(index));
				String group = buttonPref.get(PREF_GROUP_KEY, "");
				String sortby = buttonPref.get(PREF_SORTBY_KEY, "");
				String command = buttonPref.get(PREF_COMMAND_KEY, "");
				String fontColorKey = buttonPref.get(PREF_FONT_COLOR_KEY, "");
				String fontSize = buttonPref.get(PREF_FONT_SIZE, "");
				String minWidth = buttonPref.get(PREF_MIN_WIDTH, "");
				String maxWidth = buttonPref.get(PREF_MAX_WIDTH, "");
				String hotKey = buttonPref.get(PREF_HOTKEY_KEY, MacroButtonHotKeyManager.HOTKEYS[0]);
				String toolTip = buttonPref.get(PREF_TOOLTIP, "");

				buttonProperties.add(new MacroButtonProperties(index, colorKey, hotKey, command, label, group, sortby, fontColorKey, fontSize, minWidth,
						maxWidth, toolTip));
			}
		} catch (BackingStoreException e) {
			// exception due to prefsRoot.childrenNames()
			TabletopTool.showError("Problem iterating over all Global macros?!", e);
		}
		return buttonProperties;
	}

	public static int getNextIndex() {
		return ++maxIndex;
	}

	public static void delete(MacroButtonProperties properties) {
		int index = properties.getIndex();
		String paddedIndex = String.format(FORMAT_STRING, index);
		Preferences prefs = Preferences.userRoot().node(AppConstants.APP_NAME + "/macros/" + paddedIndex);
		try {
			prefs.removeNode();
		} catch (BackingStoreException e) {
			TabletopTool.showError("Problem when removing a Global macro?!", e);
		}
		TabletopTool.getFrame().getGlobalPanel().reset();
	}

	public static void deletePanel() {
		Preferences prefs = Preferences.userRoot().node(AppConstants.APP_NAME + "/macros");
		try {
			prefs.removeNode();
		} catch (BackingStoreException e) {
			TabletopTool.showError("Problem when removing a Global macro?!", e);
		}
	}
}
