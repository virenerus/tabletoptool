package com.t3.xstreamversioned;

public abstract class Migrator {

	private final int updatesFromVersion;
	private final int updatesToVersion;
	
	
	public Migrator(int updatesFromVersion, int updatesToVersion) {
		this.updatesFromVersion = updatesFromVersion;
		this.updatesToVersion = updatesToVersion;
	}

	public int getUpdatesFromVersion() {
		return updatesFromVersion;
	}

	public int getUpdatesToVersion() {
		return updatesToVersion;
	}
	
	public final int updateObject(SerializedObject ser) {
		this.update(ser);
		return this.getUpdatesToVersion();
	}

	protected abstract void update(SerializedObject ser);
}
