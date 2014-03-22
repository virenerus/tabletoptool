package com.t3.dice.expression;

import java.util.Random;

public class NegationNode extends Expression {

	private Expression expr;

	public NegationNode(Expression expr) {
		this.expr=expr;
	}
	
	public float getResult(Random random) {
		return -expr.getResult(random);
	}
	

	public String toString() {
		return "-"+expr.toString();
	}

	public String toEvaluatedString() {
		return "-("+expr.toEvaluatedString()+")";
	}

}
