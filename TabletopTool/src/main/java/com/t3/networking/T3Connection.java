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

import com.t3.client.TabletopTool;
import com.t3.clientserver.connection.ClientConnection;
import com.t3.model.Player;

/**
 * @author trevor
 */
public class T3Connection extends ClientConnection {
	private final Player player;

	public T3Connection(String host, int port, Player player) throws IOException {
		super(host, port, null);
		this.player = player;
	}

	public T3Connection(Socket socket, Player player) throws IOException {
		super(socket, null);
		this.player = player;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.t3.clientserver.simple.client.ClientConnection#sendHandshake(java.net.Socket)
	 */
	@Override
	public boolean sendHandshake(Socket s) throws IOException {
		Handshake.Response response = Handshake.sendHandshake(new Handshake.Request(player.getName(), player.getPassword(), player.getRole(), TabletopTool.getVersion()), s);

		if (response.code != Handshake.Code.OK) {
			TabletopTool.showError("ERROR: " + response.message);
			return false;
		}
		boolean result = response.code == Handshake.Code.OK;
		if (result) {
			TabletopTool.setServerPolicy(response.policy);
		}
		return result;
	}
}
