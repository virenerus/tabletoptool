package com.t3.xstreamversioned.migration;

import com.t3.xstreamversioned.model.GenericObject;
import com.t3.xstreamversioned.version.Version;

public abstract class Migrator {

	private final Version updatesFromVersion;
	private final Version updatesToVersion;
	
	public Migrator(Version updatesFromVersion, Version updatesToVersion) {
		this.updatesFromVersion = updatesFromVersion;
		this.updatesToVersion = updatesToVersion;
	}

	public Version getUpdatesFromVersion() {
		return updatesFromVersion;
	}

	public Version getUpdatesToVersion() {
		return updatesToVersion;
	}
	
	public GenericObject updateObject(MigrationManager mm, GenericObject oldObject) {
		GenericObject go = this.update(mm, oldObject);
		go.setCurrentVersion(new Version(getUpdatesToVersion()));
		return go;
	}

	protected abstract GenericObject update(MigrationManager mm, GenericObject oldObject);
}
