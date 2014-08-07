package com.t3.util.guidreference;

import com.t3.client.TabletopTool;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.guid.GUID;
import com.t3.xstreamversioned.SerializationVersion;

@SerializationVersion(0)
public class ZoneRendererReference extends CachedGUIDReference<ZoneRenderer> {

	public ZoneRendererReference(GUID id) {
		super(id);
	}
	
	public ZoneRendererReference(ZoneRenderer zoneRenderer) {
		super(zoneRenderer);
	}

	@Override
	protected ZoneRenderer resolveReference(GUID id) {
		return TabletopTool.getFrame().getZoneRenderer(id);
	}
}
