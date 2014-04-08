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

import java.util.Locale;

import javax.swing.AbstractButton;

public class ButtonLocaleChangeListener implements LocaleChangeListener {

	private AbstractButton button;
	private String key;
	
	public ButtonLocaleChangeListener(AbstractButton button, String key) {
		this.button = button;
		this.key = key;
	}
	
	////
	// LOCALE CHANGE LISTENER
	@Override
	public void localeChanged(Locale locale) {
		button.setText(I18NManager.getText(key));
	}
}
