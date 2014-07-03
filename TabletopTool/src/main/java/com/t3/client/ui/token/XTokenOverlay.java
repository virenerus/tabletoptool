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
package com.t3.client.ui.token;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import com.t3.model.Token;
import com.t3.xstreamversioned.SerializationVersion;

/**
 * Draw an X over a token.
 * 
 * @author jgorrell
 * @version $Revision: 5945 $ $Date: 2013-06-02 21:05:50 +0200 (Sun, 02 Jun 2013) $ $Author: azhrei_fje $
 */
@SerializationVersion(0)
public class XTokenOverlay extends BooleanTokenOverlay {

  /**
   * Color for the X
   */
  private Color color;

  /**
   * Stroke used to draw the line
   */
  private BasicStroke stroke;

  /**
   * Default constructor needed for XML encoding/decoding
   */
  public XTokenOverlay() {
    this(BooleanTokenOverlay.DEFAULT_STATE_NAME, Color.RED, 5);
  }

  /**
   * Create a X token overlay with the given name.
   * 
   * @param aName Name of this token overlay.
   * @param aColor The color of this token overlay.
   * @param aWidth The width of the lines in this token overlay.
   */
  public XTokenOverlay(String aName, Color aColor, int aWidth) {
    super(aName);
    if (aColor == null)
      aColor = Color.RED;
    color = aColor;
    if (aWidth <= 0)
      aWidth = 3;
    stroke = new BasicStroke(aWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
  }

  /**
   * @see com.t3.client.ui.token.BooleanTokenOverlay#paintOverlay(java.awt.Graphics2D, com.t3.model.Token, Rectangle)
   */
  @Override
  public void paintOverlay(Graphics2D g, Token aToken, Rectangle bounds) {
    Color tempColor = g.getColor();
    g.setColor(color);
    Stroke tempStroke = g.getStroke();
    g.setStroke(stroke);
    Composite tempComposite = g.getComposite();
    if (getOpacity() != 100)
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)getOpacity()/100));
    g.draw(new Line2D.Double(0, 0, bounds.width, bounds.height));
    g.draw(new Line2D.Double(0, bounds.height, bounds.width, 0));
    g.setColor(tempColor);
    g.setStroke(tempStroke);
    g.setComposite(tempComposite);
  }

  /**
   * @see com.t3.client.ui.token.BooleanTokenOverlay#clone()
   */
  @Override
  public Object clone() {
      BooleanTokenOverlay overlay = new XTokenOverlay(getName(), getColor(), getWidth());
      overlay.setOrder(getOrder());
      overlay.setGroup(getGroup());
      overlay.setMouseover(isMouseover());
      overlay.setOpacity(getOpacity());
      overlay.setShowGM(isShowGM());
      overlay.setShowOwner(isShowOwner());
      overlay.setShowOthers(isShowOthers());
      return overlay;
  }
  
  /**
   * Get the color for this XTokenOverlay.
   *
   * @return Returns the current value of color.
   */
  public Color getColor() {
    return color;
  }

  /**
   * Get the stroke for this XTokenOverlay.
   *
   * @return Returns the current value of stroke.
   */
  protected BasicStroke getStroke() {
    return stroke;
  }
  
  /**
   * Set the value of color for this XTokenOverlay.
   *
   * @param aColor The color to set.
   */
  public void setColor(Color aColor) {
    color = aColor;
  }

  /**
   * Get the width for this XTokenOverlay.
   *
   * @return Returns the current value of width.
   */
  public int getWidth() {
    return (int) stroke.getLineWidth();
  }

  /**
   * Set the value of width for this XTokenOverlay.
   *
   * @param aWidth The width to set.
   */
  public void setWidth(int aWidth) {
    if (aWidth <= 0)
      aWidth = 3;
    stroke = new BasicStroke(aWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
  }
}
