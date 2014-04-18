package com.t3.client.ui.forms;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Virenerus
 */
public abstract class StartServerBaseDialog extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JTextField _username;
	protected JTextField _GMPassword;
	protected JTextField _playerPassword;
	protected JLabel username;
	protected JLabel label1;
	protected JLabel label2;
	protected JLabel label3;
	protected JComboBox _role;
	protected JLabel label4;
	protected JLabel label5;
	protected JLabel label6;
	protected JTextField _TabletoptoolName;
	protected JLabel label7;
	protected JPanel panel1;
	protected JCheckBox _useStrictTokenOwnership;
	protected JCheckBox _playersCanRevealVision;
	protected JCheckBox _useIndividualViews;
	protected JCheckBox _restrictedImpersonation;
	protected JCheckBox _playersReceiveCampaignMacros;
	protected JCheckBox _useToolTipsForUnformattedRolls;
	protected JLabel label8;
	protected JCheckBox _useIndividualFOW;
	protected JComboBox movementMetric;
	protected JCheckBox _autoRevealOnMovement;
	protected JPanel panel2;
	protected JButton networkingHelpButton;
	protected JButton okButton;
	protected JButton cancelButton;
	protected JPanel panel3;
	protected JTextField _port;
	protected JCheckBox _useUPnP;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public StartServerBaseDialog() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		_username = new JTextField();
		_GMPassword = new JTextField();
		_playerPassword = new JTextField();
		username = new JLabel();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		_role = new JComboBox();
		label4 = new JLabel();
		label5 = new JLabel();
		label6 = new JLabel();
		_TabletoptoolName = new JTextField();
		label7 = new JLabel();
		panel1 = new JPanel();
		_useStrictTokenOwnership = new JCheckBox();
		_playersCanRevealVision = new JCheckBox();
		_useIndividualViews = new JCheckBox();
		_restrictedImpersonation = new JCheckBox();
		_playersReceiveCampaignMacros = new JCheckBox();
		_useToolTipsForUnformattedRolls = new JCheckBox();
		label8 = new JLabel();
		_useIndividualFOW = new JCheckBox();
		movementMetric = new JComboBox();
		_autoRevealOnMovement = new JCheckBox();
		panel2 = new JPanel();
		networkingHelpButton = new JButton();
		okButton = new JButton();
		cancelButton = new JButton();
		panel3 = new JPanel();
		_port = new JTextField();
		_useUPnP = new JCheckBox();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"[10px,default], 2*(default), left:[default,125dlu], 2*(default), [10px,default]", //$NON-NLS-1$
			"7*([10px,default], default), [10px,default]")); //$NON-NLS-1$

		_username.setColumns(20);
		add(_username, CC.xy(4, 2));

		_GMPassword.setColumns(20);
		add(_GMPassword, CC.xy(4, 8));

		_playerPassword.setColumns(20);
		add(_playerPassword, CC.xy(4, 10));

		username.setText("Username:"); //$NON-NLS-1$
		add(username, CC.xy(2, 2));

		label1.setText("Port:"); //$NON-NLS-1$
		add(label1, CC.xy(2, 4));

		label2.setText("GM Password:"); //$NON-NLS-1$
		add(label2, CC.xy(2, 8));

		label3.setText("Player Password:"); //$NON-NLS-1$
		add(label3, CC.xy(2, 10));
		add(_role, CC.xy(6, 2));

		label4.setText("<html><body><i>Optional</i></body></html>"); //$NON-NLS-1$
		add(label4, CC.xy(6, 8));

		label5.setText("<html><body><i>Optional</i></body></html>"); //$NON-NLS-1$
		add(label5, CC.xy(6, 10));

		label6.setText("RPTools.net Alias:"); //$NON-NLS-1$
		add(label6, CC.xy(2, 6));

		_TabletoptoolName.setColumns(20);
		add(_TabletoptoolName, CC.xy(4, 6));

		label7.setText("<html><body><i>Optional</i></body></html>"); //$NON-NLS-1$
		add(label7, CC.xy(6, 6));

		{
			panel1.setBorder(new TitledBorder(new LineBorder(new Color(153, 153, 153)), "Options", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
			panel1.setLayout(new FormLayout(
				"[10px,default], 33px, 154px, default:grow, 102px, [10px,default]", //$NON-NLS-1$
				"2*([10px,default], default), 3*(default), 4*([10px,default], default)")); //$NON-NLS-1$

			_useStrictTokenOwnership.setText("Strict token ownership"); //$NON-NLS-1$
			_useStrictTokenOwnership.setActionCommand("Strict token ownership"); //$NON-NLS-1$
			panel1.add(_useStrictTokenOwnership, CC.xywh(2, 2, 2, 1));

			_playersCanRevealVision.setText("Players can reveal vision"); //$NON-NLS-1$
			_playersCanRevealVision.setActionCommand("Players can reveal vision"); //$NON-NLS-1$
			panel1.add(_playersCanRevealVision, CC.xywh(2, 4, 2, 1));

			_useIndividualViews.setText("Use Individual Views"); //$NON-NLS-1$
			_useIndividualViews.setActionCommand("Use Individual Views"); //$NON-NLS-1$
			panel1.add(_useIndividualViews, CC.xywh(2, 6, 2, 1));

			_restrictedImpersonation.setText("Restricted Player Impersonation"); //$NON-NLS-1$
			_restrictedImpersonation.setActionCommand("Use Unrestricted Player Impersonation"); //$NON-NLS-1$
			_restrictedImpersonation.setToolTipText("Gives players the ability to impersonate any character as if they had GM status."); //$NON-NLS-1$
			panel1.add(_restrictedImpersonation, CC.xywh(2, 9, 2, 1));

			_playersReceiveCampaignMacros.setText("Players Receive Campaign Macros"); //$NON-NLS-1$
			_playersReceiveCampaignMacros.setActionCommand("Use Unrestricted Player Impersonation"); //$NON-NLS-1$
			_playersReceiveCampaignMacros.setToolTipText("The campaign panel macros will be available for players to use (but not edit)."); //$NON-NLS-1$
			panel1.add(_playersReceiveCampaignMacros, CC.xywh(2, 11, 2, 1));

			_useToolTipsForUnformattedRolls.setText("Use ToolTips for [] Rolls"); //$NON-NLS-1$
			_useToolTipsForUnformattedRolls.setActionCommand("Use Unrestricted Player Impersonation"); //$NON-NLS-1$
			_useToolTipsForUnformattedRolls.setToolTipText("Tool Tips will be used for [ ] rolls where no format options are specified"); //$NON-NLS-1$
			panel1.add(_useToolTipsForUnformattedRolls, CC.xywh(2, 13, 2, 1));

			label8.setText("Movement metric"); //$NON-NLS-1$
			label8.setToolTipText("Determines how TabletopTool handles movement on the selected grid type."); //$NON-NLS-1$
			panel1.add(label8, CC.xywh(2, 15, 2, 1));

			_useIndividualFOW.setText("Use Individual FOW\t"); //$NON-NLS-1$
			_useIndividualFOW.setActionCommand("Use Individual Views"); //$NON-NLS-1$
			_useIndividualFOW.setToolTipText("Each token has it's own FOW"); //$NON-NLS-1$
			panel1.add(_useIndividualFOW, CC.xy(3, 7));

			movementMetric.setToolTipText("Determines how TabletopTool handles movement on the selected grid type.\t"); //$NON-NLS-1$
			panel1.add(movementMetric, CC.xywh(4, 15, 2, 1));

			_autoRevealOnMovement.setText("Auto Reveal On Movement"); //$NON-NLS-1$
			_autoRevealOnMovement.setActionCommand("Auto Reveal On Movement"); //$NON-NLS-1$
			_autoRevealOnMovement.setToolTipText("Allows tokens to automatically reveal FoW at the end of movement."); //$NON-NLS-1$
			panel1.add(_autoRevealOnMovement, CC.xy(3, 5));
		}
		add(panel1, CC.xywh(2, 12, 5, 1));

		{
			panel2.setLayout(new FormLayout(
				"default, 3*([10px,default]), default, [10px,default], default", //$NON-NLS-1$
				"default")); //$NON-NLS-1$

			networkingHelpButton.setText("Networking Help"); //$NON-NLS-1$
			networkingHelpButton.setActionCommand("Test Connection"); //$NON-NLS-1$
			panel2.add(networkingHelpButton, CC.xy(1, 1));

			okButton.setText("OK"); //$NON-NLS-1$
			okButton.setActionCommand("OK"); //$NON-NLS-1$
			panel2.add(okButton, CC.xy(5, 1));

			cancelButton.setText("Cancel"); //$NON-NLS-1$
			cancelButton.setActionCommand("Cancel"); //$NON-NLS-1$
			panel2.add(cancelButton, CC.xy(7, 1));
		}
		add(panel2, CC.xywh(2, 14, 5, 1, CC.FILL, CC.DEFAULT));

		{
			panel3.setLayout(new FormLayout(
				"default, 39px, default:grow", //$NON-NLS-1$
				"default")); //$NON-NLS-1$

			_port.setColumns(5);
			panel3.add(_port, CC.xy(1, 1));

			_useUPnP.setText("Use UPnP"); //$NON-NLS-1$
			_useUPnP.setActionCommand("Use UPnP"); //$NON-NLS-1$
			panel3.add(_useUPnP, CC.xy(3, 1));
		}
		add(panel3, CC.xy(4, 4));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
