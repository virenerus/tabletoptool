package com.t3.xstreamversioned;

import java.util.Arrays;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

import com.t3.xstreamversioned.VersionedMarshallingStrategy.VersionedUnmarshaller;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class VersionedConverter implements Converter {

	private Converter parentConverter;
	private VersionedMarshallingStrategy strategy;

	public VersionedConverter(VersionedMarshallingStrategy vms, Converter converter) {
		this.strategy=vms;
		this.parentConverter=converter;
	}
	
	@Override
	public boolean canConvert(Class type) {
		return true;
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		String version=VersionGenerator.generateVersionAsString(source.getClass(), strategy.isStrict(), strategy.getIgnoredPackages());
		if(version!=null)
			writer.addAttribute("version", version);
		parentConverter.marshal(source, writer, context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		if(reader instanceof SerializedObjectReader) {//we already build a tree -> reuse
			SerializedObject ser=((SerializedObjectReader)reader).getCurrentObject();
			ser=migrate(ser, context.getRequiredType());
			Object obj=parentConverter.unmarshal(reader, context);
			return obj;
		}
		else {
			SerializedObject ser=buildXMLTree(reader, context);
			
			ser=migrate(ser, context.getRequiredType());
			
			SerializedObjectReader serReader = new SerializedObjectReader(ser);
			VersionedUnmarshaller vum = (VersionedUnmarshaller)context;
			HierarchicalStreamReader tmp = vum.getReader();
			vum.setReader(serReader);
			Object obj=parentConverter.unmarshal(serReader, context);
			vum.setReader(tmp);
			return obj;
		}
	}

	/**
	 * This method migrates the serialized object to the required current version.
	 * @param ser the deserialized object
	 * @param requiredType the type that is expected
	 * @return a migrated version of the serialized object
	 */
	private SerializedObject migrate(SerializedObject ser, Class<?> requiredType) {
		
		int[] targetVersions=VersionGenerator.generateVersion(requiredType, strategy.isStrict(), strategy.getIgnoredPackages());
		if(targetVersions!=null) {
			String[] versionStrings=StringUtils.split(ser.getVersion(), '.');
			if(versionStrings!=null && targetVersions.length<versionStrings.length)
				System.out.println("WTF");
			int[] currentVersions=new int[targetVersions.length];
			for(int i=0;versionStrings!=null && i<versionStrings.length;i++)
				currentVersions[i]=Integer.parseInt(versionStrings[i]);
			
			for(int i=0;i<targetVersions.length;i++) {
				if(targetVersions[i]>currentVersions[i]) { //migrate
					//select the current type
					Class<?> currentType=requiredType;
					for(int j=0;j<currentVersions.length-i;j++) 
						currentType=currentType.getSuperclass();
					
					//look for migrator
					while(currentVersions[i]<targetVersions[i]) {
						Migrator updater = strategy.getTypeUpdater(requiredType, currentVersions[i]);
						if(updater==null)
							throw new RuntimeException("There is a serialized object of type "+requiredType+" that "
									+ "could only be updated to version "+Arrays.toString(currentVersions)+". There "
									+ "are updaters missing to migrate it to "+ Arrays.toString(targetVersions));
						else
							currentVersions[i]=updater.updateObject(ser);
					}
				}
			}
		}
		return ser;
	}

	private SerializedObject buildXMLTree(HierarchicalStreamReader reader, UnmarshallingContext context) {
		int internalId=reader.getAttribute("id")==null?-1:Integer.parseInt(reader.getAttribute("id"));
		String referenceId=reader.getAttribute("reference");
		String currentVersion=reader.getAttribute("version");
		
		//collect all attributes to ensure compatibility
		HashMap<String, String> allAttributes=new HashMap<String, String>();
		for(int i=0;i<reader.getAttributeCount();i++)
			allAttributes.put(reader.getAttributeName(i), reader.getAttribute(i));
		
		SerializedObject ser=new SerializedObject(
				(context==null || context.getRequiredType()==null)?reader.getAttribute("class"):context.getRequiredType().getName(), 
				internalId, currentVersion, 
				reader.getNodeName(),
				allAttributes);
		if(referenceId!=null)
			ser.setReferenceId(Integer.parseInt(referenceId));
		
		if(reader.hasMoreChildren()) {
			while(reader.hasMoreChildren()) {
				reader.moveDown();
				ser.addChild(buildXMLTree(reader, null));
				reader.moveUp();
			}
		}
		else
			ser.setContent(reader.getValue());
		return ser;
	}

}
