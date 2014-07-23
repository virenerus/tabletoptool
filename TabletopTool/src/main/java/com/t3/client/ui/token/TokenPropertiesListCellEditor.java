package com.t3.client.ui.token;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.jidesoft.grid.CellEditorFactory;

public class TokenPropertiesListCellEditor extends AbstractCellEditor implements TableCellEditor {
	public static class Factory implements CellEditorFactory {
		@Override
		public CellEditor create() {
			return new TokenPropertiesListCellEditor();
		}
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return null;
	}

	
}
