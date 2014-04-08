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

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class RegularExpressionTranslationRule extends AbstractChatTranslationRule {
	private static final Logger log = Logger.getLogger(RegularExpressionTranslationRule.class);
	private Pattern pattern;
	private final String replaceWith;

	public RegularExpressionTranslationRule(String pattern, String replaceWith) {
		try {
			this.pattern = Pattern.compile(pattern);
		} catch (Exception e) {
			log.error("Could not parse regex: " + pattern, e);
		}
		this.replaceWith = replaceWith;
	}

	@Override
	public String translate(String incoming) {
		if (pattern == null) {
			return incoming;
		}
		return pattern.matcher(incoming).replaceAll(replaceWith);
	}
}
