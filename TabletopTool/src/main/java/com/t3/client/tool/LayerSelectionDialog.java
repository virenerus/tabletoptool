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

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jeta.forms.components.panel.FormPanel;
import com.t3.client.TabletopTool;
import com.t3.model.Zone;

public class LayerSelectionDialog extends JPanel {

	private final FormPanel panel;
	private JList<Zone.Layer> list;
	private final LayerSelectionListener listener;
	private final Zone.Layer[] layerList;

	public LayerSelectionDialog(Zone.Layer[] layerList, LayerSelectionListener listener) {
		panel = new FormPanel("com/t3/client/ui/forms/layerSelectionDialog.xml");
		this.listener = listener;
		this.layerList = layerList;
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		getLayerList();

		setLayout(new GridLayout(1, 1));
		add(panel);
	}

	public void fireViewSelectionChange() {

		int index = list.getSelectedIndex();
		if (index >= 0 && listener != null) {
			listener.layerSelected((Zone.Layer) list.getModel().getElementAt(index));
		}
	}

	public void updateViewList() {
		getLayerList().setSelectedValue(TabletopTool.getFrame().getCurrentZoneRenderer().getActiveLayer(), true);
	}

	private JList<Zone.Layer> getLayerList() {

		if (list == null) {
			list = panel.getList("layerList");

			DefaultListModel<Zone.Layer> model = new DefaultListModel<Zone.Layer>();
			for (Zone.Layer layer : layerList) {
				model.addElement(layer);
			}

			list.setModel(model);
			list.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (e.getValueIsAdjusting()) {
						return;
					}

					fireViewSelectionChange();
				}
			});
			list.setSelectedIndex(0);
		}

		return list;
	}

	public void setSelectedLayer(Zone.Layer layer) {
		list.setSelectedValue(layer, true);
	}

	public static interface LayerSelectionListener {
		public void layerSelected(Zone.Layer layer);
	}
}
