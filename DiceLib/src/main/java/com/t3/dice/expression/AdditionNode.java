package com.t3.dice.expression;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

public class AdditionNode extends Expression {

	private ArrayList<Expression> nodes;
	private BitSet operations;

	public AdditionNode(Expression first) {
		nodes=new ArrayList<Expression>(10);
		nodes.add(first);
		operations=new BitSet();
		operations.set(nodes.size()-1, true);
	}

	public float getResult(Random random) {
		float sum=0;
		for(int i=0;i<nodes.size();i++) {
			if(operations.get(i))
				sum+=nodes.get(i).getResult(random);
			else
				sum-=nodes.get(i).getResult(random);
		}
		return sum;
	}

	public void add(Expression term) {
		nodes.add(term);
		operations.set(nodes.size()-1, true);
	}
	
	public void subtract(Expression term) {
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
