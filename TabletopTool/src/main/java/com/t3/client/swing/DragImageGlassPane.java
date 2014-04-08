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
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.t3.swing.SwingUtil;

public class DragImageGlassPane extends JPanel {
	private static final int DEFAULT_MAX_SIZE = 75;

	private BufferedImage image;
	private Point location;

	public DragImageGlassPane() {
		setOpaque(false);
	}

	public void setImage(BufferedImage image) {
		this.image = image;

		if (image == null) {
			location = null;
		}
		repaint();
	}

	public void setImagePosition(Point p) {
		if (p.equals(location)) {
			return;
		}
		if (p != null && image != null) {
			Dimension size = getImageSize();

			if (location == null) {
				location = p;
			}
			Rectangle newRect = new Rectangle(p.x, p.y, size.width, size.height);
			Rectangle oldRect = new Rectangle(location.x, location.y, size.width, size.height);

			newRect.add(oldRect);
			newRect.translate(-size.width / 2, -size.height / 2); // account for drawing centered on point

			repaint(newRect);

			location = new Point(p);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image == null || location == null) {
			return;
		}
		Dimension size = getImageSize();
		g.drawImage(image, location.x - (size.width / 2), location.y - (size.height / 2), size.width, size.height, this);
	}

	private Dimension getImageSize() {
		Dimension size = new Dimension(image.getWidth(), image.getHeight());
		if (size.width > DEFAULT_MAX_SIZE || size.height > DEFAULT_MAX_SIZE) {
			SwingUtil.constrainTo(size, DEFAULT_MAX_SIZE);
		}
		return size;
	}
}
