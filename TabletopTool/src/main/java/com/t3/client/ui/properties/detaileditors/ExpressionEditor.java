package com.t3.client.ui.properties.detaileditors;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.t3.chatparser.generated.ChatParser;
import com.t3.chatparser.generated.ParseException;
import com.t3.client.TabletopTool;
import com.t3.dice.expression.Expression;

public class ExpressionEditor extends DetailEditor<Expression> {

	private JFormattedTextField	textField;

	public ExpressionEditor() {
		textField=new JFormattedTextField(); 
		textField.setInputVerifier(new ExpressionEditorInputVerifier());
		this.add(textField);
	}
	
	@Override
	public Expression getValue() {
		if(StringUtils.isBlank(textField.getText()))
			return null;
		else
			try {
				return new ChatParser(textField.getText()).parseExpression();
			} catch (ParseException e) {
				TabletopTool.showError("The expression "+textField.getText()+" could not be parsed", e);
				return null;
			}
	}

	@Override
	public void setTypedValue(Expression value) {
		textField.setText(value.toString());
	}

	private static class ExpressionEditorInputVerifier extends InputVerifier {

		@Override
		public boolean verify(JComponent input) {
			String text=((JFormattedTextField)input).getText();
			if(StringUtils.isBlank(text))
				return true;
			else
				try {
					new ChatParser(text).parseExpression();
					return true;
				} catch (ParseException e) {
					TabletopTool.showWarning("The expression "+text+" could not be parsed", e);
					return false;
				}
		}
		
	}
}
