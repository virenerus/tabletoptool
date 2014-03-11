package com.t3.dice.modifiers;

import java.util.Arrays;
import java.util.Random;

import com.t3.dice.Dice;

public class KeepModifier implements DiceModifier {

	private int	numberOfKept;

	public KeepModifier(int numberOfKept) {
		this.numberOfKept=numberOfKept;
	}

	public int[] modify(Dice d, int[] results, Random r) {
		Arrays.sort(results);
		return Arrays.copyOfRange(results, results.length-numberOfKept, results.length);
	}
}
