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
package com.t3.client.ui.commandpanel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ToolTipManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;

import org.apache.log4j.Logger;

import com.t3.client.AppPreferences;
import com.t3.client.TabletopTool;
import com.t3.client.swing.MessagePanelEditorKit;
import com.t3.macro.MacroEngine;
import com.t3.macro.MacroException;
import com.t3.model.chat.Speaker;
import com.t3.model.chat.TextMessage;

//FIXMESOON full of weird stuff that should no longer be required -> use a diffrent script here
public class MessagePanel extends JPanel {

	private static final Logger log = Logger.getLogger(MessagePanel.class);
	
	private final JScrollPane scrollPane;
	private final HTMLDocument document;
	private final JEditorPane textPane;
	private Speaker lastSpeaker;

	private static final String SND_MESSAGE_RECEIVED = "messageReceived";

	/**
	 * From ImageView
	 */
	private static final String IMAGE_CACHE_PROPERTY = "imageCache";

	public static final Pattern URL_PATTERN = Pattern.compile("([^:]*)://([^/]*)/([^?]*)(?:\\?(.*))?");

	public MessagePanel() {
		setLayout(new GridLayout());

		textPane = new JEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new MessagePanelEditorKit());
		textPane.addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent e) {
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentResized(ComponentEvent e) {
				// Jump to the bottom on new text
				if (!TabletopTool.getFrame().getCommandPanel().getScrollLockButton().isSelected()) {
					Rectangle rowBounds = new Rectangle(0, textPane.getSize().height, 1, 1);
					textPane.scrollRectToVisible(rowBounds);
				}
			}

			@Override
			public void componentShown(ComponentEvent e) {
			}
		});
		textPane.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					if (e.getURL() != null) {
						TabletopTool.showDocument(e.getURL().toString());
					} else {
						Matcher m = URL_PATTERN.matcher(e.getDescription());
						if (m.matches()) {
							if (m.group(1).equalsIgnoreCase("macro")) {
								try {
									MacroEngine.getInstance().evaluate(e.getDescription());
								} catch (MacroException e1) {
									e1.printStackTrace();
									log.error("Error while trying to execute script from HTML", e1);
									TabletopTool.addMessage(TextMessage.me(e1.getHTMLErrorMessage()));
								}
							}
						}
					}
				}
			}
		});
		ToolTipManager.sharedInstance().registerComponent(textPane);

		document = (HTMLDocument) textPane.getDocument();

		// Initialize and prepare for usage
		refreshRenderer();

		scrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);
		scrollPane.getViewport().setBorder(null);
		scrollPane.getViewport().setBackground(Color.white);
		scrollPane.getVerticalScrollBar().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

				boolean lock = (scrollPane.getSize().height + scrollPane.getVerticalScrollBar().getValue()) < scrollPane.getVerticalScrollBar().getMaximum();

				// The user has manually scrolled the scrollbar, Scroll lock time baby !
				TabletopTool.getFrame().getCommandPanel().getScrollLockButton().setSelected(lock);
			}
		});

		add(scrollPane);
		clearMessages();

		TabletopTool.getSoundManager().registerSoundEvent(SND_MESSAGE_RECEIVED, TabletopTool.getSoundManager().getRegisteredSound("Clink"));
	}

	public void refreshRenderer() {
		// Create the style
		StyleSheet style = document.getStyleSheet();
		style.addRule("body { font-family: sans-serif; font-size: " + AppPreferences.getFontSize() + "pt}");
		style.addRule("div {margin-bottom: 5px}");
		style.addRule("span.roll {background:#efefef}");
		setTrustedMacroPrefixColors(AppPreferences.getTrustedPrefixFG(), AppPreferences.getTrustedPrefixBG());
		repaint();
	}

	public void setTrustedMacroPrefixColors(Color foreground, Color background) {
		StringBuilder sb = new StringBuilder();
		sb.append("span.trustedPrefix {background: #").append(String.format("%06X", (background.getRGB() & 0xFFFFFF)));
		sb.append("; color: #").append(String.format("%06X", (foreground.getRGB() & 0xFFFFFF))).append("}");
		StyleSheet style = document.getStyleSheet();
		style.addRule(sb.toString());
		repaint();
	}

	public String getMessagesText() {

		return textPane.getText();
	}

	public void clearMessages() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				textPane.setText("<html><body id=\"body\"></body></html>");
				((MessagePanelEditorKit) textPane.getEditorKit()).flush();
			}
		});
	}

	/*
	 * We use ASCII control characters to mark off the rolls so that there's no limitation on what (printable)
	 * characters the output can include Rolls look like "\036roll output\036" or "\036tooltip\037roll output\036" or
	 * "\036\001format info\002roll output\036" or "\036\001format info\002tooltip\037roll output\036"
	 */
	private static Pattern roll_pattern = Pattern.compile("\036(?:\001([^\002]*)\002)?([^\036\037]*)(?:\037([^\036]*))?\036");

	public void addMessage(final TextMessage message) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				String output = message.getMessage();
				// Auto inline expansion for {HTTP|HTTPS} URLs
				//TODO test this
				output = output.replaceAll("(https?://[\\w.%-/~?&+#=]+)", "<a href='$2'>$2</a>");

				if (!message.getSource().equals(TabletopTool.getPlayer().getName())) {
					// TODO change this so 'macro' is case-insensitive
					Matcher m = Pattern.compile("href=([\"'])\\s*(macro://(?:[^/]*)/(?:[^?]*)(?:\\?(?:.*?))?)\\1\\s*", Pattern.CASE_INSENSITIVE).matcher(output);
					while (m.find()) {
						//FIXMESOON MacroLinkFunction.getInstance().processMacroLink(m.group(2));
					}
				}
				output=output.trim();
				// if rolls not being visible to this user result in an empty message, display nothing
				if(!output.isEmpty()) {

					//this tries to insert the message so that names are not printed a thousand times
					try {
						Element element = document.getElement("body");
						if(lastSpeaker != null && lastSpeaker.equals(message.getSpeaker())) {
							Element lastDiv=element.getElement(element.getElementCount()-1);
							document.insertBeforeEnd(lastDiv.getElement(0).getElement(0).getElement(1),"<div>"+output+"</div>");
						}
						else
							if(message.getSpeaker()==null)
								document.insertBeforeEnd(element, "<div>" + output + "</div>");
							else
								document.insertBeforeEnd(element, 
										"<div><table cellpadding=0><tr><td valign=top rowspan=0 style=\"margin-right: 5px\">"+
										message.getSpeaker().toHTML()+ "</td><td valign=top align=left><div>" + output + "</div></td></tr></table></div>");
						lastSpeaker=message.getSpeaker();

						if (!message.getSource().equals(TabletopTool.getPlayer().getName())) {
							TabletopTool.playSound(SND_MESSAGE_RECEIVED);
						}
					} catch (IOException ioe) {
						ioe.printStackTrace();
					} catch (BadLocationException ble) {
						ble.printStackTrace();
					}
				}
			}
		});
	}
}
