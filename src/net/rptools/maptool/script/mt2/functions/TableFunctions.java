package net.rptools.maptool.script.mt2.functions;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.language.I18N;
import net.rptools.maptool.model.LookupTable;
import net.rptools.maptool.model.LookupTable.LookupEntry;
import net.rptools.maptool.script.MT2ScriptException;

public class TableFunctions {
	public String table(String name) throws MT2ScriptException {
		return table(name, null);
	}
	
	public String table(String name, String roll) throws MT2ScriptException {
		LookupTable lookupTable = MapTool.getCampaign().getLookupTableMap().get(name);
		if (!MapTool.getPlayer().isGM() && !lookupTable.getAllowLookup()) {
			if (lookupTable.getVisible()) {
				throw new MT2ScriptException("table(): " + I18N.getText("msg.error.tableUnknown") + name);
			} else {
				throw new MT2ScriptException("table(): " + I18N.getText("msg.error.tableAccessProhibited") + ": " + name);
			}
    	}
		if (lookupTable == null) {
			throw new MT2ScriptException(I18N.getText("macro.function.LookupTableFunctions.unknownTable", "table", name));
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
		LookupTable lookupTable = MapTool.getCampaign().getLookupTableMap().get(name);
		if (!MapTool.getPlayer().isGM() && !lookupTable.getAllowLookup()) {
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
		LookupTable lookupTable = MapTool.getCampaign().getLookupTableMap().get(name);
		if (!MapTool.getPlayer().isGM() && !lookupTable.getAllowLookup()) {
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
