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

/** Class found on web to work around a STUPID SWING BUG with JComboBox */
public class NoEqualString {
	private final String text;

	public NoEqualString(String txt) {
		text = txt;
	}

	@Override
	public String toString() {
		return text;
	}
}
