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

import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;

import com.t3.client.AppActions;
import com.t3.client.ObserverListModel;
import com.t3.client.TabletopTool;
import com.t3.model.Player;
import com.t3.swing.PopupListener;

/**
 * Implements the contents of the Window -> Connections status panel.
 * Previously this class only displayed a list of connected clients, but it is
 * being extended to include other information as well:
 * <ul>
 * <li>current map name,
 * <li>viewing range of current map (as a rectangle of grid coordinates),
 * <li>whether a macro is running (?),
 * <li>IP address (for ping/traceroute tests?)
 * <li>others?
 * </ul>
 */
public class ClientConnectionPanel extends JList<Player> {
	public ClientConnectionPanel () {
		setModel(new ObserverListModel<Player>(TabletopTool.getPlayerList()));
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		setCellRenderer(new DefaultListCellRenderer());

		addMouseListener(createPopupListener());
	}

	private MouseListener createPopupListener() {
		PopupListener listener = new PopupListener(createPopupMenu());
		return listener;
	}

	private JPopupMenu createPopupMenu() {
		JPopupMenu menu = new JPopupMenu ();
		menu.add(new JMenuItem(AppActions.BOOT_CONNECTED_PLAYER));
		return menu;
	}
}
