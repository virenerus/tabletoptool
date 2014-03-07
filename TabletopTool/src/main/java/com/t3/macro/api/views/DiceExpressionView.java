package com.t3.macro.api.views;

import java.util.Random;

import com.t3.chatparser.generated.ChatParser;
import com.t3.chatparser.generated.ParseException;
import com.t3.dice.expression.DiceExpression;

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
