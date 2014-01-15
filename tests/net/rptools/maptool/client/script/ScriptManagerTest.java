/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.rptools.maptool.client.script;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class ScriptManagerTest extends TestCase {
	public void testDoit() throws IOException {
		Map<String, Object> globals = new HashMap<String, Object>();

		globals.put("myString", "MY STRING");
		System.out.println(ScriptManager.evaluate(globals, "rptools.test.echo(myString)"));

		globals.put("myValue", 10);
		System.out.println(ScriptManager.evaluate(globals, "rptools.test.add(myValue)"));

		globals.put("myArray", new Object[] { 10, 20, 30 });
		System.out.println(ScriptManager.evaluate(globals, "rptools.test.arrayLength(myArray)"));
	}

	public void testRegisterFunctions() throws IOException {
		Reader reader = new InputStreamReader(ScriptManager.class.getClassLoader().getResourceAsStream("net/rptools/maptool/client/script/js/rptools.test.js"));
		ScriptManager.registerFunctions(reader);
	}
}
