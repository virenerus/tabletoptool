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
package com.t3.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

/**
 */
public class PopupListener extends MouseAdapter {
    
    private JPopupMenu menu;
    
    public PopupListener (JPopupMenu menu) {
        this.menu = menu;
    }
    
    @Override
	public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    @Override
	public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            menu.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }
}
