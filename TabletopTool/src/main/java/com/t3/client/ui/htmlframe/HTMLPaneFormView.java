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

import java.awt.Component;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.PlainDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.FormView;
import javax.swing.text.html.HTML;

import org.apache.log4j.Logger;

public class HTMLPaneFormView extends FormView {

	private static final Logger LOGGER = Logger.getLogger(HTMLPaneFormView.class);

	private final HTMLPane htmlPane;

	/**
	 * Creates a new HTMLPaneFormView.
	 * 
	 * @param elem
	 *            The element this is a view for.
	 * @param pane
	 *            The HTMLPane this element resides on.
	 */
	public HTMLPaneFormView(Element elem, HTMLPane pane) {
		super(elem);
		htmlPane = pane;
	}

	@Override
	protected Component createComponent() {
		Component c = null;

		AttributeSet attr = getElement().getAttributes();
		HTML.Tag t = (HTML.Tag)
				attr.getAttribute(StyleConstants.NameAttribute);

		if (t == HTML.Tag.TEXTAREA) {
			JScrollPane sp = (JScrollPane) super.createComponent();
			JTextArea area = (JTextArea) sp.getViewport().getView();
			area.setLineWrap(true);
			area.setWrapStyleWord(true);
			c = new JScrollPane(area,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
		else {
			c = super.createComponent();
		}

		return c;
	}

	@Override
	protected void submitData(String data) {

		// Find the form
		Element formElement = null;
		for (Element e = getElement(); e != null; e = e.getParentElement()) {
			if (e.getAttributes().getAttribute(StyleConstants.NameAttribute) == HTML.Tag.FORM) {
				formElement = e;
				break;
			}
		}

		if (formElement != null) {
			AttributeSet att = formElement.getAttributes();
			String action = "";
			if (att.getAttribute(HTML.Attribute.ACTION) != null) {
				action = att.getAttribute(HTML.Attribute.ACTION).toString();
			}
			String method = "get";
			if (att.getAttribute(HTML.Attribute.METHOD) != null) {
				method = att.getAttribute(HTML.Attribute.METHOD).toString().toLowerCase();
			}
			htmlPane.doSubmit(method, action, data);
		}

	}

	@Override
	protected void imageSubmit(String data) {
		Element formElement = null;
		for (Element e = getElement(); e != null; e = e.getParentElement()) {
			if (e.getAttributes().getAttribute(StyleConstants.NameAttribute) == HTML.Tag.FORM) {
				formElement = e;
				break;
			}
		}

		if (formElement != null) {
			String imageMapName = data.replaceFirst("\\..*", "");

			Map<String, String> fdata = new HashMap<String, String>();
			fdata.putAll(getDataFrom(formElement, imageMapName));
			StringBuilder sb = new StringBuilder();
			for (String s : fdata.keySet()) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append(s).append("=").append(fdata.get(s));
			}
			sb.append("&").append(data);
			submitData(sb.toString());
		} else {
			submitData(data);
		}
	}

	private Map<String, String> getDataFrom(Element ele, String selectedImageMap) {
		Map<String, String> vals = new HashMap<String, String>();

		for (int i = 0; i < ele.getElementCount(); i++) {
			Element e = ele.getElement(i);
			AttributeSet as = e.getAttributes();

			if (as.getAttribute(StyleConstants.ModelAttribute) != null || as.getAttribute(HTML.Attribute.TYPE) != null) {
				String type = (String) as.getAttribute(HTML.Attribute.TYPE);
				String name = (String) as.getAttribute(HTML.Attribute.NAME);
				Object model = as.getAttribute(StyleConstants.ModelAttribute);

				if (type == null && model instanceof PlainDocument) {// Text area has no HTML.Attribute.TYPE
					PlainDocument pd = (PlainDocument) model;
					try {
						vals.put(name, encode(pd.getText(0, pd.getLength())));
					} catch (BadLocationException e1) {
						LOGGER.error(e1.getStackTrace());
					}
				} else if (type == null && model instanceof ComboBoxModel) {
					vals.put(name, ((ComboBoxModel) model).getSelectedItem().toString());
				} else if ("text".equals(type)) {
					PlainDocument pd = (PlainDocument) model;
					try {
						vals.put(name, encode(pd.getText(0, pd.getLength())));
					} catch (BadLocationException e1) {
						LOGGER.error(e1.getStackTrace());
					}
				} else if ("submit".equals(type)) {
					// Ignore
				} else if ("image".equals(type)) {
					if (name != null && name.equals(selectedImageMap)) {
						String val = (String) as.getAttribute(HTML.Attribute.VALUE);
						vals.put(name + ".value", encode(val == null ? "" : val));
					}
				} else if ("radio".equals(type)) {
					if (as.getAttribute(HTML.Attribute.CHECKED) != null) {
						vals.put(name, encode(encode((String) as.getAttribute(HTML.Attribute.VALUE))));
					}
				} else if ("checkbox".equals(type)) {
					if (as.getAttribute(HTML.Attribute.CHECKED) != null) {
						vals.put(name, encode(encode((String) as.getAttribute(HTML.Attribute.VALUE))));
					}
				} else if ("password".equals(type)) {
					PlainDocument pd = (PlainDocument) model;
					try {
						vals.put(name, encode(pd.getText(0, pd.getLength())));
					} catch (BadLocationException e1) {
						LOGGER.error(e1.getStackTrace());
					}
				} else if ("hidden".equals(type)) {
					vals.put(name, encode(encode((String) as.getAttribute(HTML.Attribute.VALUE))));
				}
			}
			vals.putAll(getDataFrom(e, selectedImageMap));

		}
		return vals;
	}

	private String encode(String str) {
		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getStackTrace());
			return str;
		}
	}
}
