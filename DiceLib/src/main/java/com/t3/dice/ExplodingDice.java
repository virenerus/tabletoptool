package com.t3.dice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ExplodingDice extends SimpleDice {


	public ExplodingDice(int count, int type) {
		super(count, type);
	}

	public void rollDice(Random r) {
		ArrayList<Integer> res = new ArrayList<Integer>(100);
		int numberOfThrows = count;
		result=0;
		
		for(int i=0;i<numberOfThrows;i++) {
			int roll=r.nextInt(type)+1;
			if(roll==type)
				numberOfThrows++;
			res.add(roll);
			result+=roll;
		}
		
		results = new int[res.size()];
	    for (int i = 0; i < results.length; i++)
	        results[i] = res.get(i);
	}
	
	@Override
	public String toString() {
		return count+"d"+type+"o";
	}
}
