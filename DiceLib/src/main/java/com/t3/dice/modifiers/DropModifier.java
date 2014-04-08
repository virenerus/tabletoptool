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
package com.t3.dice.modifiers;

import java.util.Arrays;
import java.util.Random;

import com.t3.dice.Dice;

public class DropModifier implements DiceModifier {

	private int	numberOfDropped;

	public DropModifier(int numberOfDropped) {
		this.numberOfDropped=numberOfDropped;
	}

	public int[] modify(Dice d, int[] results, Random r) {
		Arrays.sort(results);
		return Arrays.copyOfRange(results, numberOfDropped, results.length);
	}

	public String getToStringSuffix() {
		return "d"+numberOfDropped;
	}
}
