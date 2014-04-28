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

import com.t3.MD5Key;
import com.t3.model.Asset;
import com.t3.model.ExposedAreaMetaData;
import com.t3.GUID;
import com.t3.model.InitiativeList;
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

public interface ServerCommand {

	public void bootPlayer(String player);

	public void setZoneHasFoW(GUID zoneGUID, boolean hasFog);

	public void exposeFoW(GUID zoneGUID, Area area, Set<GUID> selectedToks);

	public void hideFoW(GUID zoneGUID, Area area, Set<GUID> selectedToks);

	public void setFoW(GUID zoneGUID, Area area, Set<GUID> selectedToks);

	public void addTopology(GUID zoneGUID, Area area);

	public void removeTopology(GUID zoneGUID, Area area);

	public void enforceZoneView(GUID zoneGUID, int x, int y, double scale, int width, int height);

	public void setCampaign(Campaign campaign);

	public void getZone(GUID zoneGUID);

	public void putZone(Zone zone);

	public void removeZone(GUID zoneGUID);

	public void setZoneVisibility(GUID zoneGUID, boolean visible);

	public void putAsset(Asset asset);

	public void getAsset(MD5Key assetID);

	public void removeAsset(MD5Key assetID);

	public void putToken(GUID zoneGUID, Token token);

	public void removeToken(GUID zoneGUID, GUID tokenGUID);

	public void putLabel(GUID zoneGUID, Label label);

	public void removeLabel(GUID zoneGUID, GUID labelGUID);

	public void draw(GUID zoneGUID, Pen pen, Drawable drawable);

	public void undoDraw(GUID zoneGUID, GUID drawableGUID);

	public void setZoneGridSize(GUID zoneGUID, int xOffset, int yOffset, int size, int color);

	public void message(TextMessage message);

	public void showPointer(String player, Pointer pointer);

	public void hidePointer(String player);

	public void movePointer(String player, int x, int y);

	public void startTokenMove(String playerId, GUID zoneGUID, GUID tokenGUID, Set<GUID> tokenList);

	public void updateTokenMove(GUID zoneGUID, GUID tokenGUID, int x, int y);

	public void stopTokenMove(GUID zoneGUID, GUID tokenGUID);

	public void toggleTokenMoveWaypoint(GUID zoneGUID, GUID tokenGUID, ZonePoint cp);

	public void sendTokensToBack(GUID zoneGUID, Set<GUID> tokenSet);

	public void bringTokensToFront(GUID zoneGUID, Set<GUID> tokenSet);

	public void clearAllDrawings(GUID zoneGUID, Zone.Layer layer);

	public void enforceZone(GUID zoneGUID);

	public void setServerPolicy(ServerPolicy policy);

	public void renameZone(GUID zoneGUID, String name);

	public void heartbeat(String data);

	public void updateCampaign(CampaignProperties properties);

	public void updateInitiative(InitiativeList list, Boolean ownerPermission);

	public void updateTokenInitiative(GUID zone, GUID token, Boolean hold, String state, Integer index);

	public void setVisionType(GUID zoneGUID, VisionType visionType);

	public void updateCampaignMacros(List<MacroButtonProperties> properties);

	public void setBoard(GUID zoneGUID, MD5Key mapAsset, int X, int Y);

	public void setLiveTypingLabel(String name, boolean show);

	public void enforceNotification(Boolean enforce);

	public void exposePCArea(GUID zoneGUID);

	public void updateExposedAreaMeta(GUID zoneGUID, GUID tokenExposedAreaGUID, ExposedAreaMetaData meta);
}
