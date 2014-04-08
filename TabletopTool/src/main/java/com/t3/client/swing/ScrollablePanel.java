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
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

public class ScrollablePanel extends JPanel implements Scrollable {

	private int unitIncrement;
	private int blockIncrement;

	public ScrollablePanel(JComponent component, int unitIncrement, int blockIncrement) {
		setLayout(new GridLayout());
		add(component);
		
		this.unitIncrement = unitIncrement;
		this.blockIncrement = blockIncrement;
	}

	public ScrollablePanel(JComponent component, int unitIncrement) {
		this(component, unitIncrement, unitIncrement * 3);
	}

	public ScrollablePanel(JComponent component) {
		this(component, 20);
	}
	
	public static JScrollPane wrap(JComponent component) {
		
		JScrollPane pane = new JScrollPane(new ScrollablePanel(component), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		return pane;
	}
	
	////
	// SCROLLABLE
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		return blockIncrement;
	}
	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}
	@Override
	public boolean getScrollableTracksViewportWidth() {
		return true;
	}
	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		return unitIncrement;
	}
}
