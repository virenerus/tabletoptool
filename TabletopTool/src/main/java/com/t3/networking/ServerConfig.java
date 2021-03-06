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
package com.t3.networking;

import java.net.ServerSocket;
import java.util.Random;

public class ServerConfig {
	public static final int DEFAULT_PORT = 51234;

	public static final int PORT_RANGE_START = 4000;
	public static final int PORT_RANGE_END = 20000;

	private int port;
	private String hostPlayerId;
	private String gmPassword;
	private String playerPassword;
	private boolean personalServer;
	private String serverName;

	public ServerConfig() {
		/* no op */
	}

	public ServerConfig(String hostPlayerId, String gmPassword, String playerPassword, int port, String serverName) {
		this.hostPlayerId = hostPlayerId;
		this.gmPassword = gmPassword;
		this.playerPassword = playerPassword;
		this.port = port;
		this.serverName = serverName;
	}

	public String getHostPlayerId() {
		return hostPlayerId;
	}

	public boolean isServerRegistered() {
		return serverName != null && serverName.length() > 0;
	}

	public String getServerName() {
		return serverName;
	}

	public boolean gmPasswordMatches(String password) {
		return safeCompare(gmPassword, password);
	}

	public boolean playerPasswordMatches(String password) {
		return safeCompare(playerPassword, password);
	}

	public boolean isPersonalServer() {
		return personalServer;
	}

	public int getPort() {
		return port;
	}

	public static ServerConfig createPersonalServerConfig() {
		ServerConfig config = new ServerConfig();
		config.personalServer = true;
		config.port = findOpenPort(PORT_RANGE_START, PORT_RANGE_END);
		return config;
	}

	private static Random r = new Random();

	private static int findOpenPort(int rangeLow, int rangeHigh) {
		// Presumably there will always be at least one open port between low and high
		while (true) {
			int port = rangeLow + (int) ((rangeHigh - rangeLow) * r.nextFloat());
			try (ServerSocket ss = new ServerSocket(port)){
				// This port was open before we took it, so we'll just close it and use this one
				// LATER: This isn't super exact, it's conceivable that another process will take 
				// the port between our closing it and the server opening it.  But that's the best
				// we can do at the moment until the server is refactored.
				return port;
			} catch (Exception e) {
				// Just keep trying
			}
		}
	}

	private static boolean safeCompare(String s1, String s2) {
		if (s1 == null) {
			s1 = "";
		}
		if (s2 == null) {
			s2 = "";
		}

		return s1.equals(s2);
	}
}
