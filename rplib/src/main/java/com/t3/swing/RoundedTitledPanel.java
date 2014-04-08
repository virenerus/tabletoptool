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
package com.t3.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class RoundedTitledPanel extends JPanel {

	public RoundedTitledPanel() {
		super.setLayout(new RoundedTitlePanelLayout());
	}
	
	@Override
	public void setLayout(LayoutManager mgr) {
		throw new IllegalAccessError("Can't change the layout");
	}
	
	private class RoundedTitlePanelLayout implements LayoutManager {

		@Override
		public void addLayoutComponent(String name, Component comp) {
			
		}
		@Override
		public void layoutContainer(Container parent) {
		}
		@Override
		public Dimension minimumLayoutSize(Container parent) {
			return null;
		}
		@Override
		public Dimension preferredLayoutSize(Container parent) {
			return null;
		}
		@Override
		public void removeLayoutComponent(Component comp) {
		}
	}
}
