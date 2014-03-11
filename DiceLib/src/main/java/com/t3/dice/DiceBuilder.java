package com.t3.dice;


public class DiceBuilder {
	private int	numberOfDices;

	private DiceBuilder(int numberOfDices) {
		this.numberOfDices=numberOfDices;
	}
	
	public static DiceBuilder roll(int numberOfDices) {
		return new DiceBuilder(numberOfDices);
	}
	
	/**
	 * This allows you to roll a normal dice of any size you choose 
	 * @param diceType the number of sides of the dice
	 * @return the dice roll
	 */
	public ExtendableDice d(int diceType) {
		return new ExtendableDice(numberOfDices, diceType);
	}
	
	/**
	 * This will create a fudge dice roll (-1,0,1).
	 * @see #fudgeDice
	 * @return the dice roll
	 */
	public Dice df() {
		return fudgeDice();
	}
	
	/**
	 * This will create a fudge dice roll (-1,0,1).
	 * @see #df
	 * @return the dice roll
	 */
	public ExtendableDice fudgeDice() {
		return new ExtendableDice(numberOfDices, -1, 1);
	}
	
	/**
	 * This will create a ubiquity dice roll (0 or 1).
	 * @see #ubiquityDice
	 * @return the dice roll
	 */
	public Dice du() {
		return ubiquityDice();
	}
	
	/**
	 * This will create a ubiquity dice roll (0 or 1).
	 * @see #du
	 * @return the dice
	 */
	public Dice ubiquityDice() {
		return new ExtendableDice(numberOfDices, 0, 1);
	}
	
	/**
	 * This will create a Shadowrun dice roll. It is basically a d6 that can only be exploded or
	 * gremlind.
	 * @see #shadowrunDice
	 * @return the dice roll
	 */
	public ShadowrunDice sr4() {
		return shadowrunDice();
	}
	
	/**
	 * This will create a Shadowrun dice roll. It is basically a d6 that can only be exploded or
	 * gremlind.
	 * @see #sr4
	 * @return the dice roll
	 */
	public ShadowrunDice shadowrunDice() {
		return new ShadowrunDice(numberOfDices);
	}
}
