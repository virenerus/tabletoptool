package com.t3.client.ui.properties.detaileditors;

import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JFormattedTextField;

public class FloatEditor extends DetailEditor<Float> {
	
	private JFormattedTextField	field;

	public FloatEditor() {
		field=new JFormattedTextField(NumberFormat.getNumberInstance());
		this.add(field);
	}

	@Override
	public Float getValue() {
		try {
			return NumberFormat.getNumberInstance().parse(field.getText()).floatValue();
		} catch (ParseException e) {
			return null;
		}
	}

	@Override
	public void setTypedValue(Float value) {
		field.setText(NumberFormat.getNumberInstance().format(value));
	}
}
