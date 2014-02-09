package net.rptools.maptool.client.ui.token;

import java.awt.Color;
import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import net.rptools.maptool.util.math.CappedInteger;

import com.jidesoft.grid.CellEditorFactory;

public class CappedIntegerCellEditor extends AbstractCellEditor implements TableCellEditor {
	private JSpinner valueField = new JSpinner();
	private JSpinner minField = new JSpinner();
	private JSpinner maxField = new JSpinner();
	private JPanel panel=new JPanel();
	private CappedInteger capped;
	
	public CappedIntegerCellEditor() {
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(minField);
		panel.add(valueField);
		panel.add(maxField);
		valueField.setToolTipText("current value");
		minField.setForeground(Color.GRAY);
		minField.setToolTipText("minimum value");
		maxField.setForeground(Color.GRAY);
		maxField.setToolTipText("maximum value");
	}
    
	@Override
	public CappedInteger getCellEditorValue() {
		return new CappedInteger((Integer)valueField.getValue(),
				(Integer)minField.getValue(),
				(Integer)maxField.getValue());
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		capped=(CappedInteger)value;
		valueField.setValue(capped.getValue());
		minField.setValue(capped.getMin());
		maxField.setValue(capped.getMax());
		return panel;
	}

	public static class Factory  implements CellEditorFactory {
		@Override
		public TableCellEditor create() {
			return new CappedIntegerCellEditor();
		}
	}
}
