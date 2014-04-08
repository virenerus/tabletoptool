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

import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

import com.t3.client.AppActions;
import com.t3.swing.PopupListener;



/**
 */
public class AssetTree extends JTree implements TreeSelectionListener {

    private Directory selectedDirectory;
    private AssetPanel assetPanel;
	
    public AssetTree(AssetPanel assetPanel) {
        super(assetPanel.getModel().getImageFileTreeModel());
        
		this.assetPanel = assetPanel;

        setCellRenderer(new AssetTreeCellRenderer());
        
        addMouseListener(createPopupListener());
        addTreeSelectionListener(this);
        
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        getSelectionModel().addTreeSelectionListener(this);
    }

    @Override
    public int getRowHeight() {
    	return -1;
    }
    public Directory getSelectedAssetGroup() {
        return selectedDirectory;
    }
    
    private MouseListener createPopupListener() {
        
        
        PopupListener listener = new PopupListener(createPopupMenu());
        
        return listener;
    }
    
    private JPopupMenu createPopupMenu() {
        
        JPopupMenu menu = new JPopupMenu ();
        menu.add(new JMenuItem(AppActions.REMOVE_ASSET_ROOT));
        
        return menu;
    }

    public void refresh() {
        ((ImageFileTreeModel) getModel()).refresh();
    }
    
    ////
    // Tree Selection Listener
    /* (non-Javadoc)
     * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
     */
    @Override
	public void valueChanged(TreeSelectionEvent e) {

        // Keep memory tight
        // TODO: make this an option
        if (selectedDirectory != null) {
            selectedDirectory.refresh();
        }
        
        selectedDirectory = null;
        
        Object node = e.getPath().getLastPathComponent();
        
        if (node instanceof Directory) {

            selectedDirectory = ((Directory) node);
			assetPanel.setDirectory((Directory) node);
        }
    }
}
