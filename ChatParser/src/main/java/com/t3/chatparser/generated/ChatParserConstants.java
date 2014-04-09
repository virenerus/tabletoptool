/* Generated By:JavaCC: Do not edit this line. ChatParserConstants.java */
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
package com.t3.chatparser.generated;


/** 
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ChatParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int MACRO_EXEC = 1;
  /** RegularExpression Id. */
  int CLEAR_CHAT = 2;
  /** RegularExpression Id. */
  int EMIT = 3;
  /** RegularExpression Id. */
  int EMOTE = 4;
  /** RegularExpression Id. */
  int GM = 5;
  /** RegularExpression Id. */
  int GOTO = 6;
  /** RegularExpression Id. */
  int INTEGER = 7;
  /** RegularExpression Id. */
  int NAME = 9;
  /** RegularExpression Id. */
  int GUID = 10;
  /** RegularExpression Id. */
  int IMPERSONATE = 11;
  /** RegularExpression Id. */
  int OOC = 12;
  /** RegularExpression Id. */
  int REPLY = 13;
  /** RegularExpression Id. */
  int ROLL = 14;
  /** RegularExpression Id. */
  int ROLL_GM = 15;
  /** RegularExpression Id. */
  int ROLL_ME = 16;
  /** RegularExpression Id. */
  int ROLL_SECRET = 17;
  /** RegularExpression Id. */
  int SELF = 18;
  /** RegularExpression Id. */
  int TABLE = 19;
  /** RegularExpression Id. */
  int TOKEN_SPEECH = 20;
  /** RegularExpression Id. */
  int WHISPER = 21;
  /** RegularExpression Id. */
  int UNKNOWN_COMMAND = 22;
  /** RegularExpression Id. */
  int EVERYTHING = 23;
  /** RegularExpression Id. */
  int CHAT_COMMAND_SLASH = 24;
  /** RegularExpression Id. */
  int DOLLAR_SIGN = 25;
  /** RegularExpression Id. */
  int CODE_START = 26;
  /** RegularExpression Id. */
  int TEXT = 27;
  /** RegularExpression Id. */
  int CODE_END = 28;
  /** RegularExpression Id. */
  int PLUS = 29;
  /** RegularExpression Id. */
  int MINUS = 30;
  /** RegularExpression Id. */
  int MULTIPLICATION = 31;
  /** RegularExpression Id. */
  int DIVISION = 32;
  /** RegularExpression Id. */
  int PARANTHESES_LEFT = 33;
  /** RegularExpression Id. */
  int PARANTHESES_RIGHT = 34;
  /** RegularExpression Id. */
  int NUMBER = 35;
  /** RegularExpression Id. */
  int FLOAT = 36;
  /** RegularExpression Id. */
  int DICE_D = 37;
  /** RegularExpression Id. */
  int DICE_KEEP = 38;
  /** RegularExpression Id. */
  int DICE_REROLL = 39;
  /** RegularExpression Id. */
  int DICE_SUCCESS = 40;
  /** RegularExpression Id. */
  int DICE_EXPLODING_SUCCESS = 41;
  /** RegularExpression Id. */
  int DICE_EXPLODING = 42;
  /** RegularExpression Id. */
  int DICE_OPEN = 43;
  /** RegularExpression Id. */
  int DICE_FUDGE = 44;
  /** RegularExpression Id. */
  int DICE_UBIQUITY = 45;
  /** RegularExpression Id. */
  int DICE_SHADOWRUN_EXPLODING_GREMLIN = 46;
  /** RegularExpression Id. */
  int DICE_SHADOWRUN_EXPLODING = 47;
  /** RegularExpression Id. */
  int DICE_SHADOWRUN_GREMLIN = 48;
  /** RegularExpression Id. */
  int DICE_SHADOWRUN = 49;

  /** Lexical state. */
  int CHATCOMMAND = 0;
  /** Lexical state. */
  int CC_ARGUMENTS = 1;
  /** Lexical state. */
  int DICE_EXPR = 2;
  /** Lexical state. */
  int REST = 3;
  /** Lexical state. */
  int DEFAULT = 4;
  /** Lexical state. */
  int AFTER_CC = 5;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "<MACRO_EXEC>",
    "<CLEAR_CHAT>",
    "<EMIT>",
    "<EMOTE>",
    "<GM>",
    "<GOTO>",
    "<INTEGER>",
    "\" \"",
    "<NAME>",
    "<GUID>",
    "<IMPERSONATE>",
    "\"ooc\"",
    "<REPLY>",
    "<ROLL>",
    "<ROLL_GM>",
    "<ROLL_ME>",
    "<ROLL_SECRET>",
    "\"self\"",
    "<TABLE>",
    "<TOKEN_SPEECH>",
    "<WHISPER>",
    "<UNKNOWN_COMMAND>",
    "<EVERYTHING>",
    "\"/\"",
    "\"$$\"",
    "\"$\"",
    "<TEXT>",
    "\"$\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"(\"",
    "\")\"",
    "<NUMBER>",
    "<FLOAT>",
    "\"d\"",
    "\"k\"",
    "\"r\"",
    "\"s\"",
    "\"es\"",
    "\"e\"",
    "\"o\"",
    "\"df\"",
    "\"du\"",
    "\"sr4eg\"",
    "\"sr4e\"",
    "\"sr4g\"",
    "\"sr4\"",
  };

}
