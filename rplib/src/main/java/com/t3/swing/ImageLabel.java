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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.t3.image.ImageUtil;

public class ImageLabel {

	private BufferedImage labelBoxLeftImage;
	private BufferedImage labelBoxRightImage;
	private BufferedImage labelBoxMiddleImage;
	private int leftMargin = 4;
	private int rightMargin = 4;

	public ImageLabel(String labelImage, int leftMargin, int rightMargin) {
		this.leftMargin = leftMargin;
		this.rightMargin = rightMargin;

		try {
			parseImage(ImageUtil.getCompatibleImage(labelImage));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void renderLabel(Graphics2D g, int x, int y, int width, int height) {
		g.drawImage(labelBoxLeftImage, x, y, labelBoxLeftImage.getWidth(), height, null);
		g.drawImage(labelBoxRightImage, x+width-rightMargin, y, rightMargin, height, null);
		g.drawImage(labelBoxMiddleImage, x+leftMargin, y, width-rightMargin-leftMargin, height, null);
	}
	
	private void parseImage(BufferedImage image) {
		
		labelBoxLeftImage = image.getSubimage(0, 0, leftMargin, image.getHeight());
		labelBoxRightImage = image.getSubimage(image.getWidth()-rightMargin, 0, rightMargin, image.getHeight());
		labelBoxMiddleImage = image.getSubimage(leftMargin, 0, image.getWidth() - leftMargin - rightMargin, image.getHeight());
	}
}
