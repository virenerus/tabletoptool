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

package com.t3.client.ui.macrobuttons.buttons;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import com.t3.client.AppUtil;
import com.t3.client.TabletopTool;
import com.t3.client.ui.MacroButtonDialog;
import com.t3.language.I18N;
import com.t3.model.MacroButtonProperties;
import com.t3.model.Token;
import com.t3.util.PersistenceUtil;

@SuppressWarnings("serial")
public class MacroButtonPopupMenu extends JPopupMenu{

	private final MacroButton button;
	private final String panelClass;

	public MacroButtonPopupMenu(MacroButton parent, String panelClass, Boolean commonMacro) {
		this.button = parent;
		this.panelClass = panelClass;
		if(panelClass.equals("SelectionPanel")) {
			if(button.getProperties().getCommonMacro()) {
				addCommonActions();
			} else {
				addActions();
			}
		} else if(panelClass.equals("CampaignPanel")) {
			addCampaignActions();
		} else {
			addActions();
		}
	}

	private void addActions() {
		if(TabletopTool.getPlayer().isGM() || button.getProperties().getAllowPlayerEdits()) {
			add(new EditButtonAction());
			add(new JSeparator());
			add(new AddNewButtonAction());
			add(new DuplicateButtonAction());
			add(new JSeparator());
			add(new ResetButtonAction());
			add(new DeleteButtonAction());
			add(new JSeparator());
			add(new ExportMacroAction());
			add(new JSeparator());
			add(new RunMacroForEachSelectedTokenAction());
		} else {
			add(new AddNewButtonAction());
			add(new JSeparator());
			add(new RunMacroForEachSelectedTokenAction());
		}

	}

	private void addCommonActions() {
		if(TabletopTool.getPlayer().isGM() || button.getProperties().getAllowPlayerEdits()) {
			add(new EditButtonAction());
			add(new AddNewButtonAction(I18N.getText("action.macro.addNewToSelected")));
			add(new DuplicateButtonAction(I18N.getText("action.macro.duplicateOnSelected")));
			add(new JSeparator());
			add(new DeleteButtonAction(I18N.getText("action.macro.deleteFromCommon")));
			add(new JSeparator());
			add(new ExportMacroAction(I18N.getText("action.macro.exportCommon")));
			add(new JSeparator());
			add(new RunMacroForEachSelectedTokenAction());
		} else {
			add(new AddNewButtonAction(I18N.getText("action.macro.addNewToSelected")));
			add(new JSeparator());
			add(new RunMacroForEachSelectedTokenAction());
		}
	}

	private void addCampaignActions() {
		if(TabletopTool.getPlayer().isGM()) {
			add(new EditButtonAction());
			add(new JSeparator());
			add(new AddNewButtonAction());
			add(new DuplicateButtonAction());
			add(new JSeparator());
			add(new ResetButtonAction());
			add(new DeleteButtonAction());
			add(new JSeparator());
			add(new ExportMacroAction());
			add(new JSeparator());
			add(new RunMacroForEachSelectedTokenAction());
		} else {
			add(new RunMacroForEachSelectedTokenAction());
		}
	}

	private class AddNewButtonAction extends AbstractAction {
		public AddNewButtonAction() {
			putValue(Action.NAME, I18N.getText("action.macro.new"));
		}

		public AddNewButtonAction(String name) {
			putValue(Action.NAME, name);
		}

		public void actionPerformed(ActionEvent event) {
			// TODO: refactor to put tab index from Tab enum
			if (panelClass.equals("GlobalPanel")) {
				new MacroButtonProperties(panelClass, MacroButtonPrefs.getNextIndex(), button.getProperties().getGroup());
			} else if (panelClass.equals("CampaignPanel")) {
				new MacroButtonProperties(panelClass, TabletopTool.getCampaign().getMacroButtonNextIndex(), button.getProperties().getGroup());
			} else if(panelClass.equals("SelectionPanel")) {
				if(TabletopTool.getFrame().getSelectionPanel().getCommonMacros().contains(button.getProperties())) {
					for(Token nextToken : TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList()) {
						new MacroButtonProperties(nextToken, nextToken.getMacroNextIndex(), button.getProperties().getGroup());
					}
				} else {
					new MacroButtonProperties(button.getToken(), button.getToken().getMacroNextIndex(), button.getProperties().getGroup());
				}
			} else if (button.getToken()!= null){
				new MacroButtonProperties(button.getToken(), button.getToken().getMacroNextIndex(), button.getProperties().getGroup());
			}
		}
	}

	private class EditButtonAction extends AbstractAction {
		public EditButtonAction() {
			putValue(Action.NAME, I18N.getText("action.macro.edit"));
		}

		public void actionPerformed(ActionEvent event) {
			new MacroButtonDialog().show(button);
		}
	}

	private class DeleteButtonAction extends AbstractAction {
		public DeleteButtonAction() {
			putValue(Action.NAME, I18N.getText("action.macro.delete"));
		}

		public DeleteButtonAction(String name) {
			putValue(Action.NAME, name);
		}

		public void actionPerformed(ActionEvent event) {
			if(TabletopTool.confirm(I18N.getText("confirm.macro.delete", button.getProperties().getLabel()))) {
				// remove the hot key or the hot key will remain and you'll get an exception later
				// when you want to assign that hotkey to another button.
				button.clearHotkey();

				if (panelClass.equals("GlobalPanel")) {
					MacroButtonPrefs.delete(button.getProperties());
				} else if (panelClass.equals("CampaignPanel")) {
					TabletopTool.getCampaign().deleteMacroButton(button.getProperties());
				} else if(panelClass.equals("SelectionPanel")) {
					if(TabletopTool.getFrame().getSelectionPanel().getCommonMacros().contains(button.getProperties())) {
						for(Token nextToken : TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList()) {
							if(AppUtil.playerOwns(nextToken)) {
								List<MacroButtonProperties> workingMacros = new ArrayList<MacroButtonProperties>();
								Boolean hashCodesMatch = false;
								Boolean allowDelete = false;
								for(MacroButtonProperties nextMacro : nextToken.getMacroList(true)) {
									hashCodesMatch = nextMacro.hashCodeForComparison() == button.getProperties().hashCodeForComparison();
									allowDelete = TabletopTool.getPlayer().isGM() || (!TabletopTool.getPlayer().isGM() && nextMacro.getAllowPlayerEdits());
									if(!hashCodesMatch || !allowDelete) {
										workingMacros.add(nextMacro);
									}
								}
								nextToken.replaceMacroList(workingMacros);
							}
						}
					} else {
						button.getToken().deleteMacroButtonProperty(button.getProperties());
					}
					TabletopTool.getFrame().getSelectionPanel().reset();
				} else if (button.getToken()!= null){
					if(AppUtil.playerOwns(button.getToken())) {
						button.getToken().deleteMacroButtonProperty(button.getProperties());
					}
				}
			}
		}
	}

	private class DuplicateButtonAction extends AbstractAction {
		public DuplicateButtonAction() {
			putValue(Action.NAME, I18N.getText("action.macro.duplicate"));
		}

		public DuplicateButtonAction(String name) {
			putValue(Action.NAME, name);
		}

		public void actionPerformed(ActionEvent event) {
			if (panelClass.equals("GlobalPanel")) {
				new MacroButtonProperties(panelClass, MacroButtonPrefs.getNextIndex(), button.getProperties());
			} else if (panelClass.equals("CampaignPanel")) {
				new MacroButtonProperties(panelClass, TabletopTool.getCampaign().getMacroButtonNextIndex(), button.getProperties());
			} else if(panelClass.equals("SelectionPanel")) {
				if(TabletopTool.getFrame().getSelectionPanel().getCommonMacros().contains(button.getProperties())) {
					for(Token nextToken : TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList()) {
						new MacroButtonProperties(nextToken, nextToken.getMacroNextIndex(), button.getProperties());
					}
				} else {
					new MacroButtonProperties(button.getToken(), button.getToken().getMacroNextIndex(), button.getProperties());
				}
			} else if (button.getToken() != null){
				new MacroButtonProperties(button.getToken(), button.getToken().getMacroNextIndex(), button.getProperties());
			}
		}
	}

	private class ResetButtonAction extends AbstractAction {
		public ResetButtonAction() {
			putValue(Action.NAME, I18N.getText("action.macro.reset"));
		}

		public void actionPerformed(ActionEvent event) {
			if(TabletopTool.confirm(I18N.getText("confirm.macro.reset", button.getProperties().getLabel()))) {
				button.getProperties().reset();
				button.getProperties().save();
			}
		}
	}

	private class RunMacroForEachSelectedTokenAction extends AbstractAction {
		public RunMacroForEachSelectedTokenAction() {
			putValue(Action.NAME, I18N.getText("action.macro.runForEachSelected"));
		}

		public void actionPerformed(ActionEvent event) {
			if (TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList().size() > 0) {
				button.getProperties().executeMacro(TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList());
			}
		}
	}

	private class ExportMacroAction extends AbstractAction {
			private ExportMacroAction() {
				putValue(Action.NAME, I18N.getText("action.macro.export"));
			}

			private ExportMacroAction(String name) {
				putValue(Action.NAME, name);
			}

			public void actionPerformed(ActionEvent event) {
				JFileChooser chooser = TabletopTool.getFrame().getSaveMacroFileChooser();

				if (chooser.showSaveDialog(TabletopTool.getFrame()) != JFileChooser.APPROVE_OPTION) {
					return;
				}

				final File selectedFile = chooser.getSelectedFile();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						if (selectedFile.exists()) {
						    if (selectedFile.getName().endsWith(".mtmacro")) {
						        if (!TabletopTool.confirm(I18N.getText("confirm.macro.exportInto", button.getName()))) {
						            return;
						        }
						    } else if (!TabletopTool.confirm(I18N.getText("confirm.macro.exportOverwrite"))) {
								return;
							}
						}

						try {
							if(panelClass.equals("SelectionPanel")) {
								if(TabletopTool.getFrame().getSelectionPanel().getCommonMacros().contains(button.getProperties())) {
									if(confirmCommonExport(button.getProperties())) {
										PersistenceUtil.saveMacro(button.getProperties(), selectedFile);
									} else {
										TabletopTool.showInformation(I18N.getText("msg.info.macro.exportCancel"));
										return;
									}
								} else {
									PersistenceUtil.saveMacro(button.getProperties(), selectedFile);
								}
							}
							PersistenceUtil.saveMacro(button.getProperties(), selectedFile);
							TabletopTool.showInformation(I18N.getText("msg.info.macro.exportSuccess"));
						} catch (IOException ioe) {
							ioe.printStackTrace();
							TabletopTool.showError(I18N.getText("msg.error.macro.exportFail", ioe));
						}
					}
				});
			}
	}

	private Boolean confirmCommonExport(MacroButtonProperties buttonMacro) {
		Boolean failComparison = false;
		String comparisonResults = "";
		if(!buttonMacro.getCompareGroup()) {
			failComparison = true;
			comparisonResults = comparisonResults + "<li>" + I18N.getText("component.label.macro.group") + "</li>";
		}
		if(!buttonMacro.getCompareSortPrefix()) {
			failComparison = true;
			comparisonResults = comparisonResults + "<li>" + I18N.getText("component.label.macro.sortPrefix") + "</li>";
		}
		if(!buttonMacro.getCompareCommand()) {
			failComparison = true;
			comparisonResults = comparisonResults + "<li>" + I18N.getText("component.label.macro.command") + "</li>";
		}
		if(!buttonMacro.getCompareIncludeLabel()) {
			failComparison = true;
			comparisonResults = comparisonResults + "<li>" + I18N.getText("component.label.macro.includeLabel") + "</li>";
		}
		if(!buttonMacro.getCompareAutoExecute()) {
			failComparison = true;
			comparisonResults = comparisonResults + "<li>" + I18N.getText("component.label.macro.autoExecute") + "</li>";
		}
		if(!buttonMacro.getApplyToTokens()) {
			failComparison = true;
			comparisonResults = comparisonResults + "<li>" + I18N.getText("component.label.macro.applyToSelected") + "</li>";
		}
		if(failComparison) {
		failComparison = TabletopTool.confirm(I18N.getText("msg.error.macro.exportFail", buttonMacro.getLabel(), comparisonResults));
		}
		return failComparison;
	}
}
