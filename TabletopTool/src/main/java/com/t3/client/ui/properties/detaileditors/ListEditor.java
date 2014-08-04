package com.t3.client.ui.properties.detaileditors;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.jidesoft.pane.CollapsiblePane;
import com.t3.client.AppStyle;
import com.t3.client.TabletopTool;
import com.t3.image.ImageUtil;
import com.t3.model.Token;
import com.t3.model.properties.PropertyHolder;
import com.t3.model.properties.TokenPropertiesList;
import com.t3.model.properties.TokenProperty;

public class ListEditor<TYPE> extends DetailEditor<TokenPropertiesList<TYPE>> {

	private static final Logger log=Logger.getLogger(ListEditor.class);
	private CollapsiblePane	collapsiblePane;
	private JPanel listElementsPane;
	private TokenProperty	listElementsProperty;
	private ArrayList<DetailEditor<TYPE>> subEditors=new ArrayList<>();
	private PropertyHolder	propertyHolder;

	public ListEditor(TokenProperty tokenProperty, PropertyHolder propertyHolder) {
		super(true);
		this.propertyHolder=propertyHolder;
		this.listElementsProperty=tokenProperty.getSubType();
		
		collapsiblePane=new CollapsiblePane(tokenProperty.getName());
		collapsiblePane.setStyle(CollapsiblePane.SEPARATOR_STYLE);
		
		this.add(collapsiblePane);
		JPanel contentPane = new JPanel(new BorderLayout());
		collapsiblePane.setContentPane(contentPane);
		
		listElementsPane=new JPanel(new GridBagLayout());
		contentPane.add(listElementsPane);
		
		JPanel buttonPanel=new JPanel(new BorderLayout());
		{
			JButton newButton;
			try {
				newButton=new JButton(new ImageIcon(ImageUtil.getImage("com/t3/client/image/add.png")));
			} catch (IOException e) {
				log.log(Level.WARN, "could not load 'add.png'", e);
				newButton=new JButton("Add New");
			}
			newButton.addActionListener(new NewButtonClickedActionListener());
			buttonPanel.add(newButton,BorderLayout.WEST);
		}
		collapsiblePane.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private class NewButtonClickedActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			addNewDetailsEditor(listElementsProperty.getDefaultValue());
			collapsiblePane.revalidate();
		}
	}
	
	
	@Override
	public TokenPropertiesList<TYPE> getValue() {
		ArrayList<TYPE> list=new ArrayList<>();
		for(DetailEditor<TYPE> de:subEditors) {
			TYPE value=de.getValue();
			if(value!=null)
				list.add(value);
		}
		return new TokenPropertiesList<TYPE>(
				listElementsProperty.getType(),
				list);
	}

	@Override
	public void setTypedValue(TokenPropertiesList<TYPE> value) {
		listElementsPane.removeAll();
		subEditors.clear();
		
		for(Object v:value)
			addNewDetailsEditor(v);
		collapsiblePane.revalidate();
	}

	public void addNewDetailsEditor(Object value) {
		DetailEditor<?> de=DetailEditor.createDetailEditor(listElementsProperty, propertyHolder);

		GridBagConstraints c=new GridBagConstraints();
		c.insets=new Insets(5, 5, 5, 5);
		c.gridx=0;
		c.gridwidth=1;
		c.gridy=subEditors.size();
		c.weightx=0;
		JButton deleteButton=new JButton(new ImageIcon(AppStyle.removeButton));
		deleteButton.addActionListener(new RemoveListEntryListener(subEditors.size()));
		listElementsPane.add(deleteButton,c);
		if(de.isSpanningTwoColumns()) {
			c.gridx=1;
			c.gridwidth=2;
			c.gridy=subEditors.size();
			c.anchor=GridBagConstraints.CENTER;
			c.weightx=1;
			c.fill=GridBagConstraints.BOTH;
		}
		else {
			c.gridx=1;
			c.gridwidth=1;
			c.gridy=subEditors.size();
			c.anchor=GridBagConstraints.FIRST_LINE_START;
			c.weightx=0;
			c.fill=GridBagConstraints.NONE;
			listElementsPane.add(new JLabel((subEditors.size()+1)+":"),c);
			
			c.gridx=2;
			c.weightx=1.0;
			c.fill=GridBagConstraints.BOTH;
			c.anchor=GridBagConstraints.CENTER;
		}
			
			
		
		listElementsPane.add(de,c);
		subEditors.add((DetailEditor<TYPE>) de);
		de.setValue(value);
	}
	
	private class RemoveListEntryListener implements ActionListener {
		private final int index;
		
		public RemoveListEntryListener(int index) {
			this.index=index;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			setTypedValue(getValue().without(index));
		}
	}
}
