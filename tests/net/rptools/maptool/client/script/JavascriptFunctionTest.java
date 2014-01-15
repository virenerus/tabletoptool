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

import java.math.BigDecimal;

import junit.framework.TestCase;
import net.rptools.parser.Expression;
import net.rptools.parser.Parser;
import net.rptools.parser.ParserException;

public class JavascriptFunctionTest extends TestCase {
	public void testFunction_VariableArguments() throws ParserException {
		Parser parser = new Parser();

		parser.addFunction(new JavascriptFunction("rptools.test.parserFunction", JavascriptFunction.ArrayType.Numbers, 0, -1, true, "tpf"));

		Expression xp = parser.parseExpression("tpf(\"foo\", 10, 20, 30)");

		Object result = xp.evaluate();
//		System.out.printf("%s: %s (%s)", xp.format(), result, result.getClass().getName());
	}

	public void testFunction_FixedArguments() throws ParserException {
		Parser parser = new Parser();

		parser.addFunction(new JavascriptFunction("rptools.test.parserFunctionFixedArguments", JavascriptFunction.ArrayType.Numbers, 0, -1, true, "tpf"));

		Expression xp = parser.parseExpression("tpf(10, 20)");

		Object result = xp.evaluate();
//		System.out.printf("%s: %s (%s)", xp.format(), result, result.getClass().getName());
	}

	public void testFunction_GetVariable() throws ParserException {
		Parser parser = new Parser();

		parser.addFunction(new JavascriptFunction("rptools.test.parserFunctionGetVariable", JavascriptFunction.ArrayType.Numbers, 0, -1, true, "tpgv"));

		parser.setVariable("myVar", new BigDecimal(100));
		parser.setVariable("myStringVar", "fnord");

		Expression xp = parser.parseExpression("tpgv('myVar')");

		assertEquals(new BigDecimal(100), xp.evaluate());
		assertEquals("fnord", parser.parseExpression("tpgv('myStringVar')").evaluate());
	}

	public void testFunction_SetVariable() throws ParserException {
		Parser parser = new Parser();

		parser.addFunction(new JavascriptFunction("rptools.test.parserFunctionSetVariable", JavascriptFunction.ArrayType.Numbers, 0, -1, true, "tpsv"));

		Expression xp = parser.parseExpression("tpsv('myVar')");
		xp.evaluate();

		assertEquals(new BigDecimal(10), parser.getVariable("myVar"));
	}

	public void testFunction_Evaluate() throws ParserException {
		Parser parser = new Parser();

		parser.addFunction(new JavascriptFunction("rptools.test.parserEvaluate", JavascriptFunction.ArrayType.Numbers, 0, -1, true, "tpe"));

		Expression xp = parser.parseExpression("tpe()");
		assertEquals(new BigDecimal(12), xp.evaluate());
	}
}
