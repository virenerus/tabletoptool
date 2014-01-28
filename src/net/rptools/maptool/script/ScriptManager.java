package net.rptools.maptool.script;


import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.HashMap;
import java.util.Map;

import net.rptools.maptool.client.MapToolMacroContext;
import net.rptools.maptool.model.Token;
import net.rptools.maptool.script.mt2.MT2ScriptLibrary;
import net.rptools.maptool.script.mt2.TokenView;

import org.codehaus.groovy.control.CompilerConfiguration;

/**
 * This is the central class of of the new Groovy Scripting. Use this class to call and compile scripts.
 * @author Virenerus
 *
 */
public class ScriptManager {
	private static ScriptManager INSTANCE;
	
	public static ScriptManager getInstance() {
		if(INSTANCE==null)
			INSTANCE=new ScriptManager();
		return INSTANCE;
	}

	private CompilerConfiguration compilerConfig;
	private Map<String,Object> scriptBindings;

	public void initialize() {
		scriptBindings=new HashMap<String,Object>();
		//load librarys
		//ECLIPSE get these from plugins
		MT2ScriptLibrary lib=new MT2ScriptLibrary();
		scriptBindings.put(lib.getVariableName(), lib);
		compilerConfig=new CompilerConfiguration();
	}

	public Script compile(String script) throws MT2ScriptException {
		try {
			return new GroovyShell(compilerConfig).parse(script);
		} catch (Exception e) {
			throw new MT2ScriptException("Error while trying to compile: "+script, e);
		}
	}

	public Object run(Script script, MapToolMacroContext context) throws MT2ScriptException {
		return run(script,null,context);
	}

	public Object run(Script script, Token token) throws MT2ScriptException {
		return run(script,token,null);
	}

	public Object run(Script script, Token token, MapToolMacroContext context) throws MT2ScriptException {
		try {
			Binding b=new Binding(scriptBindings);
			if(token!=null)
				b.setVariable("token", new TokenView(token));
			script.setBinding(b);
			return script.run();
		} catch(Exception e) {
			throw new MT2ScriptException("Error while trying to run:"+script,e);
		}
	}
	
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
