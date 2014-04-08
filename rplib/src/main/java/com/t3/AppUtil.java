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
package com.t3;

import java.io.File;

public class AppUtil {

    private static final String USER_HOME;

    private static String appName;
    
    static {
        
        USER_HOME = System.getProperty("user.home");
        
    }

    public static void init(String appName) {
    	AppUtil.appName = appName;
    }
    
    public static File getUserHome() {
    	checkInit();
        return USER_HOME != null ? new File(USER_HOME) : null;
    }
    
    public static File getAppHome() {
    	checkInit();
        if (USER_HOME == null) {return null;}
        
        File home = new File(USER_HOME + "/." + appName);
        home.mkdirs();
        
        return home;
    }
    
    public static File getAppHome(String subdir) {
    	checkInit();
        if (USER_HOME == null) {return null;}
        
        File home = new File(getAppHome().getPath() + "/" + subdir);
        home.mkdirs();
        
        return home;
    }

    private static void checkInit() {
    	if (appName == null) {
    		throw new IllegalStateException("Must call init() on AppUtil");
    	}
    }
    
    public static String getAppName() {
        return appName;
    }
}
