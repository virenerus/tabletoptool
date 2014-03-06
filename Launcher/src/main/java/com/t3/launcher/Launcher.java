package com.t3.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Scanner;

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
