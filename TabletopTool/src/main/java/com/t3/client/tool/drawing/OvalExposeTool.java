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
package com.t3.client.tool.drawing;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.t3.client.TabletopTool;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.guid.GUID;
import com.t3.model.Zone;
import com.t3.model.drawing.Drawable;
import com.t3.model.drawing.Pen;
import com.t3.util.guidreference.ZoneReference;

public class OvalExposeTool extends OvalTool {
	private static final long serialVersionUID = -9023090752132286356L;

	public OvalExposeTool() {
		try {
			setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("com/t3/client/image/tool/fog-blue-oval.png"))));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public boolean isAvailable() {
		return TabletopTool.getPlayer().isGM();
	}

	@Override
	// Override abstracttool to prevent color palette from
	// showing up
	protected void attachTo(ZoneRenderer renderer) {
		super.attachTo(renderer);
		// Hide the drawable color palette
		TabletopTool.getFrame().hideControlPanel();
	}

	@Override
	protected boolean isBackgroundFill(MouseEvent e) {
		// Expose tools are implied to be filled
		return false;
	}

	@Override
	protected Pen getPen() {
		Pen pen = super.getPen();
		pen.setBackgroundMode(Pen.MODE_TRANSPARENT);
		pen.setThickness(1);
		return pen;
	}

	@Override
	public String getTooltip() {
		return "tool.ovalexpose.tooltip";
	}

	@Override
	public String getInstructions() {
		return "tool.ovalexpose.instructions";
	}

	@Override
	protected void completeDrawable(ZoneReference zone, Pen pen, Drawable drawable) {
		if (!TabletopTool.getPlayer().isGM()) {
			TabletopTool.showError("msg.error.fogexpose");
			TabletopTool.getFrame().refresh();
			return;
		}

		Rectangle bounds = drawable.getBounds();
		Area area = new Area(new Ellipse2D.Double(bounds.x, bounds.y, bounds.width, bounds.height));
		Set<GUID> selectedToks = TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokenSet();
		if (pen.isEraser()) {
			zone.value().hideArea(area, selectedToks);
			TabletopTool.serverCommand().hideFoW(zone.getId(), area, selectedToks);
		} else {
			zone.value().exposeArea(area, selectedToks);
			TabletopTool.serverCommand().exposeFoW(zone.getId(), area, selectedToks);
		}
		TabletopTool.getFrame().refresh();
	}
}
