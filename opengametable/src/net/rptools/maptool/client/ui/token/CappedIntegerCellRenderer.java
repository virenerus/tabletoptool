package net.rptools.maptool.client.ui.token;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;

import net.rptools.maptool.util.math.CappedInteger;

//todo don't create a new Panel+labels every time -> just style them
public class CappedIntegerCellRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		CappedInteger capped=(CappedInteger)value;
		JPanel panel=new JPanel();
		if(capped!=null) {
			panel.setToolTipText("currently "+capped.toString());
			
			//style it depending on the default one (for look and feel)
			TableCellRenderer defaultRenderer=table.getDefaultRenderer(String.class);
			Component c=defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			panel.setBackground(c.getBackground());
			if(c instanceof JComponent)
				panel.setBorder(((JComponent) c).getBorder());
			else if(hasFocus)
				panel.setBorder(new LineBorder(Color.GRAY));
			
			//content
			panel.setLayout(new BorderLayout());
			JLabel minLabel = new JLabel(Integer.toString(capped.getMin()),SwingConstants.CENTER);
			minLabel.setForeground(Color.GRAY);
			panel.add(minLabel,BorderLayout.LINE_START);
			
			JLabel valueLabel = new JLabel(Integer.toString(capped.getValue()),SwingConstants.CENTER);
			panel.add(valueLabel,BorderLayout.CENTER);
			
			JLabel maxLabel = new JLabel(Integer.toString(capped.getMax()),SwingConstants.CENTER);
			maxLabel.setForeground(Color.GRAY);
			panel.add(maxLabel,BorderLayout.LINE_END);
		}	
		
		return panel;
	}

}
