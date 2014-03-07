package com.t3.macro.api.functions;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.codehaus.groovy.syntax.ParserException;

import com.t3.client.AppPreferences;
import com.t3.client.TabletopTool;
import com.t3.client.ui.token.BarTokenOverlay;
import com.t3.client.ui.token.BooleanTokenOverlay;
import com.t3.client.ui.tokenpanel.InitiativePanel;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.client.walker.WalkerMetric;
import com.t3.model.LightSource;
import com.t3.model.LookupTable;
import com.t3.model.SightType;
import com.t3.model.Token;
import com.t3.model.Zone;
import com.t3.model.campaign.Campaign;
import com.t3.model.campaign.CampaignProperties;
import com.t3.model.grid.Grid;
import com.t3.model.grid.GridFactory;
import com.t3.server.ServerPolicy;
import com.t3.util.SysInfo;

public class InfoFunctions {
	/**
	 * Retrieves the information about the current zone/map
	 * 
	 * @return The information about the map.
	 * @throws ParserException
	 *             when there is an error.
	 */
	public Map<String, Object> getMapInfo() {
		Map<String, Object> minfo = new HashMap<String, Object>();
		Zone zone = TabletopTool.getFrame().getCurrentZoneRenderer().getZone();

		minfo.put("name", zone.getName());
		minfo.put("image x scale", zone.getImageScaleX());
		minfo.put("image y scale", zone.getImageScaleY());
		minfo.put("player visible", zone.isVisible() ? 1 : 0);

		//FIXME trusted?
		//if (TabletopTool.getParser().isMacroTrusted()) {
			minfo.put("id", zone.getId().toString());
			minfo.put("creation time", zone.getCreationTime());
			minfo.put("width", zone.getWidth());
			minfo.put("height", zone.getHeight());
			minfo.put("largest Z order", zone.getLargestZOrder());
		//}

		String visionType = "off";
		if(zone.getVisionType()!=null)
			visionType=zone.getVisionType().name();
		minfo.put("vision type", visionType);

		Map<String, Object> ginfo = new HashMap<String, Object>();
		minfo.put("grid", ginfo);

		Grid grid = zone.getGrid();

		ginfo.put("type", GridFactory.getGridType(grid));
		ginfo.put("color", String.format("%h", zone.getGridColor()));
		ginfo.put("units per cell", zone.getUnitsPerCell());
		ginfo.put("cell height", zone.getGrid().getCellHeight());
		ginfo.put("cell width", zone.getGrid().getCellWidth());
		ginfo.put("cell offset width", zone.getGrid().getCellOffset().getWidth());
		ginfo.put("cell offset height", zone.getGrid().getCellOffset().getHeight());
		ginfo.put("size", zone.getGrid().getSize());
		ginfo.put("x offset", zone.getGrid().getOffsetX());
		ginfo.put("y offset", zone.getGrid().getOffsetY());
		ginfo.put("second dimension", grid.getSecondDimension());

		return minfo;
	}

	/**
	 * Retrieves the client side preferences that do not have server over rides
	 * 
	 * @return the client side preferences
	 */
	public Map<String, Object> getClientInfo() {
		Map<String, Object> cinfo = new HashMap<String, Object>();

		cinfo.put("face edge", AppPreferences.getFaceEdge() ? BigDecimal.ONE : BigDecimal.ZERO);
		cinfo.put("face vertex", AppPreferences.getFaceVertex() ? BigDecimal.ONE : BigDecimal.ZERO);
		cinfo.put("portrait size", AppPreferences.getPortraitSize());
		cinfo.put("show stat sheet", AppPreferences.getShowStatSheet());
		cinfo.put("version", TabletopTool.getVersion());
		cinfo.put("isFullScreen", TabletopTool.getFrame().isFullScreen() ? BigDecimal.ONE : BigDecimal.ZERO);
		cinfo.put("timeInMs", System.currentTimeMillis());
		cinfo.put("timeDate", getTimeDate());
		//FIXME trusted?
		//if (TabletopTool.getParser().isMacroTrusted()) {
			Map<String, Object> libInfo = new HashMap<String, Object>();
			for (ZoneRenderer zr : TabletopTool.getFrame().getZoneRenderers()) {
				Zone zone = zr.getZone();
				for (Token token : zone.getTokens()) {
					if (token.getName().toLowerCase().startsWith("lib:")) {
						if (token.getProperty("libversion") != null) {
							libInfo.put(token.getName(), token.getProperty("libversion"));
						} else {
							libInfo.put(token.getName(), "unknown");
						}
					}
				}
			}
			if (libInfo.size() > 0) {
				cinfo.put("library tokens", libInfo);
			}
		//}
		return cinfo;
	}

	private String getTimeDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(cal.getTime());
	}

	/**
	 * Retrieves the server side preferences
	 * 
	 * @return the server side preferences
	 */
	public Map<String, Object> getServerInfo() {
		Map<String, Object> sinfo = new HashMap<String, Object>();
		ServerPolicy sp = TabletopTool.getServerPolicy();

		sinfo.put("tooltips for default roll format", sp.getUseToolTipsForDefaultRollFormat() ? BigDecimal.ONE : BigDecimal.ZERO);
		sinfo.put("players can reveal", sp.getPlayersCanRevealVision() ? BigDecimal.ONE : BigDecimal.ZERO);
		sinfo.put("movement locked", sp.isMovementLocked() ? BigDecimal.ONE : BigDecimal.ZERO);
		sinfo.put("restricted impersonation", sp.isRestrictedImpersonation() ? BigDecimal.ONE : BigDecimal.ZERO);
		sinfo.put("individual views", sp.isUseIndividualViews() ? BigDecimal.ONE : BigDecimal.ZERO);
		sinfo.put("strict token management", sp.useStrictTokenManagement() ? BigDecimal.ONE : BigDecimal.ZERO);
		sinfo.put("players receive campaign macros", sp.playersReceiveCampaignMacros() ? BigDecimal.ONE : BigDecimal.ZERO);
		WalkerMetric metric = TabletopTool.isPersonalServer() ? AppPreferences.getMovementMetric() : TabletopTool.getServerPolicy().getMovementMetric();
		sinfo.put("movement metric", metric.toString());

		sinfo.put("timeInMs", sp.getSystemTime());
		sinfo.put("timeDate", sp.getTimeDate());

		sinfo.put("gm", TabletopTool.getGMs());

		InitiativePanel ip = TabletopTool.getFrame().getInitiativePanel();
		if (ip != null) {
			sinfo.put("initiative owner permissions", ip.isOwnerPermissions() ? BigDecimal.ONE : BigDecimal.ZERO);
		}
		return sinfo;
	}

	/**
	 * Retrieves information about the campaign as a json object.
	 * 
	 * @return the campaign information.
	 * @throws ParserException
	 *             if an error occurs.
	 */
	public Map<String, Object> getCampaignInfo() {
		Map<String, Object> cinfo = new HashMap<String, Object>();
		Campaign c = TabletopTool.getCampaign();
		CampaignProperties cp = c.getCampaignProperties();

		cinfo.put("id", c.getId().toString());
		cinfo.put("initiative movement locked", cp.isInitiativeMovementLock() ? BigDecimal.ONE : BigDecimal.ZERO);
		cinfo.put("initiative owner permissions", cp.isInitiativeOwnerPermissions() ? BigDecimal.ONE : BigDecimal.ZERO);

		Map<String, String> zinfo = new HashMap<String, String>();
		for (Zone z : c.getZones()) {
			zinfo.put(z.getName(), z.getId().toString());
		}
		cinfo.put("zones", zinfo);

		Set<String> tinfo = new HashSet<String>();
		for (LookupTable table : c.getLookupTableMap().values()) {
			tinfo.add(table.getName());
		}
		cinfo.put("tables", tinfo);

		Map<String, Object> llinfo = new HashMap<String, Object>();
		for (String ltype : c.getLightSourcesMap().keySet()) {
			Set<Object> ltinfo = new HashSet<Object>();
			for (LightSource ls : c.getLightSourceMap(ltype).values()) {
				HashMap<String, Object> linfo = new HashMap<String, Object>();
				linfo.put("name", ls.getName());
				linfo.put("max range", ls.getMaxRange());
				linfo.put("type", ls.getType());
//				List<Light> lights = new ArrayList<Light>();
//				for (Light light : ls.getLightList()) {
//					lights.add(light);
//				}
				linfo.put("light segments", ls.getLightList());
				ltinfo.add(linfo);
			}
			llinfo.put(ltype, ltinfo);
		}
		cinfo.put("light sources", llinfo);

		Map<String, Set<String>> sinfo = new HashMap<String, Set<String>>();
		for (BooleanTokenOverlay states : c.getTokenStatesMap().values()) {
			String group = states.getGroup();
			if (group == null) {
				group = "no group";
			}
			Set<String> sgroup = sinfo.get(group);
			if (sgroup != null) {
				sgroup.add(states.getName());
			} else {
				sgroup = new HashSet<String>();
				sgroup.add(states.getName());
				sinfo.put(group, sgroup);
			}
		}
		cinfo.put("states", sinfo);

		cinfo.put("remote repository", c.getRemoteRepositoryList());

		Map<String, Object> sightInfo = new HashMap<String, Object>();
		for (SightType sightType : c.getSightTypeMap().values()) {
			Map<String, Object> si = new HashMap<String, Object>();
			si.put("arc", sightType.getArc());
			si.put("distance", sightType.getArc());
			si.put("multiplier", sightType.getMultiplier());
			si.put("shape", sightType.getShape().toString());
			si.put("type", sightType.getOffset());
			sightInfo.put(sightType.getName(), si);
		}
		cinfo.put("sight", sightInfo);

		Map<String, Set<Object>> barinfo = new HashMap<String, Set<Object>>();
		for (BarTokenOverlay tbo : c.getTokenBarsMap().values()) {
			String group = tbo.getGroup();
			if (group == null) {
				group = "no group";
			}
			Set<Object> bgroup = barinfo.get(group);
			if (bgroup == null) {
				bgroup = new HashSet<Object>();
				barinfo.put(group, bgroup);
			}
			Map<String, Object> b = new HashMap<String, Object>();
			b.put("name", tbo.getName());
			b.put("side", tbo.getSide());
			b.put("increment", tbo.getIncrements());
			bgroup.add(b);
		}
		cinfo.put("bars", barinfo);

		return cinfo;
	}

	/**
	 * Retrieves debug information
	 * 
	 * @return the debug information.
	 * @throws ParserException
	 *             if an error occurs.
	 */
	public Map<String, Object> getDebugInfo() {
		return new SysInfo().getSysInfo();
	}
}
