package com.t3.util.guidreference;

import com.t3.client.TabletopTool;
import com.t3.guid.GUID;
import com.t3.model.Zone;
import com.t3.xstreamversioned.SerializationVersion;

@SerializationVersion(0)
public class ZoneReference extends CachedGUIDReference<Zone> {

	public ZoneReference(GUID id) {
		super(id);
	}
	
	public ZoneReference(Zone zone) {
		super(zone);
	}

	@Override
	protected Zone resolveReference(GUID id) {
		return TabletopTool.getFrame().getZoneRenderer(id).getZone();
	}
}
