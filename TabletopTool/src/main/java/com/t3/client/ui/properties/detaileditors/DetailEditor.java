package com.t3.client.ui.properties.detaileditors;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.t3.model.Token;
import com.t3.model.properties.PropertyHolder;
import com.t3.model.properties.TokenProperty;
import com.t3.util.math.CappedInteger;

public abstract class DetailEditor<TYPE> extends JPanel {

	private final boolean spansTwoColumns;
	
	public DetailEditor() {
		this(false);
	}
	
	public DetailEditor(boolean spansTwoColumns) {
		this.spansTwoColumns=spansTwoColumns;
		this.setLayout(new BorderLayout(10,2));
	}
	
	public boolean isSpanningTwoColumns() {
		return spansTwoColumns;
	}
	
	public static DetailEditor<?> createDetailEditor(TokenProperty tokenProperty, PropertyHolder propertyHolder) {
		switch(tokenProperty.getType()) {
			case BOOLEAN:
				return new BooleanDetailEditor();
			case CAPPED:
				return new CappedIntegerEditor();
			case EXPRESSION:
				return new ExpressionEditor();
			case FLOAT:
				return new FloatEditor();
			case INTEGER:
				return new IntegerEditor();
			case LIST:
				return new ListEditor(tokenProperty, propertyHolder);
			case MACRO:
				return new MacroDetailEditor(propertyHolder);
			case TEXT:
				return new TextEditor();
			case STRUCT:
				return new StructEditor(tokenProperty, propertyHolder);
			default:
				throw new IllegalArgumentException("There is no detail editor defined for "+tokenProperty);
		}
	}

	public abstract TYPE getValue();

	public abstract void setTypedValue(TYPE value);
	
	public void setValue(Object value) {
		this.setTypedValue((TYPE)value);
	}

}
