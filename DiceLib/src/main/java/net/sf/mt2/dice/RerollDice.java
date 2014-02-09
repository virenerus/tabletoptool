package net.sf.mt2.dice;

public class RerollDice extends SimpleDice {

	private int reroll;

	public RerollDice(int count, int type, int reroll) {
		super(count, type);
		this.reroll=reroll;
	}

}
