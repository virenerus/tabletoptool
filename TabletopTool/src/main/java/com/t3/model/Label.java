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

import java.awt.Color;

import com.t3.guid.GUID;
import com.t3.xstreamversioned.SerializationVersion;

@SerializationVersion(0)
public class Label {
	private final GUID id;
	private String label;
	private int x, y;
	private boolean showBackground;
	private int foregroundColor;

	public Label() {
		this("");
	}

	public Label(String label) {
		this(label, 0, 0);
	}

	public Label(String label, int x, int y) {
		id = new GUID();
		this.label = label;
		this.x = x;
		this.y = y;
		showBackground = true;
	}

	public Label(Label label) {
		this(label.label, label.x, label.y);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public GUID getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isShowBackground() {
		return showBackground;
	}

	public void setShowBackground(boolean showBackground) {
		this.showBackground = showBackground;
	}

	public Color getForegroundColor() {
		return new Color(foregroundColor);
	}

	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor.getRGB();
	}
}
