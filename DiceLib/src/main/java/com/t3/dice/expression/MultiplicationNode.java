/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3.dice.expression;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

import com.t3.xstreamversioned.SerializationVersion;

@SerializationVersion(0)
public class MultiplicationNode extends Expression {
	private ArrayList<Expression> nodes;
	private BitSet operations;

	public MultiplicationNode(Expression first) {
		nodes=new ArrayList<Expression>(10);
		nodes.add(first);
		operations=new BitSet();
		operations.set(nodes.size()-1, true);
	}

	@Override
	public float getResult(Random random) {
		int product=1;
		for(int i=0;i<nodes.size();i++) {
			if(operations.get(i))
				product*=nodes.get(i).getResult(random);
			else
				product/=nodes.get(i).getResult(random);
		}
		return product;
	}

	public void multiplyBy(Expression term) {
		nodes.add(term);
		operations.set(nodes.size()-1, true);
	}
	
	public void divideBy(Expression term) {
		nodes.add(term);
		operations.set(nodes.size()-1, false);
	}
	

	@Override
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

	@Override
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
