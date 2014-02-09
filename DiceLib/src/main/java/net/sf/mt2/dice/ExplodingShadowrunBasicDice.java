package net.sf.mt2.dice;

import java.util.Random;

public class ExplodingShadowrunBasicDice implements Dice {

	private int count;
	private int[] results;
	private int result;

	public ExplodingShadowrunBasicDice(int count) {
		this.count=count;
	}
	
	public void rollDice(Random r) {
		throw new UnsupportedOperationException(this.getClass().getName()+" is not implemented yet.");
	}

	public int getResult() {
		return result;
	}

	public int[] getResults() {
		return results;
	}

	public boolean isRolled() {
		return results!=null;
	}

}
