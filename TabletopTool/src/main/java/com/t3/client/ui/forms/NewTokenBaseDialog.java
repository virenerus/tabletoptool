package com.t3.client.ui.forms;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import com.jeta.forms.components.image.ImageComponent;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Virenerus
 */
public abstract class NewTokenBaseDialog extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JLabel label1;
	protected JLabel label2;
	protected JLabel label3;
	protected JTextField _name;
	protected JTextField _gmName;
	protected JPanel panel1;
	protected JRadioButton _type_NPC;
	protected JRadioButton _type_PC;
	protected JPanel panel2;
	protected JButton okButton;
	protected JButton cancelButton;
	protected ImageComponent tokenIcon;
	protected JCheckBox showDialogCheckbox;
	protected JLabel label4;
	protected JCheckBox _visible;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public NewTokenBaseDialog() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		_name = new JTextField();
		_gmName = new JTextField();
		panel1 = new JPanel();
		_type_NPC = new JRadioButton();
		_type_PC = new JRadioButton();
		panel2 = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		tokenIcon = new ImageComponent();
		showDialogCheckbox = new JCheckBox();
		label4 = new JLabel();
		_visible = new JCheckBox();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"[10px,default], 4*(default), left:default, [10px,default]", //$NON-NLS-1$
			"[10px,default], 3*(default), 3*([10px,default], default), default, [10px,default]")); //$NON-NLS-1$

		label1.setText("Name:"); //$NON-NLS-1$
		add(label1, CC.xy(4, 2));

		label2.setText("GM Name:"); //$NON-NLS-1$
		add(label2, CC.xy(4, 4));

		label3.setText("Type:"); //$NON-NLS-1$
		add(label3, CC.xy(4, 6));

		_name.setColumns(30);
		add(_name, CC.xy(6, 2));

		_gmName.setColumns(30);
		add(_gmName, CC.xy(6, 4));

		{
			panel1.setLayout(new FormLayout(
				"default, [10px,default], default", //$NON-NLS-1$
				"default")); //$NON-NLS-1$

			_type_NPC.setText("NPC"); //$NON-NLS-1$
			_type_NPC.setActionCommand("NPC"); //$NON-NLS-1$
			panel1.add(_type_NPC, CC.xy(1, 1));

			_type_PC.setText("PC"); //$NON-NLS-1$
			_type_PC.setActionCommand("PC"); //$NON-NLS-1$
			panel1.add(_type_PC, CC.xy(3, 1));
		}
		add(panel1, CC.xy(6, 6));

		{
			panel2.setLayout(new FormLayout(
				"default, [10px,default], default", //$NON-NLS-1$
				"default")); //$NON-NLS-1$

			okButton.setText("OK"); //$NON-NLS-1$
			okButton.setActionCommand("OK"); //$NON-NLS-1$
			panel2.add(okButton, CC.xy(1, 1));

			cancelButton.setText("Cancel"); //$NON-NLS-1$
			cancelButton.setActionCommand("Cancel"); //$NON-NLS-1$
			panel2.add(cancelButton, CC.xy(3, 1));
		}
		add(panel2, CC.xywh(4, 10, 3, 1, CC.RIGHT, CC.DEFAULT));
		add(tokenIcon, CC.xywh(2, 2, 1, 3, CC.DEFAULT, CC.TOP));

		showDialogCheckbox.setText("Show this dialog"); //$NON-NLS-1$
		showDialogCheckbox.setSelected(true);
		showDialogCheckbox.setActionCommand("Show this dialog"); //$NON-NLS-1$
		add(showDialogCheckbox, CC.xywh(2, 11, 5, 1));

		label4.setText("Visible:"); //$NON-NLS-1$
		add(label4, CC.xy(4, 8));
		add(_visible, CC.xy(6, 8));

		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(_type_NPC);
		buttonGroup1.add(_type_PC);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
