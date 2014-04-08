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
package com.t3.client.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * @author trevor
 */
public class StatusPanel extends JPanel {

	private JLabel statusLabel;
	
	public StatusPanel() {
		
		statusLabel = new JLabel();
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.BOTH;
		
		add(wrap(statusLabel), constraints);
	}

	public void setStatus(String status) {
		statusLabel.setText(status);
	}
	
	public void addPanel(JComponent component) {
		
		int nextPos = getComponentCount();
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		
		constraints.gridx = nextPos;
		
		add(wrap(component), constraints);
		
		invalidate();
		doLayout();
	}

	private JComponent wrap(JComponent component) {
		
		component.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		return component;
	}
}
