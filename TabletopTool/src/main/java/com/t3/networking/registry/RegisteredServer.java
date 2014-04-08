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
