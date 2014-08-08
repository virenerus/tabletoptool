package com.t3.xstreamversioned.marshalling;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.t3.xstreamversioned.migration.MigrationManager;
import com.t3.xstreamversioned.migration.Migrator;
import com.t3.xstreamversioned.model.GenericObject;
import com.t3.xstreamversioned.version.Version;
import com.t3.xstreamversioned.version.VersionElement;
import com.t3.xstreamversioned.version.VersionGenerator;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class MigratingConverter implements Converter {

	private Converter parentConverter;
	private MigrationManager migrationManager;

	public MigratingConverter(MigrationManager migrationManager, Converter converter) {
		this.migrationManager=migrationManager;
		this.parentConverter=converter;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return true;
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		Version version=VersionGenerator.generateVersion(source.getClass(), migrationManager.isStrict(), migrationManager.getVersionedPackages());
		if(version!=null)
			writer.addAttribute("version", version.toString());
		parentConverter.marshal(source, writer, context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		if(reader instanceof GenericObjectReader) {//we already built a tree -> reuse
			GenericObject ser=((GenericObjectReader)reader).getCurrentObject();
			ser=migrationManager.migrate(ser, context.getRequiredType());
			Object obj=parentConverter.unmarshal(reader, context);
			return obj;
		}
		else {
			GenericObject ser=migrationManager.getGenericObjectManager().parse(reader, context);
			
			ser=migrationManager.migrate(ser, context.getRequiredType());
			
			GenericObjectReader serReader = new GenericObjectReader(ser);
			MigratingUnmarshaller vum = (MigratingUnmarshaller)context;
			HierarchicalStreamReader tmp = vum.getReader();
			vum.setReader(serReader);
			Object obj=parentConverter.unmarshal(serReader, context);
			vum.setReader(tmp);
			return obj;
		}
	}
}
