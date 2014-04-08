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
import java.awt.Graphics;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;

public class HTMLPanelRenderer extends JTextPane {
	private static final long serialVersionUID = -7535450508528232780L;

	private final CellRendererPane rendererPane = new CellRendererPane();
	private final StyleSheet styleSheet;
	private Dimension size;

	public HTMLPanelRenderer() {
		setContentType("text/html");
		setEditable(false);
		setDoubleBuffered(false);

		styleSheet = ((HTMLDocument) getDocument()).getStyleSheet();
		styleSheet.addRule("body { font-family: sans-serif; font-size: 11pt}");
		rendererPane.add(this);
		Document document = getDocument();

		// Use a little bit of black magic to get our images to display correctly
		// TODO: Need a way to flush this cache
		HTMLPanelImageCache imageCache = new HTMLPanelImageCache();
		document.putProperty("imageCache", imageCache);
	}

	public void addStyleSheetRule(String rule) {
		styleSheet.addRule(rule);
	}

	public void attach(JComponent c) {
		c.add(rendererPane);
	}

	public void detach(JComponent c) {
		c.remove(rendererPane);
	}

	public Dimension setText(String t, int maxWidth, int maxHeight) {
		setText(t);
		setSize(maxWidth, maxHeight);
		size = getPreferredSize();
		size.width = Math.min(size.width, maxWidth);
		return size;
	}

	public void render(Graphics g, int x, int y) {
		rendererPane.paintComponent(g, this, null, x, y, size.width, size.height);
	}
}
