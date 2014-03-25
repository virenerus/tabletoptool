package com.t3.macro.api.functions;

import com.t3.client.TabletopTool;
import com.t3.language.I18N;
import com.t3.macro.MacroException;
import com.t3.model.LookupTable;
import com.t3.model.LookupTable.LookupEntry;

public class TableFunctions {
	/**
	 * This method rolls on the given table.
	 * @param tableName the table you want to roll on
	 * @return the content of the rolled row
	 * @throws MacroException when the table does not exist or if it is not accessible
	 */
	public String roll(String tableName) throws MacroException {
		return roll(tableName, null);
	}
	
	/**
	 * This method returns the text of the given table
	 * @param tableName the table you want to roll on
	 * @param roll the result of the roll
	 * @return the content of the rolled row
	 * @throws MacroException when the table does not exist or if it is not accessible
	 */
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
	
	/**
	 * @param name the name of the table
	 * @return the image of a roll on the given table
	 * @throws MacroException if table does not exist or has no images
	 */
	public String tableImage(String name) throws MacroException {
		return tableImage(name, null);
	}
	
	/**
	 * @param name the name of the table
	 * @param size the size you want the image to be scaled to
	 * @return the image of a roll on the given table
	 * @throws MacroException if table does not exist or has no images
	 */
	public String tableImage(String name, int size) throws MacroException {
		return tableImage(name, null, size);
	}
	
	/**
	 * @param name the name of the table
	 * @param roll the result of the roll
	 * @return the image of a roll on the given table
	 * @throws MacroException if table does not exist or has no images
	 */
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
	
	/**
	 * @param name the name of the table
	 * @param roll the result of the roll
	 * @param size the size you want the image to be scaled to
	 * @return the image of a roll on the given table
	 * @throws MacroException if table does not exist or has no images
	 */
	public String tableImage(String name, String roll, int size) throws MacroException {
		int i = Math.max(size, 1); // Constrain to a minimum of 1
		return tableImage(name,roll)+"-"+i;
	}
}
