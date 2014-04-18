package com.t3.client.ui.forms;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Virenerus
 */
public abstract class TokenPropertiesBaseDialog extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JTabbedPane tabs;
	protected JPanel notesPanel;
	protected JScrollPane scrollPane1;
	protected JTextArea _notes;
	protected JLabel label1;
	protected JPanel gmNotesPanel;
	protected JScrollPane scrollPane2;
	protected JTextArea _GMNotes;
	protected JLabel label2;
	protected JPanel propertiesPanel;
	protected JLabel propertiesTable;
	protected JPanel panel1;
	protected JScrollPane scrollPane3;
	protected JPanel statesPanel;
	protected JPanel panel2;
	protected JScrollPane scrollPane4;
	protected JTable speechTable;
	protected JPanel panel3;
	protected JButton speechClearAllButton;
	protected JPanel ownershipPanel;
	protected JCheckBox _ownedByAll;
	protected JLabel ownershipList;
	protected JPanel panel4;
	protected JPanel panel5;
	protected JLabel label3;
	protected JLabel label4;
	protected JLabel label5;
	protected JComboBox shape;
	protected JComboBox size;
	protected JComboBox propertyTypeCombo;
	protected JLabel label6;
	protected JCheckBox _snapToGrid;
	protected JLabel visibleLabel;
	protected JCheckBox _visible;
	protected JLabel label7;
	protected JCheckBox _hasSight;
	protected JComboBox sightTypeCombo;
	protected JLabel visibleOnlyToOwnerLabel;
	protected JCheckBox _visibleOnlyToOwner;
	protected JPanel panel6;
	protected JPanel charsheetPanel;
	protected JLabel charsheet;
	protected JPanel tokenLayoutPanel;
	protected JLabel tokenLayout;
	protected JPanel portraitPanel;
	protected JLabel portrait;
	protected JLabel tokenImage;
	protected JPanel panel7;
	protected JButton cancelButton;
	protected JButton okButton;
	protected JPanel panel8;
	protected JLabel label8;
	protected JTextField _name;
	protected JLabel tokenGMNameLabel;
	protected JTextField _GMName;
	protected JComboBox _type;
	protected JLabel label9;
	protected JTextField _label;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public TokenPropertiesBaseDialog() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		tabs = new JTabbedPane();
		notesPanel = new JPanel();
		scrollPane1 = new JScrollPane();
		_notes = new JTextArea();
		label1 = new JLabel();
		gmNotesPanel = new JPanel();
		scrollPane2 = new JScrollPane();
		_GMNotes = new JTextArea();
		label2 = new JLabel();
		propertiesPanel = new JPanel();
		propertiesTable = new JLabel();
		panel1 = new JPanel();
		scrollPane3 = new JScrollPane();
		statesPanel = new JPanel();
		panel2 = new JPanel();
		scrollPane4 = new JScrollPane();
		speechTable = new JTable();
		panel3 = new JPanel();
		speechClearAllButton = new JButton();
		ownershipPanel = new JPanel();
		_ownedByAll = new JCheckBox();
		ownershipList = new JLabel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		label3 = new JLabel();
		label4 = new JLabel();
		label5 = new JLabel();
		shape = new JComboBox();
		size = new JComboBox();
		propertyTypeCombo = new JComboBox();
		label6 = new JLabel();
		_snapToGrid = new JCheckBox();
		visibleLabel = new JLabel();
		_visible = new JCheckBox();
		label7 = new JLabel();
		_hasSight = new JCheckBox();
		sightTypeCombo = new JComboBox();
		visibleOnlyToOwnerLabel = new JLabel();
		_visibleOnlyToOwner = new JCheckBox();
		panel6 = new JPanel();
		charsheetPanel = new JPanel();
		charsheet = new JLabel();
		tokenLayoutPanel = new JPanel();
		tokenLayout = new JLabel();
		portraitPanel = new JPanel();
		portrait = new JLabel();
		tokenImage = new JLabel();
		panel7 = new JPanel();
		cancelButton = new JButton();
		okButton = new JButton();
		panel8 = new JPanel();
		label8 = new JLabel();
		_name = new JTextField();
		tokenGMNameLabel = new JLabel();
		_GMName = new JTextField();
		_type = new JComboBox();
		label9 = new JLabel();
		_label = new JTextField();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"$ugap, 2*(default), default:grow, $ugap", //$NON-NLS-1$
			"[10px,default], default, [10px,default], fill:default:grow, [10px,default], default, [10px,default]")); //$NON-NLS-1$

		{

			{
				notesPanel.setLayout(new FormLayout(
					"[10px,default], default:grow, [10px,default]", //$NON-NLS-1$
					"[10px,default], default, default:grow, [10px,default], fill:default:grow, [10px,default]")); //$NON-NLS-1$

				{

					_notes.setLineWrap(true);
					_notes.setWrapStyleWord(true);
					scrollPane1.setViewportView(_notes);
				}
				notesPanel.add(scrollPane1, CC.xy(2, 3, CC.FILL, CC.FILL));

				label1.setText("Notes"); //$NON-NLS-1$
				notesPanel.add(label1, CC.xy(2, 2));

				{
					gmNotesPanel.setLayout(new FormLayout(
						"default:grow", //$NON-NLS-1$
						"default, default:grow")); //$NON-NLS-1$

					{

						_GMNotes.setLineWrap(true);
						_GMNotes.setDisabledTextColor(Color.white);
						_GMNotes.setWrapStyleWord(true);
						scrollPane2.setViewportView(_GMNotes);
					}
					gmNotesPanel.add(scrollPane2, CC.xy(1, 2, CC.FILL, CC.FILL));

					label2.setText("GM Notes"); //$NON-NLS-1$
					gmNotesPanel.add(label2, CC.xy(1, 1));
				}
				notesPanel.add(gmNotesPanel, CC.xy(2, 5));
			}
			tabs.addTab("Notes", notesPanel); //$NON-NLS-1$

			{
				propertiesPanel.setLayout(new FormLayout(
					"[10px,default], default, default:grow, [10px,default]", //$NON-NLS-1$
					"[10px,default], default:grow, [10px,default]")); //$NON-NLS-1$

				propertiesTable.setText("propertiesTable"); //$NON-NLS-1$
				propertiesPanel.add(propertiesTable, CC.xywh(2, 2, 2, 1));
			}
			tabs.addTab("Properties", propertiesPanel); //$NON-NLS-1$

			{
				panel1.setLayout(new FormLayout(
					"[10px,default], default:grow, [10px,default]", //$NON-NLS-1$
					"[10px,default], fill:default:grow, [10px,default]")); //$NON-NLS-1$

				{
					scrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

					{
						statesPanel.setLayout(new FormLayout(
							"6*([10px,default])", //$NON-NLS-1$
							"[10px,default]")); //$NON-NLS-1$
					}
					scrollPane3.setViewportView(statesPanel);
				}
				panel1.add(scrollPane3, CC.xy(2, 2, CC.FILL, CC.FILL));
			}
			tabs.addTab("State", panel1); //$NON-NLS-1$

			{
				panel2.setLayout(new FormLayout(
					"[10px,default], default:grow, [10px,default]", //$NON-NLS-1$
					"[10px,default], default:grow, [10px,default], default, [10px,default]")); //$NON-NLS-1$

				{
					scrollPane4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					scrollPane4.setViewportView(speechTable);
				}
				panel2.add(scrollPane4, CC.xy(2, 2, CC.FILL, CC.FILL));

				{
					panel3.setLayout(new FormLayout(
						"default", //$NON-NLS-1$
						"default")); //$NON-NLS-1$

					speechClearAllButton.setText("Clear All"); //$NON-NLS-1$
					speechClearAllButton.setActionCommand("Clear All"); //$NON-NLS-1$
					panel3.add(speechClearAllButton, CC.xy(1, 1));
				}
				panel2.add(panel3, CC.xy(2, 4));
			}
			tabs.addTab("Speech", panel2); //$NON-NLS-1$

			{
				ownershipPanel.setLayout(new FormLayout(
					"[10px,default], default:grow, [10px,default]", //$NON-NLS-1$
					"[10px,default], default, [10px,default], default:grow, [10px,default]")); //$NON-NLS-1$

				_ownedByAll.setText("All Players"); //$NON-NLS-1$
				_ownedByAll.setActionCommand("All Players"); //$NON-NLS-1$
				ownershipPanel.add(_ownedByAll, CC.xy(2, 2));

				ownershipList.setText("ownerShipLabel"); //$NON-NLS-1$
				ownershipPanel.add(ownershipList, CC.xy(2, 4, CC.FILL, CC.FILL));
			}
			tabs.addTab("Ownership", ownershipPanel); //$NON-NLS-1$

			{
				panel4.setLayout(new FormLayout(
					"default:grow", //$NON-NLS-1$
					"2*(default, [10px,default])")); //$NON-NLS-1$

				{
					panel5.setLayout(new FormLayout(
						"2*([10px,default], default), 2*(default), 3*([10px,default]), 2*(default, [10px,default])", //$NON-NLS-1$
						"4*([10px,default], default), 3*([10px,default])")); //$NON-NLS-1$

					label3.setText("Shape:"); //$NON-NLS-1$
					panel5.add(label3, CC.xy(2, 2));

					label4.setText("Size:"); //$NON-NLS-1$
					panel5.add(label4, CC.xy(2, 4));

					label5.setText("Properties:"); //$NON-NLS-1$
					panel5.add(label5, CC.xy(2, 6));
					panel5.add(shape, CC.xywh(4, 2, 3, 1));
					panel5.add(size, CC.xywh(4, 4, 3, 1));
					panel5.add(propertyTypeCombo, CC.xywh(4, 6, 3, 1));

					label6.setText("Snap To Grid:"); //$NON-NLS-1$
					panel5.add(label6, CC.xy(10, 2));
					panel5.add(_snapToGrid, CC.xy(12, 2));

					visibleLabel.setText("Visible to players:"); //$NON-NLS-1$
					panel5.add(visibleLabel, CC.xy(10, 4));
					panel5.add(_visible, CC.xy(12, 4));

					label7.setText("Has Sight:"); //$NON-NLS-1$
					panel5.add(label7, CC.xy(2, 8));
					panel5.add(_hasSight, CC.xy(4, 8));
					panel5.add(sightTypeCombo, CC.xy(6, 8));

					visibleOnlyToOwnerLabel.setText("Visible to Owners Only"); //$NON-NLS-1$
					panel5.add(visibleOnlyToOwnerLabel, CC.xy(10, 6));
					panel5.add(_visibleOnlyToOwner, CC.xy(12, 6));
				}
				panel4.add(panel5, CC.xy(1, 1));

				{
					panel6.setLayout(new FormLayout(
						"3*(default, [10px,default])", //$NON-NLS-1$
						"default")); //$NON-NLS-1$

					{
						charsheetPanel.setBorder(new TitledBorder(LineBorder.createBlackLineBorder(), "Handout", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
						charsheetPanel.setLayout(new FormLayout(
							"default", //$NON-NLS-1$
							"default")); //$NON-NLS-1$

						charsheet.setText("Charsheet"); //$NON-NLS-1$
						charsheetPanel.add(charsheet, CC.xy(1, 1));
					}
					panel6.add(charsheetPanel, CC.xy(5, 1));

					{
						tokenLayoutPanel.setBorder(new TitledBorder(LineBorder.createBlackLineBorder(), "Layout", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
						tokenLayoutPanel.setLayout(new FormLayout(
							"default", //$NON-NLS-1$
							"default")); //$NON-NLS-1$

						tokenLayout.setText("Layout"); //$NON-NLS-1$
						tokenLayoutPanel.add(tokenLayout, CC.xy(1, 1));
					}
					panel6.add(tokenLayoutPanel, CC.xy(1, 1));

					{
						portraitPanel.setBorder(new TitledBorder(LineBorder.createBlackLineBorder(), "Portrait", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
						portraitPanel.setLayout(new FormLayout(
							"default", //$NON-NLS-1$
							"default")); //$NON-NLS-1$

						portrait.setText("Portrait"); //$NON-NLS-1$
						portraitPanel.add(portrait, CC.xy(1, 1));
					}
					panel6.add(portraitPanel, CC.xy(3, 1));
				}
				panel4.add(panel6, CC.xy(1, 3));
			}
			tabs.addTab("Config", panel4); //$NON-NLS-1$
		}
		add(tabs, CC.xywh(2, 4, 3, 1));

		tokenImage.setText("Image"); //$NON-NLS-1$
		add(tokenImage, CC.xy(2, 2));

		{
			panel7.setLayout(new FormLayout(
				"default, $ugap, default", //$NON-NLS-1$
				"default")); //$NON-NLS-1$

			cancelButton.setText("Close"); //$NON-NLS-1$
			cancelButton.setActionCommand("Cancel"); //$NON-NLS-1$
			panel7.add(cancelButton, CC.xy(3, 1));

			okButton.setText("OK"); //$NON-NLS-1$
			okButton.setActionCommand("OK"); //$NON-NLS-1$
			panel7.add(okButton, CC.xy(1, 1));
		}
		add(panel7, CC.xywh(2, 6, 3, 1, CC.RIGHT, CC.DEFAULT));

		{
			panel8.setLayout(new FormLayout(
				"2*(default, [10px,default]), default", //$NON-NLS-1$
				"3*(default, [10px,default])")); //$NON-NLS-1$

			label8.setText("Name:"); //$NON-NLS-1$
			panel8.add(label8, CC.xy(1, 1));

			_name.setFont(new Font("Dialog", Font.BOLD, 12)); //$NON-NLS-1$
			_name.setColumns(30);
			panel8.add(_name, CC.xy(3, 1));

			tokenGMNameLabel.setText("GM Name:"); //$NON-NLS-1$
			panel8.add(tokenGMNameLabel, CC.xy(1, 3));

			_GMName.setFont(new Font("Dialog", Font.BOLD, 12)); //$NON-NLS-1$
			_GMName.setColumns(30);
			panel8.add(_GMName, CC.xy(3, 3));
			panel8.add(_type, CC.xy(5, 1));

			label9.setText("Label:"); //$NON-NLS-1$
			panel8.add(label9, CC.xy(1, 5));
			panel8.add(_label, CC.xy(3, 5));
		}
		add(panel8, CC.xy(4, 2));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
