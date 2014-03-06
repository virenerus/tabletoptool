/*
 *  This software copyright by various authors including the RPTools.net
 *  development team, and licensed under the LGPL Version 3 or, at your
 *  option, any later version.
 *
 *  Portions of this software were originally covered under the Apache
 *  Software License, Version 1.1 or Version 2.0.
 *
 *  See the file LICENSE elsewhere in this distribution for license details.
 */

package com.t3.client.ui.zone;

import java.awt.Graphics2D;
import java.awt.geom.Area;

import com.t3.client.AppStyle;
import com.t3.client.TabletopTool;
import com.t3.model.AttachedLightSource;
import com.t3.model.LightSource;
import com.t3.model.Token;

public class LightSourceIconOverlay implements ZoneOverlay {

	public void paintOverlay(ZoneRenderer renderer, Graphics2D g) {

		for (Token token : renderer.getZone().getAllTokens()) {

			if (token.hasLightSources()) {
				boolean foundNormalLight = false;
				for (AttachedLightSource attachedLightSource : token.getLightSources()) {
					LightSource lightSource = TabletopTool.getCampaign().getLightSource(attachedLightSource.getLightSourceId());
					if (lightSource != null && lightSource.getType() == LightSource.Type.NORMAL) {
						foundNormalLight = true;
						break;
					}
				}
				if (!foundNormalLight) {
					continue;
				}
				
				Area area = renderer.getTokenBounds(token);
				if (area == null) {
					continue;
				}

				int x = area.getBounds().x + (area.getBounds().width - AppStyle.lightSourceIcon.getWidth())/2;
				int y = area.getBounds().y + (area.getBounds().height - AppStyle.lightSourceIcon.getHeight())/2;
				g.drawImage(AppStyle.lightSourceIcon, x, y, null);
			}
		}
	}
}
