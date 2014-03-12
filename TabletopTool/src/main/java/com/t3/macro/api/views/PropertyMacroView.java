package com.t3.macro.api.views;

import groovy.lang.Script;

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

	/**
	 * for hessian serializer
	 */
	public PropertyMacroView() {}
	
	public PropertyMacroView(String macroText, Token t) throws MacroException {
		this.token=t;
		this.macroText=macroText;
		this.script=MacroEngine.getInstance().compile(macroText);
	}
	
	public PropertyMacroView(PropertyMacroView pmv, String text) throws MacroException {
		this(text,pmv.token);
	}

	@Override
	public Object execute() throws MacroException {
		return MacroEngine.getInstance().run(script, token);
	}
	
	public String toString() {
		return macroText;
	}
}
