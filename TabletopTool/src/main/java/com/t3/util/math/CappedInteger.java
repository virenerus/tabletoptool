package com.t3.util.math;

import org.apache.commons.lang3.StringUtils;

public class CappedInteger {
	private int value;
	private int max;
	private int min;
	
	/**
	 * This constructor is for serialization only
	 */
	public CappedInteger() {}
	
	public CappedInteger(int value, int min, int max) {
		super();
		this.value = value;
		this.max = max;
		this.min = min;
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = Math.max(Math.min(value,max),min);
	}
	public void forceValue(int value) {
		this.value = value;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = Math.max(max,min);
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = Math.min(max,min);
	}
	
	@Override
	public String toString() {
		return value+" in range ["+min+';'+max+']';
	}

	public static CappedInteger valueOf(String str) {
		String[] parts=StringUtils.split(str, " inrage[;]");
		if(parts.length!=3)
			throw new IllegalArgumentException("The Input '"+str+"' is not of the form 123@[123;123]");
		int value=Integer.parseInt(parts[0]);
		int min=Integer.parseInt(parts[1]);
		int max=Integer.parseInt(parts[2]);
		return new CappedInteger(value, min, max);
	}

	public boolean isValidValue(int v) {
		return v>=min && v<=max;
	}
	
	public boolean isValidValue() {
		return value>=min && value<=max;
	}
}
