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
package com.t3.doccreator;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;


public class MethodDefinition {

	private String name;
	private String returnType;
	private String doc="";
	private HashMap<String, ParameterDefinition> parameters;
	private boolean throwingException;
	private String	returnComment;
	private String	errorComment;
	
	public MethodDefinition(MethodDeclaration m) {
		this.name=m.getName().getIdentifier();
		this.returnType=m.getReturnType2().toString();
		parameters=new HashMap<String, ParameterDefinition>();
		for(Object o:m.parameters()) {
			SingleVariableDeclaration svd=(SingleVariableDeclaration) o;
			parameters.put(svd.getName().getIdentifier(),new ParameterDefinition(svd.getType().toString(), svd.getName().getIdentifier()));
		}
		throwingException=!m.thrownExceptions().isEmpty();
		if(m.getJavadoc()!=null)
			parseDoc(m.getJavadoc());
	}


	private void parseDoc(Javadoc javadoc) {
		javadoc.accept(new ASTVisitor(true) {			
			@Override
			public boolean visit(TagElement node) {
				List<?> f=node.fragments();
				if(node.getTagName()==null)
					for(Object o:f) {
						if(o instanceof TextElement)
							doc+=((TextElement)o).getText();
						else if(o instanceof TagElement) {
							TagElement te=(TagElement)o;
							if(te.getTagName().equals("@link")) {
								if(te.fragments().size()==2)
									doc+=te.fragments().get(1).toString();
								else if(te.fragments().size()==1)
									doc+=te.fragments().get(0).toString().substring(1);
								else
									throw new Error();
							}
							else
								throw new Error();
						}
						else
							throw new Error();
					}
				else if(f.size()>=2 && node.getTagName().equals("@param")) 
					try {
						
						parameters.get(((SimpleName)f.get(0)).toString()).setComment(
								f.subList(1, f.size()).stream().map(Object::toString).collect(Collectors.joining()));
					} catch(NullPointerException npe) {
						System.err.println("NPE for "+getName()+" -> "+((SimpleName)f.get(0)).toString());
					}
				else if(node.getTagName().equals("@return"))
					returnComment=f.stream().map(Object::toString).collect(Collectors.joining("\n"));
				else if(f.size()==2 && node.getTagName().equals("@throws"))
					errorComment=((TextElement)f.get(1)).getText();
				else if(node.getTagName().equals("@author"));
				else if(node.getTagName().equals("@see"));
				else
					throw new Error(node.toString());
					
				return false;
			}
		});
		
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
			if(l.endsWith("/"))
				l=l.substring(0,l.length()-1);
			doc+=l.trim()+"\n";
		}
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


	public HashMap<String, ParameterDefinition> getParameters() {
		return parameters;
	}
	
	public List<ParameterDefinition> getParameterList() {
		return new ArrayList<>(parameters.values());
	}


	public boolean isThrowingException() {
		return throwingException;
	}


	public String getReturnComment() {
		return returnComment;
	}


	public void setReturnComment(String returnComment) {
		this.returnComment = returnComment;
	}


	public String getErrorComment() {
		return errorComment;
	}


	public void setErrorComment(String errorComment) {
		this.errorComment = errorComment;
	}
}
