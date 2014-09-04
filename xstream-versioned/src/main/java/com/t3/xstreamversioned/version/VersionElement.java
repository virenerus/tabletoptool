package com.t3.xstreamversioned.version;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionElement {
	private String fullyQualifiedName;
	private int serializationVersion;
	
	public VersionElement(String fullyQualifiedName, int serializationVersion) {
		this.fullyQualifiedName=fullyQualifiedName;
		this.serializationVersion=serializationVersion;
	}

	public VersionElement(VersionElement ve) {
		this(ve.fullyQualifiedName, ve.serializationVersion);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((fullyQualifiedName == null) ? 0 : fullyQualifiedName
						.hashCode());
		result = prime * result + serializationVersion;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VersionElement other = (VersionElement) obj;
		if (fullyQualifiedName == null) {
			if (other.fullyQualifiedName != null)
				return false;
		} else if (!fullyQualifiedName.equals(other.fullyQualifiedName))
			return false;
		if (serializationVersion != other.serializationVersion)
			return false;
		return true;
	}

	public String getFullyQualifiedName() {
		return fullyQualifiedName;
	}

	public void setFullyQualifiedName(String fullyQualifiedName) {
		this.fullyQualifiedName = fullyQualifiedName;
	}

	public int getSerializationVersion() {
		return serializationVersion;
	}

	public void setSerializationVersion(int serializationVersion) {
		this.serializationVersion = serializationVersion;
	}
	
	@Override
	public String toString() {
		return serializationVersion+"@"+fullyQualifiedName;
	}
}