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

import java.awt.Component;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.ProgressMonitor;

import com.t3.GUID;

public class RemoteFileDownloader {
	private final URL url;
	private final Component parentComponent;

	public RemoteFileDownloader(URL url) {
		this(url, null);
	}

	public RemoteFileDownloader(URL url, Component parentComponent) {
		if (url == null) {
			throw new IllegalArgumentException("URL cannot be null");
		}
		this.url = url;
		this.parentComponent = parentComponent;
	}

	/**
	 * Read the data at the given URL. This method should not be called on the
	 * EDT.
	 * 
	 * @return File pointer to the location of the data, file will be deleted at
	 *         program end
	 */
	public File read() throws IOException {
		URLConnection conn = url.openConnection();

		conn.setConnectTimeout(5000);
		conn.setReadTimeout(5000);

		// Send the request.
		conn.connect();

		int length = conn.getContentLength();

		String tempDir = System.getProperty("java.io.tmpdir");
		if (tempDir == null) {
			tempDir = ".";
		}
		File tempFile = new File(tempDir + "/" + new GUID() + ".dat");
		tempFile.deleteOnExit();

		ProgressMonitor monitor = new ProgressMonitor(parentComponent, "Downloading " + url, null, 0, length);
		try(InputStream in = conn.getInputStream();
				OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFile))) {
			
			int buflen = 1024 * 30;
			int bytesRead = 0;
			byte[] buf = new byte[buflen];

			long start = System.currentTimeMillis();
			for (int nRead = in.read(buf); nRead != -1; nRead = in.read(buf)) {
				if (monitor.isCanceled()) {
					return null;
				}
				bytesRead += nRead;
				out.write(buf, 0, nRead);
				monitor.setProgress(bytesRead);
//                monitor.setNote("Elapsed: " + ((System.currentTimeMillis() - start) / 1000) + " seconds");
			}
		} finally {
			monitor.close();
		}
		return tempFile;
	}

	public static void main(String[] args) throws Exception {
		RemoteFileDownloader downloader = new RemoteFileDownloader(new URL("http://library.rptools.net/torstan.zip"));

		File tempFile = downloader.read();
		System.out.println(tempFile + " - " + tempFile.length());
	}
}
