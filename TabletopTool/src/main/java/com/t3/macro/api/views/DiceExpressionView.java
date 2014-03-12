package com.t3.macro.api.views;

import java.util.Random;

import com.t3.chatparser.generated.ChatParser;
import com.t3.chatparser.generated.ParseException;
import com.t3.dice.expression.DiceExpression;

public class DiceExpressionView {
	private DiceExpression expression;
	private float currentResult;
	
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

	/**
	 * This method tells the expression to be calculated. If there are any dice in this expression
	 * they will be rolled again.
	 * @return the result of the whole expression
	 */
	public float evaluate() {
		currentResult=expression.evaluate(new Random());
		return currentResult;
	}
	
	/**
	 * @return a complex string representing this expression suitable for direct printing to the chat
	 * @see #getChatString()
	 */
	public String getChatString() {
		return expression.toEvaluatedString();
	}
	
	/**
	 * @return a simple string representing this expression
	 * @see #getSimpleString()
	 */
	public String getSimpleString() {
		return expression.toString();
	}
	
	@Override
	public String toString() {
		return expression.toString();
	}
}
