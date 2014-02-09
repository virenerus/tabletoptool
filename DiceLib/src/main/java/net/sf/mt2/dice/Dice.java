package net.sf.mt2.dice;

import java.util.Random;

public interface Dice {
	public void rollDice(Random r);
	public int getResult();
	public int[] getResults();
	public boolean isRolled();
}
