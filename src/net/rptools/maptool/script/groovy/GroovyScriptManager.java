package net.rptools.maptool.script.groovy;

import java.util.Map;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.builder.ImportCustomizerFactory;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import net.rptools.maptool.client.MapToolMacroContext;
import net.rptools.maptool.model.Token;
import net.rptools.maptool.script.MT2ScriptException;
import net.rptools.maptool.script.ScriptManager;
import net.rptools.maptool.script.mt2api.TokenView;

public class GroovyScriptManager extends ScriptManager<Script> {

	private CompilerConfiguration compilerConfiguration;

	@Override
	public void init() {
		compilerConfiguration=new CompilerConfiguration();
		compilerConfiguration.addCompilationCustomizers(new ScriptImportCustomizer());
	}
	
	public Script compile(String script) throws MT2ScriptException {
		try {
			return new GroovyShell(compilerConfiguration).parse(script);
		} catch (Exception e) {
			throw new MT2ScriptException("Error while trying to compile: "+script, e);
		}
	}

	@Override
	protected Object run(Script script, Token token, MapToolMacroContext context, Map<String, Object> scriptBindings) throws MT2ScriptException {
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
}
