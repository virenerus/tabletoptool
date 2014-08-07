/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3.model;

import com.t3.xstreamversioned.version.SerializationVersion;

@SerializationVersion(0)
public enum Direction {
	NW,
	N,
	NE,
	W,
	CENTER,
	E,
	SW,
	S,
	SE
}
