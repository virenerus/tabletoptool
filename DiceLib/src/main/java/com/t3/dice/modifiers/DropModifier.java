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
}
