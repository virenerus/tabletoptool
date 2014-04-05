package com.t3.dice.expression;

import java.util.Random;

import com.t3.dice.NumberUtil;

public class NumberNode extends Expression {

	private float number;

	public NumberNode(float number) {
		this.number=number;
	}

	@Override
	public float getResult(Random random) {
		return number;
	}
	
	@Override
	public String toString() {
		return NumberUtil.formatFloat(number);
	}

	@Override
	public String toEvaluatedString() {
		return NumberUtil.formatFloat(number);
	}
}
