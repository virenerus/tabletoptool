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
package com.t3.swing;

public class FramesPerSecond extends Thread {

	private int count = 0;
	private int lastFPS = 0;
	
	public FramesPerSecond() {
		setDaemon(true);
	}

	public void bump() {
		count ++;
	}
	
	public int getFramesPerSecond() {
		return lastFPS;
	}
	
	@Override
	public void run() {

		while (true) {
			lastFPS = count;
			count = 0;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}
}
