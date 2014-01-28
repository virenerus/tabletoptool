package net.rptools.maptool.script.mt2.functions.input;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/** Custom renderer to display icons and text inside a combo box */
public  class ComboBoxRenderer implements ListCellRenderer<JLabel> {
	
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