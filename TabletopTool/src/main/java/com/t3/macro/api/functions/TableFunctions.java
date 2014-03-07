package com.t3.macro.api.functions;

import com.t3.client.TabletopTool;
import com.t3.language.I18N;
import com.t3.macro.MacroException;
import com.t3.model.LookupTable;
import com.t3.model.LookupTable.LookupEntry;

public class TableFunctions {
	public String roll(String tableName) throws MacroException {
		return roll(tableName, null);
	}
	
	public String roll(String tableName, String roll) throws MacroException {
		LookupTable lookupTable = TabletopTool.getCampaign().getLookupTableMap().get(tableName);
		if (!TabletopTool.getPlayer().isGM() && !lookupTable.getAllowLookup()) {
			if (lookupTable.getVisible()) {
				throw new MacroException("table(): " + I18N.getText("msg.error.tableUnknown") + tableName);
			} else {
				throw new MacroException("table(): " + I18N.getText("msg.error.tableAccessProhibited") + ": " + tableName);
			}
    	}
		if (lookupTable == null) {
			throw new MacroException(I18N.getText("macro.function.LookupTableFunctions.unknownTable", "table", tableName));
		}
		
    	LookupEntry result = lookupTable.getLookup(roll);
    	return result.getValue();
	}
	
	
	public String tableImage(String name) throws MacroException {
		return tableImage(name, null);
	}
	
	public String tableImage(String name, int size) throws MacroException {
		return tableImage(name, null, size);
	}
	
	public String tableImage(String name, String roll) throws MacroException {
		LookupTable lookupTable = TabletopTool.getCampaign().getLookupTableMap().get(name);
		if (!TabletopTool.getPlayer().isGM() && !lookupTable.getAllowLookup()) {
			if (lookupTable.getVisible()) {
				throw new MacroException("tableImage(): " + I18N.getText("msg.error.tableUnknown") + name);
			} else {
				throw new MacroException("tableImage(): " + I18N.getText("msg.error.tableAccessProhibited") + ": " + name);
			}
    	}
		if (lookupTable == null) {
			throw new MacroException(I18N.getText("macro.function.LookupTableFunctions.unknownTable", "tableImage", name));
		}
		
    	LookupEntry result = lookupTable.getLookup(roll);
    	
    	if (result.getImageId() == null) {
			throw new MacroException(I18N.getText("macro.function.LookupTableFunctions.noImage", "tableImage", name));
		}
		
		StringBuilder assetId = new StringBuilder("asset://");
		assetId.append(result.getImageId().toString());
		return assetId.toString();
	}
	
	public String tableImage(String name, String roll, int size) throws MacroException {
		LookupTable lookupTable = TabletopTool.getCampaign().getLookupTableMap().get(name);
		if (!TabletopTool.getPlayer().isGM() && !lookupTable.getAllowLookup()) {
			if (lookupTable.getVisible()) {
				throw new MacroException("tableImage(): " + I18N.getText("msg.error.tableUnknown") + name);
			} else {
				throw new MacroException("tableImage(): " + I18N.getText("msg.error.tableAccessProhibited") + ": " + name);
			}
    	}
		if (lookupTable == null) {
			throw new MacroException(I18N.getText("macro.function.LookupTableFunctions.unknownTable", "tableImage", name));
		}
		
    	LookupEntry result = lookupTable.getLookup(roll);
    	
    	if (result.getImageId() == null) {
			throw new MacroException(I18N.getText("macro.function.LookupTableFunctions.noImage", "tableImage", name));
		}
		
		StringBuilder assetId = new StringBuilder("asset://");
		assetId.append(result.getImageId().toString());
		int i = Math.max(size, 1); // Constrain to a minimum of 1
		assetId.append("-");
		assetId.append(i);
		return assetId.toString();
	}
}
