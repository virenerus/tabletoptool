package com.t3.xstreamversioned.marshalling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import com.t3.xstreamversioned.model.GenericObject;
import com.thoughtworks.xstream.converters.ErrorWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

public class SerializedObjectReader implements HierarchicalStreamReader {

	HashSet<Integer> readObjects=new HashSet<>();	
	LinkedList<GenericObject> objectStack=new LinkedList<>();
	LinkedList<Integer> childIdStack=new LinkedList<>();
	
	public SerializedObjectReader(GenericObject ser) {
		objectStack.push(ser);
		childIdStack.push(0);
	}

	@Override
	public boolean hasMoreChildren() {
		return childIdStack.getFirst()<objectStack.getFirst().getChildren().size();
	}
	
	public GenericObject getCurrentObject() {
		return objectStack.peek();
	}

	@Override
	public void moveDown() {
		int id=childIdStack.poll();
		childIdStack.push(id+1);
		objectStack.push(getObjectOrReference(new ArrayList<>(objectStack.getFirst().getChildren()).get(id)));
		childIdStack.push(0);
	}

	private GenericObject getObjectOrReference(GenericObject go) {
		if(readObjects.contains(go.getInternalId())) {
			GenericObject ref=new GenericObject(go.getObjectManager(), -1);
			ref.setXStreamAttributes(Collections.singletonMap("referencedId", Integer.toString(go.getInternalId())));
			return ref;
		}
		else {
			readObjects.add(go.getInternalId());
			return go;
		}
	}

	@Override
	public void moveUp() {
		childIdStack.poll();
		objectStack.poll();
	}

	@Override
	public String getNodeName() {
		return objectStack.getFirst().getName();
	}

	@Override
	public String getValue() {
		return objectStack.getFirst().getContent();
	}

	@Override
	public String getAttribute(String name) {
		return objectStack.getFirst().getAttributes().get(name);
	}

	@Override
	public String getAttribute(int index) {
		Iterator<Entry<String, String>> it=objectStack.getFirst().getAttributes().entrySet().iterator();
		for(int i=0;i<index-1;i++)
			it.next();
		return it.next().getValue();
	}

	@Override
	public int getAttributeCount() {
		return objectStack.getFirst().getAttributes().size();
	}

	@Override
	public String getAttributeName(int index) {
		Iterator<Entry<String, String>> it=objectStack.getFirst().getAttributes().entrySet().iterator();
		for(int i=0;i<index-1;i++)
			it.next();
		return it.next().getKey();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Iterator getAttributeNames() {
		return objectStack.getFirst().getAttributes().keySet().iterator();
	}

	@Override
	public void appendErrors(ErrorWriter errorWriter) {
	}

	@Override
	public void close() {}

	@Override
	public HierarchicalStreamReader underlyingReader() {
		return this;
	}
}
