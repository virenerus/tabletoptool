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
package com.t3.client.tool;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.t3.client.AppState;
import com.t3.client.ScreenPoint;
import com.t3.client.TabletopTool;
import com.t3.client.ui.forms.AdjustBoardControlPanel;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.image.ImageUtil;
import com.t3.model.Zone;
import com.t3.model.ZonePoint;
import com.t3.model.drawing.DrawablePaint;
import com.t3.model.drawing.DrawableTexturePaint;
import com.t3.model.grid.Grid;
import com.t3.util.ImageManager;
import com.t3.util.StringUtil;

/**
 * Allows user to re-position the background map (internally called the
 * 'board'). This entire class should be 'transient'... should this be in the
 * var names, or in the reference to the BoardTool instance?
 */
public class BoardTool extends DefaultTool {
	private static final long serialVersionUID = 98389912045059L;

	// Context variables
	private static Zone zone;
	private static boolean oldShowGrid;

	// Status variables
	private static Point boardPosition = new Point(0, 0);
	private static Dimension snap = new Dimension(1, 1);

	// Action control variables
	private Point dragStart;
	private Dimension dragOffset;
	private Point boardStart;

	// UI button fields
	private final AdjustBoardControlPanel controlPanel;

	/**
	 * Initialize the panel and set up the actions.
	 */
	public BoardTool() {
		try {
			setIcon(new ImageIcon(ImageUtil.getImage("com/t3/client/image/board.png")));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		controlPanel = new AdjustBoardControlPanel();

		controlPanel.getBoardPositionXTextField().addKeyListener(new UpdateBoardListener());

		controlPanel.getBoardPositionYTextField().addKeyListener(new UpdateBoardListener());

		ActionListener enforceRules = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				enforceButtonRules();
			}
		};
		controlPanel.getSnapNoneButton().addActionListener(enforceRules);

		controlPanel.getSnapGridButton().addActionListener(enforceRules);

		controlPanel.getSnapTileButton().addActionListener(enforceRules);

		controlPanel.getCloseButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetTool();
			}
		});
	}

	@Override
	protected void installKeystrokes(Map<KeyStroke, Action> actionMap) {
		super.installKeystrokes(actionMap);

		actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), new boardPositionAction(Direction.Up));
		actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), new boardPositionAction(Direction.Left));
		actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), new boardPositionAction(Direction.Down));
		actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), new boardPositionAction(Direction.Right));
	}

	/**
	 * Figure out how big the repeating board tile image is.
	 */

	private Dimension getTileSize() {
		Zone zone = renderer.getZone();
		Dimension tileSize = null;

		if (zone != null) {
			DrawablePaint paint = zone.getBackgroundPaint();
			DrawableTexturePaint dummy = new DrawableTexturePaint();
			if (paint.getClass() == dummy.getClass()) {
				Image bgTexture = ImageManager.getImage(((DrawableTexturePaint) paint).getAsset().getId());
				tileSize = new Dimension(bgTexture.getWidth(null), bgTexture.getHeight(null));
			}
		}
		return tileSize;
	}

	/**
	 * Moves the board to the nearest snap intersection. Modifies GUI.
	 */
	private void snapBoard() {
		boardPosition.x = (boardPosition.x / snap.width) * snap.width;
		boardPosition.y = (boardPosition.y / snap.height) * snap.height;
		updateGUI();
	}

	/**
	 * Sets the snap mode with independent x/y snaps and adjusts the board
	 * position appropriately.
	 * 
	 * @param x
	 *            the new x snap amount
	 * @param y
	 *            the new y snap amount
	 */
	private void setSnap(int x, int y) {
		snap.width = x;
		snap.height = y;
		snapBoard();
	}

	private void updateGUI() {
		controlPanel.getBoardPositionXTextField().setText(Integer.toString(boardPosition.x));
		controlPanel.getBoardPositionYTextField().setText(Integer.toString(boardPosition.y));
	}

	/**
	 * Copies the current board (map image as set in "New Map/Edit Map") info to
	 * the tool so we have the appropriate starting info. Should be called each
	 * time the tool is un-hidden.
	 */
	private void copyBoardToControlPanel() {
		boardPosition.x = zone.getBoardX();
		boardPosition.y = zone.getBoardY();
		snapBoard();
		updateGUI();
	}

	private void copyControlPanelToBoard() {
		boardPosition.x = StringUtil.parseInteger(controlPanel.getBoardPositionXTextField().getText(), 0);
		boardPosition.y = StringUtil.parseInteger(controlPanel.getBoardPositionYTextField().getText(), 0);
		zone.setBoard(boardPosition);
	}

	@Override
	public String getTooltip() {
		return "tool.boardtool.tooltip";
	}

	@Override
	public String getInstructions() {
		return "tool.boardtool.instructions";
	}

	/*
	 * private double getDouble(String value, double defaultValue) { try {
	 * return value.length() > 0 ? Double.parseDouble(value.trim()) :
	 * defaultValue; } catch (NumberFormatException e) { return 0; } }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.t3.client.Tool#attachTo(com.t3.client.ZoneRenderer)
	 */
	@Override
	protected void attachTo(ZoneRenderer renderer) {
		super.attachTo(renderer);
		zone = renderer.getZone();
		copyBoardToControlPanel();
		oldShowGrid = AppState.isShowGrid();
		AppState.setShowGrid(true);

		// Find out if it is already aligned to grid or background tile, and
		// default to keeping that same alignment.
		final int offset = zone.getBoardX();
		final Dimension tileSize = getTileSize();
		final int gridSize = zone.getGrid().getSize();

		if ((tileSize != null) && ((offset % tileSize.width) == 0)) {
			setSnap(tileSize.width, tileSize.height);
			controlPanel.getSnapTileButton().setSelected(true);
		} else if ((offset % gridSize) == 0) {
			setSnap(gridSize, gridSize);
			controlPanel.getSnapGridButton().setSelected(true);
		} else {
			setSnap(1, 1);
			controlPanel.getSnapNoneButton().setSelected(true);
		}
		TabletopTool.getFrame().showControlPanel(controlPanel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tabletoptool.client.Tool#detachFrom(tabletoptool.client.ZoneRenderer)
	 */
	@Override
	protected void detachFrom(ZoneRenderer renderer) {
		TabletopTool.getFrame().hideControlPanel();
		TabletopTool.serverCommand().setBoard(zone.getId(), zone.getMapAssetId(), zone.getBoardX(), zone.getBoardY());
		AppState.setShowGrid(oldShowGrid);
		super.detachFrom(renderer);
	}

	////
	// MOUSE LISTENER

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			ZonePoint zp = new ScreenPoint(e.getX(), e.getY()).convertToZone(renderer);
			Grid grid = renderer.getZone().getGrid();
			dragStart = new Point(zp.x - grid.getOffsetX(), zp.y - grid.getOffsetY());
			boardStart = new Point(boardPosition);
			dragOffset = new Dimension(0, 0);
		} else {
			super.mousePressed(e);
		}
	}

	////
	// MOUSE MOTION LISTENER
	@Override
	public void mouseDragged(java.awt.event.MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			ZonePoint zp = new ScreenPoint(e.getX(), e.getY()).convertToZone(renderer);

			dragOffset.width = zp.x - dragStart.x;
			dragOffset.height = zp.y - dragStart.y;

			boardPosition.x = boardStart.x + dragOffset.width;
			boardPosition.y = boardStart.y + dragOffset.height;
			snapBoard();
			updateGUI();
			zone.setBoard(boardPosition);
		} else {
			super.mouseDragged(e);
		}
	}

	@Override
	public void mouseMoved(java.awt.event.MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	/**
	 * A simple enum for correlating keys with directions
	 */
	private static enum Direction {
		Left, Right, Up, Down
	};

	/**
	 * Constructs actions to attach to key-presses.
	 */
	@SuppressWarnings("serial")
	private class boardPositionAction extends AbstractAction {
		private final Direction direction;

		public boardPositionAction(Direction direction) {
			this.direction = direction;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (direction) {
			case Left:
				boardPosition.x -= snap.width;
				break;
			case Right:
				boardPosition.x += snap.width;
				break;
			case Up:
				boardPosition.y -= snap.height;
				break;
			case Down:
				boardPosition.y += snap.height;
				break;
			}
			updateGUI();
			zone.setBoard(boardPosition);
		}
	}

	////
	// ACTIONS
	private class UpdateBoardListener implements KeyListener, ChangeListener, FocusListener {
		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
			copyControlPanelToBoard();
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			copyControlPanelToBoard();
		}

		@Override
		public void focusLost(FocusEvent e) {
			copyControlPanelToBoard();
		}

		@Override
		public void focusGained(FocusEvent e) {
		}
	}

	private void enforceButtonRules() {
		if (controlPanel.getSnapGridButton().isSelected()) {
			final int gridSize = zone.getGrid().getSize();
			setSnap(gridSize, gridSize);
		} else if (controlPanel.getSnapTileButton().isSelected()) {
			final Dimension tileSize = getTileSize();
			if (tileSize != null)
				setSnap(tileSize.width, tileSize.height);
			else
				setSnap(1, 1);
		} else {
			setSnap(1, 1);
		}
		updateGUI();
		zone.setBoard(boardPosition);
	}
}
