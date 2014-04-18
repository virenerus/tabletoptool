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

import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Set;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.jidesoft.docking.DockableFrame;
import com.t3.FileUtil;
import com.t3.MD5Key;
import com.t3.client.tool.BoardTool;
import com.t3.client.tool.GridTool;
import com.t3.client.ui.AddResourceDialog;
import com.t3.client.ui.AppMenuBar;
import com.t3.client.ui.ClientConnectionPanel;
import com.t3.client.ui.ConnectToServerDialog;
import com.t3.client.ui.ConnectToServerDialogPreferences;
import com.t3.client.ui.ConnectionInfoDialog;
import com.t3.client.ui.ConnectionStatusPanel;
import com.t3.client.ui.ExportDialog;
import com.t3.client.ui.MapPropertiesDialog;
import com.t3.client.ui.PreviewPanelFileChooser;
import com.t3.client.ui.StartServerDialog;
import com.t3.client.ui.StartServerDialogPreferences;
import com.t3.client.ui.StaticMessageDialog;
import com.t3.client.ui.T3Frame;
import com.t3.client.ui.T3Frame.MTFrame;
import com.t3.client.ui.assetpanel.AssetPanel;
import com.t3.client.ui.assetpanel.Directory;
import com.t3.client.ui.campaignproperties.CampaignPropertiesDialog;
import com.t3.client.ui.dialogs.preferences.PreferencesDialog;
import com.t3.client.ui.io.FTPClient;
import com.t3.client.ui.io.FTPTransferObject;
import com.t3.client.ui.io.FTPTransferObject.Direction;
import com.t3.client.ui.io.ProgressBarList;
import com.t3.client.ui.io.UpdateRepoDialog;
import com.t3.client.ui.token.TransferProgressDialog;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.image.ImageUtil;
import com.t3.language.I18N;
import com.t3.model.Asset;
import com.t3.model.AssetManager;
import com.t3.model.CellPoint;
import com.t3.model.ExposedAreaMetaData;
import com.t3.GUID;
import com.t3.model.LookupTable;
import com.t3.model.Player;
import com.t3.model.TextMessage;
import com.t3.model.Token;
import com.t3.model.Zone;
import com.t3.model.Zone.Layer;
import com.t3.model.Zone.VisionType;
import com.t3.model.ZoneFactory;
import com.t3.model.ZonePoint;
import com.t3.model.campaign.Campaign;
import com.t3.model.campaign.CampaignFactory;
import com.t3.model.campaign.CampaignProperties;
import com.t3.model.drawing.DrawableTexturePaint;
import com.t3.model.grid.Grid;
import com.t3.networking.ServerConfig;
import com.t3.networking.ServerDisconnectHandler;
import com.t3.networking.ServerPolicy;
import com.t3.util.ImageManager;
import com.t3.util.PersistenceUtil;
import com.t3.util.PersistenceUtil.PersistedCampaign;
import com.t3.util.PersistenceUtil.PersistedMap;
import com.t3.util.SysInfo;
import com.t3.util.UPnPUtil;

/**
 * This class acts as a container for a wide variety of {@link Action}s that are
 * used throughout the application. Most of these are added to the main frame
 * menu, but some are added dynamically as needed, sometimes to the frame menu
 * but also to the context menu (the "right-click menu").
 * 
 * Each object instantiated from {@link DefaultClientAction} should have an
 * initializer that calls {@link ClientAction#init(String)} and passes the base
 * message key from the properties file. This base message key will be used to
 * locate the text that should appear on the menu item as well as the
 * accelerator, mnemonic, and short description strings. (See the {@link I18N}
 * class for more details on how the key is used.
 * 
 * In addition, each object should override {@link ClientAction#isAvailable()}
 * and return true if the application is in a state where the Action should be
 * enabled. (The default is <code>true</code>.)
 * 
 * Last is the {@link ClientAction#execute(ActionEvent)} method. It is passed
 * the {@link ActionEvent} object that triggered this Action as a parameter. It
 * should perform the necessary work to accomplish the effect of the Action.
 */
public class AppActions {
	private static final Logger log = Logger.getLogger(AppActions.class);

	private static Set<Token> tokenCopySet = null;
	public static final int menuShortcut = getMenuShortcutKeyMask();

	private static int getMenuShortcutKeyMask() {
		int key = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		String prop = System.getProperty("os.name", "unknown");
		if ("darwin".equalsIgnoreCase(prop)) {
			// TODO Should we install our own AWTKeyStroke class?  If we do it should only be if menu shortcut is CTRL...
			if (key == Event.CTRL_MASK)
				key = Event.META_MASK;
			/*
			 * In order for OpenJDK to work on Mac OS X, the user must have the
			 * X11 package installed unless they're running headless. However,
			 * in order for the Command key to work, the X11 Preferences must be
			 * set to "Enable the Meta Key" in X11 applications. Essentially, if
			 * this option is turned on, the Command key (called Meta in X11)
			 * will be intercepted by the X11 package and not sent to the
			 * application. The next step for TabletopTool will be better integration
			 * with the Mac desktop to eliminate the X11 menu altogether.
			 */
		}
		return key;
	}

	public static final Action MRU_LIST = new DefaultClientAction() {
		{
			init("menu.recent");
		}

		@Override
		public boolean isAvailable() {
			return TabletopTool.isHostingServer() || TabletopTool.isPersonalServer();
		}

		@Override
		public void execute(ActionEvent ae) {
			// Do nothing
		}
	};

	public static final Action EXPORT_SCREENSHOT = new DefaultClientAction() {
		{
			init("action.exportScreenShotAs");
		}

		@Override
		public void execute(ActionEvent e) {
			try {
				ExportDialog d = TabletopTool.getCampaign().getExportDialog();
				d.setVisible(true);
				TabletopTool.getCampaign().setExportDialog(d);
			} catch (Exception ex) {
				TabletopTool.showError("Cannot create the ExportDialog object", ex);
			}
		}
	};

	public static final Action EXPORT_SCREENSHOT_LAST_LOCATION = new DefaultClientAction() {
		{
			init("action.exportScreenShot");
		}

		@Override
		public void execute(ActionEvent e) {
			ExportDialog d = TabletopTool.getCampaign().getExportDialog();
			if (d == null || d.getExportLocation() == null || d.getExportSettings() == null) {
				// Can't do a save.. so try "save as"
				EXPORT_SCREENSHOT.actionPerformed(e);
			} else {
				try {
					d.screenCapture();
				} catch (Exception ex) {
					TabletopTool.showError("msg.error.failedExportingImage", ex);
				}
			}
		}
	};

	public static final Action EXPORT_CAMPAIGN_REPO = new AdminClientAction() {

		{
			init("admin.exportCampaignRepo");
		}

		@Override
		public void execute(ActionEvent e) {

			JFileChooser chooser = TabletopTool.getFrame().getSaveFileChooser();

			// Get target location
			if (chooser.showSaveDialog(TabletopTool.getFrame()) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			// Default extension
			File selectedFile = chooser.getSelectedFile();
			if (!selectedFile.getName().toUpperCase().endsWith(".ZIP")) {
				selectedFile = new File(selectedFile.getAbsolutePath() + ".zip");
			}

			if (selectedFile.exists()) {
				if (!TabletopTool.confirm("msg.confirm.fileExists")) {
					return;
				}
			}

			// Create index
			Campaign campaign = TabletopTool.getCampaign();
			Set<Asset> assetSet = new HashSet<Asset>();
			for (Zone zone : campaign.getZones()) {

				for (MD5Key key : zone.getAllAssetIds()) {
					assetSet.add(AssetManager.getAsset(key));
				}
			}

			// Export to temp location
			File tmpFile = new File(AppUtil.getAppHome("tmp").getAbsolutePath() + "/" + System.currentTimeMillis() + ".export");

			try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(tmpFile))){
				StringBuilder builder = new StringBuilder();
				for (Asset asset : assetSet) {

					// Index it
					builder.append(asset.getId()).append(" assets/").append(asset.getId()).append("\n");
					// Save it
					ZipEntry entry = new ZipEntry("assets/" + asset.getId().toString());
					out.putNextEntry(entry);
					out.write(asset.getImage());
				}

				// Handle the index
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				try (GZIPOutputStream gzout = new GZIPOutputStream(bout)) {
					gzout.write(builder.toString().getBytes());
				}

				ZipEntry entry = new ZipEntry("index.gz");
				out.putNextEntry(entry);
				out.write(bout.toByteArray());
				out.closeEntry();

				out.close();

				// Move to new location
				File mvFile = new File(AppUtil.getAppHome("tmp").getAbsolutePath() + "/" + selectedFile.getName());
				if (selectedFile.exists()) {
					FileUtil.copyFile(selectedFile, mvFile);
					selectedFile.delete();
				}

				FileUtil.copyFile(tmpFile, selectedFile);
				tmpFile.delete();

				if (mvFile.exists()) {
					mvFile.delete();
				}

			} catch (IOException ioe) {
				TabletopTool.showError(I18N.getString("msg.error.failedExportingCampaignRepo"), ioe);
				return;
			}

			TabletopTool.showInformation("msg.confirm.campaignExported");
		}
	};

	public static final Action UPDATE_CAMPAIGN_REPO = new DeveloperClientAction() {
		{
			init("admin.updateCampaignRepo");
		}

		/**
		 * <p>
		 * This action performs a repository update by comparing the assets in
		 * the current campaign against all assets in all repositories and
		 * uploading assets to one of the repositories and creating a
		 * replacement <b>index.gz</b> which is also uploaded.
		 * </p>
		 * <p>
		 * For the purposes of this action, only the FTP protocol is supported.
		 * The primary reason for this has to do with the way images will be
		 * uploaded. If HTTP were used and a single file sent, there would need
		 * to be a script on the remote end that knew how to unpack the file
		 * correctly. This cannot be assumed in the general case.
		 * </p>
		 * <p>
		 * Using FTP, we can upload individual files to particular directories.
		 * While this same approach could be used for HTTP, once again the user
		 * would have to install some kind of upload script on the server side.
		 * This again makes HTTP impractical and FTP more "user-friendly".
		 * </p>
		 * <p>
		 * <b>Implementation.</b> This method first makes a list of all known
		 * repositories from the campaign properties. They are presented to the
		 * user who then selects one as the destination for new assets to be
		 * uploaded. A list of assets currently used in the campaign is then
		 * generated and compared against the index files of all repositories
		 * from the previous list. Any new assets are aggregated and the user is
		 * presented with a summary of the images to be uploaded, including file
		 * size. The user enters FTP connection information and the upload
		 * begins as a separate thread. (Perhaps the Transfer Window can be used
		 * to keep track of the uploading process?)
		 * </p>
		 * <p>
		 * <b>Optimizations.</b> At some point, creating the list of assets
		 * could be spun into another thread, although there's probably not much
		 * value there. Or the FTP information could be collected at the
		 * beginning and as assets are checked they could immediately begin
		 * uploading with the summary including all assets, even those already
		 * uploaded.
		 * </p>
		 * <p>
		 * My review of FTP client libraries brought me to <a href=
		 * "http://www.javaworld.com/javaworld/jw-04-2003/jw-0404-ftp.html">
		 * this extensive review of FTP libraries</a> If we're going to do much
		 * more with FTP, <b>Globus GridFTP</b> looks good, but the library
		 * itself is 2.7MB.
		 * </p>
		 */
		@Override
		public void execute(ActionEvent e) {
			/*
			 * 1. Ask the user to select repositories which should be
			 * considered. 2. Ask the user for FTP upload information.
			 */
			UpdateRepoDialog urd;

			Campaign campaign = TabletopTool.getCampaign();
			CampaignProperties props = campaign.getCampaignProperties();

			urd = new UpdateRepoDialog(TabletopTool.getFrame(), props.getRemoteRepositoryList(), TabletopTool.getCampaign().getExportDialog().getExportLocation());
			urd.pack();
			urd.setVisible(true);
			if (urd.getStatus() == JOptionPane.CANCEL_OPTION) {
				return;
			}
			TabletopTool.getCampaign().getExportDialog().setExportLocation(urd.getFTPLocation());

			/*
			 * 3. Check all assets against the repository indices and build a
			 * new list from those that are not found.
			 */
			Map<MD5Key, Asset> missing = AssetManager.findAllAssetsNotInRepositories(urd.getSelectedRepositories());

			/*
			 * 4. Give the user a summary and ask for permission to begin the
			 * upload. I'm going to display a listbox and let the user click on
			 * elements of the list in order to see a preview to the right. But
			 * there's no plan to make it a CheckBoxList. (Wouldn't be _that_
			 * tough, however.)
			 */
			if (!TabletopTool.confirm(I18N.getText("msg.confirm.aboutToBeginFTP", missing.size() + 1)))
				return;

			/*
			 * 5. Build the index as we go, but add the images to FTP to a queue
			 * handled by another thread. Add a progress bar of some type or use
			 * the Transfer Status window.
			 */
			try {
				File topdir = urd.getDirectory();
				File dir = new File(urd.isCreateSubdir() ? getFormattedDate(null) : null);

				Map<String, String> repoEntries = new HashMap<String, String>(missing.size());
				FTPClient ftp = new FTPClient(urd.getHostname(), urd.getUsername(), urd.getPassword());

				// Enabling this means the upload begins immediately upon the first queued entry
				ftp.setEnabled(true);
				ProgressBarList pbl = new ProgressBarList(TabletopTool.getFrame(), ftp, missing.size() + 1);

				for (Map.Entry<MD5Key, Asset> entry : missing.entrySet()) {
					String remote = entry.getKey().toString();
					repoEntries.put(remote, dir == null ? remote : new File(dir, remote).getPath());
					ftp.addToQueue(new FTPTransferObject(Direction.FTP_PUT, entry.getValue().getImage(), dir, remote));
				}
				// We're done with "missing", so empty it now.
				missing.clear();

				// Handle the index
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				String saveTo = urd.getSaveToRepository();
				// When this runs our local 'repoindx' is updated.  If the FTP upload later fails,
				// it doesn't really matter much because the assets are already there.  However,
				// if our local cache is ever downloaded again, we'll "forget" that the assets are
				// on the server.  It sounds like it might be nice to have some way to resync
				// the local system with the FTP server.  But it's probably better to let the user
				// do it manually.
				byte[] index = AssetManager.updateRepositoryMap(saveTo, repoEntries);
				repoEntries.clear();
				try(GZIPOutputStream gzout = new GZIPOutputStream(bout)) {
					gzout.write(index);
				}
				ftp.addToQueue(new FTPTransferObject(Direction.FTP_PUT, bout.toByteArray(), topdir, "index.gz"));
			} catch (IOException ioe) {
				TabletopTool.showError("msg.error.failedUpdatingCampaignRepo", ioe);
				return;
			}
		}

		private String getFormattedDate(Date d) {
			// Use today's date as the directory on the FTP server.  This doesn't affect players'
			// ability to download it and might help the user determine what was uploaded to
			// their site and why.  It can't hurt. :)
			SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
			df.applyPattern("yyyy-MM-dd");
			return df.format(d == null ? new Date() : d);
		}
	};

	/**
	 * This is the menu option that forces clients to display the GM's current
	 * map.
	 */
	public static final Action ENFORCE_ZONE = new ZoneAdminClientAction() {

		{
			init("action.enforceZone");
		}

		@Override
		public void execute(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			if (renderer == null) {
				return;
			}

			TabletopTool.serverCommand().enforceZone(renderer.getZone().getId());
		}
	};

	public static final Action RESTORE_DEFAULT_IMAGES = new DefaultClientAction() {

		{
			init("action.restoreDefaultImages");
		}

		@Override
		public void execute(ActionEvent e) {
			try {
				AppSetup.installDefaultTokens();

				// TODO: Remove this hardwiring
				File unzipDir = new File(AppConstants.UNZIP_DIR.getAbsolutePath() + File.separator + "Default");
				TabletopTool.getFrame().addAssetRoot(unzipDir);
				AssetManager.searchForImageReferences(unzipDir, AppConstants.IMAGE_FILE_FILTER);

			} catch (IOException ioe) {
				TabletopTool.showError("msg.error.failedAddingDefaultImages", ioe);
			}
		}
	};

	public static final Action ADD_DEFAULT_TABLES = new DefaultClientAction() {

		{
			init("action.addDefaultTables");
		}

		@Override
		public void execute(ActionEvent e) {
			try(InputStream in = AppActions.class.getClassLoader().getResourceAsStream("com/t3/client/defaultTables.mtprops")) {
				// Load the defaults
				CampaignProperties properties = PersistenceUtil.loadCampaignProperties(in);

				// Make sure the images have been installed
				// Just pick a table and spot check
				LookupTable lookupTable = properties.getLookupTableMap().values().iterator().next();
				if (!AssetManager.hasAsset(lookupTable.getTableImage())) {
					AppSetup.installDefaultTokens();
				}

				TabletopTool.getCampaign().mergeCampaignProperties(properties);

				TabletopTool.getFrame().repaint();

			} catch (IOException ioe) {
				TabletopTool.showError("msg.error.failedAddingDefaultTables", ioe);
			}
		}
	};

	public static final Action RENAME_ZONE = new AdminClientAction() {

		{
			init("action.renameMap");
		}

		@Override
		public void execute(ActionEvent e) {

			Zone zone = TabletopTool.getFrame().getCurrentZoneRenderer().getZone();
			String msg = I18N.getText("msg.confirm.renameMap", zone.getName() != null ? zone.getName() : "");
			String name = JOptionPane.showInputDialog(TabletopTool.getFrame(), msg);
			if (name != null) {
				zone.setName(name);
				TabletopTool.serverCommand().renameZone(zone.getId(), name);
			}
		}
	};

	public static final Action SHOW_FULLSCREEN = new DefaultClientAction() {

		{
			init("action.fullscreen");
		}

		@Override
		public void execute(ActionEvent e) {

			if (TabletopTool.getFrame().isFullScreen()) {
				TabletopTool.getFrame().showWindowed();
			} else {
				TabletopTool.getFrame().showFullScreen();
			}
		}
	};

	public static final Action SHOW_CONNECTION_INFO = new DefaultClientAction() {
		{
			init("action.showConnectionInfo");
		}

		@Override
		public boolean isAvailable() {
			return super.isAvailable() && (TabletopTool.isPersonalServer() || TabletopTool.isHostingServer());
		}

		@Override
		public void execute(ActionEvent e) {

			if (TabletopTool.getServer() == null) {
				return;
			}

			ConnectionInfoDialog dialog = new ConnectionInfoDialog(TabletopTool.getServer());
			dialog.setVisible(true);
		}
	};

	public static final Action SHOW_PREFERENCES = new DefaultClientAction() {
		{
			init("action.preferences");
		}

		@Override
		public void execute(ActionEvent e) {

			// Probably don't have to create a new one each time
			PreferencesDialog dialog = new PreferencesDialog();
			dialog.setVisible(true);
		}
	};

	public static final Action SAVE_MESSAGE_HISTORY = new DefaultClientAction() {
		{
			init("action.saveMessageHistory");
		}

		@Override
		public void execute(ActionEvent e) {
			JFileChooser chooser = TabletopTool.getFrame().getSaveFileChooser();
			chooser.setDialogTitle(I18N.getText("msg.title.saveMessageHistory"));
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			if (chooser.showSaveDialog(TabletopTool.getFrame()) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File saveFile = chooser.getSelectedFile();
			if (saveFile.getName().indexOf(".") < 0) {
				saveFile = new File(saveFile.getAbsolutePath() + ".html");
			}
			if (saveFile.exists() && !TabletopTool.confirm("msg.confirm.fileExists")) {
				return;
			}

			try {
				String messageHistory = TabletopTool.getFrame().getCommandPanel().getMessageHistory();
				FileUtils.writeByteArrayToFile(saveFile, messageHistory.getBytes());
			} catch (IOException ioe) {
				TabletopTool.showError(I18N.getString("msg.error.failedSavingMessageHistory"), ioe);
			}
		}
	};

	public static final DefaultClientAction UNDO_PER_MAP = new DefaultClientAction() {
		{
			init("action.undoDrawing");
			isAvailable(); // XXX FJE Is this even necessary?
		}

		@Override
		public void execute(ActionEvent e) {
			Zone z = TabletopTool.getFrame().getCurrentZoneRenderer().getZone();
			z.undoDrawable();
			isAvailable();
			REDO_PER_MAP.isAvailable(); // XXX FJE Calling these forces the update, but won't the framework call them?
		}

		@Override
		public boolean isAvailable() {
			boolean result = false;
			T3Frame mtf = TabletopTool.getFrame();
			if (mtf != null) {
				ZoneRenderer zr = mtf.getCurrentZoneRenderer();
				if (zr != null) {
					Zone z = zr.getZone();
					result = z.canUndo();
				}
			}
			setEnabled(result);
			return isEnabled();
		}
	};

	public static final DefaultClientAction REDO_PER_MAP = new DefaultClientAction() {
		{
			init("action.redoDrawing");
			isAvailable(); // XXX Is this even necessary?
		}

		@Override
		public void execute(ActionEvent e) {
			Zone z = TabletopTool.getFrame().getCurrentZoneRenderer().getZone();
			z.redoDrawable();
			isAvailable();
			UNDO_PER_MAP.isAvailable();
		}

		@Override
		public boolean isAvailable() {
			boolean result = false;
			T3Frame mtf = TabletopTool.getFrame();
			if (mtf != null) {
				ZoneRenderer zr = mtf.getCurrentZoneRenderer();
				if (zr != null) {
					Zone z = zr.getZone();
					result = z.canRedo();
				}
			}
			setEnabled(result);
			return isEnabled();
		}
	};

	/*
	 * public static final DefaultClientAction UNDO_DRAWING = new
	 * DefaultClientAction() { { init("action.undoDrawing"); isAvailable(); //
	 * XXX FJE Is this even necessary? }
	 * 
	 * @Override public void execute(ActionEvent e) {
	 * DrawableUndoManager.getInstance().undo(); isAvailable();
	 * REDO_DRAWING.isAvailable(); // XXX FJE Calling these forces the update,
	 * but won't the framework call them? }
	 * 
	 * @Override public boolean isAvailable() {
	 * setEnabled(DrawableUndoManager.getInstance().getUndoManager().canUndo());
	 * return isEnabled(); } };
	 * 
	 * public static final DefaultClientAction REDO_DRAWING = new
	 * DefaultClientAction() { { init("action.redoDrawing"); isAvailable(); //
	 * XXX Is this even necessary? }
	 * 
	 * @Override public void execute(ActionEvent e) {
	 * DrawableUndoManager.getInstance().redo(); isAvailable();
	 * UNDO_DRAWING.isAvailable(); }
	 * 
	 * @Override public boolean isAvailable() {
	 * setEnabled(DrawableUndoManager.getInstance().getUndoManager().canRedo());
	 * return isEnabled(); } };
	 */

	public static final DefaultClientAction CLEAR_DRAWING = new DefaultClientAction() {
		{
			init("action.clearDrawing");
		}

		@Override
		public void execute(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			if (renderer == null) {
				return;
			}
			Zone.Layer layer = renderer.getActiveLayer();
			if (!TabletopTool.confirm("msg.confirm.clearAllDrawings", layer)) {
				return;
			}
			// LATER: Integrate this with the undo stuff
			// FJE ServerMethodHandler.clearAllDrawings() now empties the DrawableUndoManager as well.
			TabletopTool.serverCommand().clearAllDrawings(renderer.getZone().getId(), layer);
		}

		@Override
		public boolean isAvailable() {
			return true;
		}
	};

	public static final DefaultClientAction CUT_TOKENS = new DefaultClientAction() {
		{
			init("action.cutTokens");
		}

		@Override
		public boolean isAvailable() {
			return super.isAvailable() && TabletopTool.getFrame().getCurrentZoneRenderer() != null;
		}

		@Override
		public void execute(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			Set<GUID> selectedSet = renderer.getSelectedTokenSet();
			cutTokens(renderer.getZone(), selectedSet);
		}
	};

	/**
	 * Cut tokens in the set from the given zone.
	 * <p>
	 * If no tokens are deleted (because the incoming set is empty, because none
	 * of the tokens in the set exist in the zone, or because the user doesn't
	 * have permission to delete the tokens) then the
	 * {@link TabletopTool#SND_INVALID_OPERATION} sound is played.
	 * <p>
	 * If any tokens<i>are</i> deleted, then the selection set for the zone is
	 * cleared.
	 * 
	 * @param zone
	 * @param tokenSet
	 */
	public static final void cutTokens(Zone zone, Set<GUID> tokenSet) {
		// Only cut if some tokens are selected.  Don't want to accidentally
		// lose what might already be in the clipboard.
		boolean anythingDeleted = false;
		if (!tokenSet.isEmpty()) {
			copyTokens(tokenSet);

			// delete tokens
			for (GUID tokenGUID : tokenSet) {
				Token token = zone.getToken(tokenGUID);
				if (AppUtil.playerOwns(token)) {
					anythingDeleted = true;
					zone.removeToken(tokenGUID);
					TabletopTool.serverCommand().removeToken(zone.getId(), tokenGUID);
				}
			}
		}
		if (anythingDeleted) {
			TabletopTool.getFrame().getCurrentZoneRenderer().clearSelectedTokens();
		} else {
			TabletopTool.playSound(TabletopTool.SND_INVALID_OPERATION);
		}
	}

	public static final DefaultClientAction COPY_TOKENS = new DefaultClientAction() {
		{
			init("action.copyTokens");
		}

		@Override
		public boolean isAvailable() {
			return super.isAvailable() && TabletopTool.getFrame().getCurrentZoneRenderer() != null;
		}

		@Override
		public void execute(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			copyTokens(renderer.getSelectedTokenSet());
		}
	};

	/**
	 * Copies the given set of tokens to a holding area (not really the
	 * "clipboard") so that they can be pasted back in again later. This is the
	 * highest level function in that it determines token ownership (only owners
	 * can copy/cut tokens).
	 * 
	 * @param tokenSet
	 *            the set of tokens to copy; if empty, plays the
	 *            {@link TabletopTool#SND_INVALID_OPERATION} sound.
	 */
	public static final void copyTokens(Set<GUID> tokenSet) {
		List<Token> tokenList = null;
		boolean anythingCopied = false;
		if (!tokenSet.isEmpty()) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			Zone zone = renderer.getZone();
			tokenCopySet = new HashSet<Token>();
			tokenList = new ArrayList<Token>();

			for (GUID guid : tokenSet) {
				Token token = zone.getToken(guid);
				if (token != null && AppUtil.playerOwns(token)) {
					anythingCopied = true;
					tokenList.add(token);
				}
			}
		}
		// Only cut if some tokens are selected.  Don't want to accidentally
		// lose what might already be in the clipboard.
		if (anythingCopied) {
			copyTokens(tokenList);
		} else {
			TabletopTool.playSound(TabletopTool.SND_INVALID_OPERATION);
		}
	}

	private static Grid gridCopiedFrom = null;

	/**
	 * Copies the given set of tokens to a holding area (not really the
	 * "clipboard") so that they can be pasted back in again later. This method
	 * ignores token ownership and operates on the entire list. A token's (x,y)
	 * offset from the first token in the set is preserved so that relative
	 * positions are preserved when they are pasted back in later.
	 * <p>
	 * Here are the criteria for how copy/paste of tokens should work:
	 * <ol>
	 * <li><b>Both maps are gridless.</b><br>
	 * This case is very simple since there's no need to convert anything to
	 * cell coordinates and back again.
	 * <ul>
	 * <li>All tokens have their relative pixel offsets saved and reproduced
	 * when pasted back in.
	 * </ul>
	 * 
	 * <li><b>Both maps have grids.</b><br>
	 * This scheme will preserve proper spacing on the Token layer (for tokens)
	 * and on the Object and Background layers (for stamps). The spacing will
	 * NOT be correct when there's a mix of snapToGrid tokens and non-snapToGrid
	 * tokens, but I don't see any way to correct that. (Well, we could
	 * calculate a percentage distance from the token in the extreme corners of
	 * the pasted set and use that percentage to calculate a pixel location.
	 * Seems like a lot of work for not much payoff.)
	 * <ul>
	 * <li>For all tokens that are snapToGrid, the relative distances between
	 * tokens should be kept in "cell" units when copied. That way they can be
	 * pasted back in with the relative cell spacing reproduced.
	 * <li>For all tokens that are not snapToGrid, their relative pixel offsets
	 * should be saved and reproduced when the tokens are pasted.
	 * </ul>
	 * 
	 * <li><b>The source map is gridless and the destination has a grid.</b><br>
	 * This one is essentially identical to the first case.
	 * <ul>
	 * <li>All tokens are copied with relative pixel offsets. When pasted, those
	 * relative offsets are used for all non-snapToGrid tokens, but snapToGrid
	 * tokens have the relative pixel offsets applied and then are "snapped"
	 * into the correct cell location.
	 * </ul>
	 * 
	 * <li><b>The source map has a grid and the destination is gridless.</b><br>
	 * This one is essentially identical to the first case.
	 * <ul>
	 * <li>All tokens have their relative pixel distances saved and those
	 * offsets are reproduced when pasted.
	 * </ul>
	 * </ol>
	 * 
	 * @param tokenList
	 *            the list of tokens to copy; if empty, plays the
	 *            {@link TabletopTool#SND_INVALID_OPERATION} sound.
	 */
	public static final void copyTokens(List<Token> tokenList) {
		// Only cut if some tokens are selected.  Don't want to accidentally
		// lose what might already be in the clipboard.
		if (!tokenList.isEmpty()) {
			Token topLeft = tokenList.get(0);
			tokenCopySet = new HashSet<Token>();
			for (Token originalToken : tokenList) {
				if (originalToken.getY() < topLeft.getY() || originalToken.getX() < topLeft.getX()) {
					topLeft = originalToken;
				}
				Token newToken = new Token(originalToken);
				tokenCopySet.add(newToken);
			}
			/*
			 * Normalize. For gridless maps, keep relative pixel distances. For
			 * gridded maps, keep relative cell spacing. Since we're going to
			 * keep relative positions, we can just modify the (x,y) coordinates
			 * of all tokens by subtracting the position of the one in
			 * 'topLeft'. On paste we can use the saved 'gridCopiedFrom' to
			 * determine whether to use pixel distances or convert to cell
			 * distances.
			 */
			Zone zone = TabletopTool.getFrame().getCurrentZoneRenderer().getZone();
			try {
				gridCopiedFrom = (Grid) zone.getGrid().clone();
			} catch (CloneNotSupportedException e) {
				TabletopTool.showError("This can't happen as all grids MUST implement Cloneable!", e);
			}
			int x = topLeft.getX();
			int y = topLeft.getY();
			for (Token token : tokenCopySet) {
				// Save all token locations as relative pixel offsets.  They'll be made absolute when pasting them back in.
				token.setX(token.getX() - x);
				token.setY(token.getY() - y);
			}
		} else {
			TabletopTool.playSound(TabletopTool.SND_INVALID_OPERATION);
		}
	}

	public static final DefaultClientAction PASTE_TOKENS = new DefaultClientAction() {
		{
			init("action.pasteTokens");
		}

		@Override
		public boolean isAvailable() {
			return super.isAvailable() && tokenCopySet != null;
		}

		@Override
		public void execute(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			if (renderer == null) {
				return;
			}
			ScreenPoint screenPoint = renderer.getPointUnderMouse();
			if (screenPoint == null) {
				// Pick the middle of the map
				screenPoint = ScreenPoint.fromZonePoint(renderer, renderer.getCenterPoint());
			}
			ZonePoint zonePoint = screenPoint.convertToZone(renderer);
			pasteTokens(zonePoint, renderer.getActiveLayer());
			renderer.repaint();
		}
	};

	/**
	 * Pastes tokens from {@link #tokenCopySet} into the current zone at the
	 * specified location on the given layer. See {@link #copyTokens(List)} for
	 * details of how the copy/paste operations work with respect to grid type
	 * on the source and destination zones.
	 * 
	 * @param destination
	 *            ZonePoint specifying where to paste; normally this is
	 *            unchanged from the MouseEvent
	 * @param layer
	 *            the Zone.Layer that specifies which layer to paste onto
	 */
	private static void pasteTokens(ZonePoint destination, Layer layer) {
		Zone zone = TabletopTool.getFrame().getCurrentZoneRenderer().getZone();
		Grid grid = zone.getGrid();

		boolean snapToGrid = false;
		Token topLeft = null;

		for (Token origToken : tokenCopySet) {
			if (topLeft == null || origToken.getY() < topLeft.getY() || origToken.getX() < topLeft.getX()) {
				topLeft = origToken;
			}
			snapToGrid |= origToken.isSnapToGrid();
		}
		boolean newZoneSupportsSnapToGrid = grid.getCapabilities().isSnapToGridSupported();
		boolean gridCopiedFromSupportsSnapToGrid = gridCopiedFrom.getCapabilities().isSnapToGridSupported();
		if (snapToGrid && newZoneSupportsSnapToGrid) {
			CellPoint cellPoint = grid.convert(destination);
			destination = grid.convert(cellPoint);
		}
		// Create a set of all tokenExposedAreaGUID's to make searching by GUID much faster.
		Set<GUID> allTokensSet = null;
		{
			List<Token> allTokensList = zone.getTokens();
			if (!allTokensList.isEmpty()) {
				allTokensSet = new HashSet<GUID>(allTokensList.size());
				for (Token token : allTokensList) {
					allTokensSet.add(token.getExposedAreaGUID());
				}
			}
		}
		List<Token> tokenList = new ArrayList<Token>(tokenCopySet);
		Collections.sort(tokenList, Token.COMPARE_BY_ZORDER);
		List<String> failedPaste = new ArrayList<String>(tokenList.size());

		for (Token origToken : tokenList) {
			Token token = new Token(origToken);

			// need this here to get around times when a token is copied and pasted into the 
			// same zone, such as a framework "template"
			if (allTokensSet != null && allTokensSet.contains(token.getExposedAreaGUID())) {
				GUID guid = new GUID();
				token.setExposedAreaGUID(guid);
				ExposedAreaMetaData meta = zone.getExposedAreaMetaData(guid);
				// 'meta' references the object already stored in the zone's HashMap (it was created if necessary).
				meta.addToExposedAreaHistory(meta.getExposedAreaHistory());
				TabletopTool.serverCommand().updateExposedAreaMeta(zone.getId(), token.getExposedAreaGUID(), meta);
			}
			if (newZoneSupportsSnapToGrid && gridCopiedFromSupportsSnapToGrid && token.isSnapToGrid()) {
				// Convert (x,y) offset to a cell offset using the grid from the zone where the tokens were copied from
				CellPoint cp = gridCopiedFrom.convert(new ZonePoint(token.getX(), token.getY()));
				ZonePoint zp = grid.convert(cp);
				token.setX(zp.x + destination.x);
				token.setY(zp.y + destination.y);
			} else {
				// For gridless sources, gridless destinations, or tokens that are not SnapToGrid:  just use the pixel offsets
				token.setX(token.getX() + destination.x);
				token.setY(token.getY() + destination.y);
			}
			// paste into correct layer
			token.setLayer(layer);

			// check the token's name and change it, if necessary
			// XXX Merge this with the drag/drop code in ZoneRenderer.addTokens().
			boolean tokenNeedsNewName = false;
			if (TabletopTool.getPlayer().isGM()) {
				// For GMs, only change the name of NPCs.  It's possible that we should be changing the name of PCs as well
				// since macros don't work properly when multiple tokens have the same name, but if we changed it without
				// asking it could be seriously confusing.  Yet we don't want to popup a confirmation every time the GM pastes either. :(
				tokenNeedsNewName = token.getType() != Token.Type.PC;
			} else {
				// For Players, check to see if the name is already in use.  If it is already in use, make sure the current Player
				// owns the token being duplicated (to avoid subtle ways of manipulating someone else's token!).
				Token tokenNameUsed = zone.getTokenByName(token.getName());
				if (tokenNameUsed != null) {
					if (!AppUtil.playerOwns(tokenNameUsed)) {
						failedPaste.add(token.getName());
						continue;
					}
					tokenNeedsNewName = true;
				}
			}
			if (tokenNeedsNewName) {
				String newName = T3Util.nextTokenId(zone, token, true);
				token.setName(newName);
			}
			zone.putToken(token);
			TabletopTool.serverCommand().putToken(zone.getId(), token);
		}
		if (!failedPaste.isEmpty()) {
			String mesg = "Failed to paste token(s) with duplicate name(s): " + failedPaste;
			TextMessage msg = TextMessage.gm(mesg);
			TabletopTool.addMessage(msg);
//			msg.setChannel(Channel.ME);
//			TabletopTool.addMessage(msg);
		}
	}

	public static final Action REMOVE_ASSET_ROOT = new DefaultClientAction() {
		{
			init("action.removeAssetRoot");
		}

		@Override
		public void execute(ActionEvent e) {
			AssetPanel assetPanel = TabletopTool.getFrame().getAssetPanel();
			Directory dir = assetPanel.getSelectedAssetRoot();

			if (dir == null) {
				TabletopTool.showError("msg.error.mustSelectAssetGroupFirst");
				return;
			}
			if (!assetPanel.isAssetRoot(dir)) {
				TabletopTool.showError("msg.error.mustSelectRootGroup");
				return;
			}
			AppPreferences.removeAssetRoot(dir.getPath());
			assetPanel.removeAssetRoot(dir);
		}
	};

	public static final Action BOOT_CONNECTED_PLAYER = new DefaultClientAction() {
		{
			init("action.bootConnectedPlayer");
		}

		@Override
		public boolean isAvailable() {
			return TabletopTool.isHostingServer() || TabletopTool.getPlayer().isGM();
		}

		@Override
		public void execute(ActionEvent e) {
			ClientConnectionPanel panel = TabletopTool.getFrame().getConnectionPanel();
			Player selectedPlayer = panel.getSelectedValue();

			if (selectedPlayer == null) {
				TabletopTool.showError("msg.error.mustSelectPlayerFirst");
				return;
			}
			if (TabletopTool.getPlayer().equals(selectedPlayer)) {
				TabletopTool.showError("msg.error.cantBootSelf");
				return;
			}
			if (TabletopTool.isPlayerConnected(selectedPlayer.getName())) {
				String msg = I18N.getText("msg.confirm.bootPlayer", selectedPlayer.getName());
				if (TabletopTool.confirm(msg)) {
					TabletopTool.serverCommand().bootPlayer(selectedPlayer.getName());
					msg = I18N.getText("msg.info.playerBooted", selectedPlayer.getName());
					TabletopTool.showInformation(msg);
					return;
				}
			}
			TabletopTool.showError("msg.error.failedToBoot");
		}
	};

	/**
	 * This is the menu item that lets the GM override the typing notification
	 * toggle on the clients
	 */
	public static final Action TOGGLE_ENFORCE_NOTIFICATION = new AdminClientAction() {
		{
			init("action.enforceNotification");
		}

		@Override
		public boolean isSelected() {
			return AppState.isNotificationEnforced();
		}

		@Override
		public void execute(ActionEvent e) {
			AppState.setNotificationEnforced(!AppState.isNotificationEnforced());
			TabletopTool.serverCommand().enforceNotification(AppState.isNotificationEnforced());
		}

	};

	/**
	 * This is the menu option that forces the player view to continuously track
	 * the GM view.
	 */
	public static final Action TOGGLE_LINK_PLAYER_VIEW = new AdminClientAction() {
		{
			init("action.linkPlayerView");
		}

		@Override
		public boolean isSelected() {
			return AppState.isPlayerViewLinked();
		}

		@Override
		public void execute(ActionEvent e) {

			AppState.setPlayerViewLinked(!AppState.isPlayerViewLinked());
			TabletopTool.getFrame().getCurrentZoneRenderer().maybeForcePlayersView();
		}

	};

	public static final Action TOGGLE_SHOW_PLAYER_VIEW = new AdminClientAction() {
		{
			init("action.showPlayerView");
		}

		@Override
		public boolean isSelected() {
			return AppState.isShowAsPlayer();
		}

		@Override
		public void execute(ActionEvent e) {

			AppState.setShowAsPlayer(!AppState.isShowAsPlayer());
			TabletopTool.getFrame().refresh();
		}

	};

	public static final Action TOGGLE_SHOW_LIGHT_SOURCES = new AdminClientAction() {
		{
			init("action.showLightSources");
		}

		@Override
		public boolean isSelected() {
			return AppState.isShowLightSources();
		}

		@Override
		public void execute(ActionEvent e) {

			AppState.setShowLightSources(!AppState.isShowLightSources());
			TabletopTool.getFrame().refresh();
		}

	};

	public static final Action TOGGLE_COLLECT_PROFILING_DATA = new DefaultClientAction() {
		{
			init("action.collectPerformanceData");
		}

		@Override
		public boolean isSelected() {
			return AppState.isCollectProfilingData();
		}

		@Override
		public void execute(ActionEvent e) {
			AppState.setCollectProfilingData(!AppState.isCollectProfilingData());
			TabletopTool.getProfilingNoteFrame().setVisible(AppState.isCollectProfilingData());
		}
	};

	public static final Action TOGGLE_SHOW_MOVEMENT_MEASUREMENTS = new DefaultClientAction() {
		{
			init("action.showMovementMeasures");
		}

		@Override
		public boolean isSelected() {
			return AppState.getShowMovementMeasurements();
		}

		@Override
		public void execute(ActionEvent e) {

			AppState.setShowMovementMeasurements(!AppState.getShowMovementMeasurements());
			if (TabletopTool.getFrame().getCurrentZoneRenderer() != null) {
				TabletopTool.getFrame().getCurrentZoneRenderer().repaint();
			}
		}

	};

	public static final Action COPY_ZONE = new ZoneAdminClientAction() {
		{
			init("action.copyZone");
		}

		@Override
		public void execute(ActionEvent e) {
			Zone zone = TabletopTool.getFrame().getCurrentZoneRenderer().getZone();
			// XXX Perhaps ask the user if the copied map should have its GEA and/or TEA cleared?  An imported map would ask...
			String zoneName = JOptionPane.showInputDialog("New map name:", "Copy of " + zone.getName());
			if (zoneName != null) {
				Zone zoneCopy = new Zone(zone);
				zoneCopy.setName(zoneName);
				TabletopTool.addZone(zoneCopy);
			}
		}
	};

	public static final Action REMOVE_ZONE = new ZoneAdminClientAction() {
		{
			init("action.removeZone");
		}

		@Override
		public void execute(ActionEvent e) {
			if (!TabletopTool.confirm("msg.confirm.removeZone")) {
				return;
			}
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			TabletopTool.removeZone(renderer.getZone());
		}
	};

	public static final Action SHOW_ABOUT = new DefaultClientAction() {
		{
			init("action.showAboutDialog");
		}

		@Override
		public void execute(ActionEvent e) {
			TabletopTool.getFrame().showAboutDialog();
		}
	};

	/**
	 * This is the menu option that warps all clients views to the current GM's
	 * view.
	 */
	public static final Action ENFORCE_ZONE_VIEW = new ZoneAdminClientAction() {
		{
			init("action.enforceView");
		}

		@Override
		public void execute(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			if (renderer == null) {
				return;
			}
			renderer.forcePlayersView();
		}
	};

	/**
	 * Start entering text into the chat field
	 */
	public static final String CHAT_COMMAND_ID = "action.sendChat";

	public static final Action CHAT_COMMAND = new DefaultClientAction() {
		{
			init(CHAT_COMMAND_ID);
		}

		@Override
		public void execute(ActionEvent e) {
			if (!TabletopTool.getFrame().isCommandPanelVisible()) {
				TabletopTool.getFrame().showCommandPanel();
				TabletopTool.getFrame().getCommandPanel().startChat();
			} else {
				TabletopTool.getFrame().hideCommandPanel();
			}
		}
	};

	public static final String COMMAND_UP_ID = "action.commandUp";

	public static final String COMMAND_DOWN_ID = "action.commandDown";

	/**
	 * Start entering text into the chat field
	 */
	public static final String ENTER_COMMAND_ID = "action.runMacro";

	public static final Action ENTER_COMMAND = new DefaultClientAction() {
		{
			init(ENTER_COMMAND_ID, false);
		}

		@Override
		public void execute(ActionEvent e) {
			TabletopTool.getFrame().getCommandPanel().startMacro();
		}
	};

	/**
	 * Action tied to the chat field to commit the command.
	 */
	public static final String COMMIT_COMMAND_ID = "action.commitCommand";

	public static final Action COMMIT_COMMAND = new DefaultClientAction() {
		{
			init(COMMIT_COMMAND_ID);
		}

		@Override
		public void execute(ActionEvent e) {
			TabletopTool.getFrame().getCommandPanel().commitCommand();
		}
	};

	/**
	 * Action tied to the chat field to commit the command.
	 */
	public static final String CANCEL_COMMAND_ID = "action.cancelCommand";

	public static final Action CANCEL_COMMAND = new DefaultClientAction() {
		{
			init(CANCEL_COMMAND_ID);
		}

		@Override
		public void execute(ActionEvent e) {
			TabletopTool.getFrame().getCommandPanel().cancelCommand();
		}
	};

	public static final Action ADJUST_GRID = new ZoneAdminClientAction() {
		{
			init("action.adjustGrid");
		}

		@Override
		public void execute(ActionEvent e) {

			TabletopTool.getFrame().getToolbox().setSelectedTool(GridTool.class);
		}

	};

	public static final Action ADJUST_BOARD = new ZoneAdminClientAction() {
		{
			init("action.adjustBoard");
		}

		@Override
		public void execute(ActionEvent e) {

			if (TabletopTool.getFrame().getCurrentZoneRenderer().getZone().getMapAssetId() != null) {
				TabletopTool.getFrame().getToolbox().setSelectedTool(BoardTool.class);
			} else {
				TabletopTool.showInformation(I18N.getText("action.error.noMapBoard"));
			}
		}

	};

	private static TransferProgressDialog transferProgressDialog;
	public static final Action SHOW_TRANSFER_WINDOW = new DefaultClientAction() {
		{
			init("msg.info.showTransferWindow");
		}

		@Override
		public void execute(ActionEvent e) {

			if (transferProgressDialog == null) {
				transferProgressDialog = new TransferProgressDialog();
			}

			if (transferProgressDialog.isShowing()) {
				return;
			}

			transferProgressDialog.showDialog();
		}

	};

	public static final Action TOGGLE_GRID = new DefaultClientAction() {
		{
			init("action.showGrid");
			putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME));
			try {
				putValue(Action.SMALL_ICON, new ImageIcon(ImageUtil.getImage("com/t3/client/image/grid.gif")));
			} catch (IOException ioe) {
				TabletopTool.showError("While retrieving built-in 'grid.gif' image", ioe);
			}
		}

		@Override
		public boolean isSelected() {
			return AppState.isShowGrid();
		}

		@Override
		public void execute(ActionEvent e) {
			AppState.setShowGrid(!AppState.isShowGrid());
			if (TabletopTool.getFrame().getCurrentZoneRenderer() != null) {
				TabletopTool.getFrame().getCurrentZoneRenderer().repaint();
			}
		}
	};

	public static final Action TOGGLE_COORDINATES = new DefaultClientAction() {
		{
			init("action.showCoordinates");
			putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME));
		}

		@Override
		public boolean isAvailable() {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			return renderer != null && renderer.getZone().getGrid().getCapabilities().isCoordinatesSupported();
		}

		@Override
		public boolean isSelected() {
			return AppState.isShowCoordinates();
		}

		@Override
		public void execute(ActionEvent e) {

			AppState.setShowCoordinates(!AppState.isShowCoordinates());

			TabletopTool.getFrame().getCurrentZoneRenderer().repaint();
		}
	};

	public static final Action TOGGLE_ZOOM_LOCK = new DefaultClientAction() {
		{
			init("action.zoomLock");
			putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME));
		}

		@Override
		public boolean isAvailable() {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			return renderer != null;
		}

		@Override
		public boolean isSelected() {
			return AppState.isZoomLocked();
		}

		@Override
		public void execute(ActionEvent e) {
			AppState.setZoomLocked(!AppState.isZoomLocked());
			TabletopTool.getFrame().getZoomStatusBar().update(); // So the textfield becomes grayed out
		}
	};

	public static final Action TOGGLE_FOG = new ZoneAdminClientAction() {
		{
			init("action.enableFogOfWar");
		}

		@Override
		public boolean isSelected() {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			if (renderer == null) {
				return false;
			}

			return renderer.getZone().hasFog();
		}

		@Override
		public void execute(ActionEvent e) {

			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			if (renderer == null) {
				return;
			}

			Zone zone = renderer.getZone();
			zone.setHasFog(!zone.hasFog());

			TabletopTool.serverCommand().setZoneHasFoW(zone.getId(), zone.hasFog());

			renderer.repaint();
		}
	};

	public static class SetVisionType extends ZoneAdminClientAction {
		private final VisionType visionType;

		public SetVisionType(VisionType visionType) {
			this.visionType = visionType;
			init("visionType." + visionType.name());
		}

		@Override
		public boolean isSelected() {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			if (renderer == null) {
				return false;
			}

			return renderer.getZone().getVisionType() == visionType;
		}

		@Override
		public void execute(ActionEvent e) {

			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			if (renderer == null) {
				return;
			}

			Zone zone = renderer.getZone();

			if (zone.getVisionType() != visionType) {

				zone.setVisionType(visionType);

				TabletopTool.serverCommand().setVisionType(zone.getId(), visionType);

				renderer.flushFog();
				renderer.flushLight();
				renderer.repaint();
			}
		}
	};

	public static final Action TOGGLE_SHOW_TOKEN_NAMES = new DefaultClientAction() {
		{
			init("action.showNames");
			putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME));
			try {
				putValue(Action.SMALL_ICON, new ImageIcon(ImageUtil.getImage("com/t3/client/image/names.png")));
			} catch (IOException ioe) {
				TabletopTool.showError("While retrieving built-in 'names.png' image", ioe);
			}
		}

		@Override
		public void execute(ActionEvent e) {

			AppState.setShowTokenNames(!AppState.isShowTokenNames());
			if (TabletopTool.getFrame().getCurrentZoneRenderer() != null) {
				TabletopTool.getFrame().getCurrentZoneRenderer().repaint();
			}
		}
	};

	public static final Action TOGGLE_CURRENT_ZONE_VISIBILITY = new ZoneAdminClientAction() {

		{
			init("action.hideMap");
		}

		@Override
		public boolean isSelected() {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			if (renderer == null) {
				return false;
			}
			return renderer.getZone().isVisible();
		}

		@Override
		public void execute(ActionEvent e) {

			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			if (renderer == null) {
				return;
			}

			// TODO: consolidate this code with ZonePopupMenu
			Zone zone = renderer.getZone();
			zone.setVisible(!zone.isVisible());

			TabletopTool.serverCommand().setZoneVisibility(zone.getId(), zone.isVisible());
			TabletopTool.getFrame().getZoneMiniMapPanel().flush();
			TabletopTool.getFrame().repaint();
		}
	};

	public static final Action NEW_CAMPAIGN = new AdminClientAction() {
		{
			init("action.newCampaign");
		}

		@Override
		public void execute(ActionEvent e) {

			if (!TabletopTool.confirm("msg.confirm.newCampaign")) {

				return;
			}

			Campaign campaign = CampaignFactory.createBasicCampaign();
			AppState.setCampaignFile(null);
			TabletopTool.setCampaign(campaign);
			TabletopTool.serverCommand().setCampaign(campaign);

			ImageManager.flush();
			TabletopTool.getFrame().setCurrentZoneRenderer(TabletopTool.getFrame().getZoneRenderer(campaign.getZones().get(0)));
		}
	};

	/**
	 * Note that the ZOOM actions are defined as DefaultClientAction types. This
	 * allows the {@link ClientAction#getKeyStroke()} method to be invoked where
	 * otherwise it couldn't be.
	 * <p>
	 * (Well, it <i>could be</i> if we cast this object to the right type
	 * everywhere else but that's just tedious. And what is tedious is
	 * error-prone. :))
	 */
	public static final DefaultClientAction ZOOM_IN = new DefaultClientAction() {
		{
			init("action.zoomIn", false);
		}

		@Override
		public boolean isAvailable() {
			return !AppState.isZoomLocked();
		}

		@Override
		public void execute(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			if (renderer != null) {
				Dimension size = renderer.getSize();
				renderer.zoomIn(size.width / 2, size.height / 2);
				renderer.maybeForcePlayersView();
			}
		}
	};

	public static final DefaultClientAction ZOOM_OUT = new DefaultClientAction() {
		{
			init("action.zoomOut", false);
		}

		@Override
		public boolean isAvailable() {
			return !AppState.isZoomLocked();
		}

		@Override
		public void execute(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			if (renderer != null) {
				Dimension size = renderer.getSize();
				renderer.zoomOut(size.width / 2, size.height / 2);
				renderer.maybeForcePlayersView();
			}
		}
	};

	public static final DefaultClientAction ZOOM_RESET = new DefaultClientAction() {
		private Double lastZoom;

		{
			init("action.zoom100", false);
		}

		@Override
		public boolean isAvailable() {
			return !AppState.isZoomLocked();
		}

		@Override
		public void execute(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			if (renderer != null) {
				// Revert to last zoom if we have one, but don't if the user has manually
				// changed the scale since the last reset zoom (one to one index)
				if (lastZoom != null && renderer.getScale() == renderer.getZoneScale().getOneToOneScale()) {
					// Go back to the previous zoom
					renderer.setScale(lastZoom);

					// But make sure the next time we'll go back to 1:1
					lastZoom = null;
				} else {
					lastZoom = renderer.getScale();
					renderer.zoomReset(renderer.getWidth() / 2, renderer.getHeight() / 2);
				}
				renderer.maybeForcePlayersView();
			}
		}
	};

	public static final Action TOGGLE_ZONE_SELECTOR = new DefaultClientAction() {
		{
			init("action.showMapSelector");
		}

		@Override
		public boolean isSelected() {
			return TabletopTool.getFrame().getZoneMiniMapPanel().isVisible();
		}

		@Override
		public void execute(ActionEvent e) {
			JComponent panel = TabletopTool.getFrame().getZoneMiniMapPanel();
			panel.setVisible(!panel.isVisible());
		}
	};

	public static final Action TOGGLE_MOVEMENT_LOCK = new AdminClientAction() {
		{
			init("action.toggleMovementLock");
		}

		@Override
		public boolean isSelected() {
			return TabletopTool.getServerPolicy().isMovementLocked();
		}

		@Override
		public void execute(ActionEvent e) {

			ServerPolicy policy = TabletopTool.getServerPolicy();
			policy.setIsMovementLocked(!policy.isMovementLocked());

			TabletopTool.updateServerPolicy(policy);
			TabletopTool.getServer().updateServerPolicy(policy);
		}
	};

	public static final Action START_SERVER = new ClientAction() {
		{
			init("action.serverStart");
		}

		@Override
		public boolean isAvailable() {
			return TabletopTool.isPersonalServer();
		}

		@Override
		public void execute(ActionEvent e) {
			runBackground(new Runnable() {
				@Override
				public void run() {
					if (!TabletopTool.isPersonalServer()) {
						TabletopTool.showError("msg.error.alreadyRunningServer");
						return;
					}

					// TODO: Need to shut down the existing server first;
					StartServerDialog dialog = new StartServerDialog();
					dialog.showDialog();

					if (!dialog.accepted()) // Results stored in Preferences.userRoot()
						return;

					StartServerDialogPreferences serverProps = new StartServerDialogPreferences(); // data retrieved from Preferences.userRoot()

					ServerPolicy policy = new ServerPolicy();
					policy.setAutoRevealOnMovement(serverProps.isAutoRevealOnMovement());
					policy.setUseStrictTokenManagement(serverProps.getUseStrictTokenOwnership());
					policy.setPlayersCanRevealVision(serverProps.getPlayersCanRevealVision());
					policy.setUseIndividualViews(serverProps.getUseIndividualViews());
					policy.setPlayersReceiveCampaignMacros(serverProps.getPlayersReceiveCampaignMacros());
					policy.setIsMovementLocked(TabletopTool.getServerPolicy().isMovementLocked());

					// Tool Tips for unformatted inline rolls.
					policy.setUseToolTipsForDefaultRollFormat(serverProps.getUseToolTipsForUnformattedRolls());

					//my addition
					policy.setRestrictedImpersonation(serverProps.getRestrictedImpersonation());
					policy.setMovementMetric(serverProps.getMovementMetric());
					boolean useIF = serverProps.getUseIndividualViews() && serverProps.getUseIndividualFOW();
					policy.setUseIndividualFOW(useIF);

					ServerConfig config = new ServerConfig(serverProps.getUsername(), serverProps.getGMPassword(), serverProps.getPlayerPassword(), serverProps.getPort(), serverProps.getT3Name());

					// Use the existing campaign
					Campaign campaign = TabletopTool.getCampaign();

					boolean failed = false;
					try {
						ServerDisconnectHandler.disconnectExpected = true;
						TabletopTool.stopServer();

						// Use UPnP to open port in router
						if (serverProps.getUseUPnP()) {
							UPnPUtil.openPort(serverProps.getPort());
						}
						// Right now set this is set to whatever the last server settings were.  If we wanted to turn it on and
						// leave it turned on, the line would change to:
//						campaign.setHasUsedFogToolbar(useIF || campaign.hasUsedFogToolbar());
						campaign.setHasUsedFogToolbar(useIF);

						// Make a copy of the campaign since we don't coordinate local changes well ... yet

						/*
						 * JFJ 2010-10-27 The below creates a NEW campaign with
						 * a copy of the existing campaign. However, this is NOT
						 * a full copy. In the constructor called below, each
						 * zone from the previous campaign(ie, the one passed
						 * in) is recreated. This means that only some items for
						 * that campaign, zone(s), and token's are copied over
						 * when you start a new server instance.
						 * 
						 * You need to modify either Campaign(Campaign) or
						 * Zone(Zone) to get any data you need to persist from
						 * the pre-server campaign to the post server start up
						 * campaign.
						 */
						TabletopTool.startServer(dialog.getUsernameTextField().getText(), config, policy, new Campaign(campaign));

						// Connect to server
						String playerType = dialog.getRoleCombo().getSelectedItem().toString();
						if (playerType.equals("GM")) {
							TabletopTool.createConnection("localhost", serverProps.getPort(), new Player(dialog.getUsernameTextField().getText(), serverProps.getRole(), serverProps.getGMPassword()));
						} else {
							TabletopTool.createConnection("localhost", serverProps.getPort(), new Player(dialog.getUsernameTextField().getText(), serverProps.getRole(), serverProps.getPlayerPassword()));
						}

						// connecting
						TabletopTool.getFrame().getConnectionStatusPanel().setStatus(ConnectionStatusPanel.Status.server);
						TabletopTool.addLocalMessage("<span style='color:blue'><i>" + I18N.getText("msg.info.startServer") + "</i></span>");
					} catch (UnknownHostException uh) {
						TabletopTool.showError("msg.error.invalidLocalhost", uh);
						failed = true;
					} catch (IOException ioe) {
						TabletopTool.showError("msg.error.failedConnect", ioe);
						failed = true;
					}

					if (failed) {
						try {
							TabletopTool.startPersonalServer(campaign);
						} catch (IOException ioe) {
							TabletopTool.showError("msg.error.failedStartPersonalServer", ioe);
						}
					}

				}
			});
		}
	};

	public static final Action CONNECT_TO_SERVER = new ClientAction() {
		{
			init("action.clientConnect");
		}

		@Override
		public boolean isAvailable() {
			return TabletopTool.isPersonalServer();
		}

		@Override
		public void execute(ActionEvent e) {
			if (TabletopTool.isCampaignDirty() && !TabletopTool.confirm("msg.confirm.loseChanges"))
				return;

			final ConnectToServerDialog dialog = new ConnectToServerDialog();
			dialog.showDialog();
			if (!dialog.accepted())
				return;

			ServerDisconnectHandler.disconnectExpected = true;
			LOAD_MAP.setSeenWarning(false);
			TabletopTool.stopServer();

			// Install a temporary gimped campaign until we get the one from the
			// server
			final Campaign oldCampaign = TabletopTool.getCampaign();
			TabletopTool.setCampaign(new Campaign());

			// connecting
			TabletopTool.getFrame().getConnectionStatusPanel().setStatus(ConnectionStatusPanel.Status.connected);

			// Show the user something interesting until we've got the campaign
			// Look in ClientMethodHandler.setCampaign() for the corresponding
			// hideGlassPane
			StaticMessageDialog progressDialog = new StaticMessageDialog(I18N.getText("msg.info.connecting"));
			TabletopTool.getFrame().showFilledGlassPane(progressDialog);

			runBackground(new Runnable() {
				@Override
				public void run() {
					boolean failed = false;
					try {
						ConnectToServerDialogPreferences prefs = new ConnectToServerDialogPreferences();
						TabletopTool.createConnection(dialog.getServer(), dialog.getPort(), new Player(prefs.getUsername(), prefs.getRole(), prefs.getPassword()));

						TabletopTool.getFrame().hideGlassPane();
						TabletopTool.getFrame().showFilledGlassPane(new StaticMessageDialog(I18N.getText("msg.info.campaignLoading")));
					} catch (UnknownHostException e1) {
						TabletopTool.showError("msg.error.unknownHost", e1);
						failed = true;
					} catch (IOException e1) {
						TabletopTool.showError("msg.error.failedLoadCampaign", e1);
						failed = true;
					}
					if (failed || TabletopTool.getConnection() == null) {
						TabletopTool.getFrame().hideGlassPane();
						try {
							TabletopTool.startPersonalServer(oldCampaign);
						} catch (IOException ioe) {
							TabletopTool.showError("msg.error.failedStartPersonalServer", ioe);
						}
					}

				}
			});
		}
	};

	public static final Action DISCONNECT_FROM_SERVER = new ClientAction() {
		{
			init("action.clientDisconnect");
		}

		@Override
		public boolean isAvailable() {
			return !TabletopTool.isPersonalServer();
		}

		@Override
		public void execute(ActionEvent e) {
			if (TabletopTool.isHostingServer() && !TabletopTool.confirm("msg.confirm.hostingDisconnect"))
				return;
			disconnectFromServer();
		}
	};

	public static void disconnectFromServer() {
		Campaign campaign = TabletopTool.isHostingServer() ? TabletopTool.getCampaign() : CampaignFactory.createBasicCampaign();
		ServerDisconnectHandler.disconnectExpected = true;
		LOAD_MAP.setSeenWarning(false);
		TabletopTool.stopServer();
		TabletopTool.disconnect();
		try {
			TabletopTool.startPersonalServer(campaign);
		} catch (IOException ioe) {
			TabletopTool.showError("msg.error.failedStartPersonalServer", ioe);
		}
	}

	public static final Action LOAD_CAMPAIGN = new DefaultClientAction() {
		{
			init("action.loadCampaign");
		}

		@Override
		public boolean isAvailable() {
			return TabletopTool.isHostingServer() || TabletopTool.isPersonalServer();
		}

		@Override
		public void execute(ActionEvent ae) {
			if (TabletopTool.isCampaignDirty() && !TabletopTool.confirm("msg.confirm.loseChanges"))
				return;
			JFileChooser chooser = new CampaignPreviewFileChooser();
			chooser.setDialogTitle(I18N.getText("msg.title.loadCampaign"));
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			if (chooser.showOpenDialog(TabletopTool.getFrame()) == JFileChooser.APPROVE_OPTION) {
				File campaignFile = chooser.getSelectedFile();
				loadCampaign(campaignFile);
			}
		}
	};

	private static class CampaignPreviewFileChooser extends PreviewPanelFileChooser {
		private static final long serialVersionUID = -6566116259521360428L;

		CampaignPreviewFileChooser() {
			super();
			addChoosableFileFilter(TabletopTool.getFrame().getCmpgnFileFilter());
		}

		@Override
		protected File getImageFileOfSelectedFile() {
			if (getSelectedFile() == null) {
				return null;
			}
			return PersistenceUtil.getCampaignThumbnailFile(getSelectedFile().getName());
		}
	}

	public static void loadCampaign(final File campaignFile) {
		new Thread() {
			@Override
			public void run() {
				TabletopTool.getAutoSaveManager().pause(); // Pause auto-save while loading
				if (AppState.isSaving()) {
					int count = 5;
					do {
						StaticMessageDialog progressDialog = new StaticMessageDialog("Waiting " + count + " seconds for save to finish...");
						TabletopTool.getFrame().showFilledGlassPane(progressDialog);
						try {
							Thread.sleep(5 * 1000);
						} catch (InterruptedException e) {
							// ignore
						}
						count += 5;
					} while (AppState.isSaving());
					TabletopTool.getFrame().hideGlassPane();
				}
				try {
					StaticMessageDialog progressDialog = new StaticMessageDialog(I18N.getText("msg.info.campaignLoading"));
					try {
						// I'm going to get struck by lighting for writing code like this.
						// CLEAN ME CLEAN ME CLEAN ME !   I NEED A SWINGWORKER!
						TabletopTool.getFrame().showFilledGlassPane(progressDialog);

						// Before we do anything, let's back it up
						if (TabletopTool.getBackupManager() != null)
							TabletopTool.getBackupManager().backup(campaignFile);

						// Load
						final PersistedCampaign campaign = PersistenceUtil.loadCampaign(campaignFile);
						if (campaign != null) {
//							current = TabletopTool.getFrame().getCurrentZoneRenderer();
//							TabletopTool.getFrame().setCurrentZoneRenderer(null);
							ImageManager.flush(); // Clear out the old campaign's images

							AppState.setCampaignFile(campaignFile);
							AppPreferences.setLoadDir(campaignFile.getParentFile());
							AppMenuBar.getMruManager().addMRUCampaign(campaignFile);

							/*
							 * Bypass the serialization when we are hosting the
							 * server.
							 */
//							if (TabletopTool.isHostingServer() || TabletopTool.isPersonalServer()) {
//								/*
//								 * TODO: This optimization doesn't work since
//								 * the player name isn't the right thing to use
//								 * to exclude this thread...
//								 */
//								String playerName = TabletopTool.getPlayer().getName();
//								String command = ServerCommand.COMMAND.setCampaign.name();
//								TabletopTool.getServer().getMethodHandler().handleMethod(playerName, command, new Object[] { campaign.campaign });
//							} else
							{
								TabletopTool.serverCommand().setCampaign(campaign.campaign);
							}
							TabletopTool.setCampaign(campaign.campaign, campaign.currentZoneId);
							ZoneRenderer current = TabletopTool.getFrame().getCurrentZoneRenderer();
							if (campaign.currentView != null && current != null)
								current.setZoneScale(campaign.currentView);
							current.getZoneScale().reset();
							TabletopTool.getAutoSaveManager().tidy();

							// UI related stuff
							TabletopTool.getFrame().getCommandPanel().setIdentityName(null);
							TabletopTool.getFrame().resetPanels();
						}
					} finally {
						TabletopTool.getAutoSaveManager().restart();
						TabletopTool.getFrame().hideGlassPane();
					}
				} catch (IOException ioe) {
					TabletopTool.showError("msg.error.failedLoadCampaign", ioe);
				}
			}
		}.start();
	}

	public static final Action SAVE_CAMPAIGN = new DefaultClientAction() {
		{
			init("action.saveCampaign");
		}

		@Override
		public boolean isAvailable() {
			return (TabletopTool.isHostingServer() || TabletopTool.getPlayer().isGM());
		}

		@Override
		public void execute(final ActionEvent ae) {
			Observer callback = null;
			if (ae.getSource() instanceof Observer)
				callback = (Observer) ae.getSource();
			if (AppState.getCampaignFile() == null) {
				doSaveCampaignAs(callback);
				return;
			}
			doSaveCampaign(TabletopTool.getCampaign(), AppState.getCampaignFile(), callback);
		}
	};

	public static final Action SAVE_CAMPAIGN_AS = new DefaultClientAction() {
		{
			init("action.saveCampaignAs");
		}

		@Override
		public boolean isAvailable() {
			return TabletopTool.isHostingServer() || TabletopTool.getPlayer().isGM();
		}

		@Override
		public void execute(final ActionEvent ae) {
			doSaveCampaignAs(null);
		}
	};

	private static void doSaveCampaign(final Campaign campaign, final File file, final Observer callback) {
		TabletopTool.getFrame().showFilledGlassPane(new StaticMessageDialog(I18N.getText("msg.info.campaignSaving")));
		new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				if (AppState.isSaving()) {
					return "Campaign currently being auto-saved.  Try again later."; // string error message
				}
				try {
					AppState.setIsSaving(true);
					TabletopTool.getAutoSaveManager().pause();

					long start = System.currentTimeMillis();
					PersistenceUtil.saveCampaign(campaign, file);
					AppMenuBar.getMruManager().addMRUCampaign(AppState.getCampaignFile());
					TabletopTool.getFrame().setStatusMessage(I18N.getString("msg.info.campaignSaved"));

					// Minimum display time so people can see the message
					try {
						Thread.sleep(Math.max(0, 500 - (System.currentTimeMillis() - start)));
					} catch (InterruptedException e) {
						// Nothing to do
					}
					return null; // 'null' means everything worked; no errors
				} catch (IOException ioe) {
					TabletopTool.showError("msg.error.failedSaveCampaign", ioe);
				} catch (Throwable t) {
					TabletopTool.showError("msg.error.failedSaveCampaign", t);
				} finally {
					AppState.setIsSaving(false);
					TabletopTool.getAutoSaveManager().restart();
				}
				return "Failed due to exception"; // string error message
			}

			@Override
			protected void done() {
				TabletopTool.getFrame().hideGlassPane();
				Object obj = null;
				try {
					obj = get();
					if (obj instanceof String)
						TabletopTool.showWarning((String) obj);
				} catch (Exception e) {
					TabletopTool.showError("Exception during SwingWorker.get()?", e);
				}
				if (callback != null) {
					callback.update(null, obj);
				}
			}
		}.execute();
	}

	public static void doSaveCampaignAs(final Observer callback) {
		Campaign campaign = TabletopTool.getCampaign();
		JFileChooser chooser = TabletopTool.getFrame().getSaveCmpgnFileChooser();

		int saveStatus = chooser.showSaveDialog(TabletopTool.getFrame());
		if (saveStatus == JFileChooser.APPROVE_OPTION) {
			File campaignFile = chooser.getSelectedFile();

			if (campaignFile.exists() && !TabletopTool.confirm("msg.confirm.overwriteExistingCampaign")) {
				return;
			}
			if (campaignFile.getName().indexOf(".") < 0) {
				campaignFile = new File(campaignFile.getAbsolutePath() + AppConstants.CAMPAIGN_FILE_EXTENSION);
			}
			doSaveCampaign(campaign, campaignFile, callback);

			AppState.setCampaignFile(campaignFile);
			AppPreferences.setSaveDir(campaignFile.getParentFile());
			AppMenuBar.getMruManager().addMRUCampaign(AppState.getCampaignFile());
			TabletopTool.getFrame().setTitleViaRenderer(TabletopTool.getFrame().getCurrentZoneRenderer());
		}
	}

	public static final DeveloperClientAction SAVE_MAP_AS = new DeveloperClientAction() {
		{
			init("action.saveMapAs");
		}

		@Override
		public boolean isAvailable() {
			return TabletopTool.isHostingServer() || (TabletopTool.getPlayer() != null && TabletopTool.getPlayer().isGM());
		}

		@Override
		public void execute(ActionEvent ae) {
			ZoneRenderer zr = TabletopTool.getFrame().getCurrentZoneRenderer();
			JFileChooser chooser = TabletopTool.getFrame().getSaveFileChooser();
			chooser.setFileFilter(TabletopTool.getFrame().getMapFileFilter());
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setSelectedFile(new File(zr.getZone().getName()));
			if (chooser.showSaveDialog(TabletopTool.getFrame()) == JFileChooser.APPROVE_OPTION) {
				try {
					File mapFile = chooser.getSelectedFile();
					if (mapFile.getName().indexOf(".") < 0) {
						mapFile = new File(mapFile.getAbsolutePath() + AppConstants.MAP_FILE_EXTENSION);
					}
					PersistenceUtil.saveMap(zr.getZone(), mapFile);
					AppPreferences.setSaveDir(mapFile.getParentFile());
					TabletopTool.showInformation("msg.info.mapSaved");
				} catch (IOException ioe) {
					TabletopTool.showError("msg.error.failedSaveMap", ioe);
				}
			}
		}
	};

	public static abstract class LoadMapAction extends DeveloperClientAction {
		private boolean seenWarning = false;

		public boolean getSeenWarning() {
			return seenWarning;
		}

		public void setSeenWarning(boolean s) {
			seenWarning = s;
		}
	}

	/**
	 * LOAD_MAP is the Action used to implement the loading of an externally
	 * stored map into the current campaign. This Action is only available when
	 * the current application is either hosting a server or is not connected to
	 * a server.
	 * 
	 * Property used from <b>i18n.properties</b> is <code>action.loadMap</code>
	 * 
	 * @author FJE
	 */
	public static final LoadMapAction LOAD_MAP = new LoadMapAction() {
		{
			init("action.loadMap");
		}

		@Override
		public boolean isAvailable() {
//			return TabletopTool.isHostingServer() || TabletopTool.isPersonalServer();
			// I'd like to be able to use this instead as it's less restrictive, but it's safer to disallow for now.
			return TabletopTool.isHostingServer() || (TabletopTool.getPlayer() != null && TabletopTool.getPlayer().isGM());
		}

		@Override
		public void execute(ActionEvent ae) {
			boolean isConnected = !TabletopTool.isHostingServer() && !TabletopTool.isPersonalServer();
			if (getSeenWarning() == false) {
				// If we're connected to a remote server and we are logged in as GM, this is true
				boolean isRemoteGM = isConnected && TabletopTool.getPlayer() != null && TabletopTool.getPlayer().isGM();
				isRemoteGM = true;
				if (isRemoteGM) {
					// Returns true if they select OK and false otherwise
//					setSeenWarning(TabletopTool.confirm("action.loadMap.warning"));
					ImageIcon icon = null;
					try {
						Image img = ImageUtil.getImage("com/t3/client/image/book_open.png");
						img = ImageUtil.createCompatibleImage(img, 16, 16, null);
						icon = new ImageIcon(img);
					} catch (IOException ex) {
					}
					JButton b = new JButton("Help", icon);
					Object[] options = { b, "Yes", "No" };
					int result = JOptionPane.showOptionDialog(
							TabletopTool.getFrame(),
							// FIXME This string doesn't render as HTML properly -- no BOLD shows up?!
							"<html>This is an <b>experimental</b> feature.  Save your campaign before using this feature (you are a GM logged in remotely).",
							I18N.getText("msg.title.messageDialogConfirm"),
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE, null,
							options, options[2]
							);
					if (result == 1)
						setSeenWarning(true); // Yes
					else {
						if (result == 0) { // Help
							// TODO We really need a better way to disseminate this information.  Perhaps we could assign every
							// external link a UUID, then have TabletopTool load a mapping from UUID-to-URL at runtime?  The
							// mapping could come from the rptools.net site initially and be cached for future use, with a
							// periodic "Check for new updates" option available from the Help menu...?
							TabletopTool.showDocument("http://forums.rptools.net/viewtopic.php?f=3&t=23614");
						}
						return;
					}
				} else
					setSeenWarning(true);
			}
			if (getSeenWarning()) {
				JFileChooser chooser = new MapPreviewFileChooser();
				chooser.setDialogTitle(I18N.getText("msg.title.loadMap"));
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				if (chooser.showOpenDialog(TabletopTool.getFrame()) == JFileChooser.APPROVE_OPTION) {
					File mapFile = chooser.getSelectedFile();
					loadMap(mapFile);
				}
			}
		}
	};

	private static class MapPreviewFileChooser extends PreviewPanelFileChooser {
		MapPreviewFileChooser() {
			super();
			addChoosableFileFilter(TabletopTool.getFrame().getMapFileFilter());
		}

		@Override
		protected File getImageFileOfSelectedFile() {
			if (getSelectedFile() == null) {
				return null;
			}
			return PersistenceUtil.getCampaignThumbnailFile(getSelectedFile().getName());
		}
	}

	public static void loadMap(final File mapFile) {
		new Thread() {
			@Override
			public void run() {
				try {
					StaticMessageDialog progressDialog = new StaticMessageDialog(I18N.getText("msg.info.mapLoading"));

					try {
						// I'm going to get struck by lighting for writing code like this.
						// CLEAN ME CLEAN ME CLEAN ME ! I NEED A SWINGWORKER !
						TabletopTool.getFrame().showFilledGlassPane(progressDialog);

						// Load
						final PersistedMap map = PersistenceUtil.loadMap(mapFile);

						if (map != null) {
							AppPreferences.setLoadDir(mapFile.getParentFile());
							if ((map.zone.getExposedArea() != null && !map.zone.getExposedArea().isEmpty())
									|| (map.zone.getExposedAreaMetaData() != null && !map.zone.getExposedAreaMetaData().isEmpty())) {
								boolean ok = TabletopTool.confirm("<html>Map contains exposed areas of fog.<br>Do you want to reset all of the fog?");
								if (ok == true) {
									// This fires a ModelChangeEvent, but that shouldn't matter
									map.zone.clearExposedArea();
								}
							}
							TabletopTool.addZone(map.zone);

							TabletopTool.getAutoSaveManager().restart();
							TabletopTool.getAutoSaveManager().tidy();

							// Flush the images associated with the current
							// campaign
							// Do this juuuuuust before we get ready to show the
							// new campaign, since we
							// don't want the old campaign reloading images
							// while we loaded the new campaign

							// XXX (FJE) Is this call even needed for loading
							// maps? Probably not...
							ImageManager.flush();
						}
					} finally {
						TabletopTool.getFrame().hideGlassPane();
					}
				} catch (IOException ioe) {
					TabletopTool.showError("msg.error.failedLoadMap", ioe);
				}
			}
		}.start();
	}

	public static final Action CAMPAIGN_PROPERTIES = new DefaultClientAction() {
		{
			init("action.campaignProperties");
		}

		@Override
		public boolean isAvailable() {
			return TabletopTool.getPlayer().isGM();
		}

		@Override
		public void execute(ActionEvent ae) {
			Campaign campaign = TabletopTool.getCampaign();

			// TODO: There should probably be only one of these
			CampaignPropertiesDialog dialog = new CampaignPropertiesDialog(TabletopTool.getFrame());
			dialog.setCampaign(campaign);
			dialog.setVisible(true);
			if (dialog.getStatus() == CampaignPropertiesDialog.Status.CANCEL) {
				return;
			}
			// TODO: Make this pass all properties, but we don't have that
			// framework yet, so send what we know the old fashioned way
			TabletopTool.serverCommand().updateCampaign(campaign.getCampaignProperties());
		}
	};

	public static class GridSizeAction extends DefaultClientAction {
		private final int size;

		public GridSizeAction(int size) {
			putValue(Action.NAME, Integer.toString(size));
			this.size = size;
		}

		@Override
		public boolean isSelected() {
			return AppState.getGridSize() == size;
		}

		@Override
		public void execute(ActionEvent arg0) {
			AppState.setGridSize(size);
			TabletopTool.getFrame().refresh();
		}
	}

	public static class DownloadRemoteLibraryAction extends DefaultClientAction {
		private final URL url;

		public DownloadRemoteLibraryAction(URL url) {
			this.url = url;
		}

		@Override
		public void execute(ActionEvent arg0) {
			if (!TabletopTool.confirm("confirm.downloadRemoteLibrary", url)) {
				return;
			}
			final RemoteFileDownloader downloader = new RemoteFileDownloader(url, TabletopTool.getFrame());
			new SwingWorker<Object, Object>() {
				@Override
				protected Object doInBackground() throws Exception {
					try {
						File dataFile = downloader.read();
						if (dataFile == null) {
							// Canceled
							return null;
						}
						// Success
						String libraryName = FileUtil.getNameWithoutExtension(url);
						AppSetup.installLibrary(libraryName, dataFile.toURI().toURL());
					} catch (IOException e) {
						log.error("Could not download remote library: " + e, e);
					}
					return null;
				}

				@Override
				protected void done() {
				}
			}.execute();
		}
	}

	private static final int QUICK_MAP_ICON_SIZE = 25;

	public static class QuickMapAction extends AdminClientAction {
		private MD5Key assetId;

		public QuickMapAction(String name, File imagePath) {
			try {
				Asset asset = new Asset(name, FileUtils.readFileToByteArray(imagePath));
				assetId = asset.getId();

				// Make smaller
				BufferedImage iconImage = new BufferedImage(QUICK_MAP_ICON_SIZE, QUICK_MAP_ICON_SIZE, Transparency.OPAQUE);
				Image image = TabletopTool.getThumbnailManager().getThumbnail(imagePath);

				Graphics2D g = iconImage.createGraphics();
				g.drawImage(image, 0, 0, QUICK_MAP_ICON_SIZE, QUICK_MAP_ICON_SIZE, null);
				g.dispose();

				putValue(Action.SMALL_ICON, new ImageIcon(iconImage));
				putValue(Action.NAME, name);

				// Put it in the cache for easy access
				AssetManager.putAsset(asset);

				// But don't use up any extra memory
				AssetManager.removeAsset(asset.getId());
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			getActionList().add(this);
		}

		@Override
		public void execute(java.awt.event.ActionEvent e) {
			runBackground(new Runnable() {
				@Override
				public void run() {
					Asset asset = AssetManager.getAsset(assetId);

					Zone zone = ZoneFactory.createZone();
					zone.setBackgroundPaint(new DrawableTexturePaint(asset.getId()));
					zone.setName(asset.getName());

					TabletopTool.addZone(zone);
				}
			});
		}
	};

	public static final Action NEW_MAP = new AdminClientAction() {
		{
			init("action.newMap");
		}

		@Override
		public void execute(java.awt.event.ActionEvent e) {
			runBackground(new Runnable() {
				@Override
				public void run() {
					Zone zone = ZoneFactory.createZone();
					MapPropertiesDialog newMapDialog = new MapPropertiesDialog(TabletopTool.getFrame());
					newMapDialog.setZone(zone);

					newMapDialog.setVisible(true);

					if (newMapDialog.getStatus() == MapPropertiesDialog.Status.OK) {
						TabletopTool.addZone(zone);
					}
				}
			});
		}
	};

	public static final Action EDIT_MAP = new AdminClientAction() {
		{
			init("action.editMap");
		}

		@Override
		public void execute(java.awt.event.ActionEvent e) {
			runBackground(new Runnable() {
				@Override
				public void run() {
					Zone zone = TabletopTool.getFrame().getCurrentZoneRenderer().getZone();
					MapPropertiesDialog newMapDialog = new MapPropertiesDialog(TabletopTool.getFrame());
					newMapDialog.setZone(zone);
					newMapDialog.setVisible(true);
					// Too many things can change to send them 1 by 1 to the client... just resend the zone
//					TabletopTool.serverCommand().setBoard(zone.getId(), zone.getMapAssetId(), zone.getBoardX(), zone.getBoardY());
					TabletopTool.serverCommand().removeZone(zone.getId());
					TabletopTool.serverCommand().putZone(zone);
					TabletopTool.getFrame().getCurrentZoneRenderer().flush();
				}
			});
		}
	};

	public static final Action GATHER_DEBUG_INFO = new DefaultClientAction() {
		{
			init("action.gatherDebugInfo");
		}

		@Override
		public void execute(java.awt.event.ActionEvent e) {
			SysInfo.createAndShowGUI((String) getValue(Action.NAME));
		}
	};

	public static final Action ADD_RESOURCE_TO_LIBRARY = new DefaultClientAction() {
		{
			init("action.addIconSelector");
		}

		@Override
		public void execute(ActionEvent e) {
			runBackground(new Runnable() {
				@Override
				public void run() {
					AddResourceDialog dialog = new AddResourceDialog(TabletopTool.getFrame());
					dialog.setVisible(true);
				}
			});
		}
	};

	public static final Action EXIT = new DefaultClientAction() {
		{
			init("action.exit");
		}

		@Override
		public void execute(ActionEvent ae) {
			if (!TabletopTool.getFrame().confirmClose()) {
				return;
			} else {
				TabletopTool.getFrame().closingMaintenance();
			}
		}
	};

	/**
	 * Toggle the drawing of measurements.
	 */
	public static final Action TOGGLE_DRAW_MEASUREMENTS = new DefaultClientAction() {
		{
			init("action.toggleDrawMeasurements");
		}

		@Override
		public boolean isSelected() {
			return TabletopTool.getFrame().isPaintDrawingMeasurement();
		}

		@Override
		public void execute(ActionEvent ae) {
			TabletopTool.getFrame().setPaintDrawingMeasurement(!TabletopTool.getFrame().isPaintDrawingMeasurement());
		}
	};

	/**
	 * Toggle drawing straight lines at double width on the line tool.
	 */
	public static final Action TOGGLE_DOUBLE_WIDE = new DefaultClientAction() {
		{
			init("action.toggleDoubleWide");
		}

		@Override
		public boolean isSelected() {
			return AppState.useDoubleWideLine();
		}

		@Override
		public void execute(ActionEvent ae) {
			AppState.setUseDoubleWideLine(!AppState.useDoubleWideLine());
			if (TabletopTool.getFrame() != null && TabletopTool.getFrame().getCurrentZoneRenderer() != null)
				TabletopTool.getFrame().getCurrentZoneRenderer().repaint();
		}
	};

	public static class ToggleWindowAction extends ClientAction {
		private final MTFrame mtFrame;

		public ToggleWindowAction(MTFrame mtFrame) {
			this.mtFrame = mtFrame;
			init(mtFrame.getPropertyName());
		}

		@Override
		public boolean isSelected() {
			return TabletopTool.getFrame().getFrame(mtFrame).isVisible();
		}

		@Override
		public boolean isAvailable() {
			return true;
		}

		@Override
		public void execute(ActionEvent event) {
			DockableFrame frame = TabletopTool.getFrame().getFrame(mtFrame);
			if (frame.isVisible()) {
				TabletopTool.getFrame().getDockingManager().hideFrame(mtFrame.name());
			} else {
				TabletopTool.getFrame().getDockingManager().showFrame(mtFrame.name());
			}
		}
	}

	private static List<ClientAction> actionList;

	private static List<ClientAction> getActionList() {
		if (actionList == null) {
			actionList = new ArrayList<ClientAction>();
		}
		return actionList;
	}

	public static void updateActions() {
		for (ClientAction action : actionList) {
			action.setEnabled(action.isAvailable());
		}
		TabletopTool.getFrame().getToolbox().updateTools();
	}

	public static abstract class ClientAction extends AbstractAction {
		public void init(String key) {
			init(key, true);
		}

		public void init(String key, boolean addMenuShortcut) {
			I18N.setAction(key, this, addMenuShortcut);
			getActionList().add(this);
		}

		/**
		 * This convenience function returns the KeyStroke that represents the
		 * accelerator key used by the Action. This function can return
		 * <code>null</code> because not all Actions have an associated
		 * accelerator key defined, but it is currently only called by methods
		 * that reference the {CUT,COPY,PASTE}_TOKEN Actions.
		 * 
		 * @return KeyStroke associated with the Action or <code>null</code>
		 */
		public final KeyStroke getKeyStroke() {
			return (KeyStroke) getValue(Action.ACCELERATOR_KEY);
		}

		public abstract boolean isAvailable();

		public boolean isSelected() {
			return false;
		}

		@Override
		public final void actionPerformed(ActionEvent e) {
			execute(e);
			// System.out.println(getValue(Action.NAME));
			updateActions();
		}

		public abstract void execute(ActionEvent e);

		public void runBackground(final Runnable r) {
			new Thread() {
				@Override
				public void run() {
					r.run();

					updateActions();
				}
			}.start();
		}
	}

	/**
	 * This class simply provides an implementation for
	 * <code>isAvailable()</code> that returns <code>true</code> if the current
	 * player is a GM.
	 */
	public static abstract class AdminClientAction extends ClientAction {
		@Override
		public boolean isAvailable() {
			return TabletopTool.getPlayer().isGM();
		}
	}

	/**
	 * This class simply provides an implementation for
	 * <code>isAvailable()</code> that returns <code>true</code> if the current
	 * player is a GM and there is a ZoneRenderer current.
	 */
	public static abstract class ZoneAdminClientAction extends AdminClientAction {
		@Override
		public boolean isAvailable() {
			return super.isAvailable() && TabletopTool.getFrame().getCurrentZoneRenderer() != null;
		}
	}

	/**
	 * This class simply provides an implementation for
	 * <code>isAvailable()</code> that returns <code>true</code>.
	 */
	public static abstract class DefaultClientAction extends ClientAction {
		@Override
		public boolean isAvailable() {
			return true;
		}
	}

	/**
	 * This class provides an action that displays a url from I18N
	 */
	public static class OpenUrlAction extends DefaultClientAction {
		public OpenUrlAction(String key) {
			// The init() method will load the "key", "key.accel", and "key.description".
			// The value of "key" will be used as the menu text, the accelerator is not used,
			// and the description will be the destination URL.  We also configure "key.icon"
			// to be the value of SMALL_ICON.  Only the Help menu uses these objects and
			// only the Help menu expects that field to be set...
			init(key);
			try {
				Image img = ImageUtil.getImage(I18N.getString(key + ".icon"));
				img = ImageUtil.createCompatibleImage(img, 16, 16, null);
				putValue(Action.SMALL_ICON, new ImageIcon(img));
			} catch (Exception e) {
				// Apparently the image is not available.
			}
		}

		@Override
		public void execute(ActionEvent e) {
			if (getValue(Action.SHORT_DESCRIPTION) != null)
				TabletopTool.showDocument((String) getValue(Action.SHORT_DESCRIPTION));
		}
	}

	/**
	 * This class simply provides an implementation for
	 * <code>isAvailable()</code> that returns <code>true</code> if the system
	 * property T3_DEV is not set to "false". This allows it to contain the
	 * version number of the compatible build for debugging purposes. For
	 * example, if I'm working on a patch to 1.3.b54, I can set T3_DEV to
	 * 1.3.b54 in order to test it against a 1.3.b54 client.
	 */
	@SuppressWarnings("serial")
	public static abstract class DeveloperClientAction extends ClientAction {
		@Override
		public boolean isAvailable() {
			return System.getProperty("T3_DEV") != null && !"false".equals(System.getProperty("T3_DEV"));
		}
	}

	public static class OpenMRUCampaign extends AbstractAction {
		private final File campaignFile;

		public OpenMRUCampaign(File file, int position) {
			campaignFile = file;
			String label = position + " " + campaignFile.getName();
			putValue(Action.NAME, label);

			if (position <= 9) {
				int keyCode = KeyStroke.getKeyStroke(Integer.toString(position)).getKeyCode();
				putValue(Action.MNEMONIC_KEY, keyCode);
			}
			// Use the saved campaign thumbnail as a tooltip
			File thumbFile = PersistenceUtil.getCampaignThumbnailFile(campaignFile.getName());
			String htmlTip;

			if (thumbFile.exists()) {
				URL url = null;
				try {
					url = thumbFile.toURI().toURL();
				} catch (MalformedURLException e) {
					// Can this even happen?
					TabletopTool.showWarning("Converting File to URL threw an exception?!", e);
					return;
				}
				htmlTip = "<html><img src=\"" + url.toExternalForm() + "\"></html>";
				// The above should really be something like:
				// htmlTip = new Node("html").addChild("img").attr("src", thumbFile.toURI().toURL()).end();
				// The idea being that each method returns a proper value that allows them to be chained.
			} else {
				htmlTip = I18N.getText("msg.info.noCampaignPreview");
			}

			/*
			 * There is some extra space appearing to the right of the images,
			 * which sounds similar to what was reported in this bug (bottom
			 * half): http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5047379
			 * Removing the mnemonic will remove this extra space.
			 */
			putValue(Action.SHORT_DESCRIPTION, htmlTip);
		}

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (TabletopTool.isCampaignDirty() && !TabletopTool.confirm("msg.confirm.loseChanges"))
				return;
			AppActions.loadCampaign(campaignFile);
		}
	}
}
