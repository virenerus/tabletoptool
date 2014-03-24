package com.t3.macro.api.tokenfilter;

import com.t3.model.Token;

public interface TokenFilter {
	public boolean matchToken(Token t);
}
