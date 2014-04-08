/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3;

public class DebugUtil {

	/**
	 * Get the bits in the number represented as a string
	 * 
	 * @param num
	 * @return the bits in the number represented as a string
	 */
	public static String getBits(long num) {
		String str = "";
		for (int i = 0; i < 64; i++) {
			str = (num & 1) + str;
			num >>= 1;

			if (i % 4 == 3) {
				str = " " + str;
			}
		}
		return str;
	}

	/**
	 * Get the bits in the number represented as a string
	 * 
	 * @param num
	 * @return the bits in the number represented as a string
	 */
	public static String getBits(int num) {
		String str = "";
		for (int i = 0; i < 32; i++) {
			str = (num & 1) + str;
			num >>= 1;

			if (i % 4 == 3) {
				str = " " + str;
			}
		}
		return str;
	}
}
