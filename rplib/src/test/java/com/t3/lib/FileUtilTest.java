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
package com.t3.lib;

import java.io.File;
import java.util.zip.ZipInputStream;

import junit.framework.TestCase;

import com.t3.FileUtil;

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
