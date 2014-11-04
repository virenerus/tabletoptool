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
package com.t3.model.tokenproperties.valuetypes;

import com.jidesoft.grid.BooleanCheckBoxCellEditor;
import com.t3.client.ui.token.DiceExpressionCellEditor;
import com.t3.client.ui.token.PropertyMacroViewCellEditor;
import com.t3.dice.expression.Expression;
import com.t3.macro.api.views.PropertyMacroView;
import com.t3.model.tokenproperties.instance.CappedInteger;
import com.t3.model.tokenproperties.instance.Struct;
import com.t3.model.tokenproperties.instance.TokenPropertiesList;
import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(0)
public enum ValueType {
	//TODO implement more descriptors with converters
	BOOLEAN("Boolean", Boolean.class, new DefaultValueTypeDescriptor<Boolean>(Boolean.FALSE, BooleanCheckBoxCellEditor.CONTEXT)),
	TEXT("Text", String.class, new DefaultValueTypeDescriptor<String>("")),
	INTEGER("Integer", Integer.class, new DefaultValueTypeDescriptor<Integer>(0)),
	FLOAT("Float", Float.class, new DefaultValueTypeDescriptor<Float>(0f)),
	CAPPED("Capped", CappedInteger.class, new CappedIntegerValueTypeDescriptor()),
	EXPRESSION("Expression", Expression.class, new DefaultValueTypeDescriptor<Expression>(Expression.ZERO_EXPRESSION, new DiceExpressionCellEditor.Factory())),
	MACRO("Macro", PropertyMacroView.class, new DefaultValueTypeDescriptor<PropertyMacroView>(PropertyMacroView.EMPTY_MACRO, new PropertyMacroViewCellEditor.Factory())),
	@SuppressWarnings("rawtypes")
	LIST("List",TokenPropertiesList.class, new DefaultValueTypeDescriptor<TokenPropertiesList>(new TokenPropertiesList<Void>(null))),
	STRUCT("Struct",Struct.class, new DefaultValueTypeDescriptor<Struct>(new Struct(null)));
	
	private final Class<?> type;
	private final String name;
	private final ValueTypeDescriptor<?> descriptor;
	
	private <T> ValueType(String name, Class<T> type, ValueTypeDescriptor<T> descriptor) {
		this.type=type;
		this.name=name;
		this.descriptor=descriptor;
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
	
	public ValueTypeDescriptor<?> getDescriptor() {
		return descriptor;
	}

	public Object convert(Object object) {
		return descriptor.convert(object);
	}
}
