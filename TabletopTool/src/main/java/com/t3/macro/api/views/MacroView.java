package com.t3.macro.api.views;

import com.t3.macro.MacroException;

public interface MacroView {
	public Object execute() throws MacroException;
}
