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

import com.t3.client.TabletopTool;
import com.t3.clientserver.connection.AbstractConnection;
import com.t3.clientserver.handler.DisconnectHandler;
import com.t3.model.campaign.CampaignFactory;

/**
 * This class handles when the server inexplicably disconnects
 */
public class ServerDisconnectHandler implements DisconnectHandler {
	// TODO: This is a temporary hack until I can come up with a cleaner mechanism
	public static boolean disconnectExpected;

	@Override
	public void handleDisconnect(AbstractConnection arg0) {
		// Update internal state
		TabletopTool.disconnect();

		// TODO: attempt to reconnect if this was unexpected
		if (!disconnectExpected) {
			TabletopTool.showError("Server has disconnected.");

			// hide map so player doesn't get a brief GM view
			TabletopTool.getFrame().setCurrentZoneRenderer(null);

			try {
				TabletopTool.startPersonalServer(CampaignFactory.createBasicCampaign());
			} catch (IOException ioe) {
				TabletopTool.showError("Could not restart personal server");
			}
		} else if (!TabletopTool.isPersonalServer() && !TabletopTool.isHostingServer()) {
			// expected disconnect from someone else's server
			// hide map so player doesn't get a brief GM view
			TabletopTool.getFrame().setCurrentZoneRenderer(null);
		}
		disconnectExpected = false;
	}
}
