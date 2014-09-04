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
package com.t3.client;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import com.t3.model.AssetManager;
import com.t3.persistence.FileUtil;

/**
 * Executes only the first time the application is run.
 */
//TODO refactor this
public class AppSetup {
	private static final Logger log = Logger.getLogger(AppSetup.class);

	public static void install() {
		File appDir = AppUtil.getAppHome();

		// Only init once
		if (appDir.listFiles().length > 0) {
			return;
		}
		try {
			installDefaultTokens();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void installDefaultTokens() throws IOException {
		installLibrary("Default", AppSetup.class.getResource("default_images.zip"));
	}

	public static void installLibrary(String libraryName, URL resourceFile) throws IOException {
		File unzipDir = new File(AppConstants.UNZIP_DIR, libraryName);
		FileUtil.unzip(resourceFile, unzipDir);
		installLibrary(libraryName, unzipDir);
	}

	public static void installLibrary(final String libraryName, final File root) {
		// Add as a resource root
		AppPreferences.addAssetRoot(root);
		if (TabletopTool.getFrame() != null) {
			TabletopTool.getFrame().addAssetRoot(root);

			// License
			File licenseFile = new File(root, "License.txt");
			if (!licenseFile.exists()) {
				licenseFile = new File(root, "license.txt");
			}
			if (licenseFile.exists()) {
				final File licenseFileFinal = licenseFile;
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							JTextPane pane = new JTextPane();
							pane.setPage(licenseFileFinal.toURI().toURL());
							JOptionPane.showMessageDialog(TabletopTool.getFrame(), pane, "License for " + libraryName, JOptionPane.INFORMATION_MESSAGE);
						} catch (MalformedURLException e) {
							log.error("Could not load license file: " + licenseFileFinal, e);
						} catch (IOException e) {
							log.error("Could not load license file: " + licenseFileFinal, e);
						}
					}
				});
			}
		}
		new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				AssetManager.searchForImageReferences(root, AppConstants.IMAGE_FILE_FILTER);
				return null;
			}
		}.execute();
	}
}
