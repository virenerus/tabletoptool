package com.t3.macro.api.views;

import groovy.lang.Script;

import java.util.Map;

import com.t3.macro.MacroEngine;
import com.t3.macro.MacroException;
import com.t3.model.Token;

public class PropertyMacroView implements MacroView {

	public static final PropertyMacroView	EMPTY_MACRO;
	private Token	token;
	private Script	script;
	private String	macroText;
	
	static {
		try {
			EMPTY_MACRO=new PropertyMacroView("",null);
		} catch (MacroException e) {
			throw new Error(e);
		}
	}
	
	public PropertyMacroView(String macroText, Token t) throws MacroException {
		this.token=t;
		this.macroText=macroText;
		this.script=MacroEngine.getInstance().compile(macroText);
	}
	
	public PropertyMacroView(PropertyMacroView pmv, String text) throws MacroException {
		this(text,pmv.token);
	}

	@Override
	/**
	 * This will try to execute the macro and return its result if there are any.
	 * @returns the object returned by the called macro
	 */
	public Object execute() throws MacroException {
		return MacroEngine.getInstance().run(script, token);
	}
	
	@Override
	/**
	 * This will try to execute the macro and return its result if there are any.
	 * @param arguments the arguments you want to pass to the macro
	 * @returns the object returned by the called macro
	 */
	public Object execute(Map<String, Object> arguments) throws MacroException {
		return MacroEngine.getInstance().run(script, token);
	}
	
	@Override
	public String toString() {
		return macroText;
	}

	/**
	 * @return the token this property belongs to
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * @return the macro as a string
	 */
	public String getMacro() {
		return macroText;
	}

	/**
	 * This method will set the macro
	 * @param macro the macro as a string
	 * @throws MacroException if the compilation of the macro fails
	 */
	public void setMacro(String macro) throws MacroException {
		this.macroText = macro;
		this.script=MacroEngine.getInstance().compile(macroText);
	}
}
