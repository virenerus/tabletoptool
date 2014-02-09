package net.sf.mt2.chatparser;

import java.util.Random;

import net.sf.mt2.dice.expression.DiceExpression;

public class DiceExpressionPart extends ChatPart {

	private DiceExpression diceExpression;

	public DiceExpressionPart(DiceExpression diceExpression) {
		this.diceExpression=diceExpression;
	}
	
	@Override
	public Object getEvaluatedValue() {
		return Integer.toString(diceExpression.evaluate(new Random()));
	}

	@Override
	public String getText() {
		return diceExpression.toString();
	}
}
