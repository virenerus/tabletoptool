package com.t3.client.ui.properties.detaileditors;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.jidesoft.pane.CollapsiblePane;
import com.t3.model.properties.PropertyHolder;
import com.t3.model.properties.Struct;
import com.t3.model.properties.TokenProperty;

public class StructEditor extends DetailEditor<Struct> {

	private static final Logger log=Logger.getLogger(ListEditor.class);
	private CollapsiblePane	collapsiblePane;
	private JPanel listElementsPane;
	private TokenProperty	structProperty;
	private Map<TokenProperty, DetailEditor<?>> subEditors=new HashMap<>();
	private PropertyHolder	propertyHolder;

	public StructEditor(TokenProperty structProperty, PropertyHolder propertyHolder) {
		super(true);
		this.structProperty=structProperty;
		this.propertyHolder=propertyHolder;
		
		collapsiblePane=new CollapsiblePane(structProperty.getName());
		collapsiblePane.setStyle(CollapsiblePane.SEPARATOR_STYLE);
		
		this.add(collapsiblePane);
		JPanel contentPane = new JPanel(new BorderLayout());
		collapsiblePane.setContentPane(contentPane);
		
		listElementsPane=new JPanel(new GridBagLayout());
		contentPane.add(listElementsPane);
		
		for(TokenProperty subProperty:structProperty.getSubTypes()) {
			subEditors.put(subProperty, createSubEditor(subProperty));
		}
	}
	
	@Override
	public Struct getValue() {
		Struct struct=new Struct();
		for(Entry<TokenProperty, DetailEditor<?>> e:subEditors.entrySet())
			struct.setProperty(e.getKey(), e.getValue().getValue());
		return struct;
	}

	@Override
	public void setTypedValue(Struct value) {
		for(Entry<TokenProperty, DetailEditor<?>> e:subEditors.entrySet())
			e.getValue().setValue(value.getProperty(e.getKey()));
	}

	private DetailEditor<?> createSubEditor(TokenProperty subProperty) {
		DetailEditor<?> de=DetailEditor.createDetailEditor(subProperty, propertyHolder);

		GridBagConstraints c=new GridBagConstraints();
		c.insets=new Insets(5, 5, 5, 5);
		if(de.isSpanningTwoColumns()) {
			c.gridx=0;
			c.gridwidth=2;
			c.gridy=subEditors.size();
			c.anchor=GridBagConstraints.CENTER;
			c.weightx=1;
			c.fill=GridBagConstraints.BOTH;
		}
		else {
			c.gridx=0;
			c.gridwidth=1;
			c.gridy=subEditors.size();
			c.anchor=GridBagConstraints.FIRST_LINE_START;
			c.weightx=0;
			c.fill=GridBagConstraints.NONE;
			listElementsPane.add(new JLabel(subProperty.getName()+":"),c);
			
			c.gridx=1;
			c.weightx=1.0;
			c.fill=GridBagConstraints.BOTH;
			c.anchor=GridBagConstraints.CENTER;
		}
		
		listElementsPane.add(de,c);
		return de;
	}
}