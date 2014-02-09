package net.rptools.maptool.script;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MT2ScriptException extends Exception {

	public MT2ScriptException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MT2ScriptException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MT2ScriptException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MT2ScriptException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method generates a error message in HTML format. It is useful to dump the error into the chat.
	 */
	public String getHTMLErrorMessage() {
		StringBuilder sb=new StringBuilder();
		sb.append("<span color=\"red\" title=\"");
		StringWriter sw=new StringWriter();
		PrintWriter pw=new PrintWriter(sw);
		this.printStackTrace(pw);
		sb.append(sw.getBuffer().toString().replace("\"", "\\\"").replace("\n", "&#13;"));
		pw.close();
		sb.append("\">");
		
		if(this.getCause()!=null)
			sb.append(this.getCause().getMessage());
		else
			sb.append(this.getMessage());
		sb.append("</span>");
		return sb.toString();
	}
}
