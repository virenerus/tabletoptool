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
package com.t3.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class BackupManager {

	private static final long DEFAULT_MAX_BACKUP_SIZE = 128 * 1024 * 1024; // megs
	
	private File backupDir;
	private long maxBackupSize;
	
	public BackupManager(File backupDir) {
		this(backupDir, DEFAULT_MAX_BACKUP_SIZE);
	}
	
	public BackupManager(File backupDir, long maxBackupSize) {
		this.backupDir = backupDir;
		this.maxBackupSize = maxBackupSize;
		
		backupDir.mkdirs();
	}
	
	/**
	 * The maximum number of bytes that the backup directory should use for backups
	 */
	public void setMaxBackupSize(long size) {
		maxBackupSize = size;
	}
	
	public void backup(File file) throws IOException {

		// Active ?
		if (maxBackupSize < 1) {
			return;
		}
		
		// Enough room ?
		List<File> fileList = getFiles();
		long availableSpace = maxBackupSize - getUsedSpace();
		while (fileList.size() > 0 && file.length() > availableSpace) {
			File oldFile = fileList.remove(0);
			availableSpace += oldFile.length();
			oldFile.delete();
		}
		
		// Filename
		File newFile = new File(backupDir.getAbsolutePath() + "/" + file.getName());
		for (int count = 1; newFile.exists(); count++) {
			newFile = new File(backupDir.getAbsolutePath() + "/" +count + "_" + file.getName());
		}
		
		// Save
		FileUtil.copyFile(file, newFile);
	}

	/**
	 * List of existing backup files, with the oldest at the front 
	 */
	private List<File> getFiles() {
		
		List<File> fileList = new LinkedList<File>(Arrays.asList(backupDir.listFiles()));
		Collections.sort(fileList,  new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				return Long.compare(o1.lastModified(),o2.lastModified());
			}
		});		
		return fileList;
	}
	
	private long getUsedSpace() {
		long count = 0;
		for (File file : backupDir.listFiles()) {
			count += file.length();
		}
		return count;
	}
	
//	public static void main(String[] args) throws IOException {
//		
//		BackupManager mgr = new BackupManager(new File("/home/trevor/tmp/backup"));
//		mgr.setMaxBackupSize(35000);
//
//		mgr.backup(new File("/home/trevor/tmp/applet.html"));
//		
//		System.out.println("Done");
//	}
}
