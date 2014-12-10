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
package com.t3.client.ui.campaignproperties;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jidesoft.grid.AbstractExpandableRow;
import com.jidesoft.grid.BooleanCheckBoxCellEditor;
import com.jidesoft.grid.EditorContext;
import com.jidesoft.grid.TreeTableModel;
import com.t3.client.ui.campaignproperties.PropertyTypesTableModel.PropertyTypeRow;
import com.t3.model.tokenproperties.PropertyType;
import com.t3.model.tokenproperties.ValueType;
import com.t3.model.tokenproperties.instance.Struct;
import com.t3.model.tokenproperties.instance.list.TokenPropertiesList;
import com.t3.model.tokenproperties.instance.list.TokenPropertiesListImpl;

public class PropertyTypesTableModel extends TreeTableModel<PropertyTypeRow> {

	private static final long serialVersionUID = 1L;
	
	
	private final String[] columnNames={"Type", "Name", "Shortname", "Default Value", 
			"GM Only", "On Statsheet", "Owner Only"};

	public PropertyTypesTableModel(List<PropertyType> properties) {
		this.setAutoExpand(true);
		
		if(properties==null) {
			ArrayList<PropertyTypeRow> l=new ArrayList<PropertyTypeRow>(10);
			for(int i=0;i<3;i++)
				l.add(new PropertyTypeRow(new PropertyType()));
			this.setOriginalRows(l);
		}
		else {
			ArrayList<PropertyTypeRow> l=new ArrayList<PropertyTypeRow>(properties.size());
			for(PropertyType p:properties)
				l.add(new PropertyTypeRow(new PropertyType(p)));
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
	
	
	
	
	
	public class PropertyTypeRow extends AbstractExpandableRow {

		private PropertyType property;
		private ArrayList<PropertyTypeRow> subRows;

		public PropertyTypeRow(PropertyType p) {
			this.property=p;
			createSubRows();
		}
		
		private void createSubRows() {
			subRows=new ArrayList<>();
			for(PropertyType tp:property.getSubTypes()) {
				subRows.add(new PropertyTypeRow(tp));
			}
		}

		@Override
		public boolean isCellEditable(int columnIndex) {
			//editable if not the default value of list or struct
			return !(columnIndex==3 && (property.getType()==ValueType.LIST 
									||  property.getType()==ValueType.STRUCT));
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
				ValueType newType = (ValueType)value;
				property=property.withType(newType);
				
				//change list type if this is the child of a list
				if(this.getLevel()>0 && ((PropertyTypeRow)this.getParent()).property.getType()==ValueType.LIST) {
					PropertyTypeRow p = (PropertyTypeRow)this.getParent();
					p.setValueAt(new TokenPropertiesListImpl<>(p.property), 3);
				}
				//change list type if this is a list
				if(this.property.getType()==ValueType.LIST) {
					this.setValueAt(new TokenPropertiesListImpl<>(property), 3);
				}
				createSubRows();
				PropertyTypesTableModel.this.refresh();
			}
			else if(columnIndex==1)
				property=property.withName((String)value);
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
				return ValueType.class;
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

		public PropertyType getProperty() {
			return property;
		}

		@Override
		public List<PropertyTypeRow> getChildren() {
			return subRows;
		}

		@Override
		public void setChildren(List<?> l) {
			System.out.println(l);
		}
		
		@Override
		public PropertyTypesTableModel getTreeTableModel() {
			return (PropertyTypesTableModel) super.getTreeTableModel();
		}
	}





	public List<PropertyType> collectTokenProperties() {
		ArrayList<PropertyType> list=new ArrayList<>();
		for(PropertyTypeRow r:this.getOriginalRows()) {
			if(!StringUtils.isBlank(r.getProperty().getName())) {
				PropertyType prop=r.getProperty();
				list.add(collectTokenProperties(prop, r.getChildren()));
			}
		}
		return list;
	}

	private PropertyType collectTokenProperties(PropertyType prop, List<PropertyTypeRow> children) {
		ArrayList<PropertyType> subTypes=new ArrayList<>();
		for(PropertyTypeRow childRow:children) {
			PropertyType tp=childRow.getProperty();
			if(prop.getType()==ValueType.LIST || !StringUtils.isBlank(tp.getName()))
				subTypes.add(collectTokenProperties(tp,childRow.getChildren()));
		}
		return prop.withSubTypes(subTypes);
	}
}
