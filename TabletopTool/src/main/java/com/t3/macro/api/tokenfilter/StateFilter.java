package com.t3.macro.api.tokenfilter;

import com.t3.model.Token;

/**
 * Filter for finding tokens by set state.
 */
public class StateFilter implements TokenFilter {
	private final String stateName;

	public StateFilter(String stateName) {
		this.stateName = stateName;
	}

	public boolean matchToken(Token t) {
		return t.hasState(stateName);
	}
}