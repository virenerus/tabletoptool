package com.t3.xstreamversioned;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class SerializedObject {
	private String version;
	private final String type;
	private ArrayList<SerializedObject> children;
	private String content;
	private String name;
	private final int internalId;
	private int referenceId=-1;
	
	public SerializedObject(String type, int internalId, String currentVersion, String name) {
		children=new ArrayList<>();
		this.version = currentVersion;
		this.type=type;
		this.internalId=internalId;
		this.name=name;
	}

	public String getVersion() {
		return version;
	}
	
	public LinkedHashMap<String, String> getAttributes() {
		LinkedHashMap<String, String> map=new LinkedHashMap<>();
		if(internalId!=-1)
			map.put("id", Integer.toString(internalId));
		if(version!=null)
			map.put("version", version);
		if(type!=null)
			map.put("type", type);
		if(referenceId!=-1)
			map.put("reference", Integer.toString(referenceId));
		if(type!=null)
			map.put("class", type);
		return map;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}
	
	public List<SerializedObject> getChildren(String name) {
		ArrayList<SerializedObject> l=new ArrayList<>();
		for(SerializedObject so:children)
			if(Objects.equals(so.getName(),name))
				l.add(so);
		return Collections.unmodifiableList(l);
	}
	
	public List<SerializedObject> getChildren() {
		return children;
	}
	
	public void addChild(SerializedObject child) {
		children.add(child);
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public boolean hasContent() {
		return content!=null;
	}
	
	public boolean hasFields() {
		return content==null;
	}

	public SerializedObject getChild(int index) {
		return children.get(index);
	}

	public String getName() {
		return name;
	}

	public int getInternalId() {
		return internalId;
	}

	public int getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}
}
