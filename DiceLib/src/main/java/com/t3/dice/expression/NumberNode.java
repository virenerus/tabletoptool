package com.t3.dice.expression;

import java.util.Random;

import com.t3.dice.NumberUtil;

public class NumberNode implements DiceExpression {

	private float number;

	public NumberNode(float number) {
		this.number=number;
	}

	public float evaluate(Random random) {
		return number;
	}
	
	public String toString() {
		return NumberUtil.formatFloat(number);
	}

	public String toEvaluatedString() {
		return NumberUtil.formatFloat(number);
	}
}
