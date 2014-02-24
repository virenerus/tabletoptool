package net.sf.mt2.dice;

import java.util.Arrays;
import java.util.Random;

public class KeepDice extends SimpleDice {

	protected int keep;

	public KeepDice(int count, int type, int keep) {
		super(count, type);
		this.keep=keep;
	}

	public void rollDice(Random r) {
		results=new int[count];
		
		for(int i=0;i<count;i++)
			results[i]=r.nextInt(type)+1;
		
		result=0;
		int[] sorted = Arrays.copyOf(results, results.length);
		Arrays.sort(sorted);
		for(int i=count-keep;i<count;i++)
			result+=sorted[i];
	}
	
	@Override
	public String toString() {
		return count+"d"+type+"k"+keep;
	}
}
