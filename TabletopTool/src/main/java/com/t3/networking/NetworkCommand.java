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
