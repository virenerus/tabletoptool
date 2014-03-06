package com.t3.doccreator;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.output.StringBuilderWriter;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;


public class MethodDefinition {

	private String name;
	private String returnType;
	private String doc;
	private List<String> parameters;
	private boolean throwingException;
	
	public MethodDefinition(MethodDeclaration m) {
		this.name=m.getName().getIdentifier();
		this.returnType=m.getReturnType2().toString();
		if(m.getJavadoc()!=null) {
			doc=m.getJavadoc().toString();
			String[] lines=doc.split("\n");
			doc="";
			for(String l:lines) {
				l=l.trim();
				if(l.startsWith("*"))
					l=l.substring(1);
				if(l.startsWith("/**"))
					l=l.substring(3);
				if(l.startsWith("**/"))
					l=l.substring(3);
				doc+=l.trim()+"\n";
			}
		}
		parameters=new ArrayList<String>();
		for(Object o:m.parameters())
			parameters.add(o.toString());
		throwingException=!m.thrownExceptions().isEmpty();
	}


	public void print(PrintStream p) {
		p.print("\t"+name);
		p.print(parameters.toString().replace('[','(').replace(']',')'));
		if(!returnType.equals("void"))
			p.print(" : "+returnType);
		p.println();
	}


	public String getDoc() {
		return doc;
	}


	public String getName() {
		return name;
	}


	public String getReturnType() {
		return returnType;
	}


	public List<String> getParameters() {
		return parameters;
	}


	public boolean isThrowingException() {
		return throwingException;
	}
}
