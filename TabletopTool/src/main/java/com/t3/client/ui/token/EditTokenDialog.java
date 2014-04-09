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
package com.t3.client.ui.token;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.AbstractButton;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.JTextComponent;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.jidesoft.grid.AbstractPropertyTableModel;
import com.jidesoft.grid.Property;
import com.jidesoft.grid.PropertyPane;
import com.jidesoft.grid.PropertyTable;
import com.jidesoft.swing.CheckBoxListWithSelectable;
import com.jidesoft.swing.DefaultSelectable;
import com.jidesoft.swing.Selectable;
import com.t3.client.T3Util;
import com.t3.client.TabletopTool;
import com.t3.client.swing.AbeillePanel;
import com.t3.client.swing.GenericDialog;
import com.t3.language.I18N;
import com.t3.model.AssetManager;
import com.t3.model.Association;
import com.t3.model.ObservableList;
import com.t3.model.Player;
import com.t3.model.Token;
import com.t3.model.TokenFootprint;
import com.t3.model.Zone.Layer;
import com.t3.model.campaign.TokenProperty;
import com.t3.model.campaign.TokenPropertyType;
import com.t3.model.grid.Grid;
import com.t3.util.ImageManager;

/**
 * This dialog is used to display all of the token states and notes to the user.
 */
public class EditTokenDialog extends AbeillePanel<Token> {
	private static final long serialVersionUID = 1295729281890170792L;

	private boolean tokenSaved;
	private GenericDialog dialog;
	private ImageAssetPanel imagePanel;
	// private CharSheetController controller;

	/**
	 * The size used to constrain the icon.
	 */
	public static final int SIZE = 64;

	/**
	 * Create a new token notes dialog.
	 * 
	 * @param token
	 *            The token being displayed.
	 */
	public EditTokenDialog() {
		super("com/t3/client/ui/forms/tokenPropertiesDialog.xml");
		panelInit();
	}

	public void initPlayerNotesTextArea() {
		getNotesTextArea().addMouseListener(new MouseHandler(getNotesTextArea()));
	}

	public void initGMNotesTextArea() {
		getGMNotesTextArea().addMouseListener(new MouseHandler(getGMNotesTextArea()));
		getComponent("@GMNotes").setEnabled(TabletopTool.getPlayer().isGM());
	}

	public void showDialog(Token token) {
		dialog = new GenericDialog(I18N.getString("EditTokenDialog.msg.title"), TabletopTool.getFrame(), this) {
			private static final long serialVersionUID = 5439449816096482201L;

			@Override
			public void closeDialog() {
				// TODO: I don't like this. There should really be a AbeilleDialog class that does this
				unbind();
				super.closeDialog();
			}
		};
		bind(token);
		getRootPane().setDefaultButton(getOKButton());
		dialog.showDialog();
	}

	@Override
	public void bind(final Token token) {
		// ICON
		getTokenIconPanel().setImageId(token.getImageAssetId());

		// PROPERTIES
		updatePropertyTypeCombo();
		updatePropertiesTable(token.getPropertyType());

		// SIGHT
		updateSightTypeCombo();

		// STATES
		Component barPanel = null;
		updateStatesPanel();
		Component[] statePanels = getStatesPanel().getComponents();
		for (int j = 0; j < statePanels.length; j++) {
			if ("bar".equals(statePanels[j].getName())) {
				barPanel = statePanels[j];
				continue;
			}
			Component[] states = ((Container) statePanels[j]).getComponents();
			for (int i = 0; i < states.length; i++) {
				JCheckBox state = (JCheckBox) states[i];
				state.setSelected(token.hasState(state.getText()));
			}
		}

		// BARS
		if (barPanel != null) {
			Component[] bars = ((Container) barPanel).getComponents();
			for (int i = 0; i < bars.length; i += 2) {
				JCheckBox cb = (JCheckBox) ((Container) bars[i]).getComponent(1);
				JSlider bar = (JSlider) bars[i + 1];
				if (token.getBar(bar.getName()) == null) {
					cb.setSelected(true);
					bar.setEnabled(false);
					bar.setValue(100);
				} else {
					cb.setSelected(false);
					bar.setEnabled(true);
					bar.setValue((int) (token.getBar(bar.getName()) * 100));
				}
			}
		}

		// OWNER LIST
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				getOwnerList().setModel(new OwnerListModel());
			}
		});

		// SPEECH TABLE
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				getSpeechTable().setModel(new SpeechTableModel(token));
			}
		});

//		Player player = TabletopTool.getPlayer();
//		boolean editable = player.isGM() || !TabletopTool.getServerPolicy().useStrictTokenManagement() || token.isOwner(player.getName());
//		getAllPlayersCheckBox().setSelected(token.isOwnedByAll());

		// OTHER
		getShapeCombo().setSelectedItem(token.getShape());
		setSizeCombo(token);

		getPropertyTypeCombo().setSelectedItem(token.getPropertyType());
		getSightTypeCombo().setSelectedItem(token.getSightType() != null ? token.getSightType() : TabletopTool.getCampaign().getCampaignProperties().getDefaultSightType());
		getCharSheetPanel().setImageId(token.getCharsheetImage());
		getPortraitPanel().setImageId(token.getPortraitImage());
		getTokenLayoutPanel().setToken(token);

		// we will disable the Owner only visible check box if the token is not
		// visible to players to signify the relationship
		ActionListener tokenVisibleActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				getVisibleOnlyToOwnerCheckBox().setEnabled(selected);
				getVisibleOnlyToOwnerLabel().setEnabled(selected);
			}
		};
		getVisibleCheckBox().addActionListener(tokenVisibleActionListener);

		// Character Sheets
//		controller = null;
//		String form = TabletopTool.getCampaign().getCharacterSheets().get(token.getPropertyType());
//		if (form == null)
//			return;
//		URL formUrl = getClass().getClassLoader().getResource(form);
//		if (formUrl == null)
//			return;
//		controller = new CharSheetController(formUrl, null);
//		HashMap<String, Object> properties = new HashMap<String, Object>();
//		for (String prop : token.getPropertyNames())
//			properties.put(prop, token.getProperty(prop));
//		controller.setData(properties);
//		controller.getPanel().setName("characterSheet");
//		replaceComponent("sheetPanel", "characterSheet", controller.getPanel());

		super.bind(token);
	}

	public JTabbedPane getTabbedPane() {
		return (JTabbedPane) getComponent("tabs");
	}

	public JTextArea getNotesTextArea() {
		return (JTextArea) getComponent("@notes");
	}

	public JTextArea getGMNotesTextArea() {
		return (JTextArea) getComponent("@GMNotes");
	}

	// private JLabel getGMNameLabel() {
	// return (JLabel) getComponent("tokenGMNameLabel");
	// }
	//
	// public JTextField getNameTextField() {
	// return (JTextField) getComponent("tokenName");
	// }
	//
	// public JTextField getGMNameTextField() {
	// return (JTextField) getComponent("tokenGMName");
	// }

	public void initTypeCombo() {
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		model.addElement(Token.Type.NPC);
		model.addElement(Token.Type.PC);
		// getTypeCombo().setModel(model);
	}

	public JComboBox getTypeCombo() {
		return (JComboBox) getComponent("@type");
	}

	public void initTokenIconPanel() {
		getTokenIconPanel().setPreferredSize(new Dimension(100, 100));
		getTokenIconPanel().setMinimumSize(new Dimension(100, 100));
	}

	public ImageAssetPanel getTokenIconPanel() {
		if (imagePanel == null) {
			imagePanel = new ImageAssetPanel();
			imagePanel.setAllowEmptyImage(false);
			replaceComponent("mainPanel", "tokenImage", imagePanel);
		}
		return imagePanel;
	}

	public void initShapeCombo() {
		getShapeCombo().setModel(new DefaultComboBoxModel<Token.TokenShape>(Token.TokenShape.values()));
	}

	public JComboBox<Token.TokenShape> getShapeCombo() {
		return (JComboBox<Token.TokenShape>) getComponent("shape");
	}

	public void setSizeCombo(Token token) {
		JComboBox size = getSizeCombo();
		Grid grid = TabletopTool.getFrame().getCurrentZoneRenderer().getZone().getGrid();
		DefaultComboBoxModel model = new DefaultComboBoxModel(grid.getFootprints().toArray());
		model.insertElementAt(token.getLayer() == Layer.TOKEN ? "Native Size" : "Free Size", 0);
		size.setModel(model);
		if (token.isSnapToScale()) {
			size.setSelectedItem(token.getFootprint(grid));
		} else {
			size.setSelectedIndex(0);
		}
	}

	public void initPropertyTypeCombo() {
		updatePropertyTypeCombo();
	}

	private void updatePropertyTypeCombo() {
		List<String> typeList = new ArrayList<String>(TabletopTool.getCampaign().getTokenTypes());
		Collections.sort(typeList);
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(typeList.toArray(new String[typeList.size()]));
		getPropertyTypeCombo().setModel(model);
		getPropertyTypeCombo().addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				updatePropertiesTable((String) getPropertyTypeCombo().getSelectedItem());
			}
		});
	}

	private void updateSightTypeCombo() {
		List<String> typeList = new ArrayList<String>(TabletopTool.getCampaign().getSightTypes());
		Collections.sort(typeList);

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(typeList.toArray(new String[typeList.size()]));
		getSightTypeCombo().setModel(model);
	}

	private void updatePropertiesTable(final String propertyType) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				PropertyTable pp = getPropertyTable();
				pp.setModel(new TokenPropertyTableModel());
				pp.expandAll();
			}
		});
	}

	public JComboBox getSizeCombo() {
		return (JComboBox) getComponent("size");
	}

	public JComboBox<String> getPropertyTypeCombo() {
		return (JComboBox<String>) getComponent("propertyTypeCombo");
	}

	public JComboBox<String> getSightTypeCombo() {
		return (JComboBox<String>) getComponent("sightTypeCombo");
	}

	public void initOKButton() {
		getOKButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (commit()) {
					unbind();
					dialog.closeDialog();
				}
			}
		});
	}

	@Override
	public boolean commit() {
		Token token = getModel();

		if (getNameField().getText().equals("")) {
			TabletopTool.showError("msg.error.emptyTokenName");
			return false;
		}
		if (getSpeechTable().isEditing()) {
			getSpeechTable().getCellEditor().stopCellEditing();
		}
		if (getPropertyTable().isEditing()) {
			getPropertyTable().getCellEditor().stopCellEditing();
		}
		// Commit the changes to the token properties
		if (!super.commit()) {
			return false;
		}
		// SIZE
		token.setSnapToScale(getSizeCombo().getSelectedIndex() != 0);
		if (getSizeCombo().getSelectedIndex() > 0) {
			Grid grid = TabletopTool.getFrame().getCurrentZoneRenderer().getZone().getGrid();
			token.setFootprint(grid, (TokenFootprint) getSizeCombo().getSelectedItem());
		}
		// Other
		token.setPropertyType((String) getPropertyTypeCombo().getSelectedItem());
		token.setSightType((String) getSightTypeCombo().getSelectedItem());

		// Get the states
		Component[] stateComponents = getStatesPanel().getComponents();
		Component barPanel = null;
		for (int j = 0; j < stateComponents.length; j++) {
			if ("bar".equals(stateComponents[j].getName())) {
				barPanel = stateComponents[j];
				continue;
			}
			Component[] components = ((Container) stateComponents[j]).getComponents();
			for (int i = 0; i < components.length; i++) {
				JCheckBox cb = (JCheckBox) components[i];
				String state = cb.getText();
				token.setState(state, cb.isSelected());
			}
		} // endfor

		// BARS
		if (barPanel != null) {
			Component[] bars = ((Container) barPanel).getComponents();
			for (int i = 0; i < bars.length; i += 2) {
				JCheckBox cb = (JCheckBox) ((Container) bars[i]).getComponent(1);
				JSlider bar = (JSlider) bars[i + 1];
				Float value = cb.isSelected() ? null : new Float(bar.getValue()/100f);
				token.setBar(bar.getName(), value);
				bar.setValue((int) (token.getBar(bar.getName()) * 100));
			}
		}
		// Ownership
		// If the token is owned by all and we are a player don't alter the ownership list.
		if (TabletopTool.getPlayer().isGM() || !token.isOwnedByAll()) {
			token.clearAllOwners();

			for (int i = 0; i < getOwnerList().getModel().getSize(); i++) {
				DefaultSelectable selectable = (DefaultSelectable) getOwnerList().getModel().getElementAt(i);
				if (selectable.isSelected()) {
					token.addOwner((String) selectable.getObject());
				}
			}
			// If we are not a GM and the only non GM owner make sure we can't
			// take our selves off of the owners list
			if (!TabletopTool.getPlayer().isGM()) {
				boolean hasPlayer = false;
				Set<String> owners = token.getOwners();
				if (owners != null) {
					Iterator<Player> playerIter = TabletopTool.getPlayerList().iterator();
					while (playerIter.hasNext()) {
						Player pl = playerIter.next();
						if (!pl.isGM() && owners.contains(pl.getName())) {
							hasPlayer = true;
						}
					}
				}
				if (!hasPlayer) {
					token.addOwner(TabletopTool.getPlayer().getName());
				}
			}
		}
		// SHAPE
		token.setShape((Token.TokenShape) getShapeCombo().getSelectedItem());

		// Macros
		token.setSpeechMap(((KeyValueTableModel) getSpeechTable().getModel()).getMap());

		// Properties
		((TokenPropertyTableModel) getPropertyTable().getModel()).applyTo(token);

		// Charsheet
		if (getCharSheetPanel().getImageId() != null) {
			T3Util.uploadAsset(AssetManager.getAsset(getCharSheetPanel().getImageId()));
		}
		token.setCharsheetImage(getCharSheetPanel().getImageId());

		// IMAGE
		if (!token.getImageAssetId().equals(getTokenIconPanel().getImageId())) {
			BufferedImage image = ImageManager.getImageAndWait(getTokenIconPanel().getImageId());
			T3Util.uploadAsset(AssetManager.getAsset(getTokenIconPanel().getImageId()));
			token.setImageAsset(null, getTokenIconPanel().getImageId()); // Default image for now
			token.setWidth(image.getWidth(null));
			token.setHeight(image.getHeight(null));
		}
		// PORTRAIT
		if (getPortraitPanel().getImageId() != null) {
			// Make sure the server has the image
			if (!TabletopTool.getCampaign().containsAsset(getPortraitPanel().getImageId())) {
				TabletopTool.serverCommand().putAsset(AssetManager.getAsset(getPortraitPanel().getImageId()));
			}
		}
		token.setPortraitImage(getPortraitPanel().getImageId());

		// LAYOUT
		token.setSizeScale(getTokenLayoutPanel().getSizeScale());
		token.setAnchor(getTokenLayoutPanel().getAnchorX(), getTokenLayoutPanel().getAnchorY());

		// OTHER
		tokenSaved = true;

		// Character Sheet
//		Map<String, Object> properties = controller.getData();
//		for (String prop : token.getPropertyNames())
//			token.setProperty(prop, properties.get(prop));

		// Update UI
		TabletopTool.getFrame().updateTokenTree();
		TabletopTool.getFrame().resetTokenPanels();

		return true;
	}

	public JButton getOKButton() {
		return (JButton) getComponent("okButton");
	}

	public void initCancelButton() {
		getCancelButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				unbind();
				dialog.closeDialog();
			}
		});
	}

	public JButton getCancelButton() {
		return (JButton) getComponent("cancelButton");
	}

	public PropertyTable getPropertyTable() {
		return (PropertyTable) getComponent("propertiesTable");
	}

	private void updateStatesPanel() {
		// Group the states first into individual panels
		List<BooleanTokenOverlay> overlays = new ArrayList<BooleanTokenOverlay>(TabletopTool.getCampaign().getTokenStatesMap().values());
		Map<String, JPanel> groups = new TreeMap<String, JPanel>();
		groups.put("", new JPanel(new FormLayout("0px:grow 2px 0px:grow 2px 0px:grow 2px 0px:grow")));
		for (BooleanTokenOverlay overlay : overlays) {
			String group = overlay.getGroup();
			if (group != null && (group = group.trim()).length() != 0) {
				JPanel panel = groups.get(group);
				if (panel == null) {
					panel = new JPanel(new FormLayout("0px:grow 2px 0px:grow 2px 0px:grow 2px 0px:grow"));
					panel.setBorder(BorderFactory.createTitledBorder(group));
					groups.put(group, panel);
				}
			}
		}
		// Add the group panels and bar panel to the states panel
		JPanel panel = getStatesPanel();
		panel.removeAll();
		FormLayout layout = new FormLayout("0px:grow");
		panel.setLayout(layout);
		int row = 1;
		for (JPanel gPanel : groups.values()) {
			layout.appendRow(new RowSpec("pref"));
			layout.appendRow(new RowSpec("2px"));
			panel.add(gPanel, new CellConstraints(1, row));
			row += 2;
		}
		layout.appendRow(new RowSpec("pref"));
		layout.appendRow(new RowSpec("2px"));
		JPanel barPanel = new JPanel(new FormLayout("right:pref 2px pref 5px right:pref 2px pref"));
		panel.add(barPanel, new CellConstraints(1, row));

		// Add the individual check boxes.
		for (BooleanTokenOverlay state : overlays) {
			String group = state.getGroup();
			panel = groups.get("");
			if (group != null && (group = group.trim()).length() != 0)
				panel = groups.get(group);
			int x = panel.getComponentCount() % 4;
			int y = panel.getComponentCount() / 4;
			if (x == 0) {
				layout = (FormLayout) panel.getLayout();
				if (y != 0)
					layout.appendRow(new RowSpec("2px"));
				layout.appendRow(new RowSpec("pref"));
			}
			panel.add(new JCheckBox(state.getName()), new CellConstraints(x * 2 + 1, y * 2 + 1));
		}
		// Add sliders to the bar panel
		if (TabletopTool.getCampaign().getTokenBarsMap().size() > 0) {
			layout = (FormLayout) barPanel.getLayout();
			barPanel.setName("bar");
			barPanel.setBorder(BorderFactory.createTitledBorder("Bars"));
			int count = 0;
			row = 0;
			for (BarTokenOverlay bar : TabletopTool.getCampaign().getTokenBarsMap().values()) {
				int working = count % 2;
				if (working == 0) { // slider row
					layout.appendRow(new RowSpec("pref"));
					row += 1;
				}
				JPanel labelPanel = new JPanel(new FormLayout("pref", "pref 2px:grow pref"));
				barPanel.add(labelPanel, new CellConstraints(1 + working * 4, row));
				labelPanel.add(new JLabel(bar.getName() + ":"), new CellConstraints(1, 1, CellConstraints.RIGHT, CellConstraints.TOP));
				JSlider slider = new JSlider(0, 100);
				JCheckBox hide = new JCheckBox("Hide");
				hide.putClientProperty("JSlider", slider);
				hide.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						JSlider js = (JSlider) ((JCheckBox) e.getSource()).getClientProperty("JSlider");
						js.setEnabled(!((JCheckBox) e.getSource()).isSelected());
					}
				});
				labelPanel.add(hide, new CellConstraints(1, 3, CellConstraints.RIGHT, CellConstraints.TOP));
				slider.setName(bar.getName());
				slider.setPaintLabels(true);
				slider.setPaintTicks(true);
				slider.setMajorTickSpacing(20);
				slider.createStandardLabels(20);
				slider.setMajorTickSpacing(10);
				barPanel.add(slider, new CellConstraints(3 + working * 4, row));
				if (working != 0) { // spacer row
					layout.appendRow(new RowSpec("2px"));
					row += 1;
				}
				count += 1;
			}
		}
	}

	public JPanel getStatesPanel() {
		return (JPanel) getComponent("statesPanel");
	}

	public JTable getSpeechTable() {
		return (JTable) getComponent("speechTable");
	}

	public JButton getSpeechClearAllButton() {
		return (JButton) getComponent("speechClearAllButton");
	}

	private JLabel getVisibleLabel() {
		return (JLabel) getComponent("visibleLabel");
	}

	private JCheckBox getVisibleCheckBox() {
		return (JCheckBox) getComponent("@visible");
	}

	private JLabel getVisibleOnlyToOwnerLabel() {
		return (JLabel) getComponent("visibleOnlyToOwnerLabel");
	}

	private JCheckBox getVisibleOnlyToOwnerCheckBox() {
		return (JCheckBox) getComponent("@visibleOnlyToOwner");
	}

	private JPanel getGMNotesPanel() {
		return (JPanel) getComponent("gmNotesPanel");
	}

	private JTextField getNameField() {
		return (JTextField) getComponent("@name");
	}

	public CheckBoxListWithSelectable getOwnerList() {
		return (CheckBoxListWithSelectable) getComponent("ownerList");
	}

	public void initSpeechPanel() {
		getSpeechClearAllButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!TabletopTool.confirm("EditTokenDialog.confirm.clearSpeech")) {
					return;
				}
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						getSpeechTable().setModel(new SpeechTableModel());
					}
				});
			}
		});
	}

	public void initOwnershipPanel() {
		CheckBoxListWithSelectable list = new CheckBoxListWithSelectable();
		list.setName("ownerList");
		replaceComponent("ownershipPanel", "ownershipList", new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
	}

	public void initTokenDetails() {
		// tokenGMNameLabel = panel.getLabel("tokenGMNameLabel");
	}

	public void initTokenLayoutPanel() {
		TokenLayoutPanel layoutPanel = new TokenLayoutPanel();
		layoutPanel.setPreferredSize(new Dimension(150, 125));
		layoutPanel.setName("tokenLayout");

		replaceComponent("tokenLayoutPanel", "tokenLayout", layoutPanel);
	}

	public void initCharsheetPanel() {
		ImageAssetPanel panel = new ImageAssetPanel();
		panel.setPreferredSize(new Dimension(150, 125));
		panel.setName("charsheet");
		panel.setLayout(new GridLayout());

		replaceComponent("charsheetPanel", "charsheet", panel);
	}

	public void initPortraitPanel() {
		ImageAssetPanel panel = new ImageAssetPanel();
		panel.setPreferredSize(new Dimension(150, 125));
		panel.setName("portrait");
		panel.setLayout(new GridLayout());

		replaceComponent("portraitPanel", "portrait", panel);
	}

	public ImageAssetPanel getPortraitPanel() {
		return (ImageAssetPanel) getComponent("portrait");
	}

	public ImageAssetPanel getCharSheetPanel() {
		return (ImageAssetPanel) getComponent("charsheet");
	}

	public TokenLayoutPanel getTokenLayoutPanel() {
		return (TokenLayoutPanel) getComponent("tokenLayout");
	}

	public void initPropertiesPanel() {
		PropertyTable propertyTable = new PropertyTable();
		propertyTable.setFillsViewportHeight(true); // XXX This is Java6-only -- need Java5 solution
		propertyTable.setName("propertiesTable");

		PropertyPane pane = new PropertyPane(propertyTable);
//		pane.setPreferredSize(new Dimension(100, 300));

		replaceComponent("propertiesPanel", "propertiesTable", pane);
	}

//	/**
//	 * Set the currently displayed token.
//	 * 
//	 * @param aToken
//	 *            The token to be displayed
//	 */
//	public void setToken(Token aToken) {
//		if (aToken == token)
//			return;
//		if (token != null) {
//			token.removeModelChangeListener(this);
//		}
//		token = aToken;
//
//		if (token != null) {
//			token.addModelChangeListener(this);
//
//			List<String> typeList = new ArrayList<String>();
//			typeList.addAll(TabletopTool.getCampaign().getTokenTypes());
//			Collections.sort(typeList);
//			getPropertyTypeCombo().setModel(new DefaultComboBoxModel(typeList.toArray()));
//
//			setFields();
//			updateView();
//		}
//		getTabbedPane().setSelectedIndex(0);
//	}

	/** @return Getter for tokenSaved */
	public boolean isTokenSaved() {
		return tokenSaved;
	}

	// //
	// HANDLER
	public class MouseHandler extends MouseAdapter {
		JTextArea source;

		public MouseHandler(JTextArea source) {
			this.source = source;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				JPopupMenu menu = new JPopupMenu();
				JMenuItem sendToChatItem = new JMenuItem("Send to Chat");
				sendToChatItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String selectedText = source.getSelectedText();
						if (selectedText == null) {
							selectedText = source.getText();
						}
						// TODO: Combine this with the code in MacroButton
						JTextComponent commandArea = TabletopTool.getFrame().getCommandPanel().getCommandTextArea();

						commandArea.setText(commandArea.getText() + selectedText);
						commandArea.requestFocusInWindow();
					}
				});
				menu.add(sendToChatItem);

				JMenuItem sendAsEmoteItem = new JMenuItem("Send as Emit");
				sendAsEmoteItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String selectedText = source.getSelectedText();
						if (selectedText == null) {
							selectedText = source.getText();
						}
						// TODO: Combine this with the code in MacroButton
						JTextComponent commandArea = TabletopTool.getFrame().getCommandPanel().getCommandTextArea();

						commandArea.setText("/emit " + selectedText);
						commandArea.requestFocusInWindow();
						TabletopTool.getFrame().getCommandPanel().commitCommand();
					}
				});
				menu.add(sendAsEmoteItem);
				menu.show((JComponent) e.getSource(), e.getX(), e.getY());
			}
		}
	}

	// //
	// MODELS
	private class TokenPropertyTableModel extends AbstractPropertyTableModel<com.t3.client.ui.token.EditTokenDialog.TokenPropertyTableModel.EditTokenProperty> {
		private static final long serialVersionUID = 2822797264738675580L;

		private Map<String, Object> propertyMap;
		private List<com.t3.model.campaign.TokenProperty> propertyList;

		private Map<String, Object> getPropertyMap() {
			Token token = getModel();

			if (propertyMap == null) {
				propertyMap = new HashMap<String, Object>();

				List<com.t3.model.campaign.TokenProperty> propertyList = getPropertyList();
				for (com.t3.model.campaign.TokenProperty property : propertyList) {
					Object value = token.getProperty(property.getName());
					if (value == null) {
						value = property.getDefaultValue();
					}
					propertyMap.put(property.getName(), value);
				}
			}
			return propertyMap;
		}

		private List<com.t3.model.campaign.TokenProperty> getPropertyList() {
			if (propertyList == null) {
				propertyList = TabletopTool.getCampaign().getTokenPropertyList((String) getPropertyTypeCombo().getSelectedItem());
			}
			return propertyList;
		}

		public void applyTo(Token token) {
			for (com.t3.model.campaign.TokenProperty property : getPropertyList()) {
				Object value = getPropertyMap().get(property.getName());
				if (property.getDefaultValue() != null && property.getDefaultValue().equals(value)) {
					token.setProperty(property.getName(), null); // Clear original value
					continue;
				}
				token.setProperty(property.getName(), value);
			}
		}

		@Override
		public EditTokenProperty getProperty(int index) {
			return new EditTokenProperty(getPropertyList().get(index));
		}

		@Override
		public int getPropertyCount() {
			return getPropertyList() != null ? getPropertyList().size() : 0;
		}

		class EditTokenProperty extends Property {
			private static final long serialVersionUID = 4129033551005743554L;

			private final String key;

			private TokenPropertyType type;

			public EditTokenProperty(TokenProperty property) {
				super(property.getName(), property.getName(), null, (String) getPropertyTypeCombo().getSelectedItem());
				this.key = property.getName();
				
				//get Type from key
				type=property.getType();
				
				if(type.getEditorContext()!=null)
					this.setEditorContext(type.getEditorContext());
			}

			@Override
			public Object getValue() {
				return getPropertyMap().get(key);
			}

			@Override
			public void setValue(Object value) {
				if(value==null)
					throw new NullPointerException("Tried to set null for "+key);
				else
				getPropertyMap().put(key, value);
			}

			@Override
			public boolean hasValue() {
				return getPropertyMap().get(key) != null;
			}
			
			@Override
			public Class<?> getType() {
				return type.getType();
			}
		}
	}

	private class OwnerListModel extends AbstractListModel<Selectable> {
		private static final long serialVersionUID = 2375600545516097234L;

		List<Selectable> ownerList = new ArrayList<Selectable>();

		public OwnerListModel() {
			List<String> list = new ArrayList<String>();
			Set<String> ownerSet = getModel().getOwners();
			list.addAll(ownerSet);

			ObservableList<Player> playerList = TabletopTool.getPlayerList();
			for (Object item : playerList) {
				Player player = (Player) item;
				String playerId = player.getName();
				if (!list.contains(playerId)) {
					list.add(playerId);
				}
			}
			Collections.sort(list);

			for (String id : list) {
				Selectable selectable = new DefaultSelectable(id);
				selectable.setSelected(ownerSet.contains(id));
				ownerList.add(selectable);
			}
		}

		@Override
		public Selectable getElementAt(int index) {
			return ownerList.get(index);
		}

		@Override
		public int getSize() {
			return ownerList.size();
		}
	}

	private static class SpeechTableModel extends KeyValueTableModel {
		private static final long serialVersionUID = 1601750325218502846L;

		public SpeechTableModel(Token token) {
			List<Association<String, String>> rowList = new ArrayList<Association<String, String>>();
			for (String speechName : token.getSpeechNames()) {
				rowList.add(new Association<String, String>(speechName, token.getSpeech(speechName)));
			}
			Collections.sort(rowList, new Comparator<Association<String, String>>() {
				@Override
				public int compare(Association<String, String> o1, Association<String, String> o2) {
					return o1.getLeft().compareToIgnoreCase(o2.getLeft());
				}
			});
			init(rowList);
		}

		public SpeechTableModel() {
			init(new ArrayList<Association<String, String>>());
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return I18N.getString("EditTokenDialog.msg.speech.colID");
			case 1:
				return I18N.getString("EditTokenDialog.msg.speech.colSpeechText");
			}
			return "";
		}
	}

	private static class KeyValueTableModel extends AbstractTableModel {
		private static final long serialVersionUID = -1006405977882120853L;

		private Association<String, String> newRow = new Association<String, String>("", "");
		private List<Association<String, String>> rowList;

		protected void init(List<Association<String, String>> rowList) {
			this.rowList = rowList;
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public int getRowCount() {
			return rowList.size() + 1;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (rowIndex == getRowCount() - 1) {
				switch (columnIndex) {
				case 0:
					return newRow.getLeft();
				case 1:
					return newRow.getRight();
				}
				return "";
			}
			switch (columnIndex) {
			case 0:
				return rowList.get(rowIndex).getLeft();
			case 1:
				return rowList.get(rowIndex).getRight();
			}
			return "";
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			if (rowIndex == getRowCount() - 1) {
				switch (columnIndex) {
				case 0:
					newRow.setLeft((String) aValue);
					break;
				case 1:
					newRow.setRight((String) aValue);
					break;
				}
				rowList.add(newRow);
				newRow = new Association<String, String>("", "");
				return;
			}
			switch (columnIndex) {
			case 0:
				rowList.get(rowIndex).setLeft((String) aValue);
				break;
			case 1:
				rowList.get(rowIndex).setRight((String) aValue);
				break;
			}
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return I18N.getString("EditTokenDialog.msg.generic.colKey");
			case 1:
				return I18N.getString("EditTokenDialog.msg.generic.colValue");
			}
			return "";
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}

		public Map<String, String> getMap() {
			Map<String, String> map = new HashMap<String, String>();

			for (Association<String, String> row : rowList) {
				if (row.getLeft() == null || row.getLeft().trim().length() == 0) {
					continue;
				}
				map.put(row.getLeft(), row.getRight());
			}
			return map;
		}
	}
}
