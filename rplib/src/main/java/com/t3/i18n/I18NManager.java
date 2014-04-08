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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class I18NManager {

	private static List<String> bundleNameList = new CopyOnWriteArrayList<String>();
	private static List<ResourceBundle> bundleList = new ArrayList<ResourceBundle>();
	private static Locale locale = Locale.US;
	private static List<LocaleChangeListener> localeListenerList = new CopyOnWriteArrayList<LocaleChangeListener>(); 

	public static void addBundle(String bundleName) {
		bundleNameList.add(bundleName);
		updateBundles();
	}
	
	public static void removeBundle(String bundleName) {
		bundleNameList.remove(bundleName);
		updateBundles();
	}
	
	public static void setLocale(Locale locale) {
		I18NManager.locale = locale;
		updateBundles();
		fireLocaleChanged();
	}

	public static String getText(String key) {
		
		for (ResourceBundle bundle : bundleList) {
			
			String value = bundle.getString(key);
			if (value != null) {
				return value;
			}
		}
		
		return key;
	}

	private synchronized static void fireLocaleChanged() {
		
		for (LocaleChangeListener listener : localeListenerList) {
			listener.localeChanged(locale);
		}
	}
	
	private synchronized static void updateBundles() {
		
		bundleList.clear();
		
		for (String bundleName : bundleNameList) {
			
			bundleList.add(ResourceBundle.getBundle(bundleName, locale));
		}
	}
}
