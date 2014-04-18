package com.t3.client.ui.forms;

import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Virenerus
 */
public abstract class ExportBaseDialog extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JLabel label1;
	protected JTabbedPane tabs;
	protected JPanel panel1;
	protected JLabel label2;
	protected JTextField locationTextField;
	protected JButton browseButton;
	protected JPanel panel2;
	protected JLabel label3;
	protected JTextField host;
	protected JLabel label4;
	protected JLabel label5;
	protected JTextField username;
	protected JLabel label6;
	protected JTextField path;
	protected JPasswordField password;
	protected JLabel label7;
	protected JLabel label8;
	protected JLabel label9;
	protected JLabel label10;
	protected JPanel panel3;
	protected JRadioButton METHOD_IMAGE_WRITER;
	protected JRadioButton METHOD_BACKGROUND;
	protected JLabel METHOD_LABEL;
	protected JRadioButton METHOD_BUFFERED_IMAGE;
	protected JPanel panel4;
	protected JButton exportButton;
	protected JButton cancelButton;
	protected JPanel panel5;
	protected JLabel label11;
	protected JRadioButton VIEW_GM;
	protected JRadioButton VIEW_PLAYER;
	protected JLabel label12;
	protected JRadioButton TYPE_CURRENT_VIEW;
	protected JRadioButton TYPE_ENTIRE_MAP;
	protected JLabel LAYERS_LABEL;
	protected JCheckBox LAYER_TOKEN;
	protected JCheckBox LAYER_HIDDEN;
	protected JCheckBox LAYER_FOG;
	protected JLabel label13;
	protected JRadioButton LAYERS_CURRENT;
	protected JRadioButton LAYERS_AS_SELECTED;
	protected JCheckBox LAYER_OBJECT;
	protected JCheckBox LAYER_BACKGROUND;
	protected JCheckBox LAYER_BOARD;
	protected JCheckBox LAYER_VISIBILITY;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public ExportBaseDialog() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		label1 = new JLabel();
		tabs = new JTabbedPane();
		panel1 = new JPanel();
		label2 = new JLabel();
		locationTextField = new JTextField();
		browseButton = new JButton();
		panel2 = new JPanel();
		label3 = new JLabel();
		host = new JTextField();
		label4 = new JLabel();
		label5 = new JLabel();
		username = new JTextField();
		label6 = new JLabel();
		path = new JTextField();
		password = new JPasswordField();
		label7 = new JLabel();
		label8 = new JLabel();
		label9 = new JLabel();
		label10 = new JLabel();
		panel3 = new JPanel();
		METHOD_IMAGE_WRITER = new JRadioButton();
		METHOD_BACKGROUND = new JRadioButton();
		METHOD_LABEL = new JLabel();
		METHOD_BUFFERED_IMAGE = new JRadioButton();
		panel4 = new JPanel();
		exportButton = new JButton();
		cancelButton = new JButton();
		panel5 = new JPanel();
		label11 = new JLabel();
		VIEW_GM = new JRadioButton();
		VIEW_PLAYER = new JRadioButton();
		label12 = new JLabel();
		TYPE_CURRENT_VIEW = new JRadioButton();
		TYPE_ENTIRE_MAP = new JRadioButton();
		LAYERS_LABEL = new JLabel();
		LAYER_TOKEN = new JCheckBox();
		LAYER_HIDDEN = new JCheckBox();
		LAYER_FOG = new JCheckBox();
		label13 = new JLabel();
		LAYERS_CURRENT = new JRadioButton();
		LAYERS_AS_SELECTED = new JRadioButton();
		LAYER_OBJECT = new JCheckBox();
		LAYER_BACKGROUND = new JCheckBox();
		LAYER_BOARD = new JCheckBox();
		LAYER_VISIBILITY = new JCheckBox();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"[10px,default], default, [10px,default]", //$NON-NLS-1$
			"4*([10px,default], default), [10px,default]")); //$NON-NLS-1$

		label1.setText("Export screenshot"); //$NON-NLS-1$
		label1.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		add(label1, CC.xy(2, 2));

		{

			{
				panel1.setLayout(new FormLayout(
					"[10px,default], default, [10px,default], default:grow, [10px,default], default, [10px,default]", //$NON-NLS-1$
					"[10px,default], default, [10px,default]")); //$NON-NLS-1$

				label2.setText("Location:"); //$NON-NLS-1$
				panel1.add(label2, CC.xy(2, 2));

				locationTextField.setColumns(30);
				panel1.add(locationTextField, CC.xy(4, 2));

				browseButton.setText("Browse ..."); //$NON-NLS-1$
				browseButton.setActionCommand("Browse ..."); //$NON-NLS-1$
				panel1.add(browseButton, CC.xy(6, 2));
			}
			tabs.addTab("Filesystem", panel1); //$NON-NLS-1$

			{
				panel2.setLayout(new FormLayout(
					"3*([10px,default], default), [10px,default]", //$NON-NLS-1$
					"4*([10px,default], default), default, [10px,default]")); //$NON-NLS-1$

				label3.setText("Host:"); //$NON-NLS-1$
				panel2.add(label3, CC.xy(2, 2));

				host.setColumns(30);
				panel2.add(host, CC.xy(4, 2));

				label4.setText("Username:"); //$NON-NLS-1$
				panel2.add(label4, CC.xy(2, 4));

				label5.setText("Password:"); //$NON-NLS-1$
				panel2.add(label5, CC.xy(2, 6));
				panel2.add(username, CC.xy(4, 4));

				label6.setText("Path/Filename:"); //$NON-NLS-1$
				panel2.add(label6, CC.xy(2, 8));
				panel2.add(path, CC.xy(4, 8));
				panel2.add(password, CC.xy(4, 6));

				label7.setText("(ex: myhost.com)"); //$NON-NLS-1$
				panel2.add(label7, CC.xy(6, 2));

				label8.setText("(ex: pbpuser)"); //$NON-NLS-1$
				panel2.add(label8, CC.xy(6, 4));

				label9.setText("(ex: path/to/my/images/map.png)"); //$NON-NLS-1$
				panel2.add(label9, CC.xy(6, 8));

				label10.setText("NOTE: path must already exist"); //$NON-NLS-1$
				panel2.add(label10, CC.xy(4, 9));
			}
			tabs.addTab("FTP", panel2); //$NON-NLS-1$

			{
				panel3.setLayout(new FormLayout(
					"$rgap, pref, 3*($rgap, default)", //$NON-NLS-1$
					"23px, default, [10px,default]")); //$NON-NLS-1$

				METHOD_IMAGE_WRITER.setText("Image Writer"); //$NON-NLS-1$
				METHOD_IMAGE_WRITER.setActionCommand("Image Writer"); //$NON-NLS-1$
				METHOD_IMAGE_WRITER.setToolTipText("Renders the image incrementally as it is needed by the file output formatting code. Uses less memory. Extremely large files can take a long time. The UI will not update during the file output, so the program may be unresponsive for several minutes while the file is saved."); //$NON-NLS-1$
				panel3.add(METHOD_IMAGE_WRITER, CC.xy(6, 2));

				METHOD_BACKGROUND.setText("Background Thread"); //$NON-NLS-1$
				METHOD_BACKGROUND.setActionCommand("Background Thread"); //$NON-NLS-1$
				METHOD_BACKGROUND.setToolTipText("NOT IMPLEMENTED! Uses a the Image Writer technique in a background thread so that the UI can be updated with the progress."); //$NON-NLS-1$
				panel3.add(METHOD_BACKGROUND, CC.xy(8, 2));

				METHOD_LABEL.setText("Rendering Method:"); //$NON-NLS-1$
				panel3.add(METHOD_LABEL, CC.xy(2, 2));

				METHOD_BUFFERED_IMAGE.setText("Buffered Image"); //$NON-NLS-1$
				METHOD_BUFFERED_IMAGE.setSelected(true);
				METHOD_BUFFERED_IMAGE.setActionCommand("Buffered Image"); //$NON-NLS-1$
				METHOD_BUFFERED_IMAGE.setToolTipText("Renders the image into a single bitmap then saves that to the chosen location. Can use a lot of memory, and may fail if the image is too large."); //$NON-NLS-1$
				panel3.add(METHOD_BUFFERED_IMAGE, CC.xy(4, 2));
			}
			tabs.addTab("Advanced", panel3); //$NON-NLS-1$
		}
		add(tabs, CC.xy(2, 6));

		{
			panel4.setLayout(new FormLayout(
				"2*([10px,default], default), [10px,default]", //$NON-NLS-1$
				"default")); //$NON-NLS-1$

			exportButton.setText("Export"); //$NON-NLS-1$
			exportButton.setActionCommand("Export"); //$NON-NLS-1$
			panel4.add(exportButton, CC.xy(2, 1));

			cancelButton.setText("Cancel"); //$NON-NLS-1$
			cancelButton.setActionCommand("Cancel"); //$NON-NLS-1$
			panel4.add(cancelButton, CC.xy(4, 1));
		}
		add(panel4, CC.xy(2, 8, CC.RIGHT, CC.DEFAULT));

		{
			panel5.setLayout(new FormLayout(
				"default, $rgap, default, [10px,default], default, 46px, default, $rgap, 2*(default)", //$NON-NLS-1$
				"2*(default), 13dlu, 3*(default), [10px,default]")); //$NON-NLS-1$

			label11.setText("View:"); //$NON-NLS-1$
			panel5.add(label11, CC.xy(1, 6));

			VIEW_GM.setText("GM"); //$NON-NLS-1$
			VIEW_GM.setActionCommand("GM"); //$NON-NLS-1$
			VIEW_GM.setToolTipText("Everything the GM sees"); //$NON-NLS-1$
			panel5.add(VIEW_GM, CC.xy(3, 6));

			VIEW_PLAYER.setText("Player"); //$NON-NLS-1$
			VIEW_PLAYER.setActionCommand("Player"); //$NON-NLS-1$
			VIEW_PLAYER.setToolTipText("Only what a Player can see"); //$NON-NLS-1$
			panel5.add(VIEW_PLAYER, CC.xy(5, 6));

			label12.setText("Type:"); //$NON-NLS-1$
			panel5.add(label12, CC.xy(1, 1));

			TYPE_CURRENT_VIEW.setText("Current View"); //$NON-NLS-1$
			TYPE_CURRENT_VIEW.setSelected(true);
			TYPE_CURRENT_VIEW.setActionCommand("Current View"); //$NON-NLS-1$
			panel5.add(TYPE_CURRENT_VIEW, CC.xy(3, 1));

			TYPE_ENTIRE_MAP.setText("Entire Map"); //$NON-NLS-1$
			TYPE_ENTIRE_MAP.setActionCommand("Entire Map"); //$NON-NLS-1$
			TYPE_ENTIRE_MAP.setToolTipText("Entire map, respects visibility and fog"); //$NON-NLS-1$
			panel5.add(TYPE_ENTIRE_MAP, CC.xy(5, 1));

			LAYERS_LABEL.setEnabled(false);
			LAYERS_LABEL.setText("Layers:"); //$NON-NLS-1$
			panel5.add(LAYERS_LABEL, CC.xy(7, 1));

			LAYER_TOKEN.setEnabled(false);
			LAYER_TOKEN.setText("Token"); //$NON-NLS-1$
			LAYER_TOKEN.setSelected(true);
			LAYER_TOKEN.setActionCommand("Token"); //$NON-NLS-1$
			LAYER_TOKEN.setToolTipText("include this layer"); //$NON-NLS-1$
			panel5.add(LAYER_TOKEN, CC.xy(9, 1));

			LAYER_HIDDEN.setEnabled(false);
			LAYER_HIDDEN.setText("Hidden"); //$NON-NLS-1$
			LAYER_HIDDEN.setSelected(true);
			LAYER_HIDDEN.setActionCommand("Hidden"); //$NON-NLS-1$
			LAYER_HIDDEN.setToolTipText("include this layer"); //$NON-NLS-1$
			panel5.add(LAYER_HIDDEN, CC.xy(9, 2));

			LAYER_FOG.setEnabled(false);
			LAYER_FOG.setText("Fog"); //$NON-NLS-1$
			LAYER_FOG.setSelected(true);
			LAYER_FOG.setActionCommand("Fog"); //$NON-NLS-1$
			LAYER_FOG.setToolTipText("Entire Map:  \n    Same as enabling fog on the Map menu.\n\nEntire Map As Layers: \n    If checked, a separate file will be exported with the current fog mask."); //$NON-NLS-1$
			panel5.add(LAYER_FOG, CC.xy(10, 2));

			label13.setText("Layers:"); //$NON-NLS-1$
			panel5.add(label13, CC.xy(1, 4));

			LAYERS_CURRENT.setText("Current View"); //$NON-NLS-1$
			LAYERS_CURRENT.setSelected(true);
			LAYERS_CURRENT.setActionCommand("Current View"); //$NON-NLS-1$
			LAYERS_CURRENT.setToolTipText("What you see is what you get."); //$NON-NLS-1$
			panel5.add(LAYERS_CURRENT, CC.xy(3, 4));

			LAYERS_AS_SELECTED.setText("As Selected"); //$NON-NLS-1$
			LAYERS_AS_SELECTED.setActionCommand("As Selected"); //$NON-NLS-1$
			LAYERS_AS_SELECTED.setToolTipText("Pick which layers to include in the screenshot."); //$NON-NLS-1$
			panel5.add(LAYERS_AS_SELECTED, CC.xy(5, 4));

			LAYER_OBJECT.setEnabled(false);
			LAYER_OBJECT.setText("Object"); //$NON-NLS-1$
			LAYER_OBJECT.setSelected(true);
			LAYER_OBJECT.setActionCommand("Object"); //$NON-NLS-1$
			LAYER_OBJECT.setToolTipText("include this layer"); //$NON-NLS-1$
			panel5.add(LAYER_OBJECT, CC.xy(9, 3));

			LAYER_BACKGROUND.setEnabled(false);
			LAYER_BACKGROUND.setText("Background"); //$NON-NLS-1$
			LAYER_BACKGROUND.setSelected(true);
			LAYER_BACKGROUND.setActionCommand("Background"); //$NON-NLS-1$
			LAYER_BACKGROUND.setToolTipText("include this layer"); //$NON-NLS-1$
			panel5.add(LAYER_BACKGROUND, CC.xy(9, 4));

			LAYER_BOARD.setEnabled(false);
			LAYER_BOARD.setText("Board"); //$NON-NLS-1$
			LAYER_BOARD.setSelected(true);
			LAYER_BOARD.setActionCommand("Board"); //$NON-NLS-1$
			LAYER_BOARD.setToolTipText("The tiling background and/or the image used in \"New Map\""); //$NON-NLS-1$
			panel5.add(LAYER_BOARD, CC.xy(9, 5));

			LAYER_VISIBILITY.setEnabled(false);
			LAYER_VISIBILITY.setText("Visibility"); //$NON-NLS-1$
			LAYER_VISIBILITY.setSelected(true);
			LAYER_VISIBILITY.setActionCommand("Visibility"); //$NON-NLS-1$
			LAYER_VISIBILITY.setToolTipText("Saves Visibility Blocking Layer (VBL) as a monochrome image."); //$NON-NLS-1$
			panel5.add(LAYER_VISIBILITY, CC.xy(10, 3));
		}
		add(panel5, CC.xy(2, 4));

		ButtonGroup buttonGroup4 = new ButtonGroup();
		buttonGroup4.add(METHOD_IMAGE_WRITER);
		buttonGroup4.add(METHOD_BACKGROUND);
		buttonGroup4.add(METHOD_BUFFERED_IMAGE);

		ButtonGroup buttonGroup2 = new ButtonGroup();
		buttonGroup2.add(VIEW_GM);
		buttonGroup2.add(VIEW_PLAYER);

		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(TYPE_CURRENT_VIEW);
		buttonGroup1.add(TYPE_ENTIRE_MAP);

		ButtonGroup buttonGroup3 = new ButtonGroup();
		buttonGroup3.add(LAYERS_CURRENT);
		buttonGroup3.add(LAYERS_AS_SELECTED);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
