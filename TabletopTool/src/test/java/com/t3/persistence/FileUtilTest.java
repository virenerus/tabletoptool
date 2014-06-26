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
import java.util.zip.ZipInputStream;

import junit.framework.TestCase;

import com.t3.persistence.FileUtil;

public class FileUtilTest extends TestCase {

	private static final String TMP_DIR = "c:\\tmp\\test";
	
	public void testUnzipStream() throws Exception {
		
		File tmpDir = new File(TMP_DIR);
		try {
			if (tmpDir.exists()) {
				FileUtil.delete(tmpDir);
			}
			assertFalse(tmpDir.exists());
			
			
			FileUtil.unzip(new ZipInputStream(FileUtilTest.class.getClassLoader().getResourceAsStream("test.zip")), tmpDir);
			assertTrue(tmpDir.exists());
			assertTrue(new File(tmpDir.getAbsolutePath() + "/test/testing1.png").exists());
			assertTrue(new File(tmpDir.getAbsolutePath() + "/test/subdir").exists());
			assertTrue(new File(tmpDir.getAbsolutePath() + "/test/subdir/testing2.png").exists());
			assertTrue(new File(tmpDir.getAbsolutePath() + "/test/subdir/subsubdir/testing3.png").exists());
			
		} finally {
			if (tmpDir.exists()) {
				FileUtil.delete(tmpDir);
			}
		}
	}
}
