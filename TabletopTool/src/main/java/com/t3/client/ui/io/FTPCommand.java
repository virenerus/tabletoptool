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
package com.t3.client.ui.io;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;

// import sun.net.TransferProtocolClient;
// import sun.net.ftp.FtpClient;

/**
 * <p>
 * This class extends the Apache Commons {@link Net.FtpClient} class to ease future modification.
 * </p>
 * <p>
 * This class creates its own connection to the specified host and does not try to reuse an existing connection. This
 * has significant downsides, not the least of which is the need to provide login information (username and password),
 * but also the server seeing multiple incoming connections might think that some kind of DOS attack is being attempted
 * and lock the account!
 * </p>
 * <p>
 * Currently the only added command is <code>MKDIR</code>. This command may be implemented differently on different
 * servers (<code>MKDIR</code>, <code>MKD</code>, <code>XMKD</code>, etc) so the first time the application tries to
 * create a directory for a given host we loop through the possibilities that we know of until one works. That command
 * string is then saved for later use.
 * </p>
 * 
 * @author crash
 * 
 */

public class FTPCommand extends FTPClient {
	private final String host;

	public FTPCommand(String h) throws IOException {
		host = h;
		FTPClientConfig config = new FTPClientConfig();
		// Nothing to configure just yet but maybe in the future...
		this.configure(config);
		this.connect(host);
	}

	public int mkdir(String dir) throws IOException {
		int result = 0;
		try {
			mkd(dir);
			result = getReplyCode();
		} catch (IOException e) {
			result = getReplyCode();
			if (result != 550) {
				// "Directory already exists" is not necessarily an error.  For now just print a
				// stack trace and we'll decide later if this is a problem...
				e.printStackTrace();
			}
		}
		return result;
	}

	public int remove(String filename) throws IOException {
		int result = 0;
		try {
			dele(filename);
			result = getReplyCode();
		} catch (IOException e) {
			result = getReplyCode();
			if (result != 550) {
				// "File doesn't exist" is not an error, but we should report it for safety's sake.
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean closeServer() throws IOException {
		boolean result = this.logout();
		this.disconnect();
		return result;
	}
}
