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

package net.rptools.maptool.client.macro.impl;

import java.awt.Color;

import net.rptools.lib.MD5Key;
import net.rptools.maptool.client.AppPreferences;
import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.client.MapToolMacroContext;
import net.rptools.maptool.client.macro.MacroContext;
import net.rptools.maptool.client.macro.MacroDefinition;
import net.rptools.maptool.model.GUID;
import net.rptools.maptool.model.TextMessage;
import net.rptools.maptool.model.Token;

@MacroDefinition(
		name = "say",
		aliases = { "s" },
		description = "say.description")
public class SayMacro extends AbstractMacro {
	public void execute(MacroContext context, String macro, MapToolMacroContext executionContext) {
		macro = processText(macro);
		StringBuilder sb = new StringBuilder();
		StringBuilder speakerBuilder = new StringBuilder();
		String identity = MapTool.getFrame().getCommandPanel().getIdentity();

		if (executionContext != null && MapTool.getParser().isMacroPathTrusted() && !MapTool.getPlayer().isGM()) {
			speakerBuilder.append("<span class='trustedPrefix' ").append("title='").append(executionContext.getName());
			speakerBuilder.append("@").append(executionContext.getSouce()).append("'>");
		}
		speakerBuilder.append(identity).append(": ");
		if (MapTool.getFrame().getCommandPanel().isImpersonating() && AppPreferences.getShowAvatarInChat()) {
			Token token;
			GUID guid = MapTool.getFrame().getCommandPanel().getIdentityGUID();
			if (guid != null)
				token = MapTool.getFrame().getCurrentZoneRenderer().getZone().getToken(guid);
			else
				token = MapTool.getFrame().getCurrentZoneRenderer().getZone().getTokenByName(identity);
			if (token != null) {
				MD5Key imageId = token.getPortraitImage();
				if (imageId == null) {
					imageId = token.getImageAssetId();
				}
				speakerBuilder.append("<br><img src=\"asset://").append(imageId).append("-40\" >");
			}
		}
		if (executionContext != null && MapTool.getParser().isMacroPathTrusted() && !MapTool.getPlayer().isGM()) {
			speakerBuilder.append("</span>");
		}

		Color color = MapTool.getFrame().getCommandPanel().getTextColorWell().getColor();
		if (color != null) {
			sb.append("<span style='color:#").append(String.format("%06X", (color.getRGB() & 0xFFFFFF))).append("'>");
		}
		sb.append(macro);
		if (color != null) {
			sb.append("</span>");
		}
		MapTool.addMessage(TextMessage.say(context.getTransformationHistory(), sb.toString(),speakerBuilder.toString()));
	}
}
