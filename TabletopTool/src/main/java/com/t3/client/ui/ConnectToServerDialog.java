/*
 * This software copyright by various authors including the RPTools.net
 * development team, and licensed under the LGPL Version 3 or, at your option,
 * any later version.
 * 
 * Portions of this software were originally covered under the Apache Software
 * License, Version 1.1 or Version 2.0.
 * 
 * See the file LICENSE elsewhere in this distribution for license details.
 */

package com.t3.client.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import net.tsc.servicediscovery.AnnouncementListener;
import net.tsc.servicediscovery.ServiceFinder;
import yasb.Binder;

import com.t3.client.AppConstants;
import com.t3.client.TabletopTool;
import com.t3.client.swing.AbeillePanel;
import com.t3.client.swing.GenericDialog;
import com.t3.language.I18N;
import com.t3.networking.registry.RegisteredServer;
import com.t3.networking.registry.T3Registry;
import com.t3.swing.SwingUtil;

/**
 * @author trevor
 */
public class ConnectToServerDialog extends AbeillePanel<ConnectToServerDialogPreferences> implements AnnouncementListener {
	private static ServiceFinder finder;
	static {
		finder = new ServiceFinder(AppConstants.SERVICE_GROUP);
	}

	private boolean accepted;
	private GenericDialog dialog;
	private int port;
	private String hostname;

	/**
	 * This is the default constructor
	 */
	public ConnectToServerDialog() {
		super("com/t3/client/ui/forms/connectToServerDialog.xml");
		setPreferredSize(new Dimension(400, 400));
		panelInit();
	}

	@Override
	protected void preModelBind() {
		Binder.setFormat(getPortTextField(), new DecimalFormat("####"));
	}

	public int getPort() {
		return port;
	}

	public String getServer() {
		return hostname;
	}

	public void showDialog() {
		dialog = new GenericDialog(I18N.getText("ConnectToServerDialog.msg.title"), TabletopTool.getFrame(), this);
		bind(new ConnectToServerDialogPreferences());
		getRootPane().setDefaultButton(getOKButton());
		dialog.showDialog();
	}

	public JButton getOKButton() {
		return (JButton) getComponent("okButton");
	}

	@Override
	public void bind(ConnectToServerDialogPreferences model) {
		finder.addAnnouncementListener(this);

		updateLocalServerList();
		updateRemoteServerList();

		super.bind(model);
	}

	@Override
	public void unbind() {
		// Shutting down
		finder.removeAnnouncementListener(this);
		finder.dispose();

		super.unbind();
	}

	public JButton getCancelButton() {
		return (JButton) getComponent("cancelButton");
	}

	public void initCancelButton() {
		getCancelButton().addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				accepted = false;
				dialog.closeDialog();
			}
		});
	}

	public void initOKButton() {
		getOKButton().addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				handleOK();
			}
		});
	}

	public boolean accepted() {
		return accepted;
	}

	public JComboBox<String> getRoleComboBox() {
		return (JComboBox<String>) getComponent("@role");
	}

	public void initRoleComboBox() {
		getRoleComboBox().setModel(new DefaultComboBoxModel<String>(new String[] { "Player", "GM" }));
	}

	public void initLocalServerList() {
		getLocalServerList().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getLocalServerList().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
					handleOK();
				}
			};
		});
	}

	public JList<RegisteredServer> getLocalServerList() {
		return (JList<RegisteredServer>) getComponent("localServerList");
	}

	private void updateLocalServerList() {
		finder.find();
	}

	private void updateRemoteServerList() {
		new SwingWorker<Object, Object>() {
			RemoteServerTableModel model = null;

			@Override
			protected Object doInBackground() throws Exception {
				model = new RemoteServerTableModel(T3Registry.findAllInstances());
				return null;
			}

			@Override
			protected void done() {
				if (model != null) {
					getRemoteServerTable().setModel(model);
				}
				TableColumn column = getRemoteServerTable().getColumnModel().getColumn(1);
				column.setPreferredWidth(70);
				column.setMaxWidth(70);
				column.setCellRenderer(new DefaultTableCellRenderer() {
					{
						setHorizontalAlignment(RIGHT);
					}
				});
			}
		}.execute();
	}

	public void initRemoteServerTable() {
		getRemoteServerTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getRemoteServerTable().setModel(new RemoteServerTableModel(Collections.emptyList()));
		getRemoteServerTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					JTable rem = getRemoteServerTable();
					getServerNameTextField().setText(rem.getModel().getValueAt(rem.getSelectedRow(), 0).toString());
					if (e.getClickCount() == 2)
						handleOK();
				}
			};
		});
	}

	public JTable getRemoteServerTable() {
		return (JTable) getComponent("aliasTable");
	}

	public JButton getRescanButton() {
		return (JButton) getComponent("rescanButton");
	}

	public JButton getRefreshButton() {
		return (JButton) getComponent("refreshButton");
	}

	public void initRescanButton() {
		getRescanButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((DefaultListModel<RegisteredServer>) getLocalServerList().getModel()).clear();
				finder.find();
			}
		});
	}

	public void initRefreshButton() {
		getRefreshButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateRemoteServerList();
			}
		});
	}

	public JTextField getUsernameTextField() {
		return (JTextField) getComponent("@username");
	}

	public JTextField getPortTextField() {
		return (JTextField) getComponent("@port");
	}

	public JTextField getHostTextField() {
		return (JTextField) getComponent("@host");
	}

	public JTextField getServerNameTextField() {
		return (JTextField) getComponent("@serverName");
	}

	public JTabbedPane getTabPane() {
		return (JTabbedPane) getComponent("tabPane");
	}

	private void handleOK() {
		String username = getUsernameTextField().getText().trim();
		if (username.length() == 0) {
			TabletopTool.showError("ServerDialog.error.username"); //$NON-NLS-1$
			return;
		}
		getUsernameTextField().setText(username);

		String externalAddress = "Unknown";
		try {
			externalAddress = T3Registry.getAddress();
		} catch (Exception e) {
			// Oh well, might not be connected
		}
//		System.out.println("External address is: " + externalAddress);

		JComponent selectedPanel = (JComponent) getTabPane().getSelectedComponent();
		if (SwingUtil.hasComponent(selectedPanel, "lanPanel")) {
			if (getLocalServerList().getSelectedIndex() < 0) {
				TabletopTool.showError("ServerDialog.error.server"); //$NON-NLS-1$
				return;
			}
			// OK
			RegisteredServer info = getLocalServerList().getSelectedValue();
			port = info.getPort();
			hostname = info.getAddress();
		}
		if (SwingUtil.hasComponent(selectedPanel, "directPanel")) {
			// TODO: put these into a validation method
			if (getPortTextField().getText().length() == 0) {
				TabletopTool.showError("ServerDialog.error.port");
				return;
			}
			int portTemp = -1;
			try {
				portTemp = Integer.parseInt(getPortTextField().getText());
			} catch (NumberFormatException nfe) {
				TabletopTool.showError("ServerDialog.error.port");
				return;
			}

			String host = getHostTextField().getText().trim();
			if (host.length() == 0) {
				TabletopTool.showError("ServerDialog.error.server");
				return;
			}
			getHostTextField().setText(host);

			// OK
			port = portTemp;
			hostname = host;
		}
		if (SwingUtil.hasComponent(selectedPanel, "rptoolsPanel")) {
			int row=getRemoteServerTable().getSelectedRow();
			RegisteredServer rs=((RemoteServerTableModel)getRemoteServerTable().getModel()).servers.get(row);
			
			hostname = rs.getAddress();
			try {
				port = rs.getPort();
			} catch (NumberFormatException nfe) {
				TabletopTool.showError("ServerDialog.error.portNumberException");
				return;
			}
		}
		try {
			InetAddress server = InetAddress.getByName(hostname);
			InetAddress extAddress = InetAddress.getByName(externalAddress);
			if (extAddress != null && extAddress.equals(server)) {
				boolean yes = TabletopTool.confirm("ConnectToServerDialog.warning.doNotUseExternalAddress", I18N.getString("menu.file"), I18N.getString("action.showConnectionInfo"));
				if (!yes)
					return;
			}
		} catch (UnknownHostException e) {
			// If an exception occurs, don't bother doing the comparison.  But otherwise it's not an error.
		}
		if (commit()) {
			accepted = true;
			dialog.closeDialog();
		}
	}

	@Override
	public boolean commit() {
		ConnectToServerDialogPreferences prefs = new ConnectToServerDialogPreferences();

		// Not bindable .. yet
		prefs.setTab(getTabPane().getSelectedIndex());
		return super.commit();
	}

	private static class RemoteServerTableModel extends AbstractTableModel {
		private final List<RegisteredServer> servers;

		public RemoteServerTableModel(List<RegisteredServer> servers) {
			// Simple but sufficient
			Collections.sort(servers, (s1, s2) -> String.CASE_INSENSITIVE_ORDER.compare(s1.getName(), s2.getName()));

			this.servers = servers;
			
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return I18N.getText("ConnectToServerDialog.msg.headingServer");
			case 1:
				return I18N.getText("ConnectToServerDialog.msg.headingVersion");
			case 2:
				return I18N.getText("ConnectToServerDialog.msg.numberOfPlayers");
			}
			return "";
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public int getRowCount() {
			return servers.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch(columnIndex) {
				case 0:
					return servers.get(rowIndex).getName();
				case 1:
					return servers.get(rowIndex).getT3Version();
				case 2:
					return servers.get(rowIndex).getNumberOfPlayers();
			}
			return "";
		}
	}

	// ANNOUNCEMENT LISTENER
	@Override
	public void serviceAnnouncement(String type, InetAddress address, int port, byte[] data) {
		((DefaultListModel<RegisteredServer>) getLocalServerList().getModel()).addElement(new RegisteredServer(new String(data), address.getHostAddress(), port,"unknown",0));
	}
}
