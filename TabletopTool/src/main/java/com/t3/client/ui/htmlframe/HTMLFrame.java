/*
 *  This software copyright by various authors including the RPTools.net
 *  development team, and licensed under the LGPL Version 3 or, at your
 *  option, any later version.
 *
 *  Portions of this software were originally covered under the Apache
 *  Software License, Version 1.1 or Version 2.0.
 *
 *  See the file LICENSE elsewhere in this distribution for license details.
 */

package com.t3.client.ui.htmlframe;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.t3.client.AppStyle;
import com.t3.client.TabletopTool;
import com.t3.macro.api.views.MacroButtonView;

@SuppressWarnings("serial")
public class HTMLFrame extends DockableFrame implements HTMLPanelContainer {
	private static final Map<String, HTMLFrame> frames = new HashMap<String, HTMLFrame>();
	private final Map<String, String> macroCallbacks = new HashMap<String, String>();

	private final HTMLPanel panel;

	/**
	 * Returns if the frame is visible or not.
	 * 
	 * @param name
	 *            The name of the frame.
	 * @return true if the frame is visible.
	 */
	static boolean isVisible(String name) {
		if (frames.containsKey(name)) {
			return frames.get(name).isVisible();
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
		if (frames.containsKey(name)) {
			frames.get(name).closeRequest();
		}
	}

	/**
	 * Creates a new HTMLFrame and displays it or displays an existing frame. The width and height are ignored for
	 * existing frames so that they will not override the size that the player may have resized them to.
	 * 
	 * @param name
	 *            The name of the frame.
	 * @param title
	 *            The title of the frame.
	 * @param width
	 *            The width of the frame in pixels.
	 * @param height
	 *            The height of the frame in pixels.
	 * @param html
	 *            The html to display in the frame.
	 * @return The HTMLFrame that is displayed.
	 */
	public static HTMLFrame showFrame(String name, String title, int width, int height, String html) {
		HTMLFrame frame;

		if (frames.containsKey(name)) {
			frame = frames.get(name);
			frame.setTitle(title);
			frame.updateContents(html);
			if (!frame.isVisible()) {
				frame.setVisible(true);
				frame.getDockingManager().showFrame(name);
			}
		} else {
			// Only set size on creation so we don't override players resizing.
			width = width < 100 ? 400 : width;
			height = height < 50 ? 200 : height;

			frame = new HTMLFrame(TabletopTool.getFrame(), name, title, width, height);
			frames.put(name, frame);
			frame.updateContents(html);
			frame.getDockingManager().showFrame(name);
			center(name);
		}
		return frame;
	}

	/**
	 * Creates a new HTMLFrame.
	 * 
	 * @param parent
	 *            The parent of this frame.
	 * @param name
	 *            the name of the frame.
	 * @param title
	 *            The title of the frame.
	 * @param width
	 * @param height
	 */
	private HTMLFrame(Frame parent, String name, String title, int width, int height) {
		super(title, new ImageIcon(AppStyle.chatPanelImage));

		setPreferredSize(new Dimension(width, height));
		panel = new HTMLPanel(this, true, true); // closeOnSubmit is true so we don't get close button
		add(panel);
		this.getContext().setInitMode(DockContext.STATE_FLOATING);
		TabletopTool.getFrame().getDockingManager().addFrame(this);
		this.setVisible(true);
	}

	static public void center(String name) {
		if (!frames.containsKey(name)) {
			return;
		}
		HTMLFrame frame = frames.get(name);
		Dimension outerSize = TabletopTool.getFrame().getSize();

		int x = TabletopTool.getFrame().getLocation().x + (outerSize.width - frame.getWidth()) / 2;
		int y = TabletopTool.getFrame().getLocation().y + (outerSize.height - frame.getHeight()) / 2;

		Rectangle rect = new Rectangle(x < 0 ? 0 : x, y < 0 ? 0 : y, frame.getWidth(), frame.getHeight());
		TabletopTool.getFrame().getDockingManager().floatFrame(frame.getKey(), rect, true);
	}

	/**
	 * Updates the html contents of the frame.
	 * 
	 * @param html
	 *            the html contents.
	 */
	public void updateContents(String html) {
		macroCallbacks.clear();
		panel.updateContents(html, false);
	}

	@Override
	public void closeRequest() {
		TabletopTool.getFrame().getDockingManager().hideFrame(getKey());
		setVisible(false);
		panel.flush();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e instanceof HTMLPane.FormActionEvent) {
			HTMLPane.FormActionEvent fae = (HTMLPane.FormActionEvent) e;
			//FIXME why would we need the action?
			MacroButtonView.executeLink(/*fae.getAction() + */fae.getData());
		}
		if (e instanceof HTMLPane.RegisterMacroActionEvent) {
			HTMLPane.RegisterMacroActionEvent rmae = (HTMLPane.RegisterMacroActionEvent) e;
			macroCallbacks.put(rmae.getType(), rmae.getMacro());
		}
		if (e instanceof HTMLPane.ChangeTitleActionEvent) {
			this.setTitle(((HTMLPane.ChangeTitleActionEvent) e).getNewTitle());
		}
		if (e instanceof HTMLPane.MetaTagActionEvent) {
			HTMLPane.MetaTagActionEvent mtae = (HTMLPane.MetaTagActionEvent) e;
			if (mtae.getName().equalsIgnoreCase("onChangeToken") ||
					mtae.getName().equalsIgnoreCase("onChangeSelection") ||
					mtae.getName().equalsIgnoreCase("onChangeImpersonated")) {
				macroCallbacks.put(mtae.getName(), mtae.getContent());
			}
		}
		if (e.getActionCommand().equals("Close")) {
			closeRequest();
		}
	}
}
