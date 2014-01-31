package net.rptools.maptool.script.mt2api.functions.ini;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.model.InitiativeList;
import net.rptools.maptool.model.InitiativeList.TokenInitiative;
import net.rptools.maptool.model.InitiativeListModel;
import net.rptools.maptool.model.Token;
import net.rptools.maptool.model.Token.Type;
import net.rptools.maptool.script.mt2api.TokenView;
import net.rptools.maptool.script.mt2api.functions.ini.InitiativeListView.InitiativeEntry;

public class InitiativeFunctions {
	/**
	 * This adds all tokens in the current zone to the initiative.
	 * @return the number of added tokens
	 */
	
	public int addAllToInitiative() {
		return addAllToInitiative(false);
	}
	
	/**
	 * This adds all tokens in the current zone to the initiative.
	 * @param allowDuplicates if a token should be added if it is already in the list
	 * @return the number of added tokens
	 */
	
	public int addAllToInitiative(boolean allowDuplicates) {
		// Check for duplicates flag
        InitiativeList list = MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList();
        
        if(allowDuplicates) {
        	List<Token> l=list.getZone().getTokens();
        	list.insertTokens(l);
        	return l.size();
        }
        else {
        	LinkedList<Token> tokens=new LinkedList<Token>();
	        for (Token token : list.getZone().getTokens())
	            if (!list.contains(token)) {
	                tokens.add(token);
	            } // endif
	        list.insertTokens(tokens);
	        return tokens.size();
        }
	}
	
	/**
	 * This adds all tokens in the current zone to the initiative.
	 * @return the number of added tokens
	 */
	
	public int addAllPCsToInitiative() {
		return addAllPCsToInitiative(false);
	}
	
	/**
	 * This adds all tokens in the current zone to the initiative.
	 * @param allowDuplicates if a token should be added if it is already in the list
	 * @return the number of added tokens
	 */
	
	public int addAllPCsToInitiative(boolean allowDuplicates) {
		// Check for duplicates flag
        InitiativeList list = MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList();
        
        if(allowDuplicates) {
        	List<Token> l=list.getZone().getTokens();
        	list.insertTokens(l);
        	return l.size();
        }
        else {
        	LinkedList<Token> tokens=new LinkedList<Token>();
	        for (Token token : list.getZone().getTokens())
	            if (!list.contains(token) && token.getType() == Type.PC) {
	                tokens.add(token);
	            } // endif
	        list.insertTokens(tokens);
	        return tokens.size();
        }
	}
	
	/**
	 * This adds all tokens in the current zone to the initiative.
	 * @return the number of added tokens
	 */
	
	public int addAllNPCsToInitiative() {
		return addAllNPCsToInitiative(false);
	}
	
	/**
	 * This adds all tokens in the current zone to the initiative.
	 * @param allowDuplicates if a token should be added if it is already in the list
	 * @return the number of added tokens
	 */
	
	public int addAllNPCsToInitiative(boolean allowDuplicates) {
		// Check for duplicates flag
        InitiativeList list = MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList();
        
        if(allowDuplicates) {
        	List<Token> l=list.getZone().getTokens();
        	list.insertTokens(l);
        	return l.size();
        }
        else {
        	LinkedList<Token> tokens=new LinkedList<Token>();
	        for (Token token : list.getZone().getTokens())
	            if (!list.contains(token) && token.getType() == Type.NPC) {
	                tokens.add(token);
	            } // endif
	        list.insertTokens(tokens);
	        return tokens.size();
        }
	}
	
	/**
	 * Get the token that has the current initiative;
	 * 
	 * @return The current initiative token
	 */
	
	public TokenView getInitiativeToken() {
        InitiativeList list = MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList();
        int index = list.getCurrent();
        return new TokenView(list.getToken(index));
	}

	/**
	 * Get the index of the currently active token
	 * 
	 * @return The index of the currently active token
	 */
	
	public int getCurrentInitiative() {
        InitiativeList list = MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList();
        return list.getCurrent();
	}

	/**
	 * Set the current initiative index.
	 * 
	 * @param value the new index for the round.
	 */
	
	public void setCurrentInitiative(int value) {
        InitiativeList list = MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList();
        list.setCurrent(value);
    }
	
	/**
	 * Get the initiative round;
	 * 
	 * @return The initiative round
	 */
	public int getInitiativeRound() {
        InitiativeList list = MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList();
        return list.getRound();
	}
	
	/**
	 * Set the initiative round.
	 * 
	 * @param value New value for the round.
	 */
	public void setInitiativeRound(int round) {
        InitiativeList list = MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList();
        list.setRound(round);
	}
	
	public static int nextInitiative() {
		InitiativeList list = MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList();
		list.nextInitiative();
		return list.getCurrent();
	}
	
	/**
	 * This returns a view on the initiative list with all the needed information.
	 * If NPCs are visible depends on the settings in the Init Panel.
	 * @return
	 */
	//TODO replace this with a real view that you can interact with
	public InitiativeListView getInitiativeList() {
		return getInitiativeList(MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList().isHideNPC());
	}
	
	public InitiativeListView getInitiativeList(boolean hideNPCs) {
		InitiativeList list = MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList();
		
		// Get the visible tokens only
		List<InitiativeEntry> tokens = new ArrayList<InitiativeEntry>(list.getTokens().size());
		int current = -1; // Assume that the current isn't visible
		for (TokenInitiative ti : list.getTokens()) {
			if (!InitiativeListModel.isTokenVisible(ti.getToken(), hideNPCs))
				continue;
			if (ti == list.getTokenInitiative(list.getCurrent()))
				current = tokens.size(); // Make sure the current number matches what the user can see
			tokens.add(new InitiativeEntry(ti.isHolding(), ti.getState(), new TokenView(ti.getToken())));
		}
		return new InitiativeListView(list.getRound(),list.getZone().getName(),current,tokens);
	}
	
	public int initiativeSize() {
		return MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList().getSize();
	}
	
	public void sortInitiative() {
		MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList().sort();
	}
	
	public int removeAllFromInitiative() {
		InitiativeList list = MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList();
		int size=list.getSize();
		list.clearModel();
		return size;
	}
	
	public int removeAllPCsFromInitiative() {
		InitiativeList list = MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList();
		list.startUnitOfWork();
		int count=0;
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
	
	public int removeAllNPCsFromInitiative() {
		InitiativeList list = MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList();
		list.startUnitOfWork();
		int count=0;
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
}
