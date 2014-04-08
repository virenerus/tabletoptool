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
package com.t3.macro.api.functions.input;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InputType {
	// The regexp for the option strings is strict: no spaces, and trailing semicolon required.
	// @formatter: off
	TEXT(false, false, "WIDTH=16;SPAN=FALSE;"), 
	LIST(true, false, "VALUE=NUMBER;TEXT=TRUE;ICON=FALSE;ICONSIZE=50;SELECT=0;SPAN=FALSE;"), 
	CHECK(false, false, "SPAN=FALSE;"), 
	RADIO(true, false,"ORIENT=V;VALUE=NUMBER;SELECT=0;SPAN=FALSE;"), 
	LABEL(false, false, "TEXT=TRUE;ICON=FALSE;ICONSIZE=50;SPAN=FALSE;"), 
	PROPS(false, true, "SETVARS=NONE;SPAN=FALSE;"), 
	TAB(false, true,"SELECT=FALSE;");
	// @formatter: on

	public final OptionMap defaultOptions; // maps option name to default value
	public final boolean isValueComposite; // can "value" section be a list of values?
	public final boolean isControlComposite; // does this control contain sub-controls?

	InputType(boolean isValueComposite, boolean isControlComposite, String nameval) {
		this.isValueComposite = isValueComposite;
		this.isControlComposite = isControlComposite;

		defaultOptions = new OptionMap();
		Pattern pattern = Pattern.compile("(\\w+)=([\\w-]+)\\;"); // no spaces allowed, semicolon required
		Matcher matcher = pattern.matcher(nameval);
		while (matcher.find()) {
			defaultOptions.put(matcher.group(1).toUpperCase(), matcher.group(2).toUpperCase());
		}
	}

	/**
	 * Obtain one of the enum values, or null if <code>strName</code> 
	 * doesn't match any of them.
	 */
	public static InputType inputTypeFromName(String strName) {
		for (InputType it : InputType.values()) {
			if (strName.equalsIgnoreCase(it.name()))
				return it;
		}
		return null;
	}

	/** Gets the default value for an option. */
	public String getDefault(String option) {
		return defaultOptions.get(option.toUpperCase());
	}

	/**
	 * Parses a string and returns a Map of options for the given type.
	 * Options not found are set to the default value for the type.
	 */
	public OptionMap parseOptionString(String s) throws OptionException {
		OptionMap ret = new OptionMap();
		ret.putAll(defaultOptions); // copy the default values first
		Pattern pattern = Pattern.compile("\\s*(\\w+)\\s*\\=\\s*([\\w-]+)\\s*");
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			String key = matcher.group(1);
			String value = matcher.group(2);
			if (ret.get(key) == null)
				throw new OptionException(this, key, value);
			if (ret.getNumeric(key, -9998) != -9998) { // minor hack to detect if the option is numeric
				boolean valueIsNumeric;
				try {
					Integer.decode(value);
					valueIsNumeric = true;
				} catch (Exception e) {
					valueIsNumeric = false;
				}
				if (!valueIsNumeric)
					throw new OptionException(this, key, value);
			}
			ret.put(key, value);
		}
		return ret;
	}
	
	/*********************************************************
	 * Stores option settings as case-insensitive strings.
	 *********************************************************/
	@SuppressWarnings("serial")
	public final class OptionMap extends HashMap<String, String> {
		/** Case-insensitive put. */
		@Override
		public String put(String key, String value) {
			return super.put(key.toUpperCase(), value.toUpperCase());
		}

		/** Case-insensitive string get. */
		@Override
		public String get(Object key) {
			return super.get(key.toString().toUpperCase());
		}

		/**
		 * Case-insensitive numeric get. <br>
		 * Returns <code>defaultValue</code> if the option's value is
		 * non-numeric. <br>
		 * Use when caller wants to override erroneous option settings.
		 */
		public int getNumeric(String key, int defaultValue) {
			int ret;
			try {
				ret = Integer.decode(get(key));
			} catch (Exception e) {
				ret = defaultValue;
			}
			return ret;
		}

		/**
		 * Case-insensitive numeric get. <br>
		 * Returns the default value for the input type if option's value is
		 * non-numeric. <br>
		 * Use when caller wants to ignore erroneous option settings.
		 */
		public int getNumeric(String key) {
			String defstr = getDefault(key);
			int def;
			try {
				def = Integer.decode(defstr);
			} catch (Exception e) {
				def = -1;
				// Should never happen, since the defaults are set in the source code.
			}
			return getNumeric(key, def);
		}

		/** Tests for a given option value. */
		public boolean optionEquals(String key, String value) {
			if (get(key) == null)
				return false;
			return get(key).equalsIgnoreCase(value);
		}
	} ////////////////////////// end of OptionMap class

	/** Thrown when an option value is invalid. */
	@SuppressWarnings("serial")
	public class OptionException extends Exception {
		public String key, value, type;

		public OptionException(InputType it, String key, String value) {
			super();
			this.key = key;
			this.value = value;
			this.type = it.name();
		}
	}
}
