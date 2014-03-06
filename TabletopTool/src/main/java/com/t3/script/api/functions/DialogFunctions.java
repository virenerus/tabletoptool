package com.t3.script.api.functions;

import com.t3.client.ui.htmlframe.HTMLFrame;
import com.t3.client.ui.htmlframe.HTMLFrameFactory;

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
