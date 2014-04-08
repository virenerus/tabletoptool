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
package com.t3.model.grid;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.KeyStroke;

import com.t3.client.TabletopTool;
import com.t3.client.tool.PointerTool;
import com.t3.model.CellPoint;
import com.t3.model.MovementKey;
import com.t3.model.TokenFootprint;
import com.t3.model.ZonePoint;

public class GridlessGrid extends Grid {
	private static List<TokenFootprint> footprintList;

	// @formatter:off
	private static final GridCapabilities GRID_CAPABILITIES= new GridCapabilities() {
		@Override
		public boolean isPathingSupported() {return false;}
		@Override
		public boolean isSnapToGridSupported() {return false;}
		@Override
		public boolean isPathLineSupported() {return false;}
		@Override
		public boolean isSecondDimensionAdjustmentSupported() {return false;}
		@Override
		public boolean isCoordinatesSupported() {return false;}
	};
	// @formatter:on

	private static final int[] FACING_ANGLES = new int[] { -135, -90, -45, 0, 45, 90, 135, 180 };

	@Override
	public List<TokenFootprint> getFootprints() {
		if (footprintList == null) {
			try {
				footprintList = loadFootprints("gridlessGridFootprints.xml");
			} catch (IOException ioe) {
				TabletopTool.showError("GridlessGrid.error.notLoaded", ioe);
			}
		}
		return footprintList;
	}

	@Override
	public int[] getFacingAngles() {
		return FACING_ANGLES;
	}

	/*
	 * May as well use the same keys as for the square grid...
	 */
	@Override
	public void installMovementKeys(PointerTool callback, Map<KeyStroke, Action> actionMap) {
		if (movementKeys == null) {
			movementKeys = new HashMap<KeyStroke, Action>(18); // This is 13/0.75, rounded up
			Rectangle r = getFootprint(null).getBounds(this);
			movementKeys.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD7, 0), new MovementKey(callback, -r.width, -r.height));
			movementKeys.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD8, 0), new MovementKey(callback, 0, -r.height));
			movementKeys.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD9, 0), new MovementKey(callback, r.width, -r.height));
			movementKeys.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4, 0), new MovementKey(callback, -r.width, 0));
//			movementKeys.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD5, 0), new MovementKey(callback, 0, 0));
			movementKeys.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD6, 0), new MovementKey(callback, r.width, 0));
			movementKeys.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, 0), new MovementKey(callback, -r.width, r.height));
			movementKeys.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD2, 0), new MovementKey(callback, 0, r.height));
			movementKeys.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD3, 0), new MovementKey(callback, r.width, r.height));
			movementKeys.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), new MovementKey(callback, -r.width, 0));
			movementKeys.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), new MovementKey(callback, r.width, 0));
			movementKeys.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), new MovementKey(callback, 0, -r.height));
			movementKeys.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), new MovementKey(callback, 0, r.height));
		}
		actionMap.putAll(movementKeys);
	}

	@Override
	public void uninstallMovementKeys(Map<KeyStroke, Action> actionMap) {
		if (movementKeys != null) {
			for (KeyStroke key : movementKeys.keySet()) {
				actionMap.remove(key);
			}
		}
	}

	@Override
	public Rectangle getBounds(CellPoint cp) {
		return new Rectangle(cp.x, cp.y, getSize(), getSize());
	}

	@Override
	public ZonePoint convert(CellPoint cp) {
		return new ZonePoint(cp.x, cp.y);
	}

	@Override
	public CellPoint convert(ZonePoint zp) {
		return new CellPoint(zp.x, zp.y);
	}

	@Override
	protected Area createCellShape(int size) {
		// Doesn't do this
		return null;
	}

	@Override
	public GridCapabilities getCapabilities() {
		return GRID_CAPABILITIES;
	}

	@Override
	public double getCellWidth() {
		return getSize();
	}

	@Override
	public double getCellHeight() {
		return getSize();
	}
}
