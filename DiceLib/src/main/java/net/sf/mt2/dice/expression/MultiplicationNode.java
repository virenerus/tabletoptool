package net.sf.mt2.dice.expression;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

public class MultiplicationNode implements DiceExpression {
	private ArrayList<DiceExpression> nodes;
	private BitSet operations;

	public MultiplicationNode(DiceExpression first) {
		nodes=new ArrayList<DiceExpression>(10);
		nodes.add(first);
		operations=new BitSet();
		operations.set(nodes.size()-1, true);
	}

	public int evaluate(Random random) {
		int product=1;
		for(int i=0;i<nodes.size();i++) {
			if(operations.get(i))
				product*=nodes.get(i).evaluate(random);
			else
				product/=nodes.get(i).evaluate(random);
		}
		return product;
	}

	public void multiplyBy(DiceExpression term) {
		nodes.add(term);
		operations.set(nodes.size()-1, true);
	}
	
	public void divideBy(DiceExpression term) {
		nodes.add(term);
		operations.set(nodes.size()-1, false);
	}
	

	public String toString() {
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<nodes.size();i++) {
			if(i>0) {
				if(operations.get(i))
					sb.append('\u00D7');
				else
					sb.append('\u00F7');
			}
			if(operations.get(i))
				sb.append(nodes.get(i).toString());
			else
				sb.append(nodes.get(i).toString());
		}
		return sb.toString();
	}

	public String toEvaluatedString() {
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<nodes.size();i++) {
			if(i>0) {
				if(operations.get(i))
					sb.append('\u00D7');
				else
					sb.append('\u00F7');
			}
			if(operations.get(i))
				sb.append(nodes.get(i).toEvaluatedString());
			else
				sb.append(nodes.get(i).toEvaluatedString());
		}
		return sb.toString();
	}
}
