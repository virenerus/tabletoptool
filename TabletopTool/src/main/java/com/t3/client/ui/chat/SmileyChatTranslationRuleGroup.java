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
package com.t3.client.ui.chat;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;

import com.t3.client.AppPreferences;
import com.t3.client.TabletopTool;
import com.t3.image.ImageUtil;

public class SmileyChatTranslationRuleGroup extends ChatTranslationRuleGroup {
	static final Logger log = Logger.getLogger(SmileyChatTranslationRuleGroup.class);
	private JPopupMenu emotePopup;

	public SmileyChatTranslationRuleGroup() {
		super("Smilies");
		initSmilies();
	}

	public JPopupMenu getEmotePopup() {
		return emotePopup;
	}

	@Override
	public boolean isEnabled() {
		return AppPreferences.getShowSmilies();
	}

	private void initSmilies() {
		// Load the smilies
		Properties smileyProps = new Properties();
		try {
			smileyProps.loadFromXML(ChatProcessor.class.getClassLoader().getResourceAsStream("com/t3/client/ui/chat/smileyMap.xml"));
		} catch (IOException ioe) {
			log.error("Could not load smiley map", ioe);
		}
		// Wrap values with img tag
		emotePopup = new JPopupMenu();
		for (Enumeration<?> e = smileyProps.propertyNames(); e.hasMoreElements();) {
			String key = (String) e.nextElement();

			// This is an incredibly bad hack to avoid writing an xml parser for the smiley map. I'm feeling lazy today.
			StringTokenizer strtok = new StringTokenizer(smileyProps.getProperty(key), "|");
			String value = strtok.nextToken();
			String example = strtok.nextToken();

			String imgValue = "<img src='cp://" + value + "'>";
			smileyProps.setProperty(key, imgValue);

			JMenuItem item = new JMenuItem(new InsertEmoteAction(value, example)) {
				{
					setPreferredSize(new Dimension(25, 16));
				}
			};
			emotePopup.add(item);
		}

		// Install the translation rules
		for (Enumeration<?> e = smileyProps.propertyNames(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			String value = smileyProps.getProperty(key);
			/*
			 * Make sure we're not in roll output. Wouldn't let me do this
			 * usinglookbehind :-/
			 */
			key = "^((?:[^\036]|\036[^\036]*\036)*)" + key;
			value = "$1" + value;
			addRule(new RegularExpressionTranslationRule(key, value));
		}
	}

	////
	// EMOTE 
	private class InsertEmoteAction extends AbstractAction {
		private final String insert;

		public InsertEmoteAction(String emoteImageSrc, String insert) {
			// This will force the image to be loaded into memory for use in the message panel
			try {
				putValue(Action.SMALL_ICON, new ImageIcon(ImageUtil.getImage(emoteImageSrc)));
			} catch (IOException ioe) {
				SmileyChatTranslationRuleGroup.log.error("Cannot load smileyEmote", ioe);
			}
			this.insert = insert;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			TabletopTool.getFrame().getCommandPanel().getCommandTextArea().setText(TabletopTool.getFrame().getCommandPanel().getCommandTextArea().getText() + insert);
			TabletopTool.getFrame().getCommandPanel().getCommandTextArea().requestFocusInWindow();
		}
	}
}
