package com.t3.dice;

import java.util.ArrayList;
import java.util.Random;

public class ExplodingSuccessDice extends SuccessDice {

	public ExplodingSuccessDice(int count, int type, int targetValue) {
		super(count, type, targetValue);
	}
	
	public void rollDice(Random r) {
		ArrayList<Integer> resultList=new ArrayList<Integer>();
		int numberOfDice=count;
		result=0;
		for(int i=0;i<numberOfDice;i++) {
			int roll=r.nextInt(type)+1;
			if(roll>=targetValue)
				result++;
			if(roll==type)
				numberOfDice++;
			resultList.add(roll);
		}
		
		results = new int[resultList.size()];
	    for (int i = 0; i < results.length; i++)
	        results[i] = resultList.get(i);
	}
	
	@Override
	public String toString() {
		return count+"d"+type+"es"+targetValue;
	}
}
