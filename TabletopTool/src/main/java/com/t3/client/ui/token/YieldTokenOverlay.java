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
import java.awt.geom.Line2D;

import com.t3.model.Token;
import com.t3.xstreamversioned.SerializationVersion;

/**
 * Place a Yield (triangle point down) over a token.
 * 
 * @author pwright
 * @version $Revision$ $Date$ $Author$
 */
@SerializationVersion(0)
public class YieldTokenOverlay extends XTokenOverlay {

  /**
   * Default constructor needed for XML encoding/decoding
   */
  public YieldTokenOverlay() {
    this(BooleanTokenOverlay.DEFAULT_STATE_NAME, Color.YELLOW, 5);
  }
  
  /**
   * Create a Yield token overlay with the given name.
   * 
   * @param aName Name of this token overlay.
   * @param aColor The color of this token overlay.
   * @param aWidth The width of the lines in this token overlay.
   */
  public YieldTokenOverlay(String aName, Color aColor, int aWidth) {
    super(aName, aColor, aWidth);
  }
  
  /**
   * @see com.t3.client.ui.token.BooleanTokenOverlay#clone()
   */
  @Override
  public Object clone() {
      BooleanTokenOverlay overlay = new YieldTokenOverlay(getName(), getColor(), getWidth());
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
   * @see com.t3.client.ui.token.XTokenOverlay#paintOverlay(java.awt.Graphics2D, com.t3.model.Token, java.awt.Rectangle)
   */
  @Override
  public void paintOverlay(Graphics2D g, Token aToken, Rectangle bounds) {
	  Double hc = (double)bounds.width/2;
	  Double vc = bounds.height * 0.134;
    Color tempColor = g.getColor();
    g.setColor(getColor());
    Stroke tempStroke = g.getStroke();
    g.setStroke(getStroke());
    Composite tempComposite = g.getComposite();
    if (getOpacity() != 100)
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)getOpacity()/100));
    g.draw(new Line2D.Double(0, vc, bounds.width, vc));
    g.draw(new Line2D.Double( bounds.width, vc, hc, bounds.height));
    g.draw(new Line2D.Double(hc, bounds.height, 0, vc));
    g.setColor(tempColor);
    g.setStroke(tempStroke);
    g.setComposite(tempComposite);
  }
}
