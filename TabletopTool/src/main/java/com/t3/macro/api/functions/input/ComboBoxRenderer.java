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
package com.t3.macro.api.functions.input;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/** Custom renderer to display icons and text inside a combo box */
public  class ComboBoxRenderer implements ListCellRenderer<JLabel> {
	
	@Override
	public Component getListCellRendererComponent(JList<? extends JLabel> list, JLabel label, int index, boolean isSelected, boolean cellHasFocus) {
		if (isSelected) {
			label.setBackground(list.getSelectionBackground());
			label.setForeground(list.getSelectionForeground());
		} else {
			label.setBackground(list.getBackground());
			label.setForeground(list.getForeground());
		}
		return label;
	}
}
