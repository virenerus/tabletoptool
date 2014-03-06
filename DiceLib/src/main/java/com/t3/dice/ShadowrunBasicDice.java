package com.t3.dice;

import java.util.Random;

public class ShadowrunBasicDice implements Dice {

	private int count;
	private int hits;
	private int ones;
	private int[] results;

	public ShadowrunBasicDice(int count) {
		this.count=count;
	}
	
	public void rollDice(Random r) {
		results=new int[count];
		ones=0;
		hits=0;
		for(int i=0;i<count;i++) {
			results[i]=r.nextInt(6)+1;
			if(results[i]==0)
				ones++;
			else if(results[i]>=5)
				hits++;
		}
	}

	public int getResult() {
		return hits-ones;
	}

	public int[] getResults() {
		return results;
	}
	
	public int getHits() {
		return hits;
	}
	
	public int getOnes() {
		return ones;
	}

	public boolean isRolled() {
		return results!=null;
	}
	
	public boolean isGlitch() {
		return ones*2>=count;
	}
	
	public boolean isCriticalGlitch() {
		return hits==0 && ones*2>=count;
	}
	
	@Override
	public String toString() {
		return count+"sr4";
	}
}
