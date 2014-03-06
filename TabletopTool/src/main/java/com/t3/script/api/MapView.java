package com.t3.script.api;

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
import com.t3.model.CellPoint;
import com.t3.model.GUID;
import com.t3.model.Token;
import com.t3.model.Zone;
import com.t3.model.ZonePoint;
import com.t3.model.grid.Grid;
import com.t3.script.api.tokenfilter.AllFilter;
import com.t3.script.api.tokenfilter.ExposedFilter;
import com.t3.script.api.tokenfilter.NPCFilter;
import com.t3.script.api.tokenfilter.OwnedFilter;
import com.t3.script.api.tokenfilter.PCFilter;
import com.t3.script.api.tokenfilter.StateFilter;
import com.t3.util.TypeUtil;

public class MapView {

	private final ZoneRenderer zr;

	public MapView(ZoneRenderer zr) {
		this.zr=zr;
	}
	
	public String getName() {
		return zr.getZone().getName();
	}

	public void makeCurrentMap() {
		TabletopTool.getFrame().setCurrentZoneRenderer(zr);
	}
	
	public void exposePCAreaFog() {
		FogUtil.exposePCArea(zr);
	}

	public void exposeVisibleAreaGof() {
		Set<GUID> tokens = zr.getSelectedTokenSet();
		Set<GUID> ownedTokens = zr.getOwnedTokens(tokens);
		FogUtil.exposeVisibleArea(zr, ownedTokens);
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

	public TokenView findToken(String identifier) {
		Token t=zr.getZone().resolveToken(identifier);
		if(t==null)
			return null;
		else
			return new TokenView(t);
	}
	
	public List<TokenView> getTokens() {
		return TypeUtil.makeTokenViewList(zr.getZone().getTokensFiltered(new AllFilter()));
	}
	
	public List<TokenView> getSelectedTokens() {
		return TypeUtil.makeTokenViewList(zr.getSelectedTokensList());
	}
	
	public List<TokenView> getExposedTokens() {
		Zone zone = zr.getZone();
		return TypeUtil.makeTokenViewList(zone.getTokensFiltered(new ExposedFilter(zone)));
	}
	
	
	public List<TokenView> getPCTokens() {
		return TypeUtil.makeTokenViewList(zr.getZone().getTokensFiltered(new PCFilter()));
	}
	
	public List<TokenView> getNPCTokens() {
		return TypeUtil.makeTokenViewList(zr.getZone().getTokensFiltered(new NPCFilter()));
	}
	
	public List<TokenView> getTokensWithState(String state) {
		return TypeUtil.makeTokenViewList(zr.getZone().getTokensFiltered(new StateFilter(state)));
	}
	
	public List<TokenView> getOwnedTokens(String playerID) {
		return TypeUtil.makeTokenViewList(zr.getZone().getTokensFiltered(new OwnedFilter(playerID)));
	}

	public ArrayList<TokenView> getVisibleTokens() {
		Zone zone=zr.getZone();
		Set<GUID> visibleSet=zr.getVisibleTokenSet();
		ArrayList<TokenView> tokenList=new ArrayList<TokenView>(visibleSet.size());
		for (GUID id : visibleSet)
			tokenList.add(new TokenView(zone.getToken(id)));
		return tokenList;
	}
	
	public void selectTokens(Collection<TokenView> tokens, boolean cumulative) {
		List<GUID> guids=new ArrayList<GUID>(tokens.size());
		for(TokenView t:tokens)
			guids.add(t.getId());
		if(!cumulative)
			zr.clearSelectedTokens();
		zr.selectTokens(guids);
	}
	
	public void deselectAllTokens() {
		zr.clearSelectedTokens();
	}
	
	public void deselectTokens(Collection<TokenView> tokens) {
		for(TokenView t:tokens)
			zr.deselectToken(t.getId());
	}
	
	public boolean moveTokenToMap(MapView target, TokenView token, int targetX, int targetY) {
		Zone targetZone=target.zr.getZone();
		Token t=zr.getZone().getToken(token.getId());
		if(t==null)
			return false;

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
		return true;
	}
	
	public int moveTokenToMap(MapView target, List<TokenView> tokens, int targetX, int targetY) {
		Zone targetZone=target.zr.getZone();
		int count=0;
		for(TokenView token:tokens) {
			Token t=zr.getZone().getToken(token.getId());
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
		return count;
	}
}
