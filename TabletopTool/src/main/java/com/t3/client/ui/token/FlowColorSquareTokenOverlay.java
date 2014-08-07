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

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;

import com.t3.model.Token;
import com.t3.xstreamversioned.version.SerializationVersion;

/**
 * Paint a square so that it doesn't overlay any other states being displayed in the same grid.
 * 
 * @author Jay
 */
@SerializationVersion(0)
public class FlowColorSquareTokenOverlay extends FlowColorDotTokenOverlay {


    /**
     * Default constructor needed for XML encoding/decoding
     */
    public FlowColorSquareTokenOverlay() {
      this(BooleanTokenOverlay.DEFAULT_STATE_NAME, Color.RED, -1);
    }

    /**
     * Create a new dot token overlay
     * 
     * @param aName Name of the token overlay
     * @param aColor Color of the dot
     * @param aGrid Size of the overlay grid for this state. All states with the 
     * same grid size share the same overlay.
     */
    public FlowColorSquareTokenOverlay(String aName, Color aColor, int aGrid) {
      super(aName, aColor, aGrid);
    }

    /**
     * @see com.t3.client.ui.token.BooleanTokenOverlay#clone()
     */
    @Override
    public Object clone() {
        BooleanTokenOverlay overlay = new FlowColorSquareTokenOverlay(getName(), getColor(), getGrid());
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
     * @see com.t3.client.ui.token.FlowColorDotTokenOverlay#getShape(java.awt.Rectangle, com.t3.model.Token)
     */
    @Override
    protected Shape getShape(Rectangle bounds, Token token) {
        return getFlow().getStateBounds2D(bounds, token, getName());
    }
}
