package com.t3.dice.expression;

import java.text.NumberFormat;
import java.util.Random;

public class NumberNode implements DiceExpression {

	private float number;

	public NumberNode(float number) {
		this.number=number;
	}

	public float evaluate(Random random) {
		return number;
	}
	
	public String toString() {
		return Float.toString(number);
	}

	public String toEvaluatedString() {
		return NumberFormat.getInstance().format(number);
	}
}
