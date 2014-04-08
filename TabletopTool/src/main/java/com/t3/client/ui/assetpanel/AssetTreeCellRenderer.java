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
package com.t3.client.ui.assetpanel;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;



/**
 */
public class AssetTreeCellRenderer extends DefaultTreeCellRenderer {

	
	
    @Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        setBorder(null);
        
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (value instanceof Directory) {
            setText(((Directory) value).getPath().getName());
        }        
        
        if (row == 0) {
        	setIcon(null);
        }

        return this;
    }
    
    @Override
    public Icon getLeafIcon() {
    	return this.getOpenIcon();
    }
}
