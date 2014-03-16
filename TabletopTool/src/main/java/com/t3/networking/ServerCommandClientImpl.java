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

import java.awt.geom.Area;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import com.t3.MD5Key;
import com.t3.client.TabletopTool;
import com.t3.model.Asset;
import com.t3.model.AssetManager;
import com.t3.model.ExposedAreaMetaData;
import com.t3.model.GUID;
import com.t3.model.InitiativeList;
import com.t3.model.Label;
import com.t3.model.MacroButtonProperties;
import com.t3.model.Pointer;
import com.t3.model.TextMessage;
import com.t3.model.Token;
import com.t3.model.Zone;
import com.t3.model.ZonePoint;
import com.t3.model.Zone.VisionType;
import com.t3.model.campaign.Campaign;
import com.t3.model.campaign.CampaignProperties;
import com.t3.model.drawing.Drawable;
import com.t3.model.drawing.Pen;

public class ServerCommandClientImpl implements ServerCommand {

	private final TimedEventQueue movementUpdateQueue = new TimedEventQueue(100);
	private final LinkedBlockingQueue<MD5Key> assetRetrieveQueue = new LinkedBlockingQueue<MD5Key>();

	public ServerCommandClientImpl() {
		movementUpdateQueue.start();
//		new AssetRetrievalThread().start();
	}

	public void heartbeat(String data) {
		makeServerCall(NetworkCommand.heartbeat, data);
	}

	public void movePointer(String player, int x, int y) {
		makeServerCall(NetworkCommand.movePointer, player, x, y);
	}

	public void bootPlayer(String player) {
		makeServerCall(NetworkCommand.bootPlayer, player);
	}

	public void setCampaign(Campaign campaign) {
		try {
			campaign.setBeingSerialized(true);
			makeServerCall(NetworkCommand.setCampaign, campaign);
		} finally {
			campaign.setBeingSerialized(false);
		}
	}

	public void setVisionType(GUID zoneGUID, VisionType visionType) {
		makeServerCall(NetworkCommand.setVisionType, zoneGUID, visionType);
	}

	public void updateCampaign(CampaignProperties properties) {
		makeServerCall(NetworkCommand.updateCampaign, properties);
	}

	public void getZone(GUID zoneGUID) {
		makeServerCall(NetworkCommand.getZone, zoneGUID);
	}

	public void putZone(Zone zone) {
		makeServerCall(NetworkCommand.putZone, zone);
	}

	public void removeZone(GUID zoneGUID) {
		makeServerCall(NetworkCommand.removeZone, zoneGUID);
	}

	public void renameZone(GUID zoneGUID, String name) {
		makeServerCall(NetworkCommand.renameZone, zoneGUID, name);
	}

	public void putAsset(Asset asset) {
		makeServerCall(NetworkCommand.putAsset, asset);
	}

	public void getAsset(MD5Key assetID) {
		makeServerCall(NetworkCommand.getAsset, assetID);
	}

	public void removeAsset(MD5Key assetID) {
		makeServerCall(NetworkCommand.removeAsset, assetID);
	}

	public void enforceZoneView(GUID zoneGUID, int x, int y, double scale, int width, int height) {
		makeServerCall(NetworkCommand.enforceZoneView, zoneGUID, x, y, scale, width, height);
	}

	public void putToken(GUID zoneGUID, Token token) {
		// Hack to generate zone event. All functions that update tokens call this method
		// after changing the token. But they don't tell the zone about it so classes
		// waiting for the zone change event don't get it.
		TabletopTool.getCampaign().getZone(zoneGUID).putToken(token);
		makeServerCall(NetworkCommand.putToken, zoneGUID, token);
	}

	public void removeToken(GUID zoneGUID, GUID tokenGUID) {
		makeServerCall(NetworkCommand.removeToken, zoneGUID, tokenGUID);
	}

	public void putLabel(GUID zoneGUID, Label label) {
		makeServerCall(NetworkCommand.putLabel, zoneGUID, label);
	}

	public void removeLabel(GUID zoneGUID, GUID labelGUID) {
		makeServerCall(NetworkCommand.removeLabel, zoneGUID, labelGUID);
	}

	public void draw(GUID zoneGUID, Pen pen, Drawable drawable) {
		makeServerCall(NetworkCommand.draw, zoneGUID, pen, drawable);
	}

	public void clearAllDrawings(GUID zoneGUID, Zone.Layer layer) {
		makeServerCall(NetworkCommand.clearAllDrawings, zoneGUID, layer);
	}

	public void undoDraw(GUID zoneGUID, GUID drawableGUID) {
		makeServerCall(NetworkCommand.undoDraw, zoneGUID, drawableGUID);
	}

	public void setZoneGridSize(GUID zoneGUID, int xOffset, int yOffset, int size, int color) {
		makeServerCall(NetworkCommand.setZoneGridSize, zoneGUID, xOffset, yOffset, size, color);
	}

	public void setZoneVisibility(GUID zoneGUID, boolean visible) {
		makeServerCall(NetworkCommand.setZoneVisibility, zoneGUID, visible);
	}

	public void message(TextMessage message) {
		makeServerCall(NetworkCommand.message, message);
	}

	public void showPointer(String player, Pointer pointer) {
		makeServerCall(NetworkCommand.showPointer, player, pointer);
	}

	public void hidePointer(String player) {
		makeServerCall(NetworkCommand.hidePointer, player);
	}

	public void setLiveTypingLabel(String label, boolean show) {
		makeServerCall(NetworkCommand.setLiveTypingLabel, label, show);
	}

	public void enforceNotification(Boolean enforce) {
		// TabletopTool.showInformation(enforce.toString());
		makeServerCall(NetworkCommand.enforceNotification, enforce);
	}

	public void startTokenMove(String playerId, GUID zoneGUID, GUID tokenGUID, Set<GUID> tokenList) {
		makeServerCall(NetworkCommand.startTokenMove, playerId, zoneGUID, tokenGUID, tokenList);
	}

	public void stopTokenMove(GUID zoneGUID, GUID tokenGUID) {
		movementUpdateQueue.flush();
		makeServerCall(NetworkCommand.stopTokenMove, zoneGUID, tokenGUID);
	}

	public void updateTokenMove(GUID zoneGUID, GUID tokenGUID, int x, int y) {
		movementUpdateQueue.enqueue(NetworkCommand.updateTokenMove, zoneGUID, tokenGUID, x, y);
	}

	public void toggleTokenMoveWaypoint(GUID zoneGUID, GUID tokenGUID, ZonePoint cp) {
		movementUpdateQueue.flush();
		makeServerCall(NetworkCommand.toggleTokenMoveWaypoint, zoneGUID, tokenGUID, cp);
	}

	public void addTopology(GUID zoneGUID, Area area) {
		makeServerCall(NetworkCommand.addTopology, zoneGUID, area);
	}

	public void removeTopology(GUID zoneGUID, Area area) {
		makeServerCall(NetworkCommand.removeTopology, zoneGUID, area);
	}

	public void exposePCArea(GUID zoneGUID) {
		makeServerCall(NetworkCommand.exposePCArea, zoneGUID);
	}

	public void exposeFoW(GUID zoneGUID, Area area, Set<GUID> selectedToks) {
		makeServerCall(NetworkCommand.exposeFoW, zoneGUID, area, selectedToks);
	}

	public void setFoW(GUID zoneGUID, Area area, Set<GUID> selectedToks) {
		makeServerCall(NetworkCommand.setFoW, zoneGUID, area, selectedToks);
	}

	public void hideFoW(GUID zoneGUID, Area area, Set<GUID> selectedToks) {
		makeServerCall(NetworkCommand.hideFoW, zoneGUID, area, selectedToks);
	}

	public void setZoneHasFoW(GUID zoneGUID, boolean hasFog) {
		makeServerCall(NetworkCommand.setZoneHasFoW, zoneGUID, hasFog);
	}

	public void bringTokensToFront(GUID zoneGUID, Set<GUID> tokenList) {
		makeServerCall(NetworkCommand.bringTokensToFront, zoneGUID, tokenList);
	}

	public void sendTokensToBack(GUID zoneGUID, Set<GUID> tokenList) {
		makeServerCall(NetworkCommand.sendTokensToBack, zoneGUID, tokenList);
	}

	public void enforceZone(GUID zoneGUID) {
		makeServerCall(NetworkCommand.enforceZone, zoneGUID);
	}

	public void setServerPolicy(ServerPolicy policy) {
		makeServerCall(NetworkCommand.setServerPolicy, policy);
	}

	public void updateInitiative(InitiativeList list, Boolean ownerPermission) {
		makeServerCall(NetworkCommand.updateInitiative, list, ownerPermission);
	}

	public void updateTokenInitiative(GUID zone, GUID token, Boolean holding, String state, Integer index) {
		makeServerCall(NetworkCommand.updateTokenInitiative, zone, token, holding, state, index);
	}

	public void updateCampaignMacros(List<MacroButtonProperties> properties) {
		makeServerCall(NetworkCommand.updateCampaignMacros, properties);
	}

	private static void makeServerCall(NetworkCommand command, Object... params) {
		if (TabletopTool.getConnection() != null) {
			TabletopTool.getConnection().callMethod(command, params);
		}
	}

	public void setBoard(GUID zoneGUID, MD5Key mapAssetId, int x, int y) {
		// First, ensure that the possibly new map texture is available on the client
		// note: This may not be the optimal solution... can't tell from available documentation.
		//       it may send a texture that is already sent
		//       it might be better to do it in the background(?)
		//       there seem to be other ways to upload textures (?) (e.g. in T3Util)
		putAsset(AssetManager.getAsset(mapAssetId));
		// Second, tell the client to change the zone's board info
		makeServerCall(NetworkCommand.setBoard, zoneGUID, mapAssetId, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.t3.networking.ServerNetworkCommand#updateExposedAreaMeta(com.t3.model.GUID,
	 * com.t3.model.GUID, com.t3.model.ExposedAreaMetaData)
	 */
	public void updateExposedAreaMeta(GUID zoneGUID, GUID tokenExposedAreaGUID, ExposedAreaMetaData meta) {
		makeServerCall(NetworkCommand.updateExposedAreaMeta, zoneGUID, tokenExposedAreaGUID, meta);
	}

	/**
	 * Some events become obsolete very quickly, such as dragging a token around. This queue always has exactly one
	 * element, the more current version of the event. The event is then dispatched at some time interval. If a new
	 * event arrives before the time interval elapses, it is replaced. In this way, only the most current version of the
	 * event is released.
	 */
	private static class TimedEventQueue extends Thread {

		NetworkCommand command;
		Object[] params;

		long delay;

		Object sleepSemaphore = new Object();

		public TimedEventQueue(long millidelay) {
			delay = millidelay;
		}

		public synchronized void enqueue(NetworkCommand command, Object... params) {

			this.command = command;
			this.params = params;
		}

		public synchronized void flush() {

			if (command != null) {
				makeServerCall(command, params);
			}
			command = null;
			params = null;
		}

		@Override
		public void run() {

			while (true) {

				flush();
				synchronized (sleepSemaphore) {
					try {
						Thread.sleep(delay);
					} catch (InterruptedException ie) {
						// nothing to do
					}
				}
			}
		}
	}
}
