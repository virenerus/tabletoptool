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
package com.t3.client.ui.chat;

import junit.framework.TestCase;

public class RegularExpressionTranslationRuleTest extends TestCase {

	public void testIt() throws Exception {

		ChatTranslationRule rule = new RegularExpressionTranslationRule("one", "two");
		assertEquals("two two three", rule.translate("one two three"));
		
		rule = new RegularExpressionTranslationRule("(t.o)", "*$1*");
		assertEquals("one *two* three", rule.translate("one two three"));
		
	}
}
