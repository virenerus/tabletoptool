package com.t3.script.api.functions;

import com.t3.client.TabletopTool;
import com.t3.language.I18N;
import com.t3.model.LookupTable;
import com.t3.model.LookupTable.LookupEntry;
import com.t3.script.MT2ScriptException;

public class TableFunctions {
	public String roll(String tableName) throws MT2ScriptException {
		return roll(tableName, null);
	}
	
	public String roll(String tableName, String roll) throws MT2ScriptException {
		LookupTable lookupTable = TabletopTool.getCampaign().getLookupTableMap().get(tableName);
		if (!TabletopTool.getPlayer().isGM() && !lookupTable.getAllowLookup()) {
			if (lookupTable.getVisible()) {
				throw new MT2ScriptException("table(): " + I18N.getText("msg.error.tableUnknown") + tableName);
			} else {
				throw new MT2ScriptException("table(): " + I18N.getText("msg.error.tableAccessProhibited") + ": " + tableName);
			}
    	}
		if (lookupTable == null) {
			throw new MT2ScriptException(I18N.getText("macro.function.LookupTableFunctions.unknownTable", "table", tableName));
		}
		
    	LookupEntry result = lookupTable.getLookup(roll);
    	return result.getValue();
	}
	
	
	public String tableImage(String name) throws MT2ScriptException {
		return tableImage(name, null);
	}
	
	public String tableImage(String name, int size) throws MT2ScriptException {
		return tableImage(name, null, size);
	}
	
	public String tableImage(String name, String roll) throws MT2ScriptException {
		LookupTable lookupTable = TabletopTool.getCampaign().getLookupTableMap().get(name);
		if (!TabletopTool.getPlayer().isGM() && !lookupTable.getAllowLookup()) {
			if (lookupTable.getVisible()) {
				throw new MT2ScriptException("tableImage(): " + I18N.getText("msg.error.tableUnknown") + name);
			} else {
				throw new MT2ScriptException("tableImage(): " + I18N.getText("msg.error.tableAccessProhibited") + ": " + name);
			}
    	}
		if (lookupTable == null) {
			throw new MT2ScriptException(I18N.getText("macro.function.LookupTableFunctions.unknownTable", "tableImage", name));
		}
		
    	LookupEntry result = lookupTable.getLookup(roll);
    	
    	if (result.getImageId() == null) {
			throw new MT2ScriptException(I18N.getText("macro.function.LookupTableFunctions.noImage", "tableImage", name));
		}
		
		StringBuilder assetId = new StringBuilder("asset://");
		assetId.append(result.getImageId().toString());
		return assetId.toString();
	}
	
	public String tableImage(String name, String roll, int size) throws MT2ScriptException {
		LookupTable lookupTable = TabletopTool.getCampaign().getLookupTableMap().get(name);
		if (!TabletopTool.getPlayer().isGM() && !lookupTable.getAllowLookup()) {
			if (lookupTable.getVisible()) {
				throw new MT2ScriptException("tableImage(): " + I18N.getText("msg.error.tableUnknown") + name);
			} else {
				throw new MT2ScriptException("tableImage(): " + I18N.getText("msg.error.tableAccessProhibited") + ": " + name);
			}
    	}
		if (lookupTable == null) {
			throw new MT2ScriptException(I18N.getText("macro.function.LookupTableFunctions.unknownTable", "tableImage", name));
		}
		
    	LookupEntry result = lookupTable.getLookup(roll);
    	
    	if (result.getImageId() == null) {
			throw new MT2ScriptException(I18N.getText("macro.function.LookupTableFunctions.noImage", "tableImage", name));
		}
		
		StringBuilder assetId = new StringBuilder("asset://");
		assetId.append(result.getImageId().toString());
		int i = Math.max(size, 1); // Constrain to a minimum of 1
		assetId.append("-");
		assetId.append(i);
		return assetId.toString();
	}
}
