package com.t3.persistence.migrators;

import com.t3.xstreamversioned.migration.MigrationManager;
import com.t3.xstreamversioned.migration.Migrator;
import com.t3.xstreamversioned.model.GenericObject;
import com.t3.xstreamversioned.version.Version;

public class TokenInitiative0_1Migrator extends Migrator {

	public TokenInitiative0_1Migrator() {
		super(  Version.parseVersion("[0@com.t3.model.InitiativeList$TokenInitiative]"), 
				Version.parseVersion("[1@com.t3.model.initiative.InitiativeList$TokenInitiative]"));
	}

	@Override
	protected GenericObject update(MigrationManager mm, GenericObject oldObject) {
		oldObject.removeChild(oldObject.getChild("state"));
		return oldObject;
	}

}
