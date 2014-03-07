/*
 *  This software copyright by various authors including the RPTools.net
 *  development team, and licensed under the LGPL Version 3 or, at your
 *  option, any later version.
 *
 *  Portions of this software were originally covered under the Apache
 *  Software License, Version 1.1 or Version 2.0.
 *
 *  See the file LICENSE elsewhere in this distribution for license details.
 */

package com.t3.client;

import java.io.IOException;
import java.net.Socket;

import com.t3.model.Player;
import com.t3.server.Handshake;

import com.t3.clientserver.hessian.client.ClientConnection;

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
