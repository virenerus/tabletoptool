package com.t3.doccreator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;


public class DocCreator {
	private Multimap<String, MethodDefinition> methods=HashMultimap.create();
	private String name;
	
	public DocCreator(String name, File file) {
		this.name=name;
		try {
			String content=IOUtils.toString(new FileReader(file));
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource(content.toCharArray());
	 
	 
			final CompilationUnit cu = (CompilationUnit) parser.createAST(new NullProgressMonitor());
			
			cu.accept(new ASTVisitor() {
				public boolean visit(MethodDeclaration m) {
					if(!m.isConstructor() && 
							!m.getName().getIdentifier().equals("getVariableName") &&
							!m.toString().startsWith("private")) {
						methods.put(m.getName().getIdentifier(), new MethodDefinition(m));
					}
					return false;
				}
			});
			
			for(String key:methods.keySet()) {
				
			}
			
		} catch(FileNotFoundException e) {
			try {
				System.err.println(file.getAbsoluteFile().getCanonicalPath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void print(PrintStream p) {
		p.print(name);
		if(!name.endsWith("."))
			p.print(":");
		p.println();
		for(MethodDefinition md:methods.values())
			md.print(p);
	}

	public Multimap<String, MethodDefinition> getMethodDefinitions() {
		return methods;
	}
}
