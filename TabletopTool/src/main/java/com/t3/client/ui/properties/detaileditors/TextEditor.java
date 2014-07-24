package com.t3.client.ui.properties.detaileditors;

import java.awt.TextArea;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jidesoft.swing.AutoResizingTextArea;

public class TextEditor extends DetailEditor<String> {

	private JTextArea	textArea;

	public TextEditor() {
		textArea=new AutoResizingTextArea(1,15);
		this.add(new JScrollPane(textArea));
	}
	
	@Override
	public String getValue() {
		return textArea.getText();
	}

	@Override
	public void setTypedValue(String value) {
		textArea.setText(value);
	}

}
