package com.t3.dice.modifiers;

import java.util.Random;

import com.t3.dice.Dice;

public interface DiceModifier {

	public int[] modify(Dice basicDice, int[] results, Random r);

	public String getToStringSuffix();
	
}
