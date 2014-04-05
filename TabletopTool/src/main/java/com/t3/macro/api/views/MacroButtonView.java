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

	/**
	 * @return a string representation of the color of this button text
	 */
	public String getTextColorKey() {
		return macro.getFontColorKey();
	}

	/**
	 * This method allows you to set the color of the macro button text. You can give 
	 * the color as a name (e.g. "red") or as a hexadecimal number (e.g. #ff0000).
	 * @param colorKey the color you want to set
	 */
	public void setTextColorKey(String colorKey) {
		macro.setFontColorKey(colorKey);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	/**
	 * @return the minimum width of this button
	 */
	public String getMinWidth() {
		return macro.getMinWidth();
	}
	
	/**
	 * This method sets the minimum width of this button
	 * @param minWidth the new minimum width
	 */
	public void setMinWidth(String minWidth) {
		macro.setMinWidth(minWidth);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	/**
	 * @return the maximum width of this button
	 */
	public String getMaxWidth() {
		return macro.getMaxWidth();
	}

	/**
	 * This method sets the maximum width of this button
	 * @param maxWidth the new maximum width
	 */
	public void setMaxWidth(String maxWidth) {
		macro.setMaxWidth(maxWidth);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	/**
	 * This method sets the tooltip of this macro.
	 * @param tooltip the new tooltip
	 */
	public void setToolTip(String tooltip) {
		macro.setToolTip(tooltip);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	/**
	 * @return the tooltip of this token
	 */
	public String getToolTip() {
		return macro.getToolTip();
	}

	/**
	 * @return if this is a common macro
	 */
	public boolean getCommonMacro() {
		return macro.getCommonMacro();
	}

	/**
	 * This methods makes this macro button  a common one or not
	 * @param value if this macro button should be common
	 */
	public void setCommonMacro(boolean value) {
		macro.setCommonMacro(value);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	/**
	 * @return if the group of this macro button should be used to decide if they are 
	 * displayed as common 
	 */
	public boolean getCompareGroup() {
		return macro.getCompareGroup();
	}

	/**
	 * This method sets if the group of this macro button should be used to decide if 
	 * they are displayed as common 
	 * @param value if it is used
	 */
	public void setCompareGroup(boolean value) {
		macro.setCompareGroup(value);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	/**
	 * @return if the sort key of this macro button should be used to decide if they are 
	 * displayed as common 
	 */
	public boolean getCompareSortBy() {
		return macro.getCompareSortPrefix();
	}

	/**
	 * This method sets if the sort key of this macro button should be used to decide if 
	 * they are displayed as common 
	 * @param value if it is used
	 */
	public void setCompareSortBy(boolean value) {
		macro.setCompareSortPrefix(value);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	/**
	 * @return if the macro code of this macro button should be used to decide if they are 
	 * displayed as common 
	 */
	public boolean getCompareCommand() {
		return macro.getCompareCommand();
	}

	/**
	 * This method sets if the macro code of this macro button should be used to decide if 
	 * they are displayed as common 
	 * @param value if it is used
	 */
	public void setCompareCommand(boolean value) {
		macro.setCompareCommand(value);
		TabletopTool.serverCommand().putToken(macro.getToken().getZone().getId(), macro.getToken());
	}

	/**
	 * This will execute this macro without any arguments.
	 * @return the object returned by the macro
	 */
	@Override
	public Object execute() {
		Map<String,Object> args=Collections.emptyMap();
		return execute(args);
	}
	
	/**
	 * This will execute this macro with the given arguments
	 * @param arguments the arguments given to the called macro
	 * @return the object returned by the macro
	 */
	@Override
	public Object execute(Map<String,Object> arguments) {
		return macro.executeMacro(macro.getToken(), arguments);
	}
	
	/**
	 * This method will create a html link that will call this macro.
	 * @param text the text that should call this macro
	 * @param args the 
	 * @return
	 */
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
