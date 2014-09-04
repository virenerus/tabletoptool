package com.t3.xstreamversioned;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.t3.xstreamversioned.version.Version;
import com.t3.xstreamversioned.version.VersionElement;

public class VersionDeSerializationTest {
	@Test
	public void testVersionDeSerialization() {
		Version versions=new Version();
		versions.add(new VersionElement(Object.class.getName(), 0));
		versions.add(new VersionElement("some.class.name", 23857235));
		versions.add(new VersionElement("com.t3.client.macro.SomeClass", -5));
		versions.add(new VersionElement(SubClass.class.getName(), -5));
		versions.add(new VersionElement(SubClass2.class.getName(), -5));
		
		String versionString=versions.toString();
		
		Version deser=Version.parseVersion(versionString);
		Assert.assertEquals(deser, versions);
		Assert.assertEquals(deser.toString(), versions.toString());
	}
	
	private static class SubClass {}
	private class SubClass2 {}
}
