package com.t3.client.ui.properties.detaileditors;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;

public class BooleanDetailEditor extends DetailEditor<Boolean> {
	private JCheckBox	checkbox;

	public BooleanDetailEditor(String title) {
		checkbox=new JCheckBox(title);
		this.setLayout(new BorderLayout());
		this.add(checkbox,BorderLayout.CENTER);
	}

	@Override
	public Boolean getValue() {
		return checkbox.isSelected();
	}

	@Override
	public void setTypedValue(Boolean value) {
		checkbox.setSelected(value);
	}
}
