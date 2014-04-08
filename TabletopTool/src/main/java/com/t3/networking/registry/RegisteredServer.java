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
package com.t3.networking.registry;

public class RegisteredServer {
	private final String	address;
	private final int		port;
	private final String	t3Version;
	private final int		numberOfPlayers;
	private final String	name;
	
	public RegisteredServer(String name, String address, int port, String t3Version, int numberOfPlayers) {
		this.name = name;
		this.address = address;
		this.port = port;
		this.t3Version = t3Version;
		this.numberOfPlayers = numberOfPlayers;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public String getT3Version() {
		return t3Version;
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public String getName() {
		return name;
	}
	
}
