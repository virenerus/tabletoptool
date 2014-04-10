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
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

import com.t3.client.AppActions;
import com.t3.client.AppUtil;
import com.t3.client.TabletopTool;
import com.t3.client.tool.FacingTool;
import com.t3.client.tool.PointerTool;
import com.t3.client.tool.StampTool;
import com.t3.client.ui.token.EditTokenDialog;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.language.I18N;
import com.t3.model.Direction;
import com.t3.GUID;
import com.t3.model.Light;
import com.t3.model.LightSource;
import com.t3.model.Token;
import com.t3.model.TokenFootprint;
import com.t3.model.Zone;
import com.t3.model.ZonePoint;
import com.t3.model.grid.Grid;
import com.t3.util.ImageManager;
import com.t3.util.PersistenceUtil;
import com.t3.util.TokenUtil;

public abstract class AbstractTokenPopupMenu extends JPopupMenu {
	private static final long serialVersionUID = -3741870412603226747L;

	private final ZoneRenderer renderer;
	private final Token tokenUnderMouse;
	private boolean areTokensOwned;
	int x, y;
	Set<GUID> selectedTokenSet;

	public AbstractTokenPopupMenu(Set<GUID> selectedTokenSet, int x, int y, ZoneRenderer renderer, Token tokenUnderMouse) {
		super();
		this.renderer = renderer;
		this.x = x;
		this.y = y;
		this.selectedTokenSet = selectedTokenSet;
		this.tokenUnderMouse = tokenUnderMouse;

		setOwnership();
	}

	protected boolean tokensAreOwned() {
		return areTokensOwned;
	}

	private void setOwnership() {
		areTokensOwned = true;
		if (!TabletopTool.getPlayer().isGM() && TabletopTool.getServerPolicy().useStrictTokenManagement()) {
			for (GUID tokenGUID : selectedTokenSet) {
				Token token = getRenderer().getZone().getToken(tokenGUID);

				if (!token.isOwner(TabletopTool.getPlayer().getName())) {
					areTokensOwned = false;
					break;
				}
			}
		}
	}

	protected class ShowHandoutAction extends AbstractAction {
		public ShowHandoutAction() {
			putValue(Action.NAME, "Show Handout");
			setEnabled(getTokenUnderMouse().getCharsheetImage() != null);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			AssetViewerDialog dialog = new AssetViewerDialog(getTokenUnderMouse().getName() + "'s Character Sheet", getTokenUnderMouse().getCharsheetImage());
			dialog.pack();
			dialog.setVisible(true);
		}
	}

	protected JMenu createLightSourceMenu() {
		JMenu menu = new JMenu("Light Source");

		if (tokenUnderMouse.hasLightSources()) {
			menu.add(new ClearLightAction());

			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			for (GUID tokenGUID : selectedTokenSet) {
				Token token = renderer.getZone().getToken(tokenGUID);
				if (token.hasLightSourceType(LightSource.Type.NORMAL)) {
					menu.add(new ClearLightsOnlyAction());
				}
				if (token.hasLightSourceType(LightSource.Type.AURA)) {
					menu.add(new ClearAurasOnlyAction());
				}
				if (token.hasGMAuras()) {
					menu.add(new ClearGMAurasOnlyAction());
				}
				if (token.hasOwnerOnlyAuras()) {
					menu.add(new ClearOwnerAurasOnlyAction());
				}
			}
			menu.addSeparator();
		}
		for (Entry<String, Map<GUID, LightSource>> entry : TabletopTool.getCampaign().getLightSourcesMap().entrySet()) {
			JMenu subMenu = new JMenu(entry.getKey());

			List<LightSource> lightSources = new ArrayList<LightSource>(entry.getValue().values());
			LightSource[] lightSourceList = new LightSource[entry.getValue().size()];
			lightSources.toArray(lightSourceList);
			Arrays.sort(lightSourceList);
			LIGHTSOURCES: for (LightSource lightSource : lightSourceList) {
				for (Light light : lightSource.getLightList()) {
					if (light.isGM() && !TabletopTool.getPlayer().isGM()) {
						continue LIGHTSOURCES;
					}
				}
				JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(new ToggleLightSourceAction(lightSource));
				menuItem.setSelected(tokenUnderMouse.hasLightSource(lightSource));
				subMenu.add(menuItem);
			}
			if (subMenu.getItemCount() != 0) {
				menu.add(subMenu);
			}
		}
		return menu;
	}

	protected Token getTokenUnderMouse() {
		return tokenUnderMouse;
	}

	protected JMenu createFlipMenu() {
		JMenu flipMenu = new JMenu("Flip");

		flipMenu.add(new AbstractAction() {
			{
				putValue(NAME, "Horizontal");
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				for (GUID tokenGUID : selectedTokenSet) {
					Token token = renderer.getZone().getToken(tokenGUID);
					if (token == null) {
						continue;
					}
					token.setFlippedX(!token.isFlippedX());
					renderer.flush(token);
					TabletopTool.serverCommand().putToken(renderer.getZone().getId(), token);
				}
				TabletopTool.getFrame().refresh();
			}
		});
		flipMenu.add(new AbstractAction() {
			{
				putValue(NAME, "Vertical");
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				for (GUID tokenGUID : selectedTokenSet) {
					Token token = renderer.getZone().getToken(tokenGUID);
					if (token == null) {
						continue;
					}
					token.setFlippedY(!token.isFlippedY());
					renderer.flush(token);
					TabletopTool.serverCommand().putToken(renderer.getZone().getId(), token);
				}
				TabletopTool.getFrame().refresh();
			}
		});
		return flipMenu;
	}

	protected JMenu createChangeToMenu(Zone.Layer... types) {
		JMenu changeTypeMenu = new JMenu("Change to");
		for (Zone.Layer layer : types) {
			changeTypeMenu.add(new JMenuItem(new ChangeTypeAction(layer)));
		}
		return changeTypeMenu;
	}

	protected JMenu createArrangeMenu() {
		JMenu arrangeMenu = new JMenu("Arrange");
		JMenuItem bringToFrontMenuItem = new JMenuItem("Bring to Front");
		bringToFrontMenuItem.addActionListener(new BringToFrontAction());

		JMenuItem sendToBackMenuItem = new JMenuItem("Send to Back");
		sendToBackMenuItem.addActionListener(new SendToBackAction());

		arrangeMenu.add(bringToFrontMenuItem);
		arrangeMenu.add(sendToBackMenuItem);

		return arrangeMenu;
	}

	protected JMenu createSizeMenu() {
		JMenu sizeMenu = new JMenu("Size");

		JCheckBoxMenuItem freeSize = new JCheckBoxMenuItem(new FreeSizeAction());
		freeSize.setSelected(!tokenUnderMouse.isSnapToScale());

		sizeMenu.add(freeSize);
		sizeMenu.addSeparator();

		Grid grid = renderer.getZone().getGrid();
		for (TokenFootprint footprint : grid.getFootprints()) {
			JMenuItem menuItem = new JCheckBoxMenuItem(new ChangeSizeAction(footprint));
			if (tokenUnderMouse.isSnapToScale() && tokenUnderMouse.getFootprint(grid) == footprint) {
				menuItem.setSelected(true);
			}
			sizeMenu.add(menuItem);
		}
		return sizeMenu;
	}

	protected void addGMItem(Action action) {
		if (action == null) {
			return;
		}
		if (TabletopTool.getPlayer().isGM()) {
			add(new JMenuItem(action));
		}
	}

	protected void addGMItem(JMenu menu) {
		if (menu == null) {
			return;
		}
		if (TabletopTool.getPlayer().isGM()) {
			add(menu);
		}
	}

	protected void addToggledGMItem(Action action, boolean checked) {
		if (action == null) {
			return;
		}
		if (TabletopTool.getPlayer().isGM()) {
			JCheckBoxMenuItem item = new JCheckBoxMenuItem(action);
			item.setSelected(checked);
			add(item);
		}
	}

	protected void addToggledOwnedItem(Action action, boolean checked) {
		if (action == null) {
			return;
		}
		JCheckBoxMenuItem item = new JCheckBoxMenuItem(action);
		item.setSelected(checked);
		item.setEnabled(areTokensOwned);
		add(item);
	}

	protected void addToggledItem(Action action, boolean checked) {
		if (action == null) {
			return;
		}
		JCheckBoxMenuItem item = new JCheckBoxMenuItem(action);
		item.setSelected(checked);
		add(item);
	}

	protected void addOwnedItem(Action action) {
		if (action == null) {
			return;
		}
		JMenuItem item = new JMenuItem(action);
		item.setEnabled(areTokensOwned);
		add(new JMenuItem(action));
	}

	protected void addOwnedItem(JMenu menu) {
		if (menu == null) {
			return;
		}
		menu.setEnabled(areTokensOwned);
		add(menu);
	}

	protected ZoneRenderer getRenderer() {
		return renderer;
	}

	public void showPopup(JComponent component) {
		show(component, x, y);
	}

	public class ChangeTypeAction extends AbstractAction {
		private final Zone.Layer layer;

		public ChangeTypeAction(Zone.Layer layer) {
			putValue(Action.NAME, layer.toString());
			this.layer = layer;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (GUID tokenGUID : selectedTokenSet) {
				Token token = renderer.getZone().getToken(tokenGUID);
				if (token == null) {
					continue;
				}
				token.setLayer(layer);
				switch (layer) {
				case BACKGROUND:
				case OBJECT:
					token.setShape(Token.TokenShape.TOP_DOWN);
					break;
				case TOKEN:
					Image image = ImageManager.getImage(token.getImageAssetId());
					if (image == null || image == ImageManager.TRANSFERING_IMAGE) {
						token.setShape(Token.TokenShape.TOP_DOWN);
					} else {
						token.setShape(TokenUtil.guessTokenType(image));
					}
					break;
				}
				renderer.flush(token);
				TabletopTool.serverCommand().putToken(renderer.getZone().getId(), token);
			}
			renderer.repaint();
			TabletopTool.getFrame().updateTokenTree();
		}
	}

	public class FreeSizeAction extends AbstractAction {
		public FreeSizeAction() {
			putValue(Action.NAME, tokenUnderMouse.isStamp() ? "Free Size" : "Native Size");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (GUID tokenGUID : selectedTokenSet) {
				Token token = renderer.getZone().getToken(tokenGUID);
				if (token == null) {
					continue;
				}
				token.setSnapToScale(false);
				TabletopTool.serverCommand().putToken(renderer.getZone().getId(), token);
			}
			renderer.repaint();
		}
	}

	public class CopyAction extends AbstractAction {
		public CopyAction() {
			putValue(Action.NAME, I18N.getText("action.copyTokens"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			AppActions.copyTokens(selectedTokenSet);
		}
	}

	public class CutAction extends AbstractAction {
		public CutAction() {
			putValue(Action.NAME, I18N.getText("action.cutTokens"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			AppActions.cutTokens(renderer.getZone(), selectedTokenSet);
		}
	}

	public class ToggleLightSourceAction extends AbstractAction {
		private final LightSource lightSource;

		public ToggleLightSourceAction(LightSource lightSource) {
			super(lightSource.getName());
			this.lightSource = lightSource;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			for (GUID tokenGUID : selectedTokenSet) {
				Token token = renderer.getZone().getToken(tokenGUID);
				if (token == null) {
					continue;
				}
				if (token.hasLightSource(lightSource)) {
					token.removeLightSource(lightSource);
				} else {
					token.addLightSource(lightSource, Direction.CENTER);
				}
				// Cache clearing
				renderer.flush(token);

				TabletopTool.serverCommand().putToken(renderer.getZone().getId(), token);
				renderer.getZone().putToken(token);

				renderer.repaint();
			}
		}
	}

	public class SaveAction extends AbstractAction {
		public SaveAction() {
			super("Save ...");

			if (selectedTokenSet.size() > 1) {
				setEnabled(false);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			File defaultFile = new File(tokenUnderMouse.getName());
			JFileChooser chooser = TabletopTool.getFrame().getSaveFileChooser();
			chooser.setSelectedFile(defaultFile);

			if (chooser.showSaveDialog(TabletopTool.getFrame()) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File file = chooser.getSelectedFile();

			// Auto-extension
			if (!file.getName().endsWith(Token.FILE_EXTENSION)) {
				file = new File(file.getAbsolutePath() + "." + Token.FILE_EXTENSION);
			}
			// Confirm
			if (file.exists()) {
				if (!TabletopTool.confirm("File exists, would you like to overwrite?")) {
					return;
				}
			}
			Token token = new Token(tokenUnderMouse);
			if (!TabletopTool.getPlayer().isGM()) {
				token.setGMNotes("");
			}
			try {
				PersistenceUtil.saveToken(token, file);
			} catch (IOException ioe) {
				ioe.printStackTrace();
				TabletopTool.showError("Could not save token: " + ioe);
			}
		}
	}

	public class SetFacingAction extends AbstractAction {
		public SetFacingAction() {
			super("Set Facing");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Toolbox toolbox = TabletopTool.getFrame().getToolbox();

			FacingTool tool = (FacingTool) toolbox.getTool(FacingTool.class);
			tool.init(tokenUnderMouse, renderer.getOwnedTokens(selectedTokenSet));

			toolbox.setSelectedTool(FacingTool.class);
		}
	}

	public class ClearFacingAction extends AbstractAction {
		public ClearFacingAction() {
			super("Clear Facing");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			for (GUID tokenGUID : selectedTokenSet) {
				Token token = renderer.getZone().getToken(tokenGUID);
				token.setFacing(null);
				renderer.flush(token);
				TabletopTool.serverCommand().putToken(renderer.getZone().getId(), token);
			}
			renderer.repaint();
		}
	}

	public class ClearLightsOnlyAction extends AbstractAction {
		public ClearLightsOnlyAction() {
			super("Clear Lights Only");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			for (GUID tokenGUID : selectedTokenSet) {
				Token token = renderer.getZone().getToken(tokenGUID);
				if (token.hasLightSourceType(LightSource.Type.NORMAL)) {
					token.removeLightSourceType(LightSource.Type.NORMAL);
				}
				renderer.flush(token);
				TabletopTool.serverCommand().putToken(renderer.getZone().getId(), token);
				renderer.getZone().putToken(token);
			}
			renderer.repaint();
		}
	}

	public class ClearAurasOnlyAction extends AbstractAction {
		public ClearAurasOnlyAction() {
			super("Clear Auras Only");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			for (GUID tokenGUID : selectedTokenSet) {
				Token token = renderer.getZone().getToken(tokenGUID);
				if (token.hasLightSourceType(LightSource.Type.AURA)) {
					token.removeLightSourceType(LightSource.Type.AURA);
				}
				renderer.flush(token);
				TabletopTool.serverCommand().putToken(renderer.getZone().getId(), token);
				renderer.getZone().putToken(token);
			}
			renderer.repaint();
		}
	}

	public class ClearGMAurasOnlyAction extends AbstractAction {
		public ClearGMAurasOnlyAction() {
			super("Clear GM Auras Only");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			for (GUID tokenGUID : selectedTokenSet) {
				Token token = renderer.getZone().getToken(tokenGUID);
				if (token.hasGMAuras()) {
					token.removeGMAuras();
				}
				renderer.flush(token);
				TabletopTool.serverCommand().putToken(renderer.getZone().getId(), token);
				renderer.getZone().putToken(token);
			}
			renderer.repaint();
		}
	}

	public class ClearOwnerAurasOnlyAction extends AbstractAction {
		public ClearOwnerAurasOnlyAction() {
			super("Clear Owner Auras Only");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			for (GUID tokenGUID : selectedTokenSet) {
				Token token = renderer.getZone().getToken(tokenGUID);
				if (token.hasOwnerOnlyAuras()) {
					token.removeOwnerOnlyAuras();
				}
				renderer.flush(token);
				TabletopTool.serverCommand().putToken(renderer.getZone().getId(), token);
				renderer.getZone().putToken(token);
			}
			renderer.repaint();
		}
	}

	public class ClearLightAction extends AbstractAction {
		public ClearLightAction() {
			super("Clear All");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			for (GUID tokenGUID : selectedTokenSet) {
				Token token = renderer.getZone().getToken(tokenGUID);
				token.clearLightSources();
				renderer.flush(token);
				TabletopTool.serverCommand().putToken(renderer.getZone().getId(), token);
				renderer.getZone().putToken(token);
			}
			renderer.repaint();
		}
	}

	public class SnapToGridAction extends AbstractAction {
		private final boolean snapToGrid;
		private final ZoneRenderer renderer;

		public SnapToGridAction(boolean snapToGrid, ZoneRenderer renderer) {
			super("Snap to grid");
			this.snapToGrid = snapToGrid;
			this.renderer = renderer;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (GUID guid : selectedTokenSet) {
				Zone zone = renderer.getZone();
				Token token = zone.getToken(guid);
				if (token == null) {
					continue;
				}
				token.setSnapToGrid(!snapToGrid);
				Grid grid = zone.getGrid();
				Dimension offset = grid.getCellOffset();
				if (token.isSnapToGrid()) {
					if (grid.getCapabilities().isSnapToGridSupported()) {
						ZonePoint zp = new ZonePoint(token.getX() - offset.width, token.getY() - offset.height);
						zp = grid.convert(grid.convert(zp));
						token.setX(zp.x);
						token.setY(zp.y);
					}
				} else {
					// If SnapToGrid is now off, change the (x,y) coordinates based on the cell offset being used by the grid
					token.setX(token.getX() + offset.width);
					token.setY(token.getY() + offset.height);
				}
				TabletopTool.serverCommand().putToken(renderer.getZone().getId(), token);
			}
		}
	}

	/**
	 * Internal class used to handle token state changes.
	 */
	public class ChangeStateAction extends AbstractAction {

		/**
		 * Initialize a state action for a given state.
		 * 
		 * @param state
		 *            The name of the state set when this action is executed
		 */
		public ChangeStateAction(String state) {
			putValue(ACTION_COMMAND_KEY, state); // Set the state command

			// Load the name, mnemonic, accelerator, and description if
			// available
			String key = "defaultTool.stateAction." + state;
			String name = com.t3.language.I18N.getText(key);
			if (!name.equals(key)) {
				putValue(NAME, name);
				int mnemonic = I18N.getMnemonic(key);
				if (mnemonic != -1)
					putValue(MNEMONIC_KEY, mnemonic);
				String accel = I18N.getAccelerator(key);
				if (accel != null)
					putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(accel));
				String description = I18N.getDescription(key);
				if (description != null)
					putValue(SHORT_DESCRIPTION, description);
			} else {

				// Default name if no I18N set
				putValue(NAME, state);
			} // endif
		}

		/**
		 * Set the state for all of the selected tokens.
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent aE) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			for (GUID tokenGUID : selectedTokenSet) {
				Token token = renderer.getZone().getToken(tokenGUID);
				if (aE.getActionCommand().equals("clear")) {
					token.removeAllStates();
					token.removeAllBars();
				} else {
					token.setState(aE.getActionCommand(), ((JCheckBoxMenuItem) aE.getSource()).isSelected());
				}
				renderer.flush(token);
				TabletopTool.serverCommand().putToken(renderer.getZone().getId(), token);
			}
			renderer.repaint();
		}
	}

	public class ChangeSizeAction extends AbstractAction {
		private final TokenFootprint footprint;

		public ChangeSizeAction(TokenFootprint footprint) {
			super(footprint.getName());
			this.footprint = footprint;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
			for (GUID tokenGUID : selectedTokenSet) {
				Token token = renderer.getZone().getToken(tokenGUID);
				token.setFootprint(renderer.getZone().getGrid(), footprint);
				token.setSnapToScale(true);
				renderer.flush(token);
				TabletopTool.serverCommand().putToken(renderer.getZone().getId(), token);
			}
			renderer.repaint();
		}

	}

	public class VisibilityAction extends AbstractAction {
		{
			putValue(Action.NAME, "Visible to players");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (GUID guid : selectedTokenSet) {
				Token token = renderer.getZone().getToken(guid);
				if (token == null) {
					continue;
				}
				token.setVisible(((JCheckBoxMenuItem) e.getSource()).isSelected());
				TabletopTool.getFrame().updateTokenTree();
				TabletopTool.serverCommand().putToken(renderer.getZone().getId(), token);

				// TODO: Need a better way of indicating local changes
				renderer.getZone().putToken(token);
			}
			renderer.repaint();
		}
	}

	public class BringToFrontAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			TabletopTool.serverCommand().bringTokensToFront(renderer.getZone().getId(), selectedTokenSet);
			TabletopTool.getFrame().refresh();
		}
	}

	public class SendToBackAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			TabletopTool.serverCommand().sendTokensToBack(renderer.getZone().getId(), selectedTokenSet);
			TabletopTool.getFrame().refresh();
		}
	}

	public class ImpersonateAction extends AbstractAction {
		public ImpersonateAction() {
			putValue(Action.NAME, "Impersonate");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextComponent commandArea = TabletopTool.getFrame().getCommandPanel().getCommandTextArea();

			if (!AppUtil.playerOwns(tokenUnderMouse)) {
				return;
			}
			commandArea.setText("/im " + tokenUnderMouse.getId());
			TabletopTool.getFrame().getCommandPanel().commitCommand();
			commandArea.requestFocusInWindow();
		}
	}

	public class StartMoveAction extends AbstractAction {
		public StartMoveAction() {
			putValue(Action.NAME, "Move");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Tool tool = TabletopTool.getFrame().getToolbox().getSelectedTool();
			if (tool instanceof PointerTool) {
				((PointerTool) tool).startTokenDrag(tokenUnderMouse);
			} else if (tool instanceof StampTool) {
				((StampTool) tool).startTokenDrag(tokenUnderMouse);
			}
		}
	}

	public class ShowPropertiesDialogAction extends AbstractAction {
		public ShowPropertiesDialogAction() {
			putValue(Action.NAME, "Edit ...");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			EditTokenDialog dialog = TabletopTool.getFrame().getTokenPropertiesDialog();
			dialog.showDialog(tokenUnderMouse);
			if (dialog.isTokenSaved()) {
				getRenderer().repaint();
				TabletopTool.serverCommand().putToken(getRenderer().getZone().getId(), getTokenUnderMouse());
				getRenderer().getZone().putToken(getTokenUnderMouse());
				TabletopTool.getFrame().resetTokenPanels();
			}
		}
	}

	public class DeleteAction extends AbstractAction {
		public DeleteAction() {
			putValue(Action.NAME, "Delete");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// check to see if this is the required action
			if (!TabletopTool.confirmTokenDelete()) {
				return;
			}
			for (GUID tokenGUID : selectedTokenSet) {
				Token token = renderer.getZone().getToken(tokenGUID);

				if (AppUtil.playerOwns(token)) {
					renderer.getZone().removeToken(tokenGUID);
					TabletopTool.serverCommand().removeToken(renderer.getZone().getId(), tokenGUID);
				}
			}
		}
	}
}
