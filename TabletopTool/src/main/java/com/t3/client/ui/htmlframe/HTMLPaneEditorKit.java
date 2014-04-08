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

import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;

import com.t3.client.swing.MessagePanelEditorKit;

@SuppressWarnings("serial")
class HTMLPaneEditorKit extends MessagePanelEditorKit {
	private final HTMLPaneViewFactory viewFactory;

	HTMLPaneEditorKit(HTMLPane htmlPane) {
		setUseMacroLinkToolTips(false);
		viewFactory = new HTMLPaneViewFactory(super.getViewFactory(), htmlPane);
	}

	@Override
	public ViewFactory getViewFactory() {
		return viewFactory;
	}

	@Override
	public HTMLEditorKit.Parser getParser() {
		return super.getParser();
	}
}
