package com.t3.dice;

import java.util.Random;

public class SuccessDice implements Dice {

	protected int targetValue;
	protected int result;
	protected int[] results;
	protected int count;
	protected int type;

	public SuccessDice(int count, int type, int targetValue) {
		this.count=count;
		this.type=type;
		this.targetValue=targetValue;
	}

	public void rollDice(Random r) {
		results=new int[count];
		result=0;
		for(int i=0;i<count;i++) {
			results[i]=r.nextInt(type)+1;
			if(results[i]>=targetValue)
				result++;
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
	
	@Override
	public String toString() {
		return count+"d"+type+"s"+targetValue;
	}

}
