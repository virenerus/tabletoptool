package net.sf.mt2.chatparser;

public class UnknownCommandException extends Exception {
	public UnknownCommandException(String command) {
		super("Command '"+command+"' is unknown.");
	}
}
