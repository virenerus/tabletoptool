package com.t3.client.ui.macroeditor;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

public class MacroEditor extends RTextScrollPane {
	
	private RSyntaxTextArea	rTextArea;

	public MacroEditor() {
		super(new RSyntaxTextArea(),true);
		rTextArea=(RSyntaxTextArea)this.getTextArea();
		rTextArea.setHighlightCurrentLine(false);
		rTextArea.setCodeFoldingEnabled(true);
		rTextArea.setSyntaxEditingStyle("text/groovy"); //$NON-NLS-1$
		this.setFoldIndicatorEnabled(true);
	}

	public void setMacro(String macro) {
		rTextArea.setText(macro);
		rTextArea.setCaretPosition(0);
		rTextArea.discardAllEdits(); //this removes all edits, otherwise adding all the text is an edit itself
	}

	public String getMacro() {
		return rTextArea.getText();
	}

	public void setMinimumRows(int numberOfRows) {
		rTextArea.setRows(numberOfRows);
	}
}
