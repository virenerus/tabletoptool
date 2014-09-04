package com.t3.util.guidreference;

import com.t3.guid.GUID;
import com.t3.guid.UniquelyIdentifiable;
import com.t3.model.Token;
import com.t3.model.Zone;

/**
 * This class contains a number of helper functions for GUID references. All the given functions return null if
 * the given arguments are null but behave otherwise similar to the corresponding GUIDReference functions.
 * @author Virenerus
 */
public class NullHelper {
	
	public static <VALUE extends UniquelyIdentifiable> VALUE value(GUIDReference<VALUE> ref) {
		if(ref==null)
			return null;
		else
			return ref.value();
	}
	
	public static <VALUE extends UniquelyIdentifiable> GUID getId(GUIDReference<VALUE> ref) {
		if(ref==null)
			return null;
		else
			return ref.getId();
	}
	
	public static ZoneReference referenceZone(GUID id) {
		if(id==null)
			return null;
		else
			return new ZoneReference(id);
	}
	
	public static ZoneReference referenceZone(Zone zone) {
		if(zone==null)
			return null;
		else
			return new ZoneReference(zone);
	}

	public static TokenReference referenceToken(Token token) {
		if(token==null)
			return null;
		else
			return new TokenReference(token);
	}

	public static ZoneRendererReference referenceZoneRenderer(GUID id) {
		if(id==null)
			return null;
		else
			return new ZoneRendererReference(id);
	}
}
