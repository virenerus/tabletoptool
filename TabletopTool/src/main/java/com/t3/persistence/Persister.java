package com.t3.persistence;

import com.t3.persistence.converter.AssetImageConverter;
import com.t3.xstreamversioned.VersionedMarshallingStrategy;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;

public class Persister {
	
	/**
	 * This method returns a non thread safe fully configured version of xstream that should be used for all serialization and
	 * deserialization tasks.
	 * @return the xstream instance
	 */
	public static XStream newInstance() {
		XStream xstream=new XStream();
		xstream.setMarshallingStrategy(new VersionedMarshallingStrategy());
		
		//ADD ALL CONVERTERS HERE THAT DECIDE HOW TO SERIALIZE DESERIALIZE CERTAIN CLASSES
		xstream.registerConverter((Converter)new AssetImageConverter());
		//asset -> assetImageCOnverter
		return null;
	}
}
