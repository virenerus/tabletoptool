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
import java.util.ArrayList;

/**
 * A list of token transfer data that came from the Group Tool. Need to specify app in the class 
 * so that drag and drop functionality can check the mime type to see if it supports tokens from 
 * that particular app.
 * 
 * @author jgorrell
 * @version $Revision$ $Date$ $Author$
 */
public class GroupTokenTransferData extends ArrayList<TokenTransferData> {

    /**
     * The data flavor that describes a list of tokens for exporting to maptool.
     */
    public final static DataFlavor GROUP_TOKEN_LIST_FLAVOR = new DataFlavor(GroupTokenTransferData.class, "Group Tool Token List");
    
}
