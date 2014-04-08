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
package com.t3.transferable;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.t3.image.ImageUtil;


public class ImageTransferableHandler extends TransferableHandler {

	private enum Flavor {
		
		image(new DataFlavor("image/x-java-image; class=java.awt.Image", "Image")),
		url(new DataFlavor("text/plain; class=java.lang.String", "Image"));
		
		DataFlavor flavor;
		
		private Flavor(DataFlavor flavor) {
			this.flavor = flavor;
		}
		
		public DataFlavor getFlavor() {
			return flavor;
		}
	}
	
	@Override
	public Image getTransferObject(Transferable transferable) throws IOException, UnsupportedFlavorException {

		if (transferable.isDataFlavorSupported(Flavor.image.getFlavor())) {
			Image image = (Image) transferable.getTransferData(Flavor.image.getFlavor());
			if (!(image instanceof BufferedImage)) {
				image = ImageUtil.createCompatibleImage(image);
			}
			
			return (BufferedImage) image;
		}

		if (transferable.isDataFlavorSupported(Flavor.url.getFlavor())) {
			String urlStr = (String) transferable.getTransferData(Flavor.url.getFlavor());
			
			try {
				URL url = new URL(urlStr);
				Image image = null;
				try {
					image = ImageIO.read(url);
				} catch (Exception e) {
					// try the old fasioned way
					image = Toolkit.getDefaultToolkit().getImage(url);
					MediaTracker mt = new MediaTracker(new JPanel());
					mt.addImage(image, 0);
					try {
						mt.waitForID(0);
					} catch (InterruptedException ie) {
						ie.printStackTrace();
					}
				}
				
				if (!(image instanceof BufferedImage)) {
					image = ImageUtil.createCompatibleImage(image);
				}
				
				return (BufferedImage) image;
			} catch (MalformedURLException mue) {
				// TODO: this can probably be ignored
				mue.printStackTrace();
			}
		}
		
		if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			
			List<File> fileList = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
			return ImageUtil.getImage(fileList.get(0));
		}
		
		throw new UnsupportedFlavorException(null);
	}
}
