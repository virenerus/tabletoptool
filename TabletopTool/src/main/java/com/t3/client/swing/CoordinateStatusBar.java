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

import com.t3.language.I18N;

/**
 */
public class CoordinateStatusBar extends JLabel {

    private static final Dimension minSize = new Dimension(75, 10);
    
    public CoordinateStatusBar() {
    	setToolTipText(I18N.getString("CoordinateStatusBar.mapCoordinates")); //$NON-NLS-1$
    }
    
    /* (non-Javadoc)
     * @see javax.swing.JComponent#getMinimumSize()
     */
    @Override
	public Dimension getMinimumSize() {
        return minSize;
    }
    
    /* (non-Javadoc)
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
	public Dimension getPreferredSize() {
        return getMinimumSize();
    }
    
    public void clear() {
    	setText(""); //$NON-NLS-1$
    }
    
    public void update(int x, int y) {
    	setText("  " + x + ", " + y); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
