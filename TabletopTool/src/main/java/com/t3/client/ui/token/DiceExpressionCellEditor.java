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

import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import com.jidesoft.grid.CellEditorFactory;
import com.t3.chatparser.generated.ChatParser;
import com.t3.dice.expression.Expression;

public class DiceExpressionCellEditor extends AbstractCellEditor implements TableCellEditor {
	
	public static class Factory implements CellEditorFactory {
		@Override
		public CellEditor create() {
			return new DiceExpressionCellEditor();
		}
	}

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
