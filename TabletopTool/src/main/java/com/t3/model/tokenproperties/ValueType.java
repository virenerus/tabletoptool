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
package com.t3.model.tokenproperties;

import java.util.Objects;

import javax.annotation.Nonnull;

import com.jidesoft.grid.BooleanCheckBoxCellEditor;
import com.jidesoft.grid.CellEditorManager;
import com.jidesoft.grid.CellRendererManager;
import com.jidesoft.grid.EditorContext;
import com.t3.client.ui.token.CappedIntegerCellEditor;
import com.t3.client.ui.token.CappedIntegerCellRenderer;
import com.t3.client.ui.token.DiceExpressionCellEditor;
import com.t3.client.ui.token.PropertyMacroViewCellEditor;
import com.t3.dice.expression.Expression;
import com.t3.macro.api.views.PropertyMacroView;
import com.t3.model.tokenproperties.instance.CappedInteger;
import com.t3.model.tokenproperties.instance.Struct;
import com.t3.model.tokenproperties.instance.list.EmptyTokenPropertiesList;
import com.t3.model.tokenproperties.instance.list.TokenPropertiesList;
import com.t3.model.tokenproperties.instance.list.TokenPropertiesListImpl;
import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(0)
public enum ValueType {
	BOOLEAN("Boolean", Boolean.class) {
		@Override
		public EditorContext getEditorContext() {
			return BooleanCheckBoxCellEditor.CONTEXT;
		}

		@Override
		public Boolean getDefaultDefaultValue(PropertyType propertyType) {
			return Boolean.FALSE;
		}

		@Override
		public Boolean convert(PropertyType propertyType, Object object) {
			if(object instanceof Boolean)
				return (Boolean) object;
			else if(object instanceof String && "true".equalsIgnoreCase((String) object))
				return Boolean.TRUE;
			else
				return null;
		}
	},
	
	
	TEXT("Text", String.class) {
		@Override
		public String getDefaultDefaultValue(PropertyType propertyType) {
			return "";
		}

		@Override
		public String convert(PropertyType propertyType, Object object) {
			return Objects.toString(object);
		}
	},
	
	
	INTEGER("Integer", Integer.class) {
		@Override
		public Integer getDefaultDefaultValue(PropertyType propertyType) {
			return 0;
		}

		@Override
		public Integer convert(PropertyType propertyType, Object object) {
			if(object instanceof Integer)
				return (Integer)object;
			else if(object instanceof Float)
				return ((Float)object).intValue();
			else if(object instanceof String) {
				try {
					return Integer.valueOf((String)object);
				} catch(NumberFormatException e) {
					return null;
				}
			}
			else if(object instanceof CappedInteger) {
				return ((CappedInteger) object).getValue();
			}
			else
				return null;
		}
	},
	
	
	FLOAT("Float", Float.class) {
		@Override
		public Float getDefaultDefaultValue(PropertyType propertyType) {
			return 0f;
		}

		@Override
		public Float convert(PropertyType propertyType, Object object) {
			if(object instanceof Float)
				return (Float)object;
			else if(object instanceof Integer)
				return ((Integer)object).floatValue();
			else if(object instanceof String) {
				try {
					return Float.valueOf((String)object);
				} catch(NumberFormatException e) {
					return null;
				}
			}
			else if(object instanceof CappedInteger) {
				return (float)((CappedInteger) object).getValue();
			}
			else
				return null;
		}
	},
	
	
	CAPPED("Capped", CappedInteger.class) {
		@Override
		public String toStatsheetString(Object propertyValue) {
			if(propertyValue==null)
				return "null";
			else {
				CappedInteger capped=(CappedInteger)propertyValue;
				return capped.getValue()+" / "+capped.getMax();
			}
		}

		@Override
		public void registerCellEditors() {
			CellRendererManager.registerRenderer(CappedInteger.class, new CappedIntegerCellRenderer());
			CellEditorManager.registerEditor(CappedInteger.class, new CappedIntegerCellEditor.Factory());
		}

		@Override
		public CappedInteger getDefaultDefaultValue(PropertyType propertyType) {
			return new CappedInteger(0,0,0);
		}

		@Override
		public CappedInteger convert(PropertyType propertyType, Object object) {
			if(object instanceof CappedInteger)
				return (CappedInteger) object;
			else if(object instanceof Integer) {
				Integer o=(Integer) object;
				return new CappedInteger(o, o, o);
			}
			else if(object instanceof Float) {
				int o=((Float) object).intValue();
				return new CappedInteger(o, o, o);
			}
			else
				return null;
		}
	},
	
	
	EXPRESSION("Expression", Expression.class) {

		@Override
		public Expression getDefaultDefaultValue(PropertyType propertyType) {
			return Expression.ZERO_EXPRESSION;
		}

		@Override
		public Expression convert(PropertyType propertyType, Object object) {
			if(object instanceof Expression)
				return (Expression)object;
			else
				return null;
		}
		
		@Override
		public void registerCellEditors() {
			CellEditorManager.registerEditor(Expression.class, new DiceExpressionCellEditor.Factory());
		}
	},
	
	
	MACRO("Macro", PropertyMacroView.class) {

		@Override
		public PropertyMacroView getDefaultDefaultValue(PropertyType propertyType) {
			return PropertyMacroView.EMPTY_MACRO;
		}

		@Override
		public PropertyMacroView convert(PropertyType propertyType, Object object) {
			if(object instanceof PropertyMacroView)
				return (PropertyMacroView) object;
			else
				return null;
		}
		
		@Override
		public void registerCellEditors() {
			CellEditorManager.registerEditor(PropertyMacroView.class, new PropertyMacroViewCellEditor.Factory());
		}
	},
	
	
	LIST("List",TokenPropertiesList.class) {

		@SuppressWarnings("rawtypes")
		@Override
		public TokenPropertiesList getDefaultDefaultValue(PropertyType propertyType) {
			return new EmptyTokenPropertiesList();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public TokenPropertiesList convert(PropertyType propertyType, Object object) {
			if(object instanceof TokenPropertiesListImpl) {
				TokenPropertiesListImpl tpl=(TokenPropertiesListImpl) object;
				if(tpl.getPropertyType()==propertyType)
					return tpl;
				else
					return new TokenPropertiesListImpl(propertyType, tpl);
			}
			else
				return null;
		}
		
	},
	
	
	STRUCT("Struct",Struct.class) {
		@Override
		public Struct getDefaultDefaultValue(PropertyType propertyType) {
			return new Struct(propertyType);
		}

		@Override
		public Struct convert(PropertyType propertyType, Object object) {
			if(object instanceof Struct) {
				Struct struct=(Struct)object;
				if(struct.getPropertyType()==propertyType)
					return struct;
				else
					return new Struct(propertyType, struct);
			}
			else
				return null;
		}
	};
	
	private final Class<?> type;
	private final String name;
	
	private <T> ValueType(String name, Class<T> type) {
		this.type=type;
		this.name=name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public Class<?> getType() {
		return type;
	}
	
	public boolean isInstance(Object obj) {
		return type.isInstance(obj);
	}
	
	public EditorContext getEditorContext() {
		return null;
	}
	
	public @Nonnull String toStatsheetString(Object propertyValue) {
		return Objects.toString(propertyValue);
	}
	
	public void registerCellEditors() {
		
	}
	
	public abstract Object getDefaultDefaultValue(PropertyType propertyType);
	/**
	 * This method should try to convert the given object into an object of the type described. Otherwise return 0;
	 * @param object
	 * @return
	 */
	public abstract Object convert(PropertyType propertyType, Object object);
}
