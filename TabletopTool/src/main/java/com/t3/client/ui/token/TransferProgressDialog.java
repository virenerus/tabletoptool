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

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import com.t3.client.TabletopTool;
import com.t3.client.swing.AbeillePanel;
import com.t3.client.swing.GenericDialog;
import com.t3.model.Token;
import com.t3.transfer.AssetConsumer;
import com.t3.transfer.ConsumerListener;

/**
 * This dialog is used to display all of the assets being transferred
 */
public class TransferProgressDialog extends AbeillePanel<Token> implements ConsumerListener {

	private GenericDialog dialog;

	public TransferProgressDialog() {
		super("com/t3/client/ui/forms/transferProgressDialog.xml");

		panelInit();
	}

	public void showDialog() {
		dialog = new GenericDialog("Assets in Transit", TabletopTool.getFrame(), this, false) {
			@Override
			public void showDialog() {
				TabletopTool.getAssetTransferManager().addConsumerListener(TransferProgressDialog.this);
				super.showDialog();
			}

			@Override
			public void closeDialog() {
				TabletopTool.getAssetTransferManager().removeConsumerListener(TransferProgressDialog.this);
				super.closeDialog();
			}
		};

		getRootPane().setDefaultButton(getCloseButton());
		dialog.showDialog();
	}

	public JButton getCloseButton() {
		return (JButton) getComponent("closeButton");
	}

	public JTable getTransferTable() {
		return (JTable) getComponent("transferTable");
	}

	public void initCloseButton() {
		getCloseButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.closeDialog();
			}
		});
	}

	private void updateTransferTable() {

		final TransferTableModel model = new TransferTableModel();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				getTransferTable().setModel(model);

				TableColumnModel colModel = getTransferTable().getColumnModel();
				colModel.getColumn(1).setMaxWidth(100);
				colModel.getColumn(2).setMaxWidth(75);
			}
		});
	}

	public void initTransferTable() {
		getTransferTable().setBackground(Color.white);
		updateTransferTable();
	}

	private static class TransferTableModel extends AbstractTableModel {

		private final List<AssetConsumer> consumerList;

		public TransferTableModel() {
			consumerList = TabletopTool.getAssetTransferManager().getAssetConsumers();
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public int getRowCount() {
			return Math.max(consumerList.size(), 1);
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {

			if (consumerList.size() == 0) {
				return columnIndex == 0 ? "None" : "";
			}

			AssetConsumer consumer = consumerList.get(rowIndex);

			switch (columnIndex) {
			case 0:
				return consumer.getId();
			case 1:
				return formatSize(consumer.getSize());
			case 2:
				return NumberFormat.getPercentInstance().format(consumer.getPercentComplete());
			}

			return null;
		}

		private String formatSize(long size) {

			return NumberFormat.getIntegerInstance().format(size / 1024) + "k";
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return "ID";
			case 1:
				return "Size";
			case 2:
				return "Progress";
			}
			return "";
		}
	}

	////
	// CONSUMER LISTENER
	@Override
	public void assetComplete(Serializable id, String name, File data) {
		updateTransferTable();
	}

	@Override
	public void assetUpdated(Serializable id) {
		getTransferTable().repaint();
	}

	@Override
	public void assetAdded(Serializable id) {
		updateTransferTable();
	}
}
