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
package com.t3.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.log4j.Logger;

import com.t3.GUID;
import com.t3.MD5Key;
import com.t3.client.AppUtil;
import com.t3.client.TabletopTool;
import com.t3.image.ImageUtil;
import com.t3.language.I18N;
import com.t3.model.Zone.Layer;
import com.t3.model.campaign.Campaign;
import com.t3.model.grid.Grid;
import com.t3.transferable.TokenTransferData;
import com.t3.util.ImageManager;
import com.t3.util.StringUtil;

/**
 * This object represents the placeable objects on a map. For example an icon that represents a character would exist as
 * an {@link Asset} (the image itself) and a location and scale.
 */
public class Token extends BaseModel {
	private static final Logger log = Logger.getLogger(Token.class);

	private GUID id = new GUID();

	public static final String FILE_EXTENSION = "rptok";
	public static final String FILE_THUMBNAIL = "thumbnail";

	public static final String NAME_USE_FILENAME = "Use Filename";
	public static final String NAME_USE_CREATURE = "Use \"Creature\"";

	public static final String NUM_INCREMENT = "Increment";
	public static final String NUM_RANDOM = "Random";

	public static final String NUM_ON_NAME = "Name";
	public static final String NUM_ON_GM = "GM Name";
	public static final String NUM_ON_BOTH = "Both";

	private boolean beingImpersonated = false;
	private GUID exposedAreaGUID;

	public enum TokenShape {
		TOP_DOWN("Top down"), CIRCLE("Circle"), SQUARE("Square");

		private String displayName;

		private TokenShape(String displayName) {
			this.displayName = displayName;
		}

		@Override
		public String toString() {
			return displayName;
		}
	}

	public enum Type {
		PC, NPC
	}

	public static final Comparator<Token> NAME_COMPARATOR = new Comparator<Token>() {
		@Override
		public int compare(Token o1, Token o2) {
			return o1.getName().compareToIgnoreCase(o2.getName());
		}
	};

	private final Map<String, MD5Key> imageAssetMap;
	private String currentImageAsset;

	private int x;
	private int y;
	private int z;

	private int anchorX;
	private int anchorY;

	private double sizeScale = 1;

	private int lastX;
	private int lastY;
	private Path<? extends AbstractPoint> lastPath;

	private boolean snapToScale = true; // Whether the scaleX and scaleY represent snap-to-grid measurements

	// These are the original image width and height
	private int width;
	private int height;

	private double scaleX = 1;
	private double scaleY = 1;

	private Map<Class<? extends Grid>, GUID> sizeMap;

	private boolean snapToGrid = true; // Whether the token snaps to the current grid or is free floating

	private boolean isVisible = true;
	private boolean visibleOnlyToOwner = false;

	private String name;
	private Set<String> ownerList;
	
	private boolean ownedByAll;
	
	private transient Zone zone;

	private TokenShape tokenShape;
	private Type tokenType;
	private Zone.Layer layer;

	private String propertyType = Campaign.DEFAULT_TOKEN_PROPERTY_TYPE;

	private Integer facing = null;

	private Integer haloColorValue;
	private transient Color haloColor;

	private Integer visionOverlayColorValue;
	private transient Color visionOverlayColor;

	private boolean isFlippedX;
	private boolean isFlippedY;

	private MD5Key charsheetImage;
	private MD5Key portraitImage;

	private List<AttachedLightSource> lightSourceList;
	private String sightType;
	private boolean hasSight;

	private String label;

	/**
	 * The notes that are displayed for this token.
	 */
	private String notes;

	private String gmNotes;

	private String gmName;

	/**
	 * The states this token has
	 */
	private Set<String> states;

	/**
	 * The bars and its values of this token
	 */
	private HashMap<String, Float> bars;
	

	/**
	 * Properties
	 */
	private CaseInsensitiveMap<String,Object> propertyMap;

	private Map<Integer, MacroButtonProperties> macroPropertiesMap;

	private Map<String, String> speechMap;

	public enum ChangeEvent {
		name, MACRO_CHANGED
	}

	public Token(Token token) {
		this(token.name, token.getImageAssetId());
		currentImageAsset = token.currentImageAsset;

		x = token.x;
		y = token.y;
		z = token.z;
		
		// These properties shouldn't be transferred, they are more transient and relate to token history, not to new tokens
		//		lastX = token.lastX;
		//		lastY = token.lastY;
		//		lastPath = token.lastPath;

		snapToScale = token.snapToScale;
		width = token.width;
		height = token.height;
		scaleX = token.scaleX;
		scaleY = token.scaleY;
		facing = token.facing;
		tokenShape = token.tokenShape;
		tokenType = token.tokenType;
		haloColorValue = token.haloColorValue;

		snapToGrid = token.snapToGrid;
		isVisible = token.isVisible;
		visibleOnlyToOwner = token.visibleOnlyToOwner;
		name = token.name;
		notes = token.notes;
		gmName = token.gmName;
		gmNotes = token.gmNotes;
		label = token.label;

		isFlippedX = token.isFlippedX;
		isFlippedY = token.isFlippedY;

		layer = token.layer;

		visionOverlayColor = token.visionOverlayColor;

		charsheetImage = token.charsheetImage;
		portraitImage = token.portraitImage;
		anchorX = token.anchorX;
		anchorY = token.anchorY;
		sizeScale = token.sizeScale;
		sightType = token.sightType;
		hasSight = token.hasSight;
		propertyType = token.propertyType;

		ownedByAll = token.ownedByAll;
		if (token.ownerList != null) {
			ownerList = new HashSet<String>();
			ownerList.addAll(token.ownerList);
		}
		if (token.lightSourceList != null) {
			lightSourceList = new ArrayList<AttachedLightSource>(token.lightSourceList);
		}
		if (token.states != null)
			states.addAll(token.states);
		if (token.bars != null)
			bars.putAll(token.bars);
		if (token.propertyMap != null) {
			getPropertyMap().clear();
			getPropertyMap().putAll(token.propertyMap);
		}
		if (token.macroPropertiesMap != null) {
			macroPropertiesMap = new HashMap<Integer, MacroButtonProperties>(token.macroPropertiesMap);
		}
		if (token.speechMap != null) {
			speechMap = new HashMap<String, String>(token.speechMap);
		}
		if (token.imageAssetMap != null) {
			imageAssetMap.putAll(token.imageAssetMap);
		}
		if (token.sizeMap != null) {
			sizeMap = new HashMap<Class<? extends Grid>, GUID>(token.sizeMap);
		}
		exposedAreaGUID = token.exposedAreaGUID;
	}

	public Token() {
		imageAssetMap = new HashMap<String, MD5Key>();
	}

	public Token(MD5Key assetID) {
		this("", assetID);
	}

	public Token(String name, MD5Key assetId) {
		this.name = name;
		states = new HashSet<String>();
		bars = new HashMap<String, Float>();
		imageAssetMap = new HashMap<String, MD5Key>();

		// NULL key is the default
		imageAssetMap.put(null, assetId);
	}

	/**
	 * This token object has just been imported on a map and needs to have most of its internal data wiped clean. This
	 * prevents a token from being imported that makes use of the wrong property types, vision types, ownership, macros,
	 * and so on. Basically anything related to the presentation of the token on-screen + the two notes fields is kept.
	 * Note that the sightType is set to the campaign's default sight type, and the property type is not changed at all.
	 * This will usually be correct since the default sight is what most tokens have and the property type is probably
	 * specific to the campaign -- hopefully the properties were set up before the token/map was imported.
	 */
	public void imported() {
		// anchorX, anchorY?
		beingImpersonated = false;
		// hasSight?
		// height?
		lastPath = null;
		lastX = lastY = 0;
		// lightSourceList?
//		macroPropertiesMap = null;
		ownerList = null;
//		propertyMapCI = null;
//		propertyType = "Basic";
		sightType = TabletopTool.getCampaign().getCampaignProperties().getDefaultSightType();
//		state = null;
	}

	public void setHasSight(boolean hasSight) {
		this.hasSight = hasSight;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isMarker() {
		return isStamp() && (!StringUtil.isEmpty(notes) || !StringUtil.isEmpty(gmNotes) || portraitImage != null);
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getGMNotes() {
		return gmNotes;
	}

	public void setGMNotes(String notes) {
		gmNotes = notes;
	}

	public String getGMName() {
		return gmName;
	}

	public void setGMName(String name) {
		gmName = name;
	}

	public boolean hasHalo() {
		return haloColorValue != null;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setHaloColor(Color color) {
		if (color != null) {
			haloColorValue = color.getRGB();
		} else {
			haloColorValue = null;
		}
		haloColor = color;
	}

	public Color getHaloColor() {
		if (haloColor == null && haloColorValue != null) {
			haloColor = new Color(haloColorValue);
		}
		return haloColor;
	}

	public boolean isObjectStamp() {
		return getLayer() == Zone.Layer.OBJECT;
	}

	public boolean isGMStamp() {
		return getLayer() == Zone.Layer.GM;
	}

	public boolean isBackgroundStamp() {
		return getLayer() == Zone.Layer.BACKGROUND;
	}

	public boolean isStamp() {
		return getLayer()!=Layer.TOKEN;
	}

	public boolean isToken() {
		return getLayer() == Zone.Layer.TOKEN;
	}

	public TokenShape getShape() {
		return tokenShape != null ? tokenShape : TokenShape.SQUARE;
	}

	public void setShape(TokenShape type) {
		this.tokenShape = type;
	}

	public Type getType() {
		return tokenType != null ? tokenType : Type.NPC;
	}

	public void setType(Type type) {
		tokenType = type;
		if (type == Type.PC)
			hasSight = true;
	}

	public Zone.Layer getLayer() {
		return layer != null ? layer : Zone.Layer.TOKEN;
	}

	public void setLayer(Zone.Layer layer) {
		this.layer = layer;
	}

	public boolean hasFacing() {
		return facing != null;
	}

	public void setFacing(Integer facing) {
		while (facing != null && (facing > 180 || facing < -179)) {
			facing += facing > 180 ? -360 : 0;
			facing += facing < -179 ? 360 : 0;
		}
		this.facing = facing;
	}

	public Integer getFacing() {
		return facing;
	}

	public boolean getHasSight() {
		return hasSight;
	}

	public void addLightSource(LightSource source, Direction direction) {
		if (lightSourceList == null) {
			lightSourceList = new ArrayList<AttachedLightSource>();
		}
		if (!lightSourceList.contains(source))
			lightSourceList.add(new AttachedLightSource(source, direction));
	}

	public void removeLightSourceType(LightSource.Type lightType) {
		if (lightSourceList != null) {
			for (ListIterator<AttachedLightSource> i = lightSourceList.listIterator(); i.hasNext();) {
				AttachedLightSource als = i.next();
				LightSource lightSource = TabletopTool.getCampaign().getLightSource(als.getLightSourceId());
				if (lightSource != null && lightSource.getType() == lightType)
					i.remove();
			}
		}
	}

	public void removeGMAuras() {
		if (lightSourceList != null) {
			for (ListIterator<AttachedLightSource> i = lightSourceList.listIterator(); i.hasNext();) {
				AttachedLightSource als = i.next();
				LightSource lightSource = TabletopTool.getCampaign().getLightSource(als.getLightSourceId());
				if (lightSource != null) {
					List<Light> lights = lightSource.getLightList();
					for (Light light : lights) {
						if (light != null && light.isGM())
							i.remove();
					}
				}
			}
		}
	}

	public void removeOwnerOnlyAuras() {
		if (lightSourceList != null) {
			for (ListIterator<AttachedLightSource> i = lightSourceList.listIterator(); i.hasNext();) {
				AttachedLightSource als = i.next();
				LightSource lightSource = TabletopTool.getCampaign().getLightSource(als.getLightSourceId());
				if (lightSource != null) {
					List<Light> lights = lightSource.getLightList();
					for (Light light : lights) {
						if (light.isOwnerOnly())
							i.remove();
					}
				}
			}
		}
	}

	public boolean hasOwnerOnlyAuras() {
		if (lightSourceList != null) {
			for (ListIterator<AttachedLightSource> i = lightSourceList.listIterator(); i.hasNext();) {
				AttachedLightSource als = i.next();
				LightSource lightSource = TabletopTool.getCampaign().getLightSource(als.getLightSourceId());
				if (lightSource != null) {
					List<Light> lights = lightSource.getLightList();
					for (Light light : lights) {
						if (light.isOwnerOnly())
							return true;
					}
				}
			}
		}
		return false;
	}

	public boolean hasGMAuras() {
		if (lightSourceList != null) {
			for (ListIterator<AttachedLightSource> i = lightSourceList.listIterator(); i.hasNext();) {
				AttachedLightSource als = i.next();
				LightSource lightSource = TabletopTool.getCampaign().getLightSource(als.getLightSourceId());
				if (lightSource != null) {
					List<Light> lights = lightSource.getLightList();
					for (Light light : lights) {
						if (light.isGM())
							return true;
					}
				}
			}
		}
		return false;
	}

	public boolean hasLightSourceType(LightSource.Type lightType) {
		if (lightSourceList != null) {
			for (ListIterator<AttachedLightSource> i = lightSourceList.listIterator(); i.hasNext();) {
				AttachedLightSource als = i.next();
				LightSource lightSource = TabletopTool.getCampaign().getLightSource(als.getLightSourceId());
				if (lightSource != null && lightSource.getType() == lightType)
					return true;
			}
		}
		return false;
	}

	public void removeLightSource(LightSource source) {
		if (lightSourceList == null) {
			return;
		}
		for (ListIterator<AttachedLightSource> i = lightSourceList.listIterator(); i.hasNext();) {
			AttachedLightSource als = i.next();
			if (als != null && als.getLightSourceId() != null && als.getLightSourceId().equals(source.getId())) {
				i.remove();
			}
		}
	}

	//My Addition
	public void clearLightSources() {
		if (lightSourceList == null) {
			return;
		}
		lightSourceList = null;
	}

	//End My Addition

	public boolean hasLightSource(LightSource source) {
		if (lightSourceList == null) {
			return false;
		}
		for (ListIterator<AttachedLightSource> i = lightSourceList.listIterator(); i.hasNext();) {
			AttachedLightSource als = i.next();
			if (als != null && als.getLightSourceId() != null && als.getLightSourceId().equals(source.getId())) {
				return true;
			}
		}
		return false;
	}

	public boolean hasLightSources() {
		return lightSourceList != null && !lightSourceList.isEmpty();
	}

	public List<AttachedLightSource> getLightSources() {
		return lightSourceList != null ? Collections.unmodifiableList(lightSourceList) : new LinkedList<AttachedLightSource>();
	}

	public synchronized void addOwner(String playerId) {
		this.ownedByAll=false;
		if (ownerList == null) {
			ownerList = new HashSet<String>();
		}
		ownerList.add(playerId);
	}

	public synchronized boolean hasOwners() {
		return ownedByAll || (ownerList != null && !ownerList.isEmpty());
	}

	public synchronized void removeOwner(String playerId) {
		ownedByAll=false;
		if (ownerList == null) {
			return;
		}
		ownerList.remove(playerId);
		if (ownerList.size() == 0) {
			ownerList = null;
		}
	}

	public synchronized void setOwnedByAll(boolean ownedByAll) {
		this.ownedByAll=ownedByAll;
		if (ownedByAll)
			ownerList = null;
	}

	public Set<String> getOwners() {
		return ownerList != null ? Collections.unmodifiableSet(ownerList) : new HashSet<String>();
	}

	public boolean isOwnedByAll() {
		return this.ownedByAll;
	}

	public synchronized void clearAllOwners() {
		ownerList = null;
	}

	public synchronized boolean isOwner(String playerId) {
		return this.ownedByAll || (ownerList != null && ownerList.contains(playerId));
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Token)) {
			return false;
		}
		return id.equals(((Token) o).id);
	}

	public void setZOrder(int z) {
		this.z = z;
	}

	public int getZOrder() {
		return z;
	}

	/**
	 * Set the name of this token to the provided string. There is a potential exposure of information to the player in
	 * this method: through repeated attempts to name a token they own to another name, they could determine which token
	 * names the GM is already using. Fortunately, the showError() call makes this extremely unlikely due to the
	 * interactive nature of a failure.
	 * 
	 * @param name
	 * @throws IOException
	 */
	public void setName(String name) throws IllegalArgumentException {
		//Let's see if there is another Token with that name (only if Player is not GM)
		if (!TabletopTool.getPlayer().isGM()) {// && !TabletopTool.getParser().isMacroTrusted()) { //FIXMESOON reinstate trusted macro laws?
			Zone curZone = TabletopTool.getFrame().getCurrentZoneRenderer().getZone();
			List<Token> tokensList = curZone.getTokens();

			for (int i = 0; i < tokensList.size(); i++) {
				String curTokenName = tokensList.get(i).getName();
				if (curTokenName.equalsIgnoreCase(name)) {
					TabletopTool.showError(I18N.getText("Token.error.unableToRename", name));
					throw new IllegalArgumentException("Player dropped token with duplicate name");
				}
			}
		}
		this.name = name;
		fireModelChangeEvent(new ModelChangeEvent(this, ChangeEvent.name, name));
	}

	public MD5Key getImageAssetId() {
		MD5Key assetId = imageAssetMap.get(currentImageAsset);
		if (assetId == null) {
			assetId = imageAssetMap.get(null); // default image
		}
		return assetId;
	}

	public void setImageAsset(String name, MD5Key assetId) {
		imageAssetMap.put(name, assetId);
	}

	public void setImageAsset(String name) {
		currentImageAsset = name;
	}

	public Set<MD5Key> getAllImageAssets() {
		Set<MD5Key> assetSet = new HashSet<MD5Key>(imageAssetMap.values());
		assetSet.add(charsheetImage);
		assetSet.add(portraitImage);
		assetSet.remove(null); // Clean up from any null values from above
		return assetSet;
	}

	public MD5Key getPortraitImage() {
		return portraitImage;
	}

	public void setPortraitImage(MD5Key image) {
		portraitImage = image;
	}

	public MD5Key getCharsheetImage() {
		return charsheetImage;
	}

	public void setCharsheetImage(MD5Key charsheetImage) {
		this.charsheetImage = charsheetImage;
	}

	public GUID getId() {
		return id;
	}

	public void setId(GUID id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		lastX = this.x;
		this.x = x;
	}

	public void setY(int y) {
		lastY = this.y;
		this.y = y;
	}

	public void applyMove(int xOffset, int yOffset, Path<? extends AbstractPoint> path) {
		setX(x + xOffset);
		setY(y + yOffset);
		lastPath = path;
	}

	public void setLastPath(Path<? extends AbstractPoint> path) {
		lastPath = path;
	}

	public int getLastY() {
		return lastY;
	}

	public int getLastX() {
		return lastX;
	}

	public Path<? extends AbstractPoint> getLastPath() {
		return lastPath;
	}

	public double getScaleX() {
		return scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}

	/**
	 * @return Returns the snapScale.
	 */
	public boolean isSnapToScale() {
		return snapToScale;
	}

	/**
	 * @param snapScale
	 *            The snapScale to set.
	 */
	public void setSnapToScale(boolean snapScale) {
		this.snapToScale = snapScale;
	}

	public void setVisible(boolean visible) {
		this.isVisible = visible;
	}

	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * @return the visibleOnlyToOwner
	 */
	public boolean isVisibleOnlyToOwner() {
		return visibleOnlyToOwner;
	}

	/**
	 * @param visibleOnlyToOwner
	 *            the visibleOnlyToOwner to set
	 */
	public void setVisibleOnlyToOwner(boolean visibleOnlyToOwner) {
		this.visibleOnlyToOwner = visibleOnlyToOwner;
	}

	public String getName() {
		return name != null ? name : "";
	}

	public Rectangle getBounds(Zone zone) {
		Grid grid = zone.getGrid();
		TokenFootprint footprint = getFootprint(grid);
		Rectangle footprintBounds = footprint.getBounds(grid, grid.convert(new ZonePoint(getX(), getY())));

		double w = footprintBounds.width;
		double h = footprintBounds.height;

		// Sizing
		if (!isSnapToScale()) {
			w = this.width * getScaleX();
			h = this.height * getScaleY();
		} else {
			w = footprintBounds.width * footprint.getScale() * sizeScale;
			h = footprintBounds.height * footprint.getScale() * sizeScale;
		}
		// Positioning
		if (!isSnapToGrid()) {
			footprintBounds.x = getX();
			footprintBounds.y = getY();
		} else {
			if (!isBackgroundStamp()) {
				// Center it on the footprint
				footprintBounds.x -= (w - footprintBounds.width) / 2;
				footprintBounds.y -= (h - footprintBounds.height) / 2;
			} else {
//	        	footprintBounds.x -= zone.getGrid().getSize()/2;
//	        	footprintBounds.y -= zone.getGrid().getSize()/2;
			}
		}
		footprintBounds.width = (int) w; // perhaps make this a double
		footprintBounds.height = (int) h;

		// Offset
		footprintBounds.x += anchorX;
		footprintBounds.y += anchorY;
		return footprintBounds;
	}

	public String getSightType() {
		return sightType;
	}

	public void setSightType(String sightType) {
		this.sightType = sightType;
	}

	/**
	 * @return Returns the size.
	 */
	public TokenFootprint getFootprint(Grid grid) {
		return grid.getFootprint(getSizeMap().get(grid.getClass()));
	}

	public TokenFootprint setFootprint(Grid grid, TokenFootprint footprint) {
		return grid.getFootprint(getSizeMap().put(grid.getClass(), footprint.getId()));
	}

	private Map<Class<? extends Grid>, GUID> getSizeMap() {
		if (sizeMap == null) {
			sizeMap = new HashMap<Class<? extends Grid>, GUID>();
		}
		return sizeMap;
	}

	public boolean isSnapToGrid() {
		return snapToGrid;
	}

	public void setSnapToGrid(boolean snapToGrid) {
		this.snapToGrid = snapToGrid;
	}
	
	/**
	 * Get if this token has a certain state
	 * @param state the name of the state you want to check for
	 * @return if the token has the state
	 */
	public boolean hasState(String state) {
		return states.contains(state);
	}
	
	/**
	 * Get the value of a bar of this token
	 * @param barName the name of the bar you want to get
	 * @return the value of the bar ior null if the bar is not visible
	 */
	public Float getBar(String barName) {
		return bars.get(barName);
	}
	
	/**
	 * This adds or removes a state from this token
	 * @param state the state you want to set
	 * @param value if the token should have the state or not
	 * @return if the token had the state before the change
	 */
	public boolean setState(String state, boolean value) {
		if(value)
			return !states.add(state);
		else
			return states.remove(state);
	}
	
	/**
	 * This sets a bar of this token
	 * @param barName the name of the bar you want to set
	 * @param value the value the bar should have between 0 and 1 or null if it should be hidden
	 * @return the bar value the token had before the change
	 */
	public Float setBar(String barName, Float value) {
		if(value==null)
			return bars.remove(barName);
		else
			return bars.put(barName, value);
	}
	

	public void resetProperty(String key) {
		getPropertyMap().remove(key);
	}

	public Object setProperty(String key, Object value) {
		return getPropertyMap().put(key, value);
	}

	public Object getProperty(String key) {
		Object value = getPropertyMap().get(key);

//		// Short name ?
//		if (value == null) {
//			for (EditTokenProperty property : TabletopTool.getCampaign().getCampaignProperties().getTokenPropertyList(getPropertyType())) {
//				if (property.getShortName().equals(key)) {
//					value = getPropertyMap().get(property.getShortName().toUpperCase());
//				}
//			}
//		}
		return value;
	}

	/**
	 * Returns all property names, all in lowercase.
	 * 
	 * @return
	 */
	public Set<String> getPropertyNames() {
		return getPropertyMap().keySet();
	}

	private CaseInsensitiveMap<String,Object> getPropertyMap() {
		if (propertyMap == null) {
			propertyMap = new CaseInsensitiveMap<String,Object>();
		}
		return propertyMap;
	}

	public int getMacroNextIndex() {
		if (macroPropertiesMap == null) {
			macroPropertiesMap = new HashMap<Integer, MacroButtonProperties>();
		}
		Set<Integer> indexSet = macroPropertiesMap.keySet();
		int maxIndex = 0;
		for (int index : indexSet) {
			if (index > maxIndex)
				maxIndex = index;
		}
		return maxIndex + 1;
	}

	public Map<Integer, MacroButtonProperties> getMacroPropertiesMap(boolean secure) {
		if (macroPropertiesMap == null) {
			macroPropertiesMap = new HashMap<Integer, MacroButtonProperties>();
		}
		if (secure && !AppUtil.playerOwns(this)) {
			return new HashMap<Integer, MacroButtonProperties>();
		} else {
			return macroPropertiesMap;
		}
	}

	public MacroButtonProperties getMacro(int index, boolean secure) {
		return getMacroPropertiesMap(secure).get(index);
	}

	// avoid this; it loads the first macro with this label, but there could be more than one macro with that label
	public MacroButtonProperties getMacro(String label, boolean secure) {
		Set<Integer> keys = getMacroPropertiesMap(secure).keySet();
		for (int key : keys) {
			MacroButtonProperties prop = macroPropertiesMap.get(key);
			if (prop.getLabel().equals(label)) {
				return prop;
			}
		}
		return null;
	}

	public List<MacroButtonProperties> getMacroList(boolean secure) {
		Set<Integer> keys = getMacroPropertiesMap(secure).keySet();
		List<MacroButtonProperties> list = new ArrayList<MacroButtonProperties>();
		for (int key : keys) {
			list.add(macroPropertiesMap.get(key));
		}
		return list;
	}

	public void replaceMacroList(List<MacroButtonProperties> newMacroList) {
		// used by the token edit dialog, which will handle resetting panels and putting token to zone
		macroPropertiesMap.clear();
		for (MacroButtonProperties macro : newMacroList) {
			if (macro.getLabel() == null || macro.getLabel().trim().length() == 0 || macro.getCommand().trim().length() == 0) {
				continue;
			}
			macroPropertiesMap.put(macro.getIndex(), macro);

			// Allows the token macro panels to update only if a macro changes
			fireModelChangeEvent(new ModelChangeEvent(this, ChangeEvent.MACRO_CHANGED, id));
		}
	}

	public List<String> getMacroNames(boolean secure) {
		List<String> list = new ArrayList<String>();
		for (Entry<Integer, MacroButtonProperties> entry : getMacroPropertiesMap(secure).entrySet())
			list.add(entry.getValue().getLabel());
		return list;
	}

	public boolean hasMacros(boolean secure) {
		if (!getMacroPropertiesMap(secure).isEmpty()) {
			return true;
		}
		return false;
	}

	public void saveMacroButtonProperty(MacroButtonProperties prop) {
		getMacroPropertiesMap(false).put(prop.getIndex(), prop);
		TabletopTool.getFrame().resetTokenPanels();
		TabletopTool.serverCommand().putToken(TabletopTool.getFrame().getCurrentZoneRenderer().getZone().getId(), this);

		// Lets the token macro panels update only if a macro changes
		fireModelChangeEvent(new ModelChangeEvent(this, ChangeEvent.MACRO_CHANGED, id));
	}

	public void deleteMacroButtonProperty(MacroButtonProperties prop) {
		getMacroPropertiesMap(false).remove(prop.getIndex());
		TabletopTool.getFrame().resetTokenPanels();
		TabletopTool.serverCommand().putToken(TabletopTool.getFrame().getCurrentZoneRenderer().getZone().getId(), this);

		// Lets the token macro panels update only if a macro changes
		fireModelChangeEvent(new ModelChangeEvent(this, ChangeEvent.MACRO_CHANGED, id));
	}

	public void setSpeechMap(Map<String, String> map) {
		getSpeechMap().clear();
		getSpeechMap().putAll(map);
	}

	public Set<String> getSpeechNames() {
		return getSpeechMap().keySet();
	}

	public String getSpeech(String key) {
		return getSpeechMap().get(key);
	}

	public void setSpeech(String key, String value) {
		getSpeechMap().put(key, value);
	}

	private Map<String, String> getSpeechMap() {
		if (speechMap == null) {
			speechMap = new HashMap<String, String>();
		}
		return speechMap;
	}

	/**
	 * This will remove all states from this token
	 */
	public void removeAllStates() {
		states.clear();
	}
	
	/**
	 * This will remove all bars from this token
	 */
	public void removeAllBars() {
		bars.clear();
	}

	/** @return Getter for notes */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param aNotes
	 *            Setter for notes
	 */
	public void setNotes(String aNotes) {
		notes = aNotes;
	}

	public boolean isFlippedY() {
		return isFlippedY;
	}

	public void setFlippedY(boolean isFlippedY) {
		this.isFlippedY = isFlippedY;
	}

	public boolean isFlippedX() {
		return isFlippedX;
	}

	public void setFlippedX(boolean isFlippedX) {
		this.isFlippedX = isFlippedX;
	}

	public Color getVisionOverlayColor() {
		if (visionOverlayColor == null && visionOverlayColorValue != null) {
			visionOverlayColor = new Color(visionOverlayColorValue);
		}
		return visionOverlayColor;
	}

	public void setVisionOverlayColor(Color color) {
		if (color != null) {
			visionOverlayColorValue = color.getRGB();
		} else {
			visionOverlayColorValue = null;
		}
		visionOverlayColor = color;
	}

	@Override
	public String toString() {
		return "Token: " + id;
	}

	public void setAnchor(int x, int y) {
		anchorX = x;
		anchorY = y;
	}

	public Point getAnchor() {
		return new Point(anchorX, anchorY);
	}

	public double getSizeScale() {
		return sizeScale;
	}

	public void setSizeScale(double scale) {
		sizeScale = scale;
	}

	/**
	 * Convert the token into a hash map. This is used to ship all of the properties for the token to other apps that do
	 * need access to the <code>Token</code> class.
	 * 
	 * @return A map containing the properties of the token.
	 */
	public TokenTransferData toTransferData() {
		TokenTransferData td = new TokenTransferData();
		td.setName(name);
		td.setPlayers(ownerList);
		td.setVisible(isVisible);
		td.setLocation(new Point(x, y));
		td.setFacing(facing);

		// Set the properties
		td.put(TokenTransferData.ID, id.toString());
		td.put(TokenTransferData.ASSET_ID, imageAssetMap.get(null));
		td.put(TokenTransferData.Z, z);
		td.put(TokenTransferData.SNAP_TO_SCALE, snapToScale);
		td.put(TokenTransferData.WIDTH, scaleX);
		td.put(TokenTransferData.HEIGHT, scaleY);
		td.put(TokenTransferData.SNAP_TO_GRID, snapToGrid);
		td.put(TokenTransferData.OWNER_TYPE, ownedByAll);
		td.put(TokenTransferData.VISIBLE_OWNER_ONLY, visibleOnlyToOwner);
		td.put(TokenTransferData.TOKEN_TYPE, tokenShape);
		td.put(TokenTransferData.NOTES, notes);
		td.put(TokenTransferData.GM_NOTES, gmNotes);
		td.put(TokenTransferData.GM_NAME, gmName);
		td.put(TokenTransferData.STATES, states);
		td.put(TokenTransferData.BARS, bars);

		// Create the image from the asset and add it to the map
		Image image = ImageManager.getImageAndWait(imageAssetMap.get(null));
		if (image != null)
			td.setToken(new ImageIcon(image)); // Image icon makes it serializable.
		return td;
	}

	/**
	 * Constructor to create a new token from a transfer object containing its property values. This is used to read in
	 * a new token from other apps that don't have access to the <code>Token</code> class.
	 * 
	 * @param td
	 *            Read the values from this transfer object.
	 */
	public Token(TokenTransferData td) {
		imageAssetMap = new HashMap<String, MD5Key>();
		states = new HashSet<String>();
		if(td.get(TokenTransferData.STATES)!=null)
			states.addAll((Set<String>)td.get(TokenTransferData.STATES));
		bars = new HashMap<String, Float>();
		if(td.get(TokenTransferData.BARS)!=null)
			bars.putAll((HashMap<String, Float>)td.get(TokenTransferData.BARS));
		if (td.getLocation() != null) {
			x = td.getLocation().x;
			y = td.getLocation().y;
		}
		snapToScale = getBoolean(td, TokenTransferData.SNAP_TO_SCALE, true);
		scaleX = getInt(td, TokenTransferData.WIDTH, 1);
		scaleY = getInt(td, TokenTransferData.HEIGHT, 1);
		snapToGrid = getBoolean(td, TokenTransferData.SNAP_TO_GRID, true);
		isVisible = td.isVisible();
		visibleOnlyToOwner = getBoolean(td, TokenTransferData.VISIBLE_OWNER_ONLY, false);
		name = td.getName();
		ownerList = td.getPlayers();
		ownedByAll = getBoolean(td, TokenTransferData.OWNER_TYPE, ownerList == null ? true : false);
		tokenShape = (TokenShape)td.get(TokenTransferData.TOKEN_TYPE);
		facing = td.getFacing();
		notes = (String) td.get(TokenTransferData.NOTES);
		gmNotes = (String) td.get(TokenTransferData.GM_NOTES);
		gmName = (String) td.get(TokenTransferData.GM_NAME);

		// Get the image and portrait for the token
		Asset asset = createAssetFromIcon(td.getToken());
		if (asset != null)
			imageAssetMap.put(null, asset.getId());
		asset = createAssetFromIcon((ImageIcon) td.get(TokenTransferData.PORTRAIT));
		if (asset != null)
			portraitImage = asset.getId();

		// Get the macros
		@SuppressWarnings("unchecked")
		Map<String, Map<String, String>> macros = (Map<String, Map<String, String>>) td.get(TokenTransferData.MACROS);
		for (Map<String, String> macroButtonProperties : macros.values()) {
			MacroButtonProperties mbp = new MacroButtonProperties(this, macroButtonProperties);
			getMacroPropertiesMap(false).put(mbp.getIndex(), mbp);
		}

		// Get all of the non tabletoptool specific state
		for (String key : td.keySet()) {
			if (key.startsWith(TokenTransferData.T3PREFIX))
				continue;
			setProperty(key, td.get(key));
		}
	}

	private Asset createAssetFromIcon(ImageIcon icon) {
		if (icon == null)
			return null;

		// Make sure there is a buffered image for it
		Image image = icon.getImage();
		if (!(image instanceof BufferedImage)) {
			image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), Transparency.TRANSLUCENT);
			Graphics2D g = ((BufferedImage) image).createGraphics();
			icon.paintIcon(null, g, 0, 0);
		}
		// Create the asset
		Asset asset = null;
		try {
			asset = new Asset(name, ImageUtil.imageToBytes((BufferedImage) image));
			if (!AssetManager.hasAsset(asset))
				AssetManager.putAsset(asset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return asset;
	}

	/**
	 * Get an integer value from the map or return the default value
	 * 
	 * @param map
	 *            Get the value from this map
	 * @param propName
	 *            The name of the property being read.
	 * @param defaultValue
	 *            The value for the property if it is not set in the map.
	 * @return The value for the passed property
	 */
	private static int getInt(Map<String, Object> map, String propName, int defaultValue) {
		Integer integer = (Integer) map.get(propName);
		if (integer == null)
			return defaultValue;
		return integer.intValue();
	}

	/**
	 * Get a boolean value from the map or return the default value
	 * 
	 * @param map
	 *            Get the value from this map
	 * @param propName
	 *            The name of the property being read.
	 * @param defaultValue
	 *            The value for the property if it is not set in the map.
	 * @return The value for the passed property
	 */
	private static boolean getBoolean(Map<String, Object> map, String propName, boolean defaultValue) {
		Boolean bool = (Boolean) map.get(propName);
		if (bool == null)
			return defaultValue;
		return bool.booleanValue();
	}

	public static boolean isTokenFile(String filename) {
		return filename != null && filename.toLowerCase().endsWith(FILE_EXTENSION);
	}

	public Icon getIcon(int width, int height) {
		ImageIcon icon = new ImageIcon(ImageManager.getImageAndWait(getImageAssetId()));
		Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		return new ImageIcon(image);
	}

	public boolean isBeingImpersonated() {
		return beingImpersonated;
	}

	public void setBeingImpersonated(boolean bool) {
		beingImpersonated = bool;
	}

	public void deleteMacroGroup(String macroGroup, Boolean secure) {
		List<MacroButtonProperties> tempMacros = new ArrayList<MacroButtonProperties>(getMacroList(true));
		for (MacroButtonProperties nextProp : tempMacros) {
			if (macroGroup.equals(nextProp.getGroup())) {
				getMacroPropertiesMap(secure).remove(nextProp.getIndex());
			}
		}
		TabletopTool.getFrame().resetTokenPanels();
		TabletopTool.serverCommand().putToken(TabletopTool.getFrame().getCurrentZoneRenderer().getZone().getId(), this);
	}

	public void deleteAllMacros(Boolean secure) {
		List<MacroButtonProperties> tempMacros = new ArrayList<MacroButtonProperties>(getMacroList(true));
		for (MacroButtonProperties nextProp : tempMacros) {
			getMacroPropertiesMap(secure).remove(nextProp.getIndex());
		}
		TabletopTool.getFrame().resetTokenPanels();
		TabletopTool.serverCommand().putToken(TabletopTool.getFrame().getCurrentZoneRenderer().getZone().getId(), this);
	}

	public static final Comparator<Token> COMPARE_BY_NAME = new Comparator<Token>() {
		@Override
		public int compare(Token o1, Token o2) {
			if (o1 == null || o2 == null) {
				return 0;
			}
			return o1.getName().compareTo(o2.getName());
		}
	};
	public static final Comparator<Token> COMPARE_BY_ZORDER = new Comparator<Token>() {
		@Override
		public int compare(Token o1, Token o2) {
			if (o1 == null || o2 == null) {
				return 0;
			}
			return o1.z < o2.z ? -1 : o1.z == o2.z ? 0 : 1;
		}
	};

	@Override
	protected Object readResolve() {
		super.readResolve();
		// 1.3 b77
		if (exposedAreaGUID == null) {
			exposedAreaGUID = new GUID();
		}
		return this;
	}

	/**
	 * @param exposedAreaGUID
	 *            the exposedAreaGUID to set
	 */
	public void setExposedAreaGUID(GUID exposedAreaGUID) {
		this.exposedAreaGUID = exposedAreaGUID;
	}

	/**
	 * @return the exposedAreaGUID
	 */
	public GUID getExposedAreaGUID() {
		return exposedAreaGUID;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
}
