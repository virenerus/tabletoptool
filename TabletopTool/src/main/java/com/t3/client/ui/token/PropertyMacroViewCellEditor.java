/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3.client.ui.token;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import com.jidesoft.grid.CellEditorFactory;
import com.t3.macro.api.views.PropertyMacroView;

public class PropertyMacroViewCellEditor extends AbstractCellEditor implements TableCellEditor {
	
	public static class Factory implements CellEditorFactory {
		@Override
		public CellEditor create() {
			return new PropertyMacroViewCellEditor();
		}
	}

	JTextField textField=new JTextField();
	private PropertyMacroView	pmv;
	
	@Override
	public PropertyMacroView getCellEditorValue() {
		try {
			if(textField.getText()==null || textField.getText().trim().isEmpty())
				return PropertyMacroView.EMPTY_MACRO;
			else
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
