package com.t3.script.groovy;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.builder.ImportCustomizerFactory;

import com.t3.client.T3MacroContext;
import com.t3.model.Token;
import com.t3.script.MT2ScriptException;
import com.t3.script.ScriptManager;
import com.t3.script.api.MT2ScriptLibrary;
import com.t3.script.api.TokenView;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

public class GroovyScriptManager extends ScriptManager<Script> {

	private CompilerConfiguration compilerConfiguration;

	@Override
	public void init() {
		compilerConfiguration=new CompilerConfiguration();
		compilerConfiguration.addCompilationCustomizers(new ScriptImportCustomizer());
		compilerConfiguration.setScriptBaseClass(MT2ScriptLibrary.class.getName());
	}
	
	public Script compile(String script) throws MT2ScriptException {
		try {
			return new GroovyShell(compilerConfiguration).parse(script);
		} catch (Exception e) {
			throw new MT2ScriptException("Error while trying to compile: "+script, e);
		}
	}

	@Override
	protected Object run(Script script, HashMap<String, Object> arguments, Token token, T3MacroContext context, Map<String, Object> scriptBindings) throws MT2ScriptException {
		try {
			Binding b=new Binding(scriptBindings);
			if(token!=null)
				b.setVariable("token", new TokenView(token));
			b.setVariable("args", arguments);
			script.setBinding(b);
			return script.run();
		} catch(Exception e) {
			throw new MT2ScriptException("Error while trying to run:"+script,e);
		}
	}
}
