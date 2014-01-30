package net.rptools.maptool.script.mt2.functions;

import net.rptools.maptool.client.ui.htmlframe.HTMLFrame;
import net.rptools.maptool.client.ui.htmlframe.HTMLFrameFactory;

public class DialogFunctions {
	public boolean isDialogVisible(String name) {
		return HTMLFrameFactory.isVisible(false, name);
	}
	
	public boolean isFrameVisible(String name) {
		return HTMLFrameFactory.isVisible(true, name);
	}
	
	public void closeDialog(String name) {
		HTMLFrameFactory.close(false, name);
	}
	
	public void closeFrame(String name) {
		HTMLFrameFactory.close(true, name);
	}
	
	public void resetFrame(String name) {
		HTMLFrame.center(name);
	}
}
