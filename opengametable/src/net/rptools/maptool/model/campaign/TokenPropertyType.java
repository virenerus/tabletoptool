package net.rptools.maptool.model.campaign;

import com.jidesoft.grid.BooleanCheckBoxCellEditor;
import com.jidesoft.grid.EditorContext;

import net.rptools.maptool.util.math.CappedInteger;

public enum TokenPropertyType {
	BOOLEAN("Boolean", Boolean.class) {
		@Override
		public Boolean parseObject(String str) {
			return Boolean.valueOf(str);
		}

		@Override
		public String printObject(Object obj) {
			return ((Boolean)obj).toString();
		}
		
		@Override
		public EditorContext getEditorContext() {
			return BooleanCheckBoxCellEditor.CONTEXT;
		}
	},
	TEXT("Text", String.class) {
		@Override
		public String parseObject(String str) {
			return str.trim();
		}

		@Override
		public String printObject(Object obj) {
			return (String) obj;
		}
	},
	INTEGER("Integer", Integer.class) {
		@Override
		public Integer parseObject(String str) {
			return Integer.valueOf(str);
		}

		@Override
		public String printObject(Object obj) {
			return ((Integer)obj).toString();
		}
	},
	FLOAT("Float", Float.class) {
		@Override
		public Float parseObject(String str) {
			return Float.valueOf(str);
		}

		@Override
		public String printObject(Object obj) {
			return ((Float)obj).toString();
		}
	},
	CAPPED("Capped", CappedInteger.class) {
		@Override
		public CappedInteger parseObject(String str) {
			return CappedInteger.valueOf(str.replaceAll("\\s", ""));
		}
		
		@Override
		public String printObject(Object obj) {
			CappedInteger capped=((CappedInteger)obj);
			return capped.getValue()+" in ["+capped.getMin()+';'+capped.getMax()+']';
		}
		
		@Override
		public String toStatsheetString(Object propertyValue) {
			if(propertyValue==null)
				return "null";
			else {
				CappedInteger capped=(CappedInteger)propertyValue;
				return capped.getValue()+" / "+capped.getMax();
			}
		}
	};
	
	private Class<?> type;
	private String name;

	private TokenPropertyType(String name, Class<?> type) {
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
	
	public abstract Object parseObject(String str);
	public abstract String printObject(Object obj);

	public boolean isInstance(Object obj) {
		return type.isInstance(obj);
	}

	public String toStatsheetString(Object propertyValue) {
		return propertyValue==null?"null":propertyValue.toString();
	}

	/**
	 * @return something diffrent than null to switch the editors used for this property in a jide grid
	 */
	public EditorContext getEditorContext() {
		return null;
	}
}
