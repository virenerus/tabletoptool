/*
 *  This software copyright by various authors including the RPTools.net
 *  development team, and licensed under the LGPL Version 3 or, at your
 *  option, any later version.
 *
 *  Portions of this software were originally covered under the Apache
 *  Software License, Version 1.1 or Version 2.0.
 *
 *  See the file LICENSE elsewhere in this distribution for license details.
 */

package com.t3.client.ui.assetpanel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Directory {
	private final List<PropertyChangeListener> listenerList = new CopyOnWriteArrayList<PropertyChangeListener>();
	private final File directory;

	private Directory parent;
	private List<Directory> subdirs;
	private List<File> files;
	private final FilenameFilter fileFilter;

	public Directory(File directory) {
		this(directory, null);
	}

	public Directory(File directory, FilenameFilter fileFilter) {
		if (!directory.exists()) {
			throw new IllegalArgumentException(directory + " does not exist");
		}
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException(directory + " is not a directory");
		}
		this.directory = directory;
		this.fileFilter = fileFilter;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (!listenerList.contains(listener)) {
			listenerList.add(listener);
		}
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listenerList.remove(listener);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Directory)) {
			return false;
		}
		return directory.equals(((Directory) o).directory);
	}

	public File getPath() {
		return directory;
	}

	public void refresh() {
		subdirs = null;
		files = null;
	}

	public List<Directory> getSubDirs() throws FileNotFoundException {
		load();
		return subdirs;
	}

	public List<File> getFiles() throws FileNotFoundException {
		load();
		return files;
	}

	public Directory getParent() {
		return parent;
	}

	private void load() throws FileNotFoundException {
		if (files == null && subdirs == null) {
			ArrayList<File> files = new ArrayList<File>();
			ArrayList<Directory> subdirs = new ArrayList<Directory>();
			if (directory.exists() && directory.isDirectory()) {
				
				for(File f:directory.listFiles()) {
					if(f.isDirectory()) {
						Directory newDir = newDirectory(f, fileFilter);
						newDir.parent = this;
						subdirs.add(newDir);
					}
					else if(fileFilter.accept(directory, f.getName())) {
						files.add(f);
					}
				}
			}
			this.files = Collections.unmodifiableList(files);
			this.subdirs = Collections.unmodifiableList(subdirs);
		}
	}

	protected Directory newDirectory(File directory, FilenameFilter fileFilter) {
		return new Directory(directory, fileFilter);
	}

	protected void firePropertyChangeEvent(PropertyChangeEvent event) {
		// Me
		for (PropertyChangeListener listener : listenerList) {
			listener.propertyChange(event);
		}
		// Propagate up
		if (parent != null) {
			parent.firePropertyChangeEvent(event);
		}
	}

	public static final Comparator<Directory> COMPARATOR = new Comparator<Directory>() {
		@Override
		public int compare(Directory o1, Directory o2) {
			String filename1 = o1.getPath().getName();
			String filename2 = o2.getPath().getName();
			return filename1.compareToIgnoreCase(filename2);
		}
	};
}
