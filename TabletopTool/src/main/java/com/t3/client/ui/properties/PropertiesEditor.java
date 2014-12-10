package com.t3.client.ui.properties;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import com.t3.client.TabletopTool;
import com.t3.client.ui.properties.detaileditors.DetailEditor;
import com.t3.model.Token;
import com.t3.model.tokenproperties.PropertyType;
import com.t3.model.tokenproperties.propertyholder.PropertyHolder;

public class PropertiesEditor extends JPanel {

	private PropertyHolder	propertyHolder;
	private String	propertyType;
	private JPanel	contentPanel;
	private Map<PropertyType,DetailEditor<?>> detailEditors=new HashMap<>();

	public PropertiesEditor() {
		this.setLayout(new BorderLayout());
		contentPanel=new JPanel();
		this.add(new JScrollPane(contentPanel),BorderLayout.CENTER);
		contentPanel.setLayout(new GridBagLayout());
	}

	public void setPropertyType(String propertyType) {
		this.propertyType=propertyType;
		createSubComponents();
	}

	public void setPropertyHolder(PropertyHolder propertyHolder) {
		this.propertyHolder=propertyHolder;
		createSubComponents();
	}

	private void createSubComponents() {
		this.invalidate();
		contentPanel.removeAll();
		detailEditors.clear();
		if(propertyHolder!=null && propertyType!=null) {
			GridBagConstraints c=new GridBagConstraints();
			c.insets=new Insets(5, 5, 5, 5);
			int gridY=0;
			
			for(PropertyType tp:TabletopTool.getCampaign().getCampaignProperties().getTokenPropertyList(propertyType)) {
				DetailEditor<?> de=DetailEditor.createDetailEditor(tp, propertyHolder);
				detailEditors.put(tp, de);
				de.setValue(propertyHolder.getProperty(tp));
				//if columnspan == 1 add with label
				if(de.isSpanningTwoColumns()) {
					c.gridx=0;
					c.gridwidth=2;
					c.gridy=gridY;
					c.anchor=GridBagConstraints.CENTER;
					c.weightx=0;
					c.fill=GridBagConstraints.BOTH;
					c.anchor=GridBagConstraints.CENTER;
				}
				else {
					c.gridx=0;
					c.gridwidth=1;
					c.gridy=gridY;
					c.anchor=GridBagConstraints.FIRST_LINE_START;
					c.weightx=0;
					c.fill=GridBagConstraints.NONE;
					contentPanel.add(new JLabel(tp.getName()+":"),c);
					
					c.gridx=1;
					c.weightx=1.0;
					c.fill=GridBagConstraints.BOTH;
					c.anchor=GridBagConstraints.CENTER;
				}
				
				contentPanel.add(de,c);
				
				gridY++;
			}
		}
		this.revalidate();
	}
	
	public void applyTo(Token propertyHolder) {
		for(Entry<PropertyType, DetailEditor<?>> e:detailEditors.entrySet())
			propertyHolder.setProperty(e.getKey(), e.getValue().getValue());
	}

}
