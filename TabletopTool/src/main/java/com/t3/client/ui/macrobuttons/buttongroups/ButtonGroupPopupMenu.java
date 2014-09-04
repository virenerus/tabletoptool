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
package com.t3.client.ui.macrobuttons.buttongroups;

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
import com.t3.client.ui.macrobuttons.buttons.MacroButtonPrefs;
import com.t3.client.ui.macrobuttons.panels.CampaignPanel;
import com.t3.client.ui.macrobuttons.panels.GlobalPanel;
import com.t3.language.I18N;
import com.t3.model.MacroButtonProperties;
import com.t3.model.Token;
import com.t3.persistence.PersistenceUtil;
import com.t3.util.guidreference.NullHelper;
import com.t3.util.guidreference.TokenReference;

@SuppressWarnings("serial")
public class ButtonGroupPopupMenu extends JPopupMenu {

	private final AreaGroup areaGroup;
	private final String macroGroup;
	private final String panelClass;
	private TokenReference token;

	public ButtonGroupPopupMenu(String panelClass, AreaGroup areaGroup, String macroGroup, Token token) {
		this.areaGroup = areaGroup;
		this.macroGroup = macroGroup;
		this.panelClass = panelClass;
		this.token=NullHelper.referenceToken(token);
		if(panelClass.equals("SelectionPanel")) {
			if(areaGroup != null) {
				if(areaGroup.getGroupLabel().equals(I18N.getText("component.areaGroup.macro.commonMacros"))) {
					addCommonActions();
				} else {
					addActions();
				}
			}
		} else if(panelClass.equals("CampaignPanel")) {
			addCampaignActions();
		} else {
			addActions();
		}
	}

	private void addActions() {
		add(new AddMacroAction());
		add(new JSeparator());
		add(new ImportMacroAction());
		add(new JSeparator());
		add(new ImportMacroSetAction());
		add(new ExportMacroSetAction());
		add(new JSeparator());
		add(new ClearGroupAction());
		if(!this.panelClass.equals("SelectionPanel")) {
			add(new JSeparator());
			add(new ClearPanelAction());
		}
	}

	private void addCommonActions() {
		add(new AddMacroAction(I18N.getText("action.macro.addNewToSelected")));
		add(new JSeparator());
		add(new ImportMacroAction(I18N.getText("action.macro.importToSelected")));
		add(new JSeparator());
		add(new ImportMacroSetAction(I18N.getText("action.macro.importSetToSelected")));
		add(new ExportMacroSetAction(I18N.getText("action.macro.exportCommonSet")));
	}

	private void addCampaignActions() {
		if(TabletopTool.getPlayer().isGM()) {
			add(new AddMacroAction());
			add(new JSeparator());
			add(new ImportMacroAction());
			add(new JSeparator());
			add(new ImportMacroSetAction());
			add(new ExportMacroSetAction());
			add(new JSeparator());
			add(new ClearGroupAction());
			add(new JSeparator());
			add(new ClearPanelAction());
		}
	}

	private class AddMacroAction extends AbstractAction {
		public AddMacroAction() {
			putValue(Action.NAME, I18N.getText("action.macro.new"));
		}

		public AddMacroAction(String name) {
			putValue(Action.NAME, name);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			if (panelClass.equals("GlobalPanel")) {
				new MacroButtonProperties(panelClass, MacroButtonPrefs.getNextIndex(), macroGroup);
			} else if (panelClass.equals("CampaignPanel")) {
				new MacroButtonProperties(panelClass, TabletopTool.getCampaign().getMacroButtonNextIndex(), macroGroup);
			} else if(panelClass.equals("SelectionPanel")) {
				if(areaGroup != null) {
					if(areaGroup.getGroupLabel().equals(I18N.getText("component.areaGroup.macro.commonMacros"))) {
						for(Token nextToken : TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList()) {
							new MacroButtonProperties(nextToken, nextToken.getMacroNextIndex(), macroGroup);
						}
					} else if(token != null)
						new MacroButtonProperties(token.value(), token.value().getMacroNextIndex(), macroGroup);
				}
			} else if (token != null)
				new MacroButtonProperties(token.value(), token.value().getMacroNextIndex(), macroGroup);
		}
	}

	private class ImportMacroAction extends AbstractAction {
			public ImportMacroAction() {
				putValue(Action.NAME, I18N.getText("action.macro.import"));
			}

			public ImportMacroAction(String name) {
				putValue(Action.NAME, name);
			}

			@Override
			public void actionPerformed(ActionEvent event) {

				JFileChooser chooser = TabletopTool.getFrame().getLoadMacroFileChooser();

				if (chooser.showOpenDialog(TabletopTool.getFrame()) != JFileChooser.APPROVE_OPTION) {
					return;
				}

				final File selectedFile = chooser.getSelectedFile();
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							MacroButtonProperties newButtonProps = PersistenceUtil.loadMacro(selectedFile);
							Boolean alreadyExists = false;
							if (panelClass.equals("GlobalPanel")) {
								for(MacroButtonProperties nextMacro : MacroButtonPrefs.getButtonProperties()) {
									if(newButtonProps.hashCodeForComparison() == nextMacro.hashCodeForComparison()) {
										alreadyExists = true;
									}
								}
								if(alreadyExists) {
									alreadyExists = confirmImport(newButtonProps, I18N.getText("confirm.macro.panelLocation", I18N.getText("panel.Global")));
								}
								if(!alreadyExists) {
								new MacroButtonProperties(panelClass, MacroButtonPrefs.getNextIndex(), newButtonProps);
								}
							} else if (panelClass.equals("CampaignPanel")) {
								for(MacroButtonProperties nextMacro : TabletopTool.getCampaign().getMacroButtonPropertiesArray()) {
									if(newButtonProps.hashCodeForComparison() == nextMacro.hashCodeForComparison()) {
										alreadyExists = true;
									}
								}
								if(alreadyExists) {
									alreadyExists = confirmImport(newButtonProps, I18N.getText("confirm.macro.panelLocation", I18N.getText("panel.Campaign")));
								}
								if(!alreadyExists) {
								new MacroButtonProperties(panelClass, TabletopTool.getCampaign().getMacroButtonNextIndex(), newButtonProps);
								}
							} else if(panelClass.equals("SelectionPanel")) {
								if(areaGroup != null) {
									if(areaGroup.getGroupLabel().equals(I18N.getText("component.areaGroup.macro.commonMacros"))) {
										for(Token nextToken : TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList()) {
											alreadyExists = false;
											for(MacroButtonProperties nextMacro : nextToken.getMacroList(true)) {
												if(newButtonProps.hashCodeForComparison() == nextMacro.hashCodeForComparison()) {
													alreadyExists = true;
												}
											}
											if(alreadyExists) {
												alreadyExists = confirmImport(newButtonProps, I18N.getText("confirm.macro.commonSelectionLocation"));
											}
											if(!alreadyExists) {
												new MacroButtonProperties(nextToken, nextToken.getMacroNextIndex(), newButtonProps);
											}
										}
							} else if (token != null){
								Token tokenV = token.value();
										for(MacroButtonProperties nextMacro : tokenV.getMacroList(true)) {
											if(newButtonProps.hashCodeForComparison() == nextMacro.hashCodeForComparison()) {
												alreadyExists = true;
											}
										}
										if(alreadyExists) {
											String tokenName = tokenV.getName();
											if(TabletopTool.getPlayer().isGM()) {
												if(tokenV.getGMName() != null) {
													if(!tokenV.getGMName().equals("")) {
														tokenName = tokenName + "(" + tokenV.getGMName() + ")";
													}
												}
											}
											alreadyExists = confirmImport(newButtonProps, I18N.getText("confirm.macro.tokenLocation", tokenName));
										}
										if(!alreadyExists) {
								new MacroButtonProperties(tokenV, tokenV.getMacroNextIndex(), newButtonProps);
							}
									}
								}
							} else if (token != null){
								Token tokenV = token.value();
								for(MacroButtonProperties nextMacro : tokenV.getMacroList(true)) {
									if(newButtonProps.hashCodeForComparison() == nextMacro.hashCodeForComparison()) {
										alreadyExists = true;
									}
								}
								if(alreadyExists) {
									String tokenName = tokenV.getName();
									if(TabletopTool.getPlayer().isGM()) {
										if(tokenV.getGMName() != null) {
											if(!tokenV.getGMName().equals("")) {
												tokenName = tokenName + "(" + tokenV.getGMName() + ")";
											}
										}
									}
									alreadyExists = confirmImport(newButtonProps, I18N.getText("confirm.macro.tokenLocation", tokenName));
								}
								if(!alreadyExists) {
									new MacroButtonProperties(tokenV, tokenV.getMacroNextIndex(), newButtonProps);
								}
							}
						} catch (IOException ioe) {
							ioe.printStackTrace();
							TabletopTool.showError(I18N.getText("msg.error.macro.exportSetFail", ioe));
						}
					}
				});
			}
		}

	private class ImportMacroSetAction extends AbstractAction {
		public ImportMacroSetAction() {
			putValue(Action.NAME, I18N.getText("action.macro.importSet"));
		}

		public ImportMacroSetAction(String name) {
			putValue(Action.NAME, name);
		}

		@Override
		public void actionPerformed(ActionEvent event) {

			JFileChooser chooser = TabletopTool.getFrame().getLoadMacroSetFileChooser();

			if (chooser.showOpenDialog(TabletopTool.getFrame()) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			final File selectedFile = chooser.getSelectedFile();
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						List<MacroButtonProperties> newButtonProps = PersistenceUtil.loadMacroSet(selectedFile);
						Boolean alreadyExists = false;
						for(MacroButtonProperties nextProps : newButtonProps) {
							alreadyExists = false;
							if (panelClass.equals("GlobalPanel")) {
								for(MacroButtonProperties nextMacro : MacroButtonPrefs.getButtonProperties()) {
									if(nextProps.hashCodeForComparison() == nextMacro.hashCodeForComparison()) {
										alreadyExists = true;
									}
								}
								if(alreadyExists) {
									alreadyExists = confirmImport(nextProps, I18N.getText("confirm.macro.panelLocation", I18N.getText("panel.Global")));
								}
								if(!alreadyExists) {
									new MacroButtonProperties(panelClass, MacroButtonPrefs.getNextIndex(), nextProps);
								}
							} else if (panelClass.equals("CampaignPanel")) {
								for(MacroButtonProperties nextMacro : TabletopTool.getCampaign().getMacroButtonPropertiesArray()) {
									if(nextProps.hashCodeForComparison() == nextMacro.hashCodeForComparison()) {
										alreadyExists = true;
									}
								}
								if(alreadyExists) {
									alreadyExists = confirmImport(nextProps, I18N.getText("confirm.macro.panelLocation", I18N.getText("panel.Campaign")));
								}
								if(!alreadyExists) {
								new MacroButtonProperties(panelClass, TabletopTool.getCampaign().getMacroButtonNextIndex(), nextProps);
								}
							} else if(panelClass.equals("SelectionPanel")) {
								if(areaGroup != null) {
									if(areaGroup.getGroupLabel().equals(I18N.getText("component.areaGroup.macro.commonMacros"))) {
										for(Token nextToken : TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList()) {
											alreadyExists = false;
											for(MacroButtonProperties nextMacro : nextToken.getMacroList(true)) {
												if(nextProps.hashCodeForComparison() == nextMacro.hashCodeForComparison()) {
													alreadyExists = true;
												}
											}
											if(alreadyExists) {
												alreadyExists = confirmImport(nextProps, I18N.getText("confirm.macro.commonSelectionLocation"));
											}
											if(!alreadyExists) {
												new MacroButtonProperties(nextToken, nextToken.getMacroNextIndex(), nextProps);
											}
										}
									} else if(token != null){
										Token tokenV = token.value();
										for(MacroButtonProperties nextMacro : tokenV.getMacroList(true)) {
											if(nextProps.hashCodeForComparison() == nextMacro.hashCodeForComparison()) {
												alreadyExists = true;
											}
										}
										if(alreadyExists) {
											String tokenName = tokenV.getName();
											if(TabletopTool.getPlayer().isGM()) {
												if(tokenV.getGMName() != null) {
													if(!tokenV.getGMName().equals("")) {
														tokenName = tokenName + "(" + tokenV.getGMName() + ")";
													}
												}
											}
											alreadyExists = confirmImport(nextProps, I18N.getText("confirm.macro.tokenLocation", tokenName));
										}
										if(!alreadyExists) {
											new MacroButtonProperties(tokenV, tokenV.getMacroNextIndex(), nextProps);
										}
									}
								}
							} else if (token != null){
								Token tokenV = token.value();
								for(MacroButtonProperties nextMacro : tokenV.getMacroList(true)) {
									if(nextProps.hashCodeForComparison() == nextMacro.hashCodeForComparison()) {
										alreadyExists = true;
									}
								}
								if(alreadyExists) {
									String tokenName = tokenV.getName();
									if(TabletopTool.getPlayer().isGM()) {
										if(tokenV.getGMName() != null) {
											if(!tokenV.getGMName().equals("")) {
												tokenName = tokenName + "(" + tokenV.getGMName() + ")";
											}
										}
									}
									alreadyExists = confirmImport(nextProps, I18N.getText("confirm.macro.tokenLocation", tokenName));
								}
								if(!alreadyExists) {
								new MacroButtonProperties(tokenV, tokenV.getMacroNextIndex(), nextProps);
							}
						}
						}
					} catch (IOException ioe) {
						TabletopTool.showError("msg.error.macro.importSetFail", ioe);
					}
				}
			});
		}
	}

	private class ExportMacroSetAction extends AbstractAction {
		public ExportMacroSetAction() {
			putValue(Action.NAME, I18N.getText("action.macro.exportSet"));
		}

		public ExportMacroSetAction(String name) {
			putValue(Action.NAME, name);
		}

		@Override
		public void actionPerformed(ActionEvent event) {

			JFileChooser chooser = TabletopTool.getFrame().getSaveMacroSetFileChooser();

			if (chooser.showSaveDialog(TabletopTool.getFrame()) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			final File selectedFile = chooser.getSelectedFile();

			if (selectedFile.exists()) {
			    if (selectedFile.getName().endsWith(".mtmacset")) {
			        if (!TabletopTool.confirm(I18N.getText("confirm.macro.exportSetInto"))) {
			            return;
			        }
			    } else if (!TabletopTool.confirm(I18N.getText("confirm.macro.exportSetOverwrite"))) {
					return;
				}
			}

			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						if (panelClass.equals("GlobalPanel")) {
							PersistenceUtil.saveMacroSet(MacroButtonPrefs.getButtonProperties(), selectedFile);
						} else if (panelClass.equals("CampaignPanel")) {
							PersistenceUtil.saveMacroSet(TabletopTool.getCampaign().getMacroButtonPropertiesArray(), selectedFile);
						} else if(panelClass.equals("SelectionPanel")) {
							if(areaGroup != null) {
								if(areaGroup.getGroupLabel().equals(I18N.getText("component.areaGroup.macro.commonMacros"))) {
									Boolean checkComparisons = TabletopTool.confirm("confirm.macro.checkComparisons");
									List<MacroButtonProperties> commonMacros = TabletopTool.getFrame().getSelectionPanel().getCommonMacros();
									List<MacroButtonProperties> exportList = new ArrayList<MacroButtonProperties>();
									Boolean trusted = true;
									Boolean allowExport = true;
									for(MacroButtonProperties nextMacro : commonMacros) {
										trusted = true;
										allowExport = true;
										for(Token nextToken : TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList()) {
											if(!AppUtil.playerOwns(nextToken)) {
												trusted = false;
											}
											if(nextToken.getMacroList(trusted).size() > 0) {
												for(MacroButtonProperties nextCompMacro : nextToken.getMacroList(trusted)) {
													if(nextCompMacro.hashCodeForComparison() == nextMacro.hashCodeForComparison()
															&& (!TabletopTool.getPlayer().isGM() || (!TabletopTool.getPlayer().isGM() && !nextCompMacro.getAllowPlayerEdits()))) {
														allowExport = false;
													}
												}
											} else {
												allowExport = false;
											}
										}
										if(checkComparisons) {
											if(confirmCommonExport(nextMacro)) {
												if(trusted && allowExport) {
													exportList.add(nextMacro);
												} else {
													TabletopTool.showWarning(I18N.getText("msg.warning.macro.willNotExport", nextMacro.getLabel()));
												}
											} else {
												return;
											}
										} else {
											if(trusted && allowExport) {
												exportList.add(nextMacro);
											} else {
												TabletopTool.showWarning(I18N.getText("msg.warning.macro.willNotExport", nextMacro.getLabel()));
											}
										}
									}
									PersistenceUtil.saveMacroSet(exportList, selectedFile);
						} else if (token != null){
							Token tokenV = token.value();
									Boolean trusted = AppUtil.playerOwns(tokenV);
									List<MacroButtonProperties> exportList = new ArrayList<MacroButtonProperties>();
									for(MacroButtonProperties nextMacro : tokenV.getMacroList(trusted)) {
										if(TabletopTool.getPlayer().isGM() || (!TabletopTool.getPlayer().isGM() && nextMacro.getAllowPlayerEdits())) {
											exportList.add(nextMacro);
										} else {
											TabletopTool.showWarning(I18N.getText("msg.warning.macro.willNotExport", nextMacro.getLabel()));
										}
									}
									PersistenceUtil.saveMacroSet(exportList, selectedFile);
								}
							}
						} else if (token != null)
							PersistenceUtil.saveMacroSet(token.value().getMacroList(true), selectedFile);
					} catch (IOException ioe) {
						ioe.printStackTrace();
						TabletopTool.showError(I18N.getText("msg.error.macro.exportSetFail", ioe));
					}
				}
			});
		}
	}

	private class ClearGroupAction extends AbstractAction {
		public ClearGroupAction() {
			putValue(Action.NAME, I18N.getText("action.macro.clearGroup"));
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			if(TabletopTool.confirm(I18N.getText("confirm.macro.clearGroup", macroGroup))) {
				if (panelClass.equals("GlobalPanel")) {
					GlobalPanel.deleteButtonGroup(macroGroup);
				} else if (panelClass.equals("CampaignPanel")) {
					CampaignPanel.deleteButtonGroup(macroGroup);
				} else if (token != null){
					token.value().deleteMacroGroup(macroGroup, true);
				}
			}
		}
	}

	private class ClearPanelAction extends AbstractAction {
		public ClearPanelAction() {
			putValue(Action.NAME, I18N.getText("action.macro.clearPanel"));
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			if (panelClass.equals("GlobalPanel")) {
				if(TabletopTool.confirm(I18N.getText("confirm.macro.clearPanel", I18N.getText("panel.Global")))) {
					GlobalPanel.clearPanel();
				}
			} else if (panelClass.equals("CampaignPanel")) {
				if(TabletopTool.confirm(I18N.getText("confirm.macro.clearPanel", I18N.getText("panel.Campaign")))) {
					CampaignPanel.clearPanel();
				}
			} else if (token != null) {
				if(panelClass.equals("ImpersonatePanel")) {
					if(TabletopTool.confirm(I18N.getText("confirm.macro.clearPanel", I18N.getText("panel.Impersonate")))) {
						token.value().deleteAllMacros(true);
					}
				}
			}
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
		if(failComparison) {
		failComparison = TabletopTool.confirm(I18N.getText("confirm.macro.failComparison", buttonMacro.getLabel(), comparisonResults));
		}
		return failComparison;
	}

	private Boolean confirmImport(MacroButtonProperties importMacro, String location) {
		return !TabletopTool.confirm(I18N.getText("confirm.macro.import", importMacro.getLabel(), location));
	}
}
