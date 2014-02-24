package net.rptools.maptool.script.groovy;

import net.rptools.maptool.model.campaign.TokenPropertyType;
import net.rptools.maptool.util.math.CappedInteger;
import net.rptools.maptool.util.math.IntPoint;
import net.sf.mt2.dice.Dice;

import org.codehaus.groovy.control.customizers.ImportCustomizer;

public class ScriptImportCustomizer extends ImportCustomizer {
	public ScriptImportCustomizer() {
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
