package net.rptools.maptool.script.mt2api;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.model.MacroButtonProperties;

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
}
