package net.sf.mt2.dice;

import java.util.Random;

public class FudgeDice implements Dice {

	private int count;
	private int result;
	private int[] results;

	public FudgeDice(int count) {
		this.count=count;
	}
	
	public void rollDice(Random r) {
		results=new int[count];
		result=0;
		for(int i=0;i<count;i++) {
			results[i]=r.nextInt(3)-1;
			result=results[i];
		}
		
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
