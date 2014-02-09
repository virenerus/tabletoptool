package net.sf.mt2.chatparser;

public class ChatCommandPart extends ChatPart {

	private ChatCommand chatCommand;

	public ChatCommandPart(ChatCommand cc) {
		this.chatCommand=cc;
	}
	
	@Override
	public String getText() {
		return chatCommand.name();
	}

	@Override
	public Object getEvaluatedValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
