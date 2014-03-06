package com.t3.dice.expression;

import java.util.Random;

public interface DiceExpression {

	public final static DiceExpression ZERO_EXPRESSION = new NumberNode(0);

	public float evaluate(Random random);
	
	public String toEvaluatedString();
}
