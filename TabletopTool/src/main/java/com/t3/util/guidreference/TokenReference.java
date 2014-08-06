package com.t3.util.guidreference;

import com.t3.guid.GUID;
import com.t3.model.Token;
import com.t3.model.Zone;
import com.t3.xstreamversioned.SerializationVersion;

@SerializationVersion(0)
public class TokenReference extends GUIDReference<Token> {

	private final ZoneReference zone;
	
	public TokenReference(GUID zoneId, GUID tokenId) {
		this(new ZoneReference(zoneId),tokenId);
	}
	
	public TokenReference(Zone zone, GUID tokenId) {
		this(new ZoneReference(zone),tokenId);
	}
	
	public TokenReference(ZoneReference zone, GUID tokenId) {
		super(tokenId);
		this.zone=zone;
	}
	
	public TokenReference(Token token) {
		super(token);
		this.zone=new ZoneReference(token.getZone());
	}
	
	@Override
	protected Token resolveReference(GUID id) {
		return zone.value().getToken(id);
	}
	
}
