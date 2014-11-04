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
package com.t3.client.ui.htmlframe;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;

import com.t3.client.TabletopTool;
import com.t3.macro.MacroException;
import com.t3.macro.api.views.MacroButtonView;
import com.t3.swing.SwingUtil;

@SuppressWarnings("serial")
public class HTMLDialog extends JDialog implements HTMLPanelContainer {
	private static Map<String, HTMLDialog> dialogs = new HashMap<String, HTMLDialog>();

	private final Map<String, String> macroCallbacks = new HashMap<String, String>();
	private boolean temporary;
	private boolean input;
	private final HTMLPanel panel;
	private final String name;
	private final boolean canResize = true;
	private final Frame parent;
	private boolean closeButton;

	/**
	 * Returns if the frame is visible or not.
	 * 
	 * @param name
	 *            The name of the frame.
	 * @return true if the frame is visible.
	 */
	static boolean isVisible(String name) {
		if (dialogs.containsKey(name)) {
			return dialogs.get(name).isVisible();
		}
		return false;
	}

	/**
	 * Requests that the frame close.
	 * 
	 * @param name
	 *            The name of the frame.
	 */
	static void close(String name) {
		if (dialogs.containsKey(name)) {
			dialogs.get(name).closeRequest();
		}
	}

	/**
	 * Creates a HTMLDialog
	 * 
	 * @param parent
	 *            The parent frame.
	 * @param name
	 *            The name of the dialog.
	 * @param title
	 *            The title of the dialog.
	 * @param undecorated
	 *            If the dialog is decorated or not.
	 * @param width
	 *            The width of the dialog.
	 * @param height
	 *            The height of the dialog.
	 * @param closeButton
	 *            if the close button should be displayed or not.
	 */
	private HTMLDialog(Frame parent, String name, String title, boolean undecorated, boolean closeButton, int width, int height) {
		super(parent, title, false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeRequest();
			}
		});
		this.name = name;
		setUndecorated(undecorated);

		width = width < 100 ? 400 : width;
		height = height < 50 ? 200 : height;
		setPreferredSize(new Dimension(width, height));

		panel = new HTMLPanel(this, closeButton, !undecorated);
		add(panel);
		pack();
		this.parent = parent;

		SwingUtil.centerOver(this, parent);
	}

	/**
	 * Shows the HTML Dialog. This will create a new dialog if the named dialog does not already exist. The width and
	 * height fields are ignored if the dialog has already been opened so that it will not override any resizing that
	 * the user may have done.
	 * 
	 * @param name
	 *            The name of the dialog.
	 * @param title
	 *            The title for the dialog window .
	 * @param width
	 *            The width in pixels of the dialog.
	 * @param height
	 *            The height in pixels of the dialog.
	 * @param frame
	 *            If the dialog is decorated with frame or not.
	 * @param input
	 *            Is the dialog an input only dialog.
	 * @param temp
	 *            Is the dialog temporary.
	 * @param closeButton
	 *            should the close button be displayed or not.
	 * @param html
	 *            The HTML to display in the dialog.
	 * @return The dialog.
	 */
	public static HTMLDialog showDialog(String name, String title, int width, int height, boolean frame,
				boolean input, boolean temp, boolean closeButton, String html) {
		HTMLDialog dialog;
		if (dialogs.containsKey(name)) {
			dialog = dialogs.get(name);
			dialog.updateContents(html, temp, closeButton, input);
		} else {
			dialog = new HTMLDialog(TabletopTool.getFrame(), name, title, !frame, closeButton, width, height);
			dialogs.put(name, dialog);
			dialog.updateContents(html, temp, closeButton, input);
		}
//		dialog.canResize = false;
		if (!dialog.isVisible()) {
			dialog.setVisible(true);
		}
		return dialog;
	}

	/**
	 * Updates the contents of the dialog.
	 * 
	 * @param html
	 *            The html contents of the dialog.
	 * @param temp
	 *            Is the dialog temporary or not.
	 * @param closeButton
	 *            does the dialog have a close button.
	 */
	private void updateContents(String html, boolean temp, boolean closeButton, boolean input) {
		this.input = input;
		this.closeButton = closeButton;
		this.temporary = temp;
		macroCallbacks.clear();
		panel.updateContents(html, closeButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e instanceof HTMLPane.FormActionEvent) {
			if (input) {
				closeRequest();
			}
			HTMLPane.FormActionEvent fae = (HTMLPane.FormActionEvent) e;
			//FIXME why would we need the action?
			try {
				MacroButtonView.executeLink(/*fae.getAction() + */fae.getData());
			} catch (MacroException e1) {
				//FIXME here should be macro error handling
				throw new RuntimeException(e1);
			}
		}
		if (e instanceof HTMLPane.ChangeTitleActionEvent) {
			this.setTitle(((HTMLPane.ChangeTitleActionEvent) e).getNewTitle());
		}
		if (e instanceof HTMLPane.RegisterMacroActionEvent) {
			HTMLPane.RegisterMacroActionEvent rmae = (HTMLPane.RegisterMacroActionEvent) e;
			macroCallbacks.put(rmae.getType(), rmae.getMacro());
		}
		if (e instanceof HTMLPane.MetaTagActionEvent) {
			HTMLPane.MetaTagActionEvent mtae = (HTMLPane.MetaTagActionEvent) e;
			if (mtae.getName().equalsIgnoreCase("input")) {
				Boolean val = Boolean.valueOf(mtae.getContent());
				input = val;
				closeButton = !input;
			} else if (mtae.getName().equalsIgnoreCase("closebutton")) {
				Boolean val = Boolean.valueOf(mtae.getContent());
				closeButton = val;
				panel.updateContents(closeButton);
			} else if (mtae.getName().equalsIgnoreCase("onChangeToken") ||
						mtae.getName().equalsIgnoreCase("onChangeSelection") ||
						mtae.getName().equalsIgnoreCase("onChangeImpersonated")) {
				macroCallbacks.put(mtae.getName(), mtae.getContent());
			} else if (mtae.getName().equalsIgnoreCase("width")) {
				if (canResize) {
					setSize(new Dimension(Integer.parseInt(mtae.getContent()), getHeight()));
					validate();
				}
			} else if (mtae.getName().equalsIgnoreCase("height")) {
				if (canResize) {
					setSize(new Dimension(getWidth(), Integer.parseInt(mtae.getContent())));
					SwingUtil.centerOver(this, parent);
					this.validate();
				}
			} else if (mtae.getName().equalsIgnoreCase("temporary")) {
				Boolean val = Boolean.valueOf(mtae.getContent());
				SwingUtil.centerOver(this, parent);
				temporary = val;
			}
		}
		if (e.getActionCommand().equals("Close")) {
			closeRequest();
		}
	}

	@Override
	public void closeRequest() {
		setVisible(false);
		panel.flush();
		if (temporary) {
			dialogs.remove(this.name);
			dispose();
		}
	}
}
