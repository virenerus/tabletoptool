package com.t3.dice;

import java.util.Arrays;
import java.util.Random;

import com.t3.dice.modifiers.DiceModifier;
import com.t3.dice.modifiers.ExplodingModifier;
import com.t3.dice.modifiers.SuccessModifier;


public class ShadowrunDice extends Dice {

	private int	gremlinValue=0;
	private boolean exploding=false;
	private int zeroes;

	public ShadowrunDice(int numberOfDices) {
		super(numberOfDices, 1, 6);
	}

	/**
	 * This method returns the number of ones rolled which is important for Shadowrun rolls
	 * @return
	 */
	public int getNumberOfOnes() {
		rollIfNeeded();
		return zeroes;
	}
	
	/**
	 * @return If this roll is a gremlin roll.
	 */
	public boolean isGremlinRoll() {
		return gremlinValue==0;
	}
	
	/**
	 * This method determines if the roll was a glitch (ones at least half of the dice minus gremlin Value)
	 * @return if this roll was a glitch
	 */
	public boolean isGlitch() {
		return getNumberOfOnes() >= numberOfDices-gremlinValue;
	}
	
	/**
	 * This method determines if the roll was a glitch (ones at least half of the dice minus gremlin Value)
	 * @return if this roll was a glitch
	 */
	public boolean isCriticalGlitch() {
		return isGlitch() && result==0;
	}

	@Override
	protected int[] modifyResult(int[] orig, Random r) {
		int[] modified=Arrays.copyOf(orig, orig.length);
		if(exploding)
			modified=new ExplodingModifier().modify(this, modified, r);
		zeroes=0;
		for(int i:modified)
			if(i==maximumValue)
				zeroes++;
			
		modified=new SuccessModifier(5).modify(this, modified, r);
		return modified;
	}
	
	public ShadowrunDice e() {
		return explode();
	}
	
	public ShadowrunDice explode() {
		exploding=true;
		return this;
	}
	
	public ShadowrunDice g(int gremlinValue) {
		return gremlin(gremlinValue);
	}
	
	public ShadowrunDice gremlin(int gremlinValue) {
		this.gremlinValue=gremlinValue;
		return this;
	}
}
