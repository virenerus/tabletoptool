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

package com.t3.networking;

import java.io.IOException;

import com.t3.model.campaign.CampaignFactory;
import com.t3.client.TabletopTool;
import com.t3.clientserver.connection.AbstractConnection;
import com.t3.clientserver.handler.DisconnectHandler;

/**
 * This class handles when the server inexplicably disconnects
 */
public class ServerDisconnectHandler implements DisconnectHandler {
	// TODO: This is a temporary hack until I can come up with a cleaner mechanism
	public static boolean disconnectExpected;

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
