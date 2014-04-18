package com.t3.client.ui.forms;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Virenerus
 */
public abstract class TokenPropertiesManagementPanel extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JLabel label1;
	protected JTextField tokenTypeName;
	protected JLabel tokenProperties;
	protected JPanel panel1;
	protected JButton newButton;
	protected JPanel panel2;
	protected JButton updateButton;
	protected JButton revertButton;
	protected JButton addRowButton;
	protected JScrollPane scrollPane1;
	protected JList tokenTypeList;
	protected JLabel label2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public TokenPropertiesManagementPanel() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		label1 = new JLabel();
		tokenTypeName = new JTextField();
		tokenProperties = new JLabel();
		panel1 = new JPanel();
		newButton = new JButton();
		panel2 = new JPanel();
		updateButton = new JButton();
		revertButton = new JButton();
		addRowButton = new JButton();
		scrollPane1 = new JScrollPane();
		tokenTypeList = new JList();
		label2 = new JLabel();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"2*([10px,default], default), default:grow, default, [10px,default]", //$NON-NLS-1$
			"[10px,default], default, [10px,default], default:grow, [10px,default], default, [10px,default]")); //$NON-NLS-1$

		label1.setText("Name:"); //$NON-NLS-1$
		add(label1, CC.xy(4, 2));
		add(tokenTypeName, CC.xywh(5, 2, 2, 1));

		tokenProperties.setText("JLabel"); //$NON-NLS-1$
		add(tokenProperties, CC.xywh(4, 4, 3, 1));

		{
			panel1.setLayout(new FormLayout(
				"default", //$NON-NLS-1$
				"top:default")); //$NON-NLS-1$

			newButton.setText("New"); //$NON-NLS-1$
			newButton.setActionCommand("New"); //$NON-NLS-1$
			panel1.add(newButton, CC.xy(1, 1, CC.FILL, CC.TOP));
		}
		add(panel1, CC.xy(2, 6, CC.DEFAULT, CC.TOP));

		{
			panel2.setLayout(new FormLayout(
				"default, [10px,default], default", //$NON-NLS-1$
				"default")); //$NON-NLS-1$

			updateButton.setText("Update"); //$NON-NLS-1$
			updateButton.setActionCommand("Update"); //$NON-NLS-1$
			panel2.add(updateButton, CC.xy(1, 1));

			revertButton.setText("Revert"); //$NON-NLS-1$
			revertButton.setActionCommand("Revert"); //$NON-NLS-1$
			panel2.add(revertButton, CC.xy(3, 1));
		}
		add(panel2, CC.xy(6, 6, CC.CENTER, CC.TOP));

		addRowButton.setText("Add Row"); //$NON-NLS-1$
		addRowButton.setActionCommand("Add Row"); //$NON-NLS-1$
		add(addRowButton, CC.xywh(4, 6, 2, 1, CC.LEFT, CC.TOP));

		{
			scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane1.setViewportView(tokenTypeList);
		}
		add(scrollPane1, CC.xy(2, 4, CC.DEFAULT, CC.FILL));

		label2.setText("Token Type"); //$NON-NLS-1$
		add(label2, CC.xy(2, 2));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
