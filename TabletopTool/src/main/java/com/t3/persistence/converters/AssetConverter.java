package com.t3.persistence.converters;

import com.t3.MD5Key;
import com.t3.model.Asset;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class AssetConverter implements Converter {

	@Override
	public boolean canConvert(Class type) {
		return Asset.class.equals(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		Asset asset=(Asset)source;
		writer.startNode("id");
		writer.setValue(asset.getId().toString());
		writer.endNode();
		writer.startNode("name");
		writer.setValue(asset.getName());
		writer.endNode();
		writer.startNode("extension");
		writer.setValue(asset.getImageExtension());
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		reader.moveDown();
		MD5Key id=new MD5Key(reader.getValue());
		reader.moveUp();
		reader.moveDown();
		String name=reader.getValue();
		reader.moveUp();
		reader.moveDown();
		String extension=reader.getValue();
		reader.moveUp();
		
		return new Asset(id, name, extension);
	}

}
