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

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

/**
 */
public class TitleMenuItem extends JMenuItem {

	private String title;
	
	public TitleMenuItem(String title) {
		super(title);
		setEnabled(false);
		
		this.title = title;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {

		g.setColor(Color.darkGray);
		g.fillRect(0, 0, getSize().width, getSize().height);
		
		g.setColor(Color.white);
		FontMetrics fm = g.getFontMetrics();
		
		int x = (getSize().width - SwingUtilities.computeStringWidth(fm, title)) / 2;
		int y = (getSize().height - fm.getHeight()) / 2 + fm.getAscent();
		
		g.drawString(title, x, y);
	}
}
