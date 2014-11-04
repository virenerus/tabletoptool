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
package com.t3.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.t3.MD5Key;
import com.t3.client.TabletopTool;
import com.t3.xstreamversioned.version.SerializationVersion;

/**
 * The binary representation of an image.
 */
@SerializationVersion(0)
public class Asset {
	private MD5Key id;
	private String name;
	private String extension;

	private transient byte[] image;

	protected Asset() {
	}

	public Asset(String name, byte[] image) {
		this.image = image;
		this.name = name;
		if (image != null) {
			this.id = new MD5Key(image);
			extension = null;
			getImageExtension();
		}
	}

	public Asset(MD5Key id) {
		this.id = id;
	}

	public Asset(MD5Key id, String extension) {
		this.id=id;
		this.extension=extension;
	}

	public MD5Key getId() {
		return id;
	}

	public void setId(MD5Key id) {
		this.id = id;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
		extension = null;
		getImageExtension();
	}

	public String getImageExtension() {
		if (extension == null) {
			extension = "";
			
			if (image != null && image.length >= 4) {
				try(ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(image))){
					Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
					if (readers.hasNext()) {
						ImageReader reader = readers.next();
						reader.setInput(iis);
						extension = reader.getFormatName().toLowerCase();
					}
				} catch (IOException e) {
					TabletopTool.showError("IOException?!", e); // Can this happen??
				}
			}
		}
		return extension;
	}

	public String getName() {
		return name;
	}

	public boolean isTransfering() {
		return AssetManager.isAssetRequested(id);
	}

	@Override
	public String toString() {
		return id + "/" + name + "(" + (image != null ? image.length : "-") + ")";
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Asset)) {
			return false;
		}
		Asset asset = (Asset) obj;
		return asset.getId().equals(getId());
	}
}
