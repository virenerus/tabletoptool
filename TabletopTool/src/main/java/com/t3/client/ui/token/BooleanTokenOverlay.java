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

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.t3.model.Token;

/**
 * An overlay that may be applied to a token to show state.
 * 
 * @author jgorrell
 * @version $Revision: 5945 $ $Date: 2013-06-02 21:05:50 +0200 (Sun, 02 Jun 2013) $ $Author: azhrei_fje $
 */
public abstract class BooleanTokenOverlay extends AbstractTokenOverlay<Boolean> {

  /*---------------------------------------------------------------------------------------------
   * Constructors
   *-------------------------------------------------------------------------------------------*/

  /**
   * Create an overlay with the passed name.
   * 
   * @param aName Name of the new overlay.
   */
  protected BooleanTokenOverlay(String aName) {
      super(aName);
  }

  /*---------------------------------------------------------------------------------------------
   * AbstractTokenOverlay Methods
   *-------------------------------------------------------------------------------------------*/

  /**
   * @see com.t3.client.ui.token.AbstractTokenOverlay#paintOverlay(java.awt.Graphics2D, com.t3.model.Token, java.awt.Rectangle, java.lang.Object)
   */
  @Override
  public void paintOverlay(Graphics2D g, Token token, Rectangle bounds, Boolean value) {
      if (value)
        paintOverlay(g, token, bounds);
  }
  
  /*---------------------------------------------------------------------------------------------
   * Abstract Methods
   *-------------------------------------------------------------------------------------------*/

  /**
   * Paint the overlay for the passed token.
   * 
   * @param g Graphics used to paint. It is already translated so that 0,0 is
   * the upper left corner of the token. It is also clipped so that the overlay can not
   * draw out of the token's bounding box.
   * @param token The token being painted.
   * @param bounds The bounds of the actual token. This will be different than the clip
   * since the clip also has to take into account the edge of the window. If you draw 
   * based on the clip it will be off for partial token painting.
   */
  public abstract void paintOverlay(Graphics2D g, Token token, Rectangle bounds);
}
