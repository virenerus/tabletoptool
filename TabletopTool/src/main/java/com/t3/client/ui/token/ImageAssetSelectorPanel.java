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
package com.t3.client.ui.token;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.t3.client.TabletopTool;
import com.t3.client.ui.assetpanel.AssetPanel;
import com.t3.swing.SelectionListener;

public class ImageAssetSelectorPanel extends JPanel {

	private ImageAssetPanel imageAssetPanel;
	private JButton imageExplorerButton;
	
	public ImageAssetSelectorPanel() {
		setLayout(new BorderLayout());
		
		add(BorderLayout.CENTER, getImageAssetPanel());
		add(BorderLayout.SOUTH, getImageExplorerButton());
	}
	
	public ImageAssetPanel getImageAssetPanel() {
		if (imageAssetPanel == null) {
			imageAssetPanel = new ImageAssetPanel();
		}
		return imageAssetPanel;
	}
	
	public JButton getImageExplorerButton() {
		if (imageExplorerButton == null) {
			imageExplorerButton = new JButton("...");
		}
		
		return imageExplorerButton;
	}
	
	private JComponent createImageExplorerPanel() {
		
		final AssetPanel assetPanel = new AssetPanel("imageAssetSelectorImageExplorer", TabletopTool.getFrame().getAssetPanel().getModel(), JSplitPane.HORIZONTAL_SPLIT);
		assetPanel.addImageSelectionListener(new SelectionListener() {
			@Override
			public void selectionPerformed(List<Object> selectedList) {
				// There should be exactly one
				if (selectedList.size() != 1) {
					return;
				}

				Integer imageIndex = (Integer) selectedList.get(0);

				getImageAssetPanel().setImageId(assetPanel.getAsset(imageIndex).getId());
			}
		});

		return assetPanel;
	}
}





















