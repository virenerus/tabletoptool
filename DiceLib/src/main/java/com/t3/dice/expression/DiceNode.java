package com.t3.dice.expression;

import java.util.Arrays;
import java.util.Random;

import com.t3.dice.Dice;

public class DiceNode implements DiceExpression {

	private Dice dice;

	public DiceNode(Dice dice) {
		this.dice=dice;
	}

	public float evaluate(Random random) {
		if(!dice.isRolled())
			dice.roll(random);
		return dice.getResult();
	}


	public String toString() {
		return dice.toString();
	}

	public String toEvaluatedString() {
		return Arrays.toString(dice.getResults());
	}
}
