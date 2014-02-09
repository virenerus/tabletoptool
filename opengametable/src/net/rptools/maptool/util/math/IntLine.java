package net.rptools.maptool.util.math;

public class IntLine extends IntPoint {
	private final int x2;
	private final int y2;
	
	public IntLine(int x1, int y1, int x2, int y2) {
		super(x1,y1);
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public int getX1() {
		return getX();
	}
	public int getX2() {
		return x2;
	}
	public int getY1() {
		return getY();
	}
	public int getY2() {
		return y2;
	}
	
	
}
