/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
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
