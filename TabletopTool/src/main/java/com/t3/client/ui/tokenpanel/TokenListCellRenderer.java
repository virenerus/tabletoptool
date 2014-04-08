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
package com.t3.client.ui.tokenpanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.t3.model.Token;
import com.t3.swing.SwingUtil;
import com.t3.util.ImageManager;

public class TokenListCellRenderer extends DefaultListCellRenderer {

    private BufferedImage image;
    private String name;
    
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Token) {
            Token token = (Token) value;
            image = ImageManager.getImage(token.getImageAssetId(), this);
            name = token.getName();
            
            setText(" "); // hack to keep the row height the right size
        }
        return this;
    }
    
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        
        if (image != null) {
            
            Dimension imageSize = new Dimension(image.getWidth(), image.getHeight());
            SwingUtil.constrainTo(imageSize, getSize().height);
            g.drawImage(image, 0, 0, imageSize.width, imageSize.height, this);
            g.drawString(name, imageSize.width + 2, g.getFontMetrics().getAscent());
        }
    }
}
