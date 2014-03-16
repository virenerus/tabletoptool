package com.t3.macro.api.tokenfilter;

import java.math.BigDecimal;

import com.t3.model.Token;
import com.t3.model.Zone;

/**
 * Filter for finding tokens by set state.
 */
public class StateFilter implements Zone.Filter {
	private final String stateName;

	public StateFilter(String stateName) {
		this.stateName = stateName;
	}

	public boolean matchToken(Token t) {
		// Filter out the utility lib: and image: tokens
		if (t.getName().toLowerCase().startsWith("image:") || t.getName().toLowerCase().startsWith("lib:")) {
			return false;
		}
		else
			return t.hasState(stateName);
	}
}