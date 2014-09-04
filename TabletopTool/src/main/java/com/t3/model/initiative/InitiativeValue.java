package com.t3.model.initiative;

import com.t3.xstreamversioned.version.SerializationVersion;

/**
 * This class represents one initiative value. It can hold a string or a number and is comparable. 
 * @author Virenerus
 */
@SerializationVersion(0)
public abstract class InitiativeValue implements Comparable<InitiativeValue>{
	
	private InitiativeValue() {}
	
	public String getValueAsString() {
		throw new RuntimeException("This initiative is not of type String");
	}
	
	public Number getValueAsNumber() {
		throw new RuntimeException("This initiative is not of type Number");
	}
	
	public abstract Object getValue();
	
	@Override
	public abstract String toString();
	
	public static InitiativeValue create(String value) {
		if(value==null)
			return null;
		return new StringInitiativeValue(value);
	}
	
	public static InitiativeValue create(Number value) {
		if(value==null)
			return null;
		return new NumberInitiativeValue(value);
	}
	
	
	@SerializationVersion(0)
	private static class StringInitiativeValue extends InitiativeValue {
		private final String value;
		
		public StringInitiativeValue(String value) {
			this.value=value;
		}
		
		@Override
		public String getValueAsString() {
			return value;
		}

		@Override
		public int compareTo(InitiativeValue o) {
			if(o == null)
				return 1;
			else if(o instanceof StringInitiativeValue)
				return String.CASE_INSENSITIVE_ORDER.compare(value, o.getValueAsString());
			else
				return -1;
		}

		@Override
		public Object getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}
	
	
	
	@SerializationVersion(0)
	private static class NumberInitiativeValue extends InitiativeValue {
		private final Number value;
		
		public NumberInitiativeValue(Number value) {
			this.value=value;
		}
		
		@Override
		public Number getValueAsNumber() {
			return value;
		}

		@Override
		public int compareTo(InitiativeValue o) {
			if(o==null)
				return -1;
			else if(o instanceof NumberInitiativeValue)
				return Double.compare(value.doubleValue(),o.getValueAsNumber().doubleValue());
			else
				return 1;
		}

		@Override
		public Object getValue() {
			return value;
		}

		@Override
		public String toString() {
			if(value!=null)
				return value.toString();
			else
				return "";
		}
	}
}
