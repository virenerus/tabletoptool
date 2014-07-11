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
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import com.t3.MD5Key;
import com.t3.model.Token;
import com.t3.swing.SwingUtil;
import com.t3.util.ImageManager;
import com.t3.xstreamversioned.SerializationVersion;

/**
 * This is a token overlay that shows an image over the entire token
 * 
 * @author Jay
 */
@SerializationVersion(0)
public class ImageTokenOverlay extends BooleanTokenOverlay {

    /**
     * If of the image displayed in the overlay.
     */
    private MD5Key assetId;
    
    /**
     * Logger instance for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(ImageTokenOverlay.class.getName());
    
    /**
     * Needed for serialization
     */
    public ImageTokenOverlay() {
        this(BooleanTokenOverlay.DEFAULT_STATE_NAME, null);
    }
    
    /**
     * Create the complete image overlay.
     * 
     * @param name Name of the new token overlay
     * @param anAssetId Id of the image displayed in the new token overlay.
     */
    public ImageTokenOverlay(String name, MD5Key anAssetId) {
        super(name);
        assetId = anAssetId;
    }
    
    /**
     * @see com.t3.client.ui.token.BooleanTokenOverlay#clone()
     */
    @Override
    public Object clone() {
        BooleanTokenOverlay overlay = new ImageTokenOverlay(getName(), assetId);
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
     * @see com.t3.client.ui.token.BooleanTokenOverlay#paintOverlay(java.awt.Graphics2D, com.t3.model.Token, java.awt.Rectangle)
     */
    @Override
    public void paintOverlay(Graphics2D g, Token token, Rectangle bounds) {
        
        // Get the image
        Rectangle iBounds = getImageBounds(bounds, token);
        Dimension d = iBounds.getSize();
            
        BufferedImage image = ImageManager.getImageAndWait(assetId);
        Dimension size = new Dimension(image.getWidth(), image.getHeight());
        SwingUtil.constrainTo(size, d.width, d.height);
        
        // Paint it at the right location
        int width = size.width;
        int height = size.height;
        int x = iBounds.x + (d.width - width) / 2;
        int y = iBounds.y + (d.height - height) / 2;
        Composite tempComposite = g.getComposite();        
        if (getOpacity() != 100)
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)getOpacity()/100));
        g.drawImage(image, x, y, size.width, size.height, null);
        g.setComposite(tempComposite);
        
    }

    /** @return Getter for assetId */
    public MD5Key getAssetId() {
        return assetId;
    }

    /**
     * Calculate the image bounds from the token bounds
     * 
     * @param bounds Bounds of the token passed to the overlay.
     * @param token Token being decorated.
     * @return The bounds w/in the token where the image is painted.
     */
    protected Rectangle getImageBounds(Rectangle bounds, Token token) {
        return bounds;
    }
}
