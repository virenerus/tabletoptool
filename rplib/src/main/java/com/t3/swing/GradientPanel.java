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
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Paint;

import javax.swing.JPanel;

public class GradientPanel extends JPanel {

	private Color c1;
	private Color c2;
	
	public GradientPanel(Color c1, Color c2) {
		this(c1, c2, new FlowLayout());
	}
	
	public GradientPanel(Color c1, Color c2, LayoutManager layout) {
		super(layout);
		
		this.c1 = c1;
		this.c2 = c2;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		Paint p = new GradientPaint(0, 0, c1, getSize().width, 0, c2);
		Paint oldPaint = g2d.getPaint();
		g2d.setPaint(p);
		g2d.fillRect(0, 0, getSize().width, getSize().height);
		g2d.setPaint(oldPaint);
	}
}
