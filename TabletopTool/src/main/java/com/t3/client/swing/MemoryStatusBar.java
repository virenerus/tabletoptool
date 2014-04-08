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
package com.t3.client.swing;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.text.DecimalFormat;

import javax.swing.JProgressBar;

/**
 */
public class MemoryStatusBar extends JProgressBar {
	private static final long serialVersionUID = 1L;

	private static final Dimension minSize = new Dimension(75, 10);
	private static final DecimalFormat format = new DecimalFormat("#,##0.#");
	private static double largestMemoryUsed = -1;
	private static MemoryStatusBar msb = null;

	public static MemoryStatusBar getInstance() {
		if (msb == null)
			msb = new MemoryStatusBar();
		return msb;
	}

	private MemoryStatusBar() {
		setMinimum(0);
		setStringPainted(true);

		new Thread() {
			@Override
			public void run() {
				while (true) {
					update();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ie) {
						break;
					}
				}
			}
		}.start();
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				System.gc();
				update();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getMinimumSize()
	 */
	@Override
	public Dimension getMinimumSize() {
		return minSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		return getMinimumSize();
	}

	public double getLargestMemoryUsed() {
		return largestMemoryUsed;
	}

	private void update() {
		double totalMegs = Runtime.getRuntime().totalMemory() / (1024 * 1024);
		double freeMegs = Runtime.getRuntime().freeMemory() / (1024 * 1024);

		if (totalMegs > largestMemoryUsed)
			largestMemoryUsed = totalMegs;

		setMaximum((int) totalMegs);
		setValue((int) (totalMegs - freeMegs));
		setString(format.format(totalMegs - freeMegs) + "M/" + format.format(totalMegs) + "M");
		setToolTipText("Used Memory: " + (totalMegs - freeMegs) + "M, Total Memory: " + totalMegs + "M");
	}
}
