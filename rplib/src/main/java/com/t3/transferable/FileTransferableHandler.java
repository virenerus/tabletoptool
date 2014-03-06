/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
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
	public List<URL> getTransferObject(Transferable transferable)
			throws IOException, UnsupportedFlavorException {

		if (transferable.isDataFlavorSupported(Flavor.fileList.getFlavor())) {
			return (List<URL>)transferable.getTransferData(Flavor.fileList.getFlavor());
		}
		
		throw new UnsupportedFlavorException(null);
	}

}
