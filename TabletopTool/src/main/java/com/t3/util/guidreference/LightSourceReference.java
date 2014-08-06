package com.t3.util.guidreference;

import com.t3.client.TabletopTool;
import com.t3.guid.GUID;
import com.t3.model.LightSource;
import com.t3.xstreamversioned.SerializationVersion;

@SerializationVersion(0)
public class LightSourceReference extends GUIDReference<LightSource> {

	public LightSourceReference(GUID id) {
		super(id);
	}
	
	public LightSourceReference(LightSource lightSource) {
		super(lightSource);
	}

	@Override
	protected LightSource resolveReference(GUID id) {
		return TabletopTool.getCampaign().getLightSource(id);
	}

}
