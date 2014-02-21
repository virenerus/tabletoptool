package net.rptools.maptool.script.mt2api;

import java.util.Random;

import net.sf.mt2.chatparser.generated.ChatParser;
import net.sf.mt2.chatparser.generated.ParseException;
import net.sf.mt2.dice.expression.DiceExpression;

public class DiceExpressionView {
	private DiceExpression expression;
	private Number currentResult;
	
	public DiceExpressionView(DiceExpression expression) {
		this.expression=expression;
	}
	
	/**
	 * This method is only for the serializer. Do not use it.
	 */
	public DiceExpressionView() {
	}
	
	public DiceExpressionView(String text) throws ParseException {
		this.expression=new ChatParser(text).parseDiceExpression();
	}

	public Number roll() {
		currentResult=expression.evaluate(new Random());
		return currentResult;
	}
	
	public String getChatString() {
		return expression.toEvaluatedString();
	}
	
	public String getSimpleString() {
		return expression.toString();
	}
	
	@Override
	public String toString() {
		return expression.toString();
	}
}
