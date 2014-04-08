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

public class StaticMessageDialog extends MessageDialog {
	private static final long serialVersionUID = 3101164410637883204L;

	private String status;

	public StaticMessageDialog(String status) {
		this.status = status;
	}

	@Override
	protected String getStatus() {
		return status;
	}

	/**
	 * Doesn't work right as it forces a repaint of the GlassPane object which takes a snapshot of the RootPane and then
	 * adds the 'status' message as an overlay. The problem is that the RootPane snapshot includes the previous image
	 * that might have been displayed previously.
	 * 
	 * @param s
	 */
	public void setStatus(String s) {
		this.status = s;
		revalidate();
		repaint();
	}
}
