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

import java.util.ArrayList;
import java.util.List;

public class ChatProcessor {

	private List<ChatTranslationRuleGroup> translationRuleGroupList = new ArrayList<ChatTranslationRuleGroup>();
	
	public String process(String incoming) {
		if (incoming == null) {
			return null;
		}
		
		for (ChatTranslationRuleGroup ruleGroup : translationRuleGroupList) {
			if (!ruleGroup.isEnabled()) {
				continue;
			}
			incoming = ruleGroup.translate(incoming);
		}
		return incoming;
	}
	
	public void install(ChatTranslationRuleGroup ruleGroup) {
		translationRuleGroupList.add(ruleGroup);
	}
}
