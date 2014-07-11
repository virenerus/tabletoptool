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
package com.t3.model.drawing;

import com.t3.xstreamversioned.SerializationVersion;


@SerializationVersion(0)
public class DrawnElement {

	private Drawable drawable;
	private Pen pen;
	
	public DrawnElement(Drawable drawable, Pen pen) {
		this.drawable = drawable;
		this.pen = pen;
	}
	
	public Drawable getDrawable() {
		return drawable;
	}
	
	public Pen getPen() {
		return pen;
	}
}
