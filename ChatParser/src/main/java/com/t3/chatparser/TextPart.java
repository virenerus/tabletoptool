package com.t3.chatparser;

public class TextPart extends ChatPart {

	private final StringBuilder text;

	public TextPart(String text) {
		this.text=new StringBuilder(text);
	}
	
	@Override
	public String getDefaultTextRepresentation() {
		return text.toString();
	}

	public void append(String image) {
		text.append(image);
	}
	
	@Override
	public String toString() {
		return text.toString();
	}
}
