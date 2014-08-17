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


public class HTMLFrameFactory {
	private HTMLFrameFactory() {
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
