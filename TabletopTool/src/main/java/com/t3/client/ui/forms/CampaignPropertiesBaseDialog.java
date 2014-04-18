package com.t3.client.ui.forms;

import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import com.jeta.forms.components.colors.JETAColorWell;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Virenerus
 */
public abstract class CampaignPropertiesBaseDialog extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JTabbedPane tabbedPane1;
	protected JPanel propertiesPanel;
	protected JLabel tokenPropertiesPanel;
	protected JPanel panel1;
	protected JTextField newServer;
	protected JButton addRepoButton;
	protected JScrollPane scrollPane1;
	protected JList repoList;
	protected JPanel panel2;
	protected JButton deleteRepoButton;
	protected JPanel panel3;
	protected JButton addGalleryIndexButton;
	protected JPanel panel4;
	protected JScrollPane scrollPane2;
	protected JEditorPane sightPanel;
	protected JLabel label1;
	protected JPanel panel5;
	protected JScrollPane scrollPane3;
	protected JEditorPane lightPanel;
	protected JScrollPane scrollPane4;
	protected JEditorPane lightHelp;
	protected JPanel tokenStatesPanel;
	protected JComboBox<String> tokenStatesType;
	protected JLabel tokenStatesColorLabel;
	protected JETAColorWell tokenStatesColor;
	protected JLabel tokenStatesWidthLabel;
	protected JSpinner tokenStatesWidth;
	protected JLabel label2;
	protected JScrollPane scrollPane5;
	protected JList tokenStatesStates;
	protected JLabel tokenStatesImageFileLabel;
	protected JTextField tokenStatesImageFile;
	protected JComboBox<String> tokenStatesCorner;
	protected JSpinner tokenStatesFlowGrid;
	protected JLabel tokenStatesCornerLabel;
	protected JLabel tokenStatesFlowGridLabel;
	protected JLabel label3;
	protected JCheckBox tokenStatesMouseover;
	protected JLabel label4;
	protected JSpinner tokenStatesOpacity;
	protected JButton tokenStatesBrowseImage;
	protected JLabel label5;
	protected JPanel panel6;
	protected JButton tokenStatesAddState;
	protected JButton tokenStatesDeleteState;
	protected JButton tokenStatesUpdateState;
	protected JButton tokenStatesMoveUp;
	protected JButton tokenStatesMoveDown;
	protected JPanel panel7;
	protected JTextField tokenStatesName;
	protected JLabel label6;
	protected JTextField tokenStatesGroup;
	protected JPanel panel8;
	protected JPanel panel9;
	protected JCheckBox tokenStatesGM;
	protected JCheckBox tokenStatesOwner;
	protected JCheckBox tokenStatesEverybody;
	protected JPanel panel10;
	protected JPanel tokenStatesPanel2;
	protected JComboBox<String> tokenBarType;
	protected JLabel tokenBarColorLabel;
	protected JETAColorWell tokenBarColor;
	protected JLabel tokenBarThicknessLabel;
	protected JSpinner tokenBarThickness;
	protected JLabel label7;
	protected JComboBox<String> tokenBarSide;
	protected JLabel label8;
	protected JLabel label9;
	protected JLabel label10;
	protected JSpinner tokenBarOpacity;
	protected JScrollPane scrollPane6;
	protected JList tokenBarImages;
	protected JLabel label11;
	protected JSpinner tokenBarIncrements;
	protected JLabel label12;
	protected JLabel tokenBarBgColorLabel;
	protected JCheckBox tokenBarMouseover;
	protected JETAColorWell tokenBarBgColor;
	protected JPanel panel11;
	protected JButton tokenBarAddBar;
	protected JButton tokenBarDeleteBar;
	protected JButton tokenBarUpdateBar;
	protected JButton tokenBarMoveUp;
	protected JButton tokenBarMoveDown;
	protected JPanel panel12;
	protected JTextField tokenBarName;
	protected JPanel panel13;
	protected JPanel panel14;
	protected JCheckBox tokenBarGM;
	protected JCheckBox tokenBarOwner;
	protected JCheckBox tokenBarEverybody;
	protected JPanel panel15;
	protected JButton tokenBarAddImage;
	protected JButton tokenBarDeleteImage;
	protected JButton tokenBarUpdateImage;
	protected JButton tokenBarMoveUpImage;
	protected JButton tokenBarMoveDownImage;
	protected JLabel tokenBarImagesLabel;
	protected JPanel panel16;
	protected JScrollPane scrollPane7;
	protected JList tokenBarBars;
	protected JSlider tokenBarTest;
	protected JLabel label13;
	protected JPanel panel17;
	protected JButton okButton;
	protected JButton cancelButton;
	protected JButton importButton;
	protected JButton exportButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public CampaignPropertiesBaseDialog() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		tabbedPane1 = new JTabbedPane();
		propertiesPanel = new JPanel();
		tokenPropertiesPanel = new JLabel();
		panel1 = new JPanel();
		newServer = new JTextField();
		addRepoButton = new JButton();
		scrollPane1 = new JScrollPane();
		repoList = new JList();
		panel2 = new JPanel();
		deleteRepoButton = new JButton();
		panel3 = new JPanel();
		addGalleryIndexButton = new JButton();
		panel4 = new JPanel();
		scrollPane2 = new JScrollPane();
		sightPanel = new JEditorPane();
		label1 = new JLabel();
		panel5 = new JPanel();
		scrollPane3 = new JScrollPane();
		lightPanel = new JEditorPane();
		scrollPane4 = new JScrollPane();
		lightHelp = new JEditorPane();
		tokenStatesPanel = new JPanel();
		tokenStatesType = new JComboBox<>();
		tokenStatesColorLabel = new JLabel();
		tokenStatesColor = new JETAColorWell();
		tokenStatesWidthLabel = new JLabel();
		tokenStatesWidth = new JSpinner();
		label2 = new JLabel();
		scrollPane5 = new JScrollPane();
		tokenStatesStates = new JList();
		tokenStatesImageFileLabel = new JLabel();
		tokenStatesImageFile = new JTextField();
		tokenStatesCorner = new JComboBox<>();
		tokenStatesFlowGrid = new JSpinner();
		tokenStatesCornerLabel = new JLabel();
		tokenStatesFlowGridLabel = new JLabel();
		label3 = new JLabel();
		tokenStatesMouseover = new JCheckBox();
		label4 = new JLabel();
		tokenStatesOpacity = new JSpinner();
		tokenStatesBrowseImage = new JButton();
		label5 = new JLabel();
		panel6 = new JPanel();
		tokenStatesAddState = new JButton();
		tokenStatesDeleteState = new JButton();
		tokenStatesUpdateState = new JButton();
		tokenStatesMoveUp = new JButton();
		tokenStatesMoveDown = new JButton();
		panel7 = new JPanel();
		tokenStatesName = new JTextField();
		label6 = new JLabel();
		tokenStatesGroup = new JTextField();
		panel8 = new JPanel();
		panel9 = new JPanel();
		tokenStatesGM = new JCheckBox();
		tokenStatesOwner = new JCheckBox();
		tokenStatesEverybody = new JCheckBox();
		panel10 = new JPanel();
		tokenStatesPanel2 = new JPanel();
		tokenBarType = new JComboBox<>();
		tokenBarColorLabel = new JLabel();
		tokenBarColor = new JETAColorWell();
		tokenBarThicknessLabel = new JLabel();
		tokenBarThickness = new JSpinner();
		label7 = new JLabel();
		tokenBarSide = new JComboBox<>();
		label8 = new JLabel();
		label9 = new JLabel();
		label10 = new JLabel();
		tokenBarOpacity = new JSpinner();
		scrollPane6 = new JScrollPane();
		tokenBarImages = new JList();
		label11 = new JLabel();
		tokenBarIncrements = new JSpinner();
		label12 = new JLabel();
		tokenBarBgColorLabel = new JLabel();
		tokenBarMouseover = new JCheckBox();
		tokenBarBgColor = new JETAColorWell();
		panel11 = new JPanel();
		tokenBarAddBar = new JButton();
		tokenBarDeleteBar = new JButton();
		tokenBarUpdateBar = new JButton();
		tokenBarMoveUp = new JButton();
		tokenBarMoveDown = new JButton();
		panel12 = new JPanel();
		tokenBarName = new JTextField();
		panel13 = new JPanel();
		panel14 = new JPanel();
		tokenBarGM = new JCheckBox();
		tokenBarOwner = new JCheckBox();
		tokenBarEverybody = new JCheckBox();
		panel15 = new JPanel();
		tokenBarAddImage = new JButton();
		tokenBarDeleteImage = new JButton();
		tokenBarUpdateImage = new JButton();
		tokenBarMoveUpImage = new JButton();
		tokenBarMoveDownImage = new JButton();
		tokenBarImagesLabel = new JLabel();
		panel16 = new JPanel();
		scrollPane7 = new JScrollPane();
		tokenBarBars = new JList();
		tokenBarTest = new JSlider();
		label13 = new JLabel();
		panel17 = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		importButton = new JButton();
		exportButton = new JButton();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"[10px,default], default:grow, [10px,default]", //$NON-NLS-1$
			"[10px,default], fill:default:grow, [10px,default], default, [10px,default]")); //$NON-NLS-1$

		{

			{
				propertiesPanel.setLayout(new FormLayout(
					"[10px,default], default:grow, [10px,default]", //$NON-NLS-1$
					"[10px,default], fill:default:grow, [10px,default]")); //$NON-NLS-1$

				tokenPropertiesPanel.setText("Token Properties Panel"); //$NON-NLS-1$
				propertiesPanel.add(tokenPropertiesPanel, CC.xy(2, 2));
			}
			tabbedPane1.addTab("Token Properties", propertiesPanel); //$NON-NLS-1$

			{
				panel1.setLayout(new FormLayout(
					"[10px,default], default:grow, 2*(default), [10px,default], default, [10px,default]", //$NON-NLS-1$
					"[10px,default], default, [10px,default], default:grow, [10px,default], default, [10px,default]")); //$NON-NLS-1$
				panel1.add(newServer, CC.xy(2, 2));

				addRepoButton.setText("Add"); //$NON-NLS-1$
				addRepoButton.setActionCommand("Add"); //$NON-NLS-1$
				panel1.add(addRepoButton, CC.xy(4, 2));

				{
					scrollPane1.setViewportView(repoList);
				}
				panel1.add(scrollPane1, CC.xywh(2, 4, 3, 1, CC.FILL, CC.FILL));

				{
					panel2.setLayout(new FormLayout(
						"default", //$NON-NLS-1$
						"default")); //$NON-NLS-1$

					deleteRepoButton.setText("Delete"); //$NON-NLS-1$
					deleteRepoButton.setActionCommand("JButton"); //$NON-NLS-1$
					panel2.add(deleteRepoButton, CC.xy(1, 1));
				}
				panel1.add(panel2, CC.xy(6, 4, CC.DEFAULT, CC.TOP));

				{
					panel3.setLayout(new FormLayout(
						"[10px,default], default", //$NON-NLS-1$
						"default")); //$NON-NLS-1$

					addGalleryIndexButton.setText("Add Tabletoptool Gallery Index"); //$NON-NLS-1$
					addGalleryIndexButton.setActionCommand("Add Tabletoptool Gallery Index"); //$NON-NLS-1$
					panel3.add(addGalleryIndexButton, CC.xy(2, 1));
				}
				panel1.add(panel3, CC.xy(2, 6));
			}
			tabbedPane1.addTab("Repositories", panel1); //$NON-NLS-1$

			{
				panel4.setLayout(new FormLayout(
					"[10px,default], default:grow, [10px,default]", //$NON-NLS-1$
					"[10px,default], default:grow, [10px,default], default, [10px,default]")); //$NON-NLS-1$

				{
					scrollPane2.setViewportView(sightPanel);
				}
				panel4.add(scrollPane2, CC.xy(2, 2, CC.FILL, CC.FILL));

				label1.setText("<html><b>Format:</b> &nbsp&nbsp&nbsp;Name : options <p><p> Each sight type can have zero or more options.  Having zero options implies using defaults for all values (e.g. Normal vision).  use spaces between each option <p><p> <b>\n\nOptions:</b> <p><p> \nshape - shape may be circle, square, or cone\ndistance=y - Distance is the range at which this vision type ends.  Can be a decimal value. Ex: distance=10<br>\narc=y -  When used with the cone shape, it signifies how large a field of vision the token has.  Can be an interger value. Ex: arc=120<br>\noffset=y -  When used with the cone shape, it signifies how much offest the cone begins from the center of the tokens facing.  Can be an interger value. Ex: offset=120<br>\nx# - Maginifier, multiplies each light source radius by this value.  Can be a decimal value.  Ex: x2<br> \nr# - Range of personal light.  The token will have a light source centered on them that only they can see with a radius of this value.  Ex: r60<br>   "); //$NON-NLS-1$
				panel4.add(label1, CC.xy(2, 4));
			}
			tabbedPane1.addTab("Sight", panel4); //$NON-NLS-1$

			{
				panel5.setLayout(new FormLayout(
					"[10px,default], default:grow, [10px,default]", //$NON-NLS-1$
					"[10px,default], 2*(default:grow), default, [10px,default]")); //$NON-NLS-1$

				{

					lightPanel.setAutoscrolls(false);
					scrollPane3.setViewportView(lightPanel);
				}
				panel5.add(scrollPane3, CC.xy(2, 2, CC.FILL, CC.FILL));

				{
					scrollPane4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					scrollPane4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

					lightHelp.setSelectionEnd(3195);
					lightHelp.setText("<html>\n  <head>\n    \n  </head>\n  <body>\n    <b>Format:</b> \n\n    <table>\n      <tr>\n        <td>\n          &#160;&#160;&#160;&#160;\n        </td>\n        <td>\n          <pre>Group name\n----------\nname <b>:</b> <i>type</i> <i>shape</i> <b>OPTIONS</b> range <b>#</b>rrggbb\n&lt;blank line&gt;</pre>\n        </td>\n      </tr>\n    </table>\n    Define groups of light types with a line of text to indicate the group \n    name followed by one or more <b>name</b> definitions. End a group with a \n    blank line. All text is case-insensitive. Lines that start with a &quot;-&quot; are \n    considered comments and are ignored. The group name becomes an entry on \n    the token's popup menu under the <b>Light Sources</b> element, and \n    individual light source definitions become sub-entries. Group names are \n    displayed in sorted order, and the sub-entries are alphabetically sorted \n    as well.\n\n    <p>\n      <b>name:</b> A user-visible name for light definition.<br><b>type:</b> \n      An optional type for the light. Currently only <b>aura</b> is valid. \n      (See below for a description of auras.)<br><b>shape:</b> An optional \n      shape for the light emanation. Can be <b>circle</b> (default), <b>square</b>, \n      or <b>cone</b> (cones also have an <b>arc=<i>angle</i></b> field). \n      Shapes apply from the point they are included in the definition until \n      the end of the definition or until another shape is specified. This \n      means multiple light shapes can be created under a single name and \n      turned on or off as a unit.<br><b>range:</b> A distance measured in map \n      units (defined when the map is created). Units are measured from the \n      centerpoint of the grid cell, hence the extra 2 &#189; feet added in the \n      below example to make neat squares. Can have an optional <i>#rrggbb</i> \n      hex color component appended to it as per typical CSS rules.<br><b>OPTIONS:</b> \n      The only valid options are <b>GM</b> and <b>OWNER</b> and they apply \n      only to auras (see below).\n    </p>\n    <br>\n    <hr>\n    \n\n    <p>\n      <b>Example:</b>\n    </p>\n    <table>\n      <tr>\n        <td>\n          &#160;&#160;&#160;&#160;\n        </td>\n        <td>\n          <pre>Sample Lights - 5' square grid\n----\nCandle - 5 : 7.5 12.5#000000 \nLamp - 15 : 17.5 32.5#000000 \nTorch - 20 : 22.5 42.5#000000 \nLantern, bullseye - 60 : cone arc=60 62.5 122.5#000000 \nLantern, bullseye (leaks light) - 60 : cone arc=60 62.5 122.5#000000 circle 2.5#000000\nNightvision goggles - 40 : cone arc=30 42.5#00e000 \n&lt;blank line&gt;</pre>\n        </td>\n      </tr>\n    </table>\n    <hr>\n    \n\n    <p>\n      <b>Auras:</b>\n    </p>\n    <p>\n      To create an aura, put the keyword <b>aura</b> at the beginning of the \n      definition. Auras radiate a colored area and interact with the vision \n      blocking layer (i.e. are blocked by VBL), but do not actually cast any \n      visible light and therefore do not expose any fog-of-war. GM-only auras \n      are only visible by clients logged into the server as GM. They remain \n      visible even when the map is in <i>Show As Player</i> mode. Owner-only \n      auras are only visible to the player(s) who owns the token they are \n      attached to and are always visible to the GM. See examples, below.\n    </p>\n    <p>\n      In the following example, the GM-only auras are red, the owner-only \n      auras are green, and the player auras are blue. You can of course define \n      your own colors.\n    </p>\n    <table>\n      <tr>\n        <td>\n          &#160;&#160;&#160;&#160;\n        </td>\n        <td>\n          <pre>Sample Auras - 5' square grid\n----\nAura red 5 : aura square GM 2.5#ff0000\nAura red 10 : aura GM 7.5#ff0000\nAura Owner Also 5 : aura square owner 2.5#00ff00\nAura Owner Also 10 : aura owner 7.5#00ff00\nAura Player Also 5 : aura square 2.5#0000ff\nAura Player Also 10 : aura 7.5#0000ff</pre>\n        </td>\n      </tr>\n    </table>\n    The first line creates a square GM-only aura in red that is 5-feet across. \n    The second creates a round GM-only aura in red with a 15-foot diameter. \n    Lines three and four do the same thing, but with a green aura that is only \n    visible to players who own the token with the aura attached to it. Lines \n    five and six are visible to all players and are in blue.\n  </body>\n</html>\n"); //$NON-NLS-1$
					lightHelp.setContentType("text/html"); //$NON-NLS-1$
					lightHelp.setEditable(false);
					lightHelp.setBackground(new Color(238, 238, 238));
					lightHelp.setSelectionStart(3195);
					lightHelp.setAutoscrolls(false);
					scrollPane4.setViewportView(lightHelp);
				}
				panel5.add(scrollPane4, CC.xywh(2, 3, 1, 2));
			}
			tabbedPane1.addTab("Light", panel5); //$NON-NLS-1$

			{
				tokenStatesPanel.setLayout(new FormLayout(
					"[10px,default], right:default, 4dlu, default, 8dlu, right:default, 4dlu, 25px, 8dlu, right:default, 4dlu, 50px, 8dlu, right:default, 4dlu, 50px, 0px:grow, $rgap, default, [10px,default]", //$NON-NLS-1$
					"[10px,default], 2*(default, $nlgap), 2*(default), $nlgap, default, $rgap, fill:default:grow, [10px,default]")); //$NON-NLS-1$

				tokenStatesType.setModel(new DefaultComboBoxModel<>(new String[] {
					"Image", //$NON-NLS-1$
					"Corner Image", //$NON-NLS-1$
					"Grid Image", //$NON-NLS-1$
					"Dot", //$NON-NLS-1$
					"Grid Dot", //$NON-NLS-1$
					"Circle", //$NON-NLS-1$
					"Shaded", //$NON-NLS-1$
					"X", //$NON-NLS-1$
					"Cross", //$NON-NLS-1$
					"Diamond", //$NON-NLS-1$
					"Grid Diamond", //$NON-NLS-1$
					"Yield", //$NON-NLS-1$
					"Grid Yield", //$NON-NLS-1$
					"Triangle", //$NON-NLS-1$
					"Grid Triangle", //$NON-NLS-1$
					"Grid Square" //$NON-NLS-1$
				}));
				tokenStatesPanel.add(tokenStatesType, CC.xy(4, 4));

				tokenStatesColorLabel.setText("Color:"); //$NON-NLS-1$
				tokenStatesPanel.add(tokenStatesColorLabel, CC.xy(6, 4));
				tokenStatesPanel.add(tokenStatesColor, CC.xy(8, 4, CC.DEFAULT, CC.FILL));

				tokenStatesWidthLabel.setText("Width:"); //$NON-NLS-1$
				tokenStatesPanel.add(tokenStatesWidthLabel, CC.xy(10, 4));
				tokenStatesPanel.add(tokenStatesWidth, CC.xy(12, 4));

				label2.setText("Type:"); //$NON-NLS-1$
				tokenStatesPanel.add(label2, CC.xy(2, 4));

				{

					tokenStatesStates.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollPane5.setViewportView(tokenStatesStates);
				}
				tokenStatesPanel.add(scrollPane5, CC.xywh(2, 11, 16, 1));

				tokenStatesImageFileLabel.setText("Image:"); //$NON-NLS-1$
				tokenStatesPanel.add(tokenStatesImageFileLabel, CC.xy(2, 9));
				tokenStatesPanel.add(tokenStatesImageFile, CC.xywh(4, 9, 14, 1));

				tokenStatesCorner.setModel(new DefaultComboBoxModel<>(new String[] {
					"North East", //$NON-NLS-1$
					"North West", //$NON-NLS-1$
					"South East", //$NON-NLS-1$
					"South West" //$NON-NLS-1$
				}));
				tokenStatesPanel.add(tokenStatesCorner, CC.xy(4, 6));
				tokenStatesPanel.add(tokenStatesFlowGrid, CC.xy(12, 6, CC.FILL, CC.DEFAULT));

				tokenStatesCornerLabel.setText("Corner:"); //$NON-NLS-1$
				tokenStatesPanel.add(tokenStatesCornerLabel, CC.xy(2, 6));

				tokenStatesFlowGridLabel.setText("Grid Size:"); //$NON-NLS-1$
				tokenStatesPanel.add(tokenStatesFlowGridLabel, CC.xy(10, 6));

				label3.setText("Mouseover:"); //$NON-NLS-1$
				tokenStatesPanel.add(label3, CC.xy(6, 6));
				tokenStatesPanel.add(tokenStatesMouseover, CC.xy(8, 6, CC.CENTER, CC.DEFAULT));

				label4.setText("Opacity:"); //$NON-NLS-1$
				tokenStatesPanel.add(label4, CC.xy(14, 4));
				tokenStatesPanel.add(tokenStatesOpacity, CC.xy(16, 4));

				tokenStatesBrowseImage.setText("Browse"); //$NON-NLS-1$
				tokenStatesBrowseImage.setActionCommand("Add"); //$NON-NLS-1$
				tokenStatesPanel.add(tokenStatesBrowseImage, CC.xy(19, 9));

				label5.setText("Name:"); //$NON-NLS-1$
				tokenStatesPanel.add(label5, CC.xy(2, 2));

				{
					panel6.setLayout(new FormLayout(
						"default", //$NON-NLS-1$
						"top:default, 4*($rgap, default)")); //$NON-NLS-1$

					tokenStatesAddState.setText("Add"); //$NON-NLS-1$
					tokenStatesAddState.setActionCommand("Add"); //$NON-NLS-1$
					panel6.add(tokenStatesAddState, CC.xy(1, 1));

					tokenStatesDeleteState.setText("Delete"); //$NON-NLS-1$
					tokenStatesDeleteState.setActionCommand("deleteState\t"); //$NON-NLS-1$
					panel6.add(tokenStatesDeleteState, CC.xy(1, 5));

					tokenStatesUpdateState.setText("Update"); //$NON-NLS-1$
					tokenStatesUpdateState.setActionCommand("Add"); //$NON-NLS-1$
					panel6.add(tokenStatesUpdateState, CC.xy(1, 3));

					tokenStatesMoveUp.setText("Move Up"); //$NON-NLS-1$
					tokenStatesMoveUp.setActionCommand("deleteState\t"); //$NON-NLS-1$
					panel6.add(tokenStatesMoveUp, CC.xy(1, 7));

					tokenStatesMoveDown.setText("Move Down"); //$NON-NLS-1$
					tokenStatesMoveDown.setActionCommand("deleteState\t"); //$NON-NLS-1$
					panel6.add(tokenStatesMoveDown, CC.xy(1, 9));
				}
				tokenStatesPanel.add(panel6, CC.xy(19, 11));

				{
					panel7.setLayout(new FormLayout(
						"0px:grow, [10px,default], default, $rgap, 0px:grow", //$NON-NLS-1$
						"default")); //$NON-NLS-1$
					panel7.add(tokenStatesName, CC.xy(1, 1));

					label6.setText("Group:"); //$NON-NLS-1$
					panel7.add(label6, CC.xy(3, 1));
					panel7.add(tokenStatesGroup, CC.xy(5, 1));
				}
				tokenStatesPanel.add(panel7, CC.xywh(4, 2, 14, 1));

				{
					panel8.setLayout(new FormLayout(
						"default", //$NON-NLS-1$
						"default")); //$NON-NLS-1$

					{
						panel9.setBorder(new TitledBorder(LineBorder.createBlackLineBorder(), "Show To:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
						panel9.setLayout(new FormLayout(
							"2*(default, $ugap), default", //$NON-NLS-1$
							"default")); //$NON-NLS-1$

						tokenStatesGM.setText("GM"); //$NON-NLS-1$
						tokenStatesGM.setSelected(true);
						tokenStatesGM.setActionCommand("GM"); //$NON-NLS-1$
						panel9.add(tokenStatesGM, CC.xy(1, 1));

						tokenStatesOwner.setText("Owner"); //$NON-NLS-1$
						tokenStatesOwner.setSelected(true);
						tokenStatesOwner.setActionCommand("GM"); //$NON-NLS-1$
						panel9.add(tokenStatesOwner, CC.xy(3, 1));

						tokenStatesEverybody.setText("Everybody Else"); //$NON-NLS-1$
						tokenStatesEverybody.setSelected(true);
						tokenStatesEverybody.setActionCommand("GM"); //$NON-NLS-1$
						panel9.add(tokenStatesEverybody, CC.xy(5, 1));
					}
					panel8.add(panel9, CC.xy(1, 1));
				}
				tokenStatesPanel.add(panel8, CC.xywh(2, 7, 16, 1));
			}
			tabbedPane1.addTab("States", tokenStatesPanel); //$NON-NLS-1$

			{
				panel10.setLayout(new FormLayout(
					"default:grow", //$NON-NLS-1$
					"fill:default:grow")); //$NON-NLS-1$

				{
					tokenStatesPanel2.setLayout(new FormLayout(
						"default, right:default, 4dlu, default, 8dlu, right:default, 4dlu, 25px, 8dlu, right:default, 4dlu, 50px, 8dlu, right:default, 4dlu, 50px, 0px:grow, $rgap, default, [10px,default]", //$NON-NLS-1$
						"[10px,default], 2*(default, $nlgap), 2*(default), $nlgap, fill:default, $rgap, fill:default:grow, [10px,default]")); //$NON-NLS-1$

					tokenBarType.setModel(new DefaultComboBoxModel<>(new String[] {
						"Two Image", //$NON-NLS-1$
						"Single Image", //$NON-NLS-1$
						"Multiple Images", //$NON-NLS-1$
						"Solid", //$NON-NLS-1$
						"Two Tone" //$NON-NLS-1$
					}));
					tokenStatesPanel2.add(tokenBarType, CC.xy(4, 4));

					tokenBarColorLabel.setText("Bar Color:"); //$NON-NLS-1$
					tokenStatesPanel2.add(tokenBarColorLabel, CC.xy(6, 4));

					tokenBarColor.setColor(Color.white);
					tokenBarColor.setForeground(Color.white);
					tokenStatesPanel2.add(tokenBarColor, CC.xy(8, 4, CC.DEFAULT, CC.FILL));

					tokenBarThicknessLabel.setText("Thickness:"); //$NON-NLS-1$
					tokenStatesPanel2.add(tokenBarThicknessLabel, CC.xy(10, 4));
					tokenStatesPanel2.add(tokenBarThickness, CC.xy(12, 4));

					label7.setText("Type:"); //$NON-NLS-1$
					tokenStatesPanel2.add(label7, CC.xy(2, 4));

					tokenBarSide.setModel(new DefaultComboBoxModel<>(new String[] {
						"Top", //$NON-NLS-1$
						"Bottom", //$NON-NLS-1$
						"Left", //$NON-NLS-1$
						"Right" //$NON-NLS-1$
					}));
					tokenStatesPanel2.add(tokenBarSide, CC.xy(4, 6));

					label8.setText("Side:"); //$NON-NLS-1$
					tokenStatesPanel2.add(label8, CC.xy(2, 6));

					label9.setText("Name:"); //$NON-NLS-1$
					tokenStatesPanel2.add(label9, CC.xy(2, 2));

					label10.setText("Opacity:"); //$NON-NLS-1$
					tokenStatesPanel2.add(label10, CC.xy(10, 6));
					tokenStatesPanel2.add(tokenBarOpacity, CC.xy(12, 6));

					{

						tokenBarImages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						scrollPane6.setViewportView(tokenBarImages);
					}
					tokenStatesPanel2.add(scrollPane6, CC.xywh(4, 9, 14, 1));

					label11.setText("Increments:"); //$NON-NLS-1$
					tokenStatesPanel2.add(label11, CC.xy(14, 4));
					tokenStatesPanel2.add(tokenBarIncrements, CC.xy(16, 4));

					label12.setText("Mouseover:"); //$NON-NLS-1$
					tokenStatesPanel2.add(label12, CC.xy(14, 6));

					tokenBarBgColorLabel.setText("Background:"); //$NON-NLS-1$
					tokenStatesPanel2.add(tokenBarBgColorLabel, CC.xy(6, 6));
					tokenStatesPanel2.add(tokenBarMouseover, CC.xy(16, 6));

					tokenBarBgColor.setColor(new Color(51, 51, 51));
					tokenStatesPanel2.add(tokenBarBgColor, CC.xy(8, 6, CC.DEFAULT, CC.FILL));

					{
						panel11.setLayout(new FormLayout(
							"default", //$NON-NLS-1$
							"top:default, 4*($rgap, default)")); //$NON-NLS-1$

						tokenBarAddBar.setText("Add"); //$NON-NLS-1$
						tokenBarAddBar.setActionCommand("Add"); //$NON-NLS-1$
						panel11.add(tokenBarAddBar, CC.xy(1, 1));

						tokenBarDeleteBar.setText("Delete"); //$NON-NLS-1$
						tokenBarDeleteBar.setActionCommand("deleteState\t"); //$NON-NLS-1$
						panel11.add(tokenBarDeleteBar, CC.xy(1, 5));

						tokenBarUpdateBar.setText("Update"); //$NON-NLS-1$
						tokenBarUpdateBar.setActionCommand("Add"); //$NON-NLS-1$
						panel11.add(tokenBarUpdateBar, CC.xy(1, 3));

						tokenBarMoveUp.setText("Move Up"); //$NON-NLS-1$
						tokenBarMoveUp.setActionCommand("deleteState\t"); //$NON-NLS-1$
						panel11.add(tokenBarMoveUp, CC.xy(1, 7));

						tokenBarMoveDown.setText("Move Down"); //$NON-NLS-1$
						tokenBarMoveDown.setActionCommand("deleteState\t"); //$NON-NLS-1$
						panel11.add(tokenBarMoveDown, CC.xy(1, 9));
					}
					tokenStatesPanel2.add(panel11, CC.xy(19, 11));

					{
						panel12.setLayout(new FormLayout(
							"0px:grow", //$NON-NLS-1$
							"default")); //$NON-NLS-1$
						panel12.add(tokenBarName, CC.xy(1, 1));
					}
					tokenStatesPanel2.add(panel12, CC.xywh(4, 2, 14, 1));

					{
						panel13.setLayout(new FormLayout(
							"default", //$NON-NLS-1$
							"default")); //$NON-NLS-1$

						{
							panel14.setBorder(new TitledBorder(LineBorder.createBlackLineBorder(), "Show To:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
							panel14.setLayout(new FormLayout(
								"2*(default, $ugap), default", //$NON-NLS-1$
								"default")); //$NON-NLS-1$

							tokenBarGM.setText("GM"); //$NON-NLS-1$
							tokenBarGM.setSelected(true);
							tokenBarGM.setActionCommand("GM"); //$NON-NLS-1$
							panel14.add(tokenBarGM, CC.xy(1, 1));

							tokenBarOwner.setText("Owner"); //$NON-NLS-1$
							tokenBarOwner.setSelected(true);
							tokenBarOwner.setActionCommand("GM"); //$NON-NLS-1$
							panel14.add(tokenBarOwner, CC.xy(3, 1));

							tokenBarEverybody.setText("Everybody Else"); //$NON-NLS-1$
							tokenBarEverybody.setSelected(true);
							tokenBarEverybody.setActionCommand("GM"); //$NON-NLS-1$
							panel14.add(tokenBarEverybody, CC.xy(5, 1));
						}
						panel13.add(panel14, CC.xy(1, 1));
					}
					tokenStatesPanel2.add(panel13, CC.xywh(2, 7, 16, 1));

					{
						panel15.setLayout(new FormLayout(
							"default", //$NON-NLS-1$
							"top:default, 4*($rgap, default)")); //$NON-NLS-1$

						tokenBarAddImage.setText("Add"); //$NON-NLS-1$
						tokenBarAddImage.setActionCommand("Add"); //$NON-NLS-1$
						panel15.add(tokenBarAddImage, CC.xy(1, 1));

						tokenBarDeleteImage.setText("Delete"); //$NON-NLS-1$
						tokenBarDeleteImage.setActionCommand("deleteState\t"); //$NON-NLS-1$
						panel15.add(tokenBarDeleteImage, CC.xy(1, 5));

						tokenBarUpdateImage.setText("Update"); //$NON-NLS-1$
						tokenBarUpdateImage.setActionCommand("Add"); //$NON-NLS-1$
						panel15.add(tokenBarUpdateImage, CC.xy(1, 3));

						tokenBarMoveUpImage.setText("Move Up"); //$NON-NLS-1$
						tokenBarMoveUpImage.setActionCommand("deleteState\t"); //$NON-NLS-1$
						panel15.add(tokenBarMoveUpImage, CC.xy(1, 7));

						tokenBarMoveDownImage.setText("Move Down"); //$NON-NLS-1$
						tokenBarMoveDownImage.setActionCommand("deleteState\t"); //$NON-NLS-1$
						panel15.add(tokenBarMoveDownImage, CC.xy(1, 9));
					}
					tokenStatesPanel2.add(panel15, CC.xy(19, 9));

					tokenBarImagesLabel.setText("Images:"); //$NON-NLS-1$
					tokenStatesPanel2.add(tokenBarImagesLabel, CC.xy(2, 9, CC.DEFAULT, CC.TOP));

					{
						panel16.setLayout(new FormLayout(
							"default, $rgap, default:grow", //$NON-NLS-1$
							"fill:default:grow, default")); //$NON-NLS-1$

						{

							tokenBarBars.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							scrollPane7.setViewportView(tokenBarBars);
						}
						panel16.add(scrollPane7, CC.xywh(3, 1, 1, 2));

						tokenBarTest.setMajorTickSpacing(20);
						tokenBarTest.setPaintTicks(true);
						tokenBarTest.setOrientation(SwingConstants.VERTICAL);
						tokenBarTest.setPaintLabels(true);
						panel16.add(tokenBarTest, CC.xy(1, 1));

						label13.setText("Preview"); //$NON-NLS-1$
						label13.setHorizontalTextPosition(SwingConstants.CENTER);
						panel16.add(label13, CC.xy(1, 2, CC.CENTER, CC.DEFAULT));
					}
					tokenStatesPanel2.add(panel16, CC.xywh(1, 11, 17, 1));
				}
				panel10.add(tokenStatesPanel2, CC.xy(1, 1));
			}
			tabbedPane1.addTab("Bars", panel10); //$NON-NLS-1$
		}
		add(tabbedPane1, CC.xy(2, 2));

		{
			panel17.setLayout(new FormLayout(
				"3*(default, [10px,default]), default", //$NON-NLS-1$
				"default")); //$NON-NLS-1$

			okButton.setText("OK"); //$NON-NLS-1$
			okButton.setActionCommand("OK"); //$NON-NLS-1$
			panel17.add(okButton, CC.xy(5, 1));

			cancelButton.setText("Cancel"); //$NON-NLS-1$
			cancelButton.setActionCommand("Cancel"); //$NON-NLS-1$
			panel17.add(cancelButton, CC.xy(7, 1));

			importButton.setText("Import"); //$NON-NLS-1$
			importButton.setActionCommand("Import"); //$NON-NLS-1$
			panel17.add(importButton, CC.xy(1, 1));

			exportButton.setText("Export"); //$NON-NLS-1$
			exportButton.setActionCommand("Export"); //$NON-NLS-1$
			panel17.add(exportButton, CC.xy(3, 1));
		}
		add(panel17, CC.xy(2, 4, CC.FILL, CC.DEFAULT));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
