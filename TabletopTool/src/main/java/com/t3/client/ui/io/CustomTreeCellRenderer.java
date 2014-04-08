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
package com.t3.client.ui.io;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

/**
 * @author crash
 * 
 */
@SuppressWarnings("serial")
class CustomTreeCellRenderer extends JCheckBox implements TreeCellRenderer {
	DefaultMutableTreeNode node;
	T3Node mtnode;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		node = (DefaultMutableTreeNode) value;
		mtnode = (T3Node) node.getUserObject();
		setText(mtnode.toString());
		setBackground(tree.getBackground());
		setEnabled(tree.isEnabled());
		setComponentOrientation(tree.getComponentOrientation());
		return this;
	}

	protected boolean isFirstLevel() {
		return node.getParent() == node.getRoot();
	}
}
