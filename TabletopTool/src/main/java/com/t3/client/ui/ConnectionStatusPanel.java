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

import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.t3.image.ImageUtil;
import com.t3.language.I18N;

public class ConnectionStatusPanel extends JPanel {
	public enum Status {
		connected, disconnected, server
	}

	public static Icon disconnectedIcon;
	public static Icon connectedIcon;
	public static Icon serverIcon;

	private final JLabel iconLabel = new JLabel();

	static {
		try {
			disconnectedIcon = new ImageIcon(ImageUtil.getImage("com/t3/client/image/computer_off.png")); //$NON-NLS-1$
			connectedIcon = new ImageIcon(ImageUtil.getImage("com/t3/client/image/computer_on.png")); //$NON-NLS-1$
			serverIcon = new ImageIcon(ImageUtil.getImage("com/t3/client/image/computer_server.png")); //$NON-NLS-1$
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public ConnectionStatusPanel() {
		setLayout(new GridLayout(1, 1));
		setStatus(Status.disconnected);
		add(iconLabel);
	}

	public void setStatus(Status status) {
		Icon icon = null;
		String tip = null;
		switch (status) {
		case connected:
			icon = connectedIcon;
			tip = "ConnectionStatusPanel.serverConnected"; //$NON-NLS-1$
			break;
		case server:
			icon = serverIcon;
			tip = "ConnectionStatusPanel.runningServer"; //$NON-NLS-1$
			break;
		default:
			icon = disconnectedIcon;
			tip = "ConnectionStatusPanel.notConnected"; //$NON-NLS-1$
		}
		iconLabel.setIcon(icon);
		setToolTipText(I18N.getString(tip));
	}
}
