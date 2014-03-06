package com.t3.script.api.functions.token;

public class TokenLocation {
	private final int x;
	private final int y;
	private final int z;
	
	public TokenLocation(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}
}
