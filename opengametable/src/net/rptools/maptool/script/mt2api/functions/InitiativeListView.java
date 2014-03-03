package net.rptools.maptool.script.mt2api.functions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.model.InitiativeList;
import net.rptools.maptool.model.InitiativeList.TokenInitiative;
import net.rptools.maptool.model.InitiativeListModel;
import net.rptools.maptool.model.Token;
import net.rptools.maptool.model.Token.Type;
import net.rptools.maptool.script.mt2api.TokenView;

public class InitiativeListView {
	private InitiativeList list;
	private boolean hideNPCs;

	/**
	 * This creates a safe view of the initiative list for the script engine. If
	 * the NPCs are visible depends on the settings of the initiative panel.
	 */
	public InitiativeListView() {
		list = list;
		this.hideNPCs = list.isHideNPC();
	}

	/**
	 * This creates a safe view of the initiative list for the script engine.
	 * 
	 * @param hideNPCs
	 *            if this is true all NPCs are invisible if you are not the GM
	 */
	public InitiativeListView(boolean hideNPCs) {
		this.hideNPCs = hideNPCs;
		list = list;
	}

	/**
	 * @return the current round
	 */
	public int getRound() {
		return list.getRound();
	}

	/**
	 * This method returns a list of all the initiative entries. If you want it
	 * sorted so that the first element is the current one use
	 * {@link getOrderedEntries}
	 * 
	 * @return the list containing all the initiative entries
	 */
	public List<InitiativeEntry> getEntries() {
		List<InitiativeEntry> tokens = new ArrayList<InitiativeEntry>(list.getTokens().size());
		for (TokenInitiative ti : list.getTokens()) {
			if (InitiativeListModel.isTokenVisible(ti.getToken(), hideNPCs))
				tokens.add(new InitiativeEntry(ti));
		}
		return Collections.unmodifiableList(tokens);
	}

	/**
	 * This method returns a list that contains all initiative entries. NPCs are
	 * missing if this view was created with hidden NPCs. The iterator iterates
	 * over the elements in the same order shown in the initiative window but
	 * starts with the current or next token.
	 * 
	 * @return the list containing all the initiative entries
	 */
	public List<InitiativeEntry> getOrderedEntries() {
		LinkedList<TokenInitiative> sorting = new LinkedList<TokenInitiative>(list.getTokens());

		// change order of list
		if (list.getCurrent() > 0) {
			List<TokenInitiative> sub = sorting.subList(0, list.getCurrent());
			sorting.addAll(sub);
			sub.clear();
		}

		// transform into views
		List<InitiativeEntry> result = new ArrayList<InitiativeEntry>(sorting.size());
		for (TokenInitiative ti : sorting)
			if (InitiativeListModel.isTokenVisible(ti.getToken(), hideNPCs))
				result.add(new InitiativeEntry(ti));

		return Collections.unmodifiableList(result);
	}

	/**
	 * This adds all tokens in the current zone to the initiative.
	 * 
	 * @return the number of added tokens
	 */

	public int addAllTokens() {
		return addAllTokens(false);
	}

	/**
	 * This adds all tokens in the current zone to the initiative.
	 * 
	 * @param allowDuplicates
	 *            if a token should be added if it is already in the list
	 * @return the number of added tokens
	 */

	public int addAllTokens(boolean allowDuplicates) {
		return addAllTokens(null, allowDuplicates);
	}

	/**
	 * This adds all PC tokens in the current zone to the initiative.
	 * 
	 * @return the number of added tokens
	 */
	public int addAllPCs() {
		return addAllPCs(false);
	}

	/**
	 * This adds all PC tokens in the current zone to the initiative.
	 * 
	 * @param allowDuplicates
	 *            if a token should be added if it is already in the list
	 * @return the number of added tokens
	 */

	public int addAllPCs(boolean allowDuplicates) {
		return addAllTokens(Type.PC, allowDuplicates);
	}

	/**
	 * This adds all NPC tokens in the current zone to the initiative.
	 * 
	 * @return the number of added tokens
	 */
	public int addAllNPCs() {
		return addAllNPCs(false);
	}

	/**
	 * This adds all NPC tokens in the current zone to the initiative.
	 * 
	 * @param allowDuplicates
	 *            if a token should be added if it is already in the list
	 * @return the number of added tokens
	 */

	public int addAllNPCs(boolean allowDuplicates) {
		return addAllTokens(Type.NPC, allowDuplicates);
	}

	private int addAllTokens(Type addedType, boolean allowDuplicates) {
		if (allowDuplicates) {
			List<Token> l = list.getZone().getTokens();
			list.insertTokens(l);
			return l.size();
		} else {
			LinkedList<Token> tokens = new LinkedList<Token>();
			for (Token token : list.getZone().getTokens())
				if ((addedType == null || token.getType() == Type.NPC) && !list.contains(token))
					tokens.add(token);
			list.insertTokens(tokens);
			return tokens.size();
		}
	}

	/**
	 * returns the current initiative entry containing the token, initiative and
	 * if it is holding
	 * 
	 * @return The current initiative entry or null if NPCs are hidden and it is
	 *         the turn of an NPC
	 */
	public InitiativeEntry getCurrent() {
		TokenInitiative ti = list.getTokenInitiative(list.getCurrent());
		if (InitiativeListModel.isTokenVisible(ti.getToken(), hideNPCs))
			return new InitiativeEntry(ti);
		else
			return null;
	}

	/**
	 * Set the initiative round.
	 * 
	 * @param value
	 *            New value for the round.
	 */
	public void setRound(int round) {
		list.setRound(round);
	}

	/**
	 * This will set the initiative to the initiative of the next token
	 * 
	 * @return the new initiative
	 */
	public InitiativeEntry next() {
		list.nextInitiative();
		return this.getCurrent();
	}

	/**
	 * @return the number of initiative entries on this list
	 */
	public int size() {
		return list.getSize();
	}

	/**
	 * This will sort the initiative list.
	 */
	public void sortInitiative() {
		list.sort();
	}

	/**
	 * This will clear the initiative list.
	 * @return the number of removed entries.
	 */
	public int clear() {
		int size = list.getSize();
		list.clearModel();
		return size;
	}

	/**
	 * This will remove all PCs from the initiative list.
	 * @return the number of removed entries
	 */
	public int removeAllPCsFromInitiative() {
		list.startUnitOfWork();
		int count = 0;
		for (int i = list.getSize() - 1; i >= 0; i--) {
			Token token = list.getTokenInitiative(i).getToken();
			if (token.getType() == Type.PC) {
				list.removeToken(i);
				count++;
			}
		}
		list.setRound(-1);
		list.setCurrent(-1);
		list.finishUnitOfWork();
		return count;
	}

	/**
	 * This will remove all NPCs from the initiative list.
	 * @return the number of removed entries
	 */
	public int removeAllNPCsFromInitiative() {
		list.startUnitOfWork();
		int count = 0;
		for (int i = list.getSize() - 1; i >= 0; i--) {
			Token token = list.getTokenInitiative(i).getToken();
			if (token.getType() == Type.NPC) {
				list.removeToken(i);
				count++;
			}
		}
		list.setRound(-1);
		list.setCurrent(-1);
		list.finishUnitOfWork();
		return count;
	}

	public static class InitiativeEntry {
		private TokenInitiative ti;

		private InitiativeEntry(TokenInitiative ti) {
			this.ti = ti;
		}

		/**
		 * @return if this entry is set to holding
		 */
		public boolean isHolding() {
			return ti.isHolding();
		}
		
		/**
		 * Sets if this entry is holding.
		 */
		public void setHolding(boolean holding) {
			ti.setHolding(holding);
		}

		/**
		 * @return the initiative value of this token as a string.
		 */
		public String getInitiative() {
			return ti.getState();
		}
		
		/**
		 * sets the initiative value of this entry
		 * @param ini the new initiative of this entry
		 */
		public void setInitiative(double ini) {
			ti.setState(Double.toString(ini));
		}
		
		/**
		 * sets the initiative value of this entry
		 * @param ini the new initiative of this entry
		 */
		public void setInitiative(int ini) {
			ti.setState(Integer.toString(ini));
		}
		
		/**
		 * sets the initiative value of this entry
		 * @param ini the new initiative of this entry
		 */
		public void setInitiative(String ini) {
			ti.setState(ini);
		}

		/**
		 * @return the token of this entry
		 */
		public TokenView getToken() {
			return new TokenView(ti.getToken());
		}
	}
}
