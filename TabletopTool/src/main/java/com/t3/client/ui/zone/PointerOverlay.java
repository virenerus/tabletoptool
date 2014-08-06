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
package com.t3.client.ui.zone;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import com.t3.client.ScreenPoint;
import com.t3.image.ImageUtil;
import com.t3.model.Pointer;
import com.t3.model.Zone;
import com.t3.model.ZonePoint;
import com.t3.util.GraphicsUtil;

/**
 * Draws the various "pointer" shapes that users can call up using Spacebar/Ctrl+Spacebar/Shift+Spacebar. The
 * paintOverlay() method is called by ZoneRenderer.renderTokens() and no one else.
 */
public class PointerOverlay implements ZoneOverlay {
	private final List<PointerPair> pointerList = new ArrayList<PointerPair>();
	private static BufferedImage POINTER_IMAGE;
	private static BufferedImage SPEECH_IMAGE;
	private static BufferedImage THOUGHT_IMAGE;

	static {
		try {
			POINTER_IMAGE = ImageUtil.getCompatibleImage("com/t3/client/image/arrow.png");
			SPEECH_IMAGE = ImageUtil.getCompatibleImage("com/t3/client/image/speech.png");
			THOUGHT_IMAGE = ImageUtil.getCompatibleImage("com/t3/client/image/thought.png");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public void paintOverlay(ZoneRenderer renderer, Graphics2D g) {
		Zone zone = renderer.getZone();

		for (int i = 0; i < pointerList.size(); i++) {
			PointerPair p = pointerList.get(i);
			if (p.pointer.getZoneReference().getId().equals(zone.getId())) {
				ZonePoint zPoint = new ZonePoint(p.pointer.getX(), p.pointer.getY());
				ScreenPoint sPoint = ScreenPoint.fromZonePointRnd(renderer, zPoint.x, zPoint.y);

				int offX = 0;
				int offY = 0;
				int centX = 0;
				int centY = 0;
				BufferedImage image = null;
				switch (p.pointer.getType()) {
				case ARROW:
					image = POINTER_IMAGE;
					offX = 2;
					offY = -36;
					break;
				case SPEECH_BUBBLE:
					offX = -19;
					offY = -61;
					centX = 36;
					centY = 23;
					image = SPEECH_IMAGE;
					break;
				case THOUGHT_BUBBLE:
					offX = -13;
					offY = -65;
					centX = 36;
					centY = 23;
					image = THOUGHT_IMAGE;
					break;
				}
				g.drawImage(image, (int) sPoint.x + offX, (int) sPoint.y + offY, null);

				switch (p.pointer.getType()) {
				case ARROW:
					GraphicsUtil.drawBoxedString(g, p.player, (int) sPoint.x + POINTER_IMAGE.getWidth() - 10, (int) sPoint.y - POINTER_IMAGE.getHeight() + 15, SwingUtilities.LEFT);
					break;

				case THOUGHT_BUBBLE:
				case SPEECH_BUBBLE:
					FontMetrics fm = renderer.getFontMetrics(renderer.getFont());
					String name = p.player;
					int len = SwingUtilities.computeStringWidth(fm, name);

					g.setColor(Color.black);
					int x = (int) sPoint.x + centX + offX + 5;
					int y = (int) sPoint.y + offY + centY + fm.getHeight() / 2;
					g.drawString(name, x - len / 2, y);
					break;
				}
			}
		}
	}

	public void addPointer(String player, Pointer pointer) {
		pointerList.add(new PointerPair(player, pointer));
	}

	public void removePointer(String player) {
		for (int i = 0; i < pointerList.size(); i++) {
			if (pointerList.get(i).player.equals(player)) {
				pointerList.remove(i);
			}
		}
	}

	public Pointer getPointer(String player) {
		for (int i = 0; i < pointerList.size(); i++) {
			if (pointerList.get(i).player.equals(player)) {
				return pointerList.get(i).pointer;
			}
		}
		return null;
	}

	private class PointerPair {
		Pointer pointer;
		String player;

		PointerPair(String player, Pointer pointer) {
			this.pointer = pointer;
			this.player = player;
		}
	}
}
