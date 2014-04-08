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
package com.t3.client.ui;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import com.t3.client.ui.assetpanel.AssetPanel;
import com.t3.client.ui.assetpanel.AssetPanelModel;
import com.t3.model.Asset;
import com.t3.swing.AbstractPaintChooserPanel;
import com.t3.swing.ImagePanel;
import com.t3.swing.PaintChooser;
import com.t3.swing.SelectionListener;

public class TextureChooserPanel extends AbstractPaintChooserPanel {

	private PaintChooser paintChooser;
	private ImagePanel imagePanel;
	
	public TextureChooserPanel(PaintChooser paintChooser, AssetPanelModel model) {
		this(paintChooser, model, "textureChooser");
	}
	public TextureChooserPanel(PaintChooser paintChooser, AssetPanelModel model, String controlName) {
		setLayout(new GridLayout());

		this.paintChooser = paintChooser;
		
		add(createImageExplorerPanel(model, controlName));
	}
	
	private JComponent createImageExplorerPanel(AssetPanelModel model, String controlName) {

		final AssetPanel assetPanel = new AssetPanel(controlName, model, JSplitPane.HORIZONTAL_SPLIT);
		assetPanel.addImageSelectionListener(new SelectionListener() {
			@Override
			public void selectionPerformed(List<Object> selectedList) {
				// There should be exactly one
				if (selectedList.size() != 1) {
					return;
				}

				Integer imageIndex = (Integer) selectedList.get(0);
				Asset asset = assetPanel.getAsset(imageIndex);
				if (asset == null) {
					return;
				}

				paintChooser.setPaint(new AssetPaint(asset));
			}
		});
		assetPanel.setThumbSize(100);

		return assetPanel;
	}
	
	@Override
	public String getDisplayName() {
		return "Texture";
	}
}
