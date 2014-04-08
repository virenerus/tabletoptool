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
package com.t3.client.ui.zone;

import java.util.List;

import com.t3.model.Player;
import com.t3.model.Token;

public class PlayerView {
	private final Player.Role role;
	private final List<Token> tokens; // Optional

	// Optimization
	private final String hash;

	public PlayerView(Player.Role role) {
		this(role, null);
	}

	public PlayerView(Player.Role role, List<Token> tokens) {
		this.role = role;
		this.tokens = tokens != null && !tokens.isEmpty() ? tokens : null;
		hash = calculateHashcode();
	}

	public Player.Role getRole() {
		return role;
	}

	public boolean isGMView() {
		return role == Player.Role.GM;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public boolean isUsingTokenView() {
		return tokens != null;
	}

	@Override
	public int hashCode() {
		return hash.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof PlayerView)) {
			return false;
		}
		PlayerView other = (PlayerView) obj;
		return hash.equals(other.hash);
	}

	private String calculateHashcode() {
		StringBuilder builder = new StringBuilder();
		builder.append(role);
		if (tokens != null) {
			for (Token token : tokens) {
				builder.append(token.getId());
			}
		}
		return builder.toString();
	}
}
