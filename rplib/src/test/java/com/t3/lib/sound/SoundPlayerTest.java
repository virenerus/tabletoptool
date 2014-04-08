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
package com.t3.lib.sound;

import junit.framework.TestCase;

import com.t3.sound.SoundPlayer;

public class SoundPlayerTest extends TestCase {

	public void testPlay() throws Exception {

		System.out.println("1");
		SoundPlayer.play("com/t3/sound/door.mp3");
		System.out.println("2");
		
		SoundPlayer.waitFor();
	}
}
