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

import java.awt.Rectangle;

import com.t3.MD5Key;
import com.t3.model.Token;
import com.t3.model.drawing.AbstractTemplate.Quadrant;
import com.t3.xstreamversioned.SerializationVersion;

/**
 * Place an image in a given corner.
 * 
 * @author Jay
 */
@SerializationVersion(0)
public class CornerImageTokenOverlay extends ImageTokenOverlay {

    /**
     * The corner where the image is placed
     */
    private Quadrant corner = Quadrant.SOUTH_EAST;

    /**
     * Needed for serialization
     */
    public CornerImageTokenOverlay() {
        this(BooleanTokenOverlay.DEFAULT_STATE_NAME, null, Quadrant.SOUTH_EAST);
    }
    
    /**
     * Create the complete image overlay.
     * 
     * @param name Name of the new token overlay
     * @param anAssetId Id of the image displayed in the new token overlay.
     * @param aCorner Corner that contains the image.
     */
    public CornerImageTokenOverlay(String name, MD5Key anAssetId, Quadrant aCorner) {
        super(name, anAssetId);
        corner = aCorner;
    }
    
    /**
     * @see com.t3.client.ui.token.BooleanTokenOverlay#clone()
     */
    @Override
    public Object clone() {
        BooleanTokenOverlay overlay = new CornerImageTokenOverlay(getName(), getAssetId(), corner);
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
     * @see com.t3.client.ui.token.ImageTokenOverlay#getImageBounds(java.awt.Rectangle, Token)
     */
    @Override
    protected Rectangle getImageBounds(Rectangle bounds, Token token) {
        int x = (bounds.width + 1) / 2; 
        int y = (bounds.height + 1) / 2;
        switch (corner) {
        case SOUTH_EAST:
          break;
        case SOUTH_WEST:
          x = 0;
          break;
        case NORTH_EAST:
          y = 0;
          break;
        case NORTH_WEST:
          x = y = 0;
          break;
        } // endswitch
        return new Rectangle(x, y, bounds.width / 2, bounds.height / 2);
    }

    /** @return Getter for corner */
    public Quadrant getCorner() {
        return corner;
    }
}
