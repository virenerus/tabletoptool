package net.rptools.maptool.script.mt2api.tokenfilter;

import net.rptools.maptool.model.Token;
import net.rptools.maptool.model.Zone;

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
