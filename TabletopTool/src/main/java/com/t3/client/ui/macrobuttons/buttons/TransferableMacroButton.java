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
package com.t3.client.ui.macrobuttons.buttons;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class TransferableMacroButton implements Transferable {

	public static final DataFlavor macroButtonFlavor = new DataFlavor(MacroButton.class, "Macro Button");
	
	//private TokenMacroButton button;
	private TransferData transferData;

	public TransferableMacroButton(MacroButton button) {
		//this.button = button;
		transferData = new TransferData(button);
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] {macroButtonFlavor};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor dataFlavor) {
		return dataFlavor.equals(macroButtonFlavor);
	}

	@Override
	public Object getTransferData(DataFlavor dataFlavor) throws UnsupportedFlavorException, IOException {
		if (dataFlavor.equals(macroButtonFlavor)) {
			return transferData;
		}
		
		return null;
	}
}
