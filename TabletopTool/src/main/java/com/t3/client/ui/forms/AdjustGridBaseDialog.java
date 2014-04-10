/*
 * Created by JFormDesigner on Wed Apr 09 14:22:35 CEST 2014
 */

package com.t3.client.ui.forms;

import java.awt.Container;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.LineBorder;

import com.jidesoft.combobox.ColorComboBox;
import com.t3.client.ui.adjustgrid.AdjustGridPanel;

/**
 * @author Manuel Hegner
 */
public abstract class AdjustGridBaseDialog extends JDialog {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JLabel label1;
	protected JTextField gridSize;
	protected AdjustGridPanel adjustGridPanel;
	protected JLabel label2;
	protected JSlider zoom;
	protected JLabel label3;
	protected JLabel label4;
	protected JTextField yOffset;
	protected JLabel label5;
	protected JTextField xOffset;
	protected ColorComboBox color;
	protected JButton cancelButton;
	protected JButton okButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public AdjustGridBaseDialog() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		label1 = new JLabel();
		gridSize = new JTextField();
		adjustGridPanel = new AdjustGridPanel();
		label2 = new JLabel();
		zoom = new JSlider();
		label3 = new JLabel();
		label4 = new JLabel();
		yOffset = new JTextField();
		label5 = new JLabel();
		xOffset = new JTextField();
		color = new ColorComboBox();
		cancelButton = new JButton();
		okButton = new JButton();

		//======== this ========
		setResizable(false);
		setTitle("Adjust Grid"); //$NON-NLS-1$
		Container contentPane = getContentPane();

		//---- label1 ----
		label1.setText("Grid Size (pixels):"); //$NON-NLS-1$

		//---- gridSize ----
		gridSize.setColumns(6);

		//---- adjustGridPanel ----
		adjustGridPanel.setBorder(LineBorder.createBlackLineBorder());

		//---- label2 ----
		label2.setText("Zoom:"); //$NON-NLS-1$

		//---- zoom ----
		zoom.setMajorTickSpacing(20);
		zoom.setMaximum(50);

		//---- label3 ----
		label3.setText("Color:"); //$NON-NLS-1$

		//---- label4 ----
		label4.setText("Y Offset:"); //$NON-NLS-1$

		//---- yOffset ----
		yOffset.setColumns(6);

		//---- label5 ----
		label5.setText("X Offset:"); //$NON-NLS-1$
		label5.setLabelFor(xOffset);

		//---- xOffset ----
		xOffset.setColumns(6);

		//---- color ----
		color.setUseAlphaColorButtons(false);

		//---- cancelButton ----
		cancelButton.setText("Cancel"); //$NON-NLS-1$
		cancelButton.setActionCommand("Cancel"); //$NON-NLS-1$

		//---- okButton ----
		okButton.setText("OK"); //$NON-NLS-1$
		okButton.setActionCommand("OK"); //$NON-NLS-1$

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(10, 10, 10)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addComponent(adjustGridPanel, GroupLayout.PREFERRED_SIZE, 490, GroupLayout.PREFERRED_SIZE)
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addComponent(label1)
							.addGap(18, 18, 18)
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(gridSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18, 18, 18)
									.addComponent(label4)
									.addGap(18, 18, 18)
									.addComponent(yOffset, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(label2))
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(label5)
									.addGap(18, 18, 18)
									.addComponent(xOffset, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(32, 32, 32)
									.addComponent(label3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
							.addGap(18, 18, 18)
							.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(zoom, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
								.addComponent(color, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())))
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(okButton)
					.addGap(18, 18, 18)
					.addComponent(cancelButton)
					.addContainerGap())
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(10, 10, 10)
					.addComponent(adjustGridPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(13, 13, 13)
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(yOffset, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label4)
							.addComponent(gridSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label1)
							.addComponent(label2))
						.addComponent(zoom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(xOffset, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label5)
						.addComponent(color, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label3))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(cancelButton)
						.addComponent(okButton))
					.addContainerGap())
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
