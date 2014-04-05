/*
 *  This software copyright by various authors including the RPTools.net
 *  development team, and licensed under the LGPL Version 3 or, at your
 *  option, any later version.
 *
 *  Portions of this software were originally covered under the Apache
 *  Software License, Version 1.1 or Version 2.0.
 *
 *  See the file LICENSE elsewhere in this distribution for license details.
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
