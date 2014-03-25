/*
 *  This software copyright by various authors including the RPTools.net
 *  development team, and licensed under the LGPL Version 3 or, at your
 *  option, any later version.
 *
 *  Portions of this software were originally covered under the Apache
 *  Software License, Version 1.1 or Version 2.0.
 *
 *  See the file LICENSE elsewhere in this distribution for license details.
 */

package com.t3.client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import yasb.Binder;

import com.t3.client.AppPreferences;
import com.t3.client.TabletopTool;
import com.t3.client.swing.AbeillePanel;
import com.t3.client.swing.GenericDialog;
import com.t3.client.walker.WalkerMetric;
import com.t3.language.I18N;
import com.t3.util.StringUtil;

/**
 * @author trevor
 */
public class StartServerDialog extends AbeillePanel<StartServerDialogPreferences> {
	private boolean accepted;

	private StartServerDialogPreferences prefs;
	private GenericDialog dialog;
	private JComboBox<WalkerMetric> movementMetricCombo;
	private JCheckBox useIndividualFOW;
	private JCheckBox useIndividualViews;
	private JCheckBox autoRevealOnMovement;
	private JCheckBox playersCanRevealVision;

	public StartServerDialog() {
		super("com/t3/client/ui/forms/startServerDialog.xml");
		panelInit();
	}

	public boolean accepted() {
		return accepted;
	}

	public void showDialog() {
		dialog = new GenericDialog(I18N.getText("ServerDialog.msg.title"), TabletopTool.getFrame(), this);
		prefs = new StartServerDialogPreferences();

		bind(prefs);
		useIndividualFOW = (JCheckBox) getComponent("@useIndividualFOW");
		useIndividualViews = (JCheckBox) getComponent("@useIndividualViews");
		autoRevealOnMovement = (JCheckBox) getComponent("@autoRevealOnMovement");
		playersCanRevealVision = (JCheckBox) getComponent("@playersCanRevealVision");

		useIndividualFOW.setEnabled(prefs.getUseIndividualViews());
		useIndividualViews.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (!useIndividualViews.isSelected()) {
					useIndividualFOW.setSelected(false);
					useIndividualFOW.setEnabled(false);
				} else {
					useIndividualFOW.setEnabled(true);
				}
			}
		});

		autoRevealOnMovement.setEnabled(prefs.getPlayersCanRevealVision());
		playersCanRevealVision.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (!playersCanRevealVision.isSelected()) {
					autoRevealOnMovement.setSelected(false);
					autoRevealOnMovement.setEnabled(false);
				} else {
					autoRevealOnMovement.setEnabled(true);
				}
			}
		});

		movementMetricCombo = getMovementMetric();
		DefaultComboBoxModel<WalkerMetric> movementMetricModel = new DefaultComboBoxModel<WalkerMetric>();
		movementMetricModel.addElement(WalkerMetric.ONE_TWO_ONE);
		movementMetricModel.addElement(WalkerMetric.ONE_ONE_ONE);
		movementMetricModel.addElement(WalkerMetric.MANHATTAN);
		movementMetricModel.addElement(WalkerMetric.NO_DIAGONALS);
		movementMetricModel.setSelectedItem(AppPreferences.getMovementMetric());

		movementMetricCombo.setModel(movementMetricModel);
		movementMetricCombo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				prefs.setMovementMetric((WalkerMetric) movementMetricCombo.getSelectedItem());
			}
		});
		getRootPane().setDefaultButton(getOKButton());
		dialog.showDialog();
	}

	public JTextField getPortTextField() {
		return (JTextField) getComponent("@port");
	}

	public JTextField getUsernameTextField() {
		return (JTextField) getComponent("@username");
	}

	public JButton getOKButton() {
		return (JButton) getComponent("okButton");
	}

	public JButton getCancelButton() {
		return (JButton) getComponent("cancelButton");
	}

	public JComboBox getRoleCombo() {
		return (JComboBox) getComponent("@role");
	}

	public JButton getNetworkingHelpButton() {
		return (JButton) getComponent("networkingHelpButton");
	}

	public JCheckBox getUseUPnPCheckbox() {
		return (JCheckBox) getComponent("@useUPnP");
	}

	public JCheckBox getUseTooltipForRolls() {
		return (JCheckBox) getComponent("@useToolTipsForUnformattedRolls");
	}

	public JComboBox<WalkerMetric> getMovementMetric() {
		return (JComboBox<WalkerMetric>) getComponent("movementMetric");
	}

	@Override
	protected void preModelBind() {
		Binder.setFormat(getPortTextField(), new DecimalFormat("####"));
	}

	public void initOKButton() {
		getOKButton().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (getPortTextField().getText().length() == 0) {
					TabletopTool.showError("ServerDialog.error.port");
					return;
				}
				try {
					Integer.parseInt(getPortTextField().getText());
				} catch (NumberFormatException nfe) {
					TabletopTool.showError("ServerDialog.error.port");
					return;
				}
				if (StringUtil.isEmpty(getUsernameTextField().getText())) {
					TabletopTool.showError("ServerDialog.error.username");
					return;
				}
				if (commit()) {
					prefs.setMovementMetric((WalkerMetric) movementMetricCombo.getSelectedItem());
					prefs.setAutoRevealOnMovement(autoRevealOnMovement.isSelected());
					accepted = true;
					dialog.closeDialog();
				}
			}
		});
	}

	public void initCancelButton() {
		getCancelButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accepted = false;
				dialog.closeDialog();
			}
		});
	}

	public void initTestConnectionButton() {
		getNetworkingHelpButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// We don't have a good, server-side way of testing any more.
				boolean ok;
				ok = TabletopTool.confirm("msg.info.server.networkingHelp");
				if (ok)
					TabletopTool.showDocument(I18N.getString("msg.info.server.forumNFAQ_URL"));
			}
		});
	}
}
