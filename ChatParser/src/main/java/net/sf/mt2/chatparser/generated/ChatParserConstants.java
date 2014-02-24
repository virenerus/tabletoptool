/* Generated By:JavaCC: Do not edit this line. ChatParserConstants.java */
package net.sf.mt2.chatparser.generated;


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
  int CODE_START = 25;
  /** RegularExpression Id. */
  int TEXT = 26;
  /** RegularExpression Id. */
  int CODE_END = 27;
  /** RegularExpression Id. */
  int PLUS = 28;
  /** RegularExpression Id. */
  int MINUS = 29;
  /** RegularExpression Id. */
  int MULTIPLICATION = 30;
  /** RegularExpression Id. */
  int DIVISION = 31;
  /** RegularExpression Id. */
  int PARANTHESES_LEFT = 32;
  /** RegularExpression Id. */
  int PARANTHESES_RIGHT = 33;
  /** RegularExpression Id. */
  int NUMBER = 34;
  /** RegularExpression Id. */
  int FLOAT = 35;
  /** RegularExpression Id. */
  int DICE_D = 36;
  /** RegularExpression Id. */
  int DICE_KEEP = 37;
  /** RegularExpression Id. */
  int DICE_REROLL = 38;
  /** RegularExpression Id. */
  int DICE_SUCCESS = 39;
  /** RegularExpression Id. */
  int DICE_EXPLODING_SUCCESS = 40;
  /** RegularExpression Id. */
  int DICE_EXPLODING = 41;
  /** RegularExpression Id. */
  int DICE_OPEN = 42;
  /** RegularExpression Id. */
  int DICE_FUDGE = 43;
  /** RegularExpression Id. */
  int DICE_UBIQUITY = 44;
  /** RegularExpression Id. */
  int DICE_SHADOWRUN_EXPLODING_GREMLIN = 45;
  /** RegularExpression Id. */
  int DICE_SHADOWRUN_EXPLODING = 46;
  /** RegularExpression Id. */
  int DICE_SHADOWRUN_GREMLIN = 47;
  /** RegularExpression Id. */
  int DICE_SHADOWRUN = 48;

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
    "\"\\u00a7\"",
    "<TEXT>",
    "\"\\u00a7\"",
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
