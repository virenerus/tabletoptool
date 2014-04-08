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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageWriter;

import com.t3.FileUtil;

public class FTPLocation implements Location {

	private String username;
	private transient String password;
	private String hostname;
	private String path;
    private boolean binary;

	public FTPLocation(String username, String password, String hostname, String path) {
		this.username = username;
		this.password = password;
		this.hostname = hostname;
		this.path = path;
        this.binary = true;
	}
	
    public FTPLocation(String username, String password, String hostname, String path, boolean binary) {
        this.username = username;
        this.password = password;
        this.hostname = hostname;
        this.path = path;
        this.binary = binary;
    }
    
    public boolean isBinary() {
        return binary;
    }

    public String getHostname() {
		return hostname;
	}

	public String getPassword() {
		return password;
	}

	public String getPath() {
		return path;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public void putContent(InputStream content) throws IOException {
        try (OutputStream os = new URL(composeFileLocation()).openConnection().getOutputStream()){
            FileUtil.copyWithClose(content, os);
        }
	}

	@Override
	public InputStream getContent() throws IOException {

		return new URL(composeFileLocation()).openConnection().getInputStream();
	}

	private String composeFileLocation() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("ftp://");
		if(username != null && username.length() > 0) {
			builder.append(username);
		} else {
			builder.append("anonymous");
		}
		if(password != null && password.length() > 0) {
			builder.append(":").append(password);
		}
		
		builder.append("@");
		builder.append(hostname);
		builder.append("/");
		builder.append(path);
        if (binary) {
            builder.append(";type=i");
        }
		
		return builder.toString();
	}

	@Override
	public void putContent(ImageWriter writer, BufferedImage image) {
		try (OutputStream os = new URL(composeFileLocation()).openConnection().getOutputStream()){
            writer.setOutput(os);
            writer.write(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
