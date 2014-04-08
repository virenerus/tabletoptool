/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3.dice.modifiers;

import java.util.ArrayList;
import java.util.Random;

import com.t3.dice.Dice;

public class ExplodingModifier implements DiceModifier {

	public int[] modify(Dice d, int[] results, Random r) {
		ArrayList<Integer> a=new ArrayList<Integer>(results.length*2);
		for(int i:results) a.add(i);
		
		int range=1+d.getMaximumDiceValue()-d.getMinimumDiceValue();
		for(int i=0;i<a.size();i++)
			if(a.get(i).equals(d.getMaximumDiceValue()))
				a.add(r.nextInt(range)+d.getMinimumDiceValue());
		int[] res=new int[a.size()];
		for(int i=0;i<a.size();i++)
			res[i]=a.get(i);
		return res;
	}
	
	public String getToStringSuffix() {
		return "e";
	}
}
