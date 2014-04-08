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

import java.awt.Component;

import javax.swing.JPanel;

/**
 */
public class PositionalPanel extends JPanel {

	public PositionalPanel() {
		setLayout(new PositionalLayout());
	}
	
	@Override
	public void addImpl(Component comp, Object constraints, int index) {
		
		if (!(constraints instanceof PositionalLayout.Position)) {
			throw new IllegalArgumentException("Use add(Component, PositionalLayout.Position)");
		}
		
		super.addImpl(comp, constraints, index);

		if (((PositionalLayout.Position) constraints) == PositionalLayout.Position.CENTER) {
		
			setComponentZOrder(comp, getComponents().length - 1);
		} else {
			setComponentZOrder(comp, 0);
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#isOptimizedDrawingEnabled()
	 */
	@Override
	public boolean isOptimizedDrawingEnabled() {
		return false;
	}
}
