package com.t3.dice.expression;

import java.util.Random;

public class NegationNode extends Expression {

	private Expression expr;

	public NegationNode(Expression expr) {
		this.expr=expr;
	}
	
	@Override
	public float getResult(Random random) {
		return -expr.getResult(random);
	}
	

	@Override
	public String toString() {
		return "-"+expr.toString();
	}

	@Override
	public String toEvaluatedString() {
		return "-("+expr.toEvaluatedString()+")";
	}

}
