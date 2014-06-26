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

import java.awt.Color;
import java.awt.Image;
import java.awt.Paint;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

import com.t3.client.TabletopTool;
import com.t3.client.TransferableAsset;
import com.t3.client.TransferableToken;
import com.t3.image.ImageUtil;
import com.t3.model.Asset;
import com.t3.model.AssetManager;
import com.t3.model.Token;
import com.t3.persistence.FileUtil;
import com.t3.persistence.PersistenceUtil;
import com.t3.swing.ImagePanelModel;
import com.t3.util.ImageManager;

public class ImageFileImagePanelModel implements ImagePanelModel {

	private static final Color TOKEN_BG_COLOR = new Color(255, 250, 205);
	private static Image rptokenDecorationImage;

	static {
		try {
			rptokenDecorationImage = ImageUtil.getImage("com/t3/client/image/rptokIcon.png");
		} catch (IOException ioe) {
			rptokenDecorationImage = null;
		}
	}

	private final Directory dir;
	private String filter;
	private boolean global;
	private List<File> fileList;

	public ImageFileImagePanelModel(Directory dir) {
		this.dir = dir;
		refresh();
	}

	public void setFilter(String filter) {
		this.filter = filter.toUpperCase();
		refresh();
	}

	public void setGlobalSearch(boolean yes) {
		this.global = yes;
		// Should be calling refresh() but the only implementation calls this method
		// followed by setFilter() [above] so that method will call refresh().
	}

	@Override
	public int getImageCount() {
		return fileList.size();
	}

	@Override
	public Paint getBackground(int index) {
		return Token.isTokenFile(fileList.get(index).getName()) ? TOKEN_BG_COLOR : null;
	}

	@Override
	public Image[] getDecorations(int index) {
		return Token.isTokenFile(fileList.get(index).getName()) ? new Image[] { rptokenDecorationImage } : null;
	}

	@Override
	public Image getImage(int index) {

		Image image = null;
		if (dir instanceof AssetDirectory) {

			image = ((AssetDirectory) dir).getImageFor(fileList.get(index));
		}

		return image != null ? image : ImageManager.TRANSFERING_IMAGE;
	}

	@Override
	public Transferable getTransferable(int index) {
		Asset asset = null;

		File file = fileList.get(index);
		if (file.getName().toLowerCase().endsWith(Token.FILE_EXTENSION)) {

			try {
				Token token = PersistenceUtil.loadToken(file);

				return new TransferableToken(token);
			} catch (IOException ioe) {
				TabletopTool.showError("Could not load that token: ", ioe);
				return null;
			}
		}

		if (dir instanceof AssetDirectory) {
			asset = getAsset(index);

			if (asset == null) {
				return null;
			}

			// Now is a good time to tell the system about it
			AssetManager.putAsset(asset);
		}

		return asset != null ? new TransferableAsset(asset) : null;
	}

	@Override
	public String getCaption(int index) {
		if (index < 0 || index >= fileList.size()) {
			return null;
		}

		String name = fileList.get(index).getName();
		return FileUtil.getNameWithoutExtension(name);
	}

	@Override
	public Object getID(int index) {
		return new Integer(index);
	}

	@Override
	public Image getImage(Object ID) {
		return getImage(((Integer) ID).intValue());
	}

	public Asset getAsset(int index) {
		if (index < 0) {
			return null;
		}

		try {
			Asset asset = AssetManager.createAsset(fileList.get(index));

			// I don't like having to do this, but the ImageManager api only allows assets that
			// the assetmanager knows about (by design). So there isn't an "immediate" mode
			// for assets anymore.
			AssetManager.putAsset(asset);

			return asset;
		} catch (IOException ioe) {
			return null;
		}
	}

	/**
	 * Determines which images to display based on the setting of the Global vs. Local flag (<code>global</code> ==
	 * <b>true</b> means to search all files in the library) and the filter text.
	 */
	private void refresh() {
		fileList = new ArrayList<File>();
		if (global == true) {
			// FIXME populate fileList from all filenames in the library
			// Use the AssetManager class, something akin to searchForImageReferences()
			// but I don't want to do a search; I want to use the existing cached results.
			// Looks like all files with ".lnk" (see getAssetLinkFile() in the AssetManager class).
			assert global;
		} else {
			try {
				fileList.addAll(dir.getFiles());
			} catch (FileNotFoundException fnf) {
				TabletopTool.showError(fnf.getLocalizedMessage(), fnf);
			}
		}
		if (filter != null && filter.length() > 0) {
			for (ListIterator<File> iter = fileList.listIterator(); iter.hasNext();) {
				File file = iter.next();
				if (!file.getName().toUpperCase().contains(filter)) {
					iter.remove();
				}
			}
		}
		Collections.sort(fileList, filenameComparator);
	}

	private static Comparator<File> filenameComparator = new Comparator<File>() {
		@Override
		public int compare(File o1, File o2) {
			return o1.getName().compareToIgnoreCase(o2.getName());
		}
	};
}
