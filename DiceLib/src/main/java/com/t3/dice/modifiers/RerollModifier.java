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
