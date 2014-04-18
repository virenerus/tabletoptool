package com.t3.client.ui.forms;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Virenerus
 */
public abstract class LookupTablePanel extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JPanel buttonPanel;
	protected JButton newButton;
	protected JButton editButton;
	protected JButton deleteButton;
	protected JButton duplicateButton;
	protected JButton importButton;
	protected JButton exportButton;
	protected JLabel imagePanel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public LookupTablePanel() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		buttonPanel = new JPanel();
		newButton = new JButton();
		editButton = new JButton();
		deleteButton = new JButton();
		duplicateButton = new JButton();
		importButton = new JButton();
		exportButton = new JButton();
		imagePanel = new JLabel();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"[10px,default], default:grow, [10px,default]", //$NON-NLS-1$
			"[10px,default], default, [10px,default], default:grow, [10px,default]")); //$NON-NLS-1$

		{
			buttonPanel.setLayout(new FormLayout(
				"6*(default, [10px,default])", //$NON-NLS-1$
				"default")); //$NON-NLS-1$

			newButton.setIcon(new ImageIcon(getClass().getResource("com/t3/client/image/add.png"))); //$NON-NLS-1$
			newButton.setActionCommand("New"); //$NON-NLS-1$
			newButton.setToolTipText("Create a new lookup table"); //$NON-NLS-1$
			buttonPanel.add(newButton, CC.xy(1, 1));

			editButton.setIcon(new ImageIcon(getClass().getResource("com/t3/client/image/pencil.png"))); //$NON-NLS-1$
			editButton.setActionCommand("Edit"); //$NON-NLS-1$
			editButton.setToolTipText("Edit selected table"); //$NON-NLS-1$
			buttonPanel.add(editButton, CC.xy(3, 1));

			deleteButton.setIcon(new ImageIcon(getClass().getResource("com/t3/client/image/delete.png"))); //$NON-NLS-1$
			deleteButton.setActionCommand("Delete"); //$NON-NLS-1$
			deleteButton.setToolTipText("Delete table"); //$NON-NLS-1$
			buttonPanel.add(deleteButton, CC.xy(5, 1));

			duplicateButton.setIcon(new ImageIcon(getClass().getResource("com/t3/client/image/page_copy.png"))); //$NON-NLS-1$
			duplicateButton.setActionCommand("Duplicate"); //$NON-NLS-1$
			duplicateButton.setToolTipText("Duplicate selected table"); //$NON-NLS-1$
			buttonPanel.add(duplicateButton, CC.xy(7, 1));

			importButton.setText("Import"); //$NON-NLS-1$
			importButton.setActionCommand("Import"); //$NON-NLS-1$
			buttonPanel.add(importButton, CC.xy(9, 1));

			exportButton.setText("Export"); //$NON-NLS-1$
			exportButton.setActionCommand("Export"); //$NON-NLS-1$
			buttonPanel.add(exportButton, CC.xy(11, 1));
		}
		add(buttonPanel, CC.xy(2, 2));

		imagePanel.setText("imagePanel"); //$NON-NLS-1$
		add(imagePanel, CC.xy(2, 4, CC.FILL, CC.FILL));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
