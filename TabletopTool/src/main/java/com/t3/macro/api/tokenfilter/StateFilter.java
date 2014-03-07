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
		Object val = t.getState(stateName);
		// Filter out the utility lib: and image: tokens
		if (val == null) {
			return false;
		}
		if (t.getName().toLowerCase().startsWith("image:") || t.getName().toLowerCase().startsWith("lib:")) {
			return false;
		}
		if (val instanceof Boolean) {
			return ((Boolean) val).booleanValue();
		}
		if (val instanceof BigDecimal) {
			if (val.equals(BigDecimal.ZERO)) {
				return false;
			} else {
				return true;
			}
		}
		return true;
	}
}