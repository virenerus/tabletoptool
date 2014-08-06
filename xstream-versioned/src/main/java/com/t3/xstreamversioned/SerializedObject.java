package com.t3.xstreamversioned;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SerializedObject {
	private String version;
	private final String type;
	private ArrayList<SerializedObject> children;
	private String content;
	private String name;
	/**
	 * be aware that this field stores all atributes and should thus also be changed if version, type or ids are changed
	 */
	private Map<String, String> allAttributes;
	/**
	 * this internal id this object has
	 */
	private final int internalId;
	/**
	 * this id is something else than -1 if this object is only a reference to the object with the given internal id
	 */
	private int referenceId=-1;
	
	public SerializedObject(String type, int internalId, String currentVersion, String name, Map<String, String> allAttributes) {
		children=new ArrayList<>();
		this.version = currentVersion;
		this.type=type;
		this.internalId=internalId;
		this.name=name;
		this.allAttributes=allAttributes;
	}

	public String getVersion() {
		return version;
	}
	
	public LinkedHashMap<String, String> getAttributes() {
		return new LinkedHashMap<>(allAttributes);
	}

	public void setVersion(String version) {
		this.version = version;
		allAttributes.put("version", version);
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
		allAttributes.put("reference", Integer.toString(referenceId));
	}

}
