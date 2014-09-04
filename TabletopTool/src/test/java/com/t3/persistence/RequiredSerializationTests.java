package com.t3.persistence;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.t3.model.LightSource;

public class RequiredSerializationTests {
	@Test
	public void testLightSourceSerialization() {
		Map<String, List<LightSource>> map = (Map<String, List<LightSource>>) FileUtil.objFromResource(LightSource.class,"defaultLightSourcesMap.xml");
		for(Object o:map.keySet())
			Assert.assertEquals(o.getClass(), String.class);
		for(Object o:map.values()) {
			Assert.assertTrue(List.class.isAssignableFrom(o.getClass()));
			for(Object ls:(List<?>) o)
				Assert.assertEquals(ls.getClass(), LightSource.class);
		}
	}
}
