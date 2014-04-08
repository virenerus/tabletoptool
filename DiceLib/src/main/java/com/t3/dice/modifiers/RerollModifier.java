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

import java.util.Random;

import com.t3.dice.Dice;

public class RerollModifier implements DiceModifier {

	private int	minNumberRequired;

	public RerollModifier(int minNumberRequired) {
		this.minNumberRequired=minNumberRequired;
	}

	public int[] modify(Dice d, int[] results, Random r) {
		int range=1+d.getMaximumDiceValue()-d.getMinimumDiceValue();
		for(int i=0;i<results.length;i++)
			while(results[i]<minNumberRequired)
				results[i]=r.nextInt(range)+d.getMinimumDiceValue();
		return results;
	}
	
	public String getToStringSuffix() {
		return "r"+minNumberRequired;
	}
}
