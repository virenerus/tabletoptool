package net.rptools.maptool.client.ui.token;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import net.rptools.maptool.script.mt2api.DiceExpressionView;

public class DiceExpressionCellEditor extends AbstractCellEditor implements TableCellEditor {
	
	JTextField textField=new JTextField();
	
	@Override
	public DiceExpressionView getCellEditorValue() {
		try {
			return new DiceExpressionView(textField.getText());
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		DiceExpressionView dev=(DiceExpressionView) value;
		textField.setText(dev.toString());
		return textField;
	}

}
