package com.t3.client.ui.forms;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import com.jeta.forms.components.colors.JETAColorWell;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Virenerus
 */
public abstract class AdjustGridBaseDialog extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JLabel label1;
	protected JPanel panel1;
	protected JButton okButton;
	protected JButton cancelButton;
	protected JTextField gridSize;
	protected JLabel adjustGridPanel;
	protected JLabel label2;
	protected JSlider zoom;
	protected JLabel label3;
	protected JPanel panel2;
	protected JETAColorWell color;
	protected JLabel label4;
	protected JTextField yOffset;
	protected JLabel label5;
	protected JTextField xOffset;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public AdjustGridBaseDialog() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		label1 = new JLabel();
		panel1 = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		gridSize = new JTextField();
		adjustGridPanel = new JLabel();
		label2 = new JLabel();
		zoom = new JSlider();
		label3 = new JLabel();
		panel2 = new JPanel();
		color = new JETAColorWell();
		label4 = new JLabel();
		yOffset = new JTextField();
		label5 = new JLabel();
		xOffset = new JTextField();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"[10px,default], 10*(default), 75dlu, default:grow, [10px,default]", //$NON-NLS-1$
			"[10px,default], default:grow, 2*([10px,default], default), 2*([10px,default]), bottom:default, [10px,default]")); //$NON-NLS-1$

		label1.setText("Grid Size (pixels):"); //$NON-NLS-1$
		add(label1, CC.xy(2, 4));

		{
			panel1.setLayout(new FormLayout(
				"2*([10px,default], default), [10px,default]", //$NON-NLS-1$
				"default")); //$NON-NLS-1$

			okButton.setText("OK"); //$NON-NLS-1$
			okButton.setActionCommand("OK"); //$NON-NLS-1$
			panel1.add(okButton, CC.xy(2, 1));

			cancelButton.setText("Cancel"); //$NON-NLS-1$
			cancelButton.setActionCommand("Cancel"); //$NON-NLS-1$
			panel1.add(cancelButton, CC.xy(4, 1));
		}
		add(panel1, CC.xywh(2, 9, 12, 1, CC.RIGHT, CC.DEFAULT));

		gridSize.setColumns(6);
		add(gridSize, CC.xy(4, 4));

		adjustGridPanel.setText("AdjustGridPanel PLACEHOLDER"); //$NON-NLS-1$
		adjustGridPanel.setBorder(LineBorder.createBlackLineBorder());
		add(adjustGridPanel, CC.xywh(2, 2, 12, 1, CC.FILL, CC.FILL));

		label2.setText("Zoom:"); //$NON-NLS-1$
		add(label2, CC.xy(10, 4));

		zoom.setMajorTickSpacing(20);
		zoom.setMaximum(50);
		add(zoom, CC.xy(12, 4));

		label3.setText("Color:"); //$NON-NLS-1$
		add(label3, CC.xy(10, 6));

		{
			panel2.setLayout(new FormLayout(
				"default", //$NON-NLS-1$
				"default")); //$NON-NLS-1$
			panel2.add(color, CC.xy(1, 1));
		}
		add(panel2, CC.xy(12, 6));

		label4.setText("Y Offset:"); //$NON-NLS-1$
		add(label4, CC.xy(6, 4));

		yOffset.setColumns(6);
		add(yOffset, CC.xy(8, 4));

		label5.setText("X Offset:"); //$NON-NLS-1$
		add(label5, CC.xy(6, 6));

		xOffset.setColumns(6);
		add(xOffset, CC.xy(8, 6));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
