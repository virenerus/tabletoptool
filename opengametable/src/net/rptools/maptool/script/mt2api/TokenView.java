package net.rptools.maptool.script.mt2api;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import net.rptools.lib.MD5Key;
import net.rptools.maptool.client.AppUtil;
import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.client.MapToolUtil;
import net.rptools.maptool.client.ui.token.BooleanTokenOverlay;
import net.rptools.maptool.client.ui.token.ImageTokenOverlay;
import net.rptools.maptool.client.ui.zone.ZoneRenderer;
import net.rptools.maptool.client.walker.WalkerMetric;
import net.rptools.maptool.client.walker.ZoneWalker;
import net.rptools.maptool.client.walker.astar.AStarSquareEuclideanWalker;
import net.rptools.maptool.language.I18N;
import net.rptools.maptool.model.AbstractPoint;
import net.rptools.maptool.model.CellPoint;
import net.rptools.maptool.model.Direction;
import net.rptools.maptool.model.GUID;
import net.rptools.maptool.model.InitiativeList;
import net.rptools.maptool.model.InitiativeList.TokenInitiative;
import net.rptools.maptool.model.LightSource;
import net.rptools.maptool.model.MacroButtonProperties;
import net.rptools.maptool.model.Path;
import net.rptools.maptool.model.Token;
import net.rptools.maptool.model.Token.Type;
import net.rptools.maptool.model.TokenFootprint;
import net.rptools.maptool.model.Zone;
import net.rptools.maptool.model.Zone.Layer;
import net.rptools.maptool.model.ZonePoint;
import net.rptools.maptool.model.campaign.TokenProperty;
import net.rptools.maptool.model.grid.Grid;
import net.rptools.maptool.model.grid.SquareGrid;
import net.rptools.maptool.script.MT2ScriptException;
import net.rptools.maptool.script.mt2api.functions.token.TokenLocation;
import net.rptools.maptool.script.mt2api.functions.token.TokenPart;
import net.rptools.maptool.util.ImageManager;
import net.rptools.maptool.util.TokenUtil;
import net.rptools.maptool.util.TypeUtil;
import net.rptools.maptool.util.math.IntPoint;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.syntax.ParserException;

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
		InitiativeList list = token.getZone().getInitiativeList();
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
	
	public int removeFromInitiative() {
		InitiativeList list = token.getZone().getInitiativeList();
		List<Integer> tokens = list.indexOf(token);
		list.startUnitOfWork();
	    for (int i = tokens.size() - 1; i >= 0; i--) list.removeToken(tokens.get(i).intValue());
	    	list.finishUnitOfWork();
	    return tokens.size();
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
		ZoneRenderer renderer = MapTool.getFrame().getZoneRenderer(token.getZone());
		MapTool.serverCommand().putToken(renderer.getZone().getId(), token);
	}
	
	/**
	 * Calculates if a certain point on the map is visible for this Token.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isVisible(int x, int y) {
		Area visArea = MapTool.getFrame().getZoneRenderer(token.getZone()).getZoneView().getVisibleArea(token);
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
        Zone zone = token.getZone();
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
        Zone zone = token.getZone();
        List<Integer> list = zone.getInitiativeList().indexOf(token);
        if (list.isEmpty()) return Collections.emptyList();
        List<String> ret = new ArrayList<String>(list.size());
        for (Integer index : list)
            ret.add(zone.getInitiativeList().getTokenInitiative(index.intValue()).getState());
        return ret;
    }
    
    public void setTokenInitiative(String state) {
    	InitiativeList list=token.getZone().getInitiativeList();
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
		MapTool.serverCommand().putToken(token.getZone().getId(), token);
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
		
		ZoneRenderer zr=MapTool.getFrame().getZoneRenderer(token.getZone());
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
		MapTool.getFrame().getZoneRenderer(token.getZone()).flushLight();
	}
	
	public String getSightType() {
		return token.getSightType();
	}
	
	public void setSightType(String sightType) {
		token.setSightType(sightType);
		this.sendUpdateToServer();
		MapTool.getFrame().getZoneRenderer(token.getZone()).flushLight();
	}
	
	public void setLabel(String label) {
		token.setLabel(label);
		token.getZone().putToken(token);
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
		Zone zone = token.getZone();
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
		Zone zone = token.getZone();
		zone.putToken(token);
		MapTool.serverCommand().putToken(zone.getId(), token);
	}
	
	public boolean getInitiativeHold() {
		Zone zone = token.getZone();
        List<Integer> list = zone.getInitiativeList().indexOf(token);
        if (list.isEmpty())
        	throw new IllegalArgumentException("The accessed token is not on the current map"); 
        return zone.getInitiativeList().getTokenInitiative(list.get(0).intValue()).isHolding(); 
	}
	
	public void setInitiativeHold(boolean value) {
		Zone zone = token.getZone();
        for(TokenInitiative ti : zone.getInitiativeList().getTokens())
        	if(ti.getToken().equals(token))
        		ti.setHolding(value);
	}
	
	public boolean hasLightSource() {
		return token.hasLightSources();
	}
	
	public boolean hasLightSource(String category) {

			for (LightSource ls : MapTool.getCampaign().getLightSourcesMap().get(category).values()) {
				if (token.hasLightSource(ls))
					return true;
			}
			return false;
	}
	
	public boolean hasLightSource(String category, String name) {
		for (LightSource ls : MapTool.getCampaign().getLightSourcesMap().get(category).values()) {
			if (ls.getName().equals(name)) {
				if (token.hasLightSource(ls)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void clearLightSources() {
		token.clearLightSources();
		this.sendUpdateToServer();
		MapTool.getFrame().updateTokenTree();
		MapTool.getFrame().getZoneRenderer(token.getZone()).flushLight();
	}
	
	/**
	 * Sets the light value for a token.
	 * @param category the category of the light source.
	 * @param name  The name of the light source.
	 * @param active The value to set for the light source, false for off or true for on.
	 * @return false if the light was not found, otherwise true;
	 */
	public boolean setLight(String category, String name, boolean active) {
		boolean found = false;

		for (LightSource ls : MapTool.getCampaign().getLightSourcesMap().get(category).values()) {
			if (ls.getName().equals(name)) {
				found = true;
				if (active) {
					token.removeLightSource(ls);
				} else {
					token.addLightSource(ls, Direction.CENTER);
				}
				break;
			}
		}
		ZoneRenderer renderer = MapTool.getFrame().getZoneRenderer(token.getZone());
		Zone zone = renderer.getZone();
		zone.putToken(token);
		MapTool.serverCommand().putToken(zone.getId(), token);
		MapTool.getFrame().updateTokenTree();
		renderer.flushLight();

		return found;
	}
	
	/**
	 * Gets the names of the light sources that are on.
	 * @return a string list containing the lights that are on.
	 */
	public List<String> getLights(Token token) {
		ArrayList<String> lightList = new ArrayList<String>();
		for (Map<GUID, LightSource> category : MapTool.getCampaign().getLightSourcesMap().values()) {
			for (LightSource ls : category.values()) {
				if (token.hasLightSource(ls))
					lightList.add(ls.getName());
			}
		}
		return lightList;
	}
	
	/**
	 * Gets the names of the light sources that are on.
	 * @param category The category to get the light sources for
	 * @return a string list containing the lights that are on.
	 */
	public List<String> getLights(Token token, String category) {
		ArrayList<String> lightList = new ArrayList<String>();
		for (LightSource ls : MapTool.getCampaign().getLightSourcesMap().get(category).values()) {
			if (token.hasLightSource(ls)) {
				lightList.add(ls.getName());
			}
		}
		return lightList;
	}
	
	public TokenLocation getLocation() {
		return getLocation(true);
	}

	public TokenLocation getLocation(boolean useDistancePerCell) {
		if (useDistancePerCell) {
			Rectangle tokenBounds = token.getBounds(token.getZone());
			return new TokenLocation(tokenBounds.x, tokenBounds.y, token.getZOrder());
		} else {
			Zone zone = token.getZone();
			CellPoint cellPoint = zone.getGrid().convert(new ZonePoint(token.getX(), token.getY()));
			return new TokenLocation(cellPoint.x, cellPoint.y, token.getZOrder());
		}
	}
	
	public int getDrawOrder() {
		return token.getZOrder();
	}
	
	public void setDrawOrder(int newOrder) {
		token.setZOrder(newOrder);
		ZoneRenderer renderer = MapTool.getFrame().getZoneRenderer(token.getZone());
		Zone zone = renderer.getZone();
		zone.putToken(token);
		MapTool.serverCommand().putToken(zone.getId(), token);
		renderer.flushLight();
	}
	
	public double getDistance(TokenView target) {
		return getDistance(target,true,null);
	}
	
	public double getDistance(TokenView target, String metric) {
		return getDistance(target,true,metric);
	}
	
	public double getDistance(TokenView target, boolean gridUnits) {
		return getDistance(target,gridUnits,null);
	}
	
	/**
	 * Gets the distance between two tokens.
	 * 
	 * @param target.token
	 * @param gridUnits
	 * @return the distance between this token and target
	 * @throws ParserException
	 */
	public double getDistance(TokenView target, boolean gridUnits, String metric) {
		ZoneRenderer renderer = MapTool.getFrame().getZoneRenderer(token.getZone());
		Grid grid = renderer.getZone().getGrid();

		if (grid.getCapabilities().isPathingSupported() && !"NO_GRID".equals(metric)) {

			// Get which cells our tokens occupy
			Set<CellPoint> sourceCells = token.getFootprint(grid).getOccupiedCells(grid.convert(new ZonePoint(token.getX(), token.getY())));
			Set<CellPoint> targetCells = target.token.getFootprint(grid).getOccupiedCells(grid.convert(new ZonePoint(target.token.getX(), target.token.getY())));

			ZoneWalker walker;
			if (metric != null && grid instanceof SquareGrid) {
				try {
					WalkerMetric wmetric = WalkerMetric.valueOf(metric);
					walker = new AStarSquareEuclideanWalker(renderer.getZone(), wmetric);

				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(I18N.getText("macro.function.getDistance.invalidMetric", metric));
				}
			} else {
				walker = grid.createZoneWalker();
			}

			// Get the distances from each source to target cell and keep the minimum one
			int distance = Integer.MAX_VALUE;
			for (CellPoint scell : sourceCells) {
				for (CellPoint tcell : targetCells) {
					walker.setWaypoints(scell, tcell);
					distance = Math.min(distance, walker.getDistance());
				}
			}

			if (gridUnits) {
				return distance;
			} else {
				return (double)distance / renderer.getZone().getUnitsPerCell();
			}
		} else {

			double d = token.getFootprint(grid).getScale();
			double sourceCenterX = token.getX() + (d * grid.getSize()) / 2;
			double sourceCenterY = token.getY() + (d * grid.getSize()) / 2;
			d = target.token.getFootprint(grid).getScale();
			double targetCenterX = target.token.getX() + (d * grid.getSize()) / 2;
			double targetCenterY = target.token.getY() + (d * grid.getSize()) / 2;
			double a = sourceCenterX - targetCenterX;
			double b = sourceCenterY - targetCenterY;
			double h = Math.sqrt(a * a + b * b);
			h /= renderer.getZone().getGrid().getSize();
			if (gridUnits) {
				h *= renderer.getZone().getUnitsPerCell();
			}
			return h;
		}
	}
	
	public double getDistance(int x, int y) {
		return getDistance(x, y,true,null);
	}
	
	public double getDistance(int x, int y, String metric) {
		return getDistance(x,y,true,metric);
	}
	
	public double getDistance(int x, int y, boolean gridUnits) {
		return getDistance(x,y,gridUnits,null);
	}
	
	public double getDistance(int x, int y, boolean gridUnits, String metric) {
		ZoneRenderer renderer = MapTool.getFrame().getZoneRenderer(token.getZone());
		Grid grid = renderer.getZone().getGrid();

		if (grid.getCapabilities().isPathingSupported() && !"NO_GRID".equals(metric)) {

			// Get which cells our tokens occupy
			Set<CellPoint> sourceCells = token.getFootprint(grid).getOccupiedCells(grid.convert(new ZonePoint(token.getX(), token.getY())));
			CellPoint targetCell = grid.convert(new ZonePoint(x,y));

			ZoneWalker walker;
			if (metric != null && grid instanceof SquareGrid) {
				try {
					WalkerMetric wmetric = WalkerMetric.valueOf(metric);
					walker = new AStarSquareEuclideanWalker(renderer.getZone(), wmetric);

				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(I18N.getText("macro.function.getDistance.invalidMetric", metric));
				}
			} else {
				walker = grid.createZoneWalker();
			}

			// Get the distances from each source to target cell and keep the minimum one
			int distance = Integer.MAX_VALUE;
			for (CellPoint scell : sourceCells) {
				walker.setWaypoints(scell, targetCell);
				distance = Math.min(distance, walker.getDistance());
			}

			if (gridUnits) {
				return distance;
			} else {
				return (double)distance / renderer.getZone().getUnitsPerCell();
			}
		} else {

			double d = token.getFootprint(grid).getScale();
			double sourceCenterX = token.getX() + (d * grid.getSize()) / 2;
			double sourceCenterY = token.getY() + (d * grid.getSize()) / 2;
			double a = sourceCenterX - x;
			double b = sourceCenterY - y;
			double h = Math.sqrt(a * a + b * b);
			h /= renderer.getZone().getGrid().getSize();
			if (gridUnits) {
				h *= renderer.getZone().getUnitsPerCell();
			}
			return h;
		}
	}
		
	/**
	 * Moves a token to the specified x,y location instantly.
	 * @param x the x co-ordinate of the destination.
	 * @param y  the y co-ordinate of the destination.
	 * @param gridUnits  whether the (x,y) coordinates are in zone coordinates or point to a grid cell
	 */
	public void moveToken(int x, int y, boolean gridUnits) {
		Grid grid = token.getZone().getGrid();

		if (gridUnits) {
			CellPoint cp = new CellPoint(x, y);
			ZonePoint zp = grid.convert(cp);
			token.setX(zp.x);
			token.setY(zp.y);
		} else {
			ZonePoint zp = new ZonePoint(x, y);
			token.setX(zp.x);
			token.setY(zp.y);
		}
		
		ZoneRenderer renderer = MapTool.getFrame().getZoneRenderer(token.getZone());
		Zone zone = renderer.getZone();
		zone.putToken(token);
		MapTool.serverCommand().putToken(zone.getId(), token);
		renderer.flushLight();
	}
	
	public String getStateImage(String stateName, int size) throws MT2ScriptException {
		return getStateImage(stateName)+"-"+Math.max(Math.min(size, 500),1);
	}
	
	public String getStateImage(String stateName) throws MT2ScriptException {
		BooleanTokenOverlay over = MapTool.getCampaign().getTokenStatesMap().get(stateName);
		if (over == null) {
			throw new MT2ScriptException(I18N.getText("macro.function.stateImage.unknownState", "getStateImage()", stateName ));
		}
		if (over instanceof ImageTokenOverlay) {
			StringBuilder assetId = new StringBuilder("asset://");
			assetId.append(((ImageTokenOverlay)over).getAssetId().toString());
			return assetId.toString();
		} else {
			throw new MT2ScriptException(I18N.getText("macro.function.stateImage.notImage", "getStateImage()", stateName));
		}
	}
	
	public List<IntPoint> getLastPath(boolean gridUnits) {
		Path<? extends AbstractPoint> path = token.getLastPath();
		List<IntPoint> points = new ArrayList<IntPoint>();
		if (path != null) {
			Zone zone = token.getZone();
			AbstractPoint zp = null;
			for (AbstractPoint pathCells : path.getCellPath()) {
				if (pathCells instanceof CellPoint) {
					CellPoint cp = (CellPoint) pathCells;
					if (gridUnits)
						zp = cp;
					else
						zp = zone.getGrid().convert((CellPoint) pathCells);
				} else {
					zp = (ZonePoint) pathCells;
					if(gridUnits)
						zp=zone.getGrid().convert((ZonePoint)zp);
				}
				if (zp != null) {
					points.add(new IntPoint(zp.x,zp.y));
				}
			}
		}
		return points;
	}

	public boolean isSnapToGrid() {
		return token.isSnapToGrid();
	}
	
	/**
	 * Returns the Rectangle the token would fill if it stould at the given coordinates
	 * @param zone the zone where to calculate where the token stands
	 * @param x the x coodinate (gridless)
	 * @param y the y coodinate (gridless)
	 * @return the bounding rectangle
	 */
	public Rectangle getBounds(int x, int y) {
		Zone zone=token.getZone();
		if (token.isSnapToGrid()) {
			return token.getFootprint(zone.getGrid()).getBounds(zone.getGrid(), zone.getGrid().convert(new ZonePoint(x,y)));
		} else {
			return token.getBounds(zone);
		}
	}

	public Rectangle getBounds() {
		return getBounds(token.getX(),token.getY());
	}
	
	public Set<String> getPropertyNames() {
		return Collections.unmodifiableSet(token.getPropertyNames());
	}
	
	public boolean hasProperty(String name) {
		Object o=token.getProperty(name);
		return o!=null && StringUtils.isEmpty(o.toString());
	}
	
	public boolean isPC() {
		return token.getType()==Type.PC;
	}
	
	public boolean isNPC() {
		return token.getType()==Type.NPC;
	}
	
	public void setPC() {
		if(token.getType()!=Type.PC) {
			ZoneRenderer zr=MapTool.getFrame().getZoneRenderer(token.getZone());
			Zone zone=zr.getZone();
			token.setType(Token.Type.PC);
			MapTool.serverCommand().putToken(zone.getId(), token);
			zone.putToken(token);
			zr.flushLight();
			MapTool.getFrame().updateTokenTree();
		}
	}
	
	public void setNPC() {
		if(token.getType()!=Type.NPC) {
			ZoneRenderer zr=MapTool.getFrame().getZoneRenderer(token.getZone());
			Zone zone=zr.getZone();
			token.setType(Token.Type.NPC);
			MapTool.serverCommand().putToken(zone.getId(), token);
			zone.putToken(token);
			zr.flushLight();
			MapTool.getFrame().updateTokenTree();
		}
	}
	
	public String getLayer() {
		return token.getLayer().toString();
	}
	
	public void setLayer(String layer, boolean forceShape) {
		Layer l=Zone.Layer.valueOf(layer);
		ZoneRenderer zr=MapTool.getFrame().getZoneRenderer(token.getZone());
		Zone zone=zr.getZone();
		token.setLayer(l);
		if (forceShape) {
			switch (l) {
			case BACKGROUND:
			case OBJECT:
				token.setShape(Token.TokenShape.TOP_DOWN);
				break;
			case GM:
			case TOKEN:
				Image image = ImageManager.getImage(token.getImageAssetId());
				if (image == null || image == ImageManager.TRANSFERING_IMAGE) {
					token.setShape(Token.TokenShape.TOP_DOWN);
				} else {
					token.setShape(TokenUtil.guessTokenType(image));
				}
				break;
			}
		}
		MapTool.serverCommand().putToken(zone.getId(), token);
		zone.putToken(token);
		zr.flushLight();
		MapTool.getFrame().updateTokenTree();
	}
	
	/**
	 * Gets the size of the token.
	 * The sizes returned are:
     * <ul><li>Fine</li>
     *<li>Diminutive</li>
     *<li>Tiny</li>
     *<li>Small</li>
     *<li>Medium</li>
     *<li>Large</li>
     *<li>Huge</li>
     *<li>Gargantuan</li>
     *<li>Colossal</li></ul>
     *
	 * @return the size of the token.
	 */
	public String getSize() {
		Grid grid = token.getZone().getGrid();
		if (token.isSnapToScale()) {
			for (TokenFootprint footprint : grid.getFootprints()) {
				if (token.getFootprint(grid) == footprint) {
					return footprint.getName();
				}
			}
		}
		return "";
	}
	
	/**
	 * Sets the size of the token.
	 * 
	 * @param token
	 *            The token to set the size of.
	 * @param size
	 *            The size to set the token to.
	 * @return The new size of the token.
	 * @throws ParserException
	 *             if the size specified is an invalid size.
	 */
	public String setSize(String size) throws MT2ScriptException {
		if (size.equalsIgnoreCase("native") || size.equalsIgnoreCase("free")) {
			token.setSnapToScale(false);
			return this.getSize();
		}
		token.setSnapToScale(true);
		ZoneRenderer renderer = MapTool.getFrame().getZoneRenderer(token.getZone());
		Zone zone = renderer.getZone();
		Grid grid = zone.getGrid();
		for (TokenFootprint footprint : grid.getFootprints()) {
			if (footprint.getName().equalsIgnoreCase(size)) {
				token.setFootprint(grid, footprint);
				renderer.flush(token);
				renderer.repaint();
				MapTool.serverCommand().putToken(zone.getId(), token);
				zone.putToken(token);
				MapTool.getFrame().updateTokenTree();
				return this.getSize();
			}
		}
		throw new MT2ScriptException(I18N.getText("macro.function.tokenProperty.invalidSize", "setSize", size));
	}
	
	public Set<String> getOwners() {
		return Collections.unmodifiableSet(token.getOwners());
	}
	
	public boolean isOwnedByAll() {
		return token.isOwnedByAll();
	}
	
	public boolean isOwner(String player) {
		return token.isOwner(player);
	}
	
	public void resetProperty(String property) {
		Zone zone=token.getZone();
		token.resetProperty(property);
		MapTool.serverCommand().putToken(zone.getId(), token);
		zone.putToken(token);
	}
	
	
	public Object getProperty(String property) {
		return token.getProperty(property);
	}
	
	public void setProperty(String property, Object value) {
		Zone zone=token.getZone();
		token.setProperty(property, value);
		MapTool.serverCommand().putToken(zone.getId(), token);
		zone.putToken(token);
	}
	
	public Object getPropertyDefault(String property) {
		Object val = null;

		List<TokenProperty> propertyList = MapTool.getCampaign().getCampaignProperties().getTokenPropertyList(token.getPropertyType());
		if (propertyList != null) {
			for (TokenProperty tp : propertyList) {
				if (property.equalsIgnoreCase(tp.getName())) {
					val = tp.getDefaultValue();
					break;
				}
			}
		}
		if (val == null) {
			return null;
		}
		if (val instanceof String) {
			// try to convert to a number
			try {
				return new BigDecimal(val.toString()).intValue();
			} catch (Exception e) {
				return val;
			}
		} else {
			return val;
		}
	}
	
	public void sendToBack() {
		Zone zone=token.getZone();
		MapTool.serverCommand().sendTokensToBack(zone.getId(), Collections.singleton(token.getId()));
		zone.putToken(token);
	}
	
	public void bringToFront() {
		Zone zone=token.getZone();
		MapTool.serverCommand().bringTokensToFront(zone.getId(), Collections.singleton(token.getId()));
		zone.putToken(token);
	}
	
	public void setPropertyType(String type) {
		Zone zone=token.getZone();
		token.setPropertyType(type);
		MapTool.serverCommand().putToken(zone.getId(), token);
		zone.putToken(token); // FJE Should this be here?  Added because other places have it...?!
	}
	
	public String getPropertyType() {
		 return token.getPropertyType();
	}
	
	public Integer getFacing() {
		return token.getFacing();
	}
	
	public void setFacing(Integer direction) {
		ZoneRenderer renderer = MapTool.getFrame().getZoneRenderer(token.getZone());
		Zone zone = renderer.getZone();
		token.setFacing(direction);
		MapTool.serverCommand().putToken(zone.getId(), token);
		if(this.hasLightSource())
			renderer.flushLight();
		zone.putToken(token);
	}
	
	public void removeFacing() {
		this.setFacing(null);
	}
	
	public List<String> getMatchingProperties(String regex) {
		List<String> matching=new ArrayList<String>(); 
		Pattern p=Pattern.compile(regex);
		for(String str:token.getPropertyNames())
			if(p.matcher(str).matches())
				matching.add(str);
		return matching;
	}
	
	public void addOwner(String player) {
		Zone zone=token.getZone();
		token.addOwner(player);
		MapTool.serverCommand().putToken(zone.getId(), token);
		zone.putToken(token);
	}
	
	public void clearAllOwners() {
		token.clearAllOwners();
		Zone zone=token.getZone();
		MapTool.serverCommand().putToken(zone.getId(), token);
		zone.putToken(token);
	}
	
	public void removeOwner(String player) {
		Zone zone=token.getZone();
		token.removeOwner(player);
		MapTool.serverCommand().putToken(zone.getId(), token);
		zone.putToken(token);
	}
	
	public int getWidth() {
		return token.getBounds(token.getZone()).width;
	}
	
	public int getHeight() {
		return token.getBounds(token.getZone()).height;
	}
	
	public String getTokenShape() {
		return token.getShape().toString();
	}
	
	public void setTokenShape(String shape) {
		Token.TokenShape newShape = Token.TokenShape.valueOf(shape);
		token.setShape(newShape);
		this.sendUpdateToServer();
	}
	
	public TokenView copyToken() {
		Zone zone = token.getZone();
		List<Token> allTokens = zone.getTokens();
		Token t = new Token(token);

		if (allTokens != null) {
			for (Token tok : allTokens) {
				GUID tea = tok.getExposedAreaGUID();
				if (tea != null && tea.equals(t.getExposedAreaGUID())) {
					t.setExposedAreaGUID(new GUID());
				}
			}
		}
		zone.putToken(t);

		MapTool.serverCommand().putToken(zone.getId(), t);
			
		MapTool.getFrame().getZoneRenderer(token.getZone()).flushLight();
		return new TokenView(t);
	}
	
	public List<TokenView> copyToken(int numberOfCopies) {
		Zone zone = token.getZone();
		List<TokenView> newTokens = new ArrayList<TokenView>(numberOfCopies);
		List<Token> allTokens = zone.getTokens();
		for (int i = 0; i < numberOfCopies; i++) {
			Token t = new Token(token);

			if (allTokens != null) {
				for (Token tok : allTokens) {
					GUID tea = tok.getExposedAreaGUID();
					if (tea != null && tea.equals(t.getExposedAreaGUID())) {
						t.setExposedAreaGUID(new GUID());
					}
				}
			}
			zone.putToken(t);

			MapTool.serverCommand().putToken(zone.getId(), t);
			newTokens.add(new TokenView(t));
		}
		MapTool.getFrame().getZoneRenderer(token.getZone()).flushLight();
		return newTokens;
	}
	
	public void removeToken() {
		Zone zone = token.getZone();
		MapTool.serverCommand().removeToken(zone.getId(), token.getId());
	}
	
	public void setTokenImage(String assetId) {
		Zone zone = token.getZone();
		token.setImageAsset(null, new MD5Key(assetId));
		zone.putToken(token);
		MapTool.serverCommand().putToken(zone.getId(), token);
	}
	
	public void setTokenPortrait(String assetId) {
		Zone zone = token.getZone();
		token.setPortraitImage(new MD5Key(assetId));
		zone.putToken(token);
		MapTool.serverCommand().putToken(zone.getId(), token);
	}
	
	public void setTokenHandout(String assetId) {
		Zone zone = token.getZone();
		token.setCharsheetImage(new MD5Key(assetId));
		zone.putToken(token);
		MapTool.serverCommand().putToken(zone.getId(), token);
	}
	
	public String getTokenImage() {
		return token.getImageAssetId().toString();
	}
	
	public String getTokenPortrait() {
		return token.getPortraitImage().toString();
	}
	
	public String getTokenHandout() {
		return token.getCharsheetImage().toString();
	}
	
	public boolean hasMacro(String macroName) {
		return token.getMacroNames(false).contains(macroName);
	}
	
	public MacroView getMacro(String macroName) {
		return new MacroView(token.getMacro(macroName,false));
	}
	
	public boolean removeMacro(String macroName) {
		MacroButtonProperties mbp=token.getMacro(macroName, false);
		if(mbp!=null) {
			token.deleteMacroButtonProperty(mbp);
			MapTool.serverCommand().putToken(token.getZone().getId(), token);
			return true;
		}
		return false;
	}
	
	public MacroView createMacro(String label) {
		MacroButtonProperties mbp = new MacroButtonProperties(token.getMacroNextIndex());
		mbp.setToken(token);
		MapTool.serverCommand().putToken(token.getZone().getId(), token);
		return new MacroView(mbp);
	}
	
	public void testInt() {
		token.setProperty("HP", 5);
	}
	
	public Object executeMacro(String name) {
		MacroButtonProperties mbp=token.getMacro(name, false);
		return mbp.executeMacro(token);
	}
}
