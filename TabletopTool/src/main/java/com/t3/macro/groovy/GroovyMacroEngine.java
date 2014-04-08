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
package com.t3.macro.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.Map;

import org.codehaus.groovy.control.CompilerConfiguration;

import com.t3.client.T3MacroContext;
import com.t3.macro.MacroEngine;
import com.t3.macro.MacroException;
import com.t3.macro.api.MacroAPI;
import com.t3.macro.api.views.TokenView;
import com.t3.model.Token;

public class GroovyMacroEngine extends MacroEngine<Script> {

	private CompilerConfiguration compilerConfiguration;

	@Override
	public void init() {
		compilerConfiguration=new CompilerConfiguration();
		compilerConfiguration.addCompilationCustomizers(new MacroImportCustomizer());
		compilerConfiguration.setScriptBaseClass(MacroAPI.class.getName());
	}
	
	@Override
	public Script compile(String script) throws MacroException {
		try {
			return new GroovyShell(compilerConfiguration).parse(script);
		} catch (Exception e) {
			throw new MacroException("Error while trying to compile: "+script, e);
		}
	}

	@Override
	protected Object run(Script script, Map<String, Object> arguments, Token token, T3MacroContext context, Map<String, Object> scriptBindings) throws MacroException {
		try {
			Binding b=new Binding(scriptBindings);
			if(token!=null)
				b.setVariable("token", new TokenView(token));
			b.setVariable("args", arguments);
			script.setBinding(b);
			return script.run();
		} catch(Exception e) {
			throw new MacroException("Error while trying to run:"+script,e);
		}
	}
}
