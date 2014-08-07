package com.t3.xstreamversioned.model;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import com.t3.xstreamversioned.version.Version;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

public class GenericObjectManager {
		
	private HashMap<Integer, GenericObject> parsedObjects=new HashMap<>();
	private int nextId=0;
	private int nextNegativeId=-1;
	
	public GenericObject parse(HierarchicalStreamReader reader, UnmarshallingContext context) {
		String referencedId=reader.getAttribute("reference");
		
		//if this is only a reference to an object already parsed earlier
		if(referencedId!=null) {
			GenericObject go=parsedObjects.get(Integer.parseInt(referencedId));
			if(go==null)
				throw new RuntimeException("Reference to object "+referencedId+" before it occured directly");
			else
				return go;
		}
		else {
			int internalId=reader.getAttribute("id")==null?nextNegativeId--:Integer.parseInt(reader.getAttribute("id"));
			
			GenericObject go=new GenericObject(this,internalId);
			parsedObjects.put(internalId, go);
			nextId=Math.max(internalId+1, nextId);
			
			go.setName(reader.getNodeName());
			
			//collect all attributes to ensure compatibility
			HashMap<String, String> xStreamAttributes=new HashMap<String, String>();
			for(int i=0;i<reader.getAttributeCount();i++) {
				String attrName=reader.getAttributeName(i);
				if(!"version".equals(attrName))
					xStreamAttributes.put(attrName, reader.getAttribute(i));
			}
			go.setXStreamAttributes(xStreamAttributes);
			
			//version
			String version=reader.getAttribute("version");
			if(version!=null) go.setCurrentVersion(Version.parseVersion(version));
			
			//if child nodes
			if(reader.hasMoreChildren()) {
				reader.moveDown();
				if(StringUtils.isEmpty(reader.getValue()))
				go.addChild(parse(reader, null));
				reader.moveUp();
			}
			else
				go.setContent(reader.getValue());
			return go;
		}
	}

	public GenericObject getObject(Integer childId) {
		GenericObject go=parsedObjects.get(childId);
		if(go==null)
			throw new IllegalArgumentException("No Object of id "+childId+" was parsed.");
		else
			return go;
	}
	
	public GenericObject createObject() {
		GenericObject go= new GenericObject(this, nextId++);
		parsedObjects.put(go.getInternalId(), go);
		return go;
	}

	public void updateObject(GenericObject go) {
		parsedObjects.put(go.getInternalId(), go);
	}
}
