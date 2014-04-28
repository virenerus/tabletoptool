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
package com.t3.model.chat;

import com.t3.client.TabletopTool;

//TODO refactor this so that a message contains a player that sent it and if applicable the token GUID
public class TextMessage {
	// Not an enum so that it can be hessian serialized
	public enum Channel {
		ALL, // General message channel
		SAY, // Player/character speech
		GM, // GM visible only
		ME, // Targeted to the current tabletoptool client
		GROUP, // All in the group
		WHISPER; // To a specific player/character
	}
	
	private Channel channel;
	private final String target;
	private final String message;
	private final String source;
	private final Speaker speaker;

	////
	// CONSTRUCTION
	public TextMessage(Channel channel, String target, String source, String message, Speaker speaker) {
		this.channel = channel;
		this.target = target;
		this.message = message;
		this.source = source;
		this.speaker = speaker;
	}
	
	public TextMessage(Channel channel, String target, String source, String message) {
		this(channel, target, source, message, null);
	}

	public static TextMessage say(String message, Speaker speaker) {
		return new TextMessage(Channel.SAY, null, TabletopTool.getPlayer().getName(), message, speaker);
	}
	
	public static TextMessage say(String message) {
		return new TextMessage(Channel.SAY, null, TabletopTool.getPlayer().getName(), message, null);
	}

	public static TextMessage gm(String message) {
		return new TextMessage(Channel.GM, null, TabletopTool.getPlayer().getName(), message, null);
	}
	
	public static TextMessage gm(String message, Speaker speaker) {
		return new TextMessage(Channel.GM, null, TabletopTool.getPlayer().getName(), message, speaker);
	}

	public static TextMessage me(String message) {
		return new TextMessage(Channel.ME, null, TabletopTool.getPlayer().getName(), message, null);
	}

	public static TextMessage group(String target, String message) {
		return new TextMessage(Channel.GROUP, target, TabletopTool.getPlayer().getName(), message, null);
	}

	public static TextMessage whisper(String target, String message) {
		return new TextMessage(Channel.WHISPER, target, TabletopTool.getPlayer().getName(), message, null);
	}

	@Override
	public String toString() {
		return message;
	}

	////
	// PROPERTIES
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel c) {
		channel = c;
	}

	public String getTarget() {
		return target;
	}

	public String getMessage() {
		return message;
	}

	public String getSource() {
		return source;
	}

	////
	// CONVENIENCE
	public boolean isGM() {
		return channel == Channel.GM;
	}

	public boolean isMessage() {
		return channel == Channel.ALL;
	}

	public boolean isSay() {
		return channel == Channel.SAY;
	}

	public boolean isMe() {
		return channel == Channel.ME;
	}

	public boolean isGroup() {
		return channel == Channel.GROUP;
	}

	public boolean isWhisper() {
		return channel == Channel.WHISPER;
	}

	public Speaker getSpeaker() {
		return speaker;
	}
}
