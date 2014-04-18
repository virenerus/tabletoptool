package com.t3.client.ui.forms;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import com.jeta.forms.components.image.ImageComponent;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Virenerus
 */
public abstract class TokenNotesBaseDialog extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JLabel label1;
	protected JPanel panel1;
	protected JButton cancelButton;
	protected JButton okButton;
	protected JPanel statesPanel;
	protected JPanel panel2;
	protected ImageComponent tokenIcon;
	protected JPanel panel3;
	protected JTextField tokenName;
	protected JLabel label2;
	protected JLabel tokenGMNameLabel;
	protected JTextField tokenGMName;
	protected JPanel panel4;
	protected JLabel label3;
	protected JComboBox shape;
	protected JLabel label4;
	protected JComboBox size;
	protected JLabel label5;
	protected JCheckBox snapToGrid;
	protected JLabel visibleLabel;
	protected JCheckBox visible;
	protected JLabel gmNotesLabel;
	protected JScrollPane scrollPane1;
	protected JTextArea notes;
	protected JScrollPane scrollPane2;
	protected JTextArea gmNotes;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public TokenNotesBaseDialog() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		label1 = new JLabel();
		panel1 = new JPanel();
		cancelButton = new JButton();
		okButton = new JButton();
		statesPanel = new JPanel();
		panel2 = new JPanel();
		tokenIcon = new ImageComponent();
		panel3 = new JPanel();
		tokenName = new JTextField();
		label2 = new JLabel();
		tokenGMNameLabel = new JLabel();
		tokenGMName = new JTextField();
		panel4 = new JPanel();
		label3 = new JLabel();
		shape = new JComboBox();
		label4 = new JLabel();
		size = new JComboBox();
		label5 = new JLabel();
		snapToGrid = new JCheckBox();
		visibleLabel = new JLabel();
		visible = new JCheckBox();
		gmNotesLabel = new JLabel();
		scrollPane1 = new JScrollPane();
		notes = new JTextArea();
		scrollPane2 = new JScrollPane();
		gmNotes = new JTextArea();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"$ugap, 3*(default), default:grow, $ugap", //$NON-NLS-1$
			"[10px,default], 2*(default, $nlgap), 2*(fill:default:grow, [10px,default]), default, [10px,default]")); //$NON-NLS-1$

		label1.setText("Notes:"); //$NON-NLS-1$
		add(label1, CC.xy(2, 6, CC.DEFAULT, CC.TOP));

		{
			panel1.setLayout(new FormLayout(
				"default, $ugap, default", //$NON-NLS-1$
				"default")); //$NON-NLS-1$

			cancelButton.setText("Close"); //$NON-NLS-1$
			cancelButton.setActionCommand("Cancel"); //$NON-NLS-1$
			panel1.add(cancelButton, CC.xy(3, 1));

			okButton.setText("OK"); //$NON-NLS-1$
			okButton.setActionCommand("OK"); //$NON-NLS-1$
			panel1.add(okButton, CC.xy(1, 1));
		}
		add(panel1, CC.xywh(2, 10, 4, 1, CC.RIGHT, CC.DEFAULT));

		{
			statesPanel.setBorder(new TitledBorder(null, "States", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, UIManager.getColor("TitledBorder.titleColor"))); //$NON-NLS-1$ //$NON-NLS-2$
			statesPanel.setLayout(new FormLayout(
				"6*([10px,default])", //$NON-NLS-1$
				"[10px,default]")); //$NON-NLS-1$
		}
		add(statesPanel, CC.xywh(2, 4, 4, 1));

		{
			panel2.setLayout(new FormLayout(
				"default, $rgap, default", //$NON-NLS-1$
				"default")); //$NON-NLS-1$
			panel2.add(tokenIcon, CC.xy(1, 1));

			{
				panel3.setLayout(new FormLayout(
					"default, [10px,default], left:default, [10px,default]", //$NON-NLS-1$
					"2*(default, [10px,default]), default:grow")); //$NON-NLS-1$

				tokenName.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
				tokenName.setColumns(30);
				panel3.add(tokenName, CC.xy(3, 1));

				label2.setText("Name:"); //$NON-NLS-1$
				panel3.add(label2, CC.xy(1, 1));

				tokenGMNameLabel.setText("GM Name:"); //$NON-NLS-1$
				panel3.add(tokenGMNameLabel, CC.xy(1, 3));

				tokenGMName.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
				tokenGMName.setColumns(30);
				panel3.add(tokenGMName, CC.xy(3, 3));

				{
					panel4.setLayout(new FormLayout(
						"3*(default, [10px,default]), default", //$NON-NLS-1$
						"default, [10px,default], default")); //$NON-NLS-1$

					label3.setText("Shape:"); //$NON-NLS-1$
					panel4.add(label3, CC.xy(1, 1));
					panel4.add(shape, CC.xy(3, 1));

					label4.setText("Size:"); //$NON-NLS-1$
					panel4.add(label4, CC.xy(1, 3));
					panel4.add(size, CC.xy(3, 3));

					label5.setText("Snap To Grid:"); //$NON-NLS-1$
					panel4.add(label5, CC.xy(5, 1));
					panel4.add(snapToGrid, CC.xy(7, 1));

					visibleLabel.setText("Visible to players:"); //$NON-NLS-1$
					panel4.add(visibleLabel, CC.xy(5, 3));
					panel4.add(visible, CC.xy(7, 3));
				}
				panel3.add(panel4, CC.xy(3, 5, CC.FILL, CC.FILL));
			}
			panel2.add(panel3, CC.xy(3, 1));
		}
		add(panel2, CC.xywh(2, 2, 4, 1));

		gmNotesLabel.setText("GM Notes:"); //$NON-NLS-1$
		add(gmNotesLabel, CC.xy(2, 8, CC.DEFAULT, CC.TOP));

		{

			notes.setLineWrap(true);
			notes.setWrapStyleWord(true);
			scrollPane1.setViewportView(notes);
		}
		add(scrollPane1, CC.xywh(4, 6, 2, 1));

		{

			gmNotes.setLineWrap(true);
			gmNotes.setWrapStyleWord(true);
			scrollPane2.setViewportView(gmNotes);
		}
		add(scrollPane2, CC.xywh(4, 8, 2, 1));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
