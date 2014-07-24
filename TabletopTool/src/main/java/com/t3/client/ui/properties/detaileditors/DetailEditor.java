package com.t3.client.ui.properties.detaileditors;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

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
	
	public static DetailEditor<?> createDetailEditor(TokenProperty tokenProperty) {
		switch(tokenProperty.getType()) {
			case BOOLEAN:
				return new BooleanDetailEditor(tokenProperty.getName());
			case CAPPED:
				return new CappedIntegerEditor();
			case EXPRESSION:
				return new ExpressionEditor();
			case FLOAT:
				return new FloatEditor();
			case INTEGER:
				return new IntegerEditor();
			case LIST:
				return new ListEditor(tokenProperty);
			case MACRO:
				return new MacroEditor();
			case TEXT:
				return new TextEditor();
		}
		throw new IllegalArgumentException("There is no detail editor defined for "+tokenProperty);
	}

	public abstract TYPE getValue();

	public abstract void setTypedValue(TYPE value);
	
	public void setValue(Object value) {
		this.setTypedValue((TYPE)value);
	}

}
