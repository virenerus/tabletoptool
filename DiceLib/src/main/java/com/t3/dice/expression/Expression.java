package com.t3.dice.expression;

import java.util.Random;

import com.t3.dice.NumberUtil;

public abstract class Expression {

	public final static Expression ZERO_EXPRESSION = new NumberNode(0);

	public abstract float getResult(Random random);
	
	public float getResult() {
		return getResult(new Random());
	};
	
	public abstract String toEvaluatedString();
	
	public String toCompleteChatString() {
		float result=this.getResult(new Random());
		StringBuilder sb=new StringBuilder();
		sb.append(this.toString())
			.append(" = ")
			.append(this.toEvaluatedString())
			.append(" = <b>")
			.append(NumberUtil.formatFloat(result))
			.append("</b>");
		
		return sb.toString();
	}
}
