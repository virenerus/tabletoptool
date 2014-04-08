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

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.t3.client.TabletopTool;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.model.GUID;
import com.t3.model.Zone;
import com.t3.model.drawing.Drawable;
import com.t3.model.drawing.LineSegment;
import com.t3.model.drawing.Pen;
import com.t3.model.drawing.ShapeDrawable;

/**
 * Tool for drawing freehand lines.
 */
public class FreehandExposeTool extends FreehandTool implements MouseMotionListener {
	private static final long serialVersionUID = 3258132466219627316L;

	public FreehandExposeTool() {
		try {
			setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("com/t3/client/image/tool/fog-blue-free.png"))));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public String getTooltip() {
		return "tool.freehandexpose.tooltip";
	}

	@Override
	public String getInstructions() {
		return "tool.freehandexpose.instructions";
	}

	@Override
	public boolean isAvailable() {
		return TabletopTool.getPlayer().isGM();
	}

	@Override
	protected Pen getPen() {
		Pen pen = super.getPen();
		pen.setThickness(1);
		return pen;
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
	protected void stopLine(MouseEvent e) {
		LineSegment line = getLine();

		if (line == null)
			return; // Escape has been pressed
		addPoint(e);
		completeDrawable(renderer.getZone().getId(), getPen(), line);
		resetTool();
	}

	@Override
	protected void completeDrawable(GUID zoneId, Pen pen, Drawable drawable) {
		if (!TabletopTool.getPlayer().isGM()) {
			TabletopTool.showError("msg.error.fogexpose");
			TabletopTool.getFrame().refresh();
			return;
		}
		Zone zone = TabletopTool.getCampaign().getZone(zoneId);

		Area area = null;
		if (drawable instanceof LineSegment) {
			area = new Area(getPolygon((LineSegment) drawable));
		}
		if (drawable instanceof ShapeDrawable) {
			area = new Area(((ShapeDrawable) drawable).getShape());
		}
		Set<GUID> selectedToks = TabletopTool.getFrame().getCurrentZoneRenderer().getSelectedTokenSet();
		if (pen.isEraser()) {
			zone.hideArea(area, selectedToks);
			TabletopTool.serverCommand().hideFoW(zone.getId(), area, selectedToks);
		} else {
			zone.exposeArea(area, selectedToks);
			TabletopTool.serverCommand().exposeFoW(zone.getId(), area, selectedToks);
		}
		TabletopTool.getFrame().refresh();
	}
}
