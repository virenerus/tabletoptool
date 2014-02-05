package net.rptools.maptool.model.campaign;

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
}
