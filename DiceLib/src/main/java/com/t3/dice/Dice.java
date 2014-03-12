package com.t3.dice;

import java.util.Random;

import com.t3.dice.modifiers.DiceModifier;

public abstract class Dice {

	protected int	numberOfDices;
	protected int	minimumValue;
	protected int	maximumValue;
	protected int[]	originalResult;
	protected int[] modifiedResult;
	protected int	result;

	public Dice(int numberOfDices, int minimumValue, int maximumValue) {
		this.numberOfDices=numberOfDices;
		this.minimumValue=minimumValue;
		this.maximumValue=maximumValue;
	}

	/**
	 * @return an array with the result of every die in the roll
	 */
	public int[] getResults() {
		rollIfNeeded();
		return modifiedResult;
	}
	
	/**
	 * This is a Hero system specific value. The Body Damage is equal to the number of dice that
	 * did not roll 1 (or minimum number) plus the number of dice that did roll the maximum number.
	 * E.g.: 4d6=[6,4,4,1] wourld result in a 4
	 * @return the Hero Body Damage value of this roll
	 */
	public int getHeroBodyDamage() {
		rollIfNeeded();
		int dmg=0;
		for(int i:modifiedResult) {
			if(i==maximumValue)
				i+=2;
			else if(i>minimumValue)
				i++;
		}
		return dmg;
	}
	
	/**
	 * This returns the value of the highest dice in the last roll. This is especially usefull for open
	 * dice rolls.
	 * @return the value of the highest dice of the last roll
	 */
	public int getHighestResult() {
		rollIfNeeded();
		int max=Integer.MIN_VALUE;
		for(int i:modifiedResult) {
			if(i>max)
				max=i;
		}
		return max;
	}

	/**
	 * @return the sum of all the dice in the roll
	 */
	public int getResult() {
		rollIfNeeded();
		return result;
	}

	protected void rollIfNeeded() {
		if(originalResult==null)
			roll();
	}

	/**
	 * This will roll or reroll this dice with the given random number generator. 
	 * This is useful if you want to generate the random values from a seed.
	 * @param r the random generator to be used
	 */
	public void roll(Random r) {
		originalResult=new int[numberOfDices];
		int range=maximumValue-minimumValue+1;
		for(int i=0;i<numberOfDices;i++)
			originalResult[i]=r.nextInt(range)+minimumValue;
		
		modifiedResult=this.modifyResult(originalResult, r);
		
		result=0;
		for(int v:modifiedResult)
			result+=v;
	}

	protected abstract int[] modifyResult(int[] orig, Random r);

	/**
	 * This will roll or reroll this dice with the given random number generator. 
	 */
	public void roll() {
		roll(new Random());
	}

	/**
	 * @return the value of the highest rolled die
	 */
	public int getMaximumDiceValue() {
		return maximumValue;
	}

	/**
	 * @return the value of the lowest rolled die
	 */
	public int getMinimumDiceValue() {
		return minimumValue;
	}

	/**
	 * @return if this dice were rolled at least once
	 */
	public boolean isRolled() {
		return originalResult!=null;
	}
}