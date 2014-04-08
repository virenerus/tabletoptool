/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3.chatparser;

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
