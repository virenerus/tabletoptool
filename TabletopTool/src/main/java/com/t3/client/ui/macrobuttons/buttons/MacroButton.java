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
package com.t3.client.ui.macrobuttons.buttons;

import java.awt.Cursor;
import java.awt.Insets;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;

import com.t3.client.T3Util;
import com.t3.client.TabletopTool;
import com.t3.client.ui.MacroButtonHotKeyManager;
import com.t3.client.ui.macrobuttons.buttongroups.AbstractButtonGroup;
import com.t3.client.ui.macrobuttons.buttongroups.ButtonGroup;
import com.t3.client.ui.macrobuttons.panels.AbstractMacroPanel;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.model.GUID;
import com.t3.model.MacroButtonProperties;
import com.t3.model.Token;
import com.t3.model.Zone;
import com.t3.swing.SwingUtil;

/**
 * Base class of {@link CampaignMacroButton} and {@link GlobalMacroButton}. {@link TokenMacroButton} doesn't extend this
 * class because it is very simple. <code>MacroButton</code>s that extend this class use {@link MacroButtonProperties}.
 * <p>
 * These buttons are used in Macro Button Panel in the UI.
 */
public class MacroButton extends JButton implements MouseListener {
	private final MacroButtonProperties properties;
	private final MacroButtonHotKeyManager hotKeyManager;
	private final ButtonGroup buttonGroup;
	private final AbstractMacroPanel panel;
	private final String panelClass;
	private final GUID tokenId;
	private static final Insets buttonInsets = new Insets(2, 2, 2, 2);
	private DragSource dragSource;
	private DragGestureListener dgListener;
	private DragSourceListener dsListener;

	private static final Pattern MACRO_LABEL = Pattern.compile("^(/\\w+\\s+)(.*)$");

	public MacroButton(MacroButtonProperties properties, ButtonGroup buttonGroup) {
		this(properties, buttonGroup, null);
	}

	public MacroButton(MacroButtonProperties properties, ButtonGroup buttonGroup, Token token) {
		this.properties = properties;
		this.buttonGroup = buttonGroup;
		this.panel = buttonGroup.getPanel();
		this.panelClass = buttonGroup.getPanel().getPanelClass();
		if (token == null) {
			this.tokenId = null;
		} else {
			this.tokenId = token.getId();
		}
		this.properties.setToken(token);
		this.properties.setSaveLocation(this.panelClass);
		this.properties.setButton(this);
		// we have to call setColor() and setText() here since properties only hold "dumb" data.
		setColor(properties.getColorKey());
		setText(getButtonText());
		hotKeyManager = new MacroButtonHotKeyManager(this);
		hotKeyManager.assignKeyStroke(properties.getHotKey());
		setMargin(buttonInsets);
		makeDraggable(DragSource.DefaultCopyDrop);
		addMouseListener(this);
		ToolTipManager.sharedInstance().registerComponent(this);
	}

	public MacroButtonProperties getProperties() {
		return properties;
	}

	public MacroButtonHotKeyManager getHotKeyManager() {
		return hotKeyManager;
	}

	public GUID getTokenId() {
		return tokenId;
	}

	public Token getToken() {
		ZoneRenderer zr = TabletopTool.getFrame().getCurrentZoneRenderer();
		Zone z = (zr == null ? null : zr.getZone());
		return z == null ? null : z.getToken(tokenId);
	}

	public AbstractButtonGroup getButtonGroup() {
		return buttonGroup;
	}

	public String getPanelClass() {
		return panelClass;
	}

	public void setColor(String colorKey) {
		//If the key doesn't correspond to one of our colors, then use the default
		// FJE Why??
		if ("default".equals(colorKey)) {
			setBackground(null);
		} else {
			setBackground(T3Util.getColor(colorKey));
		}
	}

	/*
	 * Get the text for the macro button by filtering out label macro (if any), and add hotkey hint (if any)
	 */
	public String getButtonText() {
		String buttonLabel;
		String label = properties.getLabel();
		Matcher m = MACRO_LABEL.matcher(label);
		if (m.matches())
			buttonLabel = m.group(2);
		else
			buttonLabel = label;

		String div = "<div style='font-size: " + properties.getFontSize() + "; text-align: center'>";
		String formatButtonLabel = "<p style='color: " + properties.getFontColorAsHtml() + "; " + getMinWidth() + getMaxWidth() + "'>" + buttonLabel;

		// if there is no hotkey (HOTKEY[0]) then no need to add hint
		String hotKey = properties.getHotKey();
		String result = null;
		if (hotKey.equals(MacroButtonHotKeyManager.HOTKEYS[0]))
			result = "<html>" + div + formatButtonLabel;
		else
			result = "<html>" + div + formatButtonLabel + "<font style='font-size:0.8em'> (" + hotKey + ")";
		return result;
	}

	public String getMinWidth() {
		// the min-width style doesn't appear to work in the current java, so I'm
		// using width instead.
		String newMinWidth = properties.getMinWidth();
		if (newMinWidth != null && !newMinWidth.equals("")) {
			return " width:" + newMinWidth + ";";
			// return " min-width:"+newMinWidth+";";
		}
		return "";
	}

	public String getMaxWidth() {
		// doesn't appear to work in current java, leaving it in just in case
		// it is supported in the future
		String newMaxWidth = properties.getMaxWidth();
		if (newMaxWidth != null && !newMaxWidth.equals("")) {
			return " max-width:" + newMaxWidth + ";";
		}
		return "";
	}

	// Override these mouse events in subclasses to specify component specific behavior.
	@Override
	public void mouseClicked(MouseEvent event) {
	}

	@Override
	public void mousePressed(MouseEvent event) {
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (SwingUtilities.isLeftMouseButton(event)) {
			// If any of the following 3 conditions are correct we want to run it against all selected tokens,
			// Shift is held down while clicking the button, the button has apply to selected tokens set, or its a common macro button
			if (SwingUtil.isShiftDown(event) || properties.getCommonMacro()) {
				if (TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList().size() > 0) {
					properties.executeMacro(TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList());
				}
			} else {
				properties.executeMacro();
			}
		} else if (SwingUtilities.isRightMouseButton(event)) {
			if (getPanelClass().equals("GlobalPanel")) {
				new MacroButtonPopupMenu(this, panelClass, false).show(this, event.getX(), event.getY());
			} else if (getPanelClass().equals("CampaignPanel")) {
				if (TabletopTool.getPlayer().isGM()) {
					new MacroButtonPopupMenu(this, panelClass, false).show(this, event.getX(), event.getY());
				} else {
					if (properties.getAllowPlayerEdits()) {
						new MacroButtonPopupMenu(this, panelClass, false).show(this, event.getX(), event.getY());
					}
				}
			} else if (getPanelClass().equals("SelectionPanel") || getPanelClass().equals("ImpersonatePanel")) {
				if (TabletopTool.getFrame().getSelectionPanel().getCommonMacros().contains(properties)) {
					new MacroButtonPopupMenu(this, panelClass, true).show(this, event.getX(), event.getY());
				} else {
					new MacroButtonPopupMenu(this, panelClass, false).show(this, event.getX(), event.getY());
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		if (TabletopTool.getFrame().getCurrentZoneRenderer() == null) {
			return;
		}

		List<Token> selectedTokens = TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokensList();
		if (SwingUtil.isShiftDown(event)) {
			TabletopTool.getFrame().getCurrentZoneRenderer().setHighlightCommonMacros(selectedTokens);
		} else {
			if (getPanelClass() == "SelectionPanel") {
				List<Token> affectedTokens = new ArrayList<Token>();
				if (getProperties().getCommonMacro()) {
					for (Token nextSelected : selectedTokens) {
						Boolean isCommonToToken = false;
						for (MacroButtonProperties nextMacro : nextSelected.getMacroList(true)) {
							if (nextMacro.hashCodeForComparison() == getProperties().hashCodeForComparison()) {
								isCommonToToken = true;
							}
						}
						if (isCommonToToken) {
							affectedTokens.add(nextSelected);
						}
					}
				} else if (getProperties().getToken() != null) {
					affectedTokens.add(getProperties().getToken());
				}
				TabletopTool.getFrame().getCurrentZoneRenderer().setHighlightCommonMacros(affectedTokens);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent event) {
		List<Token> affectedTokens = new ArrayList<Token>();
		if (TabletopTool.getFrame().getCurrentZoneRenderer() != null) {
			TabletopTool.getFrame().getCurrentZoneRenderer().setHighlightCommonMacros(affectedTokens);
		}
	}

	private void makeDraggable(Cursor cursor) {
		dragSource = DragSource.getDefaultDragSource();
		dgListener = new DGListener(cursor);
		dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, dgListener);
		dsListener = new DSListener();
	}

	private class DGListener implements DragGestureListener {

		final Cursor cursor;

		public DGListener(Cursor cursor) {
			this.cursor = cursor;
		}

		@Override
		public void dragGestureRecognized(DragGestureEvent dge) {
			Transferable t = new TransferableMacroButton(MacroButton.this);
			dge.startDrag(cursor, t, dsListener);
		}
	}

	private class DSListener implements DragSourceListener {

		@Override
		public void dragEnter(DragSourceDragEvent event) {
			//System.out.println("TMB: drag enter");
			//DragSourceContext context = event.getDragSourceContext();
			//context.getComponent()
		}

		@Override
		public void dragOver(DragSourceDragEvent event) {
			//System.out.println("TMB: drag over");
		}

		@Override
		public void dropActionChanged(DragSourceDragEvent event) {
			//System.out.println("TMB: drop action changed");
		}

		@Override
		public void dragExit(DragSourceEvent event) {
			//System.out.println("TMB: drag exit");
		}

		@Override
		public void dragDropEnd(DragSourceDropEvent event) {
			//System.out.println("TMB: drag drop end");
			// js commented out for testing - TabletopTool.getFrame().updateSelectionPanel();
			List<Token> affectedTokens = new ArrayList<Token>();
			TabletopTool.getFrame().getCurrentZoneRenderer().setHighlightCommonMacros(affectedTokens);
		}
	}

	public void clearHotkey() {
		getHotKeyManager().assignKeyStroke(MacroButtonHotKeyManager.HOTKEYS[0]);
	}

	@Override
	public String getToolTipText(MouseEvent e) {
		String tt = properties.getEvaluatedToolTip();
		return tt.length() == 0 ? null : tt;
	}

}
