package net.sf.mt2.dice.expression;

import java.util.Random;

public class NumberNode implements DiceExpression {

	private int number;

	public NumberNode(int number) {
		this.number=number;
	}

	public int evaluate(Random random) {
		return number;
	}
	
	public String toString() {
		return Integer.toString(number);
	}
}
