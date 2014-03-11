package com.t3.dice;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import com.t3.dice.modifiers.DiceModifier;
import com.t3.dice.modifiers.DropModifier;
import com.t3.dice.modifiers.ExplodingModifier;
import com.t3.dice.modifiers.KeepModifier;
import com.t3.dice.modifiers.RerollModifier;
import com.t3.dice.modifiers.SuccessModifier;

public class ExtendableDice extends Dice {

	protected LinkedList<DiceModifier> modifiers;
	
	public ExtendableDice(int numberOfDices, int maximumValue) {
		this(numberOfDices, 1, maximumValue);
	}
	
	public ExtendableDice(int numberOfDices, int minimumValue, int maximumValue) {
		super(numberOfDices, minimumValue, maximumValue);
		this.modifiers=new LinkedList<DiceModifier>();
	}
	
	/**
	 * This will cause the roll to drop the lowest x numbers rolled.
	 * @param numberOfDropped how many dices to drop
	 * @return the updated dice roll itself
	 * @see #drop
	 */
	public ExtendableDice d(int numberOfDropped) {
		return drop(numberOfDropped);
	}
	
	/**
	 * This will cause the roll to drop the lowest X numbers rolled.
	 * @param numberOfDropped how many dices to drop
	 * @return the updated dice roll itself
	 * @see #d
	 */
	public ExtendableDice drop(int numberOfDropped) {
		modifiers.add(new DropModifier(numberOfDropped));
		return this;
	}
	
	/**
	 * This will count every dice that rolled at least X as a success (1) 
	 * @param neededNumber the target number that is required to count as a success
	 * @return the updated dice roll itself
	 * @see #successIf
	 */
	public ExtendableDice s(int neededNumber) {
		return successIf(neededNumber);
	}
	
	/**
	 * This will count every dice that rolled at least X as a success (1) 
	 * @param neededNumber the target number that is required to count as a success
	 * @return the updated dice roll itself
	 * @see #s
	 */
	public ExtendableDice successIf(int neededNumber) {
		modifiers.add(new SuccessModifier(neededNumber));
		return this;
	}
	
	/**
	 * This will only leave the X best dices in the roll
	 * @param numberOfKept the number of dices kept
	 * @return the updated dice roll itself
	 * @see #keep
	 */
	public ExtendableDice k(int numberOfKept) {
		return keep(numberOfKept);
	}
	
	/**
	 * This will only leave the X best dices in the roll
	 * @param numberOfKept the number of dices kept
	 * @return the updated dice roll itself
	 * @see #k
	 */
	public ExtendableDice keep(int numberOfKept) {
		modifiers.add(new KeepModifier(numberOfKept));
		return this;
	}

	/**
	 * This will reroll every dice in the roll that is lower than X
	 * @param minimumRequired the number that is at least required to not be rerolled
	 * @return the updated dice roll itself
	 * @see #reroll
	 */
	public ExtendableDice r(int minimumRequired) {
		return reroll(minimumRequired);
	}
	
	/**
	 * This will reroll every dice in the roll that is lower than X
	 * @param minimumRequired the number that is at least required to not be rerolled
	 * @return the updated dice roll itself
	 * @see #r
	 */
	public ExtendableDice reroll(int minimumRequired) {
		modifiers.add(new RerollModifier(minimumRequired));
		return this;
	}
	
	/**
	 * This will add one die to the roll for every die that rolled the maximum possible value
	 * @return the updated dice roll itself
	 * @see #explode
	 */
	public ExtendableDice e() {
		return explode();
	}
	
	/**
	 * This will add one die to the roll for every die that rolled the maximum possible value
	 * @return the updated dice roll itself
	 * @see #e
	 */
	public ExtendableDice explode() {
		modifiers.add(new ExplodingModifier());
		return this;
	}

	@Override
	protected int[] modifyResult(int[] orig, Random r) {
		int[] modified=Arrays.copyOf(orig, orig.length);
		for(DiceModifier dm:modifiers)
			modified=dm.modify(this, modified, r);
		return modified;
	}
}