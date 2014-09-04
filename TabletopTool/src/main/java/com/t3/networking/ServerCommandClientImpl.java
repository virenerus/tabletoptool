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

import java.awt.geom.Area;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import com.t3.MD5Key;
import com.t3.client.TabletopTool;
import com.t3.guid.GUID;
import com.t3.model.Asset;
import com.t3.model.AssetManager;
import com.t3.model.ExposedAreaMetaData;
import com.t3.model.Label;
import com.t3.model.MacroButtonProperties;
import com.t3.model.Pointer;
import com.t3.model.Token;
import com.t3.model.Zone;
import com.t3.model.Zone.VisionType;
import com.t3.model.ZonePoint;
import com.t3.model.campaign.Campaign;
import com.t3.model.campaign.CampaignProperties;
import com.t3.model.chat.TextMessage;
import com.t3.model.drawing.Drawable;
import com.t3.model.drawing.Pen;
import com.t3.model.initiative.InitiativeList;
import com.t3.model.initiative.InitiativeValue;

public class ServerCommandClientImpl implements ServerCommand {

	private final TimedEventQueue movementUpdateQueue = new TimedEventQueue(100);
	private final LinkedBlockingQueue<MD5Key> assetRetrieveQueue = new LinkedBlockingQueue<MD5Key>();

	public ServerCommandClientImpl() {
		movementUpdateQueue.start();
//		new AssetRetrievalThread().start();
	}

	@Override
	public void heartbeat(String data) {
		makeServerCall(NetworkCommand.heartbeat, data);
	}

	@Override
	public void movePointer(String player, int x, int y) {
		makeServerCall(NetworkCommand.movePointer, player, x, y);
	}

	@Override
	public void bootPlayer(String player) {
		makeServerCall(NetworkCommand.bootPlayer, player);
	}

	@Override
	public void setCampaign(Campaign campaign) {
		try {
			campaign.setBeingSerialized(true);
			makeServerCall(NetworkCommand.setCampaign, campaign);
		} finally {
			campaign.setBeingSerialized(false);
		}
	}

	@Override
	public void setVisionType(GUID zoneGUID, VisionType visionType) {
		makeServerCall(NetworkCommand.setVisionType, zoneGUID, visionType);
	}

	@Override
	public void updateCampaign(CampaignProperties properties) {
		makeServerCall(NetworkCommand.updateCampaign, properties);
	}

	@Override
	public void getZone(GUID zoneGUID) {
		makeServerCall(NetworkCommand.getZone, zoneGUID);
	}

	@Override
	public void putZone(Zone zone) {
		makeServerCall(NetworkCommand.putZone, zone);
	}

	@Override
	public void removeZone(GUID zoneGUID) {
		makeServerCall(NetworkCommand.removeZone, zoneGUID);
	}

	@Override
	public void renameZone(GUID zoneGUID, String name) {
		makeServerCall(NetworkCommand.renameZone, zoneGUID, name);
	}

	@Override
	public void putAsset(Asset asset) {
		makeServerCall(NetworkCommand.putAsset, asset);
	}

	@Override
	public void getAsset(MD5Key assetID) {
		makeServerCall(NetworkCommand.getAsset, assetID);
	}

	@Override
	public void removeAsset(MD5Key assetID) {
		makeServerCall(NetworkCommand.removeAsset, assetID);
	}

	@Override
	public void enforceZoneView(GUID zoneGUID, int x, int y, double scale, int width, int height) {
		makeServerCall(NetworkCommand.enforceZoneView, zoneGUID, x, y, scale, width, height);
	}

	@Override
	public void putToken(GUID zoneGUID, Token token) {
		// Hack to generate zone event. All functions that update tokens call this method
		// after changing the token. But they don't tell the zone about it so classes
		// waiting for the zone change event don't get it.
		TabletopTool.getCampaign().getZone(zoneGUID).putToken(token);
		makeServerCall(NetworkCommand.putToken, zoneGUID, token);
	}

	@Override
	public void removeToken(GUID zoneGUID, GUID tokenGUID) {
		makeServerCall(NetworkCommand.removeToken, zoneGUID, tokenGUID);
	}

	@Override
	public void putLabel(GUID zoneGUID, Label label) {
		makeServerCall(NetworkCommand.putLabel, zoneGUID, label);
	}

	@Override
	public void removeLabel(GUID zoneGUID, GUID labelGUID) {
		makeServerCall(NetworkCommand.removeLabel, zoneGUID, labelGUID);
	}

	@Override
	public void draw(GUID zoneGUID, Pen pen, Drawable drawable) {
		makeServerCall(NetworkCommand.draw, zoneGUID, pen, drawable);
	}

	@Override
	public void clearAllDrawings(GUID zoneGUID, Zone.Layer layer) {
		makeServerCall(NetworkCommand.clearAllDrawings, zoneGUID, layer);
	}

	@Override
	public void undoDraw(GUID zoneGUID, GUID drawableGUID) {
		makeServerCall(NetworkCommand.undoDraw, zoneGUID, drawableGUID);
	}

	@Override
	public void setZoneGridSize(GUID zoneGUID, int xOffset, int yOffset, int size, int color) {
		makeServerCall(NetworkCommand.setZoneGridSize, zoneGUID, xOffset, yOffset, size, color);
	}

	@Override
	public void setZoneVisibility(GUID zoneGUID, boolean visible) {
		makeServerCall(NetworkCommand.setZoneVisibility, zoneGUID, visible);
	}

	@Override
	public void message(TextMessage message) {
		makeServerCall(NetworkCommand.message, message);
	}

	@Override
	public void showPointer(String player, Pointer pointer) {
		makeServerCall(NetworkCommand.showPointer, player, pointer);
	}

	@Override
	public void hidePointer(String player) {
		makeServerCall(NetworkCommand.hidePointer, player);
	}

	@Override
	public void setLiveTypingLabel(String label, boolean show) {
		makeServerCall(NetworkCommand.setLiveTypingLabel, label, show);
	}

	@Override
	public void enforceNotification(Boolean enforce) {
		// TabletopTool.showInformation(enforce.toString());
		makeServerCall(NetworkCommand.enforceNotification, enforce);
	}

	@Override
	public void startTokenMove(String playerId, GUID zoneGUID, GUID tokenGUID, Set<GUID> tokenList) {
		makeServerCall(NetworkCommand.startTokenMove, playerId, zoneGUID, tokenGUID, tokenList);
	}

	@Override
	public void stopTokenMove(GUID zoneGUID, GUID tokenGUID) {
		movementUpdateQueue.flush();
		makeServerCall(NetworkCommand.stopTokenMove, zoneGUID, tokenGUID);
	}

	@Override
	public void updateTokenMove(GUID zoneGUID, GUID tokenGUID, int x, int y) {
		movementUpdateQueue.enqueue(NetworkCommand.updateTokenMove, zoneGUID, tokenGUID, x, y);
	}

	@Override
	public void toggleTokenMoveWaypoint(GUID zoneGUID, GUID tokenGUID, ZonePoint cp) {
		movementUpdateQueue.flush();
		makeServerCall(NetworkCommand.toggleTokenMoveWaypoint, zoneGUID, tokenGUID, cp);
	}

	@Override
	public void addTopology(GUID zoneGUID, Area area) {
		makeServerCall(NetworkCommand.addTopology, zoneGUID, area);
	}

	@Override
	public void removeTopology(GUID zoneGUID, Area area) {
		makeServerCall(NetworkCommand.removeTopology, zoneGUID, area);
	}

	@Override
	public void exposePCArea(GUID zoneGUID) {
		makeServerCall(NetworkCommand.exposePCArea, zoneGUID);
	}

	@Override
	public void exposeFoW(GUID zoneGUID, Area area, Set<GUID> selectedToks) {
		makeServerCall(NetworkCommand.exposeFoW, zoneGUID, area, selectedToks);
	}

	@Override
	public void setFoW(GUID zoneGUID, Area area, Set<GUID> selectedToks) {
		makeServerCall(NetworkCommand.setFoW, zoneGUID, area, selectedToks);
	}

	@Override
	public void hideFoW(GUID zoneGUID, Area area, Set<GUID> selectedToks) {
		makeServerCall(NetworkCommand.hideFoW, zoneGUID, area, selectedToks);
	}

	@Override
	public void setZoneHasFoW(GUID zoneGUID, boolean hasFog) {
		makeServerCall(NetworkCommand.setZoneHasFoW, zoneGUID, hasFog);
	}

	@Override
	public void bringTokensToFront(GUID zoneGUID, Set<GUID> tokenList) {
		makeServerCall(NetworkCommand.bringTokensToFront, zoneGUID, tokenList);
	}

	@Override
	public void sendTokensToBack(GUID zoneGUID, Set<GUID> tokenList) {
		makeServerCall(NetworkCommand.sendTokensToBack, zoneGUID, tokenList);
	}

	@Override
	public void enforceZone(GUID zoneGUID) {
		makeServerCall(NetworkCommand.enforceZone, zoneGUID);
	}

	@Override
	public void setServerPolicy(ServerPolicy policy) {
		makeServerCall(NetworkCommand.setServerPolicy, policy);
	}

	@Override
	public void updateInitiative(InitiativeList list, Boolean ownerPermission) {
		makeServerCall(NetworkCommand.updateInitiative, list, ownerPermission);
	}

	@Override
	public void updateTokenInitiative(GUID zone, GUID token, Boolean holding, InitiativeValue state, Integer index) {
		makeServerCall(NetworkCommand.updateTokenInitiative, zone, token, holding, state, index);
	}

	@Override
	public void updateCampaignMacros(List<MacroButtonProperties> properties) {
		makeServerCall(NetworkCommand.updateCampaignMacros, properties);
	}

	private static void makeServerCall(NetworkCommand command, Object... params) {
		if (TabletopTool.getConnection() != null) {
			TabletopTool.getConnection().callMethod(command, params);
		}
	}

	@Override
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
	 * @see com.t3.networking.ServerNetworkCommand#updateExposedAreaMeta(com.t3.GUID,
	 * com.t3.GUID, com.t3.model.ExposedAreaMetaData)
	 */
	@Override
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
