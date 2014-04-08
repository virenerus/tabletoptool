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

import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.t3.client.AppActions.ClientAction;

/** 
 * This little baby will keep the menu items selected state intact.  Not the most elegant, but works
 */
public class RPCheckBoxMenuItem extends JCheckBoxMenuItem implements MenuListener {

	public RPCheckBoxMenuItem(Action action, JMenu parentMenu) {
		super(action);
		
		parentMenu.addMenuListener(this);
	}
	
	@Override
	public void menuSelected(MenuEvent e) {
		Action action = getAction();
		if (action instanceof ClientAction) {
			setSelected(((ClientAction)action).isSelected());
		}		
	}

	@Override
	public void menuCanceled(MenuEvent e) {
	}

	@Override
	public void menuDeselected(MenuEvent e) {
	}
	
}
