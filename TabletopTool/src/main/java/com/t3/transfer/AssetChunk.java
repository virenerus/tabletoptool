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
package com.t3.transfer;

import java.io.Serializable;

public class AssetChunk implements Serializable {
	private Serializable id;
	private byte[] data;
	
	public AssetChunk(Serializable id, byte[] data) {
		this.id = id;
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}

	public Serializable getId() {
		return id;
	}
}
