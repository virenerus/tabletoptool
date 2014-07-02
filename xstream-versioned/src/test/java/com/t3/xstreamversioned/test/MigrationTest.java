package com.t3.xstreamversioned.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.t3.xstreamversioned.SerializationVersion;
import com.t3.xstreamversioned.SerializedObject;
import com.t3.xstreamversioned.Migrator;
import com.t3.xstreamversioned.VersionedMarshallingStrategy;
import com.thoughtworks.xstream.XStream;

public class MigrationTest {
	@Test
	public void runTest() {
		String xml="<com.t3.xstreamversioned.test.MigrationTest_-TestClass><value>hallo4</value></com.t3.xstreamversioned.test.MigrationTest_-TestClass>";
		XStream xstream=new XStream();
		VersionedMarshallingStrategy strategy=new VersionedMarshallingStrategy();
		strategy.registerTypeUpdater(TestClass.class, new Migrator(0,1) {
			@Override
			protected void update(SerializedObject ser) {
				SerializedObject value=ser.getChildren("value").get(0);
				value.setContent(value.getContent().substring(5));
			}
		});
		xstream.setMarshallingStrategy(strategy);
		TestClass tc=(TestClass) xstream.fromXML(xml);
		Assert.assertEquals(tc.value, 4);
	}
	
	@SerializationVersion(1)
	private static class TestClass {
		int value;
	}
}
