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
package com.t3.client.ui.macrobuttons.panels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import com.jidesoft.docking.DockableFrame;
import com.t3.CodeTimer;
import com.t3.client.AppState;
import com.t3.client.AppStyle;
import com.t3.client.AppUtil;
import com.t3.client.TabletopTool;
import com.t3.client.ui.T3Frame;
import com.t3.client.ui.T3Frame.MTFrame;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.language.I18N;
import com.t3.model.MacroButtonProperties;
import com.t3.model.Token;
import com.t3.util.guidreference.TokenReference;

public class SelectionPanel extends AbstractMacroPanel {
	private static final Logger log = Logger.getLogger(SelectionPanel.class);

	private final List<Token> tokenList = null;
	private List<MacroButtonProperties> commonMacros = new ArrayList<MacroButtonProperties>();
	private CodeTimer timer;

	public SelectionPanel() {
		// TODO: refactoring reminder
		setPanelClass("SelectionPanel");
		init(new ArrayList<Token>()); // when initially loading MT, the CurrentZoneRenderer isn't ready yet; just send an empty list
	}

	public List<MacroButtonProperties> getCommonMacros() {
		return commonMacros;
	}

	public void setCommonMacros(List<MacroButtonProperties> newCommonMacros) {
		commonMacros = newCommonMacros;
	}

	public void init() {
		T3Frame f = TabletopTool.getFrame();
		ZoneRenderer zr = f.getCurrentZoneRenderer();
		if (zr != null)
			init(zr.getSelectedTokensList());
	}

	public void init(List<Token> selectedTokenList) {
		boolean panelVisible = true;

		if (TabletopTool.getFrame() != null) {
			DockableFrame selectionPanel = TabletopTool.getFrame().getDockingManager().getFrame("SELECTION");
			if (selectionPanel != null)
				panelVisible = (selectionPanel.isVisible() && !selectionPanel.isAutohide()) || selectionPanel.isAutohideShowing() ? true : false;
		}
		// Set up a code timer to get some performance data
		timer = new CodeTimer("selectionpanel");
		timer.setEnabled(AppState.isCollectProfilingData() || log.isDebugEnabled());
		timer.setThreshold(10);

		timer.start("painting");

		// paint panel only when it's visible or active
		if (panelVisible) {
			// add the selection panel controls first
			add(new MenuButtonsPanel());

			// draw common group only when there is more than one token selected
			if (selectedTokenList.size() > 1) {
				populateCommonButtons(selectedTokenList);
				if (!commonMacros.isEmpty()) {
					addArea(commonMacros, I18N.getText("component.areaGroup.macro.commonMacros"));
				}
				// add(new ButtonGroup(selectedTokenList, commonMacros, this));
			}
			for (Token token : selectedTokenList) {
				if (!AppUtil.playerOwns(token)) {
					continue;
				}
				addArea(new TokenReference(token));
			}
			if (selectedTokenList.size() == 1 && AppUtil.playerOwns(selectedTokenList.get(0))) {
				// if only one token selected, show its image as tab icon
				TabletopTool.getFrame().getFrame(MTFrame.SELECTION).setFrameIcon(selectedTokenList.get(0).getIcon(16, 16));
			}
		}
		timer.stop("painting");

		if (AppState.isCollectProfilingData() || log.isDebugEnabled()) {
			String results = timer.toString();
			TabletopTool.getProfilingNoteFrame().addText(results);
			if (log.isDebugEnabled())
				log.debug(results);
		}
		TabletopTool.getEventDispatcher().addListener(this, TabletopTool.ZoneEvent.Activated);
	}

	private void populateCommonButtons(List<Token> tokenList) {
		Map<Integer, MacroButtonProperties> uniqueMacros = new HashMap<Integer, MacroButtonProperties>();
		Map<Integer, MacroButtonProperties> commonMacros = new HashMap<Integer, MacroButtonProperties>();
		for (Token nextToken : tokenList) {
			if (!AppUtil.playerOwns(nextToken)) {
				continue;
			}
			for (MacroButtonProperties nextMacro : nextToken.getMacroList(true)) {
				MacroButtonProperties copiedMacro = new MacroButtonProperties(nextMacro.getIndex(), nextMacro);
				int macroKey = copiedMacro.hashCodeForComparison();
				Boolean macroIsInUnique = uniqueMacros.containsKey(copiedMacro.hashCodeForComparison());
				Boolean macroIsInCommon = commonMacros.containsKey(copiedMacro.hashCodeForComparison());
				if (!macroIsInUnique && !macroIsInCommon) {
					uniqueMacros.put(macroKey, copiedMacro);
				} else if (macroIsInUnique && !macroIsInCommon) {
					uniqueMacros.remove(macroKey);
					commonMacros.put(macroKey, copiedMacro);
				} else if (macroIsInUnique && macroIsInCommon) {
					uniqueMacros.remove(macroKey);
				}
			}
		}
		for (MacroButtonProperties nextMacro : commonMacros.values()) {
			nextMacro.setAllowPlayerEdits(true);
			for (Token nextToken : tokenList) {
				if (!AppUtil.playerOwns(nextToken)) {
					continue;
				}
				for (MacroButtonProperties nextTokenMacro : nextToken.getMacroList(true)) {
					if (!nextTokenMacro.getAllowPlayerEdits()) {
						nextMacro.setAllowPlayerEdits(false);
					}
				}
			}
			if (!nextMacro.getCompareCommand()) {
				nextMacro.setCommand("");
			}
			if (!nextMacro.getCompareGroup()) {
				nextMacro.setGroup("");
			}
			if (!nextMacro.getCompareSortPrefix()) {
				nextMacro.setSortby("");
			}
		}
		this.commonMacros = new ArrayList<MacroButtonProperties>(commonMacros.values());
		int indexCount = 0;
		for (MacroButtonProperties nextMacro : this.commonMacros) {
			nextMacro.setIndex(indexCount);
			indexCount++;
		}
		Collections.sort(this.commonMacros);
	}

	@Override
	protected void clear() {
		// reset the tab icon
		TabletopTool.getFrame().getFrame(MTFrame.SELECTION).setFrameIcon(new ImageIcon(AppStyle.selectionPanelImage));
		super.clear();
	}

	@Override
	public void reset() {
		clear();
		init();
	}
}
