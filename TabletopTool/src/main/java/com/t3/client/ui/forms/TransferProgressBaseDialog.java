package com.t3.client.ui.forms;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Virenerus
 */
public abstract class TransferProgressBaseDialog extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JLabel label1;
	protected JScrollPane scrollPane1;
	protected JTable transferTable;
	protected JButton closeButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public TransferProgressBaseDialog() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		label1 = new JLabel();
		scrollPane1 = new JScrollPane();
		transferTable = new JTable();
		closeButton = new JButton();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"[10px,default], [300px,default], default", //$NON-NLS-1$
			"[10px,default], 30dlu, [10px,default], [150dlu,default], [10px,default], default, [10px,default]")); //$NON-NLS-1$

		label1.setText("<html>These are images that are currently being retrieved from the server or a repository."); //$NON-NLS-1$
		add(label1, CC.xywh(2, 2, 2, 1, CC.DEFAULT, CC.TOP));

		{
			scrollPane1.setViewportView(transferTable);
		}
		add(scrollPane1, CC.xy(2, 4, CC.FILL, CC.FILL));

		closeButton.setText("Close"); //$NON-NLS-1$
		closeButton.setActionCommand("Close"); //$NON-NLS-1$
		add(closeButton, CC.xy(2, 6, CC.RIGHT, CC.DEFAULT));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
