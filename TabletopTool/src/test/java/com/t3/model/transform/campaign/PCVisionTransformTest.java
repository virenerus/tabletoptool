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

import junit.framework.TestCase;

public class PCVisionTransformTest extends TestCase {

	public void testIt() throws Exception {
		
		String str = "one two three <tokenType>PC</tokenType>blah blah blah<hasSight>false</hasSight>something something";
		assertEquals("one two three <tokenType>PC</tokenType>blah blah blah<hasSight>true</hasSight>something something", new PCVisionTransform().transform(str));
	}
}
