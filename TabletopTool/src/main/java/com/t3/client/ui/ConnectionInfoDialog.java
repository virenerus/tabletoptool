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

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.jeta.forms.components.panel.FormPanel;
import com.t3.client.TabletopTool;
import com.t3.networking.T3Server;
import com.t3.networking.registry.T3Registry;
import com.t3.swing.SwingUtil;

public class ConnectionInfoDialog extends JDialog {
	private static final Logger log = Logger.getLogger(ConnectionInfoDialog.class);
	private final JTextField externalAddressLabel;

	/**
	 * This is the default constructor
	 */
	public ConnectionInfoDialog(T3Server server) {
		super(TabletopTool.getFrame(), "Server Info", true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(275, 200);

		FormPanel panel = new FormPanel("com/t3/client/ui/forms/connectionInfoDialog.xml");

		JTextField nameLabel = panel.getTextField("name");
		JTextField localAddressLabel = panel.getTextField("localAddress");
		JTextField portLabel = panel.getTextField("port");
		externalAddressLabel = panel.getTextField("externalAddress");

		String name = server.getConfig().getServerName();
		if (name == null) {
			name = "---";
		}
		String localAddress = "Unknown";
		try {
			InetAddress rptools = InetAddress.getByName("www.rptools.net");
			try {
				InetAddress localAddy = InetAddress.getLocalHost();
				localAddress = localAddy.getHostAddress();
			} catch (IOException e) { // Socket|UnknownHost
				log.warn("Can't resolve 'www.rptools.net' or our own IP address!?", e);
			}
		} catch (UnknownHostException e) {
			log.warn("Can't resolve 'www.rptools.net' or our own IP address!?", e);
		}
		String externalAddress = "Discovering ...";
		String port = TabletopTool.isPersonalServer() ? "---" : Integer.toString(server.getConfig().getPort());

		nameLabel.setText(name);
		localAddressLabel.setText(localAddress);
		externalAddressLabel.setText(externalAddress);
		portLabel.setText(port);

		JButton okButton = (JButton) panel.getButton("okButton");
		bindOKButtonActions(okButton);

		setLayout(new GridLayout());
		((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(panel);

		new Thread(new ExternalAddressFinder()).start();
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			SwingUtil.centerOver(this, TabletopTool.getFrame());
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes okButton
	 * 
	 * @return javax.swing.JButton
	 */
	private void bindOKButtonActions(JButton okButton) {
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				setVisible(false);
			}
		});
	}

	private class ExternalAddressFinder implements Runnable {
		@Override
		public void run() {
			String address = "Unknown";
			try {
				address = T3Registry.getAddress();
			} catch (Exception e) {
				// Oh well, might not be connected
			}
			final String addy = address;
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					externalAddressLabel.setText(addy);
				}
			});
		}
	}
}
