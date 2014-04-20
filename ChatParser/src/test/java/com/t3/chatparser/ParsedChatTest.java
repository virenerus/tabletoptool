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
package com.t3.chatparser;

import static com.t3.chatparser.ChatCommand.CLEAR_CHAT;
import static com.t3.chatparser.ChatCommand.CLEAR_IMPERSONATE;
import static com.t3.chatparser.ChatCommand.EMIT;
import static com.t3.chatparser.ChatCommand.EMOTE;
import static com.t3.chatparser.ChatCommand.GM;
import static com.t3.chatparser.ChatCommand.GOTO;
import static com.t3.chatparser.ChatCommand.IMPERSONATE;
import static com.t3.chatparser.ChatCommand.MACRO_EXEC;
import static com.t3.chatparser.ChatCommand.OOC;
import static com.t3.chatparser.ChatCommand.REPLY;
import static com.t3.chatparser.ChatCommand.ROLL;
import static com.t3.chatparser.ChatCommand.ROLL_GM;
import static com.t3.chatparser.ChatCommand.ROLL_ME;
import static com.t3.chatparser.ChatCommand.ROLL_SECRET;
import static com.t3.chatparser.ChatCommand.SELF;
import static com.t3.chatparser.ChatCommand.TABLE;
import static com.t3.chatparser.ChatCommand.TOKEN_SPEECH;
import static com.t3.chatparser.ChatCommand.WHISPER;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.t3.chatparser.generated.ChatParser;

public class ParsedChatTest {
	
	@DataProvider(name="chatParserProvider", parallel=false) 
	public Object[][] getChatParserData () {
		return new Object[][] {
			{"/macro print(\"Hallo\");",
				MACRO_EXEC,
				"",
				new String[] {"print(\"Hallo\");"},
				null},
			{"/m allo",
				MACRO_EXEC,
				"",
				new String[] {"allo"},
				null},
			{"/clear",
				CLEAR_CHAT,
				"",
				null,
				null},
			{"/clr",
				CLEAR_CHAT,
				"",
				null,
				null},
			{"/emit",
				EMIT,
				"",
				null,
				null},
			{"/e",
				EMIT,
				"",
				null,
				null},
			{"/emote",
				EMOTE,
				"",
				null,
				null},
			{"/me",
				EMOTE,
				"",
				null,
				null},
			{"/gm",
				GM,
				"",
				null,
				null},
			{"/togm",
				GM,
				"",
				null,
				null},
			{"/goto 4 6",
				GOTO,
				"",
				new String[]{"4","6"},
				null},
			{"/goto 4 hallo carummel",
				null,
				"/goto 4 hallo carummel",
				null,
				null},
			{"/g -14 28",
				GOTO,
				"",
				new String[]{"-14","28"},
				null},
			{"/impersonate AAAAAFt7VPUBAAAAAAAAAAvv84756312",
				IMPERSONATE,
				"",
				new String[]{"AAAAAFt7VPUBAAAAAAAAAAvv84756312"},
				null},
			{"/impersonate",
				CLEAR_IMPERSONATE,
				"",
				null,
				null},
			{"/im AAAAAFt7VPUBAAAAAAAAAAvv84756312",
				IMPERSONATE,
				"",
				new String[]{"AAAAAFt7VPUBAAAAAAAAAAvv84756312"},
				null},
			{"/ooc",
				OOC,
				"",
				null,
				null},
			{"/reply",
				REPLY,
				"",
				null,
				null},
			{"/rep",
				REPLY,
				"",
				null,
				null},
			{"/roll 1d6",
				ROLL,
				"1d6",
				null,
				ExpressionPart.class},
			{"/r 1d6",
				ROLL,
				"1d6",
				null,
				ExpressionPart.class},
			{"/rollgm 1d6",
				ROLL_GM,
				"1d6",
				null,
				ExpressionPart.class},
			{"/rgm 1d6",
				ROLL_GM,
				"1d6",
				null,
				ExpressionPart.class},
			{"/rollme 1d6",
				ROLL_ME,
				"1d6",
				null,
				ExpressionPart.class},
			{"/rme 1d6",
				ROLL_ME,
				"1d6",
				null,
				ExpressionPart.class},
			{"/rollsecret 1d6",
				ROLL_SECRET,
				"1d6",
				null,
				ExpressionPart.class},
			{"/rsec 1d6",
				ROLL_SECRET,
				"1d6",
				null,
				ExpressionPart.class},
			{"/self",
				SELF,
				"",
				null,
				null},
			{"/table name",
				TABLE,
				"",
				new String[]{"name"},
				null},
			{"/tbl name",
				TABLE,
				"",
				new String[]{"name"},
				null},
			{"/tsay baaam",
				TOKEN_SPEECH,
				"",
				new String[]{"baaam"},
				null},
			{"/ts baaam",
				TOKEN_SPEECH,
				"",
				new String[]{"baaam"},
				null},
			{"/whisper Hans hello Hans",
				WHISPER,
				"Hans hello Hans",
				null,
				TextPart.class},
			{"/w Johannes hello Johannes",
				WHISPER,
				"Johannes hello Johannes",
				null,
				TextPart.class},
			{"1d6",
				null,
				"1d6",
				null,
				null},
			{"1d6 / 4",
				null,
				"1d6 / 4",
				null,
				null},
			{"1d6 /gm 4",
				null,
				"1d6 /gm 4",
				null,
				null},
			{"hallo $1d6+ 4$",
				null,
				"hallo 1d6+4",
				null,
				null},
			{"hallo §1d6+ 4§",
				null,
				"hallo §1d6+ 4§",
				null,
				null},
			{"I own 5 $$ and 2 more",
				null,
				"I own 5 $ and 2 more",
				null,
				null},
			{"1d6+4 / 4d7d1 is parsed as $1d6+4 / 4d7d1$",
				null,
				"1d6+4 / 4d7d1 is parsed as 1d6+4÷4d7d1",
				null,
				null},
			{"",
				null,
				"",
				null,
				null},
			{"$-4*5d8/(0+3-1)$",
				null,
				"-4×5d8÷0+3-1",
				null,
				null},
			{"/r 1d 6+4+2d6",
				ROLL,
				"1d6+4+2d6",
				null,
				ExpressionPart.class},
		};
	}

	@Test(dataProvider="chatParserProvider")
	public void testParsedChat(String chatText, ChatCommand expectedChatCommand, String expectedString, String[] expectedArguments, Class<? extends ChatPart> firstExpectedPart) throws UnknownCommandException {
		ChatParser cp=new ChatParser(chatText);
    	ParsedChat pc=cp.parse();
    	assertEquals(pc.getChatCommand(), expectedChatCommand, "ChatCommand");
    	assertEquals(pc.getArguments(), expectedArguments, "Arguments");
    	assertEquals(getStringRep(pc), expectedString, "String Representation");
    	if(firstExpectedPart!=null) {
    		assertTrue(pc.size()>0, "At least one part");
    		assertTrue(firstExpectedPart.isInstance(pc.get(0)),"First part is "+firstExpectedPart.getSimpleName());
    	}
    }
	
	@Test(expectedExceptions={UnknownCommandException.class})
	public void unknownCommandTest() throws UnknownCommandException {
		testParsedChat("/karwarble this hit", null, "/karwarble this hit", null, null);
	}

	private String getStringRep(ParsedChat pc) {
		StringBuilder sb = new StringBuilder();
		for(ChatPart cp:pc) {
			sb.append(cp.toString());
		}
		return sb.toString();
	}
}
