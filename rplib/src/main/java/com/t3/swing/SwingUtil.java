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

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.Window;
import java.awt.event.InputEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import com.t3.image.ImageUtil;

/**
 */
public class SwingUtil {
    
    public static Cursor emptyCursor;
    
    static {
        try {
            emptyCursor = Toolkit.getDefaultToolkit().createCustomCursor( ImageUtil.getImage("com/t3/swing/image/empty.png"), new Point (0,0), "");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public static void useAntiAliasing(JComponent component) {
    	component.putClientProperty("AATextPropertyKey", true);
    }

    /**
     * Tell the G to use anti aliased drawing and text
     * @return old AA
     */
    public static Object useAntiAliasing(Graphics2D g) {
    	Object oldAA = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	return oldAA;
    }

    /**
     * Used after useAntiAliasing
     * @param oldAA the value returned from useAntiAliasing
     */
    public static void restoreAntiAliasing(Graphics2D g, Object oldAA) {
    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAA);
    }
    
	public static void centerOnScreen(Window window) {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension windowSize = window.getSize();
		
		int x = (screenSize.width - windowSize.width) / 2;
		int y = (screenSize.height - windowSize.height) / 2;
		
		window.setLocation(x, y);
	}
    

    public static boolean isControlDown(InputEvent e) {
        return (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) > 0;
    }
    
    public static boolean isShiftDown(InputEvent e) {
        return (e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) > 0;
    }
    
    public static boolean isShiftDown(int modifiersEx) {
        return (modifiersEx & InputEvent.SHIFT_DOWN_MASK) > 0;
    }
    
    public static void centerOver(Window innerWindow, Window outterWindow) {
    	
    	Dimension innerSize = innerWindow.getSize();
    	Dimension outterSize = outterWindow.getSize();
    	
    	int x = outterWindow.getLocation().x + (outterSize.width - innerSize.width) / 2;
    	int y = outterWindow.getLocation().y + (outterSize.height - innerSize.height) / 2;
    	
    	innerWindow.setLocation(x, y);
    }
    
    public static void constrainTo(Dimension dim, int size) {
    	
    	boolean widthBigger = dim.width > dim.height;
    	
    	if (widthBigger) {
    		dim.height = (int)((dim.height / (double)dim.width) * size);
    		dim.width = size;
    	} else {
    		dim.width = (int)((dim.width / (double)dim.height) * size);
    		dim.height = size;
    	}
    }

    public static void constrainTo(Dimension dim, int width, int height) {
        
        boolean widthBigger = dim.width > dim.height;
        
        constrainTo(dim, widthBigger ? width : height);
        
        if ((widthBigger && dim.height > height) || (!widthBigger && dim.width > width)) {

            int size = (int)Math.round(widthBigger ? (height / (double)dim.height) * width : (width / (double)dim.width) * dim.height);
            constrainTo(dim, size);
        }
    }
    
    /**
     * Don't show the mouse pointer for this component
     * @param component
     */
    public static void hidePointer ( Component component )
    {
        component.setCursor( emptyCursor );
    }
    
    /**
     * Set the mouse pointer for this component to the default system cursor
     * 
     * @param component
     */
    public static void showPointer ( Component component )
    {
        component.setCursor( Cursor.getDefaultCursor() );
    }
    
    public static void addMouseListenerToHierarchy(JComponent c, MouseListener listener) {
    	for (Component comp : c.getComponents()) {
    		comp.addMouseListener(listener);
    		
    		if (comp instanceof JComponent) {
    			addMouseListenerToHierarchy((JComponent)comp, listener);
    		}
    	}
    }
    public static void removeMouseListenerToHierarchy(JComponent c, MouseListener listener) {
    	for (Component comp : c.getComponents()) {
    		comp.removeMouseListener(listener);
    		
    		if (comp instanceof JComponent) {
    			removeMouseListenerToHierarchy((JComponent)comp, listener);
    		}
    	}
    }
    
    public static BufferedImage takeScreenShot(Component component, String... watermarks) {
    	
    	Dimension size = component.getSize();
    	
    	BufferedImage screenshot = new BufferedImage(size.width, size.height, Transparency.OPAQUE);
    	Graphics2D g = screenshot.createGraphics();
    	g.setClip(0, 0, size.width-1, size.height-1);
    	
    	component.update(g);

    	FontMetrics fm = g.getFontMetrics();
    	int y = fm.getDescent();
    	for (String watermark : watermarks)
    	if (watermark != null) {
	    	int x = size.width - SwingUtilities.computeStringWidth(fm, watermark);
	    	
	    	g.setColor(Color.black);
	    	g.drawString(watermark, x, y);
	
	    	g.setColor(Color.white);
	    	g.drawString(watermark, x-1, y-1);
	    	
	    	y -= fm.getHeight();
    	}
    	
    	g.dispose();
    	
    	return screenshot;
    }
    
    public static BufferedImage replaceColor(BufferedImage src, int sourceRGB, int replaceRGB) {
    	
    	for (int y = 0; y < src.getHeight(); y++) {
    		for (int x = 0; x < src.getWidth(); x++) {
    			
    			int rawRGB = src.getRGB(x, y);
    			int rgb = rawRGB & 0xffffff;
    			int alpha = rawRGB & 0xff000000;
    			
    			if (rgb == sourceRGB) {
    				src.setRGB(x, y, alpha | replaceRGB);
    			}
    		}
    	}
    	
    	return src;
    }
    
    public static Rectangle flip(Dimension view, Rectangle rect, int direction) {
        boolean flipHorizontal = (direction&1) == 1;
        boolean flipVertical = (direction&2) == 2;

        int x = flipHorizontal ? view.width - (rect.x + rect.width) : rect.x;
        int y = flipVertical ? view.height - (rect.y + rect.height) : rect.y;
        
        System.out.println(rect + " - " + new Rectangle(x, y, rect.width, rect.height));
        return new Rectangle(x, y, rect.width, rect.height);
    }
    
    public static JComponent getComponent(JComponent container, String name) {

    	List<Component> componentQueue = new LinkedList<Component>();
    	componentQueue.add(container);
    	while (componentQueue.size() > 0) {

    		Component c = componentQueue.remove(0);
        	String cname = c.getName();
        	if (cname != null && cname.equals(name)) {
        		return (JComponent)c;
        	}
        	
        	if (c instanceof Container) {
        		for (Component child : ((Container)c).getComponents()) {
        			componentQueue.add(child);
        		}
        	}
    	}
    	
    	return null;
    }
    
    public static boolean hasComponent(JComponent container, String name) {
    	return getComponent(container, name) != null;
    }
    
}
