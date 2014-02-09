package net.rptools.maptool.script.mt2api.tokenfilter;

import net.rptools.maptool.model.Token;
import net.rptools.maptool.model.Zone;
import net.sf.json.JSONArray;

/**
 * Filter by the layer the token is on (allows selecting tokens on the
 * Object and Background layers).
 */
public class LayerFilter implements Zone.Filter {
	private final JSONArray layers;

	public LayerFilter(JSONArray layers) {
		this.layers = new JSONArray();
		for (Object s : layers) {
			String name = s.toString().toUpperCase();
			this.layers.add(Zone.Layer.valueOf("HIDDEN".equals(name) ? "GM" : name));
		}
	}

	public boolean matchToken(Token t) {
		// Filter out the utility lib: and image: tokens
		if (t.getName().toLowerCase().startsWith("image:") || t.getName().toLowerCase().startsWith("lib:")) {
			return false;
		}
		return layers.contains(t.getLayer());
	}
}