package com.t3.model.tokenproperties.valuetypes;

import com.jidesoft.grid.CellEditorManager;
import com.jidesoft.grid.CellRendererManager;
import com.jidesoft.grid.EditorContext;
import com.t3.client.ui.token.CappedIntegerCellEditor;
import com.t3.client.ui.token.CappedIntegerCellRenderer;
import com.t3.util.math.CappedInteger;

public class CappedIntegerValueTypeDescriptor implements ValueTypeDescriptor<CappedInteger> {

	@Override
	public EditorContext getEditorContext() {
		return null;
	}

	@Override
	public String toStatsheetString(Object propertyValue) {
		if(propertyValue==null)
			return "null";
		else {
			CappedInteger capped=(CappedInteger)propertyValue;
			return capped.getValue()+" / "+capped.getMax();
		}
	}

	@Override
	public void registerCellEditors() {
		CellRendererManager.registerRenderer(CappedInteger.class, new CappedIntegerCellRenderer());
		CellEditorManager.registerEditor(CappedInteger.class, new CappedIntegerCellEditor.Factory());
	}

	@Override
	public CappedInteger getDefaultDefaultValue() {
		return new CappedInteger(0,0,0);
	}

}
