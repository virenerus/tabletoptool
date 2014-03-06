package com.t3.dice.expression;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

public class AdditionNode implements DiceExpression {

	private ArrayList<DiceExpression> nodes;
	private BitSet operations;

	public AdditionNode(DiceExpression first) {
		nodes=new ArrayList<DiceExpression>(10);
		nodes.add(first);
		operations=new BitSet();
		operations.set(nodes.size()-1, true);
	}

	public float evaluate(Random random) {
		float sum=0;
		for(int i=0;i<nodes.size();i++) {
			if(operations.get(i))
				sum+=nodes.get(i).evaluate(random);
			else
				sum-=nodes.get(i).evaluate(random);
		}
		return sum;
	}

	public void add(DiceExpression term) {
		nodes.add(term);
		operations.set(nodes.size()-1, true);
	}
	
	public void subtract(DiceExpression term) {
		nodes.add(term);
		operations.set(nodes.size()-1, false);
	}
	
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<nodes.size();i++) {
			if(operations.get(i)) {
				if(i>0)
					sb.append('+');
				sb.append(nodes.get(i).toString());
			}
			else {
				if(i>0)
					sb.append('-');
				sb.append(nodes.get(i).toString());
			}
		}
		return sb.toString();
	}

	public String toEvaluatedString() {
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<nodes.size();i++) {
			if(operations.get(i)) {
				if(i>0)
					sb.append('+');
				sb.append(nodes.get(i).toEvaluatedString());
			}
			else {
				if(i>0)
					sb.append('-');
				sb.append(nodes.get(i).toEvaluatedString());
			}
		}
		return sb.toString();
	}
}
