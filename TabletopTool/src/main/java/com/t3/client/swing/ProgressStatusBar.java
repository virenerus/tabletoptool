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

import javax.swing.JProgressBar;

/**
 */
public class ProgressStatusBar extends JProgressBar {

    private static final Dimension minSize = new Dimension(75, 10);
    
    int indeterminateCount = 0;
    int determinateCount = 0;
    int totalWork = 0;
    int currentWork = 0;
    
    public ProgressStatusBar() {
        setMinimum(0);
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
    
    public void startIndeterminate() {
        indeterminateCount ++;
        setIndeterminate(true);
    }
    
    public void endIndeterminate() {
        indeterminateCount --;
        if (indeterminateCount < 1) {
            setIndeterminate(false);
            
            indeterminateCount = 0;
        }
    }
    
    public void startDeterminate(int totalWork) {
        determinateCount ++;
        this.totalWork += totalWork;
        
        setMaximum(this.totalWork);
    }
    
    public void updateDeterminateProgress(int additionalWorkCompleted) {
        currentWork += additionalWorkCompleted;
        setValue(currentWork);
    }
    
    public void endDeterminate() {
        determinateCount --;
        if (determinateCount == 0) {
            totalWork = 0;
            currentWork = 0;
            
            setMaximum(0);
            setValue(0);
        }
    }
    
}
