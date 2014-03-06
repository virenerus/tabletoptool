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


public class HTMLFrameFactory {
	private HTMLFrameFactory() {
	}


	/**
	 * Shows a dialog or frame based on the options.
	 * 
	 * @param name
	 *            The name of the dialog or frame.
	 * @param isFrame
	 *            Is it a frame.
	 * @param properties
	 *            The properties that determine the attributes of the frame or dialog.
	 * @param html
	 *            The html contents of frame or dialog.
	 */
	public static void show(String name, boolean isFrame, String properties, String html) {
		/* removed with the event removal
		if (listener == null) {
			listener = new HTMLFrameFactory.Listener();
		}*/
		boolean input = false;
		boolean temporary = false;
		int width = -1;
		int height = -1;
		String title = name;
		boolean hasFrame = true;
		boolean closeButton = true;

		if (properties != null && !properties.isEmpty()) {
			String[] opts = properties.split(";");
			for (String opt : opts) {
				String[] vals = opt.split("=");
				String key = vals[0].trim();
				String value = vals.length > 1 ? vals[1].trim() : "";
				String keyLC = key.toLowerCase();
				if (keyLC.equals("input")) {
					try {
						int v = Integer.parseInt(value);
						if (v != 0) {
							input = true;
							closeButton = !input;
						}
					} catch (NumberFormatException e) {
						// Ignoring the value; shouldn't we warn the user?
					}
				} else if (keyLC.equals("temporary") || keyLC.equals("undecorated") || keyLC.equals("temp")) {
					try {
						int v = Integer.parseInt(value);
						if (v != 0) {
							temporary = true;
						}
					} catch (NumberFormatException e) {
						// Ignoring the value; shouldn't we warn the user?
					}
				} else if (keyLC.equals("width")) {
					try {
						width = Integer.parseInt(value);
					} catch (NumberFormatException e) {
						// Ignoring the value; shouldn't we warn the user?
					}
				} else if (keyLC.equals("height")) {
					try {
						height = Integer.parseInt(value);
					} catch (NumberFormatException e) {
						// Ignoring the value; shouldn't we warn the user?
					}
				} else if (keyLC.equals("title")) {
					title = value;
				} else if (keyLC.equals("noframe")) {
					try {
						int v = Integer.parseInt(value);
						if (v != 0) {
							hasFrame = false;
						}
					} catch (NumberFormatException e) {
						// Ignoring the value; shouldn't we warn the user?
					}
				} else if (keyLC.equals("closebutton")) {
					try {
						int v = Integer.parseInt(value);
						if (v == 0) {
							closeButton = false;
						}
					} catch (NumberFormatException e) {
						// Ignoring the value; shouldn't we warn the user?
					}
				}
			}
		}
		if (isFrame) {
			HTMLFrame.showFrame(name, title, width, height, html);
		} else {
			HTMLDialog.showDialog(name, title, width, height, hasFrame, input, temporary, closeButton, html);
		}
	}
	/* This is how an event system could be implemented here
	//TODO reimplment event system but better
	public static class Listener implements ModelChangeListener, AppEventListener {
		public Listener() {
			TabletopTool.getEventDispatcher().addListener(this, TabletopTool.ZoneEvent.Activated);
			TabletopTool.getFrame().getCurrentZoneRenderer().getZone().addModelChangeListener(this);
		}

		public void modelChanged(ModelChangeEvent event) {
			if (event.eventType == Event.TOKEN_CHANGED) {
				Token token = (Token) event.getArg();
				if (TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokenSet().contains(token)) {
					selectedListChanged();
				}
				final CommandPanel cpanel = TabletopTool.getFrame().getCommandPanel();
				if (token.getName().equals(cpanel.getIdentity()) || token.getId().equals(cpanel.getIdentityGUID())) {
					impersonateToken();
				}
				tokenChanged(token);
			}
		}

		public void handleAppEvent(AppEvent event) {
			Zone oldZone = (Zone) event.getOldValue();
			Zone newZone = (Zone) event.getNewValue();

			if (oldZone != null) {
				oldZone.removeModelChangeListener(this);
			}
			newZone.addModelChangeListener(this);
		}
	}*/

	public static boolean isVisible(boolean isFrame, String name) {
		if (isFrame) {
			return HTMLFrame.isVisible(name);
		} else {
			return HTMLDialog.isVisible(name);
		}
	}

	public static void close(boolean isFrame, String name) {
		if (isFrame) {
			HTMLFrame.close(name);
		} else {
			HTMLDialog.close(name);
		}
	}
}
