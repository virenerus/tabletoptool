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
package com.t3.persistence.converter;

import com.t3.model.Asset;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.extended.EncodedByteArrayConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class AssetImageConverter extends EncodedByteArrayConverter {
	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		// Ignore the image when creating 1.3.b65+ campaigns with assets...
		//System.out.println(context.toString());	// uncomment to set a breakpoint
	}

	// @formatter:off
	/*
	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		// But be sure to read them in if they exist.
		return super.unmarshal(reader, context);
	}
	*/
	// @formatter:on

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Asset.class.isAssignableFrom(type);
	}
}
