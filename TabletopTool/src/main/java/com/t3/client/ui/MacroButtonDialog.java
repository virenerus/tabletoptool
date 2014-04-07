/*
 * This software copyright by various authors including the RPTools.net
 * development team, and licensed under the LGPL Version 3 or, at your option,
 * any later version.
 * 
 * Portions of this software were originally covered under the Apache Software
 * License, Version 1.1 or Version 2.0.
 * 
 * See the file LICENSE elsewhere in this distribution for license details.
 */
package com.t3.client.ui;

import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.FunctionCompletion;
import org.fife.ui.autocomplete.ParameterizedCompletion.Parameter;

import com.t3.client.AppConstants;
import com.t3.client.AppUtil;
import com.t3.client.T3Util;
import com.t3.client.TabletopTool;
import com.t3.client.ui.forms.MacroButtonBaseDialog;
import com.t3.client.ui.macrobuttons.buttons.MacroButton;
import com.t3.language.I18N;
import com.t3.macro.api.functions.DialogFunctions;
import com.t3.macro.api.functions.InfoFunctions;
import com.t3.macro.api.functions.MapFunctions;
import com.t3.macro.api.functions.PathFunctions;
import com.t3.macro.api.functions.PlayerFunctions;
import com.t3.macro.api.functions.input.InputFunctions;
import com.t3.model.MacroButtonProperties;
import com.t3.model.Token;
import com.t3.swing.SwingUtil;
import com.t3.swing.preference.WindowPreferences;

public class MacroButtonDialog extends MacroButtonBaseDialog {

	MacroButton button;
	MacroButtonProperties properties;
	boolean isTokenMacro = false;
	int oldHashCode = 0;
	Boolean startingCompareGroup;
	Boolean startingCompareSortPrefix;
	Boolean startingCompareCommand;
	Boolean startingAllowPlayerEdits;

	public MacroButtonDialog() {
		SwingUtil.centerOver(this, TabletopTool.getFrame());

		getRootPane().setDefaultButton(okButton);
		installHotKeyCombo();
		installColorCombo();
		installFontColorCombo();
		installFontSizeCombo();

		initCommandTextArea();

		hotKeyCombo.setEnabled(!isTokenMacro);
		
		maxWidthTextField.setEnabled(false); // can't get max-width to work, so temporarily disabling it.
		allowPlayerEditsCheckBox.setEnabled(TabletopTool.getPlayer().isGM());

		new WindowPreferences(AppConstants.APP_NAME, "editMacroDialog", this);
	}

	private void installHotKeyCombo() {
		String[] hotkeys = MacroButtonHotKeyManager.HOTKEYS;
		for (int i = 0; i < hotkeys.length; i++)
			hotKeyCombo.insertItemAt(hotkeys[i], i);
	}

	private void installColorCombo() {
		colorComboBox.setModel(new DefaultComboBoxModel<String>(T3Util.getColorNames().toArray(new String[0])));
		colorComboBox.insertItemAt("default", 0);
		colorComboBox.setSelectedItem("default");
		colorComboBox.setRenderer(new ColorComboBoxRenderer());
	}

	private void installFontColorCombo() {
		fontColorComboBox.setModel(new DefaultComboBoxModel<String>(MacroButtonProperties.getFontColors()));
		fontColorComboBox.setSelectedItem("black");
		fontColorComboBox.setRenderer(new ColorComboBoxRenderer());
	}

	private void installFontSizeCombo() {
		String[] fontSizes = { "0.75em", "0.80em", "0.85em", "0.90em", "0.95em", "1.00em", "1.05em", "1.10em", "1.15em", "1.20em", "1.25em" };
		fontSizeComboBox.setModel(new DefaultComboBoxModel<String>(fontSizes));
	}

	public void show(MacroButton button) {
		initI18NSupport();
		this.button = button;
		this.isTokenMacro = button.getToken() == null ? false : true;
		this.properties = button.getProperties();
		oldHashCode = properties.hashCodeForComparison();
		
		Boolean playerCanEdit = !TabletopTool.getPlayer().isGM() && properties.getAllowPlayerEdits();
		Boolean onGlobalPanel = properties.getSaveLocation().equals("Global");
		Boolean allowEdits = onGlobalPanel || TabletopTool.getPlayer().isGM() || playerCanEdit;
		Boolean isCommonMacro = button.getPanelClass().equals("SelectionPanel") && TabletopTool.getFrame().getSelectionPanel().getCommonMacros().contains(properties);
		if (allowEdits) {
			this.setTitle(I18N.getText("component.dialogTitle.macro.macroID") + ": " + Integer.toString(this.properties.hashCodeForComparison()));

			colorComboBox.setSelectedItem(properties.getColorKey());
			hotKeyCombo.setSelectedItem(properties.getHotKey());
			labelTextField.setText(properties.getLabel());
			groupTextField.setText(properties.getGroup());
			sortByTextField.setText(properties.getSortby());
			commandTextArea.setText(properties.getCommand());
			commandTextArea.setCaretPosition(0);
			commandTextArea.discardAllEdits(); //this removes all edits, otherwise adding all the text is an edit itself

			fontColorComboBox.setSelectedItem(properties.getFontColorKey());
			fontSizeComboBox.setSelectedItem(properties.getFontSize());
			minWidthTextField.setText(properties.getMinWidth());
			maxWidthTextField.setText(properties.getMaxWidth());
			compareGroupCheckBox.setSelected(properties.getCompareGroup());
			compareSortPrefixCheckBox.setSelected(properties.getCompareSortPrefix());
			compareCommandCheckBox.setSelected(properties.getCompareCommand());
			allowPlayerEditsCheckBox.setSelected(properties.getAllowPlayerEdits());
			tooltipTextField.setText(properties.getToolTip());

			if (isCommonMacro) {
				colorComboBox.setEnabled(false);
				hotKeyCombo.setEnabled(false);
				groupTextField.setEnabled(properties.getCompareGroup());
				sortByTextField.setEnabled(properties.getCompareSortPrefix());
				commandTextArea.setEnabled(properties.getCompareCommand());
				fontColorComboBox.setEnabled(false);
				fontSizeComboBox.setEnabled(false);
				minWidthTextField.setEnabled(false);
				maxWidthTextField.setEnabled(false);
			}
			startingCompareGroup = properties.getCompareGroup();
			startingCompareSortPrefix = properties.getCompareSortPrefix();
			startingCompareCommand = properties.getCompareCommand();
			startingAllowPlayerEdits = properties.getAllowPlayerEdits();

			setVisible(true);
		} else {
			TabletopTool.showWarning(I18N.getText("msg.warning.macro.playerChangesNotAllowed"));
		}
	}

	private void initCommandTextArea() {
		//TODO create a real code completion -> the one below is crap
		/*CompletionProvider provider = createCompletionProvider();
		AutoCompletion ac = new AutoCompletion(provider);
		ac.setAutoActivationEnabled(true);
		ac.setParameterAssistanceEnabled(true);
		ac.setShowDescWindow(true);
		ac.install(commandTextArea);*/
	}
	
	private CompletionProvider createCompletionProvider() {
		// A DefaultCompletionProvider is the simplest concrete implementation
		// of CompletionProvider. This provider has no understanding of
		// language semantics. It simply checks the text entered up to the
		// caret position for a match against known completions. This is all
		// that is needed in the majority of cases.
		DefaultCompletionProvider provider = new DefaultCompletionProvider();
		provider.setParameterizedCompletionParams('(', ", " , ')');
		provider.setAutoActivationRules(false, ".");
		
		// Add completions for all Java keywords. A BasicCompletion is just
		// a straightforward word completion.
		//TODO create a real xml file to parse from with comments
		provider.addCompletion(new BasicCompletion(provider,"print","Printing a String","<b>Printing</b> a string."));
		createDynamicCompletions(provider, "info", InfoFunctions.class);
		createDynamicCompletions(provider, "player", PlayerFunctions.class);
		createDynamicCompletions(provider, "map", MapFunctions.class);
		createDynamicCompletions(provider, "dialog", DialogFunctions.class);
		createDynamicCompletions(provider, "path", PathFunctions.class);
		createDynamicCompletions(provider, "input", InputFunctions.class);
		
		return provider;

	}
	
	private void createDynamicCompletions(DefaultCompletionProvider prov, String lib, Class<?> c) {
		for(Method m:c.getMethods()) {
			if(m.getDeclaringClass().equals(c)) {
				FunctionCompletion fc = new FunctionCompletion(prov, lib+'.'+m.getName(),m.getReturnType().getSimpleName());
				
				LinkedList<Parameter> params=new LinkedList<Parameter>();
				Class<?>[] pts=m.getParameterTypes();
				for(int i=0;i<pts.length;i++)
					params.add(new Parameter(pts[i].getSimpleName(), "arg"+i));
				if(!params.isEmpty())
					fc.setParams(params);
				prov.addCompletion(fc);
			}
		}
	}

	private void save() {
		String hotKey = hotKeyCombo.getSelectedItem().toString();
		button.getHotKeyManager().assignKeyStroke(hotKey);
		button.setColor(colorComboBox.getSelectedItem().toString());
		button.setText(this.button.getButtonText());
		properties.setHotKey(hotKey);
		properties.setColorKey(colorComboBox.getSelectedItem().toString());
		properties.setLabel(labelTextField.getText());
		properties.setGroup(groupTextField.getText());
		properties.setSortby(sortByTextField.getText());
		properties.setCommand(commandTextArea.getText());
		properties.setFontColorKey(fontColorComboBox.getSelectedItem().toString());
		properties.setFontSize(fontSizeComboBox.getSelectedItem().toString());
		properties.setMinWidth(minWidthTextField.getText());
		properties.setMaxWidth(maxWidthTextField.getText());
		properties.setCompareGroup(compareGroupCheckBox.isSelected());
		properties.setCompareSortPrefix(compareSortPrefixCheckBox.isSelected());
		properties.setCompareCommand(compareCommandCheckBox.isSelected());
		properties.setAllowPlayerEdits(allowPlayerEditsCheckBox.isSelected());
		properties.setToolTip(tooltipTextField.getText());

		properties.save();

		if (button.getPanelClass().equals("SelectionPanel")) {
			if (TabletopTool.getFrame().getSelectionPanel().getCommonMacros().contains(button.getProperties())) {
				Boolean changeAllowPlayerEdits = false;
				Boolean endingAllowPlayerEdits = false;
				if (startingAllowPlayerEdits) {
					if (!properties.getAllowPlayerEdits()) {
						Boolean confirmDisallowPlayerEdits = TabletopTool.confirm(I18N.getText("confirm.macro.disallowPlayerEdits"));
						if (confirmDisallowPlayerEdits) {
							changeAllowPlayerEdits = true;
							endingAllowPlayerEdits = false;
						} else {
							properties.setAllowPlayerEdits(true);
						}
					}
				} else {
					if (properties.getAllowPlayerEdits()) {
						Boolean confirmAllowPlayerEdits = TabletopTool.confirm(I18N.getText("confirm.macro.allowPlayerEdits"));
						if (confirmAllowPlayerEdits) {
							changeAllowPlayerEdits = true;
							endingAllowPlayerEdits = true;
						} else {
							properties.setAllowPlayerEdits(false);
						}
					}
				}
				Boolean trusted = true;
				for (Token nextToken : TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList()) {
					if (AppUtil.playerOwns(nextToken)) {
						trusted = true;
					} else {
						trusted = false;
					}
					boolean isGM = TabletopTool.getPlayer().isGM();
					for (MacroButtonProperties nextMacro : nextToken.getMacroList(trusted)) {
						if (isGM) { //FIXME or should this be if true?
							if (nextMacro.hashCodeForComparison() == oldHashCode) {
								nextMacro.setLabel(properties.getLabel());
								if (properties.getCompareGroup() && startingCompareGroup) {
									nextMacro.setGroup(properties.getGroup());
								}
								if (properties.getCompareSortPrefix() && startingCompareSortPrefix) {
									nextMacro.setSortby(properties.getSortby());
								}
								if (properties.getCompareCommand() && startingCompareCommand) {
									nextMacro.setCommand(properties.getCommand());
								}
								if (changeAllowPlayerEdits) {
									nextMacro.setAllowPlayerEdits(endingAllowPlayerEdits);
								}
								nextMacro.setCompareGroup(properties.getCompareGroup());
								nextMacro.setCompareSortPrefix(properties.getCompareSortPrefix());
								nextMacro.setCompareCommand(properties.getCompareCommand());
								nextMacro.save();
							}
						}
					}
				}
			}
			TabletopTool.getFrame().getSelectionPanel().reset();
		}
		if (button.getPanelClass().equals("CampaignPanel")) {
			TabletopTool.serverCommand().updateCampaignMacros(TabletopTool.getCampaign().getMacroButtonPropertiesArray());
			TabletopTool.getFrame().getCampaignPanel().reset();
		}
		setVisible(false);
	}

	private void cancel() {
		setVisible(false);
	}

	private void initI18NSupport() {
		macroTabs.setTitleAt(0, I18N.getText("component.tab.macro.details"));
		macroTabs.setTitleAt(1, I18N.getText("component.tab.macro.options"));
		macroLabelLabel.setText(I18N.getText("component.label.macro.label") + ":");
		labelTextField.setToolTipText(I18N.getText("component.tooltip.macro.label"));
		macroGroupLabel.setText(I18N.getText("component.label.macro.group") + ":");
		groupTextField.setToolTipText(I18N.getText("component.tooltip.macro.group"));
		macroSortPrefixLabel.setText(I18N.getText("component.label.macro.sortPrefix") + ":");
		sortByTextField.setToolTipText(I18N.getText("component.tooltip.macro.sortPrefix"));
		macroHotKeyLabel.setText(I18N.getText("component.label.macro.hotKey") + ":");
		hotKeyCombo.setToolTipText(I18N.getText("component.tooltip.macro.hotKey"));
		macroCommandLabel.setText(I18N.getText("component.label.macro.command"));
		macroButtonColorLabel.setText(I18N.getText("component.label.macro.buttonColor") + ":");
		colorComboBox.setToolTipText(I18N.getText("component.tooltip.macro.buttonColor"));
		macroFontColorLabel.setText(I18N.getText("component.label.macro.fontColor") + ":");
		fontColorComboBox.setToolTipText(I18N.getText("component.tooltip.macro.fontColor"));
		macroFontSizeLabel.setText(I18N.getText("component.label.macro.fontSize") + ":");
		fontSizeComboBox.setToolTipText(I18N.getText("component.tooltip.macro.fontSize"));
		macroMinWidthLabel.setText(I18N.getText("component.label.macro.minWidth") + ":");
		minWidthTextField.setToolTipText(I18N.getText("component.tooltip.macro.minWidth"));
		macroMaxWidthLabel.setText(I18N.getText("component.label.macro.maxWidth") + ":");
		maxWidthTextField.setToolTipText(I18N.getText("component.tooltip.macro.maxWidth"));
		macroToolTipLabel.setText(I18N.getText("component.label.macro.toolTip") + ":");
		tooltipTextField.setToolTipText(I18N.getText("component.tooltip.macro.tooltip"));
		allowPlayerEditsCheckBox.setText(I18N.getText("component.label.macro.allowPlayerEdits"));
		allowPlayerEditsCheckBox.setToolTipText(I18N.getText("component.tooltip.macro.allowPlayerEdits"));
		((TitledBorder) macroComparisonGridView.getBorder()).setTitle(I18N.getText("component.label.macro.macroCommonality"));
		compareGroupCheckBox.setText(I18N.getText("component.label.macro.compareUseGroup"));
		compareGroupCheckBox.setToolTipText(I18N.getText("component.tooltip.macro.compareUseGroup"));
		compareSortPrefixCheckBox.setText(I18N.getText("component.label.macro.compareUseSortPrefix"));
		compareSortPrefixCheckBox.setToolTipText(I18N.getText("component.tooltip.macro.compareUseSortPrefix"));
		compareCommandCheckBox.setText(I18N.getText("component.label.macro.compareUseCommand"));
		compareCommandCheckBox.setToolTipText(I18N.getText("component.tooltip.macro.compareUseCommand"));
	}

	@Override
	protected void okButtonClicked(ActionEvent e) {
		save();
	}

	@Override
	protected void cancelButtonClicked(ActionEvent e) {
		cancel();
	}
}
