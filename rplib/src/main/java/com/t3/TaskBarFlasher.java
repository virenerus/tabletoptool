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
package com.t3;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class TaskBarFlasher {

	private static final int FLASH_DELAY = 500;
	
	private BufferedImage flashImage;
	private Image originalImage;
	private Frame frame;
	
	private FlashThread flashThread;

	public TaskBarFlasher(Frame frame) {
		this.frame = frame;
		
		originalImage = frame.getIconImage();
		flashImage = new BufferedImage(originalImage.getWidth(null), originalImage.getHeight(null), BufferedImage.OPAQUE);
		Graphics g = flashImage.getGraphics();
		g.setColor(Color.blue);
		g.fillRect(0, 0, flashImage.getWidth(), flashImage.getHeight());
		g.drawImage(originalImage, 0, 0, null);
		g.dispose();
	}
	
	public synchronized void flash() {
		if (flashThread != null) {
			// Already flashing
			return;
		}
		
		flashThread = new FlashThread();
		flashThread.start();
	}
	
	private class FlashThread extends Thread {
		@Override
		public void run() {
			while (!frame.isFocused()) {
				try {
					Thread.sleep(FLASH_DELAY);
					frame.setIconImage(flashImage);
					Thread.sleep(FLASH_DELAY);
					frame.setIconImage(originalImage);
				} catch (InterruptedException ie) {
					// Just leave, whatever
					break;
				}
			}
			
			flashThread = null;
		}
	}
	
}
