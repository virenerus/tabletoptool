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
