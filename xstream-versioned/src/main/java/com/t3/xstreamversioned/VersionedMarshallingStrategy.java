package com.t3.xstreamversioned;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.core.AbstractTreeMarshallingStrategy;
import com.thoughtworks.xstream.core.ReferenceByIdMarshaller;
import com.thoughtworks.xstream.core.ReferenceByIdUnmarshaller;
import com.thoughtworks.xstream.core.TreeMarshaller;
import com.thoughtworks.xstream.core.TreeUnmarshaller;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class VersionedMarshallingStrategy extends AbstractTreeMarshallingStrategy {

	private HashMap<Class<?>, Map<Integer, Migrator>> typeUpdaters=new HashMap<>();
	private final boolean strict;
	
	public VersionedMarshallingStrategy() {
		this(true);
	}
	
	public VersionedMarshallingStrategy(boolean strict) {
		this.strict=strict;
	}
	
	public void registerTypeUpdater(Class<?> type, Migrator updater) {
		Map<Integer, Migrator> m=typeUpdaters.get(type); 
		if(m==null) {
			m=new HashMap<>();
			typeUpdaters.put(type, m);
		}
		m.put(updater.getUpdatesFromVersion(), updater);	
	}
	
	public Migrator getTypeUpdater(Class<?> type, int fromVersion) {
		return typeUpdaters.getOrDefault(type, Collections.EMPTY_MAP).get(fromVersion);
	}
	
	protected TreeUnmarshaller createUnmarshallingContext(Object root,
        HierarchicalStreamReader reader, ConverterLookup converterLookup, Mapper mapper) {
        return new VersionedUnmarshaller(this,root, reader, converterLookup, mapper);
    }

    protected TreeMarshaller createMarshallingContext(
        HierarchicalStreamWriter writer, ConverterLookup converterLookup, Mapper mapper) {
        return new VersionedMarshaller(this,writer, converterLookup, mapper);
    }

    public static class VersionedMarshaller extends ReferenceByIdMarshaller {

    	private VersionedMarshallingStrategy vms;

		public VersionedMarshaller(VersionedMarshallingStrategy vms, HierarchicalStreamWriter writer, ConverterLookup converterLookup, Mapper mapper) {
    		super(writer, converterLookup, mapper);
    		this.vms=vms;
    	}
    	
    	@Override
    	public void convert(Object item, Converter converter) {
    		super.convert(item, new VersionedConverter(vms, converter));
    	}
    }
    
    public static class VersionedUnmarshaller extends ReferenceByIdUnmarshaller {

    	private VersionedMarshallingStrategy vms;

		public VersionedUnmarshaller(VersionedMarshallingStrategy vms, Object root, HierarchicalStreamReader reader, 
    			ConverterLookup converterLookup, Mapper mapper) {
    		super(root, reader, converterLookup, mapper);
    		this.vms=vms;
    	}

    	@Override
    	protected Object convert(Object parent, Class type, Converter converter) {
    		return super.convert(parent, type, new VersionedConverter(vms, converter));
    	}
    	
    	public HierarchicalStreamReader getReader() {
    		return this.reader;
    	}

		public void setReader(HierarchicalStreamReader tmp) {
			this.reader=tmp;
		}
    }

	public boolean isStrict() {
		return strict;
	}
}