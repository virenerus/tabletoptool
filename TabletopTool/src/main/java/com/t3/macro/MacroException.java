/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3.macro;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MacroException extends Exception {

	public MacroException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MacroException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MacroException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MacroException(Throwable cause) {
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
		try(PrintWriter pw=new PrintWriter(sw)) {
			this.printStackTrace(pw);
			sb.append(sw.getBuffer().toString().replace("\"", "\\\"").replace("\n", "&#13;"));
		}
		sb.append("\">");
		
		if(this.getCause()!=null)
			sb.append(this.getCause().getMessage());
		else
			sb.append(this.getMessage());
		sb.append("</span>");
		return sb.toString();
	}
}
