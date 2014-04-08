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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Automatically keeps track of and restores frame size when opening/closing
 * the application.
 * 
 * To use, simply add a line like this to you frame's constructor:
 * 
 *      addWindowListener(new FramePreferences(appName, this));
 */
public class WindowPreferences extends WindowAdapter {

    private Preferences          prefs;

    private static final String  KEY_X                         = "x";
    private static int     DEFAULT_X;

    private static final String  KEY_Y                         = "y";
    private static int     DEFAULT_Y;

    private static final String  KEY_WIDTH                     = "width";
    private static int     DEFAULT_WIDTH;

    private static final String  KEY_HEIGHT                    = "height";
    private static int     DEFAULT_HEIGHT;


    public WindowPreferences(String appName, String controlName, Window frame) {
        prefs = Preferences.userRoot().node(appName + "/control/" + controlName);        
        
        DEFAULT_X = frame.getLocation().x;
        DEFAULT_Y = frame.getLocation().y;
        
        DEFAULT_WIDTH = frame.getSize().width;
        DEFAULT_HEIGHT = frame.getSize().height;
        
        restorePreferences(frame);
        
        frame.addWindowListener(this);
    }
    
    /**
     * Clear out window preferences from the user's system
     */
    public void clear() {
        try {
            prefs.clear();
        } catch (BackingStoreException bse) {
            // This error shouldn't matter, really,
            // since it is an asthetic action
            bse.printStackTrace();
        }
    }
    
    ////
    // Preferences

    protected int getX() {
        return prefs.getInt(KEY_X, DEFAULT_X);
    }

    protected void setX(int x) {
        prefs.putInt(KEY_X, x);
    }

    protected int getY() {
        return prefs.getInt(KEY_Y, DEFAULT_Y);
    }

    protected void setY(int y) {
        prefs.putInt(KEY_Y, y);
    }

    protected int getWidth() {
        return prefs.getInt(KEY_WIDTH, DEFAULT_WIDTH);
    }

    protected void setWidth(int width) {
        prefs.putInt(KEY_WIDTH, width);
    }

    protected int getHeight() {
        return prefs.getInt(KEY_HEIGHT, DEFAULT_HEIGHT);
    }

    protected void setHeight(int height) {
        prefs.putInt(KEY_HEIGHT, height);
    }
    
    protected void storePreferences(Window frame) {

        setX(frame.getLocation().x);
        setY(frame.getLocation().y);
        
        setWidth(frame.getSize().width);
        setHeight(frame.getSize().height);
    }
    
    protected void restorePreferences(Window frame) {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int x = Math.max(Math.min(getX(), screenSize.width - getWidth()), 0);
		int y = Math.max(Math.min(getY(), screenSize.height - getHeight()), 0);

		frame.setSize(getWidth(), getHeight());
		frame.setLocation(x, y);
	}
    
    ////
    // WINDOW ADAPTER
    @Override
	public final void windowClosing(WindowEvent e) {
        
        storePreferences((Window) e.getSource());
    }
}
