package com.t3.macro.api.tokenfilter;

import com.t3.model.Token;
import com.t3.model.Zone;

/**
 * Filter for finding tokens by owner.
 */
public class OwnedFilter implements Zone.Filter {
	private final String playerID;

	public OwnedFilter(String playerID) {
		this.playerID = playerID;
	}

	public boolean matchToken(Token t) {
		// Filter out the utility lib: and image: tokens
		if (t.getName().toLowerCase().startsWith("image:") || t.getName().toLowerCase().startsWith("lib:")) {
			return false;
		}
		return t.isOwner(playerID);
	}
}