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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class TabPopupListener extends MouseAdapter {

	private JComponent component;
	private int index;
	
	public TabPopupListener(JComponent component, int index) {
		this.component = component;
		this.index = index;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			new TabPopupMenu(component, index).show(component, e.getX(), e.getY());
		} else {
			//System.out.println("Tab index: " + ((JTabbedPane) component).indexAtLocation(e.getX(), e.getY()));
		}
	}
}
