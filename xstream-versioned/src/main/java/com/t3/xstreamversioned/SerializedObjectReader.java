package com.t3.xstreamversioned;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import com.thoughtworks.xstream.converters.ErrorWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

public class SerializedObjectReader implements HierarchicalStreamReader {

	LinkedList<SerializedObject> objectStack=new LinkedList<>();
	LinkedList<Integer> childIdStack=new LinkedList<>();
	
	public SerializedObjectReader(SerializedObject ser) {
		objectStack.push(ser);
		childIdStack.push(0);
	}

	@Override
	public boolean hasMoreChildren() {
		return childIdStack.getFirst()<objectStack.getFirst().getChildren().size();
	}
	
	public SerializedObject getCurrentObject() {
		return objectStack.peek();
	}

	@Override
	public void moveDown() {
		int id=childIdStack.poll();
		childIdStack.push(id+1);
		objectStack.push(objectStack.getFirst().getChild(id));
		childIdStack.push(0);
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
