package com.t3.client.ui.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jidesoft.combobox.ColorComboBox;

/**
 * @author Virenerus
 */
public abstract class PreferencesBaseDialog extends JDialog {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JTabbedPane tabbedPane1;
	protected JPanel panel1;
	protected JPanel panel2;
	protected JLabel label1;
	protected JCheckBox newMapsHaveFOWCheckBox;
	protected JLabel label2;
	protected JCheckBox newMapsVisibleCheckBox;
	protected JLabel label3;
	protected JComboBox<String> defaultGridTypeCombo;
	protected JLabel label4;
	protected JTextField defaultGridSizeTextField;
	protected JLabel label5;
	protected JTextField defaultUnitsPerCellTextField;
	protected JLabel label6;
	protected JComboBox<com.t3.client.walker.WalkerMetric> movementMetricCombo;
	protected JLabel label7;
	protected JTextField defaultVisionDistanceTextField;
	protected JPanel panel3;
	protected JLabel label8;
	protected JCheckBox tokensStartSnapToGridCheckBox;
	protected JLabel label9;
	protected JCheckBox newTokensVisibleCheckBox;
	protected JLabel label10;
	protected JComboBox<String> duplicateTokenCombo;
	protected JLabel label11;
	protected JComboBox<String> showNumberingCombo;
	protected JLabel label12;
	protected JComboBox<String> tokenNamingCombo;
	protected JLabel label13;
	protected JCheckBox tokensStartFreeSizeCheckBox;
	protected JLabel label14;
	protected JCheckBox showDialogOnNewTokenCheckBox;
	protected JLabel label15;
	protected JTextField statsheetPortraitSize;
	protected JLabel label16;
	protected JCheckBox tokensPopupWarningWhenDeletedCheckBox;
	protected JLabel label17;
	protected JCheckBox showStatSheetCheckBox;
	protected JPanel panel4;
	protected JLabel label18;
	protected JCheckBox stampsStartSnapToGridCheckBox;
	protected JLabel label19;
	protected JCheckBox stampsStartFreeSizeCheckBox;
	protected JPanel panel5;
	protected JLabel label20;
	protected JCheckBox backgroundsStartSnapToGridCheckBox;
	protected JLabel label21;
	protected JCheckBox backgroundsStartFreeSizeCheckBox;
	protected JPanel panel6;
	protected JLabel label22;
	protected JCheckBox showChatAvatarCheckBox;
	protected JCheckBox showSmiliesCheckBox;
	protected JLabel label23;
	protected JLabel label25;
	protected JLabel label26;
	protected ColorComboBox trustedOuputBackground;
	protected ColorComboBox trustedOuputForeground;
	protected JLabel label27;
	protected JLabel label28;
	protected ColorComboBox chatNotificationColor;
	protected JLabel label29;
	protected JCheckBox chatNotificationShowBackgroundCheckBox;
	protected JSpinner typingNotificationDuration;
	protected JLabel label30;
	protected JCheckBox suppressToolTipsMacroLinksCheckBox;
	protected JPanel panel7;
	protected JLabel label31;
	protected JCheckBox facingFaceEdgesCheckBox;
	protected JLabel label32;
	protected JCheckBox facingFaceVerticesCheckBox;
	protected JPanel panel8;
	protected JLabel label33;
	protected JTextField fontSizeTextField;
	protected JLabel label34;
	protected JLabel label35;
	protected JTextField toolTipInitialDelay;
	protected JTextField toolTipDismissDelay;
	protected JPanel panel9;
	protected JPanel panel10;
	protected JLabel label36;
	protected JSpinner haloLineWidthSpinner;
	protected JSpinner haloOverlayOpacitySpinner;
	protected JLabel label37;
	protected JLabel label38;
	protected JCheckBox autoRevealVisionOnGMMoveCheckBox;
	protected JLabel label39;
	protected JLabel label40;
	protected JSpinner auraOverlayOpacitySpinner;
	protected JSpinner lightOverlayOpacitySpinner;
	protected JLabel label41;
	protected JSpinner fogOverlayOpacitySpinner;
	protected JLabel label42;
	protected JCheckBox useHaloColorAsVisionOverlayCheckBox;
	protected JPanel panel11;
	protected JLabel label43;
	protected JSpinner autoSaveSpinner;
	protected JLabel label44;
	protected JLabel label45;
	protected JCheckBox saveReminderCheckBox;
	protected JLabel label46;
	protected JLabel label47;
	protected JLabel label48;
	protected JTextField chatFilenameFormat;
	protected JSpinner chatAutosaveTime;
	protected JPanel panel12;
	protected JPanel panel13;
	protected JCheckBox fillSelectionCheckBox;
	protected JLabel label49;
	protected JPanel panel14;
	protected JLabel label50;
	protected JCheckBox hideNPCsCheckBox;
	protected JLabel label51;
	protected JCheckBox ownerPermissionCheckBox;
	protected JLabel label52;
	protected JCheckBox lockMovementCheckBox;
	protected JLabel label53;
	protected JCheckBox showInitGainMessageCheckBox;
	protected JPanel panel15;
	protected JLabel label54;
	protected JCheckBox fitGMViewCheckBox;
	protected JPanel panel16;
	protected JLabel label55;
	protected JCheckBox allowPlayerMacroEditsDefaultCheckBox;
	protected JPanel panel17;
	protected JCheckBox playSystemSoundsCheckBox;
	protected JCheckBox soundsOnlyWhenNotFocusedCheckBox;
	protected JLabel label56;
	protected JLabel label57;
	protected JPanel panel18;
	protected JButton okButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public PreferencesBaseDialog(Frame owner) {
		super(owner);
		initComponents();
	}

	public PreferencesBaseDialog(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		tabbedPane1 = new JTabbedPane();
		panel1 = new JPanel();
		panel2 = new JPanel();
		label1 = new JLabel();
		newMapsHaveFOWCheckBox = new JCheckBox();
		label2 = new JLabel();
		newMapsVisibleCheckBox = new JCheckBox();
		label3 = new JLabel();
		defaultGridTypeCombo = new JComboBox<>();
		label4 = new JLabel();
		defaultGridSizeTextField = new JTextField();
		label5 = new JLabel();
		defaultUnitsPerCellTextField = new JTextField();
		label6 = new JLabel();
		movementMetricCombo = new JComboBox<>();
		label7 = new JLabel();
		defaultVisionDistanceTextField = new JTextField();
		panel3 = new JPanel();
		label8 = new JLabel();
		tokensStartSnapToGridCheckBox = new JCheckBox();
		label9 = new JLabel();
		newTokensVisibleCheckBox = new JCheckBox();
		label10 = new JLabel();
		duplicateTokenCombo = new JComboBox<>();
		label11 = new JLabel();
		showNumberingCombo = new JComboBox<>();
		label12 = new JLabel();
		tokenNamingCombo = new JComboBox<>();
		label13 = new JLabel();
		tokensStartFreeSizeCheckBox = new JCheckBox();
		label14 = new JLabel();
		showDialogOnNewTokenCheckBox = new JCheckBox();
		label15 = new JLabel();
		statsheetPortraitSize = new JTextField();
		label16 = new JLabel();
		tokensPopupWarningWhenDeletedCheckBox = new JCheckBox();
		label17 = new JLabel();
		showStatSheetCheckBox = new JCheckBox();
		panel4 = new JPanel();
		label18 = new JLabel();
		stampsStartSnapToGridCheckBox = new JCheckBox();
		label19 = new JLabel();
		stampsStartFreeSizeCheckBox = new JCheckBox();
		panel5 = new JPanel();
		label20 = new JLabel();
		backgroundsStartSnapToGridCheckBox = new JCheckBox();
		label21 = new JLabel();
		backgroundsStartFreeSizeCheckBox = new JCheckBox();
		panel6 = new JPanel();
		label22 = new JLabel();
		showChatAvatarCheckBox = new JCheckBox();
		showSmiliesCheckBox = new JCheckBox();
		label23 = new JLabel();
		label25 = new JLabel();
		label26 = new JLabel();
		trustedOuputBackground = new ColorComboBox();
		trustedOuputForeground = new ColorComboBox();
		label27 = new JLabel();
		label28 = new JLabel();
		chatNotificationColor = new ColorComboBox();
		label29 = new JLabel();
		chatNotificationShowBackgroundCheckBox = new JCheckBox();
		typingNotificationDuration = new JSpinner();
		label30 = new JLabel();
		suppressToolTipsMacroLinksCheckBox = new JCheckBox();
		panel7 = new JPanel();
		label31 = new JLabel();
		facingFaceEdgesCheckBox = new JCheckBox();
		label32 = new JLabel();
		facingFaceVerticesCheckBox = new JCheckBox();
		panel8 = new JPanel();
		label33 = new JLabel();
		fontSizeTextField = new JTextField();
		label34 = new JLabel();
		label35 = new JLabel();
		toolTipInitialDelay = new JTextField();
		toolTipDismissDelay = new JTextField();
		panel9 = new JPanel();
		panel10 = new JPanel();
		label36 = new JLabel();
		haloLineWidthSpinner = new JSpinner();
		haloOverlayOpacitySpinner = new JSpinner();
		label37 = new JLabel();
		label38 = new JLabel();
		autoRevealVisionOnGMMoveCheckBox = new JCheckBox();
		label39 = new JLabel();
		label40 = new JLabel();
		auraOverlayOpacitySpinner = new JSpinner();
		lightOverlayOpacitySpinner = new JSpinner();
		label41 = new JLabel();
		fogOverlayOpacitySpinner = new JSpinner();
		label42 = new JLabel();
		useHaloColorAsVisionOverlayCheckBox = new JCheckBox();
		panel11 = new JPanel();
		label43 = new JLabel();
		autoSaveSpinner = new JSpinner();
		label44 = new JLabel();
		label45 = new JLabel();
		saveReminderCheckBox = new JCheckBox();
		label46 = new JLabel();
		label47 = new JLabel();
		label48 = new JLabel();
		chatFilenameFormat = new JTextField();
		chatAutosaveTime = new JSpinner();
		panel12 = new JPanel();
		panel13 = new JPanel();
		fillSelectionCheckBox = new JCheckBox();
		label49 = new JLabel();
		panel14 = new JPanel();
		label50 = new JLabel();
		hideNPCsCheckBox = new JCheckBox();
		label51 = new JLabel();
		ownerPermissionCheckBox = new JCheckBox();
		label52 = new JLabel();
		lockMovementCheckBox = new JCheckBox();
		label53 = new JLabel();
		showInitGainMessageCheckBox = new JCheckBox();
		panel15 = new JPanel();
		label54 = new JLabel();
		fitGMViewCheckBox = new JCheckBox();
		panel16 = new JPanel();
		label55 = new JLabel();
		allowPlayerMacroEditsDefaultCheckBox = new JCheckBox();
		panel17 = new JPanel();
		playSystemSoundsCheckBox = new JCheckBox();
		soundsOnlyWhenNotFocusedCheckBox = new JCheckBox();
		label56 = new JLabel();
		label57 = new JLabel();
		panel18 = new JPanel();
		okButton = new JButton();

		setTitle("Preferences"); //$NON-NLS-1$
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		{

			{

				panel1.setLayout(new FormLayout(
					"$ugap, 2*(default, [10px,default]), default", //$NON-NLS-1$
					"2*([10px,default], default), [10px,default]")); //$NON-NLS-1$

				{
					panel2.setBorder(new TitledBorder(new LineBorder(new Color(153, 153, 153)), "Maps", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
					panel2.setLayout(new FormLayout(
						"[10px,default], default:grow, [10px,default], default, [10px,default]", //$NON-NLS-1$
						"$nlgap, 6*(default, [10px,default]), default, $nlgap")); //$NON-NLS-1$

					label1.setText("New maps have Fog of War"); //$NON-NLS-1$
					label1.setToolTipText("Fog of War can be enabled or disabled on individual maps."); //$NON-NLS-1$
					panel2.add(label1, CC.xy(2, 2));

					newMapsHaveFOWCheckBox.setActionCommand("New maps have fog of war"); //$NON-NLS-1$
					panel2.add(newMapsHaveFOWCheckBox, CC.xy(4, 2));

					label2.setText("New maps visible to players"); //$NON-NLS-1$
					label2.setToolTipText("Individual maps may or may not be visible to players."); //$NON-NLS-1$
					panel2.add(label2, CC.xy(2, 4));

					newMapsVisibleCheckBox.setActionCommand("New maps are visible to players"); //$NON-NLS-1$
					panel2.add(newMapsVisibleCheckBox, CC.xy(4, 4));

					label3.setText("New Map Grid Type"); //$NON-NLS-1$
					label3.setToolTipText("The grid type for new maps.  Cannot be changed once the map has been created."); //$NON-NLS-1$
					panel2.add(label3, CC.xy(2, 6));
					panel2.add(defaultGridTypeCombo, CC.xy(4, 6));

					label4.setText("New Map Grid Size"); //$NON-NLS-1$
					label4.setToolTipText("Number of map pixels that represents one unit of distance.  Cannot be changed once the map has been created."); //$NON-NLS-1$
					panel2.add(label4, CC.xy(2, 8));
					panel2.add(defaultGridSizeTextField, CC.xy(4, 8));

					label5.setText("New Map Units Per Cell"); //$NON-NLS-1$
					label5.setToolTipText("The size of a grid cell in game-system units.  Cannot be changed once the map has been created."); //$NON-NLS-1$
					panel2.add(label5, CC.xy(2, 10));

					defaultUnitsPerCellTextField.setToolTipText("Only applies to newly created maps."); //$NON-NLS-1$
					panel2.add(defaultUnitsPerCellTextField, CC.xy(4, 10));

					label6.setText("Movement metric"); //$NON-NLS-1$
					label6.setToolTipText("Determines how TabletopTool handles movement on the selected grid type."); //$NON-NLS-1$
					panel2.add(label6, CC.xy(2, 14));
					panel2.add(movementMetricCombo, CC.xy(4, 14));

					label7.setText("New Map Vision Distance"); //$NON-NLS-1$
					label7.setToolTipText("The distance at which TabletopTool should stop calculating sight lines.  Cannot be changed once the map has been created."); //$NON-NLS-1$
					panel2.add(label7, CC.xy(2, 12));
					panel2.add(defaultVisionDistanceTextField, CC.xy(4, 12));
				}
				panel1.add(panel2, CC.xy(2, 2, CC.FILL, CC.FILL));

				{
					panel3.setBorder(new TitledBorder(new LineBorder(new Color(153, 153, 153)), "Tokens", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
					panel3.setLayout(new FormLayout(
						"[10px,default], default:grow, [10px,default], default, [10px,default]", //$NON-NLS-1$
						"$nlgap, 9*(default, [10px,default]), default, $nlgap")); //$NON-NLS-1$

					label8.setText("Start Snap to Grid"); //$NON-NLS-1$
					label8.setToolTipText("Whether tokens have snap-to-grid turned on by default."); //$NON-NLS-1$
					panel3.add(label8, CC.xy(2, 2));

					tokensStartSnapToGridCheckBox.setActionCommand("Tokens start with snap to grid"); //$NON-NLS-1$
					panel3.add(tokensStartSnapToGridCheckBox, CC.xy(4, 2));

					label9.setText("New tokens visible to players"); //$NON-NLS-1$
					label9.setToolTipText("Whether tokens dropped by the GM are immediately visible to players.  (Note that token owners can always see their tokens.)"); //$NON-NLS-1$
					panel3.add(label9, CC.xy(2, 4));

					newTokensVisibleCheckBox.setActionCommand("New tokens are visible to players"); //$NON-NLS-1$
					panel3.add(newTokensVisibleCheckBox, CC.xy(4, 4));

					label10.setText("Duplicate Token Numbering"); //$NON-NLS-1$
					label10.setToolTipText("When a token is pasted or dropped onto a map, how should duplicate names be resolved."); //$NON-NLS-1$
					panel3.add(label10, CC.xy(2, 8));

					duplicateTokenCombo.setModel(new DefaultComboBoxModel<>(new String[] {

					}));
					panel3.add(duplicateTokenCombo, CC.xy(4, 8));

					label11.setText("Show Numbering on"); //$NON-NLS-1$
					panel3.add(label11, CC.xy(2, 10));

					showNumberingCombo.setModel(new DefaultComboBoxModel<>(new String[] {

					}));
					panel3.add(showNumberingCombo, CC.xy(4, 10));

					label12.setText("New Token Naming"); //$NON-NLS-1$
					label12.setToolTipText("Determines how new tokens are initially named."); //$NON-NLS-1$
					panel3.add(label12, CC.xy(2, 12));

					tokenNamingCombo.setModel(new DefaultComboBoxModel<>(new String[] {

					}));
					panel3.add(tokenNamingCombo, CC.xy(4, 12));

					label13.setText("Start Freesize"); //$NON-NLS-1$
					label13.setToolTipText("If enabled, new tokens will be shown at their native resolution."); //$NON-NLS-1$
					panel3.add(label13, CC.xy(2, 14));

					tokensStartFreeSizeCheckBox.setActionCommand("Start tokens in freesize"); //$NON-NLS-1$
					panel3.add(tokensStartFreeSizeCheckBox, CC.xy(4, 14));

					label14.setText("Show Dialog on New Token"); //$NON-NLS-1$
					label14.setToolTipText("Determines whether the New Token dialog appears when a token is dropped onto the map."); //$NON-NLS-1$
					panel3.add(label14, CC.xy(2, 16));

					showDialogOnNewTokenCheckBox.setActionCommand("Show token creation dialog"); //$NON-NLS-1$
					panel3.add(showDialogOnNewTokenCheckBox, CC.xy(4, 16));

					label15.setText("Statsheet Portrait Size"); //$NON-NLS-1$
					label15.setToolTipText("Size of the image that appears next to the statsheet on mouseover.  Set to zero to disable portraits."); //$NON-NLS-1$
					panel3.add(label15, CC.xy(2, 18));

					statsheetPortraitSize.setColumns(5);
					panel3.add(statsheetPortraitSize, CC.xy(4, 18));

					label16.setText("Warn when tokens are deleted"); //$NON-NLS-1$
					label16.setToolTipText("There is no \"undo\" for token deletion.  You probably want this enabled."); //$NON-NLS-1$
					panel3.add(label16, CC.xy(2, 6));

					tokensPopupWarningWhenDeletedCheckBox.setActionCommand("Warn when tokens are deleted"); //$NON-NLS-1$
					panel3.add(tokensPopupWarningWhenDeletedCheckBox, CC.xy(4, 6));

					label17.setText("Show stat sheet on mouseover"); //$NON-NLS-1$
					label17.setToolTipText("Whether to show the statsheet.  The portrait is controlled separately."); //$NON-NLS-1$
					panel3.add(label17, CC.xy(2, 20));

					showStatSheetCheckBox.setActionCommand("Show statsheet on mouseover"); //$NON-NLS-1$
					panel3.add(showStatSheetCheckBox, CC.xy(4, 20));
				}
				panel1.add(panel3, CC.xy(4, 2, CC.FILL, CC.FILL));

				{
					panel4.setBorder(new TitledBorder(new LineBorder(new Color(153, 153, 153)), "Objects", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
					panel4.setLayout(new FormLayout(
						"[10px,default], default:grow, [10px,default], default, [10px,default]", //$NON-NLS-1$
						"$nlgap, default, [10px,default], default, $nlgap")); //$NON-NLS-1$

					label18.setText("Start Snap to Grid"); //$NON-NLS-1$
					label18.setToolTipText("Whether images dropped on the Object layer have snap-to-grid turned on by default."); //$NON-NLS-1$
					panel4.add(label18, CC.xy(2, 2));

					stampsStartSnapToGridCheckBox.setActionCommand("Stamps start Snap to Grid"); //$NON-NLS-1$
					panel4.add(stampsStartSnapToGridCheckBox, CC.xy(4, 2));

					label19.setText("Start Freesize"); //$NON-NLS-1$
					label19.setToolTipText("If enabled, new objects will be shown at their native resolution with a resizing handle attached."); //$NON-NLS-1$
					panel4.add(label19, CC.xy(2, 4));

					stampsStartFreeSizeCheckBox.setActionCommand("Stamps start Freesize"); //$NON-NLS-1$
					panel4.add(stampsStartFreeSizeCheckBox, CC.xy(4, 4));
				}
				panel1.add(panel4, CC.xy(2, 4, CC.FILL, CC.FILL));

				{
					panel5.setBorder(new TitledBorder(new LineBorder(new Color(153, 153, 153)), "Backgrounds", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
					panel5.setLayout(new FormLayout(
						"[10px,default], default:grow, [10px,default], default, [10px,default]", //$NON-NLS-1$
						"$nlgap, default, [10px,default], default, $nlgap")); //$NON-NLS-1$

					label20.setText("Start Snap to Grid"); //$NON-NLS-1$
					label20.setToolTipText("Whether images dropped on the Background layer have snap-to-grid turned on by default."); //$NON-NLS-1$
					panel5.add(label20, CC.xy(2, 2));

					backgroundsStartSnapToGridCheckBox.setActionCommand("Backgrounds start Snap to Grid"); //$NON-NLS-1$
					panel5.add(backgroundsStartSnapToGridCheckBox, CC.xy(4, 2));

					label21.setText("Start Freesize"); //$NON-NLS-1$
					label21.setToolTipText("If enabled, new background images will be shown at their native resolution with a resizing handle attached."); //$NON-NLS-1$
					panel5.add(label21, CC.xy(2, 4));

					backgroundsStartFreeSizeCheckBox.setActionCommand("Backgrounds start Freesize"); //$NON-NLS-1$
					panel5.add(backgroundsStartFreeSizeCheckBox, CC.xy(4, 4));
				}
				panel1.add(panel5, CC.xy(4, 4, CC.FILL, CC.FILL));

				{
					panel6.setBorder(new TitledBorder(new LineBorder(new Color(153, 153, 153)), "Chat", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
					panel6.setLayout(new FormLayout(
						"[10px,default], default:grow, [10px,default], 23dlu, [10px,default]", //$NON-NLS-1$
						"$nlgap, 7*(default, [10px,default]), default, $nlgap")); //$NON-NLS-1$

					label22.setText("Show Avatar per line"); //$NON-NLS-1$
					label22.setToolTipText("Causes an icon of the impersonated token to appear at the beginning of each chat message."); //$NON-NLS-1$
					panel6.add(label22, CC.xy(2, 2));

					showChatAvatarCheckBox.setActionCommand("Show avatar on each chat line"); //$NON-NLS-1$
					panel6.add(showChatAvatarCheckBox, CC.xy(4, 2));

					showSmiliesCheckBox.setActionCommand("Insert smilies"); //$NON-NLS-1$
					panel6.add(showSmiliesCheckBox, CC.xy(4, 4));

					label23.setText("Insert Smilies"); //$NON-NLS-1$
					label23.setToolTipText("Certain strings of text are treated as smilies and replaced with images.  A popup panel of smilies appears to the right of the chat input box."); //$NON-NLS-1$
					panel6.add(label23, CC.xy(2, 4));

					label25.setText("Trusted Prefix Background"); //$NON-NLS-1$
					label25.setToolTipText("Text generated by trusted macros is forced to a particular background color."); //$NON-NLS-1$
					panel6.add(label25, CC.xy(2, 14));

					label26.setText("Trusted Prefix Foreground"); //$NON-NLS-1$
					label26.setToolTipText("Text generated by trusted macros is forced to a particular foreground color."); //$NON-NLS-1$
					panel6.add(label26, CC.xy(2, 16));

					trustedOuputBackground.setColorValueVisible(false);
					trustedOuputBackground.setSelectedColor(Color.black);
					panel6.add(trustedOuputBackground, CC.xy(4, 14));

					trustedOuputForeground.setColorValueVisible(false);
					trustedOuputForeground.setSelectedColor(Color.black);
					panel6.add(trustedOuputForeground, CC.xy(4, 16));

					label27.setText("Typing Notification Duration Seconds"); //$NON-NLS-1$
					label27.setToolTipText("Time before typing notifications disappear (in milliseconds)."); //$NON-NLS-1$
					panel6.add(label27, CC.xy(2, 8));

					label28.setText("Typing Notification Color"); //$NON-NLS-1$
					label28.setToolTipText("Color of the text for typing notifications"); //$NON-NLS-1$
					panel6.add(label28, CC.xy(2, 10));

					chatNotificationColor.setUseAlphaColorButtons(false);
					chatNotificationColor.setColorValueVisible(false);
					chatNotificationColor.setInvalidValueAllowed(true);
					chatNotificationColor.setSelectedColor(Color.black);
					panel6.add(chatNotificationColor, CC.xy(4, 10));

					label29.setText("Show Chat Notification Background"); //$NON-NLS-1$
					label29.setToolTipText("Color of the text for typing notifications"); //$NON-NLS-1$
					panel6.add(label29, CC.xy(2, 12));

					chatNotificationShowBackgroundCheckBox.setText("\t"); //$NON-NLS-1$
					chatNotificationShowBackgroundCheckBox.setActionCommand("Show Chat Notification Background\t"); //$NON-NLS-1$
					panel6.add(chatNotificationShowBackgroundCheckBox, CC.xy(4, 12));
					panel6.add(typingNotificationDuration, CC.xy(4, 8));

					label30.setText("Suppress ToolTips for MacroLinks"); //$NON-NLS-1$
					label30.setToolTipText("MacroLinks show normally tooltips that state informations about the link target. This is a anti cheating device. This options let you disable this tooltips for aesthetic reasons. "); //$NON-NLS-1$
					panel6.add(label30, CC.xy(2, 6));

					suppressToolTipsMacroLinksCheckBox.setActionCommand("Use tooltips for inline rolls"); //$NON-NLS-1$
					suppressToolTipsMacroLinksCheckBox.setToolTipText("<html>Enabled: do not show tooltips for macroLinks<br>\nDisabled (default): show tooltips for macroLinks"); //$NON-NLS-1$
					panel6.add(suppressToolTipsMacroLinksCheckBox, CC.xy(4, 6));
				}
				panel1.add(panel6, CC.xy(6, 2, CC.DEFAULT, CC.TOP));

				{
					panel7.setBorder(new TitledBorder(new LineBorder(new Color(153, 153, 153)), "Facing", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
					panel7.setLayout(new FormLayout(
						"[10px,default], default:grow, [10px,default], default, [10px,default]", //$NON-NLS-1$
						"$nlgap, default, [10px,default], default, $nlgap")); //$NON-NLS-1$

					label31.setText("On Edges"); //$NON-NLS-1$
					label31.setToolTipText("Causes token facing rotation to snap to edges of the grid cell."); //$NON-NLS-1$
					panel7.add(label31, CC.xy(2, 2));

					facingFaceEdgesCheckBox.setActionCommand("Tokens can face edges."); //$NON-NLS-1$
					panel7.add(facingFaceEdgesCheckBox, CC.xy(4, 2));

					label32.setText("On Vertices"); //$NON-NLS-1$
					label32.setToolTipText("Causes token facing rotation to snap to the vertices of the grid cell."); //$NON-NLS-1$
					panel7.add(label32, CC.xy(2, 4));

					facingFaceVerticesCheckBox.setActionCommand("Tokens can face vertices."); //$NON-NLS-1$
					panel7.add(facingFaceVerticesCheckBox, CC.xy(4, 4));
				}
				panel1.add(panel7, CC.xy(6, 4));
			}
			tabbedPane1.addTab("Interactions", panel1); //$NON-NLS-1$

			{
				panel8.setLayout(new FormLayout(
					"2*([10px,default], default), [10px,default]", //$NON-NLS-1$
					"3*([10px,default], default), [10px,default]")); //$NON-NLS-1$

				label33.setText("Chat Font Size"); //$NON-NLS-1$
				label33.setToolTipText("Point size for all chat window text (both input and output)."); //$NON-NLS-1$
				panel8.add(label33, CC.xy(2, 2));

				fontSizeTextField.setColumns(4);
				panel8.add(fontSizeTextField, CC.xy(4, 2));

				label34.setText("ToolTip Initial Delay"); //$NON-NLS-1$
				label34.setToolTipText("The initial delay (in milliseconds) before a tooltip is displayed."); //$NON-NLS-1$
				panel8.add(label34, CC.xy(2, 4));

				label35.setText("ToolTip Dismiss Delay"); //$NON-NLS-1$
				label35.setToolTipText("The amount of time (in milliseconds) the tooltip will remain before being removed."); //$NON-NLS-1$
				panel8.add(label35, CC.xy(2, 6));

				toolTipInitialDelay.setColumns(4);
				panel8.add(toolTipInitialDelay, CC.xy(4, 4));

				toolTipDismissDelay.setColumns(4);
				panel8.add(toolTipDismissDelay, CC.xy(4, 6));
			}
			tabbedPane1.addTab("Accessibility", panel8); //$NON-NLS-1$

			{
				panel9.setLayout(new FormLayout(
					"2*([10px,default], default:grow), [10px,default]", //$NON-NLS-1$
					"[10px,default], 4*(default), [10px,default]")); //$NON-NLS-1$

				{
					panel10.setBorder(new TitledBorder(new LineBorder(new Color(153, 153, 153)), "Map Visuals", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
					panel10.setLayout(new FormLayout(
						"[10px,default], default:grow, [10px,default], 40dlu, [10px,default], 15dlu, [10px,default]", //$NON-NLS-1$
						"$nlgap, 6*(default, [10px,default]), default, $nlgap")); //$NON-NLS-1$

					label36.setText("Halo line width"); //$NON-NLS-1$
					label36.setToolTipText("The width of the single-color halo around individual tokens."); //$NON-NLS-1$
					panel10.add(label36, CC.xy(2, 2));
					panel10.add(haloLineWidthSpinner, CC.xy(4, 2));
					panel10.add(haloOverlayOpacitySpinner, CC.xy(4, 6));

					label37.setText("Halo opacity"); //$NON-NLS-1$
					label37.setToolTipText("Measures how opaque the halo vision overlay is drawn (0-255)."); //$NON-NLS-1$
					panel10.add(label37, CC.xy(2, 6));

					label38.setText("Auto-expose fog on token movement (GM Only)"); //$NON-NLS-1$
					label38.setToolTipText("If enabled, the fog of war is automatically exposed as a token moves on maps with a grid.  Gridless maps cannot auto-expose fog."); //$NON-NLS-1$
					panel10.add(label38, CC.xy(2, 14));

					autoRevealVisionOnGMMoveCheckBox.setActionCommand("Auto-expose Fog"); //$NON-NLS-1$
					panel10.add(autoRevealVisionOnGMMoveCheckBox, CC.xy(4, 14));

					label39.setText("Aura opacity"); //$NON-NLS-1$
					label39.setToolTipText("Measures how opaque the aura overlay is drawn (0-255)."); //$NON-NLS-1$
					panel10.add(label39, CC.xy(2, 8));

					label40.setText("Light opacity"); //$NON-NLS-1$
					label40.setToolTipText("Measures how opaque the light overlay is drawn (0-255)."); //$NON-NLS-1$
					panel10.add(label40, CC.xy(2, 10));
					panel10.add(auraOverlayOpacitySpinner, CC.xy(4, 8));
					panel10.add(lightOverlayOpacitySpinner, CC.xy(4, 10));

					label41.setText("Fog opacity"); //$NON-NLS-1$
					label41.setToolTipText("Measures how opaque the \"soft fog\" overlay is drawn (0-255)."); //$NON-NLS-1$
					panel10.add(label41, CC.xy(2, 12));
					panel10.add(fogOverlayOpacitySpinner, CC.xy(4, 12));

					label42.setText("Use halo color for vision"); //$NON-NLS-1$
					label42.setToolTipText("If enabled, the area visible to a token will be colored the same as the halo."); //$NON-NLS-1$
					panel10.add(label42, CC.xy(2, 4));

					useHaloColorAsVisionOverlayCheckBox.setActionCommand("Use halo color for vision"); //$NON-NLS-1$
					panel10.add(useHaloColorAsVisionOverlayCheckBox, CC.xy(4, 4));
				}
				panel9.add(panel10, CC.xy(2, 4));

				{
					panel11.setBorder(new TitledBorder(new LineBorder(new Color(153, 153, 153)), "Save", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
					panel11.setLayout(new FormLayout(
						"[10px,default], default:grow, [10px,default], 40dlu, default, 15dlu, 1px:grow, [10px,default]", //$NON-NLS-1$
						"$nlgap, 3*(default, [10px,default]), default, $nlgap")); //$NON-NLS-1$

					label43.setText("Campaign autosave every"); //$NON-NLS-1$
					label43.setToolTipText("<html>Autosaved campaigns are in <b>${appHome}/autosave</b>.  Autosaving a campaign is memory-intensive.  Be sure to take that into account.  Set to 0 to disable."); //$NON-NLS-1$
					panel11.add(label43, CC.xy(2, 2));
					panel11.add(autoSaveSpinner, CC.xy(4, 2));

					label44.setText("min"); //$NON-NLS-1$
					panel11.add(label44, CC.xy(6, 2));

					label45.setText("Save reminder on close"); //$NON-NLS-1$
					label45.setToolTipText("Whether a prompt appears before TabletopTool closes."); //$NON-NLS-1$
					panel11.add(label45, CC.xy(2, 4));

					saveReminderCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
					saveReminderCheckBox.setActionCommand("Save reminder on close"); //$NON-NLS-1$
					panel11.add(saveReminderCheckBox, CC.xy(4, 4));

					label46.setText("Time between autosaves"); //$NON-NLS-1$
					label46.setToolTipText("The chat log will be autosaved at this interval (in minutes) using the filename pattern below.  Set to 0 to disable."); //$NON-NLS-1$
					panel11.add(label46, CC.xy(2, 6));

					label47.setText("min"); //$NON-NLS-1$
					panel11.add(label47, CC.xy(6, 6));

					label48.setText("Autosave chat log filename"); //$NON-NLS-1$
					label48.setToolTipText("<html>Specify a filename.  Without a directory name is relative to <b>${appHome}/autosave</b>."); //$NON-NLS-1$
					panel11.add(label48, CC.xy(2, 8));
					panel11.add(chatFilenameFormat, CC.xywh(4, 8, 4, 1));
					panel11.add(chatAutosaveTime, CC.xy(4, 6));
				}
				panel9.add(panel11, CC.xy(2, 2));

				{
					panel12.setLayout(new FormLayout(
						"default:grow", //$NON-NLS-1$
						"3*(default, [10px,default]), default")); //$NON-NLS-1$

					{
						panel13.setBorder(new TitledBorder(new LineBorder(new Color(153, 153, 153)), "Performance", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
						panel13.setLayout(new FormLayout(
							"[10px,default], default:grow, [10px,default], 40dlu, 3*([10px,default])", //$NON-NLS-1$
							"$nlgap, default, $nlgap")); //$NON-NLS-1$

						fillSelectionCheckBox.setActionCommand("Fill selection box"); //$NON-NLS-1$
						panel13.add(fillSelectionCheckBox, CC.xy(4, 2));

						label49.setText("Fill selection box"); //$NON-NLS-1$
						label49.setToolTipText("If enabled, a shaded area is used when dragging the mouse to select multiple tokens."); //$NON-NLS-1$
						panel13.add(label49, CC.xy(2, 2));
					}
					panel12.add(panel13, CC.xy(1, 1));

					{
						panel14.setBorder(new TitledBorder(new LineBorder(new Color(153, 153, 153)), "Initiative Defaults", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
						panel14.setLayout(new FormLayout(
							"[10px,default], default, [10px,default], 40dlu, 3*([10px,default])", //$NON-NLS-1$
							"$nlgap, 3*(default, [10px,default]), default, $nlgap")); //$NON-NLS-1$

						label50.setText("Hide NPCs from players on new maps"); //$NON-NLS-1$
						label50.setToolTipText("If enabled, NPCs will not appear in the players views of the Initiative panel."); //$NON-NLS-1$
						panel14.add(label50, CC.xy(2, 2));

						hideNPCsCheckBox.setActionCommand("Hide NPCs from players on new maps"); //$NON-NLS-1$
						panel14.add(hideNPCsCheckBox, CC.xy(4, 2));

						label51.setText("Give Owners Permission in new campaigns"); //$NON-NLS-1$
						label51.setToolTipText("Owner Permission allows players to perform certain actions on their tokens in the Initiative panel."); //$NON-NLS-1$
						panel14.add(label51, CC.xy(2, 4));

						ownerPermissionCheckBox.setActionCommand("Give owners Permission in new campaigns"); //$NON-NLS-1$
						panel14.add(ownerPermissionCheckBox, CC.xy(4, 4));

						label52.setText("Lock Player Movement in new campaigns"); //$NON-NLS-1$
						label52.setToolTipText("When enabled, players will only be able to move their token when that token has initiative."); //$NON-NLS-1$
						panel14.add(label52, CC.xy(2, 6));

						lockMovementCheckBox.setActionCommand("Lock Player Movement in new campaigns"); //$NON-NLS-1$
						panel14.add(lockMovementCheckBox, CC.xy(4, 6));

						label53.setText("Show Initative Gain Message"); //$NON-NLS-1$
						label53.setToolTipText("When enabled, a message is sent to chat when a token gains initative."); //$NON-NLS-1$
						panel14.add(label53, CC.xy(2, 8));

						showInitGainMessageCheckBox.setActionCommand("Lock Player Movement in new campaigns"); //$NON-NLS-1$
						panel14.add(showInitGainMessageCheckBox, CC.xy(4, 8));
					}
					panel12.add(panel14, CC.xy(1, 3));

					{
						panel15.setBorder(new TitledBorder(new LineBorder(new Color(153, 153, 153)), "Client", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
						panel15.setLayout(new FormLayout(
							"[10px,default], default:grow, [10px,default], 40dlu, 3*([10px,default])", //$NON-NLS-1$
							"default, $nlgap")); //$NON-NLS-1$

						label54.setText("Fit GM view "); //$NON-NLS-1$
						label54.setToolTipText("When forcing players to the GM's view, should the player's map be zoomed such that their screen shows at least the same content as the GM's screen?"); //$NON-NLS-1$
						panel15.add(label54, CC.xy(2, 1));

						fitGMViewCheckBox.setActionCommand("Fit GM view"); //$NON-NLS-1$
						panel15.add(fitGMViewCheckBox, CC.xy(4, 1));
					}
					panel12.add(panel15, CC.xy(1, 5));

					{
						panel16.setBorder(new TitledBorder(new LineBorder(new Color(153, 153, 153)), "Macro Panels", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
						panel16.setLayout(new FormLayout(
							"[10px,default], default:grow, [10px,default], 40dlu, 3*([10px,default])", //$NON-NLS-1$
							"$nlgap, default, $nlgap")); //$NON-NLS-1$

						label55.setText("Default: Allow players to edit macros"); //$NON-NLS-1$
						label55.setToolTipText("<html>Player-editable macros cannot call <b>trusted</b> functions.  When developing a framework, this should be disabled."); //$NON-NLS-1$
						panel16.add(label55, CC.xy(2, 2));

						allowPlayerMacroEditsDefaultCheckBox.setActionCommand("Default: Allow Players to Edit Macros"); //$NON-NLS-1$
						panel16.add(allowPlayerMacroEditsDefaultCheckBox, CC.xy(4, 2));
					}
					panel12.add(panel16, CC.xy(1, 7));
				}
				panel9.add(panel12, CC.xywh(4, 2, 1, 4));
			}
			tabbedPane1.addTab("Application", panel9); //$NON-NLS-1$

			{
				panel17.setLayout(new FormLayout(
					"2*([10px,default], default), [10px,default]", //$NON-NLS-1$
					"2*([10px,default], default), [10px,default]")); //$NON-NLS-1$

				playSystemSoundsCheckBox.setActionCommand("Play system sounds"); //$NON-NLS-1$
				panel17.add(playSystemSoundsCheckBox, CC.xy(4, 2));

				soundsOnlyWhenNotFocusedCheckBox.setActionCommand("Only when window not focused"); //$NON-NLS-1$
				panel17.add(soundsOnlyWhenNotFocusedCheckBox, CC.xy(4, 4));

				label56.setText("Play system sounds\t"); //$NON-NLS-1$
				label56.setToolTipText("Turn this off to prevent all sounds from within TabletopTool.  Needed for some Java implementations as too many sounds queued up can crash the JVM."); //$NON-NLS-1$
				panel17.add(label56, CC.xy(2, 2));

				label57.setText("Only when window not focused"); //$NON-NLS-1$
				label57.setToolTipText("Only turn off sounds when the window is not focused."); //$NON-NLS-1$
				panel17.add(label57, CC.xy(2, 4));
			}
			tabbedPane1.addTab("Sounds", panel17); //$NON-NLS-1$
		}
		contentPane.add(tabbedPane1, BorderLayout.CENTER);

		{
			panel18.setBorder(new EmptyBorder(5, 5, 5, 5));
			panel18.setLayout(new BorderLayout(5, 0));

			okButton.setText("OK"); //$NON-NLS-1$
			okButton.setActionCommand("OK"); //$NON-NLS-1$
			panel18.add(okButton, BorderLayout.EAST);
		}
		contentPane.add(panel18, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
