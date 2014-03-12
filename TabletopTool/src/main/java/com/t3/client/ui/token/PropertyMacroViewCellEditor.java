package com.t3.client.ui.token;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import com.t3.macro.api.views.DiceExpressionView;
import com.t3.macro.api.views.PropertyMacroView;

public class PropertyMacroViewCellEditor extends AbstractCellEditor implements TableCellEditor {
	
	JTextField textField=new JTextField();
	private PropertyMacroView	pmv;
	
	@Override
	public PropertyMacroView getCellEditorValue() {
		try {
			return new PropertyMacroView(pmv, textField.getText());
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		PropertyMacroView pmv=(PropertyMacroView) value;
		textField.setText(pmv.toString());
		this.pmv=pmv;
		return textField;
	}
}
