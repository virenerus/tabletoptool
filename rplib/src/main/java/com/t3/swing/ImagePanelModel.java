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

import java.awt.Image;
import java.awt.Paint;
import java.awt.datatransfer.Transferable;

public interface ImagePanelModel {

	public int getImageCount();
    public Transferable getTransferable(int index);
    public Object getID(int index);
    public Image getImage(Object ID);
	public Image getImage(int index);
    public String getCaption(int index);
    public Paint getBackground(int index);
    public Image[] getDecorations(int index);
}
