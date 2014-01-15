/*
 *  This software copyright by various authors including the RPTools.net
 *  development team, and licensed under the LGPL Version 3 or, at your
 *  option, any later version.
 *
 *  Portions of this software were originally covered under the Apache
 *  Software License, Version 1.1 or Version 2.0.
 *
 *  See the file LICENSE elsewhere in this distribution for license details.
 */

package net.rptools.maptool.client.swing;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JList;

import net.rptools.maptool.model.drawing.Pen;

/**
 * Combo box showing the available pen widths and a preview of each.
 * 
 * @author Jay
 * @version $Revision: 5945 $ $Date: 2013-06-02 21:05:50 +0200 (Sun, 02 Jun 2013) $ $Author:&
 */
public class PenWidthChooser extends JComboBox {

  /**
   * The renderer for this chooser.
   */
  private PenListRenderer renderer = new PenListRenderer();

  /**
   * Supported Pen Widths
   */
  public static final int[] WIDTHS = { 1, 3, 5, 7, 11, 15, 21 };
  
  /**
   * The width that the Icon is painted.
   */
  public static final int ICON_WIDTH = 25;
  
  /**
   * The maximum number of eleemnts in the list before it scrolls
   */
  public static final int MAX_ROW_COUNT = 10;
  
  /**
   * Create the renderer and model for the combo box
   */
  public PenWidthChooser() {
    setRenderer(renderer);
    DefaultComboBoxModel model = new DefaultComboBoxModel();
    int selected = -1;
    int defaultThickness = (int)Pen.DEFAULT.getThickness();
    for (int i = 0; i < WIDTHS.length; i += 1) {
      model.addElement(WIDTHS[i]);
      renderer.icon.strokes.put(WIDTHS[i], new BasicStroke(WIDTHS[i], BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
      if (WIDTHS[i] == defaultThickness) 
        selected = i;
    } // endfor
    
    // Set up the component
    setModel(model);
    setMaximumSize(getPreferredSize());
    setMaximumRowCount(MAX_ROW_COUNT);
    setSelectedIndex(Math.max(selected, WIDTHS[0]));
  }
  
  /**
   * Renderer for the items in the combo box
   * 
   * @author jgorrell
   * @version $Revision: 5945 $ $Date: 2013-06-02 21:05:50 +0200 (Sun, 02 Jun 2013) $ $Author: azhrei_fje $
   */
  private class PenListRenderer extends DefaultListCellRenderer {
    
    /**
     * Icon used to draw the line sample
     */
    PenIcon icon = new PenIcon();
    
    /**
     * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

      // Sets the text, clears the icon
      super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); 
      
      // Set the icon
      icon.width = (Integer)value;
      setIcon(icon);
      return this;
    }
  }
  
  /**
   * Icon for the renderer
   * 
   * @author jgorrell
   * @version $Revision: 5945 $ $Date: 2013-06-02 21:05:50 +0200 (Sun, 02 Jun 2013) $ $Author: azhrei_fje $
   */
  private class PenIcon implements Icon {

    /**
     * Pen used in drawing.
     */
    private int width = 0;
    
    /**
     * Strokes for this icon
     */
    private Map<Integer, Stroke> strokes = new HashMap<Integer, Stroke>();
    
    /**
     * @see javax.swing.Icon#getIconHeight()
     */
    public int getIconHeight() {
      return getHeight(); 
    }
    
    /**
     * @see javax.swing.Icon#getIconWidth()
     */
    public int getIconWidth() {
      return ICON_WIDTH;
    }
    
    /**
     * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
      
      // Fill the background
      Graphics2D g2d = (Graphics2D)g;
      g2d.setColor(c.getBackground());
      g2d.fillRect(x, y, getIconWidth(), getIconHeight());
      
      // Draw a line centered in the foreground
      g2d.setColor(c.getForeground());
      g2d.setStroke(strokes.get(width));
      int yCentered = y + getIconHeight() / 2;
      g2d.drawLine(x, yCentered, x + getIconWidth(), yCentered);
    }
  }
}
