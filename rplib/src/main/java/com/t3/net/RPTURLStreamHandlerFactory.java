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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;
import java.util.Map;

import com.t3.FileUtil;

public class RPTURLStreamHandlerFactory implements URLStreamHandlerFactory {

	private static Map<String, byte[]> imageMap = new HashMap<String, byte[]>(); 
	
	private Map<String, URLStreamHandler> protocolMap = new HashMap<String, URLStreamHandler>();

	public RPTURLStreamHandlerFactory() {
		registerProtocol("cp", new ClasspathStreamHandler());
	}

	public void registerProtocol(String protocol, URLStreamHandler handler) {
		protocolMap.put(protocol, handler);
	}
	
	@Override
	public URLStreamHandler createURLStreamHandler(String protocol) {

		return protocolMap.get(protocol);
	}

	private static class ClasspathStreamHandler extends URLStreamHandler {
		
		@Override
		protected URLConnection openConnection(URL u) throws IOException {
			
			// TODO: This should really figure out the exact type
			return new ImageURLConnection(u);
		}
	}
	
	private static class ImageURLConnection extends URLConnection {
		
		private byte[] data;
		
		public ImageURLConnection(URL url) {
			super(url);
			
			String path = url.getHost() + url.getFile();
			data = imageMap.get(path);
			if (data == null) {
				try {
					data = FileUtil.loadResource(path);
					imageMap.put(path, data);
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		
		@Override
		public void connect() throws IOException {
			// Nothing to do
		}

		@Override
		public InputStream getInputStream() throws IOException {

			return new ByteArrayInputStream(data);
		}
	}
}
