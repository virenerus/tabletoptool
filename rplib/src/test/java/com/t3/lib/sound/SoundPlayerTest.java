/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
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
