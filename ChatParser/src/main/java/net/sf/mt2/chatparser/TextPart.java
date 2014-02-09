package net.sf.mt2.chatparser;

public class TextPart extends ChatPart {

	private final String text;

	public TextPart(String text) {
		this.text=text;
	}
	
	@Override
	public String getText() {
		return text;
	}

	@Override
	public Object getEvaluatedValue() {
		return text;
	}

}
