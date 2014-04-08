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
package com.t3.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	private int port;
	private boolean stop;
	private ServerSocket server;
	
	public EchoServer(int port) {
		
		this.port = port;
	}

	public synchronized void start() throws IOException {
		if (server != null) {
			return;
		}
		
		server = new ServerSocket(port);
		new ReceiveThread ().start();
	}
	
	public synchronized void stop() {
		if (server == null) {
			return;
		}

		try {
			stop = true;
			server.close();
			server = null;
		} catch (IOException ioe) {
			// Since we're trying to kill it anyway
			ioe.printStackTrace();
		}
	}
	
	private class ReceiveThread extends Thread {

		@Override
		public void run() {
			try {
				
				while (!stop) {
					
					Socket clientSocket = server.accept();
					BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					PrintStream os = new PrintStream(clientSocket.getOutputStream());
					
					String line = null;
					while ((line = is.readLine()) != null) {
						os.println(line);
						os.flush();
					}
				}
			} catch (IOException e) {
				// Expected when the accept is killed
			} finally {
				server = null;
			}
		}
	}
}
