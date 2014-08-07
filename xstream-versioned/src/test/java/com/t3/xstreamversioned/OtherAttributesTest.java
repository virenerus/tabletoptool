package com.t3.xstreamversioned;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.t3.xstreamversioned.marshalling.MigratingMarshallingStrategy;
import com.t3.xstreamversioned.migration.MigrationManager;
import com.thoughtworks.xstream.XStream;

public class OtherAttributesTest {
	@Test
	public void synchronizedCollectionText() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Map<String,String> map=Collections.synchronizedMap(new LinkedHashMap<String, String>());
		map.put("hello", "world");
		map.put("second", "value");
		
		XStream xs=new XStream();
		xs.setMarshallingStrategy(new MigratingMarshallingStrategy(new MigrationManager("com.t3.", true)));
		
		Map<String, String> map2=(Map<String, String>) xs.fromXML(xs.toXML(map));
		
		Assert.assertEquals(map, map2);
		Assert.assertEquals(map.getClass(), map2.getClass());
		
		//make shure that is map is of the same type
		Field f=map.getClass().getDeclaredField("m");
		f.setAccessible(true);
		Assert.assertEquals(f.get(map).getClass(), LinkedHashMap.class);
		Assert.assertEquals(f.get(map2).getClass(), LinkedHashMap.class);
	}
}
