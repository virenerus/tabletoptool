package com.t3.client.ui.forms;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.jeta.forms.components.colors.JETAColorWell;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Virenerus
 */
public abstract class EditLabelBaseDialog extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JTextField _label;
	protected JLabel label1;
	protected JCheckBox _showBackground;
	protected JPanel panel1;
	protected JButton okButton;
	protected JButton cancelButton;
	protected JLabel label2;
	protected JETAColorWell foregroundColor;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public EditLabelBaseDialog() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		_label = new JTextField();
		label1 = new JLabel();
		_showBackground = new JCheckBox();
		panel1 = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		label2 = new JLabel();
		foregroundColor = new JETAColorWell();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"12dlu, 3*(default), default:grow, [10px,default]", //$NON-NLS-1$
			"4*([10px,default], default), [10px,default]")); //$NON-NLS-1$
		add(_label, CC.xywh(2, 2, 4, 1));

		label1.setText("Show Background:"); //$NON-NLS-1$
		add(label1, CC.xy(2, 4));
		add(_showBackground, CC.xy(4, 4));

		{
			panel1.setLayout(new FormLayout(
				"default, [10px,default], default", //$NON-NLS-1$
				"default")); //$NON-NLS-1$
			((FormLayout)panel1.getLayout()).setColumnGroups(new int[][] {{1, 3}});

			okButton.setText("OK"); //$NON-NLS-1$
			okButton.setActionCommand("OK"); //$NON-NLS-1$
			panel1.add(okButton, CC.xy(1, 1));

			cancelButton.setText("Cancel"); //$NON-NLS-1$
			cancelButton.setActionCommand("Cancel"); //$NON-NLS-1$
			panel1.add(cancelButton, CC.xy(3, 1));
		}
		add(panel1, CC.xywh(2, 8, 4, 1, CC.RIGHT, CC.DEFAULT));

		label2.setText("Foreground:"); //$NON-NLS-1$
		add(label2, CC.xy(2, 6));
		add(foregroundColor, CC.xy(4, 6));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
