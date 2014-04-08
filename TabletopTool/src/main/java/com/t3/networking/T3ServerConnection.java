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

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.t3.clientserver.connection.ClientConnection;
import com.t3.clientserver.connection.ServerConnection;
import com.t3.clientserver.connection.ServerObserver;
import com.t3.model.Player;

/**
 * @author trevor
 */
public class T3ServerConnection extends ServerConnection implements ServerObserver {
	private static final Logger log = Logger.getLogger(T3ServerConnection.class);
	private final Map<String, Player> playerMap = new ConcurrentHashMap<String, Player>();
	private final T3Server server;

	public T3ServerConnection(T3Server server, int port) throws IOException {
		super(port);
		this.server = server;
		addObserver(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.t3.clientserver.simple.server.ServerConnection#handleConnectionHandshake(java.net.Socket)
	 */
	@Override
	public boolean handleConnectionHandshake(String id, Socket socket) {
		try {
			Player player = Handshake.receiveHandshake(server, socket);

			if (player != null) {
				playerMap.put(id.toUpperCase(), player);
				return true;
			}
		} catch (IOException ioe) {
			log.error("Handshake failure: " + ioe, ioe);
		}
		return false;
	}

	public Player getPlayer(String id) {
		for (Player player : playerMap.values()) {
			if (player.getName().equalsIgnoreCase(id)) {
				return player;
			}
		}
		return null;
	}

	public String getConnectionId(String playerId) {
		for (Map.Entry<String, Player> entry : playerMap.entrySet()) {
			if (entry.getValue().getName().equalsIgnoreCase(playerId)) {
				return entry.getKey();
			}
		}
		return null;
	}

	////
	// SERVER OBSERVER

	/**
	 * Handle late connections
	 */
	@Override
	public void connectionAdded(ClientConnection conn) {
		server.configureClientConnection(conn);

		Player player = playerMap.get(conn.getId().toUpperCase());
		for (String id : playerMap.keySet()) {
			server.getConnection().callMethod(conn.getId(), NetworkCommand.playerConnected, playerMap.get(id));
		}
		server.getConnection().broadcastCallMethod(NetworkCommand.playerConnected, player);
//     if (!server.isHostId(player.getName())) {
		// Don't bother sending the campaign file if we're hosting it ourselves
		server.getConnection().callMethod(conn.getId(), NetworkCommand.setCampaign, server.getCampaign());
//     }
	}

	@Override
	public void connectionRemoved(ClientConnection conn) {
		server.releaseClientConnection(conn.getId());
		server.getConnection().broadcastCallMethod(new String[] { conn.getId() }, NetworkCommand.playerDisconnected, playerMap.get(conn.getId().toUpperCase()));
		playerMap.remove(conn.getId().toUpperCase());
	}
}
