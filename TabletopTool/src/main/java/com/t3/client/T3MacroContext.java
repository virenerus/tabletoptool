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
package com.t3.client;

public class T3MacroContext {
	/** The name of the macro being executed. */
	private final String name;

	/** Where the macro comes from. */
	private final String source;

	/** Is the macro trusted or not. */
	private final boolean trusted;

	/** The index of the button that was clicked on to fire of this macro*/
	private int macroButtonIndex;

	/**
	 * Creates a new Macro Context.
	 * @param name The name of the macro.
	 * @param source The source location of the macro.
	 * @param trusted Is the macro trusted or not.
	 */
	public T3MacroContext(String name, String source, boolean trusted) {
		this(name, source, trusted, -1);
	}

	/**
	 * Creates a new Macro Context.
	 * @param name The name of the macro.
	 * @param source The source location of the macro.
	 * @param trusted Is the macro trusted or not.
	 * @param macroButtonIndex The index of the button that ran this command.
	 */
	public T3MacroContext(String name, String source, boolean trusted, int macroButtonIndex) {
		this.name = name;
		this.source = source;
		this.trusted = trusted;
		this.macroButtonIndex = macroButtonIndex;
	}



	/**
	 * Gets the name of the macro context.
	 * @return the name of the macro context.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the source location of the macro context.
	 * @return the source location of the macro context.
	 */
	public String getSouce() {
		return source;
	}

	/**
	 * Gets if the macro context is trusted or not.
	 * @return if the macro context is trusted or not.
	 */
	public boolean isTrusted() {
		return trusted;
	}


	/**
	 * Gets the index of the macro button that this macro is in
	 * @return the index of the macro button.
	 */
	public int getMacroButtonIndex() {
		return macroButtonIndex;
	}
}
