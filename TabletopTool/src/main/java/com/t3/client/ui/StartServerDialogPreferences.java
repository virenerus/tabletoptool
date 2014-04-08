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
package com.t3.client.ui;

import java.util.prefs.Preferences;

import com.t3.client.AppConstants;
import com.t3.client.AppPreferences;
import com.t3.client.walker.WalkerMetric;
import com.t3.model.Player;
import com.t3.networking.ServerConfig;

public class StartServerDialogPreferences {
	private static Preferences prefs = Preferences.userRoot().node(AppConstants.APP_NAME + "/prefs/server");

	private static final String KEY_USERNAME = "name";
	private static final String KEY_ROLE = "playerRole";
	private static final String KEY_PORT = "port";
	private static final String KEY_GM_PASSWORD = "gmPassword";
	private static final String KEY_PLAYER_PASSWORD = "playerPassword";
	private static final String KEY_STRICT_TOKEN_OWNERSHIP = "strictTokenOwnership";
	private static final String KEY_REGISTER_SERVER = "registerServer";
	private static final String KEY_T3_NAME = "t3Name";
	private static final String KEY_T3_PRIVATE = "t3Private";
	private static final String KEY_PLAYERS_CAN_REVEAL_VISION = "playersCanRevealVisionCheckbox";
	private static final String KEY_USE_INDIVIDUAL_VIEWS = "useIndividualViews";
	private static final String KEY_USE_UPNP = "useUPnP";
	private static final String KEY_RESTRICTED_IMPERSONATION = "restrictedImpersonation";
	private static final String KEY_PLAYERS_RECEIVE_CAMPAIGN_MACROS = "playersReceiveCampaignMacros";
	private static final String KEY_WALKER_METRIC = "movementMetric";
	private static final String KEY_USE_INDIVIDUAL_FOW = "useIndividualFOW";
	private static final String KEY_AUTO_REVEAL_ON_MOVE = "autoRevealOnMovement";

	private static Boolean useToolTipsForUnformattedRolls = null;

	public Player.Role getRole () {
		return Player.Role.valueOf(prefs.get(KEY_ROLE, Player.Role.GM.name()));
	}

	public void setRole(Player.Role role) {
		prefs.put(KEY_ROLE, role.name());
	}

	public String getUsername() {
		return prefs.get(KEY_USERNAME, "");
	}

	public void setUsername(String name) {
		prefs.put(KEY_USERNAME, name.trim());
	}

	public void setGMPassword(String password) {
		prefs.put(KEY_GM_PASSWORD, password.trim());
	}

	public String getGMPassword() {
		return prefs.get(KEY_GM_PASSWORD, "");
	}

	public void setPlayerPassword(String password) {
		prefs.put(KEY_PLAYER_PASSWORD, password.trim());
	}

	public String getPlayerPassword() {
		return prefs.get(KEY_PLAYER_PASSWORD, "");
	}

	public int getPort() {
		return prefs.getInt(KEY_PORT, ServerConfig.DEFAULT_PORT);
	}

	public void setPort(int port) {
		prefs.putInt(KEY_PORT, port);
	}

	public boolean getUseStrictTokenOwnership() {
		return prefs.getBoolean(KEY_STRICT_TOKEN_OWNERSHIP, false);
	}

	public void setUseStrictTokenOwnership(boolean use) {
		prefs.putBoolean(KEY_STRICT_TOKEN_OWNERSHIP, use);
	}
	// my addition
	public boolean getRestrictedImpersonation() {
		return prefs.getBoolean(KEY_RESTRICTED_IMPERSONATION, true);
	}

	public void setRestrictedImpersonation (boolean impersonation) {
		prefs.putBoolean(KEY_RESTRICTED_IMPERSONATION, impersonation);
	}
	public boolean registerServer() {
		return prefs.getBoolean(KEY_REGISTER_SERVER, false);
	}

	public void setRegisterServer(boolean register) {
		prefs.putBoolean(KEY_REGISTER_SERVER, register);
	}

	public void setT3Name(String name) {
		prefs.put(KEY_T3_NAME, name.trim());
	}

	public String getT3Name() {
		return prefs.get(KEY_T3_NAME, "");
	}

	public void setT3Private(boolean flag) {
		prefs.putBoolean(KEY_T3_PRIVATE, flag);
	}

	public boolean getT3Private() {
		return prefs.getBoolean(KEY_T3_PRIVATE, false);
	}

	public void setPlayersCanRevealVision(boolean flag) {
		prefs.putBoolean(KEY_PLAYERS_CAN_REVEAL_VISION, flag);
	}

	public boolean getPlayersCanRevealVision() {
		return prefs.getBoolean(KEY_PLAYERS_CAN_REVEAL_VISION, false);
	}

	public void setUseIndividualViews(boolean flag) {
		prefs.putBoolean(KEY_USE_INDIVIDUAL_VIEWS, flag);
	}

	public boolean getUseIndividualViews() {
		return prefs.getBoolean(KEY_USE_INDIVIDUAL_VIEWS, false);
	}

	public void setUseUPnP(boolean op) {
		prefs.putBoolean(KEY_USE_UPNP, op);
	}

	public boolean getUseUPnP() {
		return prefs.getBoolean(KEY_USE_UPNP, false);
	}

	public void setPlayersReceiveCampaignMacros(boolean flag) {
		prefs.putBoolean(KEY_PLAYERS_RECEIVE_CAMPAIGN_MACROS, flag);
	}

	public boolean getPlayersReceiveCampaignMacros() {
		return prefs.getBoolean(KEY_PLAYERS_RECEIVE_CAMPAIGN_MACROS, false);
	}

	public boolean getUseToolTipsForUnformattedRolls() {
		// Tool tips works slightly differently as its a setting that has to be available
		// to the user to configure before the start server dialog. So if it has not been
		// specified we default to the users preferences.
		if (useToolTipsForUnformattedRolls == null) {
			return AppPreferences.getUseToolTipForInlineRoll();
		}
		return useToolTipsForUnformattedRolls;
	}

	public void setUseToolTipsForUnformattedRolls(boolean flag)  {
		useToolTipsForUnformattedRolls = flag;
	}

	public WalkerMetric getMovementMetric() {
		String metric = prefs.get(KEY_WALKER_METRIC, "ONE_ONE_ONE");
		return WalkerMetric.valueOf(metric);
	}

	public void setMovementMetric(WalkerMetric metric) {
		prefs.put(KEY_WALKER_METRIC, metric.toString());
	}

	public boolean getUseIndividualFOW() {
		return prefs.getBoolean(KEY_USE_INDIVIDUAL_FOW, false);
	}

	public void setUseIndividualFOW(boolean flag) {
		prefs.putBoolean(KEY_USE_INDIVIDUAL_FOW, flag);
	}
	
	public boolean isAutoRevealOnMovement() {
		return prefs.getBoolean(KEY_AUTO_REVEAL_ON_MOVE, false);
	}

	public void setAutoRevealOnMovement(boolean flag) {
		prefs.putBoolean(KEY_AUTO_REVEAL_ON_MOVE, flag);
	}
}
