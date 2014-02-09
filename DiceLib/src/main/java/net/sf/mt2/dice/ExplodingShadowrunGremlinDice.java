package net.sf.mt2.dice;

import java.util.Random;

public class ExplodingShadowrunGremlinDice implements Dice {

	private int count;
	private int glitch;
	private int result;
	private int[] results;

	public ExplodingShadowrunGremlinDice(int count, int glitch) {
		this.count=count;
		this.glitch=glitch;
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
