package com.t3.macro.api.functions.input;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/** Adjusts the runtime behavior of components */
public class FixupComponentAdapter extends ComponentAdapter {
	final InputPanel ip;

	public FixupComponentAdapter(InputPanel ip) {
		super();
		this.ip = ip;
	}

	@Override
	public void componentShown(ComponentEvent ce) {
		ip.runtimeFixup();
	}
}
