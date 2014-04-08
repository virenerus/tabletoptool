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

public class SuccessModifier implements DiceModifier {

	private int	neededNumber;

	public SuccessModifier(int neededNumber) {
		this.neededNumber=neededNumber;
	}

	public int[] modify(Dice basicDice, int[] results, Random r) {
		for(int i=0;i<results.length;i++)
			if(results[i]>=neededNumber)
				results[i]=1;
			else
				results[i]=0;
		return results;
	}
	
	public String getToStringSuffix() {
		return "s"+neededNumber;
	}
}
