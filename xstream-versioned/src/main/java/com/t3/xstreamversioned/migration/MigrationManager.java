package com.t3.xstreamversioned.migration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.t3.xstreamversioned.model.GenericObject;
import com.t3.xstreamversioned.model.GenericObjectManager;
import com.t3.xstreamversioned.version.Version;
import com.t3.xstreamversioned.version.VersionElement;
import com.t3.xstreamversioned.version.VersionGenerator;

public class MigrationManager {
	
	private HashMap<Version, Migrator> migrators=new HashMap<>();
	private final boolean strict;
	private Set<String> versionedPackages=new HashSet<>(1);
	private GenericObjectManager genericObjectManager=new GenericObjectManager();	
	
	public MigrationManager(String versionedPackage, boolean strict) {
		this.versionedPackages.add(versionedPackage);
		this.strict=strict;
	}
	
	public boolean isStrict() {
		return strict;
	}
	
	public void addVersionedPackage(String versionedPackage) {
		this.versionedPackages.add(versionedPackage);
	}
	
	public void registerMigrator(Migrator migrator) {
		migrators.put(migrator.getUpdatesFromVersion(), migrator);
	}

	public Set<String> getVersionedPackages() {
		return Collections.unmodifiableSet(versionedPackages);
	}
	
	public void reset() {
		genericObjectManager=new GenericObjectManager();
	}
	
	public GenericObject migrate(GenericObject go, Class<?> expectedClass) {
		Version currentVersion=go.getCurrentVersion();
		Version targetVersion=VersionGenerator.generateVersion(expectedClass, strict, versionedPackages);
		
		if(targetVersion!=null) {
			while(!targetVersion.equals(currentVersion)) {
				Migrator migrator=migrators.get(currentVersion);
				if(migrator==null)
					throw new RuntimeException("There is a serialized object of type "+expectedClass+" that "
							+ "could only be updated to version "+currentVersion+". There "
							+ "are updaters missing to migrate it to "+ targetVersion);
				else {
					go=migrator.updateObject(this,go);
					genericObjectManager.updateObject(go);
					currentVersion=go.getCurrentVersion();
				}
			}
		}
		return go;
	}

	public GenericObjectManager getGenericObjectManager() {
		return genericObjectManager;
	}
}
