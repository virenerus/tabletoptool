package net.sf.mt2.dice;

public class SuccessDice extends SimpleDice {

	private int reroll;

	public SuccessDice(int count, int type, int reroll) {
		super(count, type);
		this.reroll=reroll;
	}

}
