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
package com.t3.client.ui.campaignproperties;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.lang3.StringUtils;

import com.jidesoft.grid.TreeTable;
import com.jidesoft.swing.JideScrollPane;
import com.t3.client.swing.AbeillePanel;
import com.t3.client.ui.campaignproperties.PropertyTypesTableModel.PropertyTypeRow;
import com.t3.model.campaign.Campaign;
import com.t3.model.campaign.CampaignProperties;
import com.t3.model.tokenproperties.old.TokenProperty;

public class TokenPropertiesManagementPanel extends AbeillePanel<CampaignProperties> {

	private Map<String, List<TokenProperty>> tokenTypeMap;
	private String editingType;
	private TreeTable tokenPropertiesTable;
	private PropertyTypesTableModel tokenPropertiesTableModel;

	public TokenPropertiesManagementPanel() {
		super("com/t3/client/ui/forms/tokenPropertiesManagementPanel.xml");

		panelInit();
	}

	public void copyCampaignToUI(CampaignProperties campaignProperties) {

		tokenTypeMap = new HashMap<String, List<TokenProperty>>(campaignProperties.getTokenTypeMap());

		updateTypeList();

	}

	public void copyUIToCampaign(Campaign campaign) {

		campaign.getTokenTypeMap().clear();
		campaign.getTokenTypeMap().putAll(tokenTypeMap);
	}

	public JList<String> getTokenTypeList() {
		JList<String> list = (JList<String>) getComponent("tokenTypeList");
		if (list == null)
		{
			list = new JList<String>();
		}
		return list;
	}

	public JTextField getTokenTypeName() {
		return (JTextField) getComponent("tokenTypeName");
	}

	public JButton getNewButton() {
		return (JButton) getComponent("newButton");

	}

	public JButton getUpdateButton() {
		return (JButton) getComponent("updateButton");

	}

	public JButton getRevertButton() {
		return (JButton) getComponent("revertButton");

	}

	public void initTokenProperties() {
		tokenPropertiesTableModel=new PropertyTypesTableModel(null);
		tokenPropertiesTable = new TreeTable(tokenPropertiesTableModel);
		tokenPropertiesTable.addMouseListener(new PropertiesTablePopupMenuManager());
		tokenPropertiesTable.setFillsViewportHeight(true);
		tokenPropertiesTable.setName("propertiesTable");
		tokenPropertiesTable.setColumnResizable(false);
		//tokenPropertiesTable.set
		//TableScrollPane sp = new TableScrollPane();
		//sp.add(tokenPropertiesTable);
		replaceComponent("propertiesPanel", "tokenProperties", new JideScrollPane(tokenPropertiesTable));
	}
	
	public void initUpdateButton() {
		getUpdateButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
	}

	public void initNewButton() {
		getNewButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						// This will force a reset
						getTokenTypeList().getSelectionModel().clearSelection();
						reset();
					}
				});
			}
		});
	}
	
	public void initAddRowButton() {
		((JButton)getComponent("addRowButton")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tokenPropertiesTableModel!=null)
					tokenPropertiesTableModel.addRow(tokenPropertiesTableModel.new PropertyTypeRow(new TokenProperty()));
			}
		});
	}
	
	public void initRevertButton() {
		getRevertButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bind(editingType);
			}
		});
	}

	public void initTypeList() {

		getTokenTypeList().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					return;
				}

				if (getTokenTypeList().getSelectedValue() == null) {
					reset();
				} else {
					bind(getTokenTypeList().getSelectedValue());
				}
			}
		});
		getTokenTypeList().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	private void bind(String type) {

		editingType = type;

		getTokenTypeName().setText(type != null ? type : "");
		getTokenTypeName().setEditable(!CampaignProperties.DEFAULT_TOKEN_PROPERTY_TYPE.equals(type));
		tokenPropertiesTableModel=new PropertyTypesTableModel(tokenTypeMap.get(type));
		tokenPropertiesTable.setModel(tokenPropertiesTableModel);
	}

	private void update() {

		// Pull the old one out and put the new one in (rename)
		
		try {
			List<TokenProperty> current=tokenPropertiesTableModel.collectTokenProperties();

			tokenTypeMap.remove(editingType);
			tokenTypeMap.put(getTokenTypeName().getText().trim(), current);
			reset();
			updateTypeList();
		} catch (IllegalArgumentException e) {
			// Don't need to do anything here...
		}
	}

	private void reset() {

		bind((String)null);
	}

	private void updateTypeList() {
		getTokenTypeList().setModel(new TypeListModel());
	}

	private class TypeListModel extends AbstractListModel<String> {
		
		private ArrayList<String> names;

		public TypeListModel() {
			names = new ArrayList<String>(tokenTypeMap.keySet());
			Collections.sort(names);
		}
		
		@Override
		public String getElementAt(int index) {
			return names.get(index);
		}

		@Override
		public int getSize() {
			return tokenTypeMap.size();
		}
	}
}
