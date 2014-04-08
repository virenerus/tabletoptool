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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import com.t3.client.AppStyle;
import com.t3.client.TabletopTool;
import com.t3.client.tool.DefaultTool;
import com.t3.client.ui.zone.ZoneOverlay;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.model.Zone;


/**
 * Tool for filling in enclosed areas of topology
 */
public class FillTopologyTool extends DefaultTool implements ZoneOverlay {

	private static final long serialVersionUID = -2125841145363502135L;

	public FillTopologyTool() {
        try {
            setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("com/t3/client/image/tool/top-blue-free.png"))));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    @Override
    public String getTooltip() {
        return "tool.filltopology.tooltip";
    }

	@Override
    public String getInstructions() {
    	return "tool.filltopology.instructions";
    }

	@Override
	public void mouseClicked(MouseEvent e) {

		if (!SwingUtilities.isLeftMouseButton(e)) {
			return;
		}
		
//		ZonePoint zp = new ScreenPoint(e.getX(), e.getY()).convertToZone(renderer);
		
//		Area holeArea = renderer.getTopologyAreaData().getHoleAt(zp.x, zp.y); 
//		if (holeArea == null) {
//			TabletopTool.showError("Must click in an enclosed area");
//			return;
//		}
//		
//        renderer.getZone().addTopology(holeArea);
//        TabletopTool.serverCommand().addTopology(renderer.getZone().getId(), holeArea);
        
        renderer.repaint();
	}
	
    @Override
	public void paintOverlay(ZoneRenderer renderer, Graphics2D g2) {
    	Graphics2D g = (Graphics2D)g2.create();
    	
    	Color oldColor = g.getColor();

    	if (TabletopTool.getPlayer().isGM()) {
	    	Zone zone = renderer.getZone();
	    	Area topology = zone.getTopology();
	
	    	double scale = renderer.getScale();
	    	g.translate(renderer.getViewOffsetX(), renderer.getViewOffsetY());
	    	g.scale(scale, scale);

	    	g.setColor(AppStyle.topologyColor);

	    	g.fill(topology);
	
	    	g.setColor(oldColor);
    	}
    }
    
}
