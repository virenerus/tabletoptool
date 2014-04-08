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
package com.t3.model.transform.campaign;

import com.t3.ModelVersionTransformation;

public class PCVisionTransform implements ModelVersionTransformation {
	private static final String searchFor = "<tokenType>PC";
	private static final String subField = "<hasSight>";

	@Override
	public String transform(String xml) {
		int index = 0;
		int start = 0;
		while ((start = xml.indexOf(searchFor, index)) > 0) {
			int sightPos = xml.indexOf(subField, start) + subField.length();
			while (Character.isWhitespace(xml.charAt(sightPos)))
				sightPos++;
			if (xml.charAt(sightPos) == 'f') {
				String pre = xml.substring(0, sightPos);
				String post = xml.substring(sightPos + "false".length());

				xml = pre + "true" + post;
			}
			index = sightPos;
		}

		return xml;
	}
}
