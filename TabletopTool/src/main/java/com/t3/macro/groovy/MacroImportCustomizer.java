package com.t3.macro.groovy;

import org.codehaus.groovy.control.customizers.ImportCustomizer;

import com.t3.dice.Dice;
import com.t3.model.campaign.TokenPropertyType;
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
