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

import com.t3.clientserver.Command;

public enum NetworkCommand implements Command {
	//CLIENT COMMANDS
	startAssetTransfer,
	updateAssetTransfer,
    playerConnected,
    playerDisconnected,
    setUseVision,
    
    //SERVER COMMANDS
	getZone,
	sendTokensToBack,
	bringTokensToFront,
	heartbeat,
	setVisionType,
	
	//MIXED COMMANDS
	bootPlayer,
	setCampaign,
	putZone,
	removeZone,
	putAsset,
	getAsset,
	removeAsset,
	putToken,
	removeToken,
	draw,
	clearAllDrawings,
	setZoneGridSize,
	message,
	undoDraw,
	showPointer,
	movePointer,
	hidePointer,
	startTokenMove,
	stopTokenMove,
	toggleTokenMoveWaypoint,
	updateTokenMove,
	setZoneVisibility,
	enforceZoneView,
	setZoneHasFoW,
	exposeFoW,
	hideFoW,
	setFoW,
	putLabel,
	removeLabel,
	enforceZone,
	setServerPolicy,
	addTopology,
	removeTopology,
	renameZone,
	updateCampaign,
	updateInitiative,
	updateTokenInitiative,
	updateCampaignMacros,
	setTokenLocation, // NOTE: This is to support third party token placement and shouldn't be depended on for general purpose token movement
	setLiveTypingLabel, // Experimental
	enforceNotification, // Override toggle button to show typing notifications
	exposePCArea,
	setBoard,
	updateExposedAreaMeta;
}
