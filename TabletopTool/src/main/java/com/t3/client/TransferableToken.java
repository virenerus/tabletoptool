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

import com.t3.model.Token;

public class TransferableToken implements Transferable {
    public static final DataFlavor dataFlavor = new DataFlavor(Token.class, "Token");

    private Token token;
    
    public TransferableToken(Token token) {
        this.token = token;
    }
    
    @Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return token;
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
