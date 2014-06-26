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
package com.t3.persistence;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import com.t3.persistence.PackedFile;

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
