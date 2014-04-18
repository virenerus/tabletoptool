package com.t3.client.ui.forms;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Virenerus
 */
public abstract class AdjustBoardControlPanel extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JLabel snapTitle;
	protected JLabel label1;
	protected JLabel label2;
	protected JTextField offsetX;
	protected JTextField offsetY;
	protected JButton closeButton;
	protected JRadioButton snapNone;
	protected JRadioButton snapGrid;
	protected JRadioButton snapTile;
	protected JLabel label3;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public AdjustBoardControlPanel() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		snapTitle = new JLabel();
		label1 = new JLabel();
		label2 = new JLabel();
		offsetX = new JTextField();
		offsetY = new JTextField();
		closeButton = new JButton();
		snapNone = new JRadioButton();
		snapGrid = new JRadioButton();
		snapTile = new JRadioButton();
		label3 = new JLabel();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"[10px,default], 3*(default), 21dlu, [10px,default]", //$NON-NLS-1$
			"$nlgap, default, $rgap, 3*(20px), 2*($rgap, default), 16px, $rgap, default, $rgap")); //$NON-NLS-1$

		snapTitle.setText("Snap to:"); //$NON-NLS-1$
		add(snapTitle, CC.xy(2, 4));

		label1.setText("Offset X:"); //$NON-NLS-1$
		add(label1, CC.xy(2, 8));

		label2.setText("Offset Y:"); //$NON-NLS-1$
		add(label2, CC.xy(2, 10));

		offsetX.setColumns(6);
		add(offsetX, CC.xywh(4, 8, 2, 1));

		offsetY.setColumns(6);
		add(offsetY, CC.xywh(4, 10, 2, 1));

		closeButton.setText("Close"); //$NON-NLS-1$
		closeButton.setActionCommand("Close"); //$NON-NLS-1$
		add(closeButton, CC.xywh(2, 13, 4, 1));

		snapNone.setText("None"); //$NON-NLS-1$
		snapNone.setActionCommand("None"); //$NON-NLS-1$
		snapNone.setToolTipText("no snap"); //$NON-NLS-1$
		add(snapNone, CC.xy(4, 4));

		snapGrid.setText("Grid"); //$NON-NLS-1$
		snapGrid.setActionCommand("None"); //$NON-NLS-1$
		snapGrid.setToolTipText("the token grid"); //$NON-NLS-1$
		add(snapGrid, CC.xy(4, 5));

		snapTile.setText("Tile"); //$NON-NLS-1$
		snapTile.setSelected(true);
		snapTile.setActionCommand("None"); //$NON-NLS-1$
		snapTile.setToolTipText("the repeating background texture"); //$NON-NLS-1$
		add(snapTile, CC.xy(4, 6));

		label3.setHorizontalAlignment(SwingConstants.CENTER);
		label3.setText("Adjust Board Position"); //$NON-NLS-1$
		label3.setBorder(LineBorder.createBlackLineBorder());
		add(label3, CC.xywh(2, 2, 4, 1));

		ButtonGroup buttonGroupsnap = new ButtonGroup();
		buttonGroupsnap.add(snapNone);
		buttonGroupsnap.add(snapGrid);
		buttonGroupsnap.add(snapTile);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
