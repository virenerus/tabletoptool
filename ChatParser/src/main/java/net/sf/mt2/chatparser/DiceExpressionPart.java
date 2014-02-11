package net.sf.mt2.chatparser;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import net.sf.mt2.dice.expression.DiceExpression;

public class DiceExpressionPart extends ChatPart {

	private DiceExpression diceExpression;

	public DiceExpressionPart(DiceExpression diceExpression) {
		this.diceExpression=diceExpression;
	}

	@Override
	public String getDefaultTextRepresentation() {
		int result=diceExpression.evaluate(new Random());
		StringBuilder sb=new StringBuilder();
		sb.append("<span title=\"")
			.append(diceExpression.toString())
			.append(" => ")
			.append(diceExpression.toEvaluatedString())
			.append("\">")
			.append(result)
			.append("</span>");
		
		return sb.toString();
	}
}
