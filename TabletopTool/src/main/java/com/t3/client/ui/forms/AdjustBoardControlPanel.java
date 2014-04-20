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
public class AdjustBoardControlPanel extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JLabel snapTitle;
	protected JLabel label1;
	protected JLabel label2;
	protected JTextField boardPositionXTextField;
	protected JTextField boardPositionYTextField;
	protected JButton closeButton;
	protected JRadioButton snapNoneButton;
	protected JRadioButton snapGridButton;
	protected JRadioButton snapTileButton;
	protected JLabel label3;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public AdjustBoardControlPanel() {
		initComponents();
	}

	public JLabel getSnapTitle() {
		return snapTitle;
	}

	public JLabel getLabel1() {
		return label1;
	}

	public JLabel getLabel2() {
		return label2;
	}

	public JTextField getBoardPositionXTextField() {
		return boardPositionXTextField;
	}

	public JTextField getBoardPositionYTextField() {
		return boardPositionYTextField;
	}

	public JButton getCloseButton() {
		return closeButton;
	}

	public JRadioButton getSnapNoneButton() {
		return snapNoneButton;
	}

	public JRadioButton getSnapGridButton() {
		return snapGridButton;
	}

	public JRadioButton getSnapTileButton() {
		return snapTileButton;
	}

	public JLabel getLabel3() {
		return label3;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		snapTitle = new JLabel();
		label1 = new JLabel();
		label2 = new JLabel();
		boardPositionXTextField = new JTextField();
		boardPositionYTextField = new JTextField();
		closeButton = new JButton();
		snapNoneButton = new JRadioButton();
		snapGridButton = new JRadioButton();
		snapTileButton = new JRadioButton();
		label3 = new JLabel();
		
		setLayout(new FormLayout(
			"[10px,default], 3*(default), 21dlu, [10px,default]", //$NON-NLS-1$
			"$nlgap, default, $rgap, 3*(20px), 2*($rgap, default), 16px, $rgap, default, $rgap")); //$NON-NLS-1$

		snapTitle.setText("Snap to:"); //$NON-NLS-1$
		add(snapTitle, CC.xy(2, 4));

		label1.setText("Offset X:"); //$NON-NLS-1$
		add(label1, CC.xy(2, 8));

		label2.setText("Offset Y:"); //$NON-NLS-1$
		add(label2, CC.xy(2, 10));

		boardPositionXTextField.setColumns(6);
		add(boardPositionXTextField, CC.xywh(4, 8, 2, 1));

		boardPositionYTextField.setColumns(6);
		add(boardPositionYTextField, CC.xywh(4, 10, 2, 1));

		closeButton.setText("Close"); //$NON-NLS-1$
		closeButton.setActionCommand("Close"); //$NON-NLS-1$
		add(closeButton, CC.xywh(2, 13, 4, 1));

		snapNoneButton.setText("None"); //$NON-NLS-1$
		snapNoneButton.setActionCommand("None"); //$NON-NLS-1$
		snapNoneButton.setToolTipText("no snap"); //$NON-NLS-1$
		add(snapNoneButton, CC.xy(4, 4));

		snapGridButton.setText("Grid"); //$NON-NLS-1$
		snapGridButton.setActionCommand("None"); //$NON-NLS-1$
		snapGridButton.setToolTipText("the token grid"); //$NON-NLS-1$
		add(snapGridButton, CC.xy(4, 5));

		snapTileButton.setText("Tile"); //$NON-NLS-1$
		snapTileButton.setSelected(true);
		snapTileButton.setActionCommand("None"); //$NON-NLS-1$
		snapTileButton.setToolTipText("the repeating background texture"); //$NON-NLS-1$
		add(snapTileButton, CC.xy(4, 6));

		label3.setHorizontalAlignment(SwingConstants.CENTER);
		label3.setText("Adjust Board Position"); //$NON-NLS-1$
		label3.setBorder(LineBorder.createBlackLineBorder());
		add(label3, CC.xywh(2, 2, 4, 1));

		ButtonGroup buttonGroupsnap = new ButtonGroup();
		buttonGroupsnap.add(snapNoneButton);
		buttonGroupsnap.add(snapGridButton);
		buttonGroupsnap.add(snapTileButton);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
