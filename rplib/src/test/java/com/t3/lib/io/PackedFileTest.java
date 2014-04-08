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
package com.t3.lib.io;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import com.t3.io.PackedFile;

public class PackedFileTest extends TestCase {

	public void testNewFile() throws IOException {

		PackedFile pfile = new PackedFile(new File("/tmp/pakfile/testing.zip"));

		pfile.setProperty("foo", "bar");
		pfile.setProperty("Hello", 1);

		pfile.setContent("Hello world");

		pfile.putFile("test.txt", "I am the master of your universe".getBytes("UTF-8"));

		pfile.save();
	}
}
