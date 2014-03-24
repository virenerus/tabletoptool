package com.t3.macro.api.tokenfilter;

import com.t3.model.Token;
import com.t3.model.Zone;

/**
 * Filter for finding tokens by owner.
 */
public class OwnedFilter implements TokenFilter {
	private final String playerID;

	public OwnedFilter(String playerID) {
		this.playerID = playerID;
	}

	public boolean matchToken(Token t) {
		return t.isOwner(playerID);
	}
}