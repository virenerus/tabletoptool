package com.t3.xstreamversioned.version;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version extends ArrayList<VersionElement> {
	
	private static final Pattern VERSION_LIST_PATTERN=Pattern.compile("[,\\[]\\s*(-?\\d+)@([\\w.$-]+)\\s*");

	public Version() {}
	
	public Version(Version version) {
		this.ensureCapacity(version.size());
		for(VersionElement ve:version)
			this.add(new VersionElement(ve));
	}

	public static Version parseVersion(String versionList) {
		try {
			Version list=new Version();
			Matcher m=VERSION_LIST_PATTERN.matcher(versionList);
			while(m.find()) {
				list.add(new VersionElement(m.group(2),Integer.parseInt(m.group(1))));
			}
			return list;
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException(versionList+" is no valid version list",e);
		}
	}
}
