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

import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.t3.client.TabletopTool;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.guid.GUID;
import com.t3.model.drawing.Drawable;
import com.t3.model.drawing.LineSegment;
import com.t3.model.drawing.Pen;
import com.t3.util.guidreference.ZoneReference;

/**
 * Tool for drawing freehand lines.
 */
public class PolygonExposeTool extends PolygonTool implements MouseMotionListener {
	private static final long serialVersionUID = 3258132466219627316L;

	public PolygonExposeTool() {
		try {
			setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("com/t3/client/image/tool/fog-blue-poly.png"))));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public boolean isAvailable() {
		return TabletopTool.getPlayer().isGM();
	}

	@Override
	public String getTooltip() {
		return "tool.polyexpose.tooltip";
	}

	@Override
	public String getInstructions() {
		return "tool.polyexpose.instructions";
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
	protected void stopLine(MouseEvent e) {
		LineSegment line = getLine();

		if (line == null)
			return; // Escape has been pressed
		addPoint(e);
		completeDrawable(new ZoneReference(renderer.getZone()), getPen(), line);
		resetTool();
	}

	@Override
	protected void completeDrawable(ZoneReference zone, Pen pen, Drawable drawable) {
		if (!TabletopTool.getPlayer().isGM()) {
			TabletopTool.showError("msg.error.fogexpose");
			TabletopTool.getFrame().refresh();
			return;
		}

		Polygon polygon = getPolygon((LineSegment) drawable);
		Area area = new Area(polygon);
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
