package net.sf.mt2.dice.expression;

import java.util.Random;

import net.sf.mt2.dice.Dice;

public class DiceNode implements DiceExpression {

	private Dice dice;

	public DiceNode(Dice dice) {
		this.dice=dice;
	}

	public int evaluate(Random random) {
		if(dice.isRolled())
			dice.rollDice(random);
		return dice.getResult();
	}


	public String toString() {
		return dice.toString();
	}
}
