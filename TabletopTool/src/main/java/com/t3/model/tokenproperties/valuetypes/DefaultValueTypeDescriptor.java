package com.t3.model.tokenproperties.valuetypes;

import javax.annotation.Nonnull;

import com.jidesoft.grid.CellEditorFactory;
import com.jidesoft.grid.CellEditorManager;
import com.jidesoft.grid.EditorContext;

public class DefaultValueTypeDescriptor<TYPE> implements ValueTypeDescriptor<TYPE> {
	
	
	
	private TYPE			defaultDefaultValue;
	private EditorContext	editorContext;
	private CellEditorFactory[]	cellEditorFactories;

	public DefaultValueTypeDescriptor(TYPE defaultDefaultValue, @Nonnull EditorContext editorContext, @Nonnull CellEditorFactory... cellEditorFactories) {
		this.defaultDefaultValue=defaultDefaultValue;
		this.editorContext=editorContext;
		this.cellEditorFactories=cellEditorFactories;
	}

	public DefaultValueTypeDescriptor(TYPE defaultDefaultValue, @Nonnull CellEditorFactory... cellEditorFactories) {
		this.defaultDefaultValue=defaultDefaultValue;
		this.cellEditorFactories=cellEditorFactories;
	}

	@Override
	public EditorContext getEditorContext() {
		return editorContext;
	}

	@SuppressWarnings("null")
	@Override
	public String toStatsheetString(Object object) {
		if(object==null)
			return "null";
		else
			return object.toString();
	}

	@Override
	public void registerCellEditors() {
		for(CellEditorFactory cef:cellEditorFactories)
			CellEditorManager.registerEditor(defaultDefaultValue.getClass(), cef);
	}

	@Override
	public TYPE getDefaultDefaultValue() {
		return defaultDefaultValue;
	}

}
