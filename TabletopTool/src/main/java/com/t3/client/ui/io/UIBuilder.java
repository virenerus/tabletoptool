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

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.log4j.Logger;

import com.jeta.forms.components.panel.FormPanel;
import com.jidesoft.swing.CheckBoxTree;
import com.jidesoft.swing.CheckBoxTreeSelectionModel;
import com.t3.client.TabletopTool;
import com.t3.swing.SwingUtil;

/**
 * Some keywords that make looking at the Java source code easier. :)
 * 
 * BasicTreeUI
 * 
 * @author crash
 */
@SuppressWarnings("serial")
public class UIBuilder extends JDialog {

	private static final Logger log = Logger.getLogger(UIBuilder.class);

	/**
	 * @author crash
	 * 
	 */
	public class TreeModel extends DefaultTreeModel {
		/**
		 * @param root
		 *            the top-level node for the tree
		 * @param asksAllowsChildren
		 *            <code>false</code> means all nodes are leaf nodes unless they have children; <code>true</code>
		 *            means all nodes are depicted as folders until the user actually tries to expand them
		 */
		public TreeModel(TreeNode root, boolean asksAllowsChildren) {
			super(root, asksAllowsChildren);
		}

		public TreeModel(TreeNode root) {
			this(root, false); // Nodes look like leaf nodes unless they have
								// children.
		}

		/**
		 * <p>
		 * This method accepts three parameters and inserts a node into a tree at a given path.
		 * </p>
		 * <p>
		 * The first parameter identifies the root of the tree which should be searched. This node may be the actual
		 * root of the tree or some node within the tree.
		 * </p>
		 * <p>
		 * The second parameter is a string in the style of a Unix pathname that specifies where in the tree the new
		 * node should be inserted, relative to the provided root. Any non-existent intervening nodes are created as
		 * needed on the fly.
		 * </p>
		 * <p>
		 * The third parameter is the new node to be added.
		 * </p>
		 * <p>
		 * An example of how to use this method to add a sequence of three nodes:
		 * </p>
		 * <p>
		 * 
		 * <pre>
		 * UIBuilder form = new UIBuilder();
		 * DefaultMutableTreeNode root = ((DefaultTreeModel) form.getTree().getModel())
		 * 		.getRoot();
		 * form.addNode(root, &quot;Properties/Tables&quot;,
		 * 		new T3Node(&quot;Harrow Deck&quot;, MT_Table));
		 * form.addNode(root, &quot;Properties/Tables&quot;,
		 * 		new T3Node(&quot;Harrow Data&quot;, MT_Table));
		 * form.addNode(root, &quot;Maps&quot;, new T3Node(&quot;Dead Warrens&quot;, MT_Map));
		 * </pre>
		 * 
		 * </p>
		 * 
		 * @param dir
		 *            the path from the root to the parent node of the new node
		 * @param node
		 *            the new node to be added to the tree
		 * @param start
		 *            where to begin when traversing the path (<code>null</code> means the top of the tree)
		 */
		private MutableTreeNode last_start = null, last_root = null;
		private String last_dir = null;

		@SuppressWarnings("unused")
		public void addNode(String dir, T3Node node, MutableTreeNode start) {
			MutableTreeNode firstChangedNode = null;
			if (start == null)
				start = (MutableTreeNode) this.root;
			// We'll use a cache to speed up the search...
			if (dir.equals(last_dir) && start == last_start) {
				start = last_root;
			} else {
				last_dir = dir;
				last_start = start;
				String[] elems = dir.split("/");
				OuterLoop: for (int i = 0; i < elems.length; i++) {
					_Searching(start);
					Enumeration children = start.children();
					InnerLoop: while (children.hasMoreElements()) {
						DefaultMutableTreeNode next = (DefaultMutableTreeNode) children.nextElement();
						_Checking(next);
						T3Node tmp = (T3Node) next.getUserObject();
						if (elems[i].equals(tmp.getName())) {
							start = next;
							_Found(start);
							continue OuterLoop;
						}
					}

					log.debug("Warning: adding element \"" + elems[i] + "\" of \"" + dir + "\".");
					// Since the element doesn't exist, create one... It must be
					// a folder, so create it that way.
					DefaultMutableTreeNode newchild = new DefaultMutableTreeNode(new T3Node(elems[i]));
					start.insert(newchild, start.getChildCount());
					if (firstChangedNode == null)
						firstChangedNode = start;
					start = newchild;
				} // OuterLoop
				last_root = start;
			}
			/*
			 * When we reach here, "root" is the last node from the dir search; this is the container to which the new
			 * node should be added. The obvious way would be: root.add(new DefaultMutableTreeNode(node)); which is
			 * obviously wrong. ;)
			 * 
			 * It's wrong because the TreeNodes don't send change messages to any model listeners, so the tree wouldn't
			 * know to update itself. Much better to use the TreeModel API so that the messages are sent...
			 */
			if (node.getObject() != null) {
				insertNodeInto(new DefaultMutableTreeNode(node), start, start.getChildCount());
				log.debug("      Inserted '" + node + "' under '" + ((DefaultMutableTreeNode) start).getUserObject() + "'.");
			}
			if (firstChangedNode != null)
				nodeStructureChanged(firstChangedNode);
		}

		public void addNode(String dir, T3Node node) {
			addNode(dir, node, null);
		}

		private void _Searching(MutableTreeNode node) {
			DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) node;
			T3Node mtnode = (T3Node) dmtn.getUserObject();
			log.debug("Searching '" + mtnode + "'...");
		}

		private void _Checking(MutableTreeNode node) {
			DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) node;
			T3Node mtnode = (T3Node) dmtn.getUserObject();
			log.debug("  checking '" + mtnode + "'...");
		}

		private void _Found(MutableTreeNode node) {
			DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) node;
			T3Node mtnode = (T3Node) dmtn.getUserObject();
			log.debug("    Found '" + mtnode + "'.");
		}

		private DefaultMutableTreeNode last_search;

		public DefaultMutableTreeNode findNearestNode(String dir, MutableTreeNode start) {
			if (start == null || dir.charAt(0) == '/')
				start = (MutableTreeNode) this.root;
			String[] elems = dir.split("/");
			OuterLoop: for (int i = 0; i < elems.length; i++) {
				if (elems[i].length() == 0) // Skip empty elements, such as "//" or leading "/"
					continue;
				if (TabletopTool.isDevelopment())
					_Searching(start);
				Enumeration<?> children = start.children();
				while (children.hasMoreElements()) {
					DefaultMutableTreeNode next = (DefaultMutableTreeNode) children.nextElement();
					if (TabletopTool.isDevelopment())
						_Checking(next);
					if (((T3Node) next.getUserObject()).hashCode() == elems[i].hashCode()) {
						start = next;
						_Found(start);
						continue OuterLoop;
					}
				}
				/*
				 * If we get here it means we've searched the current node and not found any children that match the
				 * next element in the path. Return the node that is as deep as we could go before the search failed.
				 * The caller will need to verify that we found what they were looking for.
				 */
				break OuterLoop;
			} // OuterLoop
			last_search = (DefaultMutableTreeNode) start;
			return last_search;
		}

		public TreePath findPathToNearestNode(String dir, MutableTreeNode start) {
			DefaultMutableTreeNode result = findNearestNode(dir, start);
			return new TreePath(getPathToRoot(result));
		}
	}

	private static final String LOAD_SAVE_DIALOG = "com/t3/client/ui/forms/campaignItemList.xml";
	private static final FormPanel form = new FormPanel(LOAD_SAVE_DIALOG);

	private final CheckBoxTree tree;
	private final TreeModel dtm;
	private int status = -1;

	public UIBuilder(Frame frame) {
		super(frame, "Load/Save Dialog", true);
		add(form);
		tree = (CheckBoxTree) form.getTree("mainTree");
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new T3Node("Root"));
		dtm = new TreeModel(root);
//		buildTree();

		tree.setModel(dtm);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setRootVisible(false);

		// This is how to turn ON the checkbox for a particular node
		tree.getCheckBoxTreeSelectionModel().addSelectionPath(new TreePath(root.getPath()));

		// This is how to turn OFF the checkbox for a particular node
//		tree.getCheckBoxTreeSelectionModel().removeSelectionPath(new TreePath(root.getPath()));

		Dimension size;
		JScrollPane jsp = (JScrollPane) tree.getParent().getParent();
		JViewport jv = jsp.getViewport();
		size = tree.getPreferredScrollableViewportSize();
		size = jv.getViewSize();
		jsp.setPreferredSize(new Dimension(550, 300));
		size = tree.getPreferredScrollableViewportSize();
		size = jv.getViewSize();

		final JDialog dialog = this;
		AbstractButton btn = form.getButton("ok");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				status = JOptionPane.OK_OPTION;
				dialog.setVisible(false);
			}
		});
		btn = form.getButton("cancel");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				status = JOptionPane.CANCEL_OPTION;
				dialog.setVisible(false);
			}
		});
		SwingUtil.centerOver(this, frame);
	}

	public CheckBoxTree getTree() {
		return tree;
	}

	public TreeModel getTreeModel() {
		return dtm;
	}

	public CheckBoxTreeSelectionModel getCheckBoxTreeSelectionModel() {
		return tree.getCheckBoxTreeSelectionModel();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int s) {
		status = s;
	}

	// If expand is true, expands all nodes in the tree.
	// Otherwise, collapses all nodes in the tree.
	public void expandAndSelectAll(boolean expand) {
		TreeNode root = (TreeNode) tree.getModel().getRoot();

		// Traverse tree from root
		expandAndSelectAll(new TreePath(root), expand);
	}

	private void expandAndSelectAll(TreePath parent, boolean expand) {
		// Traverse children
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAndSelectAll(path, expand);
			}
		}
		// Expansion or collapse must be done bottom-up
		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}
}
