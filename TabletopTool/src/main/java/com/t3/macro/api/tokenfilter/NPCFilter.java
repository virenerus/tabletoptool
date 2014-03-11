package com.t3.macro.api.tokenfilter;

import com.t3.model.Token;
import com.t3.model.Zone;

/**
 * Filter for NPC tokens.
 */
public class NPCFilter implements Zone.Filter {
	public boolean matchToken(Token t) {
		// Filter out the utility lib: and image: tokens
		if (t.getName().toLowerCase().startsWith("image:") || t.getName().toLowerCase().startsWith("lib:")) {
			return false;
		}
		return t.getType() == Token.Type.NPC;
	}
}