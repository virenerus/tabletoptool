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
package com.t3.model;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.t3.client.tool.PointerTool;

public class MovementKey extends AbstractAction {
	private static final long serialVersionUID = -4103031698708914986L;
	private final double dx, dy;
	private final PointerTool tool; // I'd like to store this in the Grid, but then it has to be final :(

	public MovementKey(PointerTool callback, double x, double y) {
		tool = callback;
		dx = x;
		dy = y;
	}

	@Override
	public String toString() {
		return "[" + dx + "," + dy + "]";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		tool.handleKeyMove(dx, dy);
	}
}
