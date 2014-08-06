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
import java.net.URL;
import java.util.List;

public class FileTransferableHandler extends TransferableHandler {

	private enum Flavor {
		
		fileList(new DataFlavor("application/x-java-file-list;class=java.util.List", null));
		
		DataFlavor flavor;
		
		private Flavor(DataFlavor flavor) {
			this.flavor = flavor;
		}
		
		public DataFlavor getFlavor() {
			return flavor;
		}
	}

	@Override
	public List<File> getTransferObject(Transferable transferable)
			throws IOException, UnsupportedFlavorException {

		if (transferable.isDataFlavorSupported(Flavor.fileList.getFlavor())) {
            // THIS IS AN INVALID CAST!
			// return (List<URL>)transferable.getTransferData(Flavor.fileList.getFlavor());
            return (List<File>)transferable.getTransferData(Flavor.fileList.getFlavor());
		}
		
		throw new UnsupportedFlavorException(null);
	}

}
