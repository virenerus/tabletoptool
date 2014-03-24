package com.t3.macro.api.tokenfilter;

import com.t3.model.Token;
import com.t3.model.Zone;

/**
 * Filter for player exposed tokens.
 */
public class ExposedFilter implements TokenFilter {
	private final Zone zone;

	public ExposedFilter(Zone zone) {
		this.zone = zone;
	}

	public boolean matchToken(Token t) {
		return zone.isTokenVisible(t);
	}
}
