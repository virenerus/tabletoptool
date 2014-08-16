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

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.geom.Area;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.t3.MD5Key;
import com.t3.client.AppActions;
import com.t3.client.AppPreferences;
import com.t3.client.AppUtil;
import com.t3.client.ScreenPoint;
import com.t3.client.TabletopTool;
import com.t3.client.ui.T3Frame;
import com.t3.client.ui.tokenpanel.InitiativePanel;
import com.t3.client.ui.zone.FogUtil;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.client.ui.zone.ZoneRendererFactory;
import com.t3.clientserver.handler.AbstractMethodHandler;
import com.t3.guid.GUID;
import com.t3.model.Asset;
import com.t3.model.AssetManager;
import com.t3.model.CellPoint;
import com.t3.model.ExposedAreaMetaData;
import com.t3.model.Label;
import com.t3.model.MacroButtonProperties;
import com.t3.model.Player;
import com.t3.model.Pointer;
import com.t3.model.Token;
import com.t3.model.Zone;
import com.t3.model.Zone.VisionType;
import com.t3.model.ZonePoint;
import com.t3.model.campaign.Campaign;
import com.t3.model.campaign.CampaignProperties;
import com.t3.model.chat.TextMessage;
import com.t3.model.drawing.Drawable;
import com.t3.model.drawing.DrawnElement;
import com.t3.model.drawing.Pen;
import com.t3.model.grid.Grid;
import com.t3.model.initiative.InitiativeList;
import com.t3.model.initiative.InitiativeValue;
import com.t3.model.initiative.InitiativeList.TokenInitiative;
import com.t3.transfer.AssetChunk;
import com.t3.transfer.AssetConsumer;
import com.t3.transfer.AssetHeader;

public class ClientMethodHandler extends AbstractMethodHandler<NetworkCommand> {
	public ClientMethodHandler() {
	}

	@Override
	public void handleMethod(final String id, final NetworkCommand method, final Object... parameters) {

		
//		System.out.println("ClientMethodHandler#handleMethod: " + cmd.name());

		// These commands are safe to do in the background, any events that cause model updates need
		// to be on the EDT (See next section)
		switch (method) {
		case putAsset:
			AssetManager.putAsset((Asset) parameters[0]);
			TabletopTool.getFrame().getCurrentZoneRenderer().flushDrawableRenderer();
			TabletopTool.getFrame().refresh();
			return;

		case removeAsset:
			return;

		case startAssetTransfer:
			AssetHeader header = (AssetHeader) parameters[0];
			TabletopTool.getAssetTransferManager().addConsumer(new AssetConsumer(AppUtil.getTmpDir(), header));
			return;

		case updateAssetTransfer:
			AssetChunk chunk = (AssetChunk) parameters[0];
			try {
				TabletopTool.getAssetTransferManager().update(chunk);
			} catch (IOException ioe) {
				// TODO: do something intelligent like clear the transfer
				// manager, and clear the "we're waiting for" flag so that it
				// gets requested again
				ioe.printStackTrace();
			}
			return;
		}

		// Model events need to update on the EDT
		EventQueue.invokeLater(new Runnable() {
			@Override
			@SuppressWarnings("unchecked")
			public void run() {
				GUID zoneGUID;
				Zone zone;
				Set<GUID> selectedToks = null;

				switch (method) {
				case bootPlayer:
					String playerName = (String) parameters[0];
					if (TabletopTool.getPlayer().getName().equals(playerName)) {
						ServerDisconnectHandler.disconnectExpected = true;
						AppActions.disconnectFromServer();
						TabletopTool.showInformation("You have been booted from the server.");
					}
					return;

				case enforceZone:
					zoneGUID = (GUID) parameters[0];
					ZoneRenderer renderer = TabletopTool.getFrame().getZoneRenderer(zoneGUID);

					if (renderer != null && renderer != TabletopTool.getFrame().getCurrentZoneRenderer() && (renderer.getZone().isVisible() || TabletopTool.getPlayer().isGM())) {
						TabletopTool.getFrame().setCurrentZoneRenderer(renderer);
					}
					return;

				case clearAllDrawings:
					zoneGUID = (GUID) parameters[0];
					Zone.Layer layer = (Zone.Layer) parameters[1];
					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					zone.getDrawnElements(layer).clear();

					TabletopTool.getFrame().refresh();
					return;

				case setZoneHasFoW:
					zoneGUID = (GUID) parameters[0];
					boolean hasFog = (Boolean) parameters[1];

					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					zone.setHasFog(hasFog);

					// In case we're looking at the zone
					TabletopTool.getFrame().refresh();
					return;

				case exposeFoW:
					zoneGUID = (GUID) parameters[0];
					Area area = (Area) parameters[1];

					if (parameters.length > 2) {
						if (parameters[2] != null) {
							selectedToks = (Set<GUID>) parameters[2];
						}
					}
					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					zone.exposeArea(area, selectedToks);
					TabletopTool.getFrame().refresh();
					return;

				case setFoW:
					zoneGUID = (GUID) parameters[0];
					area = (Area) parameters[1];

					if (parameters.length > 2) {
						if (parameters[2] != null) {
							selectedToks = (Set<GUID>) parameters[2];
						}
					}
					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					zone.setFogArea(area, selectedToks);
					TabletopTool.getFrame().refresh();
					return;

				case hideFoW:
					zoneGUID = (GUID) parameters[0];
					area = (Area) parameters[1];

					if (parameters.length > 2) {
						if (parameters[2] != null) {
							selectedToks = (Set<GUID>) parameters[2];
						}
					}
					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					zone.hideArea(area, selectedToks);
					TabletopTool.getFrame().refresh();
					return;

				case setCampaign:
					Campaign campaign = (Campaign) parameters[0];
					TabletopTool.setCampaign(campaign);

					// Hide the "Connecting" overlay
					TabletopTool.getFrame().hideGlassPane();
					return;

				case putZone:
					zone = (Zone) parameters[0];
					TabletopTool.getCampaign().putZone(zone);

					// TODO: combine this with TabletopTool.addZone()
					renderer = ZoneRendererFactory.newRenderer(zone);
					TabletopTool.getFrame().addZoneRenderer(renderer);
					if (TabletopTool.getFrame().getCurrentZoneRenderer() == null && zone.isVisible()) {
						TabletopTool.getFrame().setCurrentZoneRenderer(renderer);
					}
					TabletopTool.getEventDispatcher().fireEvent(TabletopTool.ZoneEvent.Added, TabletopTool.getCampaign(), null, zone);
					return;

				case removeZone:
					zoneGUID = (GUID) parameters[0];
					TabletopTool.getCampaign().removeZone(zoneGUID);
					TabletopTool.getFrame().removeZoneRenderer(TabletopTool.getFrame().getZoneRenderer(zoneGUID));
					return;

				case putToken:
					zoneGUID = (GUID) parameters[0];
					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					Token token = (Token) parameters[1];
					zone.putToken(token);
					TabletopTool.getFrame().refresh();
					return;

				case putLabel:
					zoneGUID = (GUID) parameters[0];
					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					Label label = (Label) parameters[1];
					zone.putLabel(label);
					TabletopTool.getFrame().refresh();
					return;

				case removeToken:
					zoneGUID = (GUID) parameters[0];
					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					GUID tokenGUID = (GUID) parameters[1];
					zone.removeToken(tokenGUID);
					TabletopTool.getFrame().refresh();
					return;

				case removeLabel:
					zoneGUID = (GUID) parameters[0];
					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					GUID labelGUID = (GUID) parameters[1];
					zone.removeLabel(labelGUID);
					TabletopTool.getFrame().refresh();
					return;

				case enforceZoneView:
					zoneGUID = (GUID) parameters[0];
					int x = (Integer) parameters[1];
					int y = (Integer) parameters[2];
					double scale = (Double) parameters[3];
					int gmWidth = (Integer) parameters[4];
					int gmHeight = (Integer) parameters[5];

					renderer = TabletopTool.getFrame().getZoneRenderer(zoneGUID);
					if (renderer == null) {
						return;
					}
					if (AppPreferences.getFitGMView()) {
						renderer.enforceView(x, y, scale, gmWidth, gmHeight);
					} else {
						renderer.setScale(scale);
						renderer.centerOn(new ZonePoint(x, y));
					}
					return;

				case draw:
					zoneGUID = (GUID) parameters[0];
					Pen pen = (Pen) parameters[1];
					Drawable drawable = (Drawable) parameters[2];

					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					zone.addDrawable(new DrawnElement(drawable, pen));
					TabletopTool.getFrame().refresh();
					return;

				case undoDraw:
					zoneGUID = (GUID) parameters[0];
					GUID drawableId = (GUID) parameters[1];
					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					if (zone == null) {
						return;
					}
					zone.removeDrawable(drawableId);
					if (TabletopTool.getFrame().getCurrentZoneRenderer().getZone().getId().equals(zoneGUID) && zoneGUID != null) {
						TabletopTool.getFrame().refresh();
					}
					return;

				case setZoneVisibility:
					zoneGUID = (GUID) parameters[0];
					boolean visible = (Boolean) parameters[1];

					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					zone.setVisible(visible);

					ZoneRenderer currentRenderer = TabletopTool.getFrame().getCurrentZoneRenderer();
					if (!visible && !TabletopTool.getPlayer().isGM() && currentRenderer != null && currentRenderer.getZone().getId().equals(zoneGUID)) {
						TabletopTool.getFrame().setCurrentZoneRenderer(null);
					}
					if (visible && currentRenderer == null) {
						currentRenderer = TabletopTool.getFrame().getZoneRenderer(zoneGUID);
						TabletopTool.getFrame().setCurrentZoneRenderer(currentRenderer);
					}
					TabletopTool.getFrame().refresh();
					return;

				case setZoneGridSize:
					zoneGUID = (GUID) parameters[0];
					int xOffset = ((Integer) parameters[1]).intValue();
					int yOffset = ((Integer) parameters[2]).intValue();
					int size = ((Integer) parameters[3]).intValue();
					int color = ((Integer) parameters[4]).intValue();

					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					zone.getGrid().setSize(size);
					zone.getGrid().setOffset(xOffset, yOffset);
					zone.setGridColor(color);

					TabletopTool.getFrame().refresh();
					return;

				case playerConnected:
					TabletopTool.addPlayer((Player) parameters[0]);
					TabletopTool.getFrame().refresh();
					return;

				case playerDisconnected:
					TabletopTool.removePlayer((Player) parameters[0]);
					TabletopTool.getFrame().refresh();
					return;

				case message:
					TextMessage message = (TextMessage) parameters[0];
					TabletopTool.addServerMessage(message);
					return;

				case showPointer:
					TabletopTool.getFrame().getPointerOverlay().addPointer((String) parameters[0], (Pointer) parameters[1]);
					TabletopTool.getFrame().refresh();
					return;

				case hidePointer:
					TabletopTool.getFrame().getPointerOverlay().removePointer((String) parameters[0]);
					TabletopTool.getFrame().refresh();
					return;

				case startTokenMove:
					String playerId = (String) parameters[0];
					zoneGUID = (GUID) parameters[1];
					GUID keyToken = (GUID) parameters[2];
					Set<GUID> selectedSet = (Set<GUID>) parameters[3];

					renderer = TabletopTool.getFrame().getZoneRenderer(zoneGUID);
					renderer.addMoveSelectionSet(playerId, keyToken, selectedSet, true);
					return;

				case stopTokenMove:
					zoneGUID = (GUID) parameters[0];
					keyToken = (GUID) parameters[1];

					renderer = TabletopTool.getFrame().getZoneRenderer(zoneGUID);
					renderer.removeMoveSelectionSet(keyToken);
					return;

				case updateTokenMove:
					zoneGUID = (GUID) parameters[0];
					keyToken = (GUID) parameters[1];

					x = ((Integer) parameters[2]).intValue();
					y = ((Integer) parameters[3]).intValue();

					renderer = TabletopTool.getFrame().getZoneRenderer(zoneGUID);
					renderer.updateMoveSelectionSet(keyToken, new ZonePoint(x, y));
					return;

				case setTokenLocation:
					// Only the table should process this
					if (TabletopTool.getPlayer().getName().equalsIgnoreCase("Table")) {
//						System.out.println("Inside ClientMethodHandler.handleMethod().setTokenLocation");
						zoneGUID = (GUID) parameters[0];
						keyToken = (GUID) parameters[1];

						// This X,Y is the where the center of the token needs to be placed in
						// relation to the screen. So 0,0 would be top left which means only 1/4
						// of token would be drawn. 1024,768 would be lower right (on my table).
						x = ((Integer) parameters[2]).intValue();
						y = ((Integer) parameters[3]).intValue();

						// Get the zone
						zone = TabletopTool.getCampaign().getZone(zoneGUID);
						// Get the token
						token = zone.getToken(keyToken);

						Grid grid = zone.getGrid();
						// Convert the X/Y to the screen point
						renderer = TabletopTool.getFrame().getZoneRenderer(zone);
						CellPoint newPoint = renderer.getCellAt(new ScreenPoint(x, y));
						ZonePoint zp2 = grid.convert(newPoint);

						token.setX(zp2.x);
						token.setY(zp2.y);

						TabletopTool.serverCommand().putToken(zoneGUID, token);
					}
					return;

				case toggleTokenMoveWaypoint:
					zoneGUID = (GUID) parameters[0];
					keyToken = (GUID) parameters[1];
					ZonePoint zp = (ZonePoint) parameters[2];

					renderer = TabletopTool.getFrame().getZoneRenderer(zoneGUID);
					renderer.toggleMoveSelectionSetWaypoint(keyToken, zp);
					return;

				case setServerPolicy:
					ServerPolicy policy = (ServerPolicy) parameters[0];
					TabletopTool.setServerPolicy(policy);
					return;

				case addTopology:
					zoneGUID = (GUID) parameters[0];
					area = (Area) parameters[1];

					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					zone.addTopology(area);

					TabletopTool.getFrame().getZoneRenderer(zoneGUID).repaint();
					return;

				case removeTopology:
					zoneGUID = (GUID) parameters[0];
					area = (Area) parameters[1];

					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					zone.removeTopology(area);

					TabletopTool.getFrame().getZoneRenderer(zoneGUID).repaint();
					return;

				case renameZone:
					zoneGUID = (GUID) parameters[0];
					String name = (String) parameters[1];

					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					if (zone != null) {
						zone.setName(name);
					}
					return;

				case updateCampaign:
					CampaignProperties properties = (CampaignProperties) parameters[0];

					TabletopTool.getCampaign().replaceCampaignProperties(properties);
					T3Frame frame = TabletopTool.getFrame();
					ZoneRenderer zr = frame.getCurrentZoneRenderer();
					if (zr != null) {
						zr.getZoneView().flush();
						zr.repaint();
					}
					AssetManager.updateRepositoryList();

					InitiativePanel ip = frame.getInitiativePanel();
					ip.setOwnerPermissions(properties.isInitiativeOwnerPermissions());
					ip.setMovementLock(properties.isInitiativeMovementLock());
					TabletopTool.getFrame().getLookupTablePanel().updateView();
					return;

				case movePointer:
					String player = (String) parameters[0];
					x = (Integer) parameters[1];
					y = (Integer) parameters[2];

					Pointer pointer = TabletopTool.getFrame().getPointerOverlay().getPointer(player);
					if (pointer == null) {
						return;
					}
					pointer.setX(x);
					pointer.setY(y);

					TabletopTool.getFrame().refresh();
					return;

				case updateInitiative:
					InitiativeList list = (InitiativeList) parameters[0];
					Boolean ownerPermission = (Boolean) parameters[1];
					if (list != null) {
						zone = list.getZone();
						if (zone == null)
							return;
						zone.setInitiativeList(list);
					}
					if (ownerPermission != null) {
						TabletopTool.getFrame().getInitiativePanel().setOwnerPermissions(ownerPermission.booleanValue());
					}
					return;

				case updateTokenInitiative:
					zoneGUID = (GUID) parameters[0];
					tokenGUID = (GUID) parameters[1];
					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					list = zone.getInitiativeList();
					TokenInitiative ti = list.getTokenInitiative((Integer) parameters[4]);
					if (!ti.getId().equals(tokenGUID)) {
						// Index doesn't point to same token, try to find it
						token = zone.getToken(tokenGUID);
						List<Integer> tokenIndex = list.indexOf(token);

						// If token in list more than one time, punt
						if (tokenIndex.size() != 1)
							return;
						ti = list.getTokenInitiative(tokenIndex.get(0));
					} // endif
					ti.update((Boolean) parameters[2], (InitiativeValue) parameters[3]);
					return;

				case setUseVision:
					zoneGUID = (GUID) parameters[0];
					VisionType visionType = (VisionType) parameters[1];
					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					if (zone != null) {
						zone.setVisionType(visionType);
						if (TabletopTool.getFrame().getCurrentZoneRenderer() != null) {
							TabletopTool.getFrame().getCurrentZoneRenderer().flushFog();
							TabletopTool.getFrame().getCurrentZoneRenderer().getZoneView().flush();
						}
						TabletopTool.getFrame().refresh();
					}
					return;

				case setBoard:
					zoneGUID = (GUID) parameters[0];
					zone = TabletopTool.getCampaign().getZone(zoneGUID);

					Point boardXY = new Point((Integer) parameters[2], (Integer) parameters[3]);
					zone.setBoard(boardXY, (MD5Key) parameters[1]);
					return;

				case updateCampaignMacros:
					TabletopTool.getCampaign().setMacroButtonPropertiesArray(new ArrayList<MacroButtonProperties>((ArrayList<MacroButtonProperties>) parameters[0]));
					TabletopTool.getFrame().getCampaignPanel().reset();
					return;
					// moved this down into the event queue section so that the threading works as expected

				case setLiveTypingLabel:
					if ((Boolean) parameters[1]) {
						// add a typer
						TabletopTool.getFrame().getChatNotificationTimers().setChatTyper(parameters[0].toString());
						return;
					} else {
						// remove typer from list
						TabletopTool.getFrame().getChatNotificationTimers().removeChatTyper(parameters[0].toString());
						return;
					}

				case exposePCArea:
					if (parameters[0] != null && parameters[0] instanceof GUID) {
						ZoneRenderer currentRenderer1 = TabletopTool.getFrame().getZoneRenderer((GUID) parameters[0]);
						FogUtil.exposePCArea(currentRenderer1);
					}
					return;

				case enforceNotification:
					Boolean enforce = (Boolean) parameters[0];
					TabletopTool.getFrame().getCommandPanel().disableNotifyButton(enforce);
					return;

				case updateExposedAreaMeta:
					zoneGUID = (GUID) parameters[0];
					tokenGUID = (GUID) parameters[1];
					ExposedAreaMetaData meta = (ExposedAreaMetaData) parameters[2];
					zone = TabletopTool.getCampaign().getZone(zoneGUID);
					zone.setExposedAreaMetaData(tokenGUID, meta);
					return;
				}
			}
		});
	}
}
