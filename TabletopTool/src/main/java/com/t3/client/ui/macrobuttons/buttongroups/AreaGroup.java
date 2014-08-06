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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.SwingUtilities;

import com.t3.client.TabletopTool;
import com.t3.client.ui.macrobuttons.panels.AbstractMacroPanel;
import com.t3.guid.GUID;
import com.t3.model.MacroButtonProperties;
import com.t3.model.Token;
import com.t3.util.guidreference.NullHelper;
import com.t3.util.guidreference.TokenReference;

public class AreaGroup extends AbstractButtonGroup {
	// constructor for creating an area group in the campaign/global panels
	public AreaGroup(List<MacroButtonProperties> propertiesList, String panelLabel, AbstractMacroPanel panel) {
		setPropertiesList(propertiesList);
		setPanel(panel);
		setPanelClass(panel.getPanelClass());
		setGroupClass("AreaGroup");
		setGroupLabel(panelLabel);
		setTokenReference(NullHelper.referenceToken(panel.getToken()));
		addMouseListener(this);
		drawArea();
	}

	// constructor for creating an area group for the impersonate/selection panels
	public AreaGroup(TokenReference token, AbstractMacroPanel panel) {
		setTokenReference(token);
		setPropertiesList(getToken().getMacroList(true));
		setPanel(panel);
		setPanelClass(panel.getPanelClass());
		setGroupClass("AreaGroup");
		setGroupLabel(getTokenName(getToken()));
		addMouseListener(this);
		drawArea();
	}

	// constructor for creating an area spacer, used to take up space where an area label would be
	public AreaGroup(int height, AbstractMacroPanel panel) {
		setPanel(panel);
		setPanelClass(panel.getPanelClass());
		setOpaque(false);
//		addMouseListener(this);  don't use; the label has its own 
	}

	public void drawArea() {
		if (getToken() == null && getGroupLabel().equals("")) {
			// don't put an extra border around the campaign/global panels, or if there is no label
		} else {
			ThumbnailedBorder border = createBorder(getGroupLabel());
			setBorder(border);
			add(new AreaGroup(12, getPanel())); // spacer
		}
		String lastGroup = "akjaA#$Qq4jakjj#%455jkkajDAJFAJ"; // random string
		String currentGroup = "";

		List<MacroButtonProperties> propertiesList = getPropertiesList();
		List<MacroButtonProperties> groupList = new ArrayList<MacroButtonProperties>();
		Collections.sort(propertiesList);

		if (propertiesList.isEmpty()) {
			add(new ButtonGroup(propertiesList, "", getPanel(), getTokenReference(), this));
		} else {
			// build separate button groups for each user-defined group
			for (MacroButtonProperties prop : propertiesList) {
				currentGroup = prop.getGroup();
				if (!groupList.isEmpty() && !lastGroup.equalsIgnoreCase(currentGroup)) { // better to use currentGroup.equals(lastGroup) since lastGroup could be initialized to null
					add(new ButtonGroup(groupList, lastGroup, getPanel(), getTokenReference(), this));
					groupList.clear();
				}
				lastGroup = currentGroup;
				groupList.add(prop);
			}
			if (!groupList.isEmpty()) {
				add(new ButtonGroup(groupList, lastGroup, getPanel(), getTokenReference(), this));
				groupList.clear();
			}
		}
		setLayout(new FlowLayout(FlowLayout.LEFT));
		revalidate();
		repaint();
	}

	@Override
	public void drop(DropTargetDropEvent event) {
		//System.out.println("BG: drop!");
		event.rejectDrop(); // don't accept drops in an area group, it should be in the button group
		event.dropComplete(true);
	}

	@Override
	public Insets getInsets() {
		return new Insets(0, 1, 3, 0);
	}

	@Override
	public Dimension getPreferredSize() {
		FlowLayout layout = (FlowLayout) getLayout();
		Insets insets = getInsets();
		// This isn't exact, but hopefully it's close enough
		int availableWidth = getPanel().getAvailableWidth() - insets.left - insets.right;
		int height = insets.top + insets.bottom + layout.getVgap();
		int rowHeight = 0;
		int rowWidth = insets.left + layout.getHgap() + insets.right;
		for (Component c : getComponents()) {
			Dimension cSize = c.getPreferredSize();
			if (rowWidth + cSize.width + layout.getHgap() - 5 > availableWidth && rowWidth > 0) {
				height += rowHeight + layout.getVgap();
				rowHeight = 0;
				rowWidth = insets.left + layout.getHgap() + insets.right;
			}
			rowWidth += cSize.width + layout.getHgap();
			rowHeight = Math.max(cSize.height, rowHeight);
		}
		height += rowHeight;
		Dimension prefSize = new Dimension(availableWidth, height);
		return prefSize;
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		Token token = getToken();
		if (SwingUtilities.isRightMouseButton(event)) {
			if (getPanelClass() == "CampaignPanel" && !TabletopTool.getPlayer().isGM()) {
				return;
			}
			// open button group menu
			new ButtonGroupPopupMenu(getPanelClass(), this, getMacroGroup(), token).show(this, event.getX(), event.getY());
		}
	}

	public List<ButtonGroup> getButtonGroups() {
		List<ButtonGroup> myButtonGroups = new ArrayList<ButtonGroup>();
		for (int buttonGroupCount = 0; buttonGroupCount < this.getComponentCount(); buttonGroupCount++) {
			myButtonGroups.add((ButtonGroup) this.getComponent(buttonGroupCount));
		}
		return myButtonGroups;
	}
}
