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

import java.util.LinkedList;
import java.util.List;

public class ChatTranslationRuleGroup {
	private final String name;
	private final List<ChatTranslationRule> translationRuleList = new LinkedList<ChatTranslationRule>();

	public ChatTranslationRuleGroup(String name) {
		this(name, null);
	}

	public ChatTranslationRuleGroup(String name, List<ChatTranslationRule> translationRuleList) {
		this.name = name;
		if (translationRuleList != null) {
			this.translationRuleList.addAll(translationRuleList);
		}
	}

	public void addRule(ChatTranslationRule rule) {
		translationRuleList.add(rule);
	}

	public boolean isEnabled() {
		return true;
	}

	public String getName() {
		return name;
	}

	public String translate(String incoming) {
		if (incoming == null) {
			return null;
		}
		for (ChatTranslationRule rule : translationRuleList) {
			incoming = rule.translate(incoming);
		}
		return incoming;
	}
}
