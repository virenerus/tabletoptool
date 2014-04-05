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

package com.t3.client.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.t3.MD5Key;
import com.t3.client.TabletopTool;
import com.t3.client.ui.assetpanel.AssetPanel;
import com.t3.model.Asset;
import com.t3.model.AssetManager;
import com.t3.swing.SelectionListener;
import com.t3.swing.SwingUtil;

public class ImageChooserDialog extends JDialog {

	private MD5Key imageId = null;
	private AssetPanel imageChooser = new AssetPanel("imageAssetPanel", TabletopTool.getFrame().getAssetPanel().getModel());
	
	public ImageChooserDialog(JFrame owner) {
		super(owner, "Choose Image", true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				imageId = null;
				setVisible(false);
			}
		});

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(BorderLayout.CENTER, imageChooser);
		panel.add(BorderLayout.SOUTH, createButtonPanel());
		
		setContentPane(panel);
		setSize(400, 500);
		SwingUtil.centerOver(this, getOwner());
		
		imageChooser.addImageSelectionListener(new SelectionListener() {
			@Override
			public void selectionPerformed(List<Object> selected) {
				if (selected.size() < 0 || (Integer)selected.get(0) < 0) {
					return;
				}
				
				Asset asset = imageChooser.getAsset((Integer)selected.get(0)); 
				imageId = asset.getId();
				
				// Put the asset into the asset manager since we have the asset handy here
				AssetManager.putAsset(asset);
			}
		});
	}
	
	public MD5Key getImageId() {
		return imageId;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		panel.add(createOKButton());
		panel.add(createCancelButton());
		
		return panel;
	}
	
	private JButton createOKButton() {
		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				setVisible(false);
			}
		});
		
		return button;
	}
	
	private JButton createCancelButton() {
		JButton button = new JButton("Cancel");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				imageId = null;
				setVisible(false);
			}
		});
		
		return button;
	}
}
