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

import java.util.Arrays;
import java.util.Random;

import com.t3.dice.Dice;
import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(0)
public class DiceNode extends Expression {

	private Dice dice;

	public DiceNode(Dice dice) {
		this.dice=dice;
	}

	@Override
	public float getResult(Random random) {
		if(!dice.isRolled())
			dice.roll(random);
		return dice.getResult();
	}


	@Override
	public String toString() {
		return dice.toString();
	}

	@Override
	public String toEvaluatedString() {
		return Arrays.toString(dice.getResults());
	}
}
