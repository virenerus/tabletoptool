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
