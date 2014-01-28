package net.rptools.maptool.script.mt2.tokenfilter;

import net.rptools.maptool.model.Token;
import net.rptools.maptool.model.Zone;

/**
 * Filter for all tokens.
 */
public class AllFilter implements Zone.Filter {
	public boolean matchToken(Token t) {
		// Filter out the utility lib: and image: tokens
		if (t.getName().toLowerCase().startsWith("image:") || t.getName().toLowerCase().startsWith("lib:")) {
			return false;
		}
		return true;
	}
}
