package com.t3.macro;


import groovy.lang.Script;

import java.util.HashMap;
import java.util.Map;

import com.t3.client.T3MacroContext;
import com.t3.macro.groovy.GroovyMacroEngine;
import com.t3.model.Token;

/**
 * This is the central class of of the new Scrip APIting. Use this class to call and compile scripts.
 * This tries to capsules the actual implementation. All the groovy stuff is in the groovy Subpackage.
 * @author Virenerus
 *
 * @param <T> the type you want to use for your compiled scripts. This should be String if you don't compile
 */
public abstract class MacroEngine<T> {
	private static MacroEngine<Script> INSTANCE;
	
	public static MacroEngine<Script> getInstance() {
		if(INSTANCE==null)
			INSTANCE=new GroovyMacroEngine();
		return INSTANCE;
	}

	private Map<String,Object> scriptBindings;

	public void initialize() {
		scriptBindings=new HashMap<String,Object>();
		//TODO load libraries from plugins
		//load libraries like this
		//scriptBindings.put(lib.getVariableName(), lib);
		init();
	}
	
	public abstract void init();
	public abstract T compile(String script) throws MacroException;

	public Object run(T script, T3MacroContext context) throws MacroException {
		return run(script,null,null,context);
	}

	public Object run(T script, Token token) throws MacroException {
		return run(script,null,token,null);
	}

	public Object run(T script, HashMap<String, Object> arguments, Token token, T3MacroContext context) throws MacroException {
		return run(script, arguments, token, context, scriptBindings);
	}
	
	protected abstract Object run(T script, HashMap<String, Object> arguments, Token token, T3MacroContext context, Map<String, Object> scriptBindings2) throws MacroException;

	public Object evaluate(String script, Token token) throws MacroException {
		return evaluate(script, token, null);
	}
	
	public Object evaluate(String script, T3MacroContext context) throws MacroException {
		return evaluate(script, null, context);
	}
	
	public Object evaluate(String script, Token token, T3MacroContext context) throws MacroException {
		return run(compile(script),null,token,context);
	}

	//FIXMESOON find out what this should do and implement it
	public String parseLine(Token token, String toolTip, T3MacroContext context) throws MacroException {
		return "hallo";
	}

	public Object evaluate(String script) throws MacroException {
		return evaluate(script, null, null);
	}
}
