package com.t3.xstreamversioned.marshalling;

import com.t3.xstreamversioned.migration.MigrationManager;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.core.ReferenceByIdUnmarshaller;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;

public class MigratingUnmarshaller extends ReferenceByIdUnmarshaller {

	private MigrationManager mm;

	public MigratingUnmarshaller(MigrationManager mm, Object root, HierarchicalStreamReader reader, 
			ConverterLookup converterLookup, Mapper mapper) {
		super(root, reader, converterLookup, mapper);
		this.mm=mm;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Object convert(Object parent, Class type, Converter converter) {
		return super.convert(parent, type, new MigratingConverter(mm, converter));
	}
	
	public HierarchicalStreamReader getReader() {
		return this.reader;
	}

	public void setReader(HierarchicalStreamReader tmp) {
		this.reader=tmp;
	}
}