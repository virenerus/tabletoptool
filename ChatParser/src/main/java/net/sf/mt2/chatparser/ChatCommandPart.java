package net.sf.mt2.chatparser;

public class ChatCommandPart extends ChatPart {

	private ChatCommand chatCommand;

	public ChatCommandPart(ChatCommand cc) {
		this.chatCommand=cc;
	}
	
	public ChatCommand getChatCommand() {
		return chatCommand;
	}
	
	@Override
	public String getDefaultTextRepresentation() {
		return chatCommand.name();
	}

}
