package net.rptools.maptool.script.mt2api.tokenfilter;

import net.rptools.maptool.model.Token;
import net.rptools.maptool.model.Zone;

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