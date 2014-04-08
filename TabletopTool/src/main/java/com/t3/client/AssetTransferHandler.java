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
package com.t3.client;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.io.FileUtils;

import com.t3.model.Asset;
import com.t3.model.AssetManager;
import com.t3.transfer.ConsumerListener;

/**
 * Handles incoming segmented assets
 * 
 * @author trevor
 */
public class AssetTransferHandler implements ConsumerListener {
	@Override
	public void assetComplete(Serializable id, String name, File data) {
		byte[] assetData = null;
		try {
			assetData = FileUtils.readFileToByteArray(data);
		} catch (IOException ioe) {
			TabletopTool.showError("Error loading composed asset file: " + id);
			return;
		}
		Asset asset = new Asset(name, assetData);
		if (!asset.getId().equals(id)) {
			TabletopTool.showError("Received an invalid image: " + id);
			return;
		}
		// Install it into our system
		AssetManager.putAsset(asset);

		// Remove the temp file
		data.delete();
		TabletopTool.getFrame().refresh();
	}

	@Override
	public void assetUpdated(Serializable id) {
		// Nothing to do
	}

	@Override
	public void assetAdded(Serializable id) {
		// Nothing to do
	}
}
