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
package com.t3.macro.api.views;

import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.t3.client.TabletopTool;
import com.t3.client.ui.zone.FogUtil;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.macro.api.functions.token.TokenLocation;
import com.t3.model.CellPoint;
import com.t3.model.GUID;
import com.t3.model.Token;
import com.t3.model.Token.Type;
import com.t3.model.Zone;
import com.t3.model.Zone.TokenFilter;
import com.t3.model.ZonePoint;
import com.t3.model.grid.Grid;

//TODO rework the way VBL is manipulated
public class MapView {

	private final ZoneRenderer zr;

	public MapView(ZoneRenderer zr) {
		this.zr=zr;
	}
	
	/**
	 * @return the name of the map
	 */
	public String getName() {
		return zr.getZone().getName();
	}

	/**
	 * This method will make this map the current map on your T³
	 */
	public void makeCurrentMap() {
		TabletopTool.getFrame().setCurrentZoneRenderer(zr);
	}
	
	/**
	 * This method will expose all fog currently visible for the PCs
	 */
	public void exposePCAreaFog() {
		FogUtil.exposePCArea(zr);
	}

	/**
	 * This method exposes all fog currently visible for the given tokens. It will
	 * only expose the visible areas of the tokens you own.
	 * @param tokens the tokens
	 */
	public void exposeVisibleAreaOfOwnedTokens(List<TokenView> tokens) {
		List<Token> owned=new ArrayList<Token>(tokens.size());
		for(TokenView t:tokens)
			if(t.isOwner(TabletopTool.getPlayer().getName()))
				owned.add(t.token);
		FogUtil.exposeVisibleArea(zr, owned);
	}
	
	/**
	 * This moves your camera to the point
	 * @param x the x part of the coordinate
	 * @param x the x part of the coordinate
	 */
	public void goTo(int x, int y) {
		goTo(x,y,true);
	}
	
	/**
	 * This moves your camera to the point
	 * @param x the x part of the coordinate
	 * @param x the x part of the coordinate
	 * @param gridUnit if the given coordinates are in grid or zone units
	 */
	public void goTo(int x, int y, boolean gridUnit) {
		if(gridUnit)
			zr.centerOn(new CellPoint(x, y));
		else
			zr.centerOn(new ZonePoint(x, y));
	}
	
	/**
	 * This moves your camera to the given token
	 * @param token the token you want to focus on
	 */
	public void goTo(TokenView token) {
		TokenLocation tl=token.getLocation(false);
		zr.centerOn(new ZonePoint(tl.getX(), tl.getY()));
	}
	
	/**
	 * An convenience method. This will return the initiative list of the current map. 
	 * @return the initiative list of the current map
	 */
	public InitiativeListView getInitiativeList() {
		return new InitiativeListView(zr.getZone().getInitiativeList());
	}
	
	public void drawVBL(Shape shape) {
		drawVBL(false,shape,true,2,null);
	}
	
	public void drawVBL(Shape shape, boolean fill) {
		drawVBL(false,shape,fill,2,null);
	}
	
	public void drawVBL(Shape shape, int thickness) {
		drawVBL(false,shape,true,thickness,null);
	}
	
	public void drawVBL(Shape shape, boolean fill, int thickness) {
		drawVBL(false,shape,fill,thickness,null);
	}
	
	public void drawVBL(Shape shape, AffineTransform transform) {
		drawVBL(false,shape,true,2,transform);
	}
	
	public void drawVBL(Shape shape, boolean fill, AffineTransform transform) {
		drawVBL(false,shape,fill,2,transform);
	}
	
	public void drawVBL(Shape shape, int thickness, AffineTransform transform) {
		drawVBL(false,shape,true,thickness,transform);
	}
	
	public void drawVBL(Shape shape, boolean fill, int thickness, AffineTransform transform) {
		drawVBL(false,shape,fill,thickness,transform);
	}
	
	public void eraseVBL(Shape shape) {
		drawVBL(true,shape,true,2,null);
	}
	
	public void eraseVBL(Shape shape, boolean fill) {
		drawVBL(true,shape,fill,2,null);
	}
	
	public void eraseVBL(Shape shape, int thickness) {
		drawVBL(true,shape,true,thickness,null);
	}
	
	public void eraseVBL(Shape shape, boolean fill, int thickness) {
		drawVBL(true,shape,fill,thickness,null);
	}
	
	public void eraseVBL(Shape shape, AffineTransform transform) {
		drawVBL(true,shape,true,2,transform);
	}
	
	public void eraseVBL(Shape shape, boolean fill, AffineTransform transform) {
		drawVBL(true,shape,fill,2,transform);
	}
	
	public void eraseVBL(Shape shape, int thickness, AffineTransform transform) {
		drawVBL(true,shape,true,thickness,transform);
	}
	
	public void eraseVBL(Shape shape, boolean fill, int thickness, AffineTransform transform) {
		drawVBL(true,shape,fill,thickness,transform);
	}
	
	private void drawVBL(boolean erase, Shape shape, boolean fill, float thickness, AffineTransform transform) {
		BasicStroke stroke = new BasicStroke(thickness);
		
		Area area;
		if(fill)
			area = new Area(shape);
		else
			area = new Area(stroke.createStrokedShape(shape));
		
		if (transform!=null && !transform.isIdentity())
			area.transform(transform);

		// Send to the engine to render
		if (erase) {
			zr.getZone().removeTopology(area);
			TabletopTool.serverCommand().removeTopology(zr.getZone().getId(), area);
		} else {
			zr.getZone().addTopology(area);
			TabletopTool.serverCommand().addTopology(zr.getZone().getId(), area);
		}
		zr.repaint();
	}

	/**
	 * This returns a token from this map given its name, gm name or GUID
	 * @param identifier the name, gm name or GUID of the token you want
	 * @return the token or null if it was not found
	 */
	public TokenView findToken(String identifier) {
		Token t=zr.getZone().resolveToken(identifier);
		if(t==null)
			return null;
		else
			return new TokenView(t);
	}
	
	/**
	 * @return a list of all the tokens on this map
	 */
	public List<TokenView> getTokens() {
		return TokenView.makeTokenViewList(zr.getZone().getTokens());
	}
	
	/**
	 * @return a list of all the selected tokens on this map
	 */
	public List<TokenView> getSelectedTokens() {
		return TokenView.makeTokenViewList(zr.getSelectedTokensList());
	}
	
	/**
	 * @return a list of all the tokens on this map currently visible
	 */
	public List<TokenView> getExposedTokens() {
		final Zone zone = zr.getZone();
		return TokenView.makeTokenViewList(zone.getTokensFiltered(new TokenFilter() {
			@Override
			public boolean filter(Token t) {
				return zone.isTokenVisible(t);
			}
		}));
	}
	
	/**
	 * @return a list of all the tokens on this map that are PCs
	 */
	public List<TokenView> getPCTokens() {
		return TokenView.makeTokenViewList(zr.getZone().getTokensFiltered(new TokenFilter() {
			@Override
			public boolean filter(Token t) {
				return t.getType()==Type.PC;
			}
		}));
	}
	
	/**
	 * @return a list of all the tokens on this map that are NPCs
	 */
	public List<TokenView> getNPCTokens() {
		return TokenView.makeTokenViewList(zr.getZone().getTokensFiltered(new TokenFilter() {
			@Override
			public boolean filter(Token t) {
				return t.getType() == Token.Type.NPC;
			}
		}));
		
	}
	
	/**
	 * @param state the state you want to filter for
	 * @return a list of all the tokens on this map that have the gien state
	 */
	public List<TokenView> getTokensWithState(final String state) {
		return TokenView.makeTokenViewList(zr.getZone().getTokensFiltered(new TokenFilter() {
			@Override
			public boolean filter(Token t) {
				return t.hasState(state);
			}
		}));
	}
	
	/**
	 * This method returns a list of all tokens that are owned by the given player on this map.
	 * @param player the given player
	 * @return a list of the found tokens
	 */
	public List<TokenView> getOwnedTokens(final String player) {
		return TokenView.makeTokenViewList(zr.getZone().getTokensFiltered(new TokenFilter() {
			@Override
			public boolean filter(Token t) {
				return t.isOwner(player);
			}
		}));
	}

	/**
	 * This method returns a list of all tokens that are visible.
	 * @return a list of the found tokens
	 */
	public ArrayList<TokenView> getVisibleTokens() {
		Zone zone=zr.getZone();
		Set<GUID> visibleSet=zr.getVisibleTokenSet();
		ArrayList<TokenView> tokenList=new ArrayList<TokenView>(visibleSet.size());
		for (GUID id : visibleSet)
			tokenList.add(new TokenView(zone.getToken(id)));
		return tokenList;
	}
	
	/**
	 * This method will select the given tokens. 
	 * @param tokens the tokens that should be selected
	 * @param cumulative if the given tokens should be added to the already selected ones
	 */
	public void selectTokens(Collection<TokenView> tokens, boolean cumulative) {
		List<GUID> guids=new ArrayList<GUID>(tokens.size());
		for(TokenView t:tokens)
			guids.add(t.getId());
		if(!cumulative)
			zr.clearSelectedTokens();
		zr.selectTokens(guids);
	}
	
	/**
	 * This method will deselect all tokens on the map.
	 */
	public void deselectAllTokens() {
		zr.clearSelectedTokens();
	}
	
	/**
	 * This method will deselect the given tokens.
	 * @param tokens the tokens you want to deselect
	 */
	public void deselectTokens(Collection<TokenView> tokens) {
		for(TokenView t:tokens)
			zr.deselectToken(t.getId());
	}
	
	/**
	 * This method will move the given token from this map to the given map
	 * @param token the token you want to move to another map
	 * @param targetMap the map you want to move the token to
	 * @param targetX the target location x coordinate
	 * @param targetY the target location y coordinate
	 */
	public void moveTokenToMap(TokenView token, MapView targetMap, int targetX, int targetY) {
		Zone targetZone=targetMap.zr.getZone();
		Token t=token.token;

		Grid grid = targetZone.getGrid();

		ZonePoint zp = grid.convert(new CellPoint(targetX, targetY));
		targetX = zp.x;
		targetY = zp.y;

		t.setX(targetX);
		t.setY(targetY);
		t.setZOrder(targetZone.getLargestZOrder()+1);
		targetZone.putToken(t);
		TabletopTool.serverCommand().putToken(targetZone.getId(), t);
		TabletopTool.serverCommand().removeToken(zr.getZone().getId(), token.getId());
		TabletopTool.getFrame().getCurrentZoneRenderer().flushLight();
		TabletopTool.getFrame().refresh();
	}
	
	/**
	 * This method will move the given tokens from this map to the given map
	 * @param tokens the tokens you want to move to another map
	 * @param targetMap the map you want to move the token to
	 * @param targetX the target location x coordinate
	 * @param targetY the target location y coordinate
	 */
	public void moveTokensToMap(List<TokenView> tokens, MapView targetMap, int targetX, int targetY) {
		Zone targetZone=targetMap.zr.getZone();
		int count=0;
		for(TokenView token:tokens) {
			Token t=token.token;
			if(t!=null) {
				Grid grid = targetZone.getGrid();
		
				ZonePoint zp = grid.convert(new CellPoint(targetX+count, targetY));
				targetX = zp.x;
				targetY = zp.y;
		
				t.setX(targetX);
				t.setY(targetY);
				t.setZOrder(targetZone.getLargestZOrder()+1);
				targetZone.putToken(t);
				
				TabletopTool.serverCommand().putToken(targetZone.getId(), t);
				TabletopTool.serverCommand().removeToken(zr.getZone().getId(), token.getId());
				count++;
			}
		}
		
		//if at least one could be moved
		if(count>0) {
			TabletopTool.getFrame().getCurrentZoneRenderer().flushLight();
			TabletopTool.getFrame().refresh();
		}
	}
}
