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
import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(0)
public class NumberNode extends Expression {

	private float number;

	public NumberNode(float number) {
		this.number=number;
	}

	@Override
	public float getResult(Random random) {
		return number;
	}
	
	@Override
	public String toString() {
		return NumberUtil.formatFloat(number);
	}

	@Override
	public String toEvaluatedString() {
		return NumberUtil.formatFloat(number);
	}
}
