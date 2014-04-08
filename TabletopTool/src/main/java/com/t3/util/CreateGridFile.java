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
package com.t3.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.t3.image.ImageUtil;

/**
 * @author drice
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreateGridFile {
    
    private static BufferedImage createImage(int width, int height, int gridSize, Color color, Color backgroundColor) {
        BufferedImage image = ImageUtil.createCompatibleImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        Graphics g = image.getGraphics();
        
        g.setColor(backgroundColor);
        g.fillRect(0, 0, width, height);
        
        g.setColor(color);
        drawGrid(g, width, height, gridSize);
        
        return image;
    }
    
    private static void drawGrid(Graphics g, int width, int height, int gridSize) {
        for (int x = 0; x < width; x += gridSize) {
            g.drawLine(x, 0, x, height - 1);
        }
        
        for (int y = 0; y < height; y += gridSize) {
            g.drawLine(0, y, width - 1, y);
        }
    }
    
    public static void main(String[] args) throws Exception {
        BufferedImage image = createImage(501, 501, 10, Color.RED, Color.WHITE);
        
        ImageIO.write(image, "png", new File("grid_10.png"));
    }

}
