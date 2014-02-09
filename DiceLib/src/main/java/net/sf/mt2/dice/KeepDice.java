package net.sf.mt2.dice;

public class KeepDice extends SimpleDice {

	private int keep;

	public KeepDice(int count, int type, int keep) {
		super(count, type);
		this.keep=keep;
	}

}
