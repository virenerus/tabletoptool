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
package com.t3.client.ui;

import java.util.Set;

import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import com.t3.client.AppUtil;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.guid.GUID;
import com.t3.model.Token;
import com.t3.model.Zone;

public class StampPopupMenu extends AbstractTokenPopupMenu {
	private static final long serialVersionUID = -1355308966041002520L;

	public StampPopupMenu(Set<GUID> selectedTokenSet, int x, int y, ZoneRenderer renderer, Token tokenUnderMouse) {
		super(selectedTokenSet, x, y, renderer, tokenUnderMouse);

		add(new SetFacingAction());
		add(new ClearFacingAction());
		add(new StartMoveAction());
		add(createFlipMenu());
		add(createSizeMenu());
		add(createArrangeMenu());
		add(createChangeToMenu(Zone.Layer.TOKEN, Zone.Layer.GM, Zone.Layer.OBJECT, Zone.Layer.BACKGROUND));
		if (getTokenUnderMouse().getCharsheetImage() != null && AppUtil.playerOwns(getTokenUnderMouse())) {
			add(new ShowHandoutAction());
		}
		add(new JSeparator());
		addOwnedItem(createLightSourceMenu());
		add(new JSeparator());

		addToggledItem(new SnapToGridAction(tokenUnderMouse.isSnapToGrid(), renderer), tokenUnderMouse.isSnapToGrid());
		addToggledGMItem(new VisibilityAction(), tokenUnderMouse.isVisible());

		add(new JSeparator());

		add(new JMenuItem(new CutAction()));
		add(new JMenuItem(new CopyAction()));
		add(new JMenuItem(new DeleteAction()));

		add(new JSeparator());

		add(new ShowPropertiesDialogAction());
		add(new SaveAction());
	}
}
