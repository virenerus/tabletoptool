package net.rptools.maptool.model.campaign;

import net.rptools.maptool.util.math.CappedInteger;

public enum TokenPropertyType {
	TEXT(String.class) {
		@Override
		public String parseObject(String str) {
			return str.trim();
		}

		@Override
		public String printObject(Object obj) {
			return (String) obj;
		}
	},
	INTEGER(Integer.class) {
		@Override
		public Integer parseObject(String str) {
			return Integer.valueOf(str);
		}

		@Override
		public String printObject(Object obj) {
			return ((Integer)obj).toString();
		}
	},
	FLOAT(Float.class) {
		@Override
		public Float parseObject(String str) {
			return Float.valueOf(str);
		}

		@Override
		public String printObject(Object obj) {
			return ((Float)obj).toString();
		}
	},
	CAPPED(CappedInteger.class) {
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

	private TokenPropertyType(Class<?> type) {
		this.type=type;
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
}
