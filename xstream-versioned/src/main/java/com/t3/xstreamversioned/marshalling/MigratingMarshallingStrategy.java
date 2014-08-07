package com.t3.xstreamversioned.marshalling;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.t3.xstreamversioned.migration.MigrationManager;
import com.t3.xstreamversioned.migration.Migrator;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.core.AbstractTreeMarshallingStrategy;
import com.thoughtworks.xstream.core.TreeMarshaller;
import com.thoughtworks.xstream.core.TreeUnmarshaller;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class MigratingMarshallingStrategy extends AbstractTreeMarshallingStrategy {

	private final MigrationManager migrationManager;
	
	public MigratingMarshallingStrategy(MigrationManager migrationManager) {
		this.migrationManager=migrationManager;
	}
	
	@Override
	protected TreeUnmarshaller createUnmarshallingContext(Object root,
        HierarchicalStreamReader reader, ConverterLookup converterLookup, Mapper mapper) {
        return new MigratingUnmarshaller(migrationManager,root, reader, converterLookup, mapper);
    }

    @Override
	protected TreeMarshaller createMarshallingContext(
        HierarchicalStreamWriter writer, ConverterLookup converterLookup, Mapper mapper) {
        return new MigratingMarshaller(migrationManager,writer, converterLookup, mapper);
    }
}
