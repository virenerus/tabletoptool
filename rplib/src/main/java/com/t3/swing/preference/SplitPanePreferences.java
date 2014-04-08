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
package com.t3.swing.preference;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.prefs.Preferences;

import javax.swing.JSplitPane;

public class SplitPanePreferences implements PropertyChangeListener {

    private JSplitPane splitPane;
    private Preferences prefs;
    
    private static final String PREF_LOCATION_KEY = "location";
    
    public SplitPanePreferences(String appName, String controlName, JSplitPane splitPane) {
        this.splitPane = splitPane;
        
        prefs = Preferences.userRoot().node(appName + "/control/" + controlName);        
        
        splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, this);
        
        restorePreferences();
    }
    
    private void restorePreferences() {
        
        int position = prefs.getInt(PREF_LOCATION_KEY, -1);
        if (position == -1) {
            // First time usage, don't change the position of the split pane
            return;
        }

        splitPane.setDividerLocation(position);
    }
    
    private void savePreferences() {
        
        prefs.putInt(PREF_LOCATION_KEY, splitPane.getDividerLocation());
    }
    
    ////
    // PROPERTY CHANGE LISTENER
    @Override
	public void propertyChange(PropertyChangeEvent evt) {
        savePreferences();
    }
}
