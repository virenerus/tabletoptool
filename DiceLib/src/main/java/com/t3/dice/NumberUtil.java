package com.t3.dice;

public class NumberUtil {
	public static String formatFloat(float f) {
		if(f == (int) f)
	        return String.format("%d",(int)f);
	    else
	        return String.format("%s",f);
	}
}
