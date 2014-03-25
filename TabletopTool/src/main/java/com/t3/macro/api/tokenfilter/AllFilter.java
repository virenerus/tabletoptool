package com.t3.macro.api.tokenfilter;

import com.t3.model.Token;

/**
 * Filter for all tokens.
 */
public class AllFilter implements TokenFilter {
	public boolean matchToken(Token t) {
		return true;
	}
}
