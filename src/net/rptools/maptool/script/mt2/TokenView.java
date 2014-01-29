package net.rptools.maptool.script.mt2;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.rptools.maptool.client.AppUtil;
import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.client.MapToolUtil;
import net.rptools.maptool.client.ui.token.BooleanTokenOverlay;
import net.rptools.maptool.client.ui.zone.ZoneRenderer;
import net.rptools.maptool.language.I18N;
import net.rptools.maptool.model.GUID;
import net.rptools.maptool.model.Grid;
import net.rptools.maptool.model.InitiativeList;
import net.rptools.maptool.model.InitiativeList.TokenInitiative;
import net.rptools.maptool.model.Token;
import net.rptools.maptool.model.Zone;
import net.rptools.maptool.model.ZonePoint;
import net.rptools.maptool.script.mt2.functions.token.TokenPart;
import net.rptools.maptool.util.TypeUtil;

public class TokenView extends TokenPropertyView {
	public TokenView(Token token) {
		super(token);
	}
	
	@Override
	public int hashCode() {
		return token.hashCode();
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenView other = (TokenView) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return token.toString();
	}
	
	
	
	
	
	///////////////////////////////////////////
	// Implementation of available functions //
	///////////////////////////////////////////
	
	
	/**
	 * 
	 * @param barName the name of the bar you want the value of
	 * @return the value of the bar or null if the bar is invisible
	 */
	public Integer getBar(String barName) {
		BigDecimal i=(BigDecimal) token.getState(barName);
		if(i==null)
			return null;
		else
			return i.intValue();
	}
	
	/**
	 * 
	 * @param barName the name of the bar you want to set the value of
	 * @param value the value the bar should have between 0 and 100
	 */
	public void setBar(String barName, int value) {
		BigDecimal val = new BigDecimal(value);
		token.setState(barName, val);
		this.sendUpdateToServer();
	}
	
	/**
	 * @param barName the name of the bar
	 * @return if this bar is currently visible on this token
	 */
	public boolean isBarVisible(String barName) {
		return token.getState(barName) != null;
	}

	/**
	 * This allows you to show or hide a bar. This will reset the value of the bar!
	 * @param barName the name of the bar
	 * @param show if the bar should be visible or not
	 */
	public void setBarVisible(String barName, boolean show) {
		token.setState(barName, show ? BigDecimal.ONE : null);
		this.sendUpdateToServer();
	}
	
	
	public boolean addToInitiative() {
		return addToInitiative(false, null);
	}
	
	public boolean addToInitiative(boolean allowDuplicates) {
		return addToInitiative(allowDuplicates, null);
	}
	
	/**
	 * 
	 * @param allowDuplicates if this token should be added if it is already in the intiative list
	 * @param state an optional state that can be displayed with the token name (like a initiative value)
	 * @return if this token was actually added
	 */
	public boolean addToInitiative(boolean allowDuplicates, String state) {
		InitiativeList list = MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList();
	    // insert the token if needed
	    TokenInitiative ti = null;
	    if (allowDuplicates || !list.contains(token)) {
	        ti = list.insertToken(-1, token);
	        if (state != null) ti.setState(state);
	    } else {
	    	setTokenInitiative(state);
	        return false;
	    } // endif
	    return ti != null;
	}
	
	/**
	 * Gets the name of the token.
	 * @return the name of the token.
	 */
	public String getName() {
		return token.getName();
	}
	
	/**
	 * Sets the name of the token.
	 * @param name the name of the token.
	 */
	public void setName(String name) {
		token.setName(name);
		ZoneRenderer renderer = MapTool.getFrame().getCurrentZoneRenderer();
		MapTool.serverCommand().putToken(renderer.getZone().getId(), token);
	}
	
	/**
	 * Calculates if a certain point on the map is visible for this Token.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isVisible(int x, int y) {
		Area visArea = MapTool.getFrame().getCurrentZoneRenderer().getZoneView().getVisibleArea(token);
		if (visArea == null)
			return false;
		else
			return visArea.contains(x, y);
	}
	
	/**
     * Get the first token initiative
     * 
     * @param token Get it for this token
     * @return The first token initiative value for the passed token
     */
    public String getInitiative() {
        Zone zone = MapTool.getFrame().getCurrentZoneRenderer().getZone();
        List<Integer> list = zone.getInitiativeList().indexOf(token);
        if (list.isEmpty()) return null; 
        return zone.getInitiativeList().getTokenInitiative(list.get(0).intValue()).getState(); 
    }
    
    /**
     * Get the first token initiative
     * 
     * @param token Get it for this token
     * @return The first token initiative value for the passed token
     */
    public List<String> getInitiatives() {
        Zone zone = MapTool.getFrame().getCurrentZoneRenderer().getZone();
        List<Integer> list = zone.getInitiativeList().indexOf(token);
        if (list.isEmpty()) return Collections.emptyList();
        List<String> ret = new ArrayList<String>(list.size());
        for (Integer index : list)
            ret.add(zone.getInitiativeList().getTokenInitiative(index.intValue()).getState());
        return ret;
    }
    
    public void setTokenInitiative(String state) {
    	InitiativeList list=MapTool.getFrame().getCurrentZoneRenderer().getZone().getInitiativeList();
    	for(Integer index:list.indexOf(token)) {
	        TokenInitiative ti = list.getTokenInitiative(index);
	        if(ti!=null)
	        	ti.setState(state);
        }
    }
    
    /**
     * @return if this token is visible for the current player
     */
    public boolean isVisible() {
    	return !(token.isVisibleOnlyToOwner() && !AppUtil.playerOwns(token)) && token.isVisible();
    }
    
    public void setVisible(boolean visible) {
		token.setVisible(visible);
		this.sendUpdateToServer();
    }

    public boolean isVisibleToOwnerOnly() {
    	return token.isVisibleOnlyToOwner();
    }
    
    public void setVisibleToOwnerOnly(boolean ownerOnlyVisible) {
		token.setVisibleOnlyToOwner(ownerOnlyVisible);
		this.sendUpdateToServer();
	}
    
    public String getSpeech(String speechKey) {
    	return token.getSpeech(speechKey);
    }
    
    public void setSpeech(String speechKey, String speech) {
    	token.setSpeech(speechKey, speech);
    }
    
    public Set<String> getSpeechNames() {
    	return token.getSpeechNames();
    }
    
    public String getNotes() {
    	return token.getNotes();
    }
    
    public void setNotes(String notes) {
    	token.setNotes(notes);
    	this.sendUpdateToServer();
    }
    
    public String getGMNotes() {
    	return token.getGMNotes();
    }
    
    public void setGMNotes(String notes) {
    	token.setGMNotes(notes);
    	this.sendUpdateToServer();
    }
    
    public boolean isState(String state) {
    	return TypeUtil.getBooleanValue(token.getState(state));
    }
    
    public void setState(String state, boolean value) {
    	token.setState(state, value);
    	this.sendUpdateToServer();
    }
    
    public void setAllStates(boolean value) {
    	for (String stateName : MapTool.getCampaign().getTokenStatesMap().keySet())
			token.setState(stateName, value);
		this.sendUpdateToServer();
    }

    public Set<String> getStateName() {
    	return Collections.unmodifiableSet(MapTool.getCampaign().getTokenStatesMap().keySet());
    }
    
    public Set<String> getStateNames(String group) {
    	Set<String> stateNames;
		Map<String, BooleanTokenOverlay> states = MapTool.getCampaign().getTokenStatesMap();
		stateNames = new HashSet<String>();
		for (BooleanTokenOverlay bto : states.values()) {
			if (group.equals(bto.getGroup())) {
				stateNames.add(bto.getName());
			}
		}
		return stateNames;
    }
    
	private void sendUpdateToServer() {
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), token);
	}

	public GUID getId() {
		return token.getId();
	}
	
	/**
	 * Returns a set of the parts of a token that can be seen by this token.
	 * 
	 * @param t the token of which we want to check what this token can see
	 * @return the set of visible token parts
	 */
	public EnumSet<TokenPart> getVisibleTokenParts(TokenView target) {
		if(!token.getHasSight())
			return EnumSet.noneOf(TokenPart.class);
		
		ZoneRenderer zr=MapTool.getFrame().getCurrentZoneRenderer();
		Zone zone=zr.getZone();
		Area tokensVisibleArea = zr.getZoneView().getVisibleArea(token);
		if (tokensVisibleArea == null)
			return EnumSet.noneOf(TokenPart.class);
		if (target == null)
			throw new NullPointerException();
		if (!target.isVisible() || (target.token.isVisibleOnlyToOwner() && !AppUtil.playerOwns(target.token))) {
			return EnumSet.noneOf(TokenPart.class);
		}
		Grid grid = zone.getGrid();
		
		Rectangle bounds = target.token.getFootprint(grid).getBounds(grid, grid.convert(new ZonePoint(target.token.getX(), target.token.getY())));
		if(!target.token.isSnapToGrid())
			bounds = target.token.getBounds(zone);

		EnumSet<TokenPart> ret = EnumSet.noneOf(TokenPart.class);
		
		int x = (int) bounds.getX();
		int y = (int) bounds.getY();
		int w = (int) bounds.getWidth();
		int h = (int) bounds.getHeight();

		int halfX = x + (w) / 2;
		int halfY = y + (h) / 2;
		if (tokensVisibleArea.intersects(bounds)) {
			if (tokensVisibleArea.contains(new Point(x, y)))
				ret.add(TokenPart.TOP_LEFT);
			if (tokensVisibleArea.contains(new Point(x, y + h)))
			if (tokensVisibleArea.contains(new Point(x + w, y)))
				ret.add(TokenPart.TOP_RIGHT);
			if (tokensVisibleArea.contains(new Point(x + w, y + h)))
				ret.add(TokenPart.BOTTOM_LEFT);
			if (tokensVisibleArea.contains(new Point(halfX, halfY)))
				ret.add(TokenPart.BOTTOM_RIGHT);
		}
		return ret;
	}
	
	public boolean hasSight() {
		return token.getHasSight();
	}
	
	public void setHasSight(boolean value) {
		token.setHasSight(value);
		this.sendUpdateToServer();
		MapTool.getFrame().getCurrentZoneRenderer().flushLight();
	}
	
	public String getSightType() {
		return token.getSightType();
	}
	
	public void setSightType(String sightType) {
		token.setSightType(sightType);
		this.sendUpdateToServer();
		MapTool.getFrame().getCurrentZoneRenderer().flushLight();
	}
	
	public void setLabel(String label) {
		token.setLabel(label);
		MapTool.getFrame().getCurrentZoneRenderer().getZone().putToken(token);
		this.sendUpdateToServer();
	}
	
	public String getLabel() {
		return token.getLabel();
	}
	
	public String getGMName() {
		return token.getGMName();
	}
	
	public void setGMName(String name) {
		token.setGMName(name);
		Zone zone = MapTool.getFrame().getCurrentZoneRenderer().getZone();
		MapTool.serverCommand().putToken(zone.getId(), token);
		zone.putToken(token);
	}
	
	/**
	 * Gets the halo for the token.
	 * @return the halo color or null if there is no halo
	 */
	public String getHalo() {
		if (token.getHaloColor() != null)
			return "#" + Integer.toHexString(token.getHaloColor().getRGB()).substring(2);
		else
			return null;
	}

	/**
	 * Sets the halo color of the token.
	 * @param value the color to set in the form #AAAAAA or null to deactivate the halo
	 */
	public void setHalo(String hexColor) {
		if (hexColor == null) {
			token.setHaloColor(null);
		} else {
			Color color = MapToolUtil.getColor(hexColor);
			token.setHaloColor(color);
		}
		
		// TODO: This works for now but could result in a lot of resending of data
		Zone zone = MapTool.getFrame().getCurrentZoneRenderer().getZone();
		zone.putToken(token);
		MapTool.serverCommand().putToken(zone.getId(), token);
	}
	
	public boolean getInitiativeHold() {
		Zone zone = MapTool.getFrame().getCurrentZoneRenderer().getZone();
        List<Integer> list = zone.getInitiativeList().indexOf(token);
        if (list.isEmpty())
        	throw new IllegalArgumentException("The accessed token is not on the current map"); 
        return zone.getInitiativeList().getTokenInitiative(list.get(0).intValue()).isHolding(); 
	}
	
	public void setInitiativeHold(boolean value) {
		Zone zone = MapTool.getFrame().getCurrentZoneRenderer().getZone();
        for(TokenInitiative ti : zone.getInitiativeList().getTokens())
        	if(ti.getToken().equals(token))
        		ti.setHolding(value);
	}
}
