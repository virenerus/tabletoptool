package com.t3.client.ui.forms;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
public abstract class MapPropertiesBaseDialog extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JPanel panel1;
	protected JLabel label1;
	protected JTextField name;
	protected JLabel label2;
	protected JLabel label3;
	protected JTextField distance;
	protected JLabel label4;
	protected JTextField pixelsPerCell;
	protected JPanel panel2;
	protected JRadioButton squareRadio;
	protected ImageComponent imageComponent1;
	protected JPanel panel3;
	protected JRadioButton hexVertRadio;
	protected ImageComponent imageComponent2;
	protected JPanel panel4;
	protected JRadioButton hexHoriRadio;
	protected ImageComponent imageComponent3;
	protected JPanel panel5;
	protected JRadioButton noGridRadio;
	protected ImageComponent imageComponent4;
	protected JLabel label5;
	protected JTextField defaultVision;
	protected JPanel panel6;
	protected JButton okButton;
	protected JButton cancelButton;
	protected JPanel previewPanel;
	protected JLabel mapPreviewPanel;
	protected JButton backgroundButton;
	protected JButton mapButton;
	protected JButton fogButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public MapPropertiesBaseDialog() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		panel1 = new JPanel();
		label1 = new JLabel();
		name = new JTextField();
		label2 = new JLabel();
		label3 = new JLabel();
		distance = new JTextField();
		label4 = new JLabel();
		pixelsPerCell = new JTextField();
		panel2 = new JPanel();
		squareRadio = new JRadioButton();
		imageComponent1 = new ImageComponent();
		panel3 = new JPanel();
		hexVertRadio = new JRadioButton();
		imageComponent2 = new ImageComponent();
		panel4 = new JPanel();
		hexHoriRadio = new JRadioButton();
		imageComponent3 = new ImageComponent();
		panel5 = new JPanel();
		noGridRadio = new JRadioButton();
		imageComponent4 = new ImageComponent();
		label5 = new JLabel();
		defaultVision = new JTextField();
		panel6 = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		previewPanel = new JPanel();
		mapPreviewPanel = new JLabel();
		backgroundButton = new JButton();
		mapButton = new JButton();
		fogButton = new JButton();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"[10px,default], default:grow, 2*(default), [10px,default]", //$NON-NLS-1$
			"[10px,default], fill:default, [10px,default], default, [10px,default]")); //$NON-NLS-1$

		{
			panel1.setLayout(new FormLayout(
				"default, [10px,default], left:pref, left:[pref,100dlu], 4*(default), left:pref:grow", //$NON-NLS-1$
				"4*(default, [10px,default]), default")); //$NON-NLS-1$

			label1.setText("Name:"); //$NON-NLS-1$
			panel1.add(label1, CC.xy(1, 1));

			name.setColumns(30);
			panel1.add(name, CC.xywh(3, 1, 7, 1));

			label2.setText("Cell Type:"); //$NON-NLS-1$
			panel1.add(label2, CC.xy(1, 3));

			label3.setText("Distance per cell:"); //$NON-NLS-1$
			panel1.add(label3, CC.xy(1, 5));

			distance.setColumns(5);
			panel1.add(distance, CC.xy(3, 5));

			label4.setText("Pixels per Cell:\t"); //$NON-NLS-1$
			panel1.add(label4, CC.xy(1, 7));

			pixelsPerCell.setColumns(5);
			panel1.add(pixelsPerCell, CC.xy(3, 7));

			{
				panel2.setLayout(new FormLayout(
					"2*(default)", //$NON-NLS-1$
					"default")); //$NON-NLS-1$

				squareRadio.setActionCommand("Square"); //$NON-NLS-1$
				panel2.add(squareRadio, CC.xy(1, 1));

				imageComponent1.setIcon(new ImageIcon(getClass().getResource("com/t3/client/image/gridSquare.png"))); //$NON-NLS-1$
				panel2.add(imageComponent1, CC.xy(2, 1));
			}
			panel1.add(panel2, CC.xy(3, 3));

			{
				panel3.setLayout(new FormLayout(
					"2*(default)", //$NON-NLS-1$
					"default")); //$NON-NLS-1$

				hexVertRadio.setActionCommand("Hex"); //$NON-NLS-1$
				panel3.add(hexVertRadio, CC.xy(1, 1));

				imageComponent2.setIcon(new ImageIcon(getClass().getResource("com/t3/client/image/gridVerticalHex.png"))); //$NON-NLS-1$
				panel3.add(imageComponent2, CC.xy(2, 1));
			}
			panel1.add(panel3, CC.xy(5, 3));

			{
				panel4.setLayout(new FormLayout(
					"2*(default)", //$NON-NLS-1$
					"default")); //$NON-NLS-1$

				hexHoriRadio.setActionCommand("Horizontal Hex"); //$NON-NLS-1$
				panel4.add(hexHoriRadio, CC.xy(1, 1));

				imageComponent3.setIcon(new ImageIcon(getClass().getResource("com/t3/client/image/gridHorizontalHex.png"))); //$NON-NLS-1$
				panel4.add(imageComponent3, CC.xy(2, 1));
			}
			panel1.add(panel4, CC.xy(7, 3));

			{
				panel5.setLayout(new FormLayout(
					"2*(default)", //$NON-NLS-1$
					"default")); //$NON-NLS-1$

				noGridRadio.setActionCommand("Horizontal Hex"); //$NON-NLS-1$
				panel5.add(noGridRadio, CC.xy(1, 1));

				imageComponent4.setIcon(new ImageIcon(getClass().getResource("com/t3/image/icons/cross.png"))); //$NON-NLS-1$
				imageComponent4.setToolTipText("No Grid"); //$NON-NLS-1$
				panel5.add(imageComponent4, CC.xy(2, 1));
			}
			panel1.add(panel5, CC.xy(9, 3));

			label5.setText("Vision Distance:"); //$NON-NLS-1$
			panel1.add(label5, CC.xy(1, 9));

			defaultVision.setColumns(5);
			panel1.add(defaultVision, CC.xy(3, 9));
		}
		add(panel1, CC.xy(2, 2, CC.DEFAULT, CC.TOP));

		{
			panel6.setLayout(new FormLayout(
				"default, [10px,default], default", //$NON-NLS-1$
				"default")); //$NON-NLS-1$

			okButton.setText("OK"); //$NON-NLS-1$
			okButton.setActionCommand("OK"); //$NON-NLS-1$
			panel6.add(okButton, CC.xy(1, 1));

			cancelButton.setText("Cancel"); //$NON-NLS-1$
			cancelButton.setActionCommand("Cancel"); //$NON-NLS-1$
			panel6.add(cancelButton, CC.xy(3, 1));
		}
		add(panel6, CC.xywh(2, 4, 3, 1, CC.RIGHT, CC.DEFAULT));

		{
			previewPanel.setLayout(new FormLayout(
				"default, [10px,default], default", //$NON-NLS-1$
				"5*(default), default:grow")); //$NON-NLS-1$

			mapPreviewPanel.setText("Preview Panel"); //$NON-NLS-1$
			previewPanel.add(mapPreviewPanel, CC.xywh(1, 1, 1, 6));

			backgroundButton.setText("Background"); //$NON-NLS-1$
			backgroundButton.setActionCommand("Background"); //$NON-NLS-1$
			previewPanel.add(backgroundButton, CC.xy(3, 1));

			mapButton.setText("Map"); //$NON-NLS-1$
			mapButton.setActionCommand("JButton"); //$NON-NLS-1$
			previewPanel.add(mapButton, CC.xy(3, 3));

			fogButton.setText("Fog"); //$NON-NLS-1$
			fogButton.setActionCommand("JButton"); //$NON-NLS-1$
			previewPanel.add(fogButton, CC.xy(3, 5));
		}
		add(previewPanel, CC.xy(4, 2));

		ButtonGroup buttonGroup2 = new ButtonGroup();
		buttonGroup2.add(squareRadio);
		buttonGroup2.add(hexVertRadio);
		buttonGroup2.add(hexHoriRadio);
		buttonGroup2.add(noGridRadio);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
