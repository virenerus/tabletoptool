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
package com.t3.client.swing;

import java.awt.Dimension;

import javax.swing.JLabel;

/**
 */
public class SpacerStatusBar extends JLabel {

    private Dimension minSize = new Dimension(0, 10);
    
    public SpacerStatusBar(int size) {
    	minSize = new Dimension(size, 10);
    }
    
    @Override
	public Dimension getMinimumSize() {
        return minSize;
    }
    
    @Override
	public Dimension getPreferredSize() {
        return getMinimumSize();
    }
}
