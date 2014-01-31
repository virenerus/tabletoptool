package net.rptools.maptool.script.mt2api.functions.ini;

import java.util.Collections;
import java.util.List;

import net.rptools.maptool.script.mt2api.TokenView;

public class InitiativeListView {
	private final int round;
	private final String map;
	private final int current;
	private final List<InitiativeEntry> tokens;
	
	
	
	public InitiativeListView(int round, String map, int current, List<InitiativeEntry> tokens) {
		super();
		this.round = round;
		this.map = map;
		this.current = current;
		this.tokens=Collections.unmodifiableList(tokens);
	}



	public int getRound() {
		return round;
	}



	public String getMap() {
		return map;
	}



	public int getCurrent() {
		return current;
	}



	public List<InitiativeEntry> getTokens() {
		return tokens;
	}



	public static class InitiativeEntry {
		private final boolean holding;
		private final String initiative;
		private final TokenView Token;
		
		public InitiativeEntry(boolean holding, String initiative, TokenView token) {
			super();
			this.holding = holding;
			this.initiative = initiative;
			Token = token;
		}
		
		public boolean isHolding() {
			return holding;
		}
		
		public String getInitiative() {
			return initiative;
		}
		
		public TokenView getToken() {
			return Token;
		}
		
		
	}
}
