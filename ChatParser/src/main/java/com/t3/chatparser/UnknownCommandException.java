package com.t3.chatparser;

public class UnknownCommandException extends Exception {
	public UnknownCommandException(String command) {
		super("Command '"+command+"' is unknown.");
	}
}
