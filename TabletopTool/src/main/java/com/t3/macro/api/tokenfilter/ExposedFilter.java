package com.t3.macro.api.tokenfilter;

import com.t3.model.Token;
import com.t3.model.Zone;

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
