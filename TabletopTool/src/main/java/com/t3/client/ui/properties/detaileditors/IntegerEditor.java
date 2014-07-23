package com.t3.client.ui.properties.detaileditors;

import javax.swing.JSpinner;

public class IntegerEditor extends DetailEditor<Integer> {
	private JSpinner	spinner;

	public IntegerEditor() {
		spinner=new JSpinner();
		this.add(spinner);
	}

	@Override
	public Integer getValue() {
		return (Integer)spinner.getValue();
	}

	@Override
	public void setTypedValue(Integer value) {
		spinner.setValue(value);
	}
}
