package com.t3.macro.api.tokenfilter;

import com.t3.model.Token;

/**
 * Filter for NPC tokens.
 */
public class NPCFilter implements TokenFilter {
	public boolean matchToken(Token t) {
		return t.getType() == Token.Type.NPC;
	}
}
