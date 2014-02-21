package net.sf.mt2.dice.expression;

import java.util.Random;

public class NegationNode implements DiceExpression {

	private DiceExpression expr;

	public NegationNode(DiceExpression expr) {
		this.expr=expr;
	}
	
	public float evaluate(Random random) {
		return -expr.evaluate(random);
	}
	

	public String toString() {
		return "-"+expr.toString();
	}

	public String toEvaluatedString() {
		return "-("+expr.toEvaluatedString()+")";
	}

}
