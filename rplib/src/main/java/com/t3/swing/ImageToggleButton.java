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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.JToggleButton;

import com.t3.image.ImageUtil;

public class ImageToggleButton extends JToggleButton {

	private Image onImage;
	private Image offImage;

	private int padding;
	
	public ImageToggleButton(String onImage, String offImage) {
		this(onImage, offImage, 3);
	}
	public ImageToggleButton(String onImage, String offImage, int padding) {
		try {
			init(ImageUtil.getImage(onImage), ImageUtil.getImage(offImage), padding);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public ImageToggleButton(Image onImage, Image offImage) {
		this(onImage, offImage, 3);
	}
	public ImageToggleButton(Image onImage, Image offImage, int padding) {
		init(onImage, offImage, padding);
	}

	private void init(Image onImage, Image offImage, int padding) {
		
		this.onImage = onImage;
		this.offImage = offImage;
		this.padding = padding;
		
		int maxWidth = Math.max(onImage.getWidth(null), offImage.getWidth(null));
		int maxHeight = Math.max(onImage.getHeight(null), offImage.getHeight(null));
		
		setPreferredSize(new Dimension(maxWidth, maxHeight));
		setMinimumSize(new Dimension(maxWidth, maxHeight));
		setFocusPainted(false);
		setBorderPainted(false);
		
		setOpaque(false);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Dimension size = getSize();
		
//		g.setColor(getBackground());
//		g.fillRect(0, 0, size.width-1, size.height-1);
		
		Image image = isSelected() ? onImage : offImage;
		
		g.drawImage(image, (size.width - image.getWidth(null))/2+padding/2, (size.height - image.getHeight(null))/2+padding/2, this);
	}
}
