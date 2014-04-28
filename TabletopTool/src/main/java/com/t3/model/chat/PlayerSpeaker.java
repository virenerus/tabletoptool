package com.t3.model.chat;

public class PlayerSpeaker extends Speaker {
	public PlayerSpeaker(String identity) {
		super(identity);
	}

	@Override
	public String toHTML() {
		return getIdentity();
	}

	@Override
	public String toString() {
		return getIdentity();
	}
}