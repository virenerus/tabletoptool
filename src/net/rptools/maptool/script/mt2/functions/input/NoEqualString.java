package net.rptools.maptool.script.mt2.functions.input;

/** Class found on web to work around a STUPID SWING BUG with JComboBox */
public class NoEqualString {
	private final String text;

	public NoEqualString(String txt) {
		text = txt;
	}

	@Override
	public String toString() {
		return text;
	}
}