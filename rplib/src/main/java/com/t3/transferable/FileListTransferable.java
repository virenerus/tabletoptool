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
package com.t3.transferable;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileListTransferable implements Transferable {

    public static final DataFlavor FLAVOR = new DataFlavor("application/x-java-file-list;class=java.util.List", null);
    
    private List<File> fileList;
    
    public FileListTransferable(List<File> fileList) {
        this.fileList = fileList;
    }

    public FileListTransferable(File file) {
        fileList = new LinkedList<File>();
        fileList.add(file);
    }
    
    @Override
	public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{FLAVOR};
    }

    @Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(FLAVOR);
    }

    @Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        
        if (!flavor.equals(FLAVOR)) {
            throw new UnsupportedFlavorException(flavor);
        }
        
        return fileList;
    }

}
