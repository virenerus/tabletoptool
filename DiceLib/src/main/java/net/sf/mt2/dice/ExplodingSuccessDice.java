package net.sf.mt2.dice;

public class ExplodingSuccessDice extends SimpleDice {

	private int reroll;

	public ExplodingSuccessDice(int count, int type, int reroll) {
		super(count, type);
		this.reroll=reroll;
	}

}
