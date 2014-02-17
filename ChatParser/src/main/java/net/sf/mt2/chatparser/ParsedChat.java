package net.sf.mt2.chatparser;

import java.util.ArrayList;

public class ParsedChat extends ArrayList<ChatPart> {
	
	private ChatCommand chatCommand;
	private String[] arguments;

	public ParsedChat() {}
	
	public ParsedChat(TextPart textPart) {
		super(1);
		this.add(textPart);
	}

	public ChatCommand getChatCommand() {
		return chatCommand;
	}

	public void setChatCommand(ChatCommand chatCommand) {
		this.chatCommand = chatCommand;
	}

	public ChatPart getLast() {
		int size=size();
		if(size==0)
			return null;
		else
			return get(size-1);
	}

	public String[] getArguments() {
		return arguments;
	}

	public void setArguments(String... arguments) {
		this.arguments = arguments;
	}
}
