package com.t3.xstreamversioned.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.t3.xstreamversioned.version.Version;

public class GenericObject {
	
	private Version currentVersion;
	private LinkedHashSet<Integer> children;
	private String content;
	private String name;
	private Map<String, String> xStreamAttributes;
	/**
	 * this internal id this object has
	 */
	private final int internalId;
	private final GenericObjectManager genericObjectManager;
	private Integer referencing;
	
	public GenericObject(GenericObjectManager genericObjectManager, int internalId) {
		this.children=new LinkedHashSet<>();
		this.internalId=internalId;
		this.genericObjectManager=genericObjectManager;
	}

	public Version getCurrentVersion() {
		return currentVersion;
	}
	
	public Map<String, String> getXStreamAttributes() {
		return Collections.unmodifiableMap(xStreamAttributes);
	}

	public void setVersion(Version version) {
		this.currentVersion = version;
		xStreamAttributes.put("version", version.toString());
	}

	public List<GenericObject> getChildren(String name) {
		ArrayList<GenericObject> l=new ArrayList<>(children.size());
		for(Integer childId:children) {
			GenericObject go=genericObjectManager.getObject(childId);
			if(Objects.equals(go.getName(),name))
				l.add(go);
		}
		return Collections.unmodifiableList(l);
	}
	
	public List<GenericObject> getChildren() {
		ArrayList<GenericObject> l=new ArrayList<>(children.size());
		for(Integer childId:children)
			l.add(genericObjectManager.getObject(childId));
		return Collections.unmodifiableList(l);
	}
	
	public GenericObject getChild(String name) {
		GenericObject result=null;
		for(Integer childId:children) {
			GenericObject go=genericObjectManager.getObject(childId);
			if(Objects.equals(go.getName(),name)) {
				if(result==null)
					result=go;
				else
					throw new RuntimeException("There is more than one child with the name "+name);
			}
		}
		return result;
	}
	
	public void addChild(GenericObject child) {
		children.add(child.getInternalId());
	}
	
	public String getName() {
		return name;
	}

	public int getInternalId() {
		return internalId;
	}

	public void setCurrentVersion(Version version) {
		this.currentVersion=version;
	}

	public void setName(String name) {
		this.name=name;
	}

	public void setXStreamAttributes(Map<String, String> xStreamAttributes) {
		this.xStreamAttributes=xStreamAttributes;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public GenericObjectManager getObjectManager() {
		return genericObjectManager;
	}
	
	public void setReferencing(GenericObject ref) {
		if(ref==null) {
			referencing=null;
			xStreamAttributes.remove("reference");
		}
		else {
			this.referencing=ref.getInternalId();
			xStreamAttributes.put("reference", Integer.toString(ref.getInternalId()));
		}
	}
	
	public GenericObject getReferencing() {
		return genericObjectManager.getObject(referencing);
	}
}
