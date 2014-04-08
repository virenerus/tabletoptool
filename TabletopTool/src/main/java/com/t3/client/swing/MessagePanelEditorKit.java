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

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

public class MessagePanelEditorKit extends HTMLEditorKit {

	private ViewFactory viewFactory = new MessagePanelViewFactory();

	private ImageLoaderCache imageCache = new ImageLoaderCache();

	private boolean macroLinkTTips = true;
	
	public MessagePanelEditorKit() {
		viewFactory = new MessagePanelViewFactory();
	}

	public void setUseMacroLinkToolTips(boolean show) {
		macroLinkTTips = show;
	}
	
	@Override
	public ViewFactory getViewFactory() {
		return viewFactory;
	}
	
	public void flush() {
		imageCache.flush();
	}
	
	private class MessagePanelViewFactory extends HTMLFactory {

		@Override
		public View create(Element elem) {
			
	        AttributeSet attrs = elem.getAttributes();
	 	    Object elementName = attrs.getAttribute(AbstractDocument.ElementNameAttribute);
		    Object o = (elementName != null) ? null : attrs.getAttribute(StyleConstants.NameAttribute);
		    if (o instanceof HTML.Tag) {
				HTML.Tag kind = (HTML.Tag) o;
				if (kind == HTML.Tag.IMG) {
				    return new MessagePanelImageView(elem, imageCache);
				}
				if (kind == HTML.Tag.CONTENT) {
					return new TooltipView(elem, macroLinkTTips);
				}
		    }

			return super.create(elem);
		}
	}
}
