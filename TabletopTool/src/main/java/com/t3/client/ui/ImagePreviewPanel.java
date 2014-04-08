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
package com.t3.client.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

import com.t3.swing.SwingUtil;

@SuppressWarnings("serial")
public class ImagePreviewPanel extends JComponent {

	private Image img;

	public ImagePreviewPanel() {
		setPreferredSize(new Dimension(150, 100));
		setMinimumSize(new Dimension(150, 100));
	}

	public void setImage(Image image) {

		this.img = image;
		repaint();
	}

	public Image getImage() {
		return img;
	}

	@Override
	protected void paintComponent(Graphics g) {

		// Image
		Dimension size = getSize();
		if (img != null) {
			Dimension imgSize = new Dimension(img.getWidth(null), img
					.getHeight(null));
			SwingUtil.constrainTo(imgSize, size.width, size.height);

			// Border
			int x = (size.width - imgSize.width) / 2;
			int y = (size.height - imgSize.height) / 2;

			g.drawImage(img, x, y, imgSize.width, imgSize.height, null);
			g.setColor(Color.black);
			g.drawRect(x, y, imgSize.width - 1, imgSize.height - 1);
		}

	}
}
