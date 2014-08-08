package com.t3.xstreamversioned.marshalling;

import com.t3.xstreamversioned.migration.MigrationManager;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.core.ReferenceByIdMarshaller;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class MigratingMarshaller extends ReferenceByIdMarshaller {

	private MigrationManager mm;

	public MigratingMarshaller(MigrationManager mm, HierarchicalStreamWriter writer, ConverterLookup converterLookup, Mapper mapper) {
		super(writer, converterLookup, mapper);
		this.mm=mm;
	}
	
	@Override
	public void convert(Object item, Converter converter) {
		super.convert(item, new MigratingConverter(mm, converter));
	}
}