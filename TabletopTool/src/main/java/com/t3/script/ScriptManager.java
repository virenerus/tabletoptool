package com.t3.script;


import groovy.lang.Script;

import java.util.HashMap;
import java.util.Map;

import com.t3.client.T3MacroContext;
import com.t3.model.Token;
import com.t3.script.groovy.GroovyScriptManager;

/**
 * This is the central class of of the new Scrip APIting. Use this class to call and compile scripts.
 * This tries to capsules the actual implementation. All the groovy stuff is in the groovy Subpackage.
 * @author Virenerus
 *
 * @param <T> the type you want to use for your compiled scripts. This should be String if you don't compile
 */
public abstract class ScriptManager<T> {
	private static ScriptManager<Script> INSTANCE;
	
	public static ScriptManager<Script> getInstance() {
		if(INSTANCE==null)
			INSTANCE=new GroovyScriptManager();
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
	public abstract T compile(String script) throws MT2ScriptException;

	public Object run(T script, T3MacroContext context) throws MT2ScriptException {
		return run(script,null,null,context);
	}

	public Object run(T script, Token token) throws MT2ScriptException {
		return run(script,null,token,null);
	}

	public Object run(T script, HashMap<String, Object> arguments, Token token, T3MacroContext context) throws MT2ScriptException {
		return run(script, arguments, token, context, scriptBindings);
	}
	
	protected abstract Object run(T script, HashMap<String, Object> arguments, Token token, T3MacroContext context, Map<String, Object> scriptBindings2) throws MT2ScriptException;

	public Object evaluate(String script, Token token) throws MT2ScriptException {
		return evaluate(script, token, null);
	}
	
	public Object evaluate(String script, T3MacroContext context) throws MT2ScriptException {
		return evaluate(script, null, context);
	}
	
	public Object evaluate(String script, Token token, T3MacroContext context) throws MT2ScriptException {
		return run(compile(script),null,token,context);
	}

	//FIXMESOON find out what this should do and implement it
	public String parseLine(Token token, String toolTip, T3MacroContext context) throws MT2ScriptException {
		return "hallo";
	}

	public Object evaluate(String script) throws MT2ScriptException {
		return evaluate(script, null, null);
	}
}
