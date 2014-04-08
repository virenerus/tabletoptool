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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.JPanel;

public class PaintedPanel extends JPanel {

	private Paint paint;

	public PaintedPanel() {
		this(null);
	}
	
	public PaintedPanel(Paint paint) {
		this.paint = paint;
		setMinimumSize(new Dimension(10, 10));
		setPreferredSize(getMinimumSize());
	}
	
	public Paint getPaint() {
		return paint;
	}
	
	public void setPaint(Paint paint) {
		this.paint = paint;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Dimension size = getSize();
		g.setColor(getBackground());
		g.fillRect(0, 0, size.width, size.height);
		
		if (paint != null) {
			((Graphics2D) g).setPaint(paint);
			g.fillRect(0, 0, size.width, size.height);
		} else {
			g.setColor(Color.white);
			g.fillRect(0, 0, size.width, size.height);
			g.setColor(Color.red);
			g.drawLine(size.width-1, 0, 0, size.height-1);
		}
	}
}
