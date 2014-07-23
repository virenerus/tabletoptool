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
package com.t3.macro.groovy;

import org.codehaus.groovy.control.customizers.ImportCustomizer;

import com.t3.dice.Dice;
import com.t3.model.properties.TokenPropertyType;
import com.t3.util.math.CappedInteger;
import com.t3.util.math.IntPoint;

public class MacroImportCustomizer extends ImportCustomizer {
	public MacroImportCustomizer() {
		super();
		this.addImports(
				CappedInteger.class,
				TokenPropertyType.class,
				IntPoint.class
				);
		this.addStarImports(Dice.class.getPackage().getName());
	}
	
	public void addImports(Class<?> ... cs) {
		for(Class<?> c:cs)
			this.addImports(c.getName());
	}
}
