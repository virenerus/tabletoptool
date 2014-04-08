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

import java.util.ArrayList;
import java.util.List;

/**
 * @author trevor
 */
public class AnimationManager {

	private static List<Animatable> animatableList = new ArrayList<Animatable>();

	private static List<Animatable> removeList = new ArrayList<Animatable>();
	private static List<Animatable> addList = new ArrayList<Animatable>();

	private static int delay = 200;

	static {
		new AnimThread().start();
	}

	public static void addAnimatable(Animatable animatable) {

		synchronized (animatableList) {
			if (!animatableList.contains(animatable)) {
				addList.add(animatable);
			}
		}
	}

	public static void removeAnimatable(Animatable animatable) {

		synchronized (animatableList) {
			removeList.remove(animatable);
		}
	}

	private static class AnimThread extends Thread {

		@Override
		public void run() {

			while (true) {

				if (animatableList.size() > 0) {

				}

				synchronized (animatableList) {

					animatableList.addAll(addList);
					addList.clear();

					for (Animatable animatable : animatableList) {
						animatable.animate();
					}

					animatableList.removeAll(removeList);
					removeList.clear();
				}

				try {
					Thread.sleep(delay);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
	}
}
