/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3.model.campaign;

import com.jidesoft.grid.BooleanCheckBoxCellEditor;
import com.jidesoft.grid.EditorContext;
import com.t3.dice.expression.Expression;
import com.t3.macro.api.views.PropertyMacroView;
import com.t3.util.math.CappedInteger;

public enum TokenPropertyType {
	BOOLEAN("Boolean", Boolean.class, Boolean.FALSE) {
		@Override
		public EditorContext getEditorContext() {
			return BooleanCheckBoxCellEditor.CONTEXT;
		}
	},
	TEXT("Text", String.class, ""),
	INTEGER("Integer", Integer.class, 0),
	FLOAT("Float", Float.class, new Float(0)),
	CAPPED("Capped", CappedInteger.class, new CappedInteger(0,0,0)) {
		@Override
		public String toStatsheetString(Object propertyValue) {
			if(propertyValue==null)
				return "null";
			else {
				CappedInteger capped=(CappedInteger)propertyValue;
				return capped.getValue()+" / "+capped.getMax();
			}
		}
	},
	EXPRESSION("Expression", Expression.class, Expression.ZERO_EXPRESSION),
	MACRO("Macro", PropertyMacroView.class,PropertyMacroView.EMPTY_MACRO);	
	
	private final Class<?> type;
	private final String name;
	private final Object defaultDefaultValue;

	private <T> TokenPropertyType(String name, Class<T> type, T defaultDefaultValue) {
		this.type=type;
		this.name=name;
		this.defaultDefaultValue=defaultDefaultValue;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public Class<?> getType() {
		return type;
	}
	
	public boolean isInstance(Object obj) {
		return type.isInstance(obj);
	}

	public String toStatsheetString(Object propertyValue) {
		return propertyValue==null?"null":propertyValue.toString();
	}

	/**
	 * @return something different than null to switch the editors used for this property in a jide grid
	 */
	public EditorContext getEditorContext() {
		return null;
	}

	public Object getDefaultDefaultValue() {
		return defaultDefaultValue;
	}
	
}
