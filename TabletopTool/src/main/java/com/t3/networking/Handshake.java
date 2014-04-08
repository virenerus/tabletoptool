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

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.t3.client.TabletopTool;
import com.t3.language.I18N;
import com.t3.model.Player;

/**
 * @author trevor
 */
public class Handshake {

	public interface Code {
		public static final int UNKNOWN = 0;
		public static final int OK = 1;
		public static final int ERROR = 2;
	}

	/**
	 * Server side of the handshake
	 */
	public static Player receiveHandshake(T3Server server, Socket s) throws IOException {
		// TODO: remove server config as a param
		ServerConfig config = server.getConfig();

		HessianInput input = new HessianInput(s.getInputStream());
		HessianOutput output = new HessianOutput(s.getOutputStream());
		output.getSerializerFactory().setAllowNonSerializable(true);
		//output.findSerializerFactory().setAllowNonSerializable(true);

		Request request = (Request) input.readObject();

		Response response = new Response();
		response.code = Code.OK;

		boolean passwordMatches = request.role == Player.Role.GM ? config.gmPasswordMatches(request.password) : config.playerPasswordMatches(request.password);
		if (!passwordMatches) {

			// PASSWORD
			response.code = Code.ERROR;
			response.message = I18N.getString("Handshake.msg.wrongPassword");
		} else if (server.isPlayerConnected(request.name)) {

			// UNIQUE NAME
			response.code = Code.ERROR;
			response.message = I18N.getString("Handshake.msg.duplicateName");
		} else if (!TabletopTool.isDevelopment() && !TabletopTool.getVersion().equals(request.version) && !"DEVELOPMENT".equals(request.version)) {
			// Allows a version running without a 'version.txt' to act as client or server to any other version

			// CORRECT VERSION
			response.code = Code.ERROR;
			String clientUsed = request.version;
			String serverUsed = TabletopTool.getVersion();
			response.message = I18N.getText("Handshake.msg.wrongVersion", clientUsed, serverUsed);
		}
		response.policy = server.getPolicy();
		output.writeObject(response);
		return response.code == Code.OK ? new Player(request.name, request.role, request.password) : null;
	}

	/**
	 * Client side of the handshake
	 */
	public static Response sendHandshake(Request request, Socket s) throws IOException {
		HessianInput input = new HessianInput(s.getInputStream());
		HessianOutput output = new HessianOutput(s.getOutputStream());
		output.getSerializerFactory().setAllowNonSerializable(true);
		output.writeObject(request);

		return (Response) input.readObject();
	}

	public static class Request {
		public String name;
		public String password;
		public Player.Role role;
		public String version;

		public Request() {
			// for serialization
		}

		public Request(String name, String password, Player.Role role, String version) {
			this.name = name;
			this.password = password;
			this.role = role;
			this.version = version;
		}
	}

	public static class Response {
		public int code;
		public String message;
		public ServerPolicy policy;
	}
}
