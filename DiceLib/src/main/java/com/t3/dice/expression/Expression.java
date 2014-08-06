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

import java.util.Random;

import com.t3.dice.NumberUtil;
import com.t3.xstreamversioned.SerializationVersion;

@SerializationVersion(0)
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
