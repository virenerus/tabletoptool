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
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;

import com.t3.model.Token;

/**
 * Draw an empty circle over a token.
 * 
 * @author jgorrell
 * @version $Revision: 5945 $ $Date: 2013-06-02 21:05:50 +0200 (Sun, 02 Jun 2013) $ $Author: azhrei_fje $
 */
public class OTokenOverlay extends XTokenOverlay {

  /**
   * Default constructor needed for XML encoding/decoding
   */
  public OTokenOverlay() {
    this(BooleanTokenOverlay.DEFAULT_STATE_NAME, Color.RED, 5);
  }

  /**
   * Create an O token overlay with the given name.
   * 
   * @param aName Name of this token overlay.
   * @param aColor The color of this token overlay.
   * @param aWidth The width of the lines in this token overlay.
   */
  public OTokenOverlay(String aName, Color aColor, int aWidth) {
    super(aName, aColor, aWidth);
  }

  /**
   * @see com.t3.client.ui.token.BooleanTokenOverlay#clone()
   */
  @Override
  public Object clone() {
      BooleanTokenOverlay overlay = new OTokenOverlay(getName(), getColor(), getWidth());
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
   * @see com.t3.client.ui.token.BooleanTokenOverlay#paintOverlay(java.awt.Graphics2D, com.t3.model.Token, Rectangle)
   */
  @Override
  public void paintOverlay(Graphics2D g, Token aToken, Rectangle bounds) {
    Color tempColor = g.getColor();
    g.setColor(getColor());
    Stroke tempStroke = g.getStroke();
    g.setStroke(getStroke());
    Composite tempComposite = g.getComposite();
    if (getOpacity() != 100)
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)getOpacity()/100));
    double offset = getStroke().getLineWidth() / 2.0;
    g.draw(new Ellipse2D.Double(0 + offset, 0 + offset, bounds.width - offset * 2, bounds.height - offset * 2));
    g.setColor(tempColor);
    g.setStroke(tempStroke);
    g.setComposite(tempComposite);
  }
}
