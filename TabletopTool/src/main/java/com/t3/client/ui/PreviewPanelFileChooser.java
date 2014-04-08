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

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.t3.client.AppPreferences;
import com.t3.client.AppUtil;
import com.t3.image.ThumbnailManager;

/*
 * A File chooser with an image preview panel
 */
public class PreviewPanelFileChooser extends JFileChooser {
	
	private JPanel previewWrapperPanel;
	private ImagePreviewPanel browsePreviewPanel;
	private ThumbnailManager thumbnailManager = new ThumbnailManager(AppUtil.getAppHome("previewPanelThumbs"), new Dimension(150, 150));
	
	public PreviewPanelFileChooser() {
		this.setCurrentDirectory(AppPreferences.getLoadDir());	
		this.setAccessory(getPreviewWrapperPanel());
		this.addPropertyChangeListener(PreviewPanelFileChooser.SELECTED_FILE_CHANGED_PROPERTY,
				new FileSystemSelectionHandler());
	}
	
	private class FileSystemSelectionHandler implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			File previewFile = getImageFileOfSelectedFile();
			
			if (previewFile != null && !previewFile.isDirectory()) {
				try {	
					Image img = thumbnailManager.getThumbnail(previewFile);
					getPreviewPanel().setImage(img);
				} catch (IOException ioe) {
					getPreviewPanel().setImage(null);
				}
			} else {
				getPreviewPanel().setImage(null);
			}
		}
	}
	
	//Override if selected file is not also the image
	protected File getImageFileOfSelectedFile() {
		return getSelectedFile();
	}
	
	public Image getSelectedThumbnailImage() {
		return browsePreviewPanel.getImage();
	}
	
	private JPanel getPreviewWrapperPanel() {
		if (previewWrapperPanel == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			gridLayout.setColumns(1);
			previewWrapperPanel = new JPanel();
			previewWrapperPanel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(0, 5, 0, 0), BorderFactory
							.createTitledBorder(null, "Preview",
									TitledBorder.CENTER,
									TitledBorder.BELOW_BOTTOM, null, null)));
			previewWrapperPanel.setLayout(gridLayout);
			previewWrapperPanel.add(getPreviewPanel(), null);
		}
		return previewWrapperPanel;
	}
	
	private ImagePreviewPanel getPreviewPanel() {
		if (browsePreviewPanel == null) {

			browsePreviewPanel = new ImagePreviewPanel();
		}

		return browsePreviewPanel;
	}

}
