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
import junit.framework.Assert;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.t3.chatparser.generated.ChatParser;
import com.t3.chatparser.generated.ParseException;

public class ParsedChatTest {
	
	@DataProvider(name="chatParserProvider", parallel=false) 
	public Object[][] getChatParserData () {
		return new Object[][] {
			{"hallo welt",
				null,
				"hallo welt",
				null,
				null,false},
			{"return \"hallo\";",
				null,
				"return \"hallo\";",
				null,
				null,false},
			{"/macro print(\"Hallo\");",
				MACRO_EXEC,
				"",
				new String[] {"print(\"Hallo\");"},
				null,false},
			{"/m allo",
				MACRO_EXEC,
				"",
				new String[] {"allo"},
				null,false},
			{"/clear",
				CLEAR_CHAT,
				"",
				null,
				null,false},
			{"/clr",
				CLEAR_CHAT,
				"",
				null,
				null,false},
			{"/emit",
				EMIT,
				"",
				null,
				null,false},
			{"/e",
				EMIT,
				"",
				null,
				null,false},
			{"/emote",
				EMOTE,
				"",
				null,
				null,false},
			{"/me",
				EMOTE,
				"",
				null,
				null,false},
			{"/gm",
				GM,
				"",
				null,
				null,false},
			{"/togm",
				GM,
				"",
				null,
				null,false},
			{"/goto 4 6",
				GOTO,
				"",
				new String[]{"4","6"},
				null,false},
			{"/goto 4 hallo carummel",
				null,
				"/goto 4 hallo carummel",
				null,
				null,true},
			{"/g -14 28",
				GOTO,
				"",
				new String[]{"-14","28"},
				null,false},
			{"/impersonate AAAAAFt7VPUBAAAAAAAAAAvv84756312",
				IMPERSONATE,
				"",
				new String[]{"AAAAAFt7VPUBAAAAAAAAAAvv84756312"},
				null,false},
			{"/impersonate",
				CLEAR_IMPERSONATE,
				"",
				null,
				null,false},
			{"/im AAAAAFt7VPUBAAAAAAAAAAvv84756312",
				IMPERSONATE,
				"",
				new String[]{"AAAAAFt7VPUBAAAAAAAAAAvv84756312"},
				null,false},
			{"/ooc",
				OOC,
				"",
				null,
				null,false},
			{"/reply",
				REPLY,
				"",
				null,
				null,false},
			{"/rep",
				REPLY,
				"",
				null,
				null,false},
			{"/roll 1d6",
				ROLL,
				"1d6",
				null,
				ExpressionPart.class,false},
			{"/r 1d6",
				ROLL,
				"1d6",
				null,
				ExpressionPart.class,false},
			{"/rollgm 1d6",
				ROLL_GM,
				"1d6",
				null,
				ExpressionPart.class,false},
			{"/rgm 1d6",
				ROLL_GM,
				"1d6",
				null,
				ExpressionPart.class,false},
			{"/rollme 1d6",
				ROLL_ME,
				"1d6",
				null,
				ExpressionPart.class,false},
			{"/rme 1d6",
				ROLL_ME,
				"1d6",
				null,
				ExpressionPart.class,false},
			{"/rollsecret 1d6",
				ROLL_SECRET,
				"1d6",
				null,
				ExpressionPart.class,false},
			{"/rsec 1d6",
				ROLL_SECRET,
				"1d6",
				null,
				ExpressionPart.class,false},
			{"/self",
				SELF,
				"",
				null,
				null,false},
			{"/table name",
				TABLE,
				"",
				new String[]{"name"},
				null,false},
			{"/tbl name",
				TABLE,
				"",
				new String[]{"name"},
				null,false},
			{"/tsay baaam",
				TOKEN_SPEECH,
				"",
				new String[]{"baaam"},
				null,false},
			{"/ts baaam",
				TOKEN_SPEECH,
				"",
				new String[]{"baaam"},
				null,false},
			{"/whisper Hans hello Hans",
				WHISPER,
				"Hans hello Hans",
				null,
				TextPart.class,false},
			{"/w Johannes hello Johannes",
				WHISPER,
				"Johannes hello Johannes",
				null,
				TextPart.class,false},
			{"1d6",
				null,
				"1d6",
				null,
				null,false},
			{"1d6 / 4",
				null,
				"1d6 / 4",
				null,
				null,false},
			{"1d6 /gm 4",
				null,
				"1d6 /gm 4",
				null,
				null,false},
			{"hallo $1d6+ 4$",
				null,
				"hallo 1d6+4",
				null,
				null,false},
			{"hallo §1d6+ 4§",
				null,
				"hallo §1d6+ 4§",
				null,
				null,false},
			{"I own 5 $$ and 2 more",
				null,
				"I own 5 $ and 2 more",
				null,
				null,false},
			{"1d6+4 / 4d7d1 is parsed as $1d6+4 / 4d7d1$",
				null,
				"1d6+4 / 4d7d1 is parsed as 1d6+4÷4d7d1",
				null,
				null,false},
			{"",
				null,
				"",
				null,
				null,false},
			{"$-4*5d8/(0+3-1)$",
				null,
				"-4×5d8÷0+3-1",
				null,
				null,false},
			{"/r 1d 6+4+2d6",
				ROLL,
				"1d6+4+2d6",
				null,
				ExpressionPart.class,false},
		};
	}

	@Test(dataProvider="chatParserProvider")
	public void testParsedChat(String chatText, ChatCommand expectedChatCommand, String expectedString, String[] expectedArguments, Class<? extends ChatPart> firstExpectedPart, boolean expectsParseException) throws UnknownCommandException {
		ChatParser cp=new ChatParser(chatText);
		ParsedChat pc;
		try {
			pc = cp.parse();
	    	
		} catch (ParseException e) {
			if(!expectsParseException)
				Assert.fail("Parse Exception: "+e.getMessage());
			pc=new ParsedChat(new TextPart(chatText));				
		}
		assertEquals(pc.getChatCommand(), expectedChatCommand, "ChatCommand");
    	assertEquals(pc.getArguments(), expectedArguments, "Arguments");
    	assertEquals(getStringRep(pc), expectedString, "String Representation");
    	if(firstExpectedPart!=null) {
    		assertTrue(pc.size()>0, "At least one part");
    		assertTrue(firstExpectedPart.isInstance(pc.get(0)),"First part is "+firstExpectedPart.getSimpleName());
    	}
    }
	
	@Test(expectedExceptions={UnknownCommandException.class})
	public void unknownCommandTest() throws UnknownCommandException, ParseException {
		testParsedChat("/karwarble this hit", null, "/karwarble this hit", null, null, false);
	}

	private String getStringRep(ParsedChat pc) {
		StringBuilder sb = new StringBuilder();
		for(ChatPart cp:pc) {
			sb.append(cp.toString());
		}
		return sb.toString();
	}
}
