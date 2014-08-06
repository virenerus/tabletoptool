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
package com.t3.client;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import com.t3.MD5Key;
import com.t3.guid.GUID;
import com.t3.model.Asset;



/**
 */
public class TransferableAssetReference implements Transferable {

    public static final DataFlavor dataFlavor = new DataFlavor(GUID.class, "AssetReference");
    
    private MD5Key assetID;
    
    public TransferableAssetReference(Asset asset) {
        this.assetID = asset.getId();
    }
    
    @Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return assetID;
    }

    @Override
	public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { dataFlavor};
    }

    @Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(dataFlavor);
    }
}
