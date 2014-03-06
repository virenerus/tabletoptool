package com.t3.dice;

import java.util.ArrayList;
import java.util.Random;

public class ExplodingShadowrunBasicDice implements Dice {
	
	private int count;
	private int hits;
	private int ones;
	private int[] results;

	public ExplodingShadowrunBasicDice(int count) {
		this.count=count;
	}
	
	public void rollDice(Random r) {
		ArrayList<Integer> tmpResults = new ArrayList<Integer>(count*2);
		ones=0;
		hits=0;
		int exploding=0;
		for(int i=0;i<count+exploding;i++) {
			int roll=r.nextInt(6)+1;
			tmpResults.add(roll);
			if(roll==0)
				ones++;
			else if(roll>=5) {
				hits++;
				if(roll==6)
					exploding++;
			}
		}
		
		results = new int[tmpResults.size()];
	    for (int i = 0; i < results.length; i++)
	        results[i] = tmpResults.get(i);
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
		return count+"sr4e";
	}
}
