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
package com.t3.util;

import junit.framework.TestCase;

public class StringUtilTest extends TestCase {

	
	public void testCountOccurances() throws Exception {
		
		String str = "<div>";
		
		assertEquals(0, StringUtil.countOccurances("", str));
		assertEquals(1, StringUtil.countOccurances("<div>", str));
		assertEquals(1, StringUtil.countOccurances("one<div>two", str));
		assertEquals(2, StringUtil.countOccurances("one<div>two<div>three", str));
		assertEquals(3, StringUtil.countOccurances("one<div>two<div>three<div>", str));
		
	}
}
