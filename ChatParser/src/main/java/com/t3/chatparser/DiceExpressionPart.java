package com.t3.chatparser;

import java.security.SecureRandom;
import java.text.NumberFormat;

import com.t3.dice.expression.DiceExpression;

public class DiceExpressionPart extends ChatPart {

	
	private DiceExpression diceExpression;

	public DiceExpressionPart(DiceExpression diceExpression) {
		this.diceExpression=diceExpression;
	}

	@Override
	public String getDefaultTextRepresentation() {
		float result=diceExpression.evaluate(new SecureRandom());
		StringBuilder sb=new StringBuilder();
		sb.append("<span title=\"")
			.append(diceExpression.toString())
			.append(" => ")
			.append(diceExpression.toEvaluatedString())
			.append("\">")
			.append(NumberFormat.getInstance().format(result))
			.append("</span>");
		
		return sb.toString();
	}

	public DiceExpression getDiceExpression() {
		return diceExpression;
	}
	
	@Override
	public String toString() {
		return diceExpression.toString();
	}
	
}
