package com.t3.macro.api.tokenfilter;

import java.math.BigDecimal;

import com.t3.model.Token;
import com.t3.model.Zone;

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