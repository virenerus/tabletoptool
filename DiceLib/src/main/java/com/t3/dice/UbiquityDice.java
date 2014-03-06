package com.t3.dice;

import java.util.Random;

public class UbiquityDice implements Dice {

	private int count;
	private int result;
	private int[] results;

	public UbiquityDice(int count) {
		this.count=count;
	}
	
	public void rollDice(Random r) {
		results=new int[count];
		for(int i=0;i<count;i++) {
			if(r.nextBoolean()) {
				results[i]=1;
				result++;
			}
			else
				results[i]=0;
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
