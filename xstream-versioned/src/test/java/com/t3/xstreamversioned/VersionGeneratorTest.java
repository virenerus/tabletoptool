package com.t3.xstreamversioned;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.t3.xstreamversioned.version.Version;
import com.t3.xstreamversioned.version.VersionElement;
import com.t3.xstreamversioned.version.VersionGenerator;

public class VersionGeneratorTest {
	
	@Test
	public void versionGeneratorTest() {
		Version list=VersionGenerator.generateVersion(ArrayList.class,true,Collections.<String>emptySet());
		Assert.assertEquals(list, Version.parseVersion("[0@java.util.AbstractCollection, 0@java.util.AbstractList, 0@java.util.ArrayList]"));
	}
}
