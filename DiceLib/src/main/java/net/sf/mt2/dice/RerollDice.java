package net.sf.mt2.dice;


public class RerollDice extends DropDice {

	public RerollDice(int count, int type, int reroll) {
		super(count+reroll, type, reroll);
	}

	@Override
	public String toString() {
		return (count-drop)+"d"+type+"r"+drop;
	}
}
