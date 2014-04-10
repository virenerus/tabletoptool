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
package com.t3.launcher;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class Launcher {

	public static void main(String[] args) {
		File mainFolder=new File(Launcher.class.getProtectionDomain()
									.getCodeSource().getLocation().getPath()
								).getParentFile();
		System.out.println("Main Folder: "+mainFolder.getAbsolutePath());
		File propertiesFile=new File(
				mainFolder,
				"config.xml");
		System.out.println("Config File: "+propertiesFile.getAbsolutePath());
		
		String[][] defaults= {
			{"Xmx", "1024M"},
			{"Xss", "512K"},
		};
		
		Properties properties=new Properties();
		//load properties
		try {
			properties.loadFromXML(new FileInputStream(propertiesFile));
		} catch(Exception e) {
			//if file not found should not be a problem
			warn("Could not find config.xml. Trying to create new one.",e);
		}
		
		//set and store defaults
		for(String[] def:defaults) {
			if(!properties.contains(def[0]))
				properties.put(def[0], def[1]);
		}
		try {
			properties.storeToXML(new FileOutputStream(propertiesFile), "T³ Properties");
		} catch (IOException e) {
			error("Could not store config.xml.",e);
		}
		
		//check jre version
		String[] versionParts=System.getProperty("java.version").split("\\.");
		if(Integer.parseInt(versionParts[0])<1 ||
				(Integer.parseInt(versionParts[0])==1 && 
				 Integer.parseInt(versionParts[1])<7)) {
			JOptionPane.showMessageDialog(null,generateErrorPanel(
							"T³ will need at least Java 1.7.<br>"
							+"You can download the JRE 7 here: <a href=\"http://java.com\">http://java.com</a>.<br>"
							+"Your version is "+System.getProperty("java.version")		
							),
					"Java 1.7 required", JOptionPane.ERROR_MESSAGE);
		}
		else {
			File javaExecutable=new File(System.getProperty("java.home"),"/bin/java");
			File t3jar=new File(mainFolder,"t3.jar");
			if(!t3jar.exists()) 
				error("Could not find tabletop tool jar at '"+t3jar.getAbsolutePath()+"'",null);
			
			//execute it
			ArrayList<String> execution=new ArrayList<String>();
			execution.add(javaExecutable.getAbsolutePath());
			for(Entry<Object, Object> p:properties.entrySet())
				execution.add("-"+p.getKey()+p.getValue());
			execution.add("-jar");
			execution.add(t3jar.getName());
			for(String a:args)
				execution.add(a);
			
			try {
				ProcessBuilder pb=new ProcessBuilder(execution).directory(mainFolder);
				System.out.println("Executing "+pb.toString());
				pb.start();
			} catch (IOException e) {
				error("Could not launch Tabletop Tool",e);
			}
		}
	}

	private static Object generateErrorPanel(String text) {
		JEditorPane ep = new JEditorPane("text/html", "<html><body>"
	            + text
	            + "</body></html>");

	    // handle link events
	    ep.addHyperlinkListener(new HyperlinkListener() {
	        @Override
	        public void hyperlinkUpdate(HyperlinkEvent e) {
	            if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))
					try {
						Desktop.getDesktop().browse(e.getURL().toURI());
					} catch (URISyntaxException | IOException e1) {}
	        }
	    });
	    ep.setEditable(false);
	    ep.setBackground(UIManager.getColor("Panel.background"));
	    return ep;
	}

	private static void error(String msg, Exception e) {
		warn("Error: "+msg,e);
		System.out.println("Aborted Execution. Press Enter to close: ");
		try (Scanner s=new Scanner(System.in)) {
			s.nextLine();
		}
		System.exit(-1);
	}

	private static void warn(String msg, Exception e) {
		System.out.println(msg);
		if(e!=null)
			e.printStackTrace();
	}

}
