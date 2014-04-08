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
package com.t3.sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class SoundPlayer {

	private static ExecutorService playerThreadPool = Executors.newCachedThreadPool();
	private static AtomicInteger playerCount = new AtomicInteger();
	
	public static void play(File file) throws IOException {
		try {
			Player player = new Player(new FileInputStream(file));
			play(player);
		} catch (JavaLayerException jle) {
			throw new IOException (jle.toString());
		}
	}
	
	public static void play(URL url) throws IOException {
		try {
			Player player = new Player(url.openStream());
			play(player);
		} catch (JavaLayerException jle) {
			throw new IOException (jle.toString());
		}
	}
	
	public static void play(String sound) throws IOException {
		try {
			Player player = new Player(SoundPlayer.class.getClassLoader().getResourceAsStream(sound));
			play(player);
			player.close();
		} catch (JavaLayerException jle) {
			throw new IOException (jle.toString());
		} catch (NullPointerException npe) {
			throw new IOException ("Could not find sound: " + sound);
		}
	}

	/**
	 * Wait for all sounds to stop playing (Mostly for testing purposes)
	 */
	public static void waitFor() {

		while (playerCount.get() > 0) {
			try {
				synchronized(playerCount) {
					playerCount.wait();
				}
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}
	
	private static void play(final Player player) {
		playerCount.incrementAndGet();

		playerThreadPool.submit(new Runnable() {
			@Override
			public void run() {
				try {
					player.play();
					playerCount.decrementAndGet();
					synchronized (playerCount) {
						playerCount.notify();
					}
				} catch (JavaLayerException jle) {
					jle.printStackTrace();
				}
			}
		});
	}
}
