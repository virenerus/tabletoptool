package com.t3.macro.api.views;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.t3.client.TabletopTool;
import com.t3.model.GUID;
import com.t3.model.MacroButtonProperties;
import com.t3.model.Token;

public class MacroButtonView implements MacroView {

	private MacroButtonProperties macro;

	public MacroButtonView(MacroButtonProperties macro) {
		this.macro=macro;
	}
	
	/**
	 * @return the label of the macro button
	 */
	public String getLabel() {
		return macro.getLabel();
	}
	
	/**
	 * This method sets the label of this macro button
	 * @param label the new label of the button
	 */
	public void setLabel(String label) {
		macro.setLabel(label);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	/**
	 * @return the token this macro button is attached to
	 */
	public TokenView getToken() {
		return new TokenView(macro.getToken());
	}

	/**
	 * @return a string representation of the color of this button
	 */
	public String getColorKey() {
		return macro.getColorKey();
	}

	/**
	 * This method allows you to set the color of the macro button. You can give the color
	 * as a name (e.g. "red") or as a hexadecimal number (e.g. #ff0000).
	 * @param colorKey the color you want to set
	 */
	public void setColorKey(String colorKey) {
		macro.setColorKey(colorKey);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	/**
	 * @return the macro stored in the button as text
	 */
	public String getMacro() {
		return macro.getCommand();
	}

	/**
	 * This method sets the macro of this button.
	 * @param command the new macro
	 */
	public void setMacro(String command) {
		macro.setCommand(command);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	/**
	 * @return the group this button belongs to
	 */
	public String getGroup() {
		return macro.getGroup();
	}
	
	/**
	 * This method sets the group of this button.
	 * @param group the new group of this button
	 */
	public void setGroup(String group) {
		macro.setGroup(group);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	/**
	 * @return a key to sort this button by
	 */
	public String getSortby() {
		return macro.getSortby();
	}

	/**
	 * This method sets the sort key of this button. This allows you to sort the buttons
	 * in another order than by the label
	 * @param sortby the new sort key
	 */
	public void setSortby(String sortby) {
		macro.setSortby(sortby);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	public String getFontColorKey() {
		return macro.getFontColorKey();
	}

	public void setFontColorKey(String fontColorKey) {
		macro.setFontColorKey(fontColorKey);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	public String getMinWidth() {
		return macro.getMinWidth();
	}

	public void setMinWidth(String minWidth) {
		macro.setMinWidth(minWidth);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	public String getMaxWidth() {
		return macro.getMaxWidth();
	}

	public void setMaxWidth(String maxWidth) {
		macro.setMaxWidth(maxWidth);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	public void setToolTip(String tt) {
		macro.setToolTip(tt);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	public String getToolTip() {
		return macro.getToolTip();
	}

	public Boolean getCommonMacro() {
		return macro.getCommonMacro();
	}

	public void setCommonMacro(Boolean value) {
		macro.setCommonMacro(value);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	public Boolean getCompareGroup() {
		return macro.getCompareGroup();
	}

	public void setCompareGroup(Boolean value) {
		macro.setCompareGroup(value);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	public Boolean getCompareSortPrefix() {
		return macro.getCompareSortPrefix();
	}

	public void setCompareSortPrefix(Boolean value) {
		macro.setCompareSortPrefix(value);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	public Boolean getCompareCommand() {
		return macro.getCompareCommand();
	}

	public void setCompareCommand(Boolean value) {
		macro.setCompareCommand(value);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	public Object execute() {
		Map<String,Object> args=Collections.emptyMap();
		return execute(args);
	}
	public Object execute(Map<String,Object> arguments) {
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
			Token t=TabletopTool.getFrame().findToken(new GUID(u.getHost()));
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
