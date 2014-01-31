package net.rptools.maptool.script.mt2api.tokenfilter;

import net.rptools.maptool.model.Token;
import net.rptools.maptool.model.Zone;

/**
 * Filter for player exposed tokens.
 */
public class ExposedFilter implements Zone.Filter {
	private final Zone zone;

	public ExposedFilter(Zone zone) {
		this.zone = zone;
	}

	public boolean matchToken(Token t) {
		// Filter out the utility lib: and image: tokens
		if (t.getName().toLowerCase().startsWith("image:") || t.getName().toLowerCase().startsWith("lib:")) {
			return false;
		}
		return zone.isTokenVisible(t);
	}
}
