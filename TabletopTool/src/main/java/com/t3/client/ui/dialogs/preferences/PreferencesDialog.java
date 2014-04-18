/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3.client.ui.dialogs.preferences;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.ToolTipManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.t3.client.AppPreferences;
import com.t3.client.TabletopTool;
import com.t3.client.ui.forms.PreferencesBaseDialog;
import com.t3.client.walker.WalkerMetric;
import com.t3.model.Token;
import com.t3.model.Zone;
import com.t3.model.grid.Grid;
import com.t3.model.grid.GridFactory;
import com.t3.swing.SwingUtil;
import com.t3.util.StringUtil;

public class PreferencesDialog extends PreferencesBaseDialog {

	/**
	 * @author frank
	 * 
	 */
	private abstract class DocumentListenerProxy implements DocumentListener {
		JTextField comp;

		public DocumentListenerProxy(JTextField tf) {
			comp = tf;
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			updateValue();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			updateValue();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			updateValue();
		}

		protected void updateValue() {
			try {
				int value = StringUtil.parseInteger(comp.getText()); // Localized
				storeNumericValue(value);
			} catch (ParseException nfe) {
				// Ignore it
			}
		}

		protected abstract void storeNumericValue(int value);
	}

	/**
	 * @author frank
	 */
	private abstract class ChangeListenerProxy implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent ce) {
			JSpinner sp = (JSpinner) ce.getSource();
			int value = (Integer) sp.getValue();
			storeSpinnerValue(value);
		}

		protected abstract void storeSpinnerValue(int value);
	}

	public PreferencesDialog() {
		super(TabletopTool.getFrame());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		getRootPane().setDefaultButton(okButton);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				setVisible(false);
				dispose();
				TabletopTool.getEventDispatcher().fireEvent(TabletopTool.PreferencesEvent.Changed);
			}
		});
		setInitialState();

		// And keep it updated
		facingFaceEdgesCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setFaceEdge(facingFaceEdgesCheckBox.isSelected());
				updateFacings();
			}
		});
		facingFaceVerticesCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setFaceVertex(facingFaceVerticesCheckBox.isSelected());
				updateFacings();
			}
		});

		suppressToolTipsMacroLinksCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setSuppressToolTipsForMacroLinks(suppressToolTipsMacroLinksCheckBox.isSelected());
			}
		});

		toolTipInitialDelay.getDocument().addDocumentListener(new DocumentListenerProxy(toolTipInitialDelay) {
			@Override
			protected void storeNumericValue(int value) {
				AppPreferences.setToolTipInitialDelay(value);
				ToolTipManager.sharedInstance().setInitialDelay(value);
			}
		});
		toolTipDismissDelay.getDocument().addDocumentListener(new DocumentListenerProxy(toolTipDismissDelay) {
			@Override
			protected void storeNumericValue(int value) {
				AppPreferences.setToolTipDismissDelay(value);
				ToolTipManager.sharedInstance().setDismissDelay(value);
			}
		});

		chatNotificationColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setChatNotificationColor(chatNotificationColor.getSelectedColor());
				TabletopTool.getFrame().setChatTypingLabelColor(AppPreferences.getChatNotificationColor());
			}
		});

		trustedOuputForeground.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setTrustedPrefixFG(trustedOuputForeground.getSelectedColor());
				TabletopTool.getFrame().getCommandPanel().setTrustedMacroPrefixColors(AppPreferences.getTrustedPrefixFG(), AppPreferences.getTrustedPrefixBG());
			}
		});
		trustedOuputBackground.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setTrustedPrefixBG(trustedOuputBackground.getSelectedColor());
				TabletopTool.getFrame().getCommandPanel().setTrustedMacroPrefixColors(AppPreferences.getTrustedPrefixFG(), AppPreferences.getTrustedPrefixBG());
			}
		});

		chatAutosaveTime.addChangeListener(new ChangeListenerProxy() {
			@Override
			protected void storeSpinnerValue(int value) {
				AppPreferences.setChatAutosaveTime(value);
			}
		});
		typingNotificationDuration.addChangeListener(new ChangeListenerProxy() {
			@Override
			protected void storeSpinnerValue(int value) {
				AppPreferences.setTypingNotificationDuration(value);
			}
		});

		chatFilenameFormat.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!e.isTemporary()) {
					StringBuffer saveFile = new StringBuffer(chatFilenameFormat.getText());
					if (saveFile.indexOf(".") < 0) {
						saveFile.append(".html");
					}
					AppPreferences.setChatFilenameFormat(saveFile.toString());
				}
			}
		});

		allowPlayerMacroEditsDefaultCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setAllowPlayerMacroEditsDefault(allowPlayerMacroEditsDefaultCheckBox.isSelected());
			}
		});

		showChatAvatarCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setShowAvatarInChat(showChatAvatarCheckBox.isSelected());
			}
		});
		saveReminderCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setSaveReminder(saveReminderCheckBox.isSelected());
			}
		});
		fillSelectionCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setFillSelectionBox(fillSelectionCheckBox.isSelected());
			}
		});
		showDialogOnNewTokenCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setShowDialogOnNewToken(showDialogOnNewTokenCheckBox.isSelected());
			}
		});
		autoSaveSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				int newInterval = (Integer) autoSaveSpinner.getValue();
				AppPreferences.setAutoSaveIncrement(newInterval);
				TabletopTool.getAutoSaveManager().restart();
			}
		});
		newMapsHaveFOWCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setNewMapsHaveFOW(newMapsHaveFOWCheckBox.isSelected());
			}
		});
		tokensPopupWarningWhenDeletedCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setTokensWarnWhenDeleted(tokensPopupWarningWhenDeletedCheckBox.isSelected());
			}
		});
		tokensStartSnapToGridCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setTokensStartSnapToGrid(tokensStartSnapToGridCheckBox.isSelected());
			}
		});
		newMapsVisibleCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setNewMapsVisible(newMapsVisibleCheckBox.isSelected());
			}
		});
		newTokensVisibleCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setNewTokensVisible(newTokensVisibleCheckBox.isSelected());
			}
		});
		stampsStartFreeSizeCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setObjectsStartFreesize(stampsStartFreeSizeCheckBox.isSelected());
			}
		});
		tokensStartFreeSizeCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setTokensStartFreesize(tokensStartFreeSizeCheckBox.isSelected());
			}
		});
		stampsStartSnapToGridCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setObjectsStartSnapToGrid(stampsStartSnapToGridCheckBox.isSelected());
			}
		});
		showStatSheetCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setShowStatSheet(showStatSheetCheckBox.isSelected());
			}
		});
		backgroundsStartFreeSizeCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setBackgroundsStartFreesize(backgroundsStartFreeSizeCheckBox.isSelected());
			}
		});
		backgroundsStartSnapToGridCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setBackgroundsStartSnapToGrid(backgroundsStartSnapToGridCheckBox.isSelected());
			}
		});
		defaultGridSizeTextField.getDocument().addDocumentListener(new DocumentListenerProxy(defaultGridSizeTextField) {
			@Override
			protected void storeNumericValue(int value) {
				AppPreferences.setDefaultGridSize(value);
			}
		});

		defaultUnitsPerCellTextField.getDocument().addDocumentListener(new DocumentListenerProxy(defaultUnitsPerCellTextField) {
			@Override
			protected void storeNumericValue(int value) {
				AppPreferences.setDefaultUnitsPerCell(value);
			}
		});
		defaultVisionDistanceTextField.getDocument().addDocumentListener(new DocumentListenerProxy(defaultVisionDistanceTextField) {
			@Override
			protected void storeNumericValue(int value) {
				AppPreferences.setDefaultVisionDistance(value);
			}
		});
		statsheetPortraitSize.getDocument().addDocumentListener(new DocumentListenerProxy(statsheetPortraitSize) {
			@Override
			protected void storeNumericValue(int value) {
				AppPreferences.setPortraitSize(value);
			}
		});
		haloLineWidthSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				AppPreferences.setHaloLineWidth((Integer) haloLineWidthSpinner.getValue());
			}
		});

		// Overlay opacity options in AppPreferences, with
		// error checking to ensure values are within the acceptable range
		// of 0 and 255.
		haloOverlayOpacitySpinner.addChangeListener(new ChangeListenerProxy() {
			@Override
			protected void storeSpinnerValue(int value) {
				AppPreferences.setHaloOverlayOpacity(value);
				TabletopTool.getFrame().refresh();
			}
		});
		auraOverlayOpacitySpinner.addChangeListener(new ChangeListenerProxy() {
			@Override
			protected void storeSpinnerValue(int value) {
				AppPreferences.setAuraOverlayOpacity(value);
				TabletopTool.getFrame().refresh();
			}
		});
		lightOverlayOpacitySpinner.addChangeListener(new ChangeListenerProxy() {
			@Override
			protected void storeSpinnerValue(int value) {
				AppPreferences.setLightOverlayOpacity(value);
				TabletopTool.getFrame().refresh();
			}
		});
		fogOverlayOpacitySpinner.addChangeListener(new ChangeListenerProxy() {
			@Override
			protected void storeSpinnerValue(int value) {
				AppPreferences.setFogOverlayOpacity(value);
				TabletopTool.getFrame().refresh();
			}
		});
		useHaloColorAsVisionOverlayCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setUseHaloColorOnVisionOverlay(useHaloColorAsVisionOverlayCheckBox.isSelected());
			}
		});
		autoRevealVisionOnGMMoveCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setAutoRevealVisionOnGMMovement(autoRevealVisionOnGMMoveCheckBox.isSelected());
			}
		});
		showSmiliesCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setShowSmilies(showSmiliesCheckBox.isSelected());
			}
		});
		playSystemSoundsCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setPlaySystemSounds(playSystemSoundsCheckBox.isSelected());
			}
		});

		soundsOnlyWhenNotFocusedCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setPlaySystemSoundsOnlyWhenNotFocused(soundsOnlyWhenNotFocusedCheckBox.isSelected());
			}
		});

		fontSizeTextField.getDocument().addDocumentListener(new DocumentListenerProxy(fontSizeTextField) {
			@Override
			protected void storeNumericValue(int value) {
				AppPreferences.setFontSize(value);
			}
		});

		fitGMViewCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setFitGMView(fitGMViewCheckBox.isSelected());
			}
		});
		hideNPCsCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setInitHideNpcs(hideNPCsCheckBox.isSelected());
			}
		});
		ownerPermissionCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setInitOwnerPermissions(ownerPermissionCheckBox.isSelected());
			}
		});
		lockMovementCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setInitLockMovement(lockMovementCheckBox.isSelected());
			}
		});
		showInitGainMessageCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setShowInitGainMessage(showInitGainMessageCheckBox.isSelected());
			}
		});
		chatNotificationShowBackgroundCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppPreferences.setChatNotificationShowBackground(chatNotificationShowBackgroundCheckBox.isSelected());
			}
		});

		DefaultComboBoxModel<String> gridTypeModel = new DefaultComboBoxModel<String>();
		gridTypeModel.addElement(GridFactory.SQUARE);
		gridTypeModel.addElement(GridFactory.HEX_HORI);
		gridTypeModel.addElement(GridFactory.HEX_VERT);
		gridTypeModel.setSelectedItem(AppPreferences.getDefaultGridType());
		defaultGridTypeCombo.setModel(gridTypeModel);
		defaultGridTypeCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				AppPreferences.setDefaultGridType((String) defaultGridTypeCombo.getSelectedItem());
			}
		});

		DefaultComboBoxModel<String> tokenNumModel = new DefaultComboBoxModel<String>();
		tokenNumModel.addElement(Token.NUM_INCREMENT);
		tokenNumModel.addElement(Token.NUM_RANDOM);
		tokenNumModel.setSelectedItem(AppPreferences.getDuplicateTokenNumber());
		duplicateTokenCombo.setModel(tokenNumModel);
		duplicateTokenCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				AppPreferences.setDuplicateTokenNumber((String) duplicateTokenCombo.getSelectedItem());
			}
		});

		DefaultComboBoxModel<String> tokenNameModel = new DefaultComboBoxModel<String>();
		tokenNameModel.addElement(Token.NAME_USE_FILENAME);
		tokenNameModel.addElement(Token.NAME_USE_CREATURE);
		tokenNameModel.setSelectedItem(AppPreferences.getNewTokenNaming());
		tokenNamingCombo.setModel(tokenNameModel);
		tokenNamingCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				AppPreferences.setNewTokenNaming((String) tokenNamingCombo.getSelectedItem());
			}
		});

		DefaultComboBoxModel<String> showNumModel = new DefaultComboBoxModel<String>();
		showNumModel.addElement(Token.NUM_ON_NAME);
		showNumModel.addElement(Token.NUM_ON_GM);
		showNumModel.addElement(Token.NUM_ON_BOTH);
		showNumModel.setSelectedItem(AppPreferences.getTokenNumberDisplay());
		showNumberingCombo.setModel(showNumModel);
		showNumberingCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				AppPreferences.setTokenNumberDisplay((String) showNumberingCombo.getSelectedItem());
			}
		});

		DefaultComboBoxModel<WalkerMetric> movementMetricModel = new DefaultComboBoxModel<WalkerMetric>();
		movementMetricModel.addElement(WalkerMetric.ONE_TWO_ONE);
		movementMetricModel.addElement(WalkerMetric.ONE_ONE_ONE);
		movementMetricModel.addElement(WalkerMetric.MANHATTAN);
		movementMetricModel.addElement(WalkerMetric.NO_DIAGONALS);
		movementMetricModel.setSelectedItem(AppPreferences.getMovementMetric());

		movementMetricCombo.setModel(movementMetricModel);
		movementMetricCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				AppPreferences.setMovementMetric((WalkerMetric) movementMetricCombo.getSelectedItem());
			}
		});
		//showInitGainMessage

		pack();
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			SwingUtil.centerOver(this, TabletopTool.getFrame());
		}
		super.setVisible(b);
	}

	/**
	 * Used by the ActionListeners of the facing checkboxes to update the facings for all of the current zones.
	 * Redundant to go through all zones because all zones using the same grid type share facings but it doesn't hurt
	 * anything and avoids having to track what grid types are being used.
	 */
	private void updateFacings() {
//		List<Zone> zlist = TabletopTool.getServer().getCampaign().getZones();	// generated NPE http://forums.rptools.net/viewtopic.php?f=3&t=17334
		List<Zone> zlist = TabletopTool.getCampaign().getZones();
		boolean faceEdges = AppPreferences.getFaceEdge();
		boolean faceVertices = AppPreferences.getFaceVertex();
		for (Zone z : zlist) {
			Grid g = z.getGrid();
			g.setFacings(faceEdges, faceVertices);
		}
	}

	private void setInitialState() {
		showDialogOnNewTokenCheckBox.setSelected(AppPreferences.getShowDialogOnNewToken());
		saveReminderCheckBox.setSelected(AppPreferences.getSaveReminder());
		fillSelectionCheckBox.setSelected(AppPreferences.getFillSelectionBox());
		autoSaveSpinner.setValue(AppPreferences.getAutoSaveIncrement());
		newMapsHaveFOWCheckBox.setSelected(AppPreferences.getNewMapsHaveFOW());
		tokensPopupWarningWhenDeletedCheckBox.setSelected(AppPreferences.getTokensWarnWhenDeleted());
		tokensStartSnapToGridCheckBox.setSelected(AppPreferences.getTokensStartSnapToGrid());
		newMapsVisibleCheckBox.setSelected(AppPreferences.getNewMapsVisible());
		newTokensVisibleCheckBox.setSelected(AppPreferences.getNewTokensVisible());
		stampsStartFreeSizeCheckBox.setSelected(AppPreferences.getObjectsStartFreesize());
		tokensStartFreeSizeCheckBox.setSelected(AppPreferences.getTokensStartFreesize());
		stampsStartSnapToGridCheckBox.setSelected(AppPreferences.getObjectsStartSnapToGrid());
		backgroundsStartFreeSizeCheckBox.setSelected(AppPreferences.getBackgroundsStartFreesize());
		showStatSheetCheckBox.setSelected(AppPreferences.getShowStatSheet());
		backgroundsStartSnapToGridCheckBox.setSelected(AppPreferences.getBackgroundsStartSnapToGrid());
		defaultGridSizeTextField.setText(Integer.toString(AppPreferences.getDefaultGridSize()));
		defaultUnitsPerCellTextField.setText(Integer.toString(AppPreferences.getDefaultUnitsPerCell()));
		defaultVisionDistanceTextField.setText(Integer.toString(AppPreferences.getDefaultVisionDistance()));
		statsheetPortraitSize.setText(Integer.toString(AppPreferences.getPortraitSize()));
		fontSizeTextField.setText(Integer.toString(AppPreferences.getFontSize()));
		haloLineWidthSpinner.setValue(AppPreferences.getHaloLineWidth());

		haloOverlayOpacitySpinner.setModel(new SpinnerNumberModel(AppPreferences.getHaloOverlayOpacity(), 0, 255, 1));
		auraOverlayOpacitySpinner.setModel(new SpinnerNumberModel(AppPreferences.getAuraOverlayOpacity(), 0, 255, 1));
		lightOverlayOpacitySpinner.setModel(new SpinnerNumberModel(AppPreferences.getLightOverlayOpacity(), 0, 255, 1));
		fogOverlayOpacitySpinner.setModel(new SpinnerNumberModel(AppPreferences.getFogOverlayOpacity(), 0, 255, 1));

		useHaloColorAsVisionOverlayCheckBox.setSelected(AppPreferences.getUseHaloColorOnVisionOverlay());
		autoRevealVisionOnGMMoveCheckBox.setSelected(AppPreferences.getAutoRevealVisionOnGMMovement());
		showSmiliesCheckBox.setSelected(AppPreferences.getShowSmilies());
		playSystemSoundsCheckBox.setSelected(AppPreferences.getPlaySystemSounds());
		soundsOnlyWhenNotFocusedCheckBox.setSelected(AppPreferences.getPlaySystemSoundsOnlyWhenNotFocused());
		showChatAvatarCheckBox.setSelected(AppPreferences.getShowAvatarInChat());
		allowPlayerMacroEditsDefaultCheckBox.setSelected(AppPreferences.getAllowPlayerMacroEditsDefault());
		suppressToolTipsMacroLinksCheckBox.setSelected(AppPreferences.getSuppressToolTipsForMacroLinks());
		trustedOuputForeground.setSelectedColor(AppPreferences.getTrustedPrefixFG());
		trustedOuputBackground.setSelectedColor(AppPreferences.getTrustedPrefixBG());
		toolTipInitialDelay.setText(Integer.toString(AppPreferences.getToolTipInitialDelay()));
		toolTipDismissDelay.setText(Integer.toString(AppPreferences.getToolTipDismissDelay()));
		facingFaceEdgesCheckBox.setSelected(AppPreferences.getFaceEdge());
		facingFaceVerticesCheckBox.setSelected(AppPreferences.getFaceVertex());

		chatAutosaveTime.setModel(new SpinnerNumberModel(AppPreferences.getChatAutosaveTime(), 0, 24 * 60, 1));
		chatFilenameFormat.setText(AppPreferences.getChatFilenameFormat());

		fitGMViewCheckBox.setSelected(AppPreferences.getFitGMView());
		hideNPCsCheckBox.setSelected(AppPreferences.getInitHideNpcs());
		ownerPermissionCheckBox.setSelected(AppPreferences.getInitOwnerPermissions());
		lockMovementCheckBox.setSelected(AppPreferences.getInitLockMovement());
		showInitGainMessageCheckBox.setSelected(AppPreferences.isShowInitGainMessage());
		Integer rawVal = AppPreferences.getTypingNotificationDuration();
		Integer typingVal = null;
		if (rawVal != null && rawVal > 99) { // backward compatibility -- used to be stored in ms, now in seconds
			Double dbl = (double) (rawVal / 1000);
			if (dbl >= 1) {
				long fixedUp = Math.round(dbl);
				typingVal = (int) fixedUp;
				typingVal = typingVal > 99 ? 99 : typingVal;
			} else {
				typingVal = 1;
			}
		}
		int value = Math.abs((typingVal == null || typingVal > rawVal) ? rawVal : typingVal);
		AppPreferences.setTypingNotificationDuration(value);

		SpinnerNumberModel typingDurationModel = new SpinnerNumberModel((int) AppPreferences.getTypingNotificationDuration(), 0, 99, 1);
		typingNotificationDuration.setModel(typingDurationModel);

		chatNotificationColor.setSelectedColor(AppPreferences.getChatNotificationColor());
		chatNotificationShowBackground.setSelected(AppPreferences.getChatNotificationShowBackground());
	}
}
