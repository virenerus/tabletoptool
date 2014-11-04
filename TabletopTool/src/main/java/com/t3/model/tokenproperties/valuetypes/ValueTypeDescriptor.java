package com.t3.model.tokenproperties.valuetypes;

import javax.annotation.Nonnull;

import com.jidesoft.grid.EditorContext;

public interface ValueTypeDescriptor<TYPE> {
	public EditorContext getEditorContext();
	public @Nonnull String toStatsheetString(Object propertyValue);
	public void registerCellEditors();
	public TYPE getDefaultDefaultValue();
	/**
	 * This method should try to convert the given object into an object of the type described. Otherwise return 0;
	 * @param object
	 * @return
	 */
	public TYPE convert(Object object);
}
