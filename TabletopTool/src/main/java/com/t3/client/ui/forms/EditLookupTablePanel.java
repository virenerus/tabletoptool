package com.t3.client.ui.forms;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Virenerus
 */
public abstract class EditLookupTablePanel extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JScrollPane scrollPane1;
	protected JTable definitionTable;
	protected JLabel tableImage;
	protected JLabel label1;
	protected JPanel panel1;
	protected JLabel label2;
	protected JTextField tableName;
	protected JLabel label3;
	protected JTextField defaultTableRoll;
	protected JLabel label4;
	protected JPanel panel2;
	protected JButton acceptButton;
	protected JButton cancelButton;
	protected JCheckBox visibleCheckbox;
	protected JCheckBox allowLookupCheckbox;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public EditLookupTablePanel() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		scrollPane1 = new JScrollPane();
		definitionTable = new JTable();
		tableImage = new JLabel();
		label1 = new JLabel();
		panel1 = new JPanel();
		label2 = new JLabel();
		tableName = new JTextField();
		label3 = new JLabel();
		defaultTableRoll = new JTextField();
		label4 = new JLabel();
		panel2 = new JPanel();
		acceptButton = new JButton();
		cancelButton = new JButton();
		visibleCheckbox = new JCheckBox();
		allowLookupCheckbox = new JCheckBox();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"[10px,default], default:grow, default, 100px, [10px,default]", //$NON-NLS-1$
			"[10px,default], default, 100px, [10px,default], 2*(default), [10px,default], default:grow, [10px,default], default, [10px,default]")); //$NON-NLS-1$

		{
			scrollPane1.setViewportView(definitionTable);
		}
		add(scrollPane1, CC.xywh(2, 8, 3, 1, CC.FILL, CC.FILL));

		tableImage.setText("Table Image"); //$NON-NLS-1$
		add(tableImage, CC.xy(4, 3));

		label1.setText("Table Image"); //$NON-NLS-1$
		add(label1, CC.xy(4, 2));

		{
			panel1.setLayout(new FormLayout(
				"default, [10px,default], default:grow, 2*(default)", //$NON-NLS-1$
				"default, [10px,default], default")); //$NON-NLS-1$

			label2.setText("Name:"); //$NON-NLS-1$
			panel1.add(label2, CC.xy(1, 1));
			panel1.add(tableName, CC.xywh(3, 1, 3, 1));

			label3.setText("Roll:"); //$NON-NLS-1$
			panel1.add(label3, CC.xy(1, 3));
			panel1.add(defaultTableRoll, CC.xy(3, 3));

			label4.setText("(Leave blank for default)"); //$NON-NLS-1$
			label4.setFont(new Font("Dialog", Font.ITALIC, 11)); //$NON-NLS-1$
			panel1.add(label4, CC.xy(5, 3));
		}
		add(panel1, CC.xywh(2, 2, 1, 2));

		{
			panel2.setLayout(new FormLayout(
				"default, [10px,default], default", //$NON-NLS-1$
				"default")); //$NON-NLS-1$

			acceptButton.setText("Accept"); //$NON-NLS-1$
			acceptButton.setActionCommand("Update"); //$NON-NLS-1$
			panel2.add(acceptButton, CC.xy(1, 1));

			cancelButton.setText("Cancel"); //$NON-NLS-1$
			cancelButton.setActionCommand("Close"); //$NON-NLS-1$
			panel2.add(cancelButton, CC.xy(3, 1));
		}
		add(panel2, CC.xywh(2, 10, 3, 1, CC.RIGHT, CC.DEFAULT));

		visibleCheckbox.setText("Show Table to Players"); //$NON-NLS-1$
		visibleCheckbox.setActionCommand("Hide Table from Players"); //$NON-NLS-1$
		add(visibleCheckbox, CC.xy(2, 5));

		allowLookupCheckbox.setText("Allow Lookup by Players"); //$NON-NLS-1$
		allowLookupCheckbox.setActionCommand("Hide Table from Players"); //$NON-NLS-1$
		add(allowLookupCheckbox, CC.xy(2, 6));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
