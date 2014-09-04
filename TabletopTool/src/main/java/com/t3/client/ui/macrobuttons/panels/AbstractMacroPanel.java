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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;

import com.t3.AppEvent;
import com.t3.AppEventListener;
import com.t3.client.TabletopTool;
import com.t3.client.ui.macrobuttons.buttongroups.AreaGroup;
import com.t3.client.ui.macrobuttons.buttongroups.ButtonGroup;
import com.t3.client.ui.macrobuttons.buttongroups.ButtonGroupPopupMenu;
import com.t3.client.ui.macrobuttons.buttons.MacroButton;
import com.t3.model.MacroButtonProperties;
import com.t3.model.ModelChangeEvent;
import com.t3.model.ModelChangeListener;
import com.t3.model.Token;
import com.t3.model.Zone;
import com.t3.model.Zone.Event;
import com.t3.util.guidreference.NullHelper;
import com.t3.util.guidreference.TokenReference;

@SuppressWarnings("serial")
public abstract class AbstractMacroPanel extends JPanel implements Scrollable, MouseListener, ModelChangeListener, AppEventListener {
	private String panelClass = "";
	private TokenReference token = null;

	public void addArea(List<MacroButtonProperties> propertiesList, String label) {
		add(new AreaGroup(propertiesList, label, this));
		setLayout(new FlowLayout(FlowLayout.LEFT));
		revalidate();
		repaint();
	}

	public void addArea(TokenReference token) {
		add(new AreaGroup(token, this));
		setLayout(new FlowLayout(FlowLayout.LEFT));
		revalidate();
		repaint();
	}

	@Override
	public Insets getInsets() {
		return new Insets(0, 0, 0, 0);
	}

	public int getAvailableWidth() {
		Dimension size = getParent().getSize();
		Insets insets = getInsets();
		return size.width - insets.left - insets.right;
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension size = getParent().getSize();
		FlowLayout layout = (FlowLayout) getLayout();
		Insets insets = getInsets();
		// This isn't exact, but hopefully it's close enough
		int panelWidth = size.width - insets.left - insets.right;
		int panelHeight = size.height - insets.top - insets.bottom;
		int height = insets.top + insets.bottom + layout.getVgap();
		for (Component c : getComponents()) {
			Dimension cSize = c.getPreferredSize();
			height += cSize.height + layout.getVgap();
		}
		height = Math.max(height, panelHeight); // fill the panel if it wouldn't already
		Dimension prefSize = new Dimension(panelWidth, height);
		return prefSize;
	}

	public String getPanelClass() {
		return panelClass;
	}

	public void setPanelClass(String panelClass) {
		this.panelClass = panelClass;
	}

	public Token getToken() {
		return NullHelper.value(token);
	}

	public TokenReference getTokenReference() {
		return this.token;
	}

	public void setToken(Token token) {
		this.token=NullHelper.referenceToken(token);
	}

	protected void clear() {
		removeAll();
		revalidate();
		repaint();
	}

	public abstract void reset();

	// SCROLLABLE
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 75;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return getPreferredSize().height < getParent().getSize().height;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return true;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 25;
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
		if (SwingUtilities.isRightMouseButton(event)) {
			if ("CampaignPanel".equals(getPanelClass()) && !TabletopTool.getPlayer().isGM()) {
				return;
			}
			// open button group menu
			new ButtonGroupPopupMenu(getPanelClass(), null, "", getToken()).show(this, event.getX(), event.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent event) {
	}

	@Override
	public void mouseExited(MouseEvent event) {
	}

	// currently only used for Impersonate/Selection panels to refresh when the token is removed or a macro changes
	@Override
	public void modelChanged(ModelChangeEvent event) {
		if (event.eventType == Token.ChangeEvent.MACRO_CHANGED || event.eventType == Event.TOKEN_REMOVED) {
			reset();
		}
	}

	@Override
	public void handleAppEvent(AppEvent event) {
		Zone oldZone = (Zone) event.getOldValue();
		Zone newZone = (Zone) event.getNewValue();

		if (oldZone != null) {
			oldZone.removeModelChangeListener(this);
		}
		newZone.addModelChangeListener(this);
		reset();
	}

	public static void clearHotkeys(AbstractMacroPanel panel) {
		for (int areaGroupCount = 0; areaGroupCount < panel.getComponentCount(); areaGroupCount++) {
			AreaGroup area = (AreaGroup) panel.getComponent(areaGroupCount);
			for (ButtonGroup group : area.getButtonGroups()) {
				for (MacroButton nextButton : group.getButtons()) {
					nextButton.clearHotkey();
				}
			}
		}
	}
}
