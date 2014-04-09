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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

public class WebDownloader {
	private final URL url;

	public WebDownloader(URL url) {
		if (url == null) {
			throw new IllegalArgumentException("url can not be null");
		}
		this.url = url;
	}

	/**
	 * Read the data at the given URL. This method should not be called on the EDT.
	 * 
	 * @return File pointer to the location of the data, file will be deleted at program end
	 */
	public String read() throws IOException {
		URLConnection conn = url.openConnection();

		conn.setConnectTimeout(5000);
		conn.setReadTimeout(5000);

		// Send the request.
		conn.connect();

		
		try (
			InputStream in = conn.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		) {
			int buflen = 1024 * 30;
			int bytesRead = 0;
			byte[] buf = new byte[buflen];

			for (int nRead = in.read(buf); nRead != -1; nRead = in.read(buf)) {
				bytesRead += nRead;
				out.write(buf, 0, nRead);
			}
			return out != null ? new String(out.toByteArray()) : null;
		}
		
	}
}
