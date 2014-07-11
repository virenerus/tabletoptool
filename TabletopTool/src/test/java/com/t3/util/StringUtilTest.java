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

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

public class StringUtilTest {

	
	@Test
	public void testCountOccurances() throws Exception {
		
		String str = "<div>";
		
		AssertJUnit.assertEquals(0, StringUtil.countOccurances("", str));
		AssertJUnit.assertEquals(1, StringUtil.countOccurances("<div>", str));
		AssertJUnit.assertEquals(1, StringUtil.countOccurances("one<div>two", str));
		AssertJUnit.assertEquals(2, StringUtil.countOccurances("one<div>two<div>three", str));
		AssertJUnit.assertEquals(3, StringUtil.countOccurances("one<div>two<div>three<div>", str));
		
	}
}
