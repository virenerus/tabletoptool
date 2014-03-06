package com.t3.client.ui.campaignproperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jidesoft.grid.AbstractRow;
import com.jidesoft.grid.BooleanCheckBoxCellEditor;
import com.jidesoft.grid.EditorContext;
import com.jidesoft.grid.IndexReferenceRow;
import com.jidesoft.grid.TreeTableModel;
import com.t3.model.campaign.TokenProperty;
import com.t3.model.campaign.TokenPropertyType;

public class PropertyTypesTableModel extends TreeTableModel<PropertyTypeRow> {

	private static final long serialVersionUID = 1L;
	
	
	private final String[] columnNames={"Type", "Name", "Shortname", "Default Value", 
			"GM Only", "On Statsheet", "Owner Only"};

	public PropertyTypesTableModel(List<TokenProperty> properties) {
		if(properties==null) {
			ArrayList<PropertyTypeRow> l=new ArrayList<PropertyTypeRow>(10);
			for(int i=0;i<10;i++)
				l.add(new PropertyTypeRow(new TokenProperty()));
			setOriginalRows(l);
		}
		else {
			ArrayList<PropertyTypeRow> l=new ArrayList<PropertyTypeRow>(properties.size());
			for(TokenProperty p:properties)
				l.add(new PropertyTypeRow(new TokenProperty(p)));
			setOriginalRows(l);	
		}
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}
}

class PropertyTypeRow extends AbstractRow {

	private TokenProperty property;

	public PropertyTypeRow(TokenProperty p) {
		this.property=p;
	}
	
	@Override
	public boolean isCellEditable(int columnIndex) {
		return true;
	}
	
	@Override
	public EditorContext getEditorContextAt(int columnIndex) {
		if(columnIndex>3)
			return BooleanCheckBoxCellEditor.CONTEXT;
		else if(columnIndex==3)
			return property.getType().getEditorContext();
		return super.getEditorContextAt(columnIndex);
	}
	
	@Override
	public void setValueAt(Object value, int columnIndex) {
		if(columnIndex==0) {
			TokenPropertyType newType = (TokenPropertyType)value;
			property.setType(newType);
			property.setDefaultValue(newType.getDefaultDefaultValue());
			cellUpdated(6);
		}
		else if(columnIndex==1)
			property.setName((String)value);
		else if(columnIndex==2)
			property.setShortName((String)value);
		else if(columnIndex==3)
			property.setDefaultValue(value);
		else if(columnIndex==4)
			property.setGMOnly((Boolean)value);
		else if(columnIndex==5)
			property.setShowOnStatSheet((Boolean)value);
		else if(columnIndex==6)
			property.setOwnerOnly((Boolean)value);
		else
			throw new Error("There should never be an eight column in the tokenproperty table");
	}
	
	@Override
	public Class<?> getCellClassAt(int columnIndex) {
		if(columnIndex==0)
			return TokenPropertyType.class;
		else if(columnIndex==1)
			return String.class;
		else if(columnIndex==2)
			return String.class;
		else if(columnIndex==3)
			return property.getType().getType();
		else if(columnIndex==4)
			return Boolean.class;
		else if(columnIndex==5)
			return Boolean.class;
		else if(columnIndex==6)
			return Boolean.class;
		else
			throw new Error("There should never be an eight column in the tokenproperty table");
	}
	
	@Override
	public Object getValueAt(int columnIndex) {
		if(columnIndex==0)
			return property.getType();
		else if(columnIndex==1)
			return property.getName();
		else if(columnIndex==2)
			return property.getShortName();
		else if(columnIndex==3)
			return property.getDefaultValue();
		else if(columnIndex==4)
			return property.isGMOnly();
		else if(columnIndex==5)
			return property.isShowOnStatSheet();
		else if(columnIndex==6)
			return property.isOwnerOnly();
		else
			throw new Error("There should never be an eight column in the tokenproperty table");
	}

	public TokenProperty getProperty() {
		return property;
	}
	
}
