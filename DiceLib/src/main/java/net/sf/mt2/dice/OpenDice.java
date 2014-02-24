package net.sf.mt2.dice;

import java.util.ArrayList;
import java.util.Random;

public class OpenDice extends SimpleDice {


	public OpenDice(int count, int type) {
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
			result=Math.max(roll,result);
		}
		
		results = new int[res.size()];
	    for (int i = 0; i < results.length; i++)
	        results[i] = res.get(i);
	}
	
	public int getSum() {
		int sum=0;
		for(int i=0;i<results.length;i++)
			sum+=results[i];
		return sum;
	}
	
	@Override
	public String toString() {
		return count+"d"+type+"e";
	}
}
