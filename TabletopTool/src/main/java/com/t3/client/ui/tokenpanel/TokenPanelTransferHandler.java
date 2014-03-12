/*
 *  This software copyright by various authors including the RPTools.net
 *  development team, and licensed under the LGPL Version 3 or, at your
 *  option, any later version.
 *
 *  Portions of this software were originally covered under the Apache
 *  Software License, Version 1.1 or Version 2.0.
 *
 *  See the file LICENSE elsewhere in this distribution for license details.
 */

package com.t3.client.ui.tokenpanel;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import com.t3.model.Token;
import com.t3.transferable.T3TokenTransferData;

/**
 * @author jgorrell
 * @version $Revision$ $Date$ $Author$
 */
public class TokenPanelTransferHandler extends TransferHandler {

  /*---------------------------------------------------------------------------------------------
   * Constructor
   *-------------------------------------------------------------------------------------------*/

  /**
   * Create the transfer handler for the passed display component.
   * 
   * @param displayComponent Create the handler for this component.
   */
  public TokenPanelTransferHandler(JComponent displayComponent) {
    if (displayComponent instanceof JList) 
      ((JList)displayComponent).setDragEnabled(true);
    displayComponent.setTransferHandler(this);
  }
  
  /*---------------------------------------------------------------------------------------------
   * Overridden TransferHandler methods
   *-------------------------------------------------------------------------------------------*/

  /**
   * @see javax.swing.TransferHandler#canImport(javax.swing.JComponent, java.awt.datatransfer.DataFlavor[])
   */
  @Override
  public boolean canImport(JComponent aComp, DataFlavor[] aTransferFlavors) {
    return false;
  }

  /**
   * @see javax.swing.TransferHandler#importData(javax.swing.JComponent, java.awt.datatransfer.Transferable)
   */
  @Override
  public boolean importData(JComponent aComp, Transferable aT) {
    return false;
  }

  /**
   * @see javax.swing.TransferHandler#getSourceActions(javax.swing.JComponent)
   */
  @Override
  public int getSourceActions(JComponent aC) {
    return TransferHandler.COPY;
  }
  
  /**
   * @see javax.swing.TransferHandler#createTransferable(javax.swing.JComponent)
   */
  @Override
  protected Transferable createTransferable(JComponent aC) {
    if (aC instanceof JList) {
      List<Token> selectedValues = ((JList<Token>)aC).getSelectedValuesList();
      return new TokenPanelTransferable(selectedValues);
    } // endif
    return null;
  }
}

/**
 * Used to transfer the selected tokens from the token panel.
 * 
 * @author jgorrell
 * @version $Revision$ $Date$ $Author$
 */
class TokenPanelTransferable implements Transferable {

  /**
   * The array of tokens read from the token panel when the transferable was created
   */
  private List<Token> tokens;
  
  /**
   * Create the transferable for the given tokens. 
   * 
   * @param theTokens Tokens being transfered. Uses object array since that is what is 
   * provided by the list.
   */
  TokenPanelTransferable(List<Token> theTokens) {
    tokens = theTokens;
  }
  
  /**
   * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
   */
  public T3TokenTransferData getTransferData(DataFlavor aFlavor) throws UnsupportedFlavorException, IOException {
    if (!isDataFlavorSupported(aFlavor)) 
      throw new UnsupportedFlavorException(aFlavor);
    T3TokenTransferData tokenList = new T3TokenTransferData();
    for (Token t:tokens)
      tokenList.add(t.toTransferData());
    return tokenList;
  }

  /**
   * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
   */
  public DataFlavor[] getTransferDataFlavors() {
    return new DataFlavor[] { T3TokenTransferData.MAP_TOOL_TOKEN_LIST_FLAVOR };
  }

  /**
   * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
   */
  public boolean isDataFlavorSupported(DataFlavor aFlavor) {
    return T3TokenTransferData.MAP_TOOL_TOKEN_LIST_FLAVOR.equals(aFlavor);
  }
  
}
