package com.t3.script.api.functions.input;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.t3.language.I18N;
import com.t3.script.MT2ScriptException;

/**********************************************************************************
 * Variable Specifier structure - holds extracted bits of info for a
 * variable.
 **********************************************************************************/
public final class VarSpec {
	public String name, value, prompt;
	public InputType inputType;
	public InputType.OptionMap optionValues;
	public List<String> valueList; // used for types with composite "value" properties

	public VarSpec(String name, String value, String prompt, InputType inputType, String options) throws InputType.OptionException {
		initialize(name, value, prompt, inputType, options);
	}

	/** Create a VarSpec from a non-empty specifier string. */
	public VarSpec(String specifier) throws SpecifierException, InputType.OptionException {
		String[] parts = (specifier).split("\\|");
		int numparts = parts.length;

		String name, value, prompt;
		InputType inputType;

		name = (numparts > 0) ? parts[0].trim() : "";
		if (StringUtils.isEmpty(name))
			throw new SpecifierException(I18N.getText("macro.function.input.invalidSpecifier", specifier));

		value = (numparts > 1) ? parts[1].trim() : "";
		if (StringUtils.isEmpty(value))
			value = "0"; // Avoids having a default value of ""

		prompt = (numparts > 2) ? parts[2].trim() : "";
		if (StringUtils.isEmpty(prompt))
			prompt = name;

		String inputTypeStr = (numparts > 3) ? parts[3].trim() : "";
		inputType = InputType.inputTypeFromName(inputTypeStr);
		if (inputType == null) {
			if (StringUtils.isEmpty(inputTypeStr)) {
				inputType = InputType.TEXT; // default
			} else {
				throw new SpecifierException(I18N.getText("macro.function.input.invalidType", inputTypeStr, specifier));
			}
		}

		String options = (numparts > 4) ? parts[4].trim() : "";

		initialize(name, value, prompt, inputType, options);
	}

	public void initialize(String name, String value, String prompt, InputType inputType, String options) throws InputType.OptionException {
		this.name = name;
		this.value = value;
		this.prompt = prompt;
		this.inputType = inputType;
		this.optionValues = inputType.parseOptionString(options);

		if (inputType != null && inputType.isValueComposite)
			this.valueList = parseStringList(this.value);
	}

	/**
	 * Parses a string into a list of values, for composite types. <br>
	 * Before calling, the <code>inputType</code> and <code>value</code>
	 * must be set. <br>
	 * After calling, the <code>listIndex</code> member is adjusted if
	 * necessary.
	 */
	public List<String> parseStringList(String valueString) {
		List<String> ret = new ArrayList<String>();
		if (valueString != null) {
			String[] values = valueString.split(",");
			int i = 0;
			for (String s : values) {
				ret.add(s.trim());
				i++;
			}
		}
		return ret;
	}

	@SuppressWarnings("serial")
	public class SpecifierException extends MT2ScriptException {
		public SpecifierException(String msg) {
			super(msg);
		}
	}
}
