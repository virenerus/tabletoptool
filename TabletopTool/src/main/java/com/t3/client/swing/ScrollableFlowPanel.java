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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 * This code is taken directly from:
 *  
 * 		http://forum.java.sun.com/thread.jspa?forumID=57&threadID=701797&start=2
 * 
 * In direct response to this bug:
 * 
 * 		http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5082531
 * 
 * @author trevor
 */
public class ScrollableFlowPanel extends JPanel implements Scrollable {
	
	public ScrollableFlowPanel() {
		// Default
	}
	
	public ScrollableFlowPanel(int alignment) {
		setLayout(new FlowLayout(alignment));
	}
	
	@Override
	public void setBounds( int x, int y, int width, int height ) {
		super.setBounds( x, y, getParent().getWidth(), height );
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension( getWidth(), getPreferredHeight() );
	}
	
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return super.getPreferredSize();
	}

	@Override
	public int getScrollableUnitIncrement( Rectangle visibleRect, int orientation, int direction ) {
		int hundredth = ( orientation ==  SwingConstants.VERTICAL
				? getParent().getHeight() : getParent().getWidth() ) / 100;
		return ( hundredth == 0 ? 1 : hundredth ); 
	}

	@Override
	public int getScrollableBlockIncrement( Rectangle visibleRect, int orientation, int direction ) {
		return orientation == SwingConstants.VERTICAL ? getParent().getHeight() : getParent().getWidth();
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return true;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	private int getPreferredHeight() {
		int rv = 0;
		for ( int k = 0, count = getComponentCount(); k < count; k++ ) {
			Component comp = getComponent( k );
			Rectangle r = comp.getBounds();
			int height = r.y + r.height;
			if ( height > rv )
				rv = height;
		}
		rv += ( (FlowLayout) getLayout() ).getVgap();
		return rv;
	}
}
