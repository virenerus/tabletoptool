package net.sf.mt2.dice;

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
			int roll=singleRoll(r);
			if(roll==type)
				numberOfThrows++;
			res.add(roll);
			result+=roll;
		}
	}
}
