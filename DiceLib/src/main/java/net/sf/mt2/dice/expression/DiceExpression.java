package net.sf.mt2.dice.expression;

import java.util.Random;

public interface DiceExpression {

	public int evaluate(Random random);
	
	public String toEvaluatedString();
}
