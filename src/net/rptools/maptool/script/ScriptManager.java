package net.rptools.maptool.script;


import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.HashMap;
import java.util.Map;

import net.rptools.maptool.client.MapToolMacroContext;
import net.rptools.maptool.model.Token;
import net.rptools.maptool.script.groovy.GroovyScriptManager;
import net.rptools.maptool.script.mt2api.MT2ScriptLibrary;
import net.rptools.maptool.script.mt2api.TokenView;

import org.codehaus.groovy.control.CompilerConfiguration;

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
		//load librarys
		//ECLIPSE get these from plugins
		MT2ScriptLibrary lib=new MT2ScriptLibrary();
		scriptBindings.put(lib.getVariableName(), lib);
		init();
	}
	
	public abstract void init();
	public abstract T compile(String script) throws MT2ScriptException;

	public Object run(T script, MapToolMacroContext context) throws MT2ScriptException {
		return run(script,null,context);
	}

	public Object run(T script, Token token) throws MT2ScriptException {
		return run(script,token,null);
	}

	public Object run(T script, Token token, MapToolMacroContext context) throws MT2ScriptException {
		return run(script, token, context, scriptBindings);
	}
	
	protected abstract Object run(T script, Token token, MapToolMacroContext context, Map<String, Object> scriptBindings2) throws MT2ScriptException;

	public Object evaluate(String script, Token token) throws MT2ScriptException {
		return evaluate(script, token, null);
	}
	
	public Object evaluate(String script, MapToolMacroContext context) throws MT2ScriptException {
		return evaluate(script, null, context);
	}
	
	public Object evaluate(String script, Token token, MapToolMacroContext context) throws MT2ScriptException {
		return run(compile(script),token,context);
	}

	//FIXMESOON find out what this should do and implement it
	public String parseLine(Token token, String toolTip, MapToolMacroContext context) throws MT2ScriptException {
		return "hallo";
	}

	public Object evaluate(String script) throws MT2ScriptException {
		return evaluate(script, null, null);
	}
}
