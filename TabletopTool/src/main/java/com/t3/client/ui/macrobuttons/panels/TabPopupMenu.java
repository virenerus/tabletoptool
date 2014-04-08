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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;

public class TabPopupMenu extends JPopupMenu{
	
	//private final JComponent parent;
	private int index;
	
	//TODO: replace index with Tab.TABNAME.index
	public TabPopupMenu(JComponent parent, int index) {
		//this.parent = parent;
		this.index = index;
		add(new AddNewButtonAction());
	}

	private class AddNewButtonAction extends AbstractAction {
		public AddNewButtonAction() {
			putValue(Action.NAME, "New Tab");
		}

		@Override
		public void actionPerformed(ActionEvent event) {
		}
	}
}
