package com.t3.client.ui.token;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import com.t3.chatparser.generated.ChatParser;
import com.t3.dice.expression.Expression;

public class DiceExpressionCellEditor extends AbstractCellEditor implements TableCellEditor {
	
	JTextField textField=new JTextField();
	
	@Override
	public Expression getCellEditorValue() {
		try {
			return new ChatParser(textField.getText()).parseExpression();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		Expression dev=(Expression) value;
		textField.setText(dev.toString());
		return textField;
	}

}
