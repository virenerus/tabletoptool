package com.t3.model.chat;

import com.t3.model.Player;

public class PlayerSpeaker extends Speaker {
	public PlayerSpeaker(Player player) {
		super(player.getName());
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