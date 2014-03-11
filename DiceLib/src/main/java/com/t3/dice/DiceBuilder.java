package com.t3.dice;


public class DiceBuilder {
	private int	numberOfDices;

	private DiceBuilder(int numberOfDices) {
		this.numberOfDices=numberOfDices;
	}
	
	public static DiceBuilder roll(int numberOfDices) {
		return new DiceBuilder(numberOfDices);
	}
	
	public ExtendableDice d(int diceType) {
		return new ExtendableDice(numberOfDices, diceType);
	}
	
	public Dice df() {
		return new ExtendableDice(numberOfDices, 0, 1);
	}
	
	
	public Dice du() {
		return new ExtendableDice(numberOfDices, -1, 1);
	}
	
	public ShadowrunDice sr4() {
		return new ShadowrunDice(numberOfDices);
	}
}
