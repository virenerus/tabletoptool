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
package com.t3.net;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageWriter;

import com.t3.FileUtil;

public class LocalLocation implements Location {

	private String localFile;

	public LocalLocation() {
		// For serialization
	}
	
	public LocalLocation(File file) {
		this.localFile = file.getAbsolutePath();
	}
	
	public File getFile() {
		return new File(localFile);
	}
	
	@Override
	public InputStream getContent() throws IOException {
		return new BufferedInputStream(new FileInputStream(getFile()));
	}
	
	@Override
	public void putContent(InputStream content) throws IOException {
		try(BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(getFile()))) {
			FileUtil.copyWithClose(content, out);
		}
	}

	@Override
	public void putContent(ImageWriter writer, BufferedImage image) {
		try(BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(getFile()))) {
			writer.setOutput(out);
			writer.write(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
