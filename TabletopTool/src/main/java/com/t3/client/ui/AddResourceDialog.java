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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jidesoft.swing.FolderChooser;
import com.t3.FileUtil;
import com.t3.client.AppPreferences;
import com.t3.client.AppSetup;
import com.t3.client.RemoteFileDownloader;
import com.t3.client.TabletopTool;
import com.t3.client.WebDownloader;
import com.t3.client.ui.forms.AddResourceBaseDialog;
import com.t3.language.I18N;

public class AddResourceDialog extends AddResourceBaseDialog {

	private static final Logger log = Logger.getLogger(AddResourceDialog.class);

	private static final String LIBRARY_URL = "http://library.tabletoptool.com/legacy";
	private static final String LIBRARY_LIST_URL = LIBRARY_URL + "/listArtPacks";

	public enum Tab {
		LOCAL, WEB, TABLETOPTOOL_SITE
	}
	
	private Tab tab;

	private boolean downloadLibraryListInitiated;

	private boolean install = false;

	public AddResourceDialog(Frame owner) {
		super(owner);
		setPreferredSize(new Dimension(550, 300));
		resourceListDownloadingLabel.setText(I18N.getText("dialog.addresource.downloading"));
	}

	public boolean getInstall() {
		return install;
	}

	public JButton getInstallButton() {
		return installButton;
	}

	private void downloadLibraryList() {
		if (downloadLibraryListInitiated) {
			return;
		}

		// This pattern is safe because it is only called on the EDT
		downloadLibraryListInitiated = true;

		new SwingWorker<Object, Object>() {
			private ListModel<LibraryRow> model;
			private String downloadingLabelText="";

			@Override
			protected Object doInBackground() throws Exception {
				String result = null;
				try {
					WebDownloader downloader = new WebDownloader(new URL(LIBRARY_LIST_URL));
					result = downloader.read();
				} finally {
					if (result == null) {
						downloadingLabelText=I18N.getText("dialog.addresource.errorDownloading");
						return null;
					}
				}
				DefaultListModel<LibraryRow> listModel = new DefaultListModel<LibraryRow>();

				// Create a list to compare against for dups
				List<String> libraryNameList = new ArrayList<String>();
				for (File file : AppPreferences.getAssetRoots()) {
					libraryNameList.add(file.getName());
				}
				// Generate the list
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(result.getBytes())));
					String line = null;
					while ((line = reader.readLine()) != null) {
						LibraryRow row = new LibraryRow(line);

						// Don't include if we've already got it
						if (libraryNameList.contains(row.name)) {
							continue;
						}
						listModel.addElement(row);
					}
					model = listModel;
				} catch (Throwable t) {
					log.error("unable to parse library list", t);
					downloadingLabelText=I18N.getText("dialog.addresource.errorDownloading");
				}
				return null;
			}

			@Override
			protected void done() {
				if(model!=null)
					tabletoptoolList.setModel(model);
				resourceListDownloadingLabel.setText(downloadingLabelText);
				resourceListDownloadingLabel.setForeground(Color.RED);
			}
		}.execute();
	}

	public static class LibraryRow {
		private final String name;
		private String path;
		private final int size;

		public LibraryRow(String name, String path, int size) {
			this.name = name.trim();
			this.path = path.trim();
			this.size = size;
		}

		public LibraryRow(String row) {
			String[] data = row.split("\\|");

			name = data[0].trim();
			path = data[1].trim();
			size = Integer.parseInt(data[2]);
		}

		@Override
		public String toString() {
			return "<html><b>" + name + "</b> <i>(" + getSizeString() + ")</i>";
		}

		private String getSizeString() {
			NumberFormat format = NumberFormat.getNumberInstance();
			if (size < 1000) {
				return format.format(size) + " bytes";
			}
			if (size < 1000000) {
				return format.format(size / 1000) + " k";
			}
			return format.format(size / 1000000) + " mb";
		}
	}

	@Override
	protected void installButtonClicked(ActionEvent evt) {
		// Add the resource
		final List<LibraryRow> rowList = new ArrayList<LibraryRow>();

		switch (tab) {
		case LOCAL:
			if (StringUtils.isEmpty(localDirectoryField.getText())) {
				TabletopTool.showMessage("dialog.addresource.warn.filenotfound", "Error", JOptionPane.ERROR_MESSAGE, localDirectoryField.getText());
				return;
			}
			File root = new File(localDirectoryField.getText());
			if (!root.exists()) {
				TabletopTool.showMessage("dialog.addresource.warn.filenotfound", "Error", JOptionPane.ERROR_MESSAGE, localDirectoryField.getText());
				return;
			}
			if (!root.isDirectory()) {
				TabletopTool.showMessage("dialog.addresource.warn.directoryrequired", "Error", JOptionPane.ERROR_MESSAGE, localDirectoryField.getText());
				return;
			}
			try {
				AppSetup.installLibrary(FileUtil.getNameWithoutExtension(root), root);
			} catch (MalformedURLException e) {
				log.error("Bad path url: " + root.getPath(), e);
				TabletopTool.showMessage("dialog.addresource.warn.badpath", "Error", JOptionPane.ERROR_MESSAGE, localDirectoryField.getText());
				return;
			} catch (IOException e) {
				log.error("IOException adding local root: " + root.getPath(), e);
				TabletopTool.showMessage("dialog.addresource.warn.badpath", "Error", JOptionPane.ERROR_MESSAGE, localDirectoryField.getText());
				return;
			}
			return;
		case WEB:
			if (StringUtils.isEmpty(urlNameField.getText())) {
				TabletopTool.showMessage("dialog.addresource.warn.musthavename", "Error", JOptionPane.ERROR_MESSAGE, localDirectoryField.getText());
				return;
			}
			// validate the url format so that we don't hit it later
			try {
				new URL(urlField.getText());
			} catch (MalformedURLException e) {
				TabletopTool.showMessage("dialog.addresource.warn.invalidurl", "Error", JOptionPane.ERROR_MESSAGE, urlField.getText());
				return;
			}
			rowList.add(new LibraryRow(urlNameField.getText(), urlField.getText(), -1));
			break;

		case TABLETOPTOOL_SITE:
			List<LibraryRow> selectedRows = tabletoptoolList.getSelectedValuesList();
			if (selectedRows == null || selectedRows.isEmpty()) {
				TabletopTool.showMessage("dialog.addresource.warn.mustselectone", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			for (LibraryRow row : selectedRows) {

				//validate the url format
				row.path = LIBRARY_URL + "/" + row.path;
				try {
					new URL(row.path);
				} catch (MalformedURLException e) {
					TabletopTool.showMessage("dialog.addresource.warn.invalidurl", "Error", JOptionPane.ERROR_MESSAGE, row.path);
					return;
				}
				rowList.add(row);
			}
			break;
		}

		new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				for (LibraryRow row : rowList) {
					try {
						RemoteFileDownloader downloader = new RemoteFileDownloader(new URL(row.path));
						File tmpFile = downloader.read();
						AppSetup.installLibrary(row.name, tmpFile.toURL());
						tmpFile.delete();
					} catch (IOException e) {
						log.error("Error downloading library: " + e, e);
						TabletopTool.showInformation("dialog.addresource.warn.couldnotload");
					}
				}
				return null;
			}
		}.execute();
	}

	@Override
	protected void cancelButtonClicked(ActionEvent e) {
		this.dispose();
	}

	@Override
	protected void tabPaneChanged(ChangeEvent e) {
		switch (tabPane.getSelectedIndex()) {
			case 0:
				tab=Tab.LOCAL;
				break;
			case 1:
				tab=Tab.WEB;
				break;
			case 2:
				tab=Tab.TABLETOPTOOL_SITE;
				downloadLibraryList();
				break;
			}
	}

	@Override
	protected void localDirectoryButtonClicked(ActionEvent e) {
		FolderChooser folderChooser = new FolderChooser();
		folderChooser.setCurrentDirectory(TabletopTool.getFrame().getLoadFileChooser().getCurrentDirectory());
		folderChooser.setRecentListVisible(false);
		folderChooser.setFileHidingEnabled(true);
		folderChooser.setDialogTitle(I18N.getText("msg.title.loadAssetTree"));

		int result = folderChooser.showOpenDialog(localDirectoryButton.getTopLevelAncestor());
		if (result == FolderChooser.APPROVE_OPTION) {
			File root = folderChooser.getSelectedFolder();
			localDirectoryField.setText(root.getAbsolutePath());
		}
	}
}
