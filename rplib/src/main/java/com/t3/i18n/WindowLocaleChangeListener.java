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
package com.t3.i18n;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.util.Locale;

public class WindowLocaleChangeListener implements LocaleChangeListener {

	private Window window;
	private String key;
	
	public WindowLocaleChangeListener(Window window, String key) {
		this.window = window;
		this.key = key;
	}
	
	////
	// LOCALE CHANGE LISTENER
	@Override
	public void localeChanged(Locale locale) {
		
		if (window instanceof Dialog) {
			((Dialog)window).setTitle(I18NManager.getText(key));
		}
		if (window instanceof Frame) {
			((Frame)window).setTitle(I18NManager.getText(key));
		}
	}
}
