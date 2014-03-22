package com.t3.chatparser;

import java.security.SecureRandom;
import java.text.NumberFormat;

import com.t3.dice.expression.Expression;

public class ExpressionPart extends ChatPart {

	
	private Expression expression;

	public ExpressionPart(Expression expression) {
		this.expression=expression;
	}

	@Override
	public String getDefaultTextRepresentation() {
		float result=expression.getResult(new SecureRandom());
		StringBuilder sb=new StringBuilder();
		sb.append("<span title=\"")
			.append(expression.toString())
			.append(" => ")
			.append(expression.toEvaluatedString())
			.append("\">")
			.append(NumberFormat.getInstance().format(result))
			.append("</span>");
		
		return sb.toString();
	}

	public Expression getDiceExpression() {
		return expression;
	}
	
	@Override
	public String toString() {
		return expression.toString();
	}
	
}
