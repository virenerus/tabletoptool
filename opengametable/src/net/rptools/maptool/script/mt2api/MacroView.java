package net.rptools.maptool.script.mt2api;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.model.GUID;
import net.rptools.maptool.model.MacroButtonProperties;
import net.rptools.maptool.model.Token;

public class MacroView {

	private MacroButtonProperties macro;

	public MacroView(MacroButtonProperties macro) {
		this.macro=macro;
	}
	
	public String getLabel() {
		return macro.getLabel();
	}
	
	public void setLabel(String label) {
		macro.setLabel(label);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}

	public TokenView getToken() {
		return new TokenView(macro.getToken());
	}

	public String getColorKey() {
		return macro.getColorKey();
	}

	public void setColorKey(String colorKey) {
		macro.setColorKey(colorKey);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}

	public String getCommand() {
		return macro.getCommand();
	}

	public void setCommand(String command) {
		macro.setCommand(command);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}

	public String getGroup() {
		return macro.getGroup();
	}
	
	public void setGroup(String group) {
		macro.setGroup(group);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}

	public String getSortby() {
		return macro.getSortby();
	}

	public void setSortby(String sortby) {
		macro.setSortby(sortby);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}

	public boolean getAutoExecute() {
		return macro.getAutoExecute();
	}

	public void setAutoExecute(boolean autoExecute) {
		macro.setAutoExecute(autoExecute);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}

	public boolean getApplyToTokens() {
		return macro.getApplyToTokens();
	}

	public void setApplyToTokens(boolean applyToTokens) {
		macro.setApplyToTokens(applyToTokens);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}

	public String getFontColorKey() {
		return macro.getFontColorKey();
	}

	public void setFontColorKey(String fontColorKey) {
		macro.setFontColorKey(fontColorKey);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}

	public String getMinWidth() {
		return macro.getMinWidth();
	}

	public void setMinWidth(String minWidth) {
		macro.setMinWidth(minWidth);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}

	public String getMaxWidth() {
		return macro.getMaxWidth();
	}

	public void setMaxWidth(String maxWidth) {
		macro.setMaxWidth(maxWidth);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}

	public void setToolTip(String tt) {
		macro.setToolTip(tt);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}

	public String getToolTip() {
		return macro.getToolTip();
	}

	public Boolean getCommonMacro() {
		return macro.getCommonMacro();
	}

	public void setCommonMacro(Boolean value) {
		macro.setCommonMacro(value);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}

	public Boolean getCompareGroup() {
		return macro.getCompareGroup();
	}

	public void setCompareGroup(Boolean value) {
		macro.setCompareGroup(value);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}

	public Boolean getCompareSortPrefix() {
		return macro.getCompareSortPrefix();
	}

	public void setCompareSortPrefix(Boolean value) {
		macro.setCompareSortPrefix(value);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}

	public Boolean getCompareCommand() {
		return macro.getCompareCommand();
	}

	public void setCompareCommand(Boolean value) {
		macro.setCompareCommand(value);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}

	public Boolean getCompareIncludeLabel() {
		return macro.getCompareIncludeLabel();
	}

	public Boolean getCompareAutoExecute() {
		return macro.getCompareAutoExecute();
	}

	public void setCompareAutoExecute(Boolean value) {
		macro.setCompareAutoExecute(value);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}

	public Boolean getCompareApplyToSelectedTokens() {
		return macro.getCompareApplyToSelectedTokens();
	}

	public void setCompareApplyToSelectedTokens(Boolean value) {
		macro.setCompareApplyToSelectedTokens(value);
		MapTool.serverCommand().putToken(MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), macro.getToken());
	}
	
	public Object executeMacro(String name, HashMap<String,Object> arguments) {
		return macro.executeMacro(macro.getToken(), arguments);
	}
	
	public String createLink(String text, String... args) {
		try {
			StringBuilder sb=new StringBuilder("<a href=\"macro://");
			sb.append(macro.getToken().getId().toString())
				.append('/')
				.append(URLEncoder.encode(macro.getLabel(),"utf8"));
			if(args.length>0) {
				sb.append('?');
				
				for(int i=0;i<args.length;i++) {
					if(i>0)
						sb.append('&');
					sb.append("arg").append(i).append('=').append(URLEncoder.encode(args[i],"utf8"));
				}
			}
			
			return sb.toString();
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object executeLink(String link) {
		try {
			URL u=new URL(link);
			Token t=MapTool.getFrame().findToken(new GUID(u.getHost()));
			MacroButtonProperties mbp = t.getMacro(URLDecoder.decode(u.getPath(),"utf8"),false);
			HashMap<String,Object> arguments=new HashMap<String,Object>();
			for(String a:StringUtils.split(u.getQuery(),'&')) {
				String[] aps=StringUtils.split(a,'=');
				arguments.put(aps[0], URLDecoder.decode(aps[1], "utf8"));
			}
			return mbp.executeMacro(t, arguments);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
