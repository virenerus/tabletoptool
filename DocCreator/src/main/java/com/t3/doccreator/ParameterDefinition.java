package com.t3.doccreator;

public class ParameterDefinition {
	private final String type;
	private final String name;
	private String comment;
	
	
	public ParameterDefinition(String type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
