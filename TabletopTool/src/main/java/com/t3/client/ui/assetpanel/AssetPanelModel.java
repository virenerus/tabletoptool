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
package com.t3.client.ui.assetpanel;

import java.awt.image.ImageObserver;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AssetPanelModel implements PropertyChangeListener {

	private final ImageFileTreeModel imageFileTreeModel;

	private final List<ImageObserver> observerList = new CopyOnWriteArrayList<ImageObserver>();

	public AssetPanelModel() {
		imageFileTreeModel = new ImageFileTreeModel();
	}

	public ImageFileTreeModel getImageFileTreeModel() {
		return imageFileTreeModel;
	}

	public void removeRootGroup(Directory dir) {
		imageFileTreeModel.removeRootGroup(dir);
		dir.removePropertyChangeListener(this);
	}

	public void addRootGroup(Directory dir) {
		if (imageFileTreeModel.containsRootGroup(dir)) {
			return;
		}
		dir.addPropertyChangeListener(this);
		imageFileTreeModel.addRootGroup(dir);
	}

	public void addImageUpdateObserver(ImageObserver observer) {
		if (!observerList.contains(observer)) {
			observerList.add(observer);
		}
	}

	public void removeImageUpdateObserver(ImageObserver observer) {
		observerList.remove(observer);
	}

	// PROPERTY CHANGE LISTENER
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		for (ImageObserver observer : observerList) {
			observer.imageUpdate(null, ImageObserver.ALLBITS, 0, 0, 0, 0);
		}
	}
}
