package com.t3.dice;

import java.util.Arrays;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DiceTests {
	
	@DataProvider(name="diceProvider", parallel=false) 
	public Object[][] getChatParserData () {
		return new Object[][] {
			{DiceBuilder.roll(1).d(6),new Random(1),4,new int[]{4}},
			{DiceBuilder.roll(5).d(8),new Random(1),17,new int[]{6,1,4,4,2}},
			{DiceBuilder.roll(5).d(8).drop(1),new Random(1),16,new int[]{6,4,4,2}},
			{DiceBuilder.roll(5).d(8).keep(3),new Random(1),14,new int[]{6,4,4}},
			{DiceBuilder.roll(5).d(8).reroll(2),new Random(2),28,new int[]{3,4,6,7,8}},
			{DiceBuilder.roll(5).d(8).s(4),new Random(1),3,new int[]{1,0,1,1,0}},
			{DiceBuilder.roll(5).d(8).e(),new Random(2),29,new int[]{1,3,4,6,7,8}},
		};
	}

	@Test(dataProvider="diceProvider")
	public void testParsedChat(Dice d, Random r, int result, int[] results) {
		d.roll(r);
		Assert.assertEquals(d.getResult(),result);
		Arrays.sort(d.getResults());
		Arrays.sort(results);
		Assert.assertEquals(d.getResults(),results, "Expected "+Arrays.toString(results)+" instead of "+Arrays.toString(d.getResults()));
    }
	
	@Test
	public void testHighestResult() {
		Dice d=DiceBuilder.roll(7).d(9);
		d.roll(new Random(1));
		Assert.assertEquals(d.getHighestResult(), 9);
	}
	
	@Test
	public void testHerodBodyDamage() {
		Dice d=DiceBuilder.roll(9).d(6);
		d.roll(new Random(1));
		Assert.assertEquals(d.getHeroBodyDamage(),0);
	}
	
	@Test
	public void testRolling() {
		Dice d=DiceBuilder.roll(3).d(6);
		Assert.assertFalse(d.isRolled());
		d.getResult();
		Assert.assertTrue(d.isRolled());
	}
	
	@Test
	public void specialDices() {
		DiceBuilder.roll(4).df().roll();
		DiceBuilder.roll(4).du().roll();
	}
	
	
	@DataProvider(name="shadowRunDataProvider", parallel=false) 
	public Object[][] getShadowrunDiceTestData() {
		return new Object[][] {
			{DiceBuilder.roll(5).sr4(),new Random(1),1,new int[]{0,0,0,0,1},false,false},
			{DiceBuilder.roll(5).sr4().e(),new Random(5),4,new int[]{0,0,0,1,1,1,1},false,false},
			{DiceBuilder.roll(5).sr4().g(4),new Random(5),2,new int[]{0,0,0,1,1},true,false},
			{DiceBuilder.roll(5).sr4().e().g(4),new Random(5),4,new int[]{0,0,0,1,1,1,1},true,false},
			{DiceBuilder.roll(2).sr4().e().g(4),new Random(3),0,new int[]{0,0},true,true},
		};
	}
	
	@Test(dataProvider="shadowRunDataProvider")
	public void shadowrunDice(ShadowrunDice srd, Random r, int expectedResult, int[] expectedResults, boolean isGlitch, boolean isCritiaclGlitch) {
		srd.roll(r);
		Assert.assertEquals(srd.getResult(), expectedResult);
		Arrays.sort(srd.getResults());
		Arrays.sort(expectedResults);
		Assert.assertEquals(srd.getResults(), expectedResults, "Expected "+Arrays.toString(expectedResults)+" instead of "+Arrays.toString(srd.getResults()));
		Assert.assertEquals(srd.isGlitch(),isGlitch);
		Assert.assertEquals(srd.isCriticalGlitch(), isCritiaclGlitch);
	}
}
