package com.t3.persistence.migrators;

import com.t3.xstreamversioned.migration.MigrationManager;
import com.t3.xstreamversioned.migration.Migrator;
import com.t3.xstreamversioned.model.GenericObject;
import com.t3.xstreamversioned.version.Version;

public class InitiativeList0_1Migrator extends Migrator {

	public InitiativeList0_1Migrator() {
		super(  Version.parseVersion("[0@com.t3.model.InitiativeList]"), 
				Version.parseVersion("[1@com.t3.model.initiative.InitiativeList]"));
	}

	@Override
	protected GenericObject update(MigrationManager mm, GenericObject oldObject) {
		return oldObject; //simply return because this is only a renaming
	}

}
