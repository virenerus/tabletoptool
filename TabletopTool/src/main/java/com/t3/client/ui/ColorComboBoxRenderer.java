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

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.t3.client.T3Util;
import com.t3.language.I18N;

/**
 * This class renders the list entries of a color selection combo box.
 */
public class ColorComboBoxRenderer extends JLabel implements ListCellRenderer<String> {
	private static final long serialVersionUID = -8994115147056186827L;

	/**
	 * Selects a black or white text color for the given background color. The function tries to optimize readability by
	 * computing the gray value of the background color. Black is used only if the color becomes lighter than 70% gray
	 * because white it is significantly more difficult to read. The luma of the background color is calculated using
	 * the standard PAL/NTSC luma algorithm.
	 * 
	 * @param background
	 *            color
	 * @return returns the chosen background color, either black or white.
	 */
	public static Color selectForegroundColor(Color background) {
		float[] rgbValues = background.getColorComponents(null);
		float contrast = 0.299f * rgbValues[0] + 0.587f * rgbValues[1] + 0.114f * rgbValues[2];
		if (contrast > 0.7) {
			return Color.black;
		} else {
			return Color.white;
		}
	}

	/**
	 * Creates a new label with an opaque background.
	 */
	public ColorComboBoxRenderer() {
		super();
		setOpaque(true);
	}

	@Override
	/**
	 * Renders the label as a color selection combo box entry.
	 */
	public Component getListCellRendererComponent(JList<? extends String> list, String name, int index, boolean isSelected, boolean cellHasFocus) {
		String colorPropertyKey = "Color.".concat(name);
		String colorName = I18N.getString(colorPropertyKey);
		if (colorName == null) {
			colorName = name;
		}

		Color fgColor;
		Color bgColor;

		if (isSelected && !cellHasFocus) {
			fgColor = list.getSelectionForeground();
			bgColor = list.getSelectionBackground();
		} else {
			bgColor = T3Util.getColor(name);
			fgColor = selectForegroundColor(bgColor);
		}
		setForeground(fgColor);
		setBackground(bgColor);

		setText(colorName);

		return this;
	}

}
