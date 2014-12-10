package com.t3.client.ui.properties.detaileditors;

import com.t3.client.ui.macroeditor.MacroEditor;
import com.t3.macro.MacroException;
import com.t3.macro.api.views.PropertyMacroView;
import com.t3.model.Token;
import com.t3.model.tokenproperties.propertyholder.PropertyHolder;

public class MacroDetailEditor extends DetailEditor<PropertyMacroView> {

	private MacroEditor	macroEditor;
	private PropertyHolder	propertyHolder;

	public MacroDetailEditor(PropertyHolder propertyHolder) {
		this.propertyHolder=propertyHolder;
		macroEditor=new MacroEditor();
		macroEditor.setMinimumRows(8);
		this.add(macroEditor);
	}
	
	@Override
	public PropertyMacroView getValue() {
		try {
			if(propertyHolder instanceof Token)
				return new PropertyMacroView(macroEditor.getMacro(), (Token)propertyHolder);
			else
				return new PropertyMacroView(macroEditor.getMacro());
		} catch (MacroException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setTypedValue(PropertyMacroView value) {
		this.macroEditor.setMacro(value.getMacro());
	}

}
