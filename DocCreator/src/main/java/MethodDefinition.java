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
	private Javadoc doc;
	private List<String> parameters;
	private boolean throwingException;
	
	public MethodDefinition(MethodDeclaration m) {
		this.name=m.getName().getIdentifier();
		this.returnType=m.getReturnType2().toString();
		doc=m.getJavadoc();
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
}
