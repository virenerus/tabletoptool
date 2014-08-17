package com.t3.tools.migration;

import java.util.Collections;

import com.t3.model.Token;
import com.t3.model.initiative.InitiativeList;
import com.t3.model.initiative.InitiativeList.TokenInitiative;
import com.t3.persistence.Persister;
import com.t3.xstreamversioned.version.VersionGenerator;


public class MigrationHelper {
	public static void main(String[] args) {
		System.out.println(VersionGenerator.generateVersion(
				TokenInitiative.class, 
				true, Collections.singleton("com.t3")));
	}
}
