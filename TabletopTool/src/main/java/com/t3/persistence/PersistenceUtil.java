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
package com.t3.persistence;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.jidesoft.utils.StringUtils;
import com.t3.CodeTimer;
import com.t3.MD5Key;
import com.t3.client.AppConstants;
import com.t3.client.AppUtil;
import com.t3.client.TabletopTool;
import com.t3.client.ui.Scale;
import com.t3.client.ui.zone.PlayerView;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.guid.GUID;
import com.t3.image.ImageUtil;
import com.t3.model.Asset;
import com.t3.model.AssetManager;
import com.t3.model.LookupTable;
import com.t3.model.MacroButtonProperties;
import com.t3.model.Token;
import com.t3.model.Zone;
import com.t3.model.campaign.Campaign;
import com.t3.model.campaign.CampaignProperties;
import com.t3.swing.SwingUtil;
import com.t3.util.ImageManager;
import com.t3.util.StringUtil;
import com.t3.util.guidreference.ZoneReference;
import com.t3.xstreamversioned.version.SerializationVersion;
import com.thoughtworks.xstream.converters.ConversionException;

/**
 * @author trevor
 */
public class PersistenceUtil {
	private static final Logger log = Logger.getLogger(PersistenceUtil.class);

	private static final String ASSET_DIR = "assets/"; //$NON-NLS-1$

	static {
		PackedFile.init(AppUtil.getAppHome("tmp")); //$NON-NLS-1$
	}

	@SerializationVersion(0)
	public static class PersistedMap {
		public Zone zone;
		public Map<MD5Key, Asset> assetMap = new HashMap<MD5Key, Asset>();
		public String tabletopToolVersion;
	}

	@SerializationVersion(0)
	public static class PersistedCampaign {
		public Campaign campaign;
		public Map<MD5Key, Asset> assetMap = new HashMap<MD5Key, Asset>();
		public ZoneReference currentZone;
		public Scale currentView;
		public String tabletopToolVersion;
	}

	public static void saveMap(Zone z, File mapFile) throws IOException {
		PersistedMap pMap = new PersistedMap();
		pMap.zone = z;

		// Save all assets in active use (consolidate duplicates)
		Set<MD5Key> allAssetIds = z.getAllAssetIds();
		for (MD5Key key : allAssetIds) {
			// Put in a placeholder
			pMap.assetMap.put(key, null);
		}
		PackedFile pakFile = null;
		try {
			pakFile = new PackedFile(mapFile);
			saveAssets(z.getAllAssetIds(), pakFile);
			pakFile.setContent(pMap);
			pakFile.save();
		} finally {
			if (pakFile != null)
				pakFile.close();
		}
	}

	public static PersistedMap loadMap(File mapFile) {
		PackedFile pakFile = null;
		try {
			pakFile = new PackedFile(mapFile);

			PersistedMap persistedMap = (PersistedMap) pakFile.getContent();

			// Now load up any images that we need
			loadAssets(persistedMap.assetMap.keySet(), pakFile);

			// FJE We only want the token's graphical data, so loop through all tokens and
			// destroy all properties and macros.  Keep some fields, though.  Since that type
			// of object editing doesn't belong here, we just call Token.imported() and let
			// that method Do The Right Thing.
			for (Iterator<Token> iter = persistedMap.zone.getAllTokens().iterator(); iter.hasNext();) {
				Token token = iter.next();
				token.imported();
			}
			// XXX FJE This doesn't work the way I want it to.  But doing this the Right Way
			// is too much work right now. :-}
			Zone z = persistedMap.zone;
			String n = fixupZoneName(z.getName());
			z.setName(n);
			z.imported(); // Resets creation timestamp and init panel, among other things
			z.optimize(); // Collapses overlaid or redundant drawables
			return persistedMap;
		} catch (IOException ioe) {
			TabletopTool.showError("PersistenceUtil.error.mapRead", ioe);
		} finally {
			if (pakFile != null)
				pakFile.close();
		}
		return null;
	}

	/**
	 * Determines whether the incoming map name is unique. If it is, it's
	 * returned as-is. If it's not unique, a newly generated name is returned.
	 * 
	 * @param n
	 *            name from imported map
	 * @return new name to use for the map
	 */
	private static String fixupZoneName(String n) {
		List<Zone> zones = TabletopTool.getCampaign().getZones();
		for (Zone zone : zones) {
			if (zone.getName().equals(n)) {
				String count = n.replaceFirst("Import (\\d+) of.*", "$1"); //$NON-NLS-1$
				Integer next = 1;
				try {
					next = StringUtil.parseInteger(count) + 1;
					n = n.replaceFirst("Import \\d+ of", "Import " + next + " of"); //$NON-NLS-1$
				} catch (ParseException e) {
					n = "Import " + next + " of " + n; //$NON-NLS-1$
				}
			}
		}
		return n;
	}

	public static void saveCampaign(Campaign campaign, File campaignFile) throws IOException {
		CodeTimer saveTimer; // FJE Previously this was 'private static' -- why?
		saveTimer = new CodeTimer("CampaignSave");
		saveTimer.setThreshold(5);
		saveTimer.setEnabled(log.isDebugEnabled()); // Don't bother keeping track if it won't be displayed...

		// Strategy: save the file to a tmp location so that if there's a failure the original file
		// won't be touched. Then once we're finished, replace the old with the new.
		File tmpDir = AppUtil.getTmpDir();
		File tmpFile = new File(tmpDir.getAbsolutePath(), campaignFile.getName());
		if (tmpFile.exists())
			tmpFile.delete();

		PackedFile pakFile = null;
		try {
			pakFile = new PackedFile(tmpFile);
			// Configure the meta file (this is for legacy support)
			PersistedCampaign persistedCampaign = new PersistedCampaign();

			persistedCampaign.campaign = campaign;

			// Keep track of the current view
			ZoneRenderer currentZoneRenderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			if (currentZoneRenderer != null) {
				persistedCampaign.currentZone = new ZoneReference(currentZoneRenderer.getZone());
				persistedCampaign.currentView = currentZoneRenderer.getZoneScale();
			}
			// Save all assets in active use (consolidate duplicates between maps)
			saveTimer.start("Collect all assets");
			Set<MD5Key> allAssetIds = campaign.getAllAssetIds();
			for (MD5Key key : allAssetIds) {
				// Put in a placeholder; all we really care about is the MD5Key for now...
				persistedCampaign.assetMap.put(key, null);
			}
			saveTimer.stop("Collect all assets");

			// And store the asset elsewhere
			saveTimer.start("Save assets");
			saveAssets(allAssetIds, pakFile);
			saveTimer.stop("Save assets");

			try {
				saveTimer.start("Set content");
				pakFile.setContent(persistedCampaign);
				saveTimer.stop("Set content");

				saveTimer.start("Save");
				pakFile.save();
				saveTimer.stop("Save");
			} catch (OutOfMemoryError oom) {
				/*
				 * This error is normally because the heap space has been
				 * exceeded while trying to save the campaign. Since TabletopTool
				 * caches the images used by the current Zone, and since the
				 * VersionManager must keep the XML for objects in memory in
				 * order to apply transforms to them, the memory usage can spike
				 * very high during the save() operation. A common solution is
				 * to switch to an empty map and perform the save from there;
				 * this causes TabletopTool to unload any images that it may have had
				 * cached and this can frequently free up enough memory for the
				 * save() to work. We'll tell the user all this right here and
				 * then fail the save and they can try again.
				 */
				saveTimer.start("OOM Close");
				pakFile.close(); // Have to close the tmpFile first on some OSes
				pakFile = null;
				tmpFile.delete(); // Delete the temporary file
				saveTimer.stop("OOM Close");
				if (log.isDebugEnabled()) {
					log.debug(saveTimer);
				}
				TabletopTool.showError("msg.error.failedSaveCampaignOOM");
				return;
			}
		} finally {
			saveTimer.start("Close");
			IOUtils.closeQuietly(pakFile);
			saveTimer.stop("Close");
			pakFile = null;
		}

		// Copy to the new location
		// Not the fastest solution in the world if renameTo() fails, but worth the safety net it provides
		saveTimer.start("Backup");
		File bakFile = new File(tmpDir.getAbsolutePath(), campaignFile.getName() + ".bak");
		bakFile.delete();
		if (campaignFile.exists()) {
			if (!campaignFile.renameTo(bakFile)) {
				saveTimer.start("Backup campaignFile");
				FileUtil.copyFile(campaignFile, bakFile);
				campaignFile.delete();
				saveTimer.stop("Backup campaignFile");
			}
		}
		if (!tmpFile.renameTo(campaignFile)) {
			saveTimer.start("Backup tmpFile");
			FileUtil.copyFile(tmpFile, campaignFile);
			tmpFile.delete();
			saveTimer.stop("Backup tmpFile");
		}
		if (bakFile.exists())
			bakFile.delete();
		saveTimer.stop("Backup");

		// Save the campaign thumbnail
		saveTimer.start("Thumbnail");
		saveCampaignThumbnail(campaignFile.getName());
		saveTimer.stop("Thumbnail");

		if (log.isDebugEnabled()) {
			log.debug(saveTimer);
		}
	}

	/*
	 * A public function because I think it should be called when a campaign is
	 * opened as well so if it is opened then closed without saving, there is
	 * still a preview created; however, the rendering of the campaign appears
	 * to complete after AppActions.loadCampaign returns, causing the preview to
	 * always appear as black if this method is called from within loadCampaign.
	 * Either need to find another place to call saveCampaignThumbnail upon
	 * opening, or code to delay it's call until the render is complete. =P
	 */
	static public void saveCampaignThumbnail(String fileName) {
		BufferedImage screen = TabletopTool.takeMapScreenShot(new PlayerView(TabletopTool.getPlayer().getRole()));
		if (screen == null)
			return;

		Dimension imgSize = new Dimension(screen.getWidth(null), screen.getHeight(null));
		SwingUtil.constrainTo(imgSize, 200, 200);

		BufferedImage thumb = new BufferedImage(imgSize.width, imgSize.height, BufferedImage.TYPE_INT_BGR);
		Graphics2D g2d = thumb.createGraphics();
		g2d.drawImage(screen, 0, 0, imgSize.width, imgSize.height, null);
		g2d.dispose();

		File thumbFile = getCampaignThumbnailFile(fileName);

		try {
			ImageIO.write(thumb, "jpg", thumbFile);
		} catch (IOException ioe) {
			TabletopTool.showError("msg.error.failedSaveCampaignPreview", ioe);
		}
	}

	/**
	 * Gets a file pointing to where the campaign's thumbnail image should be.
	 * 
	 * @param fileName
	 *            The campaign's file name.
	 */
	public static File getCampaignThumbnailFile(String fileName) {
		return new File(AppUtil.getAppHome("campaignthumbs"), fileName + ".jpg");
	}

	public static PersistedCampaign loadCampaign(File campaignFile) throws IOException {
		PersistedCampaign persistedCampaign = null;

		// Try the new way first
		PackedFile pakFile = null;
		try {
			pakFile = new PackedFile(campaignFile);

			try {
				persistedCampaign = (PersistedCampaign) pakFile.getContent();
			} catch (ConversionException ce) {
				// Ignore the exception and check for "campaign == null" below...
				TabletopTool.showError("PersistenceUtil.error.campaignVersion", ce);
			}
			if (persistedCampaign != null) {
				// Now load up any images that we need
				// Note that the values are all placeholders
				Set<MD5Key> allAssetIds = persistedCampaign.assetMap.keySet();
				loadAssets(allAssetIds, pakFile);
				for (Zone zone : persistedCampaign.campaign.getZones()) {
					zone.optimize();
				}
				return persistedCampaign;
			}
		} catch (OutOfMemoryError oom) {
			TabletopTool.showError("Out of memory while reading campaign.", oom);
			return null;
		} catch (RuntimeException rte) {
			TabletopTool.showError("PersistenceUtil.error.campaignRead", rte);
		} catch (Error e) {
			TabletopTool.showError("PersistenceUtil.error.unknown", e);
		} finally {
			if (pakFile != null)
				pakFile.close();
		}
		if (persistedCampaign == null)
			TabletopTool.showWarning("PersistenceUtil.warn.campaignNotLoaded");
		return persistedCampaign;
	}

	public static BufferedImage getTokenThumbnail(File file) throws Exception {
		PackedFile pakFile = new PackedFile(file);
		BufferedImage thumb;
		try {
			thumb = null;
			if (pakFile.hasFile(Token.FILE_THUMBNAIL)) {
				try(InputStream is = pakFile.getFileAsInputStream(Token.FILE_THUMBNAIL)){
					thumb = ImageIO.read(is);
				}
			}
		} finally {
			pakFile.close();
		}
		return thumb;
	}

	public static void saveToken(Token token, File file) throws IOException {
		saveToken(token, file, false);
	}

	public static void saveToken(Token token, File file, boolean doWait) throws IOException {
		// Thumbnail
		BufferedImage image = null;
		if (doWait)
			image = ImageManager.getImageAndWait(token.getImageAssetId());
		else
			image = ImageManager.getImage(token.getImageAssetId());

		Dimension sz = new Dimension(image.getWidth(), image.getHeight());
		SwingUtil.constrainTo(sz, 50);
		BufferedImage thumb = new BufferedImage(sz.width, sz.height, BufferedImage.TRANSLUCENT);
		Graphics2D g = thumb.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(image, 0, 0, sz.width, sz.height, null);
		g.dispose();

		PackedFile pakFile = null;
		try {
			pakFile = new PackedFile(file);
			saveAssets(token.getAllImageAssets(), pakFile);
			pakFile.putFile(Token.FILE_THUMBNAIL, ImageUtil.imageToBytes(thumb, "png"));
			pakFile.setContent(token);
			pakFile.save();
		} finally {
			if (pakFile != null)
				pakFile.close();
		}
	}

	public static Token loadToken(File file) {
		PackedFile pakFile = null;
		Token token = null;
		try {
			pakFile = new PackedFile(file);

			token = (Token) pakFile.getContent();
			loadAssets(token.getAllImageAssets(), pakFile);
		} catch (ConversionException ce) {
			TabletopTool.showError("PersistenceUtil.error.tokenVersion", ce);
		} catch (IOException ioe) {
			TabletopTool.showError("PersistenceUtil.error.tokenRead", ioe);
		}
		if (pakFile != null)
			pakFile.close();
		return token;
	}

	public static Token loadToken(URL url) throws IOException {
		// Create a temporary file from the downloaded URL
		GUID guid = new GUID();
		File newFile = new File(PackedFile.getTmpDir(guid.toString()), guid + ".url");
		FileUtils.copyURLToFile(url, newFile);
		Token token = loadToken(newFile);
		newFile.delete();
		return token;
	}

	private static void loadAssets(Collection<MD5Key> assetIds, PackedFile pakFile) {
		List<Asset> addToServer = new ArrayList<Asset>(assetIds.size());

		for (MD5Key key : assetIds) {
			if (key == null)
				continue;

			if (!AssetManager.hasAsset(key)) {
				String pathname = ASSET_DIR + key;
				Asset asset = null;
				
				try {
					asset = (Asset) pakFile.getFileObject(pathname); // XML deserialization
					
					//this fixes a bug where asset objects were serialized without their extension
					if(asset.getId()==null) {
						String imageDataPath=null;
						for(String path:pakFile.getPaths()) {
							if(path.startsWith(pathname+'.')) {
								imageDataPath=path;
								break;
							}
						}
						
						if(imageDataPath==null)
							continue;
						else {
							String ext=imageDataPath.substring(imageDataPath.lastIndexOf('.')+1);
							if(org.apache.commons.lang3.StringUtils.isEmpty(ext))
								continue;
							else
								asset=new Asset(key, ext);
						}
						
					}
				} catch (Exception e) {
					// Do nothing.
					log.error("Exception while handling asset '" + pathname + "'", e);
					continue;
				}
				
				// If the asset was marked as "broken" then ignore it completely.  The end
				// result is that MT will attempt to load it from a repository again, as normal.
				if ("broken".equals(asset.getName())) {
					log.warn("Reference to 'broken' asset '" + pathname + "' not restored.");
					ImageManager.flushImage(asset);
					continue;
				}
				// pre 1.3b52 campaign files stored the image data directly in the asset serialization.
				// New XStreamConverter creates empty byte[] for image.
				if (asset.getImage() == null || asset.getImage().length < 4) {
					String ext = asset.getImageExtension();
					pathname = pathname + "." + (StringUtil.isEmpty(ext) ? "dat" : ext);
					try(InputStream is = pakFile.getFileAsInputStream(pathname)){
						asset.setImage(IOUtils.toByteArray(is));
					} catch (FileNotFoundException fnf) {
						log.error("Image data for '" + pathname + "' not found?!", fnf);
						continue;
					} catch (Exception e) {
						log.error("While reading image data for '" + pathname + "'", e);
						continue;
					}
				}
				AssetManager.putAsset(asset);
				addToServer.add(asset);
			}
		}
		if (!addToServer.isEmpty()) {
			// Isn't this the same as (TabletopTool.getServer() == null) ?  And won't there always
			// be a server?  Even if we don't start one explicitly, TabletopTool keeps a server
			// running in the background all the time (called a "personal server") so that the rest
			// of the code is consistent with regard to client<->server operations...
			boolean server = !TabletopTool.isHostingServer() && !TabletopTool.isPersonalServer();
			if (server) {
				if (TabletopTool.isDevelopment())
					TabletopTool.showInformation("Please report this:  (!isHostingServer() && !isPersonalServer()) == true");
				// If we are remotely installing this token, we'll need to send the image data to the server.
				for (Asset asset : addToServer) {
					TabletopTool.serverCommand().putAsset(asset);
				}
			}
			addToServer.clear();
		}
	}

	private static void saveAssets(Collection<MD5Key> assetIds, PackedFile pakFile) throws IOException {
		for (MD5Key assetId : assetIds) {
			if (assetId == null)
				continue;

			// And store the asset elsewhere
			// As of 1.3.b64, assets are written in binary to allow them to be readable
			// when a campaign file is unpacked.
			Asset asset = AssetManager.getAsset(assetId);
			if (asset == null) {
				log.error("AssetId " + assetId + " not found while saving?!");
				continue;
			}
			pakFile.putFile(ASSET_DIR + assetId + "." + asset.getImageExtension(), asset.getImage());
			pakFile.putFile(ASSET_DIR + assetId, asset); // Does not write the image
		}
	}

	private static void clearAssets(PackedFile pakFile) throws IOException {
		for (String path : pakFile.getPaths()) {
			if (path.startsWith(ASSET_DIR) && !path.equals(ASSET_DIR))
				pakFile.removeFile(path);
		}
	}

	public static CampaignProperties loadLegacyCampaignProperties(File file) throws IOException {
		if (!file.exists())
			throw new FileNotFoundException();

		try(FileInputStream in = new FileInputStream(file)) {
			return loadCampaignProperties(in);
		}
	}

	public static CampaignProperties loadCampaignProperties(InputStream in) throws IOException {
		CampaignProperties props = null;
		try {
			props = (CampaignProperties) Persister.newInstance().fromXML(new InputStreamReader(in, "UTF-8"));
		} catch (ConversionException ce) {
			TabletopTool.showError("PersistenceUtil.error.campaignPropertiesVersion", ce);
		}
		return props;
	}

	public static CampaignProperties loadCampaignProperties(File file) {
		PackedFile pakFile = new PackedFile(file);
		CampaignProperties props = null;
		try {
			props = (CampaignProperties) pakFile.getContent();
			loadAssets(props.getAllImageAssets(), pakFile);
		} catch (ConversionException ce) {
			TabletopTool.showError("PersistenceUtil.error.campaignPropertiesVersion", ce);
		} catch (IOException ioe) {
			TabletopTool.showError("Could not load campaign properties", ioe);
		}
		return props;
	}

	public static void saveCampaignProperties(Campaign campaign, File file) throws IOException {
		// Put this in FileUtil
		if (file.getName().indexOf(".") < 0) {
			file = new File(file.getAbsolutePath() + AppConstants.CAMPAIGN_PROPERTIES_FILE_EXTENSION);
		}
		PackedFile pakFile = null;
		try {
			pakFile = new PackedFile(file);
			clearAssets(pakFile);
			saveAssets(campaign.getCampaignProperties().getAllImageAssets(), pakFile);
			pakFile.setContent(campaign.getCampaignProperties());
			pakFile.save();
		} finally {
			if (pakFile != null)
				pakFile.close();
		}
	}

	// Macro import/export support
	public static MacroButtonProperties loadLegacyMacro(File file) throws IOException {
		if (!file.exists())
			throw new FileNotFoundException();

		try(FileInputStream in = new FileInputStream(file)) {
			return loadMacro(in);
		}
	}

	public static MacroButtonProperties loadMacro(InputStream in) {
		MacroButtonProperties mbProps = null;
		try {
			mbProps = (MacroButtonProperties) Persister.newInstance().fromXML(new InputStreamReader(in, "UTF-8"));
		} catch (ConversionException ce) {
			TabletopTool.showError("PersistenceUtil.error.macroVersion", ce);
		} catch (IOException ioe) {
			TabletopTool.showError("PersistenceUtil.error.macroRead", ioe);
		}
		return mbProps;
	}

	public static MacroButtonProperties loadMacro(File file) throws IOException {
		PackedFile pakFile = null;
		try {
			pakFile = new PackedFile(file);

			MacroButtonProperties macroButton = (MacroButtonProperties) pakFile.getContent();
			return macroButton;
		} catch (IOException e) {
			if (pakFile != null)
				pakFile.close();
			pakFile = null;
			return loadLegacyMacro(file);
		} finally {
			if (pakFile != null)
				pakFile.close();
		}
	}

	public static void saveMacro(MacroButtonProperties macroButton, File file) throws IOException {
		// Put this in FileUtil
		if (file.getName().indexOf(".") < 0) {
			file = new File(file.getAbsolutePath() + AppConstants.MACRO_FILE_EXTENSION);
		}
		PackedFile pakFile = null;
		try {
			pakFile = new PackedFile(file);
			pakFile.setContent(macroButton);
			pakFile.save();
		} finally {
			if (pakFile != null)
				pakFile.close();
		}
	}

	public static List<MacroButtonProperties> loadLegacyMacroSet(File file) throws IOException {
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		try(FileInputStream in = new FileInputStream(file)) {
			return loadMacroSet(in);
		}
	}

	@SuppressWarnings("unchecked")
	public static List<MacroButtonProperties> loadMacroSet(InputStream in) throws IOException {
		List<MacroButtonProperties> macroButtonSet = null;
		try {
			macroButtonSet = (List<MacroButtonProperties>) Persister.newInstance().fromXML(new InputStreamReader(in, "UTF-8"));
		} catch (ConversionException ce) {
			TabletopTool.showError("PersistenceUtil.error.macrosetVersion", ce);
		}
		return macroButtonSet;
	}

	@SuppressWarnings("unchecked")
	public static List<MacroButtonProperties> loadMacroSet(File file) throws IOException {
		PackedFile pakFile = null;
		List<MacroButtonProperties> macroButtonSet = null;
		try {
			pakFile = new PackedFile(file);

			macroButtonSet = (List<MacroButtonProperties>) pakFile.getContent();
		} catch (ConversionException ce) {
			TabletopTool.showError("PersistenceUtil.error.macrosetVersion", ce);
		} catch (IOException e) {
			return loadLegacyMacroSet(file);
		} finally {
			if (pakFile != null)
				pakFile.close();
		}
		return macroButtonSet;
	}

	public static void saveMacroSet(List<MacroButtonProperties> macroButtonSet, File file) throws IOException {
		// Put this in FileUtil
		if (file.getName().indexOf(".") < 0) {
			file = new File(file.getAbsolutePath() + AppConstants.MACROSET_FILE_EXTENSION);
		}
		PackedFile pakFile = null;
		try {
			pakFile = new PackedFile(file);
			pakFile.setContent(macroButtonSet);
			pakFile.save();
		} finally {
			if (pakFile != null)
				pakFile.close();
		}
	}

	// end of Macro import/export support

	// Table import/export support
	public static LookupTable loadLegacyTable(File file) throws IOException {
		if (!file.exists())
			throw new FileNotFoundException();

		try(FileInputStream in = new FileInputStream(file)) {
			return loadTable(in);
		}
	}

	public static LookupTable loadTable(InputStream in) {
		LookupTable table = null;
		try {
			table = (LookupTable) Persister.newInstance().fromXML(new InputStreamReader(in, "UTF-8"));
		} catch (ConversionException ce) {
			TabletopTool.showError("PersistenceUtil.error.tableVersion", ce);
		} catch (IOException ioe) {
			TabletopTool.showError("PersistenceUtil.error.tableRead", ioe);
		}
		return table;
	}

	public static LookupTable loadTable(File file) {
		PackedFile pakFile = null;
		try {
			pakFile = new PackedFile(file);

			LookupTable lookupTable = (LookupTable) pakFile.getContent();
			loadAssets(lookupTable.getAllAssetIds(), pakFile);
			return lookupTable;
		} catch (ConversionException ce) {
			TabletopTool.showError("PersistenceUtil.error.tableVersion", ce);
		} catch (IOException e) {
			try {
				if (pakFile != null)
					pakFile.close();
				pakFile = null;
				return loadLegacyTable(file);
			} catch (IOException ioe) {
				TabletopTool.showError("PersistenceUtil.error.tableRead", ioe);
			}
		} finally {
			if (pakFile != null)
				pakFile.close();
		}
		return null;
	}

	public static void saveTable(LookupTable lookupTable, File file) throws IOException {
		// Put this in FileUtil
		if (file.getName().indexOf(".") < 0) {
			file = new File(file.getAbsolutePath() + AppConstants.TABLE_FILE_EXTENSION);
		}
		PackedFile pakFile = null;
		try {
			pakFile = new PackedFile(file);
			pakFile.setContent(lookupTable);
			saveAssets(lookupTable.getAllAssetIds(), pakFile);
			pakFile.save();
		} finally {
			if (pakFile != null)
				pakFile.close();
		}
	}
}
