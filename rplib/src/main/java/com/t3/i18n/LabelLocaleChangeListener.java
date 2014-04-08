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

import java.awt.Label;
import java.util.Locale;

public class LabelLocaleChangeListener implements LocaleChangeListener {

	private Label label;
	private String key;
	
	public LabelLocaleChangeListener(Label label, String key) {
		this.label = label;
		this.key = key;
	}
	
	////
	// LOCALE CHANGE LISTENER
	@Override
	public void localeChanged(Locale locale) {
		label.setText(I18NManager.getText(key));
	}
}
