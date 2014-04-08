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
package com.t3.chatparser;

public class TextPart extends ChatPart {

	private final StringBuilder text;

	public TextPart(String text) {
		this.text=new StringBuilder(text);
	}
	
	@Override
	public String getDefaultTextRepresentation() {
		return text.toString();
	}

	public void append(String image) {
		text.append(image);
	}
	
	@Override
	public String toString() {
		return text.toString();
	}
}
