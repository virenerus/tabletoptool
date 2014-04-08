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
package com.t3.client.ui.macrobuttons.buttons;

import java.io.Serializable;

import com.t3.model.MacroButtonProperties;

public class TransferData implements Serializable {
		
	public int index=0;
	public String command="";
	public String colorKey="";
	public String hotKey="";
	public String label="";
	public String group="";
	public String sortby="";
	public boolean autoExecute=true;
	public boolean includeLabel=false;
	public boolean applyToTokens=true;
	public String fontColorKey="";
	public String fontSize="";
	public String minWidth="";
	public String maxWidth="";
	public String panelClass="";
	public String toolTip="";
	
	public TransferData(MacroButton button) {
		MacroButtonProperties prop = button.getProperties();
		this.index = prop.getIndex();
		this.label = prop.getLabel();
		this.command = prop.getCommand();
		this.colorKey = prop.getColorKey();
		this.hotKey = prop.getHotKey();
		this.group = prop.getGroup();
		this.sortby = prop.getSortby();
		this.panelClass = button.getPanelClass();
		this.fontColorKey = prop.getFontColorKey();
		this.fontSize = prop.getFontSize();
		this.minWidth = prop.getMinWidth();
		this.maxWidth = prop.getMaxWidth();
		this.toolTip = prop.getToolTip();
	}

}
