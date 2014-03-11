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
	
	public ExtendableDice d(int numberOfDropped) {
		return drop(numberOfDropped);
	}
	
	public ExtendableDice drop(int numberOfDropped) {
		modifiers.add(new DropModifier(numberOfDropped));
		return this;
	}
	
	public ExtendableDice s(int numberOfDropped) {
		return successIf(numberOfDropped);
	}
	
	public ExtendableDice successIf(int neededNumber) {
		modifiers.add(new SuccessModifier(neededNumber));
		return this;
	}
	
	public ExtendableDice k(int numberOfKept) {
		return keep(numberOfKept);
	}
	
	public ExtendableDice keep(int numberOfKept) {
		modifiers.add(new KeepModifier(numberOfKept));
		return this;
	}
	
	public ExtendableDice r(int numberOfRerolled) {
		return reroll(numberOfRerolled);
	}
	
	public ExtendableDice reroll(int numberOfRerolled) {
		modifiers.add(new RerollModifier(numberOfRerolled));
		return this;
	}
	
	public ExtendableDice e() {
		return explode();
	}
	
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
